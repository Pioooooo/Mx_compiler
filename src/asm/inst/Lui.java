package asm.inst;

import asm.AsmBlock;
import asm.AsmInst;
import asm.operand.Immediate;
import asm.operand.Register;

import java.util.Collections;
import java.util.HashSet;

public class Lui extends AsmInst {
    public Register reg;
    public Immediate imm;

    Lui(Register reg, Immediate imm, AsmBlock block) {
        super(block);
        this.reg = reg;
        this.imm = imm;
    }

    public static Lui create(Register reg, Immediate imm, AsmBlock block) {
        return new Lui(reg, imm, block);
    }

    @Override
    public HashSet<Register> getDef() {
        return new HashSet<>(Collections.singletonList(reg));
    }

    @Override
    public void replaceDef(Register a, Register b) {
        if (reg == a) {
            reg = b;
        }
    }

    @Override
    public String toString() {
        return "lui " + reg + ", " + imm;
    }
}
