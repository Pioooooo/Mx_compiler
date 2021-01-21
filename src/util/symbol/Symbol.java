package util.symbol;

abstract public class Symbol {
    private final String name;

    protected Symbol(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}
