package util.symbol.type;

import util.symbol.Entity;

import java.util.HashMap;

public class ClassType extends BaseType {
    private HashMap<String, Entity> varMap = new HashMap<>();
    private HashMap<String, FuncType> funcMap = new HashMap<>();
    private FuncType constructor = null;

    public ClassType(String name) {
        super(name);
    }

    public HashMap<String, Entity> varMap() {
        return varMap;
    }

    public ClassType varMap(HashMap<String, Entity> varMap) {
        this.varMap = varMap;
        return this;
    }

    public HashMap<String, FuncType> funcMap() {
        return funcMap;
    }

    public ClassType funcMap(HashMap<String, FuncType> funcMap) {
        this.funcMap = funcMap;
        return this;
    }

    public ClassType constructor(FuncType constructor) {
        this.constructor = constructor;
        return this;
    }

    public FuncType constructor() {
        return constructor;
    }

    @Override
    public boolean equals(Type t) {
        return t.isNull() || t instanceof ClassType && name().equals(t.name());
    }
}
