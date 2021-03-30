package frontend.symbol.type;

import frontend.symbol.Symbol;
import ir.Module;

abstract public class Type extends Symbol {
    protected Type(String name) {
        super(name);
    }

    abstract public BaseType base();

    public int dim() {
        return 0;
    }

    abstract public boolean equals(Type t);

    public boolean isNull() {
        return name().equals("null");
    }

    public boolean isInt() {
        return name().equals("int");
    }

    public boolean isBool() {
        return name().equals("bool");
    }

    public boolean isVoid() {
        return name().equals("void");
    }

    public boolean isArray() {
        return this instanceof ArrayType;
    }

    public boolean isClass() {
        return this instanceof ClassType;
    }

    public ir.Type irType(Module m) {
        return ir.Type.get(m, this);
    }
}
