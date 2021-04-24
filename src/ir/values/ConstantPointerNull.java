package ir.values;

import ir.Module;
import ir.Type;

public class ConstantPointerNull extends GlobalPointer {
    ConstantPointerNull(Module m) {
        super(Type.getPointerTy(Type.getVoidTy(m), false));
    }

    ConstantPointerNull(Type type) {
        super(type);
    }

    public static ConstantPointerNull get(Module m) {
        return new ConstantPointerNull(m);
    }

    public static ConstantPointerNull get(Type type) {
        return new ConstantPointerNull(type);
    }

    @Override
    public String toString() {
        return "null";
    }
}
