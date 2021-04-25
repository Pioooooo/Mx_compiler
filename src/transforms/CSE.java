package transforms;

import ir.BasicBlock;
import ir.Function;
import ir.Inst;
import ir.Module;

public class CSE {
    Module m;

    public CSE(Module m) {
        this.m = m;
    }

    public void run() {
        m.functions.values().forEach(this::run);
    }

    void run(Function f) {
        f.basicBlockList.forEach(this::run);
    }

    void run(BasicBlock b) {
        for (var i = b.getHead().get(); i.getNext() != null; i = i.getNext().get()) {
            Inst inst = i.get();
            for (var j = inst.getNext().get(); j.getNext() != null; j = j.getNext().get()) {
                if (i.sameMeaning(j)) {
                    j.replaceUseWith(i);
                }
            }
        }
    }
}
