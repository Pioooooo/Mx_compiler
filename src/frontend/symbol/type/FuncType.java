package frontend.symbol.type;

import frontend.symbol.Entity;
import util.error.InternalError;

import java.util.ArrayList;

public class FuncType extends BaseType {
    Type retType;
    ArrayList<Entity> param;

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
        throw new InternalError("call sameType on FuncType");
    }
}
