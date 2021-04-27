package ir.values;

import ir.Type;
import ir.Value;
import util.IRCloner;

import java.util.HashSet;

abstract public class Constant extends Value {
    public int num;

    protected Constant(Type type) {
        super(type);
    }

    @Override
    public void replaceUse(Value o, Value n) {
    }

    @Override
    public HashSet<Value> getDef() {
        return new HashSet<>();
    }

    @Override
    public boolean sameMeaning(Value other) {
        return false;
    }

    @Override
    public void getClone(IRCloner c) {
    }
}
