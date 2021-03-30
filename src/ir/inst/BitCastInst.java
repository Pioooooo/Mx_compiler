package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;

public class BitCastInst extends Inst {
    public Value val;

    BitCastInst(Value val, Type type, BasicBlock basicBlock, Inst inst) {
        super(type, basicBlock, inst);
        this.val = val;
    }

    BitCastInst(Value val, Type type, BasicBlock basicBlock) {
        super(type, basicBlock);
        this.val = val;
    }

    BitCastInst(Value val, Type type, Inst inst) {
        super(type, inst);
        this.val = val;
    }

    public static BitCastInst create(Value val, Type type, BasicBlock basicBlock, Inst inst) {
        return new BitCastInst(val, type, basicBlock, inst);
    }

    public static BitCastInst create(Value val, Type type, BasicBlock basicBlock) {
        return new BitCastInst(val, type, basicBlock);
    }

    public static BitCastInst create(Value val, Type type, Inst inst) {
        return new BitCastInst(val, type, inst);
    }

    @Override
    public boolean hasRet() {
        return true;
    }

    @Override
    public String getFullInst() {
        return this + " = bitcast " + val.getType() + " " + val + " to " + type;
    }
}
