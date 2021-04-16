package transforms.utils;

import ir.BasicBlock;
import ir.Function;
import ir.Module;

public class DeadBlockRemove {
    Module m;

    public DeadBlockRemove(Module m) {
        this.m = m;
    }

    public void run() {
        m.functions.values().forEach(this::run);
    }

    void run(Function f) {
        f.basicBlockList.forEach(this::run);
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

    void run(BasicBlock b) {
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
}
