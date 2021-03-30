package ir;

import asm.operand.Register;

abstract public class Value {
    protected Type type;
    public Register asmReg;

    public Value(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Module getContext() {
        return type.m;
    }

    abstract public String getName();
}
