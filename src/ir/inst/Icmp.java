package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;

public class Icmp extends Inst {
    public enum OpType {
        slt, sge, sle, sgt, eq, ne
    }

    public OpType op;
    public Value lhs, rhs;

    Icmp(Value lhs, Value rhs, OpType op, BasicBlock basicBlock, Inst inst) {
        super(Type.getIntNTy(lhs.getContext(), 1), basicBlock, inst);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        lhs.addUse(this);
        rhs.addUse(this);
    }

    Icmp(Value lhs, Value rhs, OpType op, BasicBlock basicBlock) {
        super(Type.getIntNTy(lhs.getContext(), 1), basicBlock);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        lhs.addUse(this);
        rhs.addUse(this);
    }

    Icmp(Value lhs, Value rhs, OpType op, Inst inst) {
        super(Type.getIntNTy(lhs.getContext(), 1), inst);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        lhs.addUse(this);
        rhs.addUse(this);
    }

    public static Icmp create(Value lhs, Value rhs, OpType opType, BasicBlock basicBlock, Inst inst) {
        return new Icmp(lhs, rhs, opType, basicBlock, inst);
    }

    public static Icmp create(Value lhs, Value rhs, OpType opType, BasicBlock basicBlock) {
        return new Icmp(lhs, rhs, opType, basicBlock);
    }

    public static Icmp create(Value lhs, Value rhs, OpType opType, Inst inst) {
        return new Icmp(lhs, rhs, opType, inst);
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
    public boolean hasRet() {
        return true;
    }

    @Override
    public String getFullInst() {
        return this + " = icmp " + op + " " + lhs.getType() + " " + lhs + ", " + rhs;
    }
}
