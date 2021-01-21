package util.symbol.type;

import util.symbol.Symbol;

abstract public class Type extends Symbol {
    protected Type(String name) {
        super(name);
    }

    abstract public BaseType base();

    public int dim() {
        return 0;
    }

    public boolean equals(Type t) {
        return name().equals("bool");
    }

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
}
