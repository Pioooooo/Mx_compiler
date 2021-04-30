package ir.inst;

import ir.BasicBlock;
import ir.Inst;
import ir.Type;
import ir.Value;
import ir.values.ConstantInt;
import util.IRCloner;

import java.util.HashSet;

public class BrInst extends Terminator {
    public Value cond;
    public BasicBlock trueDest, falseDest;

    BrInst(Value cond, BasicBlock trueDest, BasicBlock falseDest, BasicBlock basicBlock, Inst inst) {
        super(Type.getVoidTy(trueDest.getContext()), basicBlock, inst);
        this.cond = cond;
        this.trueDest = trueDest;
        this.falseDest = falseDest;
        basicBlock.addSuccessor(trueDest);
        basicBlock.addSuccessor(falseDest);
        trueDest.addUse(this);
        if (cond != null) {
            cond.addUse(this);
            falseDest.addUse(this);
        }
    }

    BrInst(Value cond, BasicBlock trueDest, BasicBlock falseDest, BasicBlock basicBlock) {
        super(Type.getVoidTy(trueDest.getContext()), basicBlock);
        this.cond = cond;
        this.trueDest = trueDest;
        this.falseDest = falseDest;
        basicBlock.addSuccessor(trueDest);
        basicBlock.addSuccessor(falseDest);
        trueDest.addUse(this);
        if (cond != null) {
            cond.addUse(this);
            falseDest.addUse(this);
        }
    }

    BrInst(Value cond, BasicBlock trueDest, BasicBlock falseDest, Inst inst) {
        super(Type.getVoidTy(trueDest.getContext()), inst);
        this.cond = cond;
        this.trueDest = trueDest;
        this.falseDest = falseDest;
        inst.getParent().addSuccessor(trueDest);
        inst.getParent().addSuccessor(falseDest);
        trueDest.addUse(this);
        if (cond != null) {
            cond.addUse(this);
            falseDest.addUse(this);
        }
    }

    BrInst(BasicBlock trueDest, BasicBlock basicBlock, Inst inst) {
        this(null, trueDest, null, basicBlock, inst);
    }

    BrInst(BasicBlock trueDest, BasicBlock basicBlock) {
        this(null, trueDest, null, basicBlock);
    }

    BrInst(BasicBlock trueDest, Inst inst) {
        this(null, trueDest, null, inst);
    }

    public static BrInst create(Value cond, BasicBlock trueDest, BasicBlock falseDest, BasicBlock basicBlock, Inst inst) {
        return new BrInst(cond, trueDest, falseDest, basicBlock, inst);
    }

    public static BrInst create(Value cond, BasicBlock trueDest, BasicBlock falseDest, BasicBlock basicBlock) {
        return new BrInst(cond, trueDest, falseDest, basicBlock);
    }

    public static BrInst create(BasicBlock trueDest, BasicBlock basicBlock, Inst inst) {
        return new BrInst(trueDest, basicBlock, inst);
    }

    public static BrInst create(BasicBlock trueDest, BasicBlock basicBlock) {
        return new BrInst(trueDest, basicBlock);
    }

    public static BrInst create(Value cond, BasicBlock trueDest, BasicBlock falseDest, Inst inst) {
        return new BrInst(cond, trueDest, falseDest, inst);
    }

    public static BrInst create(BasicBlock trueDest, Inst inst) {
        return new BrInst(trueDest, inst);
    }

    @Override
    public HashSet<Value> getDef() {
        var def = new HashSet<Value>();
        if (cond != null) {
            def.add(cond);
            def.add(falseDest);
        }
        def.add(trueDest);
        return def;
    }

    @Override
    public void replaceUse(Value o, Value n) {
        if (cond == o) {
            cond = n;
        }
        if (trueDest == o) {
            trueDest = (BasicBlock) n;
        }
        if (falseDest == o) {
            falseDest = (BasicBlock) n;
        }
    }

    @Override
    public void getClone(IRCloner c) {
        if (c.getClone(this) != null) {
            return;
        }
        super.getClone(c);
        c.setClone(this, create(c.getClone(cond), c.getClone(trueDest), c.getClone(falseDest), c.getClone(getParent()), null));
    }

    @Override
    public Value simplify() {
        if (cond != null && cond instanceof ConstantInt) {
            BasicBlock b = getParent();
            b.unTerminate();
            BasicBlock deadBlock = ((ConstantInt) cond).val != 0 ? falseDest : trueDest;
            BrInst br = BrInst.create(((ConstantInt) cond).val != 0 ? trueDest : falseDest, this);
            var it = b.use.iterator();
            while (it.hasNext()) {
                var i = it.next();
                if (i instanceof PhiInst && i.getParent() == deadBlock) {
                    Value v = ((PhiInst) i).blocks.remove(b);
                    if (v != null) {
                        v.removeUse(i);
                        it.remove();
                    }
                }
            }
            return br;
        }
        return null;
    }

    @Override
    public boolean noSideEffect() {
        return false;
    }

    @Override
    public String getFullInst() {
        return "br " + (cond != null ? cond.getType() + " " + cond + ", label " + trueDest + ", label " + falseDest : "label " + trueDest);
    }
}
