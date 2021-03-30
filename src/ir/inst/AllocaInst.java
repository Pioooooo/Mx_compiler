package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.type.PointerType;

public class AllocaInst extends Inst {
    public int offset;

    protected AllocaInst(Type type, BasicBlock basicBlock, Inst inst) {
        super(PointerType.get(type, true), basicBlock, inst);
        basicBlock.getParent().allocas.add(this);
    }

    protected AllocaInst(Type type, BasicBlock basicBlock) {
        super(PointerType.get(type, true), basicBlock);
        basicBlock.getParent().allocas.add(this);
    }

    protected AllocaInst(Type type, Inst inst) {
        super(PointerType.get(type, true), inst);
        inst.getParent().getParent().allocas.add(this);
    }

    public static AllocaInst create(Type type, BasicBlock basicBlock, Inst inst) {
        return new AllocaInst(type, basicBlock, inst);
    }

    public static AllocaInst create(Type type, BasicBlock basicBlock) {
        return new AllocaInst(type, basicBlock);
    }

    public static AllocaInst create(Type type, int off, Inst inst) {
        return new AllocaInst(type, inst);
    }

    @Override
    public boolean hasRet() {
        return true;
    }

    @Override
    public String getFullInst() {
        return this + " = alloca " + type.getBaseType() + ", align " + type.getBaseType().size() / 8;
    }
}
