package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Value;

public class LoadInst extends Inst {
    public Value ptr;

    LoadInst(Value ptr, BasicBlock basicBlock, Inst inst) {
        super(ptr.getType().getBaseType(), basicBlock, inst);
        this.ptr = ptr;
    }

    LoadInst(Value ptr, BasicBlock basicBlock) {
        super(ptr.getType().getBaseType(), basicBlock);
        this.ptr = ptr;
    }

    LoadInst(Value ptr, Inst inst) {
        super(ptr.getType().getBaseType(), inst);
        this.ptr = ptr;
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
    public boolean hasRet() {
        return true;
    }

    @Override
    public String getFullInst() {
        return this + " = load " + type + ", " + ptr.getType() + " " + ptr + ", align " + type.size() / 8;
    }
}
