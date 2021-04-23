package ir.type;

import ir.Module;
import ir.Type;
import org.antlr.v4.runtime.misc.Pair;

import java.util.Objects;

public class ArrayType extends Type {
    final Type containedType;
    final int size;

    ArrayType(Type containedType, int size) {
        super(containedType.m, TypeID.ArrayTyID);
        this.containedType = containedType;
        this.size = size;
    }

    @Override
    public int size() {
        return 32;
    }

    @Override
    public Type getBaseType() {
        return containedType;
    }

    public static ArrayType get(Module m, Type containedType, int size) {
        ArrayType arrayType = new ArrayType(containedType, size), entry = containedType.m.arrayTypes.putIfAbsent(new Pair<>(containedType, size), arrayType);
        return Objects.requireNonNullElse(entry, arrayType);
    }

    @Override
    public String toString() {
        return "[" + size + " x " + containedType.toString() + ']';
    }
}
