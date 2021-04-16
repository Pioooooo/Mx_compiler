package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;

public class BrInst extends Terminator {
    public Value cond;
    public BasicBlock trueDest, falseDest;

    BrInst(Value cond, BasicBlock trueDest, BasicBlock falseDest, BasicBlock basicBlock, Inst inst) {
        super(Type.getLabelTy(trueDest.getContext()), basicBlock, inst);
        this.cond = cond;
        this.trueDest = trueDest;
        this.falseDest = falseDest;
        basicBlock.addSuccessor(trueDest);
        basicBlock.addSuccessor(falseDest);
        if (cond != null) {
            cond.addUse(this);
        }
    }

    BrInst(Value cond, BasicBlock trueDest, BasicBlock falseDest, BasicBlock basicBlock) {
        super(Type.getLabelTy(trueDest.getContext()), basicBlock);
        this.cond = cond;
        this.trueDest = trueDest;
        this.falseDest = falseDest;
        basicBlock.addSuccessor(trueDest);
        basicBlock.addSuccessor(falseDest);
        if (cond != null) {
            cond.addUse(this);
        }
    }

    BrInst(Value cond, BasicBlock trueDest, BasicBlock falseDest, Inst inst) {
        super(Type.getLabelTy(trueDest.getContext()), inst);
        this.cond = cond;
        this.trueDest = trueDest;
        this.falseDest = falseDest;
        inst.getParent().addSuccessor(trueDest);
        inst.getParent().addSuccessor(falseDest);
        if (cond != null) {
            cond.addUse(this);
        }
    }

    BrInst(BasicBlock trueDest, BasicBlock basicBlock, Inst inst) {
        this(null, trueDest, null, basicBlock, inst);
    }

    BrInst(BasicBlock trueDest, BasicBlock basicBlock) {
        this(null, trueDest, null, basicBlock);
    }

    BrInst(BasicBlock trueDest, Inst inst) {
        this(null, trueDest, null, inst);
    }

    public static BrInst create(Value cond, BasicBlock trueDest, BasicBlock falseDest, BasicBlock basicBlock, Inst inst) {
        return new BrInst(cond, trueDest, falseDest, basicBlock, inst);
    }

    public static BrInst create(Value cond, BasicBlock trueDest, BasicBlock falseDest, BasicBlock basicBlock) {
        return new BrInst(cond, trueDest, falseDest, basicBlock);
    }

    public static BrInst create(BasicBlock trueDest, BasicBlock basicBlock, Inst inst) {
        return new BrInst(trueDest, basicBlock, inst);
    }

    public static BrInst create(BasicBlock trueDest, BasicBlock basicBlock) {
        return new BrInst(trueDest, basicBlock);
    }

    public static BrInst create(Value cond, BasicBlock trueDest, BasicBlock falseDest, Inst inst) {
        return new BrInst(cond, trueDest, falseDest, inst);
    }

    public static BrInst create(BasicBlock trueDest, Inst inst) {
        return new BrInst(trueDest, inst);
    }

    @Override
    public void replaceUse(Value o, Value n) {
        if (cond == o) {
            cond = n;
        }
        if (trueDest == o) {
            trueDest = (BasicBlock) n;
        }
        if (falseDest == o) {
            falseDest = (BasicBlock) n;
        }
    }

    @Override
    public Value simplify() {
        return null;
    }

    @Override
    public String getFullInst() {
        return "br " + (cond != null ? cond.getType() + " " + cond + ", label " + trueDest + ", label " + falseDest : "label " + trueDest);
    }
}
