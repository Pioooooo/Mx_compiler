package transforms.utils;

import ir.Module;
import ir.*;
import ir.inst.AllocaInst;
import ir.inst.LoadInst;
import ir.inst.PhiInst;
import ir.inst.StoreInst;
import ir.values.ConstantPointerNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class MemToReg {
    Module m;

    public MemToReg(Module m) {
        this.m = m;
    }

    DominatorTree dom;
    HashMap<PhiInst, AllocaInst> phiAllocaMap;
    HashMap<BasicBlock, ArrayList<PhiInst>> insertedPhi;
    HashSet<BasicBlock> visited;

    public void run() {
        m.functions.values().forEach(this::run);
    }

    void run(Function f) {
        dom = new DominatorTree(f);
        dom.run();
        dom.calcDomFrontier();
        phiAllocaMap = new HashMap<>();
        insertedPhi = new HashMap<>();
        f.basicBlockList.forEach(b -> insertedPhi.put(b, new ArrayList<>()));
        for (var it = f.allocas.iterator(); it.hasNext(); ) {
            AllocaInst a = it.next();
            if (a.use.isEmpty()) {
                a.removeSelf();
                it.remove();
                continue;
            }
            BasicBlock useBlock = ((Inst) a.use.get(0)).getParent();
            if (a.use.stream().noneMatch(i -> ((Inst) i).getParent() != useBlock)) {
                rewriteSingleBlockAlloca(a, useBlock);
                if (a.use.isEmpty()) {
                    a.removeSelf();
                    it.remove();
                    continue;
                }
            }
            if (a.storeInst.size() == 1) {
                rewriteSingleStoreAlloca(a);
                if (a.use.isEmpty()) {
                    a.removeSelf();
                    it.remove();
                    continue;
                }
            }
            phiInsert(a);
        }
        visited = new HashSet<>();
        rename(f);
        phiEliminate(f);
    }

    void phiInsert(AllocaInst a) {
        HashSet<BasicBlock> defBlocks = new HashSet<>();
        HashSet<BasicBlock> worklist = new HashSet<>();
        HashMap<BasicBlock, PhiInst> phi = new HashMap<>();
        a.storeInst.forEach(s -> {
            defBlocks.add(s.getParent());
            worklist.add(s.getParent());
        });
        HashSet<BasicBlock> liveInBlocks = computeLiveInBlocks(a, defBlocks);
        while (!worklist.isEmpty()) {
            var it = worklist.iterator();
            BasicBlock x = it.next();
            it.remove();
            dom.getDomFrontier().getOrDefault(x, new ArrayList<>()).stream().filter(liveInBlocks::contains).filter(y -> !phi.containsKey(y)).forEach(y -> {
                PhiInst p = PhiInst.create(a.getType().getBaseType(), y);
                phi.put(y, p);
                phiAllocaMap.put(p, a);
                insertedPhi.get(y).add(p);
                if (!defBlocks.contains(y)) {
                    worklist.add(y);
                }
            });
        }
    }

    static class RenameData {
        BasicBlock b, pred;
        HashMap<AllocaInst, Value> values;

        RenameData(BasicBlock b, BasicBlock pred, HashMap<AllocaInst, Value> values) {
            this.b = b;
            this.pred = pred;
            this.values = values;
        }
    }

    void rename(Function f) {
        Stack<RenameData> worklist = new Stack<>();
        HashMap<AllocaInst, Value> values = new HashMap<>();
        f.allocas.forEach(a -> values.put(a, ConstantPointerNull.get(f.getParent())));
        worklist.push(new RenameData(f.getHead().get(), null, values));
        do {
            RenameData rData = worklist.pop();
            rename(rData.b, rData.pred, rData.values, worklist);
        } while (!worklist.isEmpty());
        f.allocas.forEach(AllocaInst::removeSelf);
    }

    void rename(BasicBlock b, BasicBlock pred, HashMap<AllocaInst, Value> incomingValues, Stack<RenameData> worklist) {
        while (true) {
            BasicBlock finalPred = pred;
            insertedPhi.get(b).forEach(p -> {
                AllocaInst a = phiAllocaMap.get(p);
                p.addIncoming(finalPred, incomingValues.get(a));
                incomingValues.put(a, p);
            });
            if (!visited.add(b)) {
                return;
            }
            b.instList.forEach(inst -> {
                if (inst instanceof StoreInst && ((StoreInst) inst).ptr instanceof AllocaInst) {
                    AllocaInst a = (AllocaInst) ((StoreInst) inst).ptr;
                    incomingValues.put(a, ((StoreInst) inst).val);
                    inst.removeSelf();
                } else if (inst instanceof LoadInst && ((LoadInst) inst).ptr instanceof AllocaInst) {
                    AllocaInst a = (AllocaInst) ((LoadInst) inst).ptr;
                    inst.replaceUseWith(incomingValues.get(a));
                    inst.removeSelf();
                }
            });
            if (b.suc.isEmpty()) {
                return;
            }
            pred = b;
            var i = b.suc.iterator();
            b = i.next();
            while (i.hasNext()) {
                worklist.push(new RenameData(i.next(), pred, new HashMap<>(incomingValues)));
            }
        }
    }

    void phiEliminate(Function f) {
        boolean eliminated = true;
        while (eliminated) {
            eliminated = false;
            var it = phiAllocaMap.keySet().iterator();
            while (it.hasNext()) {
                PhiInst p = it.next();
                Value v = p.simplify();
                if (v != null) {
                    p.replaceUseWith(v);
                    p.removeSelf();
                    it.remove();
                    eliminated = true;
                }
            }
        }
    }

    HashSet<BasicBlock> computeLiveInBlocks(AllocaInst a, HashSet<BasicBlock> defBlocks) {
        HashSet<BasicBlock> liveInBlocks = new HashSet<>();
        HashSet<BasicBlock> usingBlocks = new HashSet<>();
        a.loadInst.forEach(l -> usingBlocks.add(l.getParent()));
        ArrayList<BasicBlock> worklist = new ArrayList<>(usingBlocks);
        for (int i = 0; i < worklist.size(); i++) {
            BasicBlock b = worklist.get(i);
            if (!defBlocks.contains(b)) {
                continue;
            }
            for (var inst : b) {
                if (inst instanceof StoreInst && ((StoreInst) inst).ptr == a) {
                    worklist.set(i, worklist.get(worklist.size() - 1));
                    worklist.remove(worklist.size() - 1);
                    i--;
                    break;
                }
                if (inst instanceof LoadInst && ((LoadInst) inst).ptr == a) {
                    break;
                }
            }
        }
        while (!worklist.isEmpty()) {
            BasicBlock b = worklist.get(worklist.size() - 1);
            worklist.remove(worklist.size() - 1);
            if (!liveInBlocks.add(b)) {
                continue;
            }
            b.pre.forEach(p -> {
                if (!defBlocks.contains(p)) {
                    worklist.add(p);
                }
            });
        }
        return liveInBlocks;
    }

    void rewriteSingleBlockAlloca(AllocaInst a, BasicBlock b) {
        Value cur = null;
        for (Inst i : b.instList) {
            if (i instanceof StoreInst && ((StoreInst) i).ptr == a) {
                cur = ((StoreInst) i).val;
                i.removeSelf();
            } else if (i instanceof LoadInst && ((LoadInst) i).ptr == a && cur != null) {
                i.replaceUseWith(cur);
                i.removeSelf();
            }
        }
    }

    void rewriteSingleStoreAlloca(AllocaInst a) {
        StoreInst store = a.storeInst.get(0);
        BasicBlock storeBlock = store.getParent();
        Value val = store.val;
        boolean stored = false;
        for (Inst i : storeBlock.instList) {
            if (i instanceof StoreInst && ((StoreInst) i).ptr == a) {
                stored = true;
            } else if (i instanceof LoadInst && ((LoadInst) i).ptr == a && stored) {
                i.replaceUseWith(val);
                i.removeSelf();
            }
        }
        a.loadInst.forEach(l -> {
            if (dom.dominates(storeBlock, l.getParent())) {
                l.replaceUseWith(val);
                l.removeSelf();
            }
        });
        if (a.use.isEmpty()) {
            store.removeSelf();
        }
    }
}
