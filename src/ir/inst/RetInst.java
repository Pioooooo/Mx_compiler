package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;

public class RetInst extends Terminator {
    public Value val;

    RetInst(Value val, BasicBlock basicBlock, Inst inst) {
        super(val == null ? Type.getVoidTy(basicBlock.getContext()) : val.getType(), basicBlock, inst);
        this.val = val;
    }

    RetInst(Value val, BasicBlock basicBlock) {
        super(val == null ? Type.getVoidTy(basicBlock.getContext()) : val.getType(), basicBlock);
        this.val = val;
    }

    RetInst(Value val, Inst inst) {
        super(val == null ? Type.getVoidTy(inst.getContext()) : val.getType(), inst);
        this.val = val;
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
    public String getFullInst() {
        return "ret " + (val == null ? "void" : val.getType() + " " + val);
    }
}
