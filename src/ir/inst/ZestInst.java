package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Value;

public class ZestInst extends Inst {
    Value val;

    public ZestInst(Value val, BasicBlock basicBlock) {
        super(val.getType(), basicBlock);
        this.val = val;
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
