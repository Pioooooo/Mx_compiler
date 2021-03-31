package asm;

import asm.operand.Register;
import asm.operand.VReg;
import util.list.List;
import util.list.ListIterator;
import util.list.ListNode;
import util.list.ListNodeWithParent;

import java.util.ArrayList;

public class AsmFunction implements ListNodeWithParent<AsmFunction, AsmRoot> {
    public String name;
    public ArrayList<Register> args = new ArrayList<>();
    public ArrayList<VReg> calleeSaveVReg = new ArrayList<>();
    public VReg raSaveVReg = null;
    public boolean built = false;
    public List<AsmBlock> blocks = new List<>();
    public int spOffset = 0;

    AsmFunction prev, next;
    AsmRoot root;
    ArrayList<AsmBlock> tails = new ArrayList<>();

    AsmFunction(String name) {
        this.name = name;
    }

    public static AsmFunction create(String name) {
        return new AsmFunction(name);
    }

    public ListIterator<AsmBlock> getHead() {
        return blocks.getHead();
    }

    public ArrayList<AsmBlock> getTail() {
        return tails;
    }

    public void addTail(AsmBlock tail) {
        tails.add(tail);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public ListNode<AsmFunction> getPrev() {
        return prev;
    }

    @Override
    public ListNode<AsmFunction> getNext() {
        return next;
    }

    @Override
    public void setPrev(ListNode<AsmFunction> prev) {
        this.prev = prev.get();
    }

    @Override
    public void setNext(ListNode<AsmFunction> next) {
        this.next = next.get();
    }

    @Override
    public AsmFunction get() {
        return this;
    }

    @Override
    public AsmRoot getParent() {
        return root;
    }

    @Override
    public void setParent(AsmRoot root) {
        this.root = root;
    }
}
