package frontend.symbol.type;

import ast.Nodes.ClassTypeNode;
import frontend.symbol.Entity;

import java.util.HashMap;

public class ClassType extends BaseType {
    HashMap<String, Entity> varMap = new HashMap<>();
    HashMap<String, FuncType> funcMap = new HashMap<>();
    FuncType constructor = null;
    public ClassTypeNode classDef;

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
