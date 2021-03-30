package ir.inst;

import ir.BasicBlock;
import ir.Function;
import ir.Inst;
import ir.Value;
import ir.type.FunctionType;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CallInst extends Inst {
    public Function function;
    public ArrayList<Value> args;

    CallInst(Function function, ArrayList<Value> args, BasicBlock basicBlock, Inst inst) {
        super(((FunctionType) function.getType()).retType, basicBlock, inst);
        this.function = function;
        this.args = args;
    }

    CallInst(Function function, ArrayList<Value> args, BasicBlock basicBlock) {
        super(((FunctionType) function.getType()).retType, basicBlock);
        this.function = function;
        this.args = args;
    }

    CallInst(Function function, BasicBlock basicBlock, Inst inst) {
        this(function, new ArrayList<>(), basicBlock, inst);
    }

    CallInst(Function function, BasicBlock basicBlock) {
        this(function, new ArrayList<>(), basicBlock);
    }

    CallInst(Function function, ArrayList<Value> args, Inst inst) {
        super(((FunctionType) function.getType()).retType, inst);
        this.function = function;
        this.args = args;
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
    public boolean hasRet() {
        return !function.getRetType().isVoid();
    }

    @Override
    public String getFullInst() {
        return (hasRet() ? this + " = call " + function.getRetType() : "call void") + " " + function + " ("
                + args.stream().map(a -> a.getType() + " " + a).collect(Collectors.joining(", "))
                + ")";
    }
}
