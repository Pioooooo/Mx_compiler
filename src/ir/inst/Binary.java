package ir.inst;

import ir.*;

public class Binary extends Inst {
    public enum OpType {
        mul, sdiv, srem, shl, ashr, and, or, xor, sub, add
    }

    public OpType opType;
    public Value lhs, rhs;

    Binary(Value lhs, Value rhs, OpType opType, BasicBlock basicBlock, Inst inst) {
        super(lhs.getType(), basicBlock, inst);
        this.opType = opType;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    Binary(Value lhs, Value rhs, OpType opType, BasicBlock basicBlock) {
        super(lhs.getType(), basicBlock);
        this.opType = opType;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    Binary(Value lhs, Value rhs, OpType opType, Inst inst) {
        super(lhs.getType(), inst);
        this.opType = opType;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public static Binary create(Value lhs, Value rhs, OpType opType, BasicBlock basicBlock, Inst inst) {
        return new Binary(lhs, rhs, opType, basicBlock, inst);
    }

    public static Binary create(Value lhs, Value rhs, OpType opType, BasicBlock basicBlock) {
        return new Binary(lhs, rhs, opType, basicBlock);
    }

    public static Binary create(Value lhs, Value rhs, OpType opType, Inst inst) {
        return new Binary(lhs, rhs, opType, inst);
    }

    @Override
    public boolean hasRet() {
        return true;
    }

    @Override
    public String getFullInst() {
        return this + " = " + opType + " " + type + " " + lhs + ", " + rhs;
    }
}
