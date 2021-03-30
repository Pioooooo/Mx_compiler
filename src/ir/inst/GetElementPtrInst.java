package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GetElementPtrInst extends Inst {
    public Value ptrVal;
    public ArrayList<Value> indexes;

    GetElementPtrInst(Type type, Value ptrVal, ArrayList<Value> indexes, BasicBlock basicBlock, Inst inst) {
        super(type, basicBlock, inst);
        this.ptrVal = ptrVal;
        this.indexes = indexes;
    }

    GetElementPtrInst(Type type, Value ptrVal, ArrayList<Value> indexes, BasicBlock basicBlock) {
        super(type, basicBlock);
        this.ptrVal = ptrVal;
        this.indexes = indexes;
    }

    GetElementPtrInst(Type type, Value ptrVal, ArrayList<Value> indexes, Inst inst) {
        super(type, inst);
        this.ptrVal = ptrVal;
        this.indexes = indexes;
    }

    public static GetElementPtrInst create(Type type, Value ptrVal, ArrayList<Value> indexes, BasicBlock basicBlock, Inst inst) {
        return new GetElementPtrInst(type, ptrVal, indexes, basicBlock, inst);
    }

    public static GetElementPtrInst create(Type type, Value ptrVal, ArrayList<Value> indexes, BasicBlock basicBlock) {
        return new GetElementPtrInst(type, ptrVal, indexes, basicBlock);
    }

    public static GetElementPtrInst create(Type type, Value ptrVal, ArrayList<Value> indexes, Inst inst) {
        return new GetElementPtrInst(type, ptrVal, indexes, inst);
    }

    @Override
    public boolean hasRet() {
        return true;
    }

    @Override
    public String getFullInst() {
        return this + " = getelementptr inbounds " + ptrVal.getType().getBaseType() + ", " + ptrVal.getType() + " " + ptrVal + ", "
                + indexes.stream().map(i -> i.getType() + " " + i).collect(Collectors.joining(", "));
    }
}