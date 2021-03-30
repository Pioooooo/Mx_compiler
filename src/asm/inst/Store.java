package asm.inst;

import asm.AsmBlock;
import asm.AsmInst;
import asm.operand.Immediate;
import asm.operand.Register;

import java.util.Arrays;
import java.util.HashSet;

public class Store extends AsmInst {
    public Register value, address;
    public Immediate offset;
    public int size;

    Store(Register value, Register address, Immediate offset, int size, AsmBlock block) {
        super(block);
        this.value = value;
        this.address = address;
        this.offset = offset;
        this.size = size;
    }

    Store(Register value, Register address, Immediate offset, int size, AsmInst inst) {
        super(inst);
        this.value = value;
        this.address = address;
        this.offset = offset;
        this.size = size;
    }

    public static Store createW(Register value, Register address, Immediate offset, AsmBlock block) {
        return new Store(value, address, offset, 4, block);
    }

    public static Store createW(Register value, Register address, Immediate offset, AsmInst inst) {
        return new Store(value, address, offset, 4, inst);
    }

    @Override
    public HashSet<Register> getUse() {
        return new HashSet<>(Arrays.asList(value, address));
    }

    @Override
    public void replaceUse(Register a, Register b) {
        if (value == a) {
            value = b;
        }
        if (address == a) {
            address = b;
        }
    }

    @Override
    public String toString() {
        return "s" + ((size == 1) ? "b" : ((size == 4) ? "w" : "h")) + " " + value + ", " + offset + "(" + address + ")";
    }
}
