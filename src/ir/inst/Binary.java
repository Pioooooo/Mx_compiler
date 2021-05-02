package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Value;
import ir.values.ConstantInt;
import util.IRCloner;

import java.util.HashSet;

import static util.Math.log2;
import static util.Math.powOf2;

public class Binary extends Inst {
    public enum OpType {
        mul, sdiv, srem, shl, ashr, and, or, xor, sub, add;

        public boolean commutable() {
            return switch (this) {
                case mul, and, or, xor, add -> true;
                default -> false;
            };
        }
    }

    public OpType op;
    public Value lhs, rhs;

    Binary(Value lhs, Value rhs, OpType op, BasicBlock basicBlock, Inst inst) {
        super(lhs.getType(), basicBlock, inst);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        lhs.addUse(this);
        rhs.addUse(this);
    }

    Binary(Value lhs, Value rhs, OpType op, BasicBlock basicBlock) {
        super(lhs.getType(), basicBlock);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        lhs.addUse(this);
        rhs.addUse(this);
    }

    Binary(Value lhs, Value rhs, OpType op, Inst inst) {
        super(lhs.getType(), inst);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        lhs.addUse(this);
        rhs.addUse(this);
    }

    public static Binary create(Value lhs, Value rhs, OpType op, BasicBlock basicBlock, Inst inst) {
        return new Binary(lhs, rhs, op, basicBlock, inst);
    }

    public static Binary create(Value lhs, Value rhs, OpType op, BasicBlock basicBlock) {
        return new Binary(lhs, rhs, op, basicBlock);
    }

    public static Binary create(Value lhs, Value rhs, OpType op, Inst inst) {
        return new Binary(lhs, rhs, op, inst);
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
    public boolean sameMeaning(Value other) {
        if (!(other instanceof Binary)) {
            return false;
        }
        return switch (op) {
            case mul, and, or, xor, add -> op == ((Binary) other).op &&
                    (lhs == ((Binary) other).lhs && rhs == ((Binary) other).rhs || lhs == ((Binary) other).rhs && rhs == ((Binary) other).lhs);
            case sdiv, srem, shl, ashr, sub -> op == ((Binary) other).op && lhs == ((Binary) other).lhs && rhs == ((Binary) other).rhs;
        };
    }

    @Override
    public Value simplify() {
        if (lhs instanceof ConstantInt && rhs instanceof ConstantInt) {
            if ((op == OpType.sdiv || op == OpType.srem) && ((ConstantInt) rhs).val == 0) {
                return null;
            }
            int val = switch (op) {
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
        }
        return algebraicSimplify();
    }

    Inst algebraicSimplify() {
        Binary binary;
        Value src;
        int a, b;
        if (lhs instanceof ConstantInt && rhs instanceof Binary && op.commutable()) {
            binary = (Binary) rhs;
            b = ((ConstantInt) lhs).val;
        } else if (lhs instanceof Binary && rhs instanceof ConstantInt) {
            binary = (Binary) lhs;
            b = ((ConstantInt) rhs).val;
        } else {
            return null;
        }
        if (binary.lhs instanceof ConstantInt && !(binary.rhs instanceof ConstantInt) && binary.op.commutable()) {
            a = ((ConstantInt) binary.lhs).val;
            src = binary.rhs;
        } else if (!(binary.lhs instanceof ConstantInt) && binary.rhs instanceof ConstantInt) {
            a = ((ConstantInt) binary.rhs).val;
            src = binary.lhs;
        } else {
            return null;
        }
        if (op == OpType.add) {
            if (binary.op == OpType.add) {
                return Binary.create(src, ConstantInt.get(getContext(), 32, a + b), OpType.add, getParent(), this);
            } else if (binary.op == OpType.sub) {
                return Binary.create(src, ConstantInt.get(getContext(), 32, b - a), OpType.add, getParent(), this);
            }
        } else if (op == OpType.sub) {
            if (binary.op == OpType.add) {
                return Binary.create(src, ConstantInt.get(getContext(), 32, a - b), OpType.add, getParent(), this);
            } else if (binary.op == OpType.sub) {
                return Binary.create(src, ConstantInt.get(getContext(), 32, -a - b), OpType.add, getParent(), this);
            }
        } else if (op == OpType.mul) {
            if (binary.op == OpType.mul) {
                return Binary.create(src, ConstantInt.get(getContext(), 32, a * b), OpType.mul, getParent(), this);
            } else if (binary.op == OpType.sdiv) {
                if (a % b == 0) {
                    return Binary.create(src, ConstantInt.get(getContext(), 32, a / b), OpType.sdiv, getParent(), this);
                } else if (b % a == 0) {
                    return Binary.create(src, ConstantInt.get(getContext(), 32, b / a), OpType.mul, getParent(), this);
                }
            }
        } else if (op == OpType.sdiv) {
            if (binary.op == OpType.mul) {
                if (a % b == 0) {
                    return Binary.create(src, ConstantInt.get(getContext(), 32, a / b), OpType.mul, getParent(), this);
                } else if (b % a == 0) {
                    return Binary.create(src, ConstantInt.get(getContext(), 32, b / a), OpType.sdiv, getParent(), this);
                }
            } else if (binary.op == OpType.sdiv) {
                return Binary.create(src, ConstantInt.get(getContext(), 32, a * b), OpType.sdiv, getParent(), this);
            }
        } else if (op == OpType.and && binary.op == OpType.and) {
            return Binary.create(src, ConstantInt.get(getContext(), 32, a & b), OpType.and, getParent(), this);
        } else if (op == OpType.or && binary.op == OpType.or) {
            return Binary.create(src, ConstantInt.get(getContext(), 32, a | b), OpType.or, getParent(), this);
        } else if (op == OpType.or && binary.op == OpType.xor) {
            return Binary.create(src, ConstantInt.get(getContext(), 32, a ^ b), OpType.xor, getParent(), this);
        } else if (op == OpType.shl && binary.op == OpType.shl) {
            return Binary.create(src, ConstantInt.get(getContext(), 32, a + b), OpType.shl, getParent(), this);
        } else if (op == OpType.ashr && binary.op == OpType.ashr) {
            return Binary.create(src, ConstantInt.get(getContext(), 32, a + b), OpType.ashr, getParent(), this);
        }
        return strengthReduction();
    }

    Inst strengthReduction() {
        Value src;
        int val;
        if (lhs instanceof ConstantInt && op.commutable()) {
            src = rhs;
            val = ((ConstantInt) lhs).val;
        } else if (rhs instanceof ConstantInt) {
            src = lhs;
            val = ((ConstantInt) rhs).val;
        } else {
            return null;
        }
        if (powOf2(val)) {
            if (op == OpType.mul) {
                return Binary.create(src, ConstantInt.get(getContext(), 32, log2(val)), OpType.shl, this);
            } else if (op == OpType.sdiv) {
                return Binary.create(src, ConstantInt.get(getContext(), 32, log2(val)), OpType.ashr, this);
            } else if (op == OpType.srem) {
                return Binary.create(src, ConstantInt.get(getContext(), 32, val - 1), OpType.and, this);
            }
        }
        return null;
    }

    @Override
    public void getClone(IRCloner c) {
        if (c.getClone(this) != null) {
            return;
        }
        super.getClone(c);
        c.setClone(this, create(c.getClone(lhs), c.getClone(rhs), op, c.getClone(getParent()), null));
    }

    @Override
    public boolean hasRet() {
        return true;
    }

    @Override
    public String getFullInst() {
        return this + " = " + op + " " + type + " " + lhs + ", " + rhs;
    }
}
