package util.symbol;

import util.symbol.type.Type;

public class Entity extends Symbol {
    private Type type;

    public Entity(String name, Type type) {
        super(name);
        this.type = type;
    }

    public Entity(String name) {
        super(name);
    }

    public Type type() {
        return type;
    }

    public Entity type(Type type) {
        this.type = type;
        return this;
    }
}
