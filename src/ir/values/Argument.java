package ir.values;

import ir.Function;
import ir.Type;
import ir.Value;

import java.util.HashSet;

public class Argument extends Constant {
    Function parent;
    public Value ptr;

    public Argument(Type type, Function parent, int num) {
        super(type);
        this.parent = parent;
        this.num = num;
    }

    @Override
    public String toString() {
        return "%val." + num;
    }

    @Override
    public HashSet<Value> getDef() {
        return new HashSet<>();
    }

    @Override
    public String getName() {
        return "val." + num;
    }
}
