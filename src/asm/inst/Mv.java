package asm.inst;

import asm.AsmBlock;
import asm.AsmInst;
import asm.operand.Register;

import java.util.Collections;
import java.util.HashSet;

public class Mv extends AsmInst {
    public Register reg, src;

    Mv(Register reg, Register src, AsmBlock block) {
        super(block);
        this.reg = reg;
        this.src = src;
    }

    Mv(Register reg, Register src, AsmInst inst) {
        super(inst);
        this.reg = reg;
        this.src = src;
    }

    public static Mv create(Register reg, Register src, AsmBlock block) {
        return new Mv(reg, src, block);
    }

    public static Mv create(Register reg, Register src, AsmInst inst) {
        return new Mv(reg, src, inst);
    }

    @Override
    public HashSet<Register> getUse() {
        return new HashSet<>(Collections.singletonList(src));
    }

    @Override
    public HashSet<Register> getDef() {
        return new HashSet<>(Collections.singletonList(reg));
    }

    @Override
    public void replaceUse(Register a, Register b) {
        if (src == a) {
            src = b;
        }
    }

    @Override
    public void replaceDef(Register a, Register b) {
        if (reg == a) {
            reg = b;
        }
    }

    @Override
    public String toString() {
        return "mv " + reg + ", " + src;
    }
}
