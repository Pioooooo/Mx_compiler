package util.symbol.type;

public class FundamentalType extends BaseType {
    public FundamentalType(String name) {
        super(name);
    }

    @Override
    public boolean equals(Type a) {
        return isNull() && (a instanceof ArrayType || a instanceof ClassType) || name().equals(a.name()) && a instanceof FundamentalType;
    }
}
