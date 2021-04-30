package transforms;

import ir.Module;
import ir.*;
import ir.inst.BrInst;
import ir.inst.LoadInst;
import ir.inst.PhiInst;
import transforms.util.DominatorTree;
import util.error.InternalError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class LICM {
    Module m;

    public LICM(Module m) {
        this.m = m;
    }

    public void run() {
        m.functions.values().forEach(this::run);
    }

    DominatorTree dom;
    HashSet<BasicBlock> loopBlocks;

    void run(Function f) {
        if (!f.doOptimize()) {
            return;
        }
        dom = new DominatorTree(f);
        dom.run();
        ArrayList<BasicBlock> revSeq = new ArrayList<>();
        dom.forEach(b -> revSeq.add(0, b));
        HashSet<BasicBlock> done = new HashSet<>();
        for (BasicBlock b : revSeq) {
            for (BasicBlock head : b.suc) {
                if (dom.dominates(head, b) && !done.contains(head)) {
                    var sub = dom.getSubDom().get(head);
                    var tails = head.pre.stream().filter(sub::contains)
                            .collect(Collectors.toCollection(HashSet::new));
                    done.add(head);
                    loopBlocks = collectLoop(head, tails);
                    if (loopBlocks != null) {
                        var pre = new HashSet<>(head.pre);
                        pre.removeIf(tails::contains);
                        BasicBlock preHead;
                        if (pre.size() == 1) {
                            preHead = pre.iterator().next();
                        } else {
                            preHead = BasicBlock.create(head.loopDepth, m, f, head);
                            var it = head.use.iterator();
                            while (it.hasNext()) {
                                var i = it.next();
                                if (i instanceof BrInst && !tails.contains(i.getParent())) {
                                    i.replaceUse(head, preHead);
                                    i.getParent().suc.remove(head);
                                    i.getParent().suc.add(preHead);
                                    preHead.pre.add(i.getParent());
                                    it.remove();
                                }
                            }
                            head.forEach(i -> {
                                if (i instanceof PhiInst) {
                                    var phi = PhiInst.create(i.getType(), preHead, null);
                                    var iter = ((PhiInst) i).blocks.entrySet().iterator();
                                    while (iter.hasNext()) {
                                        var e = iter.next();
                                        BasicBlock p = e.getKey();
                                        Value v = e.getValue();
                                        if (!tails.contains(p)) {
                                            phi.addIncoming(p, v);
                                            iter.remove();
                                            p.removeUse(i);
                                            v.removeUse(i);
                                        }
                                    }
                                    ((PhiInst) i).addIncoming(preHead, phi);
                                    BrInst.create(head, preHead);
                                    Value v = i.simplify();
                                    if (v != null) {
                                        i.replaceUseWith(v);
                                        i.removeSelfAndDef();
                                    }
                                    v = phi.simplify();
                                    if (v != null) {
                                        phi.replaceUseWith(v);
                                        phi.removeSelfAndDef();
                                    }
                                }
                            });
                            BrInst.create(head, preHead);
                        }
                        Queue<Inst> worklist = new LinkedList<>();
                        loopBlocks.forEach(l -> l.forEach(i -> {
                            if (movable(i)) {
                                worklist.add(i);
                            }
                        }));
                        while (!worklist.isEmpty()) {
                            var i = worklist.poll();
                            try {
                                i.removeSelf();
                            } catch (InternalError ignored) {
                            }
                            preHead.instList.insertBefore(preHead.getTail().previous(), i);
                            i.setParent(preHead);
                            i.getDef().forEach(d -> {
                                if (movable(d)) {
                                    worklist.add((Inst) d);
                                }
                            });
                        }
                    }
                    break;
                }
            }
        }
    }

    boolean movable(Value i) {
        if (!(i instanceof Inst)) {
            return false;
        }
        if (i instanceof LoadInst) {
            return false;
        }
        return loopBlocks.contains(((Inst) i).getParent()) && ((Inst) i).noSideEffect() && !(i instanceof PhiInst)
                && i.getDef().stream().filter(d -> d instanceof Inst)
                .noneMatch(d -> loopBlocks.contains(((Inst) d).getParent()));
    }

    HashSet<BasicBlock> collectLoop(BasicBlock head, HashSet<BasicBlock> tails) {
        HashSet<BasicBlock> loopBlocks = new HashSet<>(tails);
        loopBlocks.add(head);
        Queue<BasicBlock> worklist = new LinkedList<>(tails);
        while (!worklist.isEmpty()) {
            var b = worklist.poll();
            for (BasicBlock p : b.pre) {
                if (!loopBlocks.contains(p)) {
                    if (!dom.dominates(head, p)) {
                        return null;
                    }
                    loopBlocks.add(p);
                    worklist.add(p);
                }
            }
        }
        return loopBlocks;
    }
}
