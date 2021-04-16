package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;

import java.util.HashMap;
import java.util.stream.Collectors;

public class PhiInst extends Inst {
    public HashMap<BasicBlock, Value> blocks;

    PhiInst(Type type, HashMap<BasicBlock, Value> blocks, BasicBlock basicBlock, Inst inst) {
        super(type, basicBlock, inst);
        this.blocks = blocks;
        blocks.values().forEach(v -> v.addUse(this));
    }

    PhiInst(Type type, HashMap<BasicBlock, Value> blocks, BasicBlock basicBlock) {
        super(type, basicBlock);
        this.blocks = blocks;
        blocks.values().forEach(v -> v.addUse(this));
    }

    PhiInst(Type type, HashMap<BasicBlock, Value> blocks, Inst inst) {
        super(type, inst);
        this.blocks = blocks;
        blocks.values().forEach(v -> v.addUse(this));
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
        addUse(value);
    }

    @Override
    public void replaceUse(Value o, Value n) {
        blocks.replaceAll((k, v) -> v == o ? n : v);
    }

    @Override
    public Value simplify() {
        Value commonValue = blocks.values().iterator().next();
        if (blocks.values().stream().allMatch(v -> v == commonValue)) {
            return commonValue;
        }
        return null;
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
