package ir;

import asm.AsmBlock;
import ir.inst.BrInst;
import ir.inst.PhiInst;
import ir.inst.Terminator;
import util.error.InternalError;
import util.list.List;
import util.list.ListIterator;
import util.list.ListNode;
import util.list.ListNodeWithParent;

import java.util.HashSet;
import java.util.Iterator;

public class BasicBlock extends Value implements ListNodeWithParent<BasicBlock, Function>, Iterable<Inst> {
    public List<Inst> instList = new List<>();
    boolean terminated = false;
    public HashSet<BasicBlock> pre = new HashSet<>();
    public HashSet<BasicBlock> suc = new HashSet<>();

    BasicBlock prev, next;
    Function function;

    public int num, loopDepth;
    public AsmBlock asmBlock;

    BasicBlock(int loopDepth, int num, Module m, Function function, BasicBlock basicBlock) {
        super(Type.getLabelTy(m));
        this.loopDepth = loopDepth;
        this.num = num;
        if (function != null) {
            this.function = function;
            if (basicBlock != null) {
                function.basicBlockList.insertBefore(this, basicBlock);
            } else {
                function.basicBlockList.add(this);
            }
        }
    }

    public static BasicBlock create(int loopDepth, Module m, Function function, BasicBlock basicBlock) {
        return new BasicBlock(loopDepth, function.blockCnt++, m, function, basicBlock);
    }

    public static BasicBlock create(int loopDepth, Module m, Function function) {
        return new BasicBlock(loopDepth, function.blockCnt++, m, function, null);
    }

    public static BasicBlock create(int loopDepth, Module m) {
        return new BasicBlock(loopDepth, 0, m, null, null);
    }

    public void addSuccessor(BasicBlock suc) {
        if (suc != null) {
            this.suc.add(suc);
            suc.pre.add(this);
        }
    }

    @Override
    public String getName() {
        return "block." + num;
    }

    @Override
    public boolean sameMeaning(Value other) {
        return false;
    }

    @Override
    public String toString() {
        return "%block." + num;
    }

    public BasicBlock add(Inst inst) {
        instList.add(inst);
        if (inst instanceof Terminator) {
            if (terminated) {
                throw new InternalError("calling terminate() on terminated Block");
            } else {
                terminated = true;
            }
        }
        return this;
    }

    public void terminate() {
        terminated = true;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public ListIterator<Inst> getHead() {
        return instList.getHead();
    }

    public ListIterator<Inst> getTail() {
        return instList.getTail();
    }

    public void replaceUse() {
        BasicBlock next = suc.iterator().next();
        use.forEach(i -> {
            if (i instanceof BrInst) {
                i.replaceUse(this, next);
                i.getParent().suc.remove(this);
                i.getParent().suc.add(next);
                next.pre.add(i.getParent());
            } else if (i instanceof PhiInst) {
                Value v = ((PhiInst) i).blocks.remove(this);
                pre.forEach(p -> ((PhiInst) i).addIncoming(p, v));
                Value n = i.simplify();
                if (n != null) {
                    i.replaceUseWith(n);
                    i.removeSelfAndDef();
                }
            } else {
                throw new InternalError("unexpected type of use");
            }
        });
        next.pre.remove(this);
        next.pre.addAll(pre);
        use.clear();
    }

    @Override
    public ListNode<BasicBlock> getPrev() {
        return prev;
    }

    @Override
    public ListNode<BasicBlock> getNext() {
        return next;
    }

    @Override
    public void setPrev(ListNode<BasicBlock> prev) {
        this.prev = prev == null ? null : prev.get();
    }

    @Override
    public void setNext(ListNode<BasicBlock> next) {
        this.next = next == null ? null : next.get();
    }

    @Override
    public BasicBlock get() {
        return this;
    }

    @Override
    public void removeSelf() {
        if (function != null) {
            function.basicBlockList.remove(this);
            return;
        }
        if (next != null) {
            next.setPrev(prev);
        }
        if (prev != null) {
            prev.setNext(next);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicBlock that = (BasicBlock) o;

        return instList.equals(that.instList);
    }

    @Override
    public int hashCode() {
        return instList.hashCode();
    }

    @Override
    public Function getParent() {
        return function;
    }

    public void setParent(Function function) {
        this.function = function;
        this.num = function.blockCnt++;
    }

    @Override
    public Iterator<Inst> iterator() {
        return instList.iterator();
    }

    @Override
    public HashSet<Value> getDef() {
        return new HashSet<>();
    }

    @Override
    public void replaceUse(Value o, Value n) {
    }
}
