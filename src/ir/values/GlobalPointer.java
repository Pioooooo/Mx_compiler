package ir.values;

import ir.Type;

public class GlobalPointer extends Constant {
    GlobalPointer(Type type) {
        super(type);
    }

    @Override
    public String getName() {
        return "g." + num;
    }

    public static GlobalPointer create(Type type) {
        GlobalPointer ptr = new GlobalPointer(type);
        type.m.globals.add(ptr);
        return ptr;
    }

    @Override
    public String toString() {
        return "@g." + num;
    }
}
