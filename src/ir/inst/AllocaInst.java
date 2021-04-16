package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;
import ir.type.PointerType;

import java.util.ArrayList;

public class AllocaInst extends Inst {
    public int offset;
    public ArrayList<LoadInst> loadInst = new ArrayList<>();
    public ArrayList<StoreInst> storeInst = new ArrayList<>();

    protected AllocaInst(Type type, BasicBlock basicBlock, Inst inst) {
        super(PointerType.get(type, true), basicBlock, inst);
        basicBlock.getParent().allocas.add(this);
    }

    protected AllocaInst(Type type, BasicBlock basicBlock) {
        super(PointerType.get(type, true), basicBlock);
        basicBlock.getParent().allocas.add(this);
    }

    protected AllocaInst(Type type, Inst inst) {
        super(PointerType.get(type, true), inst);
        inst.getParent().getParent().allocas.add(this);
    }

    @Override
    public Value simplify() {
        return null;
    }

    public static AllocaInst create(Type type, BasicBlock basicBlock, Inst inst) {
        return new AllocaInst(type, basicBlock, inst);
    }

    public static AllocaInst create(Type type, BasicBlock basicBlock) {
        return new AllocaInst(type, basicBlock);
    }

    public static AllocaInst create(Type type, int off, Inst inst) {
        return new AllocaInst(type, inst);
    }

    @Override
    public void addUse(Value u) {
        if (u instanceof LoadInst) {
            loadInst.add((LoadInst) u);
        }
        if (u instanceof StoreInst) {
            storeInst.add((StoreInst) u);
        }
        super.addUse(u);
    }

    @Override
    public boolean removeUse(Value u) {
        if (u instanceof LoadInst) {
            loadInst.remove(u);
        }
        if (u instanceof StoreInst) {
            storeInst.remove(u);
        }
        return super.removeUse(u);
    }

    @Override
    public void replaceUse(Value o, Value n) {
    }

    @Override
    public boolean hasRet() {
        return true;
    }

    @Override
    public String getFullInst() {
        return this + " = alloca " + type.getBaseType() + ", align " + type.getBaseType().size() / 8;
    }
}
