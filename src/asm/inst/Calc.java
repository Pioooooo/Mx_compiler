package asm.inst;

import asm.AsmBlock;
import asm.AsmInst;
import asm.Operand;
import asm.operand.Register;

import java.util.Collections;
import java.util.HashSet;

public class Calc extends AsmInst {
    public enum OpType {
        add, addi, sub, slt, sltu, sltiu, xor, xori, or, ori, and, andi, sll, srl, sra, mul, div, rem
    }

    public Register rd;
    public OpType op;
    boolean isInt;
    public Operand rs1, rs2;

    Calc(Register rd, OpType op, Operand rs1, Operand rs2, boolean isInt, AsmBlock block) {
        super(block);
        this.rd = rd;
        this.op = op;
        this.rs1 = rs1;
        this.rs2 = rs2;
    }

    Calc(Register rd, OpType op, Operand rs1, Operand rs2, boolean isInt, AsmInst inst) {
        super(inst);
        this.rd = rd;
        this.op = op;
        this.rs1 = rs1;
        this.rs2 = rs2;
    }

    public static Calc createR(Register rd, OpType op, Operand rs1, Operand rs2, AsmBlock block) {
        return new Calc(rd, op, rs1, rs2, false, block);
    }

    public static Calc createI(Register rd, OpType op, Operand rs1, Operand rs2, AsmBlock block) {
        return new Calc(rd, op, rs1, rs2, true, block);
    }

    public static Calc createI(Register rd, OpType op, Operand rs1, Operand rs2, AsmInst inst) {
        return new Calc(rd, op, rs1, rs2, true, inst);
    }

    @Override
    public HashSet<Register> getUse() {
        HashSet<Register> ret = new HashSet<>();
        if (rs1 instanceof Register) {
            ret.add((Register) rs1);
        }
        if (rs2 instanceof Register) {
            ret.add((Register) rs2);
        }
        return ret;
    }

    @Override
    public HashSet<Register> getDef() {
        return new HashSet<>(Collections.singletonList(rd));
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
    public void replaceDef(Register a, Register b) {
        if (rd == a) {
            rd = b;
        }
    }

    @Override
    public String toString() {
        return op + (isInt ? "i" : "") + " " + rd + ", " + rs1 + ", " + rs2;
    }
}
