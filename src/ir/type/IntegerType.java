package ir.type;

import ir.Module;
import ir.Type;
import ir.Value;
import ir.values.ConstantInt;

import java.util.Objects;

public class IntegerType extends Type {
    final int size;

    public IntegerType(Module m, int size) {
        super(m, TypeID.IntegerTyID);
        this.size = size;
    }

    @Override
    public int size() {
        return 32;
    }

    public static IntegerType get(Module m, int size) {
        IntegerType intTy = new IntegerType(m, size), entry = m.integerTypes.putIfAbsent(size, intTy);
        return Objects.requireNonNullElse(entry, intTy);
    }

    @Override
    public Value getDefaultValue(Module m) {
        return ConstantInt.get(m, size, 0);
    }

    @Override
    public String toString() {
        return "i" + size;
    }

//    @Override
//    public int size() {
//        return size;
//    }
//
//    @Override
//    public Boolean equals(Type other) {
//        return other instanceof IntType && other.size() == size;
//    }
//
}
