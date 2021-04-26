package transforms;

import ir.Function;
import ir.Inst;
import ir.Module;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class ADCE {
    Module m;

    public ADCE(Module m) {
        this.m = m;
    }

    public void run() {
        m.functions.values().forEach(this::run);
    }

    void run(Function f) {
        HashSet<Inst> live = new HashSet<>();
        Queue<Inst> worklist = new LinkedList<>();
        f.basicBlockList.forEach(b -> b.forEach(i -> {
            if (!i.noSideEffect()) {
                live.add(i);
                worklist.add(i);
            }
        }));
        while (!worklist.isEmpty()) {
            Inst i = worklist.poll();
            i.getDef().forEach(v -> {
                if (v instanceof Inst && !live.contains(v)) {
                    live.add((Inst) v);
                    worklist.add((Inst) v);
                }
            });
        }
        f.basicBlockList.forEach(b -> b.forEach(i -> {
            if (!live.contains(i)) {
                i.removeSelfAndDef();
            }
        }));
    }
}
