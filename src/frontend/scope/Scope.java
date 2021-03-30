package frontend.scope;

import frontend.symbol.Entity;
import frontend.symbol.type.FuncType;
import frontend.symbol.type.Type;
import util.Position;
import util.error.SemanticError;

import java.util.HashMap;

public class Scope {
    private final Scope parent;
    private final HashMap<String, Entity> varMap = new HashMap<>();
    private final HashMap<String, FuncType> funcMap = new HashMap<>();
    private final HashMap<String, Type> typeMap = new HashMap<>();

    public Scope(Scope parent) {
        this.parent = parent;
    }

    public Scope parent() {
        return parent;
    }

    public Entity defVar(String k, Entity v, Position pos) {
        if (varMap.put(k, v) != null) {
            throw new SemanticError("multiple definition of variable " + k, pos);
        }
        return v;
    }

    public boolean containsVar(String k, boolean recursive) {
        return varMap.containsKey(k) || recursive && parent != null && parent.containsVar(k, true);
    }

    public Entity getVar(String k, boolean recursive, Position pos) {
        if (varMap.containsKey(k)) {
            return varMap.get(k);
        } else if (recursive && parent != null) {
            return parent.getVar(k, true, pos);
        } else {
            throw new SemanticError("undefined variable " + k, pos);
        }
    }

    public HashMap<String, Entity> varMap() {
        return this.varMap;
    }

    public void defFunc(String k, FuncType v, Position pos) {
        if (funcMap.put(k, v) != null) {
            throw new SemanticError("multiple definition of function " + k, pos);
        }
    }

    public boolean containsFunc(String k, boolean recursive) {
        return funcMap.containsKey(k) || recursive && parent != null && parent.containsFunc(k, true);
    }

    public FuncType getFunc(String k, boolean recursive, Position pos) {
        if (funcMap.containsKey(k)) {
            return funcMap.get(k);
        } else if (recursive && parent != null) {
            return parent.getFunc(k, true, pos);
        } else {
            throw new SemanticError("undefined function " + k, pos);
        }
    }

    public HashMap<String, FuncType> funcMap() {
        return this.funcMap;
    }

    public void defType(String k, Type v, Position pos) {
        if (typeMap.put(k, v) != null) {
            throw new SemanticError("type " + k + " already defined", pos);
        }
    }

    public boolean containsType(String k, boolean recursive) {
        return typeMap.containsKey(k) || recursive && parent != null && parent.containsType(k, true);
    }

    public Type getType(String k, boolean recursive, Position pos) {
        if (typeMap.containsKey(k)) {
            return typeMap.get(k);
        } else if (recursive && parent != null) {
            return parent.getType(k, true, pos);
        } else {
            throw new SemanticError("undefined type " + k, pos);
        }
    }
}
