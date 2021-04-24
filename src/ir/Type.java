package ir;

import ir.type.ArrayType;
import ir.type.IntegerType;
import ir.type.PointerType;
import ir.type.StructType;
import ir.values.ConstantPointerNull;
import util.error.InternalError;

public class Type {
    public enum TypeID {
        VoidTyID,
        LabelTyID,
        IntegerTyID,
        FunctionTyID,
        PointerTyID,
        StructTyID,
        ArrayTyID
    }

    public final Module m;
    public final TypeID id;

    protected Type(Module m, TypeID id) {
        this.m = m;
        this.id = id;
    }

    public int size() {
        return 0;
    }

    public int allocSize() {
        return size();
    }

    public boolean isVoid() {
        return id == TypeID.VoidTyID;
    }

    public boolean isInt() {
        return id == TypeID.IntegerTyID;
    }

    public boolean isPointer() {
        return id == TypeID.PointerTyID;
    }

    public boolean isResolvable() {
        return false;
    }

    public Type getBaseType() {
        return this;
    }

    public static Type get(Module m, frontend.symbol.type.Type type) {
        if (type.isArray()) {
            Type ret = get(m, type.base());
            for (int i = 0; i < type.dim(); i++) {
                ret = getPointerTy(ret, false);
            }
            return ret;
        } else if (type.isInt()) {
            return getIntNTy(m, 32);
        } else if (type.isBool()) {
            return getIntNTy(m, 1);
        } else if (type.isVoid() || type.isNull()) {
            return getVoidTy(m);
        } else if (type.isClass()) {
            if (type.name().equals("string")) {
                return m.stringTy;
            } else {
                return getPointerTy(getStructTy(m, type.name()), false);
            }
        }
        throw new InternalError("unknown type " + type.name());
    }

    public static Type getVoidTy(Module m) {
        return m.voidTy;
    }

    public static Type getLabelTy(Module m) {
        return m.labelTy;
    }

    public static IntegerType getIntNTy(Module m, int n) {
        return IntegerType.get(m, n);
    }

    public static ArrayType getArrayTy(Type elementTy, int size) {
        return ArrayType.get(elementTy.m, elementTy, size);
    }

    public static PointerType getPointerTy(Type pointeeTy, boolean isResolvable) {
        return PointerType.get(pointeeTy, isResolvable);
    }

    public static Type getStructTy(Module m, String typename) {
        return StructType.get(m, typename);
    }

    public Value getDefaultValue(Module m) {
        return switch (id) {
            case PointerTyID -> ConstantPointerNull.get(m, this);
            case ArrayTyID -> ConstantPointerNull.get(m, getPointerTy(getBaseType(), false));
            case StructTyID -> ConstantPointerNull.get(m, getPointerTy(this, false));
            default -> throw new InternalError("calling getDefaultValue on Type");
        };
    }

    @Override
    public String toString() {
        return switch (id) {
            case VoidTyID -> "void";
            default -> throw new InternalError("calling toString on Type");
        };
    }
}
