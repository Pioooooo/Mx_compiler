package ir.values;

import ir.Module;
import ir.type.IntegerType;
import org.antlr.v4.runtime.misc.Pair;
import util.error.InternalError;

import java.util.Objects;

public class ConstantInt extends Constant {
    public final int val;

    ConstantInt(IntegerType type, int val) {
        super(type);
        this.val = val;
    }

    public static ConstantInt get(Module m, int size, int val) {
        ConstantInt constantInt = new ConstantInt(IntegerType.get(m, size), val), entry = m.constantInts.putIfAbsent(new Pair<>(size, val), constantInt);
        return Objects.requireNonNullElse(entry, constantInt);
    }

    public static ConstantInt getBool(Module m, int size, boolean val) {
        return ConstantInt.get(m, size, val ? 1 : 0);
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
