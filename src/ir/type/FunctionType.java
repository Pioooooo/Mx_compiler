package ir.type;

import ir.Type;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.Objects;

public class FunctionType extends Type {
    final public Type retType;
    final public ArrayList<Type> params;

    FunctionType(Type retType, ArrayList<Type> params) {
        super(retType.m, TypeID.FunctionTyID);
        this.retType = retType;
        this.params = params;
    }

    public static FunctionType get(Type result, ArrayList<Type> params) {
        FunctionType functionType = new FunctionType(result, params), entry = result.m.functionTypes.putIfAbsent(new Pair<>(result, params), functionType);
        return Objects.requireNonNullElse(entry, functionType);
    }

    public static FunctionType get(Type result) {
        FunctionType functionType = new FunctionType(result, new ArrayList<>()), entry = result.m.functionTypes.putIfAbsent(new Pair<>(result, new ArrayList<>()), functionType);
        return Objects.requireNonNullElse(entry, functionType);
    }
}
