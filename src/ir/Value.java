package ir;

import asm.operand.Register;
import util.IRCloner;

import java.util.HashSet;

abstract public class Value {
    protected Type type;
    public Register asmReg;

    public HashSet<Inst> use = new HashSet<>();

    public Value(Type type) {
        this.type = type;
    }

    public abstract HashSet<Value> getDef();

    public HashSet<Inst> getUse() {
        return use;
    }

    public void addUse(Inst u) {
        use.add(u);
    }

    public boolean removeUse(Inst u) {
        return use.remove(u);
    }

    public void replaceUseWith(Value n) {
        for (Inst u : use) {
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

    abstract public boolean sameMeaning(Value other);

    public abstract void getClone(IRCloner c);
}
