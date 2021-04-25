package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class GetElementPtrInst extends Inst {
    public Value ptrVal;
    public ArrayList<Value> indexes;

    GetElementPtrInst(Type type, Value ptrVal, ArrayList<Value> indexes, BasicBlock basicBlock, Inst inst) {
        super(type, basicBlock, inst);
        this.ptrVal = ptrVal;
        this.indexes = indexes;
        ptrVal.addUse(this);
        indexes.forEach(i -> i.addUse(this));
    }

    GetElementPtrInst(Type type, Value ptrVal, ArrayList<Value> indexes, BasicBlock basicBlock) {
        super(type, basicBlock);
        this.ptrVal = ptrVal;
        this.indexes = indexes;
        ptrVal.addUse(this);
        indexes.forEach(i -> i.addUse(this));
    }

    GetElementPtrInst(Type type, Value ptrVal, ArrayList<Value> indexes, Inst inst) {
        super(type, inst);
        this.ptrVal = ptrVal;
        this.indexes = indexes;
        ptrVal.addUse(this);
        indexes.forEach(i -> i.addUse(this));
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
    public void replaceUse(Value o, Value n) {
        if (ptrVal == o) {
            ptrVal = n;
        }
        indexes.replaceAll(i -> i == o ? n : i);
    }

    @Override
    public boolean sameMeaning(Value other) {
        return other instanceof GetElementPtrInst && ptrVal == ((GetElementPtrInst) other).ptrVal &&
                indexes.equals(((GetElementPtrInst) other).indexes);
    }

    @Override
    public Value simplify() {
        return null;
    }

    @Override
    public HashSet<Value> getDef() {
        var def = new HashSet<Value>();
        def.add(ptrVal);
        def.addAll(indexes);
        return def;
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
