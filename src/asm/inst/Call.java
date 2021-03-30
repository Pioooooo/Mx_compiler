package asm.inst;

import asm.AsmBlock;
import asm.AsmFunction;
import asm.AsmInst;
import asm.operand.Register;

import java.util.HashSet;

public class Call extends AsmInst {
    public AsmFunction function;

    Call(AsmFunction function, AsmBlock block) {
        super(block);
        this.function = function;
    }

    public static Call create(AsmFunction function, AsmBlock block) {
        return new Call(function, block);
    }

    @Override
    public HashSet<Register> getUse() {
        HashSet<Register> ret = new HashSet<>();
        for (int i = 0; i < Math.min(function.args.size(), 8); i++) {
            ret.add(function.getParent().getPReg("a" + i));
        }
        return ret;
    }

    @Override
    public HashSet<Register> getDef() {
        return new HashSet<>(function.getParent().getCallerSave());
    }

    @Override
    public String toString() {
        return "call " + function;
    }
}
