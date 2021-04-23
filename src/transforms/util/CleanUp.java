package transforms.util;

import ir.BasicBlock;
import ir.Function;
import ir.Module;
import ir.inst.CallInst;

import java.util.concurrent.atomic.AtomicBoolean;

public class CleanUp {
    Module m;

    public CleanUp(Module m) {
        this.m = m;
    }

    public void run() {
        m.functions.values().forEach(this::run);
    }

    void run(Function f) {
        deadBlockRemove(f);
        deadInstRemove(f);
    }

    void deadBlockRemove(Function f) {
        f.basicBlockList.forEach(this::deadBlockRemove);
        BasicBlock entry = f.basicBlockList.getHead().get();
        boolean eliminated = true;
        while (eliminated) {
            eliminated = false;
            for (BasicBlock b : f.basicBlockList) {
                if (b != entry && b.pre.size() == 0) {
                    b.suc.forEach(s -> s.pre.remove(b));
                    b.removeSelf();
                    eliminated = true;
                }
            }
        }
    }

    void deadBlockRemove(BasicBlock b) {
        if (b.suc.isEmpty()) {
            return;
        }
        BasicBlock next = b.suc.get(0);
        if (b.suc.size() == 1 && next.pre.size() == 1) {
            b.instList.remove(b.getTail().previous());
            b.suc = next.suc;
            b.instList.addAll(next.instList);
            next.suc.forEach(s -> {
                s.pre.remove(next);
                s.pre.add(b);
            });
            next.removeSelf();
            // for Inst in next.instList
            next.instList = b.instList;
            next.pre = b.pre;
        }
    }

    void deadInstRemove(Function f) {
        AtomicBoolean eliminated = new AtomicBoolean(true);
        while (eliminated.get()) {
            eliminated.set(false);
            f.basicBlockList.forEach(b -> b.forEach(i -> {
                if (!i.getType().isVoid() && !(i instanceof CallInst) && i.use.size() == 0) {
                    i.removeSelfAndUse();
                    eliminated.set(true);
                }
            }));
        }
    }
}
