package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Value;
import util.IRCloner;

import java.util.HashSet;

public class LoadInst extends Inst {
    public Value ptr;

    LoadInst(Value ptr, BasicBlock basicBlock, Inst inst) {
        super(ptr.getType().getBaseType(), basicBlock, inst);
        this.ptr = ptr;
        ptr.addUse(this);
    }

    LoadInst(Value ptr, BasicBlock basicBlock) {
        super(ptr.getType().getBaseType(), basicBlock);
        this.ptr = ptr;
        ptr.addUse(this);
    }

    LoadInst(Value ptr, Inst inst) {
        super(ptr.getType().getBaseType(), inst);
        this.ptr = ptr;
        ptr.addUse(this);
    }

    public static LoadInst create(Value ptr, BasicBlock basicBlock, Inst inst) {
        return new LoadInst(ptr, basicBlock, inst);
    }

    public static LoadInst create(Value ptr, BasicBlock basicBlock) {
        return new LoadInst(ptr, basicBlock);
    }

    public static LoadInst create(Value ptr, Inst inst) {
        return new LoadInst(ptr, inst);
    }

    @Override
    public HashSet<Value> getDef() {
        var def = new HashSet<Value>();
        def.add(ptr);
        return def;
    }

    @Override
    public void replaceUse(Value o, Value n) {
        if (ptr == o) {
            ptr = n;
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
    public void getClone(IRCloner c) {
        if (c.getClone(this) != null) {
            return;
        }
        super.getClone(c);
        c.setClone(this, create(c.getClone(ptr), c.getClone(getParent()), null));
    }

    @Override
    public boolean hasRet() {
        return true;
    }

    @Override
    public String getFullInst() {
        return this + " = load " + type + ", " + ptr.getType() + " " + ptr + ", align " + type.size() / 8;
    }
}
