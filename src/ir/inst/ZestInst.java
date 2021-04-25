package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Value;

import java.util.HashSet;

public class ZestInst extends Inst {
    Value val;

    public ZestInst(Value val, BasicBlock basicBlock) {
        super(val.getType(), basicBlock);
        this.val = val;
        val.addUse(this);
    }

    @Override
    public void replaceUse(Value o, Value n) {
        if (val == o) {
            val = n;
        }
    }

    @Override
    public boolean sameMeaning(Value other) {
        return false;
    }

    @Override
    public Value simplify() {
        return null;
    }

    @Override
    public HashSet<Value> getDef() {
        var def = new HashSet<Value>();
        def.add(val);
        return def;
    }

    @Override
    public boolean hasRet() {
        return true;
    }

    @Override
    public String getFullInst() {
        return this + " = zext " + val.getType() + " " + val + "to " + type;
    }
}
