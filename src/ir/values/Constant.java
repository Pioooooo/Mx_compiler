package ir.values;

import ir.Type;
import ir.Value;

abstract public class Constant extends Value {
    public int num;

    protected Constant(Type type) {
        super(type);
    }
}
