package ir;

import asm.AsmFunction;
import ir.inst.AllocaInst;
import ir.values.Argument;
import ir.type.FunctionType;
import util.list.List;
import util.list.ListIterator;
import util.list.ListNode;
import util.list.ListNodeWithParent;

import java.util.ArrayList;
import java.util.HashSet;

public class Function extends Value implements ListNodeWithParent<Function, Module> {
    Module module;
    ArrayList<Argument> args = new ArrayList<>();
    public List<BasicBlock> basicBlockList = new List<>();
    public HashSet<AllocaInst> allocas = new HashSet<>();
    String name;
    Function prev, next;
    public AsmFunction asmFunction;
    int blockCnt = 0;

    Function(FunctionType functionType, Module module, String name) {
        super(functionType);
        if (module != null) {
            this.module = module;
            if (name != null) {
                module.addFunction(name, this);
            }
        }
    }

    public static Function create(FunctionType functionType, Module module, String name) {
        return new Function(functionType, module, name);
    }

    public static Function create(FunctionType functionType, String name) {
        return new Function(functionType, null, name);
    }

    public static Function create(FunctionType functionType, Module module) {
        return new Function(functionType, module, null);
    }

    public static Function create(FunctionType functionType) {
        return new Function(functionType, null, null);
    }

    @Override
    public String toString() {
        return "@" + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean addArg(Argument arg) {
        return args.add(arg);
    }

    public Argument addArg(Type type) {
        Argument arg = new Argument(type, this, args.size());
        args.add(arg);
        ((FunctionType) this.type).add(type);
        return arg;
    }

    public ArrayList<Argument> getArgs() {
        return args;
    }

    public Argument getArg(int index) {
        return args.get(index);
    }

    public Argument setArg(int index, Argument arg) {
        return args.set(index, arg);
    }

    public Value getClassPtr() {
        return getArg(0);
    }

    public Type getRetType() {
        return ((FunctionType) type).retType;
    }

    public ListIterator<BasicBlock> getHead() {
        return basicBlockList.getHead();
    }

    public ListIterator<BasicBlock> getTail() {
        return basicBlockList.getTail();
    }

    @Override
    public ListNodeWithParent<Function, Module> getPrev() {
        return prev;
    }

    @Override
    public ListNodeWithParent<Function, Module> getNext() {
        return next;
    }

    @Override
    public void setPrev(ListNode<Function> prev) {
        this.prev = prev.get();
    }

    @Override
    public void setNext(ListNode<Function> next) {
        this.next = next.get();
    }

    @Override
    public Function get() {
        return this;
    }

    @Override
    public Module getParent() {
        return module;
    }

    @Override
    public void setParent(Module parent) {
        this.module = parent;
    }
}
