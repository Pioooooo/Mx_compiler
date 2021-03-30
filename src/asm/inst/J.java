package asm.inst;

import asm.AsmBlock;
import asm.AsmInst;

public class J extends AsmInst {
    public AsmBlock dest;

    J(AsmBlock dest, AsmBlock block) {
        super(block);
        this.dest = dest;
        block.suc.add(dest);
        dest.pre.add(block);
    }

    public static J create(AsmBlock dest, AsmBlock block) {
        return new J(dest, block);
    }

    @Override
    public String toString() {
        return "j " + dest;
    }
}
