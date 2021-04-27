package util;

import ir.BasicBlock;
import ir.Inst;
import ir.Value;
import ir.inst.PhiInst;
import ir.values.Argument;
import ir.values.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class IRCloner {
    HashMap<Value, Value> clone;
    public HashSet<PhiInst> phi = new HashSet<>();

    public IRCloner(HashMap<Value, Value> clone) {
        this.clone = clone;
    }

    public IRCloner() {
        this(new HashMap<>());
    }

    public void setClone(Value o, Value n) {
        clone.put(o, n);
    }

    public BasicBlock getClone(BasicBlock v) {
        return (BasicBlock) clone.get(v);
    }

    public Inst getClone(Inst v) {
        return (Inst) clone.get(v);
    }

    public Constant getClone(Constant v) {
        return v;
    }

    public Value getClone(Argument v) {
        return clone.get(v);
    }

    public Value getClone(Value v) {
        if (v instanceof Constant && !(v instanceof Argument)) {
            return v;
        }
        return clone.get(v);
    }

    public ArrayList<Value> getClone(ArrayList<Value> c) {
        return c.stream().map(this::getClone).collect(Collectors.toCollection(ArrayList::new));
    }
}
