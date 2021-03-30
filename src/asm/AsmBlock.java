package asm;

import util.list.List;
import util.list.ListIterator;
import util.list.ListNode;
import util.list.ListNodeWithParent;

import java.util.HashSet;
import java.util.Iterator;

public class AsmBlock implements ListNodeWithParent<AsmBlock, AsmFunction>, Iterable<AsmInst> {
    public String name = null;
    public List<AsmInst> instList = new List<>();
    public HashSet<AsmBlock> pre = new HashSet<>(), suc = new HashSet<>();
    AsmBlock prev, next;
    AsmFunction function;
    public int loopDepth;

    AsmBlock(int loopDepth, AsmFunction function, AsmBlock block) {
        this.loopDepth = loopDepth;
        if (function != null) {
            this.function = function;
            if (block != null) {
                function.blocks.insertBefore(this, block);
            } else {
                function.blocks.add(this);
            }
        }
    }

    public static AsmBlock create(int loopDepth, AsmFunction function, AsmBlock block) {
        return new AsmBlock(loopDepth, function, block);
    }

    public static AsmBlock create(int loopDepth, AsmFunction function) {
        return new AsmBlock(loopDepth, function, null);
    }

    public static AsmBlock create(int loopDepth) {
        return new AsmBlock(loopDepth, null, null);
    }

    public AsmBlock add(AsmInst inst) {
        instList.add(inst);
        return this;
    }

    public ListIterator<AsmInst> getHead() {
        return instList.getHead();
    }

    public ListIterator<AsmInst> getTail() {
        return instList.getTail();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Iterator<AsmInst> iterator() {
        return instList.iterator();
    }

    @Override
    public ListNode<AsmBlock> getPrev() {
        return prev;
    }

    @Override
    public ListNode<AsmBlock> getNext() {
        return next;
    }

    @Override
    public void setPrev(ListNode<AsmBlock> prev) {
        this.prev = prev.get();
    }

    @Override
    public void setNext(ListNode<AsmBlock> next) {
        this.next = next.get();
    }

    @Override
    public AsmBlock get() {
        return this;
    }

    @Override
    public AsmFunction getParent() {
        return function;
    }

    @Override
    public void setParent(AsmFunction function) {
        this.function = function;
    }
}
