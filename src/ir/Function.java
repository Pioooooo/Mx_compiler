package ir;

import asm.AsmFunction;
import ir.inst.AllocaInst;
import ir.inst.CallInst;
import ir.type.FunctionType;
import ir.values.Argument;
import util.IRCloner;
import util.list.List;
import util.list.ListIterator;
import util.list.ListNode;
import util.list.ListNodeWithParent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Function extends Value implements ListNodeWithParent<Function, Module> {
    Module module;
    Function prev, next;

    String name;
    ArrayList<Argument> args = new ArrayList<>();
    public List<BasicBlock> basicBlockList = new List<>();
    public HashSet<AllocaInst> allocas = new HashSet<>();
    public HashSet<CallInst> calls = new HashSet<>();
    public AsmFunction asmFunction;
    int blockCnt = 0;
    boolean noSideEffect = false;
    public boolean isBuiltIn = false;

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

    public HashSet<Function> getPre() {
        return use.stream().filter(u -> u instanceof CallInst).map(u -> u.getParent().getParent())
                .collect(Collectors.toCollection(HashSet::new));
    }

    public HashSet<Function> getSuc() {
        return calls.stream().filter(u -> !u.function.isBuiltIn).map(u -> u.function)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public boolean sameMeaning(Value other) {
        return false;
    }

    @Override
    public void getClone(IRCloner c) {
    }

    public void setHasSideEffect() {
        if (this.noSideEffect) {
            this.noSideEffect = false;
            use.forEach(u -> u.getParent().getParent().setHasSideEffect());
        }
    }

    public boolean noSideEffect() {
        return noSideEffect;
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

        ArrayList<Type> params = new ArrayList<>(((FunctionType) this.type).params);
        params.add(arg.type);
        this.type = FunctionType.get(getRetType(), params);
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
        this.prev = prev == null ? null : prev.get();
    }

    @Override
    public void setNext(ListNode<Function> next) {
        this.next = next == null ? null : next.get();
    }

    @Override
    public Function get() {
        return this;
    }

    @Override
    public void removeSelf() {
        if (module != null) {
            module.functions.remove(name);
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
    public Module getParent() {
        return module;
    }

    @Override
    public void setParent(Module parent) {
        this.module = parent;
    }

    @Override
    public HashSet<Value> getDef() {
        return new HashSet<>();
    }

    @Override
    public void replaceUse(Value o, Value n) {
    }
}
