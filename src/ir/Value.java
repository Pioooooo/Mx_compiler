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

    public abstract HashSet<Value> getDef();

    public HashSet<Value> getUse() {
        return use;
    }

    public void addUse(Value u) {
        use.add(u);
    }

    public boolean removeUse(Value u) {
        return use.remove(u);
    }

    public void removeSelfAndDef() {
        getDef().forEach(v -> v.removeUse(this));
    }

    public void replaceUseWith(Value n) {
        for (Value u : use) {
            u.replaceUse(this, n);
            n.addUse(u);
        }
        use.clear();
    }

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
