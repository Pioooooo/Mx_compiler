package asm.inst;

import asm.AsmBlock;
import asm.AsmInst;
import asm.operand.Register;

import java.util.Arrays;
import java.util.HashSet;

public class Branch extends AsmInst {
    public enum OpType {
        blt, bge, ble, bgt, beq, bne
    }

    public OpType op;
    public Register rs1, rs2;
    public AsmBlock dest;

    Branch(OpType op, Register rs1, Register rs2, AsmBlock dest, AsmBlock block) {
        super(block);
        this.op = op;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.dest = dest;
        block.suc.add(dest);
        dest.pre.add(block);
    }

    public static Branch create(OpType op, Register rs1, Register rs2, AsmBlock dest, AsmBlock block) {
        return new Branch(op, rs1, rs2, dest, block);
    }

    @Override
    public HashSet<Register> getUse() {
        return new HashSet<>(Arrays.asList(rs1, rs2));
    }

    @Override
    public void replaceUse(Register a, Register b) {
        if (rs1 == a) {
            rs1 = b;
        }
        if (rs2 == a) {
            rs2 = b;
        }
    }

    @Override
    public String toString() {
        return op + " " + rs1 + ", " + rs2 + ", " + dest;
    }
}
