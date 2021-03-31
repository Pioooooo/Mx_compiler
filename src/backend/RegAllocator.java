package backend;

import asm.AsmBlock;
import asm.AsmFunction;
import asm.AsmInst;
import asm.AsmRoot;
import asm.inst.*;
import asm.operand.Immediate;
import asm.operand.PReg;
import asm.operand.Register;
import asm.operand.VReg;
import util.list.ListIterator;

import java.util.*;

public class RegAllocator {
    static class edge {
        Register x, y;

        public edge(Register x, Register y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return x.hashCode() ^ y.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof edge && ((edge) obj).x == x && ((edge) obj).y == y);
        }
    }

    AsmRoot root;
    AsmFunction currentFunction;

    public RegAllocator(AsmRoot root) {
        this.root = root;
    }

    public void run() {
        root.functions.values().forEach(this::colorFunction);
    }

    HashMap<Register, Double> weight;
    HashMap<Register, Integer> offset;

    int K;
    HashSet<Register> preColored, initial, simplifyWorklist, freezeWorklist,
            spillWorklist, spilledNodes, coalescedNodes, coloredNodes, unspillableNodes;
    Stack<Register> selectStack;
    HashSet<Mv> coalescedMoves, constrainedMoves, frozenMoves, workListMoves, activeMoves;
    HashSet<edge> adjSet;
    HashMap<Register, HashSet<Register>> adjList;
    HashMap<Register, Integer> degree;
    HashMap<Register, HashSet<Mv>> moveList;
    HashMap<Register, Register> alias;

    void colorFunction(AsmFunction function) {
        currentFunction = function;
        init();
        livenessAnalysis();
        build();
        makeWorklist();
        while (!simplifyWorklist.isEmpty() || !workListMoves.isEmpty() || !freezeWorklist.isEmpty() || !spillWorklist.isEmpty()) {
            if (!simplifyWorklist.isEmpty()) {
                simplify();
            } else if (!workListMoves.isEmpty()) {
                coalesce();
            } else if (!freezeWorklist.isEmpty()) {
                freeze();
            } else {
                selectSpill();
            }
        }
        assignColors();
        if (!spilledNodes.isEmpty()) {
            rewriteProgram();
            colorFunction(function);
        } else {
            addSp();
            removeDeadMv();
            removeUselessBlock();
        }
        currentFunction = null;
    }

    void init() {
        K = root.getColors().size();
        preColored = new HashSet<>(root.getPReg());
        initial = new HashSet<>();
        simplifyWorklist = new HashSet<>();
        freezeWorklist = new HashSet<>();
        spillWorklist = new HashSet<>();
        spilledNodes = new HashSet<>();
        coalescedNodes = new HashSet<>();
        coloredNodes = new HashSet<>();
        unspillableNodes = new HashSet<>();
        selectStack = new Stack<>();
        coalescedMoves = new HashSet<>();
        constrainedMoves = new HashSet<>();
        frozenMoves = new HashSet<>();
        workListMoves = new HashSet<>();
        activeMoves = new HashSet<>();
        adjSet = new HashSet<>();
        adjList = new HashMap<>();
        degree = new HashMap<>();
        moveList = new HashMap<>();
        alias = new HashMap<>();

        weight = new HashMap<>();
        offset = new HashMap<>();
        currentFunction.blocks.forEach(b -> b.instList.forEach(i -> {
            initial.addAll(i.getUse());
            initial.addAll(i.getDef());
        }));
        initial.forEach(x -> {
            moveList.put(x, new HashSet<>());
            adjList.put(x, new HashSet<>());
            weight.put(x, 0.0);
            degree.put(x, 0);
            alias.put(x, x);
            x.color = null;
        });
        initial.removeAll(preColored);
        preColored.forEach(x -> {
            degree.put(x, (int) 1e7);
            x.color = (PReg) x;
        });
        currentFunction.blocks.forEach(b -> b.instList.forEach(i -> {
            i.getUse().forEach(u -> weight.replace(u, weight.get(u) + Math.pow(10.0, b.loopDepth)));
            i.getDef().forEach(d -> weight.replace(d, weight.get(d) + Math.pow(10.0, b.loopDepth)));
        }));
    }

    HashMap<AsmBlock, HashSet<Register>> bUses, bDefs, bLiveIn, bLiveOut;

    void livenessAnalysis() {
        bUses = new HashMap<>();
        bDefs = new HashMap<>();
        bLiveIn = new HashMap<>();
        bLiveOut = new HashMap<>();
        currentFunction.blocks.forEach(b -> {
            HashSet<Register> uses = new HashSet<>(), defs = new HashSet<>();
            b.instList.forEach(i -> {
                HashSet<Register> t = i.getUse();
                t.removeAll(defs);
                uses.addAll(t);
                defs.addAll(i.getDef());
            });
            bUses.put(b, uses);
            bDefs.put(b, defs);
            bLiveIn.put(b, new HashSet<>());
            bLiveOut.put(b, new HashSet<>());
        });
        HashSet<AsmBlock> vis = new HashSet<>();
        Queue<AsmBlock> q = new LinkedList<>();
        currentFunction.blocks.forEach(b -> {
            if (b.suc.isEmpty()) {
                vis.add(b);
                q.add(b);
            }
        });
        while (!q.isEmpty()) {
            AsmBlock b = q.poll();
            vis.remove(b);
            HashSet<Register> liveOut = new HashSet<>();
            b.suc.forEach(a -> liveOut.addAll(bLiveIn.get(a)));
            bLiveOut.replace(b, liveOut);
            HashSet<Register> liveIn = new HashSet<>(liveOut);
            liveIn.removeAll(bDefs.get(b));
            liveIn.addAll(bUses.get(b));
            if (!liveIn.equals(bLiveIn.get(b))) {
                bLiveIn.replace(b, liveIn);
                b.pre.forEach(a -> {
                    if (!vis.contains(a)) {
                        vis.add(a);
                        q.add(a);
                    }
                });
            }
        }
    }

    void build() {
        currentFunction.blocks.forEach(b -> {
            HashSet<Register> live = new HashSet<>(bLiveOut.get(b));
            ListIterator<AsmInst> it = b.getTail();
            while (it.hasPrevious()) {
                AsmInst i = it.previous();
                if (i instanceof Mv) {
                    live.removeAll(i.getUse());
                    HashSet<Register> t = i.getDef();
                    t.addAll(i.getUse());
                    t.forEach(n -> moveList.get(n).add((Mv) i));
                    workListMoves.add((Mv) i);
                }
                live.addAll(i.getDef());
                i.getDef().forEach(d -> live.forEach(l -> addEdge(l, d)));
                live.removeAll(i.getDef());
                live.addAll(i.getUse());
            }
        });
    }

    void addEdge(Register u, Register v) {
        if (u != v && !adjSet.contains(new edge(u, v))) {
            adjSet.add(new edge(u, v));
            adjSet.add(new edge(v, u));
            if (!preColored.contains(u)) {
                adjList.get(u).add(v);
                degree.replace(u, degree.get(u) + 1);
            }
            if (!preColored.contains(v)) {
                adjList.get(v).add(u);
                degree.replace(v, degree.get(v) + 1);
            }
        }
    }

    void makeWorklist() {
        initial.forEach(n -> {
            if (degree.get(n) >= K) {
                spillWorklist.add(n);
            } else if (moveRelated(n)) {
                freezeWorklist.add(n);
            } else {
                simplifyWorklist.add(n);
            }
        });
        initial.clear();
    }

    HashSet<Register> adjacent(Register n) {
        HashSet<Register> ret = new HashSet<>(adjList.get(n));
        ret.removeAll(selectStack);
        ret.removeAll(coalescedNodes);
        return ret;
    }

    HashSet<Mv> nodeMoves(Register n) {
        HashSet<Mv> ret = new HashSet<>(activeMoves);
        ret.addAll(workListMoves);
        ret.retainAll(moveList.get(n));
        return ret;
    }

    boolean moveRelated(Register n) {
        return !nodeMoves(n).isEmpty();
    }

    void simplify() {
        Register n = simplifyWorklist.iterator().next();
        simplifyWorklist.remove(n);
        selectStack.push(n);
        adjacent(n).forEach(this::decrementDegree);
    }

    void decrementDegree(Register m) {
        int d = degree.get(m);
        degree.replace(m, d - 1);
        if (d == K) {
            HashSet<Register> nodes = adjacent(m);
            nodes.add(m);
            enableMoves(nodes);
            spillWorklist.remove(m);
            if (moveRelated(m)) {
                freezeWorklist.add(m);
            } else {
                simplifyWorklist.add(m);
            }
        }
    }

    void enableMoves(HashSet<Register> nodes) {
        nodes.forEach(n -> nodeMoves(n).forEach(m -> {
            if (activeMoves.contains(m)) {
                activeMoves.remove(m);
                workListMoves.add(m);
            }
        }));
    }

    void coalesce() {
        Mv m = workListMoves.iterator().next();
        Register x = getAlias(m.reg), y = getAlias(m.src), u, v;
        if (preColored.contains(y)) {
            u = y;
            v = x;
        } else {
            u = x;
            v = y;
        }
        workListMoves.remove(m);
        if (u == v) {
            coalescedMoves.add(m);
            addWorkList(u);
        } else if (preColored.contains(v) || adjSet.contains(new edge(u, v))) {
            constrainedMoves.add(m);
            addWorkList(u);
            addWorkList(v);
        } else if (preColored.contains(u) && ok(adjacent(v), u) ||
                !preColored.contains(u) && conservative(adjacent(x), adjacent(y))) {
            coalescedMoves.add(m);
            combine(u, v);
            addWorkList(u);
        } else {
            activeMoves.add(m);
        }
    }

    void addWorkList(Register u) {
        if (!preColored.contains(u) && !moveRelated(u) && degree.get(u) < K) {
            freezeWorklist.remove(u);
            simplifyWorklist.add(u);
        }
    }

    boolean ok(HashSet<Register> adj, Register u) {
        return adj.stream().filter(t -> !ok(t, u)).findAny().isEmpty();
    }

    boolean ok(Register t, Register r) {
        return degree.get(t) < K || preColored.contains(t) || adjSet.contains(new edge(t, r));
    }

    boolean conservative(HashSet<Register> nodes, HashSet<Register> y) {
        nodes.addAll(y);
        return nodes.stream().filter(n -> degree.get(n) >= K).count() < K;
    }

    Register getAlias(Register n) {
        if (coalescedNodes.contains(n)) {
            return getAlias(alias.get(n));
        } else {
            return n;
        }
    }

    void combine(Register u, Register v) {
        if (freezeWorklist.contains(v)) {
            freezeWorklist.remove(v);
        } else {
            spillWorklist.remove(v);
        }
        coalescedNodes.add(v);
        alias.put(v, u);
        moveList.get(u).addAll(moveList.get(v));
        enableMoves(new HashSet<>(Collections.singletonList(v)));
        adjacent(v).forEach(t -> {
            addEdge(t, u);
            decrementDegree(t);
        });
        if (degree.get(u) >= K && freezeWorklist.contains(u)) {
            freezeWorklist.remove(u);
            spillWorklist.add(u);
        }
    }

    void freeze() {
        Register u = freezeWorklist.iterator().next();
        freezeWorklist.remove(u);
        simplifyWorklist.add(u);
        freezeMoves(u);
    }

    void freezeMoves(Register u) {
        nodeMoves(u).forEach(m -> {
            Register x = m.reg, y = m.src, v;
            if (getAlias(y) == getAlias(u)) {
                v = getAlias(x);
            } else {
                v = getAlias(y);
            }
            activeMoves.remove(m);
            frozenMoves.add(m);
            if (nodeMoves(v).isEmpty() && degree.get(v) < K) {
                freezeWorklist.remove(v);
                simplifyWorklist.add(v);
            }
        });
    }

    void selectSpill() {
        Register m = null;
        double min = Double.POSITIVE_INFINITY;
        for (Register x : spillWorklist) {
            if (unspillableNodes.contains(x) || preColored.contains(x)) {
                continue;
            }
            double t = weight.get(x) / degree.get(x);
            if (t < min) {
                m = x;
                min = t;
            }
        }
        spillWorklist.remove(m);
        simplifyWorklist.add(m);
        freezeMoves(m);
    }

    void assignColors() {
        while (!selectStack.isEmpty()) {
            Register n = selectStack.pop();
            ArrayList<PReg> okColors = root.getColors();
            HashSet<Register> colored = new HashSet<>(coloredNodes);
            colored.addAll(preColored);
            adjList.get(n).forEach(w -> {
                if (colored.contains(getAlias(w))) {
                    okColors.remove(getAlias(w).color);
                }
            });
            if (okColors.isEmpty()) {
                spilledNodes.add(n);
            } else {
                coloredNodes.add(n);
                n.color = okColors.get(0);
            }
        }
        coalescedNodes.forEach(n -> n.color = getAlias(n).color);
    }

    void rewriteProgram() {
        spilledNodes.forEach(v -> {
            offset.put(v, currentFunction.spOffset);
            currentFunction.spOffset += 4;
        });
        currentFunction.blocks.forEach(b -> {
            ListIterator<AsmInst> it = b.instList.getHead();
            while (it.hasNext()) {
                AsmInst inst = it.next();
                inst.getUse().forEach(n -> {
                    if (spilledNodes.contains(n)) {
                        if (inst instanceof Mv) {
                            Load.createW(((Mv) inst).reg, root.getPReg("sp"), Immediate.create(offset.get(n)), inst);
                            b.instList.remove(inst);
                        } else {
                            VReg tmp = VReg.create();
                            unspillableNodes.add(tmp);
                            inst.replaceUse(n, tmp);
                            Load.createW(tmp, root.getPReg("sp"), Immediate.create(offset.get(n)), inst);
                        }
                    }
                });
                inst.getDef().forEach(n -> {
                    if (spilledNodes.contains(n)) {
                        if (inst instanceof Mv) {
                            Store.createW(((Mv) inst).src, root.getPReg("sp"), Immediate.create(offset.get(n)), inst);
                            b.instList.remove(inst);
                        } else {
                            VReg tmp = VReg.create();
                            unspillableNodes.add(tmp);
                            inst.replaceDef(n, tmp);
                            Store.createW(tmp, root.getPReg("sp"), Immediate.create(offset.get(n)), inst.getNext().get());
                            it.next();
                        }
                    }
                });
            }
        });
    }

    void removeDeadMv() {
        currentFunction.blocks.forEach(b -> b.instList.forEach(i -> {
            if (i instanceof Mv && ((Mv) i).reg.color == ((Mv) i).src.color) {
                b.instList.remove(i);
            }
        }));
    }

    void removeUselessBlock() {
        currentFunction.blocks.forEach(block -> {
            if (block.getHead().get() instanceof J) {
                AsmBlock dest = ((J) block.getHead().get()).dest;
                currentFunction.blocks.forEach(b -> {
                    if (b.pre.contains(block)) {
                        b.pre.remove(block);
                        b.pre.add(dest);
                    }
                    if (b.suc.contains(block)) {
                        b.suc.remove(block);
                        b.suc.add(dest);
                    }
                    b.instList.forEach(i -> {
                        if (i instanceof J && ((J) i).dest == block) {
                            ((J) i).dest = dest;
                        }
                        if (i instanceof Branch && ((Branch) i).dest == block) {
                            ((Branch) i).dest = dest;
                        }
                    });
                });
                currentFunction.blocks.remove(block);
//            } else if (block.getTail().get() instanceof J && ((J) block.getTail().get()).dest == block.getNext()) {
//                block.instList.remove(block.getTail().get());
            }
        });
    }

    void addSp() {
        int realOffset = currentFunction.spOffset + Math.max(0, currentFunction.args.size() - 8) * 4;
        if (realOffset != 0) {
            Calc.createI(root.getPReg("sp"), Calc.OpType.addi, root.getPReg("sp"), Immediate.create(-realOffset), currentFunction.getHead().get().getHead().get());
            currentFunction.getTail().forEach(t -> Calc.createI(root.getPReg("sp"), Calc.OpType.addi, root.getPReg("sp"), Immediate.create(realOffset),t.getTail().previous()));
        }
        currentFunction.getHead().get().instList.forEach(i -> {
            if (i instanceof Load && ((Load) i).offset.inParam) {
                ((Load) i).offset.val += currentFunction.spOffset;
            }
        });
    }
}
