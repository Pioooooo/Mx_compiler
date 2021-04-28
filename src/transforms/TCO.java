package transforms;

import ir.BasicBlock;
import ir.Function;
import ir.Module;
import ir.inst.BrInst;
import ir.inst.CallInst;
import ir.inst.PhiInst;
import ir.inst.RetInst;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TCO {
    Module m;

    public TCO(Module m) {
        this.m = m;
    }

    public void run() {
        m.functions.values().forEach(this::run);
    }

    void run(Function f) {
        ArrayList<CallInst> tailCalls = f.calls.stream()
                .filter(c -> c.function == f && c.getNext().get() instanceof RetInst
                        && (f.getRetType().isVoid() || ((RetInst) c.getNext().get()).val == c))
                .collect(Collectors.toCollection(ArrayList::new));
        if (tailCalls.isEmpty()) {
            return;
        }
        BasicBlock entry = f.getHead().get(), newEntry = BasicBlock.create(entry.loopDepth, m, f, entry);
        f.getArgs().forEach(a -> {
            var phi = PhiInst.create(a.getType(), entry);
            a.replaceUseWith(phi);
            tailCalls.forEach(c -> phi.addIncoming(c.getParent(), c.args.get(a.num)));
            phi.addIncoming(newEntry, a);
        });
        BrInst.create(entry, newEntry, null);
        tailCalls.forEach(c -> {
            c.getNext().get().removeSelfAndDef();
            BrInst.create(entry, c.getParent(), null);
            c.removeSelfAndDef();
        });
    }
}
