package ir.values;

import ir.Function;
import ir.Type;
import ir.Value;

public class Argument extends Value {
    Function parent;
    public int argNo;

    public Argument(Type type, Function parent, int argNo) {
        super(type);
        this.parent = parent;
        this.argNo = argNo;
    }

    @Override
    public String toString() {
        return "%val." + argNo;
    }

    @Override
    public String getName() {
        return "val." + argNo;
    }
}
