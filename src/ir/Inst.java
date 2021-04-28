package ir;

import util.IRCloner;
import util.error.InternalError;
import util.list.ListNode;
import util.list.ListNodeWithParent;

abstract public class Inst extends Value implements ListNodeWithParent<Inst, BasicBlock> {
    Inst prev, next;
    BasicBlock basicBlock;
    public int num;

    protected Inst(Type type, BasicBlock basicBlock, Inst inst) {
        super(type);
        this.basicBlock = basicBlock;
        basicBlock.instList.insertBefore(inst, this);
    }

    protected Inst(Type type, BasicBlock basicBlock) {
        super(type);
        this.basicBlock = basicBlock;
        basicBlock.instList.insertBefore(basicBlock.getHead().get(), this);
    }

    protected Inst(Type type, Inst inst) {
        super(type);
        this.basicBlock = inst.getParent();
        basicBlock.instList.insertBefore(inst, this);
    }

    public abstract Value simplify();

    public boolean noSideEffect() {
        return true;
    }

    public void removeSelfAndDef() {
        getDef().forEach(v -> v.removeUse(this));
        removeSelf();
    }

    public boolean hasRet() {
        return false;
    }

    @Override
    public String getName() {
        return "val." + num;
    }

    @Override
    public void getClone(IRCloner c) {
        if (c.getClone(this) != null) {
            return;
        }
        if (getPrev() != null) {
            getPrev().get().getClone(c);
        }
        getDef().forEach(d -> d.getClone(c));
    }

    abstract public String getFullInst();

    @Override
    public String toString() {
        return "%val." + num;
    }

    @Override
    public ListNode<Inst> getPrev() {
        return prev;
    }

    @Override
    public ListNode<Inst> getNext() {
        return next;
    }

    @Override
    public void setPrev(ListNode<Inst> prev) {
        this.prev = prev == null ? null : prev.get();
    }

    @Override
    public void setNext(ListNode<Inst> next) {
        this.next = next == null ? null : next.get();
    }

    @Override
    public Inst get() {
        return this;
    }

    @Override
    public void removeSelf() {
        if (!use.isEmpty()) {
            throw new InternalError("remove used inst");
        }
        if (basicBlock != null) {
            basicBlock.instList.remove(this);
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
    public BasicBlock getParent() {
        return basicBlock;
    }

    @Override
    public void setParent(BasicBlock parent) {
        basicBlock = parent;
    }
}
