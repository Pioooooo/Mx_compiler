package ir.values;

import ir.Module;
import ir.Type;

public class ConstantPointerNull extends GlobalPointer {
    ConstantPointerNull(Module m) {
        super(Type.getPointerTy(Type.getVoidTy(m), false));
    }

    public static ConstantPointerNull get(Module m) {
        return new ConstantPointerNull(m);
    }
}
