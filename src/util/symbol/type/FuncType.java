package util.symbol.type;

import util.Position;
import util.error.InternalError;
import util.symbol.Entity;

import java.util.ArrayList;

public class FuncType extends BaseType {
    private Type retType;
    private ArrayList<Entity> param;

    public FuncType(String name, Type retType, ArrayList<Entity> param) {
        super(name);
        this.retType = retType;
        this.param = param;
    }

    public FuncType(String name) {
        super(name);
        this.param = new ArrayList<>();
    }

    public Type retType() {
        return retType;
    }

    public FuncType retType(Type retType) {
        this.retType = retType;
        return this;
    }

    public ArrayList<Entity> param() {
        return param;
    }

    public FuncType param(ArrayList<Entity> param) {
        this.param = param;
        return this;
    }

    @Override
    public boolean equals(Type a) {
        throw new InternalError("call sameType on FuncType", new Position(0, 0));
    }
}
