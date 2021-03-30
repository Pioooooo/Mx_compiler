package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PhiInst extends Inst {
    ArrayList<Pair<BasicBlock, Value>> blocks;

    PhiInst(Type type, ArrayList<Pair<BasicBlock, Value>> blocks, BasicBlock basicBlock, Inst inst) {
        super(type, basicBlock, inst);
        this.blocks = blocks;
    }

    PhiInst(Type type, ArrayList<Pair<BasicBlock, Value>> blocks, BasicBlock basicBlock) {
        super(type, basicBlock);
        this.blocks = blocks;
    }

    PhiInst(Type type, ArrayList<Pair<BasicBlock, Value>> blocks, Inst inst) {
        super(type, inst);
        this.blocks = blocks;
    }

    public static PhiInst create(Type type, ArrayList<Pair<BasicBlock, Value>> blocks, BasicBlock basicBlock, Inst inst) {
        return new PhiInst(type, blocks, basicBlock, inst);
    }

    public static PhiInst create(Type type, ArrayList<Pair<BasicBlock, Value>> blocks, BasicBlock basicBlock) {
        return new PhiInst(type, blocks, basicBlock);
    }

    public static PhiInst create(Type type, ArrayList<Pair<BasicBlock, Value>> blocks, Inst inst) {
        return new PhiInst(type, blocks, inst);
    }

    public void addIncoming(BasicBlock basicBlock, Value value) {
        blocks.add(new Pair<>(basicBlock, value));
    }

    @Override
    public boolean hasRet() {
        return true;
    }

    @Override
    public String getFullInst() {
        return this + " = phi " + type + " "
                + blocks.stream().map(p -> "[ " + p.b + ", " + p.a + " ]").collect(Collectors.joining(", "));
    }
}
