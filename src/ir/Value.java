package ir;

import asm.operand.Register;

import java.util.HashSet;

abstract public class Value {
    protected Type type;
    public Register asmReg;

    public HashSet<Value> use = new HashSet<>();

    public Value(Type type) {
        this.type = type;
    }

    public abstract void addUse(Value u);

    public abstract boolean removeUse(Value u);

    public void removeSelfAndUse() {
    }

    public abstract void replaceUseWith(Value n);

    public abstract void replaceUse(Value o, Value n);

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Module getContext() {
        return type.m;
    }

    abstract public String getName();
}
