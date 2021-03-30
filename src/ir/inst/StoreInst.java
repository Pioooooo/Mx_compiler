package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;

public class StoreInst extends Inst {
    public Value val, ptr;

    StoreInst(Value val, Value ptr, BasicBlock basicBlock, Inst inst) {
        super(Type.getVoidTy(val.getContext()), basicBlock, inst);
        this.val = val;
        this.ptr = ptr;
    }

    StoreInst(Value val, Value ptr, BasicBlock basicBlock) {
        super(Type.getVoidTy(val.getContext()), basicBlock);
        this.val = val;
        this.ptr = ptr;
    }

    StoreInst(Value val, Value ptr, Inst inst) {
        super(Type.getVoidTy(val.getContext()), inst);
        this.val = val;
        this.ptr = ptr;
    }

    public static StoreInst create(Value val, Value ptr, BasicBlock basicBlock, Inst inst) {
        return new StoreInst(val, ptr, basicBlock, inst);
    }

    public static StoreInst create(Value val, Value ptr, BasicBlock basicBlock) {
        return new StoreInst(val, ptr, basicBlock);
    }

    public static StoreInst create(Value val, Value ptr, Inst inst) {
        return new StoreInst(val, ptr, inst);
    }

    @Override
    public String getFullInst() {
        return "store " + val.getType() + " " + val + ", " + ptr.getType() + " " + ptr + ", align " + val.getType().size() / 8;
    }
}
