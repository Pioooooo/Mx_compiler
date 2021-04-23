package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Value;

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

    public static LoadInst create(Value pointer, BasicBlock basicBlock, Inst inst) {
        return new LoadInst(pointer, basicBlock, inst);
    }

    public static LoadInst create(Value pointer, BasicBlock basicBlock) {
        return new LoadInst(pointer, basicBlock);
    }

    public static LoadInst create(Value pointer, Inst inst) {
        return new LoadInst(pointer, inst);
    }

    @Override
    public void replaceUse(Value o, Value n) {
        if (ptr == o) {
            ptr = n;
        }
    }

    @Override
    public Value simplify() {
        return null;
    }

    @Override
    public void removeSelfAndUse() {
        ptr.removeUse(this);
        removeSelf();
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
