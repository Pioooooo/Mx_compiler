package transforms;

import ir.Function;
import ir.Inst;
import ir.Module;
import ir.Value;

import java.util.LinkedList;
import java.util.Queue;

public class SCCP {
    Module m;

    public SCCP(Module m) {
        this.m = m;
    }

    public void run() {
        m.functions.values().forEach(this::run);
    }

    void run(Function f) {
        if (!f.doOptimize()) {
            return;
        }
        Queue<Inst> worklist = new LinkedList<>();
        Queue<Inst> finalWorklist = worklist;
        f.basicBlockList.forEach(b -> b.forEach(finalWorklist::add));
        boolean eliminated = true;
        while (eliminated) {
            eliminated = false;
            Queue<Inst> newWorklist = new LinkedList<>();
            while (!worklist.isEmpty()) {
                Inst i = worklist.poll();
                if (!i.inList()) {
                    continue;
                }
                Value v = i.simplify();
                if (v != null) {
                    worklist.addAll(i.use);
                    i.replaceUseWith(v);
                    i.removeSelfAndDef();
                    eliminated = true;
                } else {
                    newWorklist.add(i);
                }
            }
            worklist = newWorklist;
        }
    }
}
