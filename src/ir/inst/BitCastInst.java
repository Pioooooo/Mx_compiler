package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;
import util.IRCloner;

import java.util.HashSet;

public class BitCastInst extends Inst {
    public Value val;

    BitCastInst(Value val, Type type, BasicBlock basicBlock, Inst inst) {
        super(type, basicBlock, inst);
        this.val = val;
        val.addUse(this);
    }

    BitCastInst(Value val, Type type, BasicBlock basicBlock) {
        super(type, basicBlock);
        this.val = val;
        val.addUse(this);
    }

    BitCastInst(Value val, Type type, Inst inst) {
        super(type, inst);
        this.val = val;
        val.addUse(this);
    }

    public static BitCastInst create(Value val, Type type, BasicBlock basicBlock, Inst inst) {
        return new BitCastInst(val, type, basicBlock, inst);
    }

    public static BitCastInst create(Value val, Type type, BasicBlock basicBlock) {
        return new BitCastInst(val, type, basicBlock);
    }

    public static BitCastInst create(Value val, Type type, Inst inst) {
        return new BitCastInst(val, type, inst);
    }

    @Override
    public HashSet<Value> getDef() {
        var def = new HashSet<Value>();
        def.add(val);
        return def;
    }

    @Override
    public void replaceUse(Value o, Value n) {
        if (val == o) {
            val = n;
        }
    }

    @Override
    public boolean sameMeaning(Value other) {
        return other instanceof BitCastInst && val == ((BitCastInst) other).val && type == other.getType();
    }

    @Override
    public Value simplify() {
        return null;
    }

    @Override
    public void getClone(IRCloner c) {
        if (c.getClone(this) != null) {
            return;
        }
        super.getClone(c);
        c.setClone(this, create(c.getClone(val), type, getParent(), null));
    }

    @Override
    public boolean hasRet() {
        return true;
    }

    @Override
    public String getFullInst() {
        return this + " = bitcast " + val.getType() + " " + val + " to " + type;
    }
}
