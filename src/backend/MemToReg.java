package backend;

import ir.Function;
import ir.Module;

public class MemToReg {
    Module m;

    public MemToReg(Module m) {
        this.m = m;
    }

    public void run() {
        m.functions.values().forEach(this::phiInsert);
    }

    void phiInsert(Function f) {

    }
}
