package ir;

import ir.values.GlobalPointer;
import ir.values.GlobalString;
import ir.type.*;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Module {
    public Type voidTy = new Type(this, Type.TypeID.VoidTyID),
            labelTy = new Type(this, Type.TypeID.LabelTyID);
    public IntegerType int1Ty = new IntegerType(this, 1),
            int8Ty = new IntegerType(this, 8),
            int16Ty = new IntegerType(this, 16),
            int32Ty = new IntegerType(this, 32);
    public PointerType stringTy = new PointerType(int8Ty, false);

    public HashMap<Integer, IntegerType> integerTypes = new HashMap<>();
    public HashMap<Pair<Type, ArrayList<Type>>, FunctionType> functionTypes = new HashMap<>();
    public HashMap<ArrayList<Type>, StructType> structTypes = new HashMap<>();
    public HashMap<String, Type> namedStructTypes = new HashMap<>();
    public HashMap<Pair<Type, Integer>, ArrayType> arrayTypes = new HashMap<>();
    public HashMap<Type, PointerType> pointerTypes = new HashMap<>();

    public ArrayList<GlobalPointer> globals = new ArrayList<>();
    public HashMap<String, GlobalString> constantStrings = new HashMap<>();
    public HashMap<String, Function> functions = new HashMap<>();
    public HashMap<String, Function> builtinFunctions = new HashMap<>();


    public Module() {
        integerTypes.put(1, int1Ty);
        integerTypes.put(8, int8Ty);
        integerTypes.put(16, int16Ty);
        integerTypes.put(32, int32Ty);
        namedStructTypes.put("string", stringTy);
    }

    public Function getFunction(String funcName) {
        Function ret = functions.get(funcName);
        if (ret == null) {
            return builtinFunctions.get(funcName);
        }
        return ret;
    }

    public Function addFunction(String funcName, Function function) {
        function.setParent(this);
        function.setName(funcName);
        return functions.put(funcName, function);
    }

    public Function getBuiltinFunction(String funcName) {
        return builtinFunctions.get(funcName);
    }

    public Function addBuiltinFunction(String funcName, Function function) {
        function.setParent(this);
        function.setName(funcName);
        return builtinFunctions.put(funcName, function);
    }
}
