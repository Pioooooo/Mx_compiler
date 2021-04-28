package transforms;

import ir.Function;
import ir.Inst;
import ir.Module;
import util.error.InternalError;

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
//        HashSet<Inst> use = new HashSet<>(), removed = new HashSet<>();
        f.basicBlockList.forEach(b -> b.forEach(i -> {
            if (!live.contains(i)) {
//                use.addAll(i.use);
                try {
                    i.removeSelfAndDef();
                } catch (InternalError ignored) {
                }
//                removed.add(i);
            }
        }));
//        use.removeAll(removed);
//        if (!use.isEmpty()) {
//            throw new InternalError("remove used inst");
//        }
    }
}
