package ir;

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

    @Override
    public String getName() {
        return "val." + num;
    }

    protected Inst(Type type, BasicBlock basicBlock) {
        super(type);
        this.basicBlock = basicBlock;
        basicBlock.instList.insertBefore(basicBlock.getHead().get(), this);
    }

    protected Inst(Type type, Inst inst) {
        super(type);
        this.basicBlock = inst.get().basicBlock;
        basicBlock.instList.insertBefore(inst, this);
    }

    public boolean hasRet() {
        return false;
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
    public BasicBlock getParent() {
        return basicBlock;
    }

    @Override
    public void setParent(BasicBlock parent) {
        basicBlock = parent;
    }
}
