package asm.inst;

import asm.AsmBlock;
import asm.AsmInst;
import asm.operand.Register;

import java.util.Collections;
import java.util.HashSet;

public class Ret extends AsmInst {
    Ret(AsmBlock block) {
        super(block);
    }

    public static Ret create(AsmBlock block) {
        return new Ret(block);
    }

    @Override
    public HashSet<Register> getUse() {
        return new HashSet<>(Collections.singletonList(block.getParent().getParent().getPReg("ra")));
    }

    @Override
    public String toString() {
        return "ret";
    }
}
