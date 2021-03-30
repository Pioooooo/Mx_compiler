package frontend.symbol.type;

public class ArrayType extends Type {
    private final BaseType baseType;
    private final int dim;

    public ArrayType(BaseType baseType, int dim) {
        super(baseType.name() + "[]".repeat(dim));
        this.baseType = baseType;
        this.dim = dim;
    }

    @Override
    public BaseType base() {
        return baseType;
    }

    @Override
    public int dim() {
        return dim;
    }

    @Override
    public boolean equals(Type t) {
        return t.isNull() || baseType.equals(t.base()) && dim == t.dim();
    }
}
