package asm.operand;

import asm.Operand;

public class Immediate extends Operand {
    public int val;
    public boolean inParam = false;

    Immediate(int val, boolean inParam) {
        this.val = val;
        this.inParam = inParam;
    }

    Immediate(int val) {
        this.val = val;
    }

    public static Immediate create(int val, boolean inParam) {
        return new Immediate(val, inParam);
    }

    public static Immediate create(int val) {
        return new Immediate(val);
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
