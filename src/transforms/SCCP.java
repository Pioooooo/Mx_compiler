package transforms;

import ir.Function;
import ir.Inst;
import ir.Module;
import ir.Value;

import java.util.HashSet;
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
        f.basicBlockList.forEach(b -> b.forEach(worklist::add));
        boolean eliminated = true;
        HashSet<Inst> done = new HashSet<>();
        while (eliminated) {
            eliminated = false;
            while (!worklist.isEmpty()) {
                Inst i = worklist.poll();
                if (!i.inList()) {
                    continue;
                }
                Value v = i.simplify();
                if (v != null) {
                    done.add(i);
                    i.use.forEach(u -> {
                        if (!done.contains(u)) {
                            worklist.add(u);
                        }
                    });
                    i.replaceUseWith(v);
                    i.removeSelfAndDef();
                    eliminated = true;
                }
            }
        }
    }
}
