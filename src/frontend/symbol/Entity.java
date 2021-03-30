package frontend.symbol;

import frontend.symbol.type.Type;
import ir.Value;

public class Entity extends Symbol {
    Type type;
    Value value;
    public boolean isMember = false;
    public int numElement = 0;

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

    public Entity setType(Type type) {
        this.type = type;
        return this;
    }

    public Value value() {
        return value;
    }

    public Entity setValue(Value value) {
        this.value = value;
        return this;
    }
}
