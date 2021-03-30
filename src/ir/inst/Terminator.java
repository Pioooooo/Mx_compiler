package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import util.error.InternalError;

abstract public class Terminator extends Inst {
    public Terminator(Type type, BasicBlock basicBlock, Inst inst) {
        super(type, basicBlock, inst);
        if (basicBlock.isTerminated()) {
            throw new InternalError("calling terminate() on terminated Block");
        } else {
            basicBlock.terminate();
        }
    }

    public Terminator(Type type, BasicBlock basicBlock) {
        super(type, basicBlock);
    }

    public Terminator(Type type, Inst inst) {
        super(type, inst);
    }
}
