package ir.values;

import ir.Type;
import ir.Value;

abstract public class Constant extends Value {
    public int num;

    protected Constant(Type type) {
        super(type);
    }

    @Override
    public void addUse(Value u) {
    }

    @Override
    public boolean removeUse(Value u) {
        return false;
    }

    @Override
    public void replaceUseWith(Value n) {
    }

    @Override
    public void replaceUse(Value o, Value n) {
    }
}
