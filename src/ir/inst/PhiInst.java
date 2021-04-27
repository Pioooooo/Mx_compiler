package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;
import ir.values.ConstantPointerNull;
import util.IRCloner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class PhiInst extends Inst {
    public HashMap<BasicBlock, Value> blocks;

    PhiInst(Type type, HashMap<BasicBlock, Value> blocks, BasicBlock basicBlock, Inst inst) {
        super(type, basicBlock, inst);
        this.blocks = blocks;
        blocks.forEach((b, v) -> {
            b.addUse(this);
            v.addUse(this);
        });
    }

    PhiInst(Type type, HashMap<BasicBlock, Value> blocks, BasicBlock basicBlock) {
        super(type, basicBlock);
        this.blocks = blocks;
        blocks.forEach((b, v) -> {
            b.addUse(this);
            v.addUse(this);
        });
    }

    PhiInst(Type type, HashMap<BasicBlock, Value> blocks, Inst inst) {
        super(type, inst);
        this.blocks = blocks;
        blocks.forEach((b, v) -> {
            b.addUse(this);
            v.addUse(this);
        });
    }

    public static PhiInst create(Type type, HashMap<BasicBlock, Value> blocks, BasicBlock basicBlock, Inst inst) {
        return new PhiInst(type, blocks, basicBlock, inst);
    }

    public static PhiInst create(Type type, HashMap<BasicBlock, Value> blocks, BasicBlock basicBlock) {
        return new PhiInst(type, blocks, basicBlock);
    }

    public static PhiInst create(Type type, HashMap<BasicBlock, Value> blocks, Inst inst) {
        return new PhiInst(type, blocks, inst);
    }

    public static PhiInst create(Type type, BasicBlock basicBlock, Inst inst) {
        return new PhiInst(type, new HashMap<>(), basicBlock, inst);
    }

    public static PhiInst create(Type type, BasicBlock basicBlock) {
        return new PhiInst(type, new HashMap<>(), basicBlock);
    }

    public static PhiInst create(Type type, Inst inst) {
        return new PhiInst(type, new HashMap<>(), inst);
    }

    public void addIncoming(BasicBlock basicBlock, Value value) {
        blocks.put(basicBlock, value);
        basicBlock.addUse(this);
        value.addUse(this);
    }

    @Override
    public HashSet<Value> getDef() {
        return new HashSet<>(blocks.values());
    }

    @Override
    public void replaceUse(Value o, Value n) {
        if (o instanceof BasicBlock && blocks.containsKey(o)) {
            blocks.put((BasicBlock) n, blocks.get(o));
            blocks.remove(o);
        } else {
            blocks.replaceAll((k, v) -> v == o ? n : v);
        }
    }

    @Override
    public boolean sameMeaning(Value other) {
        return false;
    }

    @Override
    public Value simplify() {
        Value commonValue = blocks.values().iterator().next();
        if (blocks.values().stream().allMatch(v -> v == commonValue)) {
            return commonValue;
        }
        if (use.stream().allMatch(u -> u == this)) {
            return ConstantPointerNull.get(type.m);
        }
        return null;
    }

    @Override
    public void getClone(IRCloner c) {
        if (c.getClone(this) != null) {
            return;
        }
        if (getPrev() != null) {
            getPrev().get().getClone(c);
        }
        c.setClone(this, create(type, c.getClone(getParent()), null));
        c.phi.add(this);
    }

    @Override
    public boolean hasRet() {
        return true;
    }

    @Override
    public String getFullInst() {
        return this + " = phi " + type + " " +
                blocks.entrySet().stream().map(e -> "[ " + e.getValue() + ", " + e.getKey() + " ]").collect(Collectors.joining(", "));
    }
}
