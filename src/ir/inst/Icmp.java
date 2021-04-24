package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;
import ir.values.ConstantInt;

import java.util.HashSet;

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

    public static Icmp create(Value lhs, Value rhs, OpType op, BasicBlock basicBlock, Inst inst) {
        return new Icmp(lhs, rhs, op, basicBlock, inst);
    }

    public static Icmp create(Value lhs, Value rhs, OpType op, BasicBlock basicBlock) {
        return new Icmp(lhs, rhs, op, basicBlock);
    }

    public static Icmp create(Value lhs, Value rhs, OpType op, Inst inst) {
        return new Icmp(lhs, rhs, op, inst);
    }

    @Override
    public HashSet<Value> getDef() {
        var def = new HashSet<Value>();
        def.add(lhs);
        def.add(rhs);
        return def;
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
        if (lhs instanceof ConstantInt && rhs instanceof ConstantInt) {
            boolean val = switch (op) {
                case slt -> ((ConstantInt) lhs).val < ((ConstantInt) rhs).val;
                case sge -> ((ConstantInt) lhs).val >= ((ConstantInt) rhs).val;
                case sle -> ((ConstantInt) lhs).val <= ((ConstantInt) rhs).val;
                case sgt -> ((ConstantInt) lhs).val > ((ConstantInt) rhs).val;
                case eq -> ((ConstantInt) lhs).val == ((ConstantInt) rhs).val;
                case ne -> ((ConstantInt) lhs).val != ((ConstantInt) rhs).val;
            };
            return ConstantInt.getBool(getContext(), 1, val);
        } else {
            return null;
        }
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
