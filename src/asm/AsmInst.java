package asm;

import asm.operand.Register;
import util.list.ListNode;
import util.list.ListNodeWithParent;

import java.util.HashSet;

abstract public class AsmInst implements ListNodeWithParent<AsmInst, AsmBlock> {
    AsmInst prev, next;
    public AsmBlock block;

    protected AsmInst(AsmBlock block) {
        this.block = block;
        block.instList.add(this);
    }

    protected AsmInst(AsmInst inst) {
        this.block = inst.block;
        block.instList.insertBefore(inst, this);
    }

    public HashSet<Register> getUse() {
        return new HashSet<>();
    }

    public HashSet<Register> getDef() {
        return new HashSet<>();
    }

    public void replaceUse(Register a, Register b) {
    }

    public void replaceDef(Register a, Register b) {
    }

    @Override
    public ListNode<AsmInst> getPrev() {
        return prev;
    }

    @Override
    public ListNode<AsmInst> getNext() {
        return next;
    }

    @Override
    public void setPrev(ListNode<AsmInst> prev) {
        this.prev = prev == null ? null : prev.get();
    }

    @Override
    public void setNext(ListNode<AsmInst> next) {
        this.next = next == null ? null : next.get();
    }

    @Override
    public AsmInst get() {
        return this;
    }

    @Override
    public AsmBlock getParent() {
        return block;
    }

    @Override
    public void setParent(AsmBlock block) {
        this.block = block;
    }
}
