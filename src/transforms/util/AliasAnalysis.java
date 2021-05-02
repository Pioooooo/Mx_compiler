package transforms.util;

import ir.Module;
import ir.*;
import ir.inst.CallInst;
import ir.inst.LoadInst;
import ir.inst.StoreInst;
import ir.values.GlobalPointer;
import util.DisjointSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class AliasAnalysis {
    Module m;

    public AliasAnalysis(Module m) {
        this.m = m;
    }

    DisjointSet<Value> faPtr, faData;
    HashMap<Function, HashSet<Value>> funcPtr, funcData, funcPtrS, funcDataS;

    public void run() {
        faPtr = new DisjointSet<>();
        faData = new DisjointSet<>();
        funcPtr = new HashMap<>();
        funcData = new HashMap<>();
        funcPtrS = new HashMap<>();
        funcDataS = new HashMap<>();
        m.functions.values().forEach(f -> {
            funcPtr.put(f, new HashSet<>());
            funcData.put(f, new HashSet<>());
            funcPtrS.put(f, new HashSet<>());
            funcDataS.put(f, new HashSet<>());
        });
        m.functions.values().forEach(f -> f.basicBlockList.forEach(block -> block.forEach(inst -> {
            if (inst.getType().isPointer()) {
                if (inst instanceof CallInst && !((CallInst) inst).function.isBuiltIn) {
                    ((CallInst) inst).function.rets.forEach(r -> {
                        joinPtr(inst, r);
                        joinData(inst, r);
                    });
                } else {
                    inst.getDef().forEach(d -> {
                        joinPtr(inst, d);
                        joinData(inst, d);
                    });
                }
            }
            if (inst instanceof CallInst && !((CallInst) inst).function.isBuiltIn) {
                CallInst c = (CallInst) inst;
                for (int i = 0; i < c.args.size(); i++) {
                    Value a = c.args.get(i), b = c.function.getArg(i);
                    joinPtr(a, b);
                    joinData(a, b);
                }
            } else if (inst instanceof LoadInst) {
                LoadInst l = (LoadInst) inst;
                if (l.ptr.getType().isMemoryPointer()) {
                    funcPtr.get(f).add((l.ptr));
                } else {
                    funcData.get(f).add((l.ptr));
                }
            } else if (inst instanceof StoreInst) {
                StoreInst s = (StoreInst) inst;
                if (s.ptr.getType().isMemoryPointer()) {
                    funcPtr.get(f).add(s.ptr);
                    funcPtrS.get(f).add(s.ptr);
                } else {
                    funcData.get(f).add(s.ptr);
                    funcDataS.get(f).add(s.ptr);
                }
            }
        })));
        funcPtr.values().forEach(s -> {
            HashSet<Value> t = s.stream().map(x -> faPtr.get(x)).collect(Collectors.toCollection(HashSet::new));
            s.clear();
            s.addAll(t);
        });
        funcData.values().forEach(s -> {
            HashSet<Value> t = s.stream().map(x -> faData.get(x)).collect(Collectors.toCollection(HashSet::new));
            s.clear();
            s.addAll(t);
        });
        funcPtrS.values().forEach(s -> {
            HashSet<Value> t = s.stream().map(x -> faPtr.get(x)).collect(Collectors.toCollection(HashSet::new));
            s.clear();
            s.addAll(t);
        });
        funcDataS.values().forEach(s -> {
            HashSet<Value> t = s.stream().map(x -> faData.get(x)).collect(Collectors.toCollection(HashSet::new));
            s.clear();
            s.addAll(t);
        });
        var ref = new Object() {
            boolean added = true;
        };
        while (ref.added) {
            ref.added = false;
            funcPtr.forEach((f, s) -> f.getSuc().forEach(b -> ref.added |= s.addAll(funcPtr.get(b))));
            funcData.forEach((f, s) -> f.getSuc().forEach(b -> ref.added |= s.addAll(funcData.get(b))));
            funcPtrS.forEach((f, s) -> f.getSuc().forEach(b -> ref.added |= s.addAll(funcPtrS.get(b))));
            funcDataS.forEach((f, s) -> f.getSuc().forEach(b -> ref.added |= s.addAll(funcDataS.get(b))));
        }
    }

    void joinPtr(Value a, Value b) {
        if (a.getType().isMemoryPointer() && b.getType().isMemoryPointer()) {
            faPtr.join(a, b);
        }
    }

    void joinData(Value a, Value b) {
        if (a.getType().isPointer() && b.getType().isPointer()) {
            faData.join(a, b);
        }
    }

    public boolean mayConflictPtr(Value a, Value b) {
        if (a instanceof GlobalPointer || b instanceof GlobalPointer) {
            return a == b;
        }
        return faPtr.isJoined(a, b);
    }

    public boolean mayConflictData(Value a, Value b) {
        if (a instanceof GlobalPointer || b instanceof GlobalPointer) {
            return a == b;
        }
        return faData.isJoined(a, b);
    }

    public boolean funcConflict(Function f, Value x) {
        if (f.isBuiltIn) {
            return false;
        }
        if (x.getType().isMemoryPointer()) {
            return funcPtr.get(f).contains(faPtr.get(x));
        } else {
            Value t = faData.get(x);
            return funcPtr.get(f).contains(t) || funcData.get(f).contains(t);
        }
    }

    public boolean funcConflictS(Function f, Value x) {
        if (f.isBuiltIn) {
            return false;
        }
        if (x.getType().isMemoryPointer()) {
            return funcPtrS.get(f).contains(faPtr.get(x));
        } else {
            Value t = faData.get(x);
            return funcPtrS.get(f).contains(t) || funcDataS.get(f).contains(t);
        }
    }

    public boolean funcHavePtr(Function f, Value x) {
        if (f.isBuiltIn) {
            return false;
        }
        return funcPtr.get(f).contains(faPtr.get(x));
    }

    public boolean funcHaveData(Function func, Value x) {
        if (func.isBuiltIn) {
            return false;
        }
        Value t = faData.get(x);
        return funcPtr.get(func).contains(t) || funcData.get(func).contains(t);
    }

    public boolean noConflict(HashSet<BasicBlock> blocks, LoadInst inst) {
        boolean isMemory = inst.getType().isMemoryPointer();
        for (BasicBlock b : blocks) {
            for (Inst i : b) {
                if (i instanceof CallInst && funcConflictS(((CallInst) i).function, inst.ptr)) {
                    return false;
                } else if (i instanceof StoreInst) {
                    if (mayConflictData(((StoreInst) i).ptr, inst.ptr)) {
                        return false;
                    }
                    if (isMemory && ((StoreInst) i).ptr.getType().isMemoryPointer() && mayConflictPtr(((StoreInst) i).ptr, inst.ptr)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
