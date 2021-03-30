package ir.values;

import ir.Value;
import ir.Type;

abstract public class Constant extends Value {
    public int num;

    protected Constant(Type type) {
        super(type);
    }
}
