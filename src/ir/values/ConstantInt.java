package ir.values;

import ir.Module;
import ir.type.IntegerType;
import util.error.InternalError;

public class ConstantInt extends Constant {
    public final int val;

    ConstantInt(IntegerType type, int val) {
        super(type);
        this.val = val;
    }

    public static ConstantInt get(Module m, int size, int val) {
        return new ConstantInt(IntegerType.get(m, size), val); // TODO: singleton
    }

    public static ConstantInt getBool(Module m, int size, boolean val) {
        return new ConstantInt(IntegerType.get(m, size), val ? 1 : 0);
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    public String getName() {
        throw new InternalError("calling getName() on ConstantInt");
    }
}
