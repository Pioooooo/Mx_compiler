package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Value;

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
        lhs.addUse(this);
        rhs.addUse(this);
    }

    Binary(Value lhs, Value rhs, OpType opType, BasicBlock basicBlock) {
        super(lhs.getType(), basicBlock);
        this.opType = opType;
        this.lhs = lhs;
        this.rhs = rhs;
        lhs.addUse(this);
        rhs.addUse(this);
    }

    Binary(Value lhs, Value rhs, OpType opType, Inst inst) {
        super(lhs.getType(), inst);
        this.opType = opType;
        this.lhs = lhs;
        this.rhs = rhs;
        lhs.addUse(this);
        rhs.addUse(this);
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
    public void replaceUse(Value o, Value n) {
        if (lhs == o) {
            lhs = n;
        }
        if (rhs == o) {
            rhs = n;
        }
    }

    @Override
    public Value simplify() {
        return null;
    }

    @Override
    public void removeSelfAndUse() {
        lhs.removeUse(this);
        rhs.removeUse(this);
        removeSelf();
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
