package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Value;
import ir.values.ConstantInt;

import java.util.HashSet;

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
            if ((opType == OpType.sdiv || opType == OpType.srem) && ((ConstantInt) rhs).val == 0) {
                return null;
            }
            int val = switch (opType) {
                case mul -> ((ConstantInt) lhs).val * ((ConstantInt) rhs).val;
                case sdiv -> ((ConstantInt) lhs).val / ((ConstantInt) rhs).val;
                case srem -> ((ConstantInt) lhs).val % ((ConstantInt) rhs).val;
                case shl -> ((ConstantInt) lhs).val << ((ConstantInt) rhs).val;
                case ashr -> ((ConstantInt) lhs).val >> ((ConstantInt) rhs).val;
                case and -> ((ConstantInt) lhs).val & ((ConstantInt) rhs).val;
                case or -> ((ConstantInt) lhs).val | ((ConstantInt) rhs).val;
                case xor -> ((ConstantInt) lhs).val ^ ((ConstantInt) rhs).val;
                case sub -> ((ConstantInt) lhs).val - ((ConstantInt) rhs).val;
                case add -> ((ConstantInt) lhs).val + ((ConstantInt) rhs).val;
            };
            return ConstantInt.get(getContext(), type.size(), val);
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
        return this + " = " + opType + " " + type + " " + lhs + ", " + rhs;
    }
}
