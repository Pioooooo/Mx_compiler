package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;

public class RetInst extends Terminator {
    public Value val;

    RetInst(Value val, BasicBlock basicBlock, Inst inst) {
        super(Type.getVoidTy(basicBlock.getContext()), basicBlock, inst);
        this.val = val;
        if (val != null) {
            val.addUse(this);
        }
    }

    RetInst(Value val, BasicBlock basicBlock) {
        super(Type.getVoidTy(basicBlock.getContext()), basicBlock);
        this.val = val;
        if (val != null) {
            val.addUse(this);
        }
    }

    RetInst(Value val, Inst inst) {
        super(Type.getVoidTy(inst.getContext()), inst);
        this.val = val;
        if (val != null) {
            val.addUse(this);
        }
    }

    public static RetInst create(Value val, BasicBlock basicBlock, Inst inst) {
        return new RetInst(val, basicBlock, inst);
    }

    public static RetInst create(Value val, BasicBlock basicBlock) {
        return new RetInst(val, basicBlock);
    }

    public static RetInst create(Value val, Inst inst) {
        return new RetInst(val, inst);
    }

    @Override
    public void replaceUse(Value o, Value n) {
        if (val == o) {
            val = n;
        }
    }

    @Override
    public Value simplify() {
        return null;
    }

    @Override
    public void removeSelfAndUse() {
        if (val != null) {
            val.removeUse(this);
        }
        removeSelf();
    }

    @Override
    public String getFullInst() {
        return "ret " + (val == null ? "void" : val.getType() + " " + val);
    }
}
