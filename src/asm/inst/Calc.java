package asm.inst;

import asm.AsmBlock;
import asm.AsmInst;
import asm.Operand;
import asm.operand.Register;

import java.util.Collections;
import java.util.HashSet;

public class Calc extends AsmInst {
    public enum OpType {
        add, addi, sub, slt, sltu, sltiu, xor, xori, or, ori,
        and, andi, sll, slli, srl, srli, sra, srai, mul, div, rem
    }

    public Register rd;
    public OpType op;
    public Operand rs1, rs2;

    Calc(Register rd, OpType op, Operand rs1, Operand rs2, AsmBlock block) {
        super(block);
        this.rd = rd;
        this.op = op;
        this.rs1 = rs1;
        this.rs2 = rs2;
    }

    Calc(Register rd, OpType op, Operand rs1, Operand rs2, AsmInst inst) {
        super(inst);
        this.rd = rd;
        this.op = op;
        this.rs1 = rs1;
        this.rs2 = rs2;
    }

    public static Calc createI(Register rd, OpType op, Operand rs1, Operand rs2, AsmBlock block) {
        return new Calc(rd, op, rs1, rs2, block);
    }

    public static Calc createI(Register rd, OpType op, Operand rs1, Operand rs2, AsmInst inst) {
        return new Calc(rd, op, rs1, rs2, inst);
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
        return op + " " + rd + ", " + rs1 + ", " + rs2;
    }
}
