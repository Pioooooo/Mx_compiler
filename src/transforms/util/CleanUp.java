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
        BasicBlock entry = f.basicBlockList.getHead().get();
        boolean eliminated = true;
        while (eliminated) {
            eliminated = false;
            for (BasicBlock b : f.basicBlockList) {
                if (b != entry && b.pre.size() == 0) {
                    b.suc.forEach(s -> s.pre.remove(b));
                    b.replaceUse();
                    b.removeSelf();
                    eliminated = true;
                }
            }
        }
        eliminated = true;
        while (eliminated) {
            eliminated = false;
            for (BasicBlock b : f.basicBlockList) {
                if (deadBlockRemove(b)) {
                    eliminated = true;
                }
            }
        }
    }

    boolean deadBlockRemove(BasicBlock b) {
        if (b.suc.isEmpty()) {
            return false;
        }
        BasicBlock next = b.suc.iterator().next();
        if (b.suc.size() == 1 && next.pre.size() == 1) {
            b.getTail().previous().removeSelfAndDef();
            b.suc = next.suc;
            b.instList.addAll(next.instList);
            next.instList.forEach(i -> i.setParent(b));
            next.suc.forEach(s -> {
                s.pre.remove(next);
                s.pre.add(b);
            });
            next.replaceUse();
            next.removeSelf();
            return true;
        }
        return false;
    }

    void deadInstRemove(Function f) {
        AtomicBoolean eliminated = new AtomicBoolean(true);
        while (eliminated.get()) {
            eliminated.set(false);
            f.basicBlockList.forEach(b -> b.forEach(i -> {
                if (!i.getType().isVoid() && !(i instanceof CallInst) && i.use.isEmpty()) {
                    i.removeSelfAndDef();
                    eliminated.set(true);
                }
            }));
        }
    }
}
