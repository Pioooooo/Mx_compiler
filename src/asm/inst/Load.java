package asm.inst;

import asm.AsmBlock;
import asm.AsmInst;
import asm.operand.Immediate;
import asm.operand.Register;

import java.util.Collections;
import java.util.HashSet;

public class Load extends AsmInst {
    public Register reg, address;
    public Immediate offset;
    public int size;

    Load(Register reg, Register address, Immediate offset, int size, AsmBlock block) {
        super(block);
        this.reg = reg;
        this.address = address;
        this.offset = offset;
        this.size = size;
    }

    Load(Register reg, Register address, Immediate offset, int size, AsmInst inst) {
        super(inst);
        this.reg = reg;
        this.address = address;
        this.offset = offset;
        this.size = size;
    }

    public static Load createW(Register reg, Register address, Immediate offset, AsmBlock block) {
        return new Load(reg, address, offset, 4, block);
    }

    public static Load createW(Register reg, Register address, Immediate offset, AsmInst inst) {
        return new Load(reg, address, offset, 4, inst);
    }

    @Override
    public HashSet<Register> getUse() {
        return new HashSet<>(Collections.singletonList(address));
    }

    @Override
    public HashSet<Register> getDef() {
        return new HashSet<>(Collections.singletonList(reg));
    }

    @Override
    public void replaceUse(Register a, Register b) {
        if (address == a) {
            address = b;
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
        return "l" + ((size == 1) ? "b" : ((size == 4) ? "w" : "h")) + " " + reg + ", " + offset + "(" + address + ")";
    }
}
