package ir.inst;

import ir.BasicBlock;
import ir.Function;
import ir.Inst;
import ir.Value;
import ir.type.FunctionType;
import ir.values.ConstantInt;
import ir.values.GlobalString;
import util.IRCloner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class CallInst extends Inst {
    public Function function;
    public ArrayList<Value> args;

    CallInst(Function function, ArrayList<Value> args, BasicBlock basicBlock, Inst inst) {
        super(((FunctionType) function.getType()).retType, basicBlock, inst);
        this.function = function;
        this.args = args;
        function.addUse(this);
        getParent().getParent().calls.add(this);
        args.forEach(a -> a.addUse(this));
        if (!function.noSideEffect()) {
            getParent().getParent().setHasSideEffect();
        }
    }

    CallInst(Function function, ArrayList<Value> args, BasicBlock basicBlock) {
        super(((FunctionType) function.getType()).retType, basicBlock);
        this.function = function;
        this.args = args;
        function.addUse(this);
        getParent().getParent().calls.add(this);
        args.forEach(a -> a.addUse(this));
        if (!function.noSideEffect()) {
            getParent().getParent().setHasSideEffect();
        }
    }

    CallInst(Function function, ArrayList<Value> args, Inst inst) {
        super(((FunctionType) function.getType()).retType, inst);
        this.function = function;
        this.args = args;
        function.addUse(this);
        getParent().getParent().calls.add(this);
        args.forEach(a -> a.addUse(this));
        if (!function.noSideEffect()) {
            getParent().getParent().setHasSideEffect();
        }
    }

    CallInst(Function function, BasicBlock basicBlock, Inst inst) {
        this(function, new ArrayList<>(), basicBlock, inst);
    }

    CallInst(Function function, BasicBlock basicBlock) {
        this(function, new ArrayList<>(), basicBlock);
    }

    CallInst(Function function, Inst inst) {
        this(function, new ArrayList<>(), inst);
    }

    public static CallInst create(Function function, ArrayList<Value> args, BasicBlock basicBlock, Inst inst) {
        return new CallInst(function, args, basicBlock, inst);
    }

    public static CallInst create(Function function, ArrayList<Value> args, BasicBlock basicBlock) {
        return new CallInst(function, args, basicBlock);
    }

    public static CallInst create(Function function, BasicBlock basicBlock, Inst inst) {
        return new CallInst(function, basicBlock, inst);
    }

    public static CallInst create(Function function, BasicBlock basicBlock) {
        return new CallInst(function, basicBlock);
    }

    public static CallInst create(Function function, ArrayList<Value> args, Inst inst) {
        return new CallInst(function, args, inst);
    }

    public static CallInst create(Function function, Inst inst) {
        return new CallInst(function, inst);
    }

    @Override
    public HashSet<Value> getDef() {
        var def = new HashSet<Value>();
        def.add(function);
        def.addAll(args);
        return def;
    }

    @Override
    public void replaceUse(Value o, Value n) {
        args.replaceAll(a -> a == o ? n : a);
    }

    @Override
    public boolean sameMeaning(Value other) {
        return false;
    }

    @Override
    public Value simplify() {
        switch (function.getName()) {
            case "g_toString" -> {
                if (args.get(0) instanceof ConstantInt) {
                    ArrayList<Value> idx = new ArrayList<>();
                    idx.add(ConstantInt.get(getContext(), 32, 0));
                    idx.add(ConstantInt.get(getContext(), 32, 0));
                    return GetElementPtrInst.create(getContext().stringTy,
                            GlobalString.getFromVal(getContext(), String.valueOf(((ConstantInt) args.get(0)).val)), idx, this);
                }
            }
            case "__g_str_add" -> {
                if (args.get(0) instanceof GetElementPtrInst && args.get(1) instanceof GetElementPtrInst) {
                    ArrayList<Value> idx = new ArrayList<>();
                    idx.add(ConstantInt.get(getContext(), 32, 0));
                    idx.add(ConstantInt.get(getContext(), 32, 0));
                    return GetElementPtrInst.create(getContext().stringTy,
                            GlobalString.get(getContext(), ((GlobalString) ((GetElementPtrInst) args.get(0)).ptrVal).src.substring(0, ((GlobalString) ((GetElementPtrInst) args.get(0)).ptrVal).src.length() - 1)
                                    + ((GlobalString) ((GetElementPtrInst) args.get(1)).ptrVal).src.substring(1)), idx, this);
                }
            }
            case "__g_str_gt" -> {
                if (args.get(0) instanceof GetElementPtrInst && args.get(1) instanceof GetElementPtrInst) {
                    return ConstantInt.getBool(getContext(), 1, ((GlobalString) ((GetElementPtrInst) args.get(0)).ptrVal).val
                            .compareTo(((GlobalString) ((GetElementPtrInst) args.get(1)).ptrVal).val) > 0);
                }
            }
            case "__g_str_lt" -> {
                if (args.get(0) instanceof GetElementPtrInst && args.get(1) instanceof GetElementPtrInst) {
                    return ConstantInt.getBool(getContext(), 1, ((GlobalString) ((GetElementPtrInst) args.get(0)).ptrVal).val
                            .compareTo(((GlobalString) ((GetElementPtrInst) args.get(1)).ptrVal).val) < 0);
                }
            }
            case "__g_str_ge" -> {
                if (args.get(0) instanceof GetElementPtrInst && args.get(1) instanceof GetElementPtrInst) {
                    return ConstantInt.getBool(getContext(), 1, ((GlobalString) ((GetElementPtrInst) args.get(0)).ptrVal).val
                            .compareTo(((GlobalString) ((GetElementPtrInst) args.get(1)).ptrVal).val) >= 0);
                }
            }
            case "__g_str_le" -> {
                if (args.get(0) instanceof GetElementPtrInst && args.get(1) instanceof GetElementPtrInst) {
                    return ConstantInt.getBool(getContext(), 1, ((GlobalString) ((GetElementPtrInst) args.get(0)).ptrVal).val
                            .compareTo(((GlobalString) ((GetElementPtrInst) args.get(1)).ptrVal).val) <= 0);
                }
            }
            case "__g_str_ne" -> {
                if (args.get(0) instanceof GetElementPtrInst && args.get(1) instanceof GetElementPtrInst) {
                    return ConstantInt.getBool(getContext(), 1, ((GlobalString) ((GetElementPtrInst) args.get(0)).ptrVal).val
                            .compareTo(((GlobalString) ((GetElementPtrInst) args.get(1)).ptrVal).val) != 0);
                }
            }
            case "__g_str_eq" -> {
                if (args.get(0) instanceof GetElementPtrInst && args.get(1) instanceof GetElementPtrInst) {
                    return ConstantInt.getBool(getContext(), 1, ((GlobalString) ((GetElementPtrInst) args.get(0)).ptrVal).val
                            .compareTo(((GlobalString) ((GetElementPtrInst) args.get(1)).ptrVal).val) == 0);
                }
            }
            case "c_string_length" -> {
                if (args.get(0) instanceof GetElementPtrInst) {
                    return ConstantInt.get(getContext(), 32, ((GlobalString) ((GetElementPtrInst) args.get(0)).ptrVal).val.length() - 1);
                }
            }
            case "c_string_substring" -> {
                if (args.get(0) instanceof GetElementPtrInst && args.get(1) instanceof ConstantInt && args.get(2) instanceof ConstantInt) {
                    ArrayList<Value> idx = new ArrayList<>();
                    idx.add(ConstantInt.get(getContext(), 32, 0));
                    idx.add(ConstantInt.get(getContext(), 32, 0));
                    return GetElementPtrInst.create(getContext().stringTy,
                            GlobalString.getFromVal(getContext(), ((GlobalString) ((GetElementPtrInst) args.get(0)).ptrVal).val
                                    .substring(((ConstantInt) args.get(1)).val, ((ConstantInt) args.get(2)).val)), idx, this);
                }
            }
            case "c_string_parseInt" -> {
                if (args.get(0) instanceof GetElementPtrInst) {
                    return ConstantInt.get(getContext(), 32, Integer.parseInt(((GlobalString) ((GetElementPtrInst) args.get(0)).ptrVal).val));
                }
            }
            case "c_string_ord" -> {
                if (args.get(0) instanceof GetElementPtrInst && args.get(1) instanceof ConstantInt) {
                    return ConstantInt.get(getContext(), 32, ((GlobalString) ((GetElementPtrInst) args.get(0)).ptrVal).val
                            .charAt(((ConstantInt) args.get(1)).val));
                }
            }
        }
        return null;
    }

    @Override
    public void getClone(IRCloner c) {
        if (c.getClone(this) != null) {
            return;
        }
        super.getClone(c);
        c.setClone(this, create(function, c.getClone(args), c.getClone(getParent()), null));
    }

    @Override
    public boolean noSideEffect() {
        return function.noSideEffect();
    }

    @Override
    public boolean hasRet() {
        return !function.getRetType().isVoid();
    }

    @Override
    public String getFullInst() {
        return (hasRet() ? this + " = call " + function.getRetType() : "call void") + " " + function + " ("
                + args.stream().map(a -> a.getType() + " " + a).collect(Collectors.joining(", "))
                + ")";
    }

    @Override
    public void removeSelf() {
        super.removeSelf();
        getParent().getParent().calls.remove(this);
    }
}
