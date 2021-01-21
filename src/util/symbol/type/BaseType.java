package util.symbol.type;

abstract public class BaseType extends Type {
    public BaseType(String name) {
        super(name);
    }

    @Override
    public BaseType base() {
        return this;
    }
}
