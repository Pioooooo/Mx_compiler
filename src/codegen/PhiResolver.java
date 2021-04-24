package codegen;

import ir.BasicBlock;
import ir.Function;
import ir.Value;
import ir.inst.BrInst;
import ir.inst.PhiInst;

import java.util.HashMap;
import java.util.HashSet;

public class PhiResolver {
    Function f;

    PhiResolver(Function f) {
        this.f = f;
    }

    public HashMap<BasicBlock, HashMap<PhiInst, Value>> pCopy = new HashMap<>();

    public void run() {
        f.basicBlockList.forEach(this::run);
    }

    public HashMap<PhiInst, Value> getPCopy(BasicBlock b) {
        return pCopy.getOrDefault(b, new HashMap<>());
    }

    void run(BasicBlock b) {
        if (b.instList.stream().noneMatch(i -> i instanceof PhiInst)) {
            return;
        }
        HashSet<BasicBlock> remove = new HashSet<>(), add = new HashSet<>();
        b.pre.stream().filter(p -> p.suc.size() > 1).forEach(p -> {
            BasicBlock t = BasicBlock.create(b.loopDepth, f.getParent(), f);
            p.getTail().previous().replaceUse(b, t);
            t.pre.add(p);
            remove.add(p);
            p.suc.remove(b);
            p.suc.add(t);
            add.add(t);
            b.instList.forEach(inst -> {
                if (inst instanceof PhiInst && ((PhiInst) inst).blocks.containsKey(p)) {
                    Value v = ((PhiInst) inst).blocks.get(p);
                    ((PhiInst) inst).blocks.put(t, v);
                    ((PhiInst) inst).blocks.remove(p);
                }
            });
        });
        b.pre.removeAll(remove);
        add.forEach(a -> BrInst.create(b, a));
        b.instList.forEach(i -> {
            if (i instanceof PhiInst) {
                ((PhiInst) i).blocks.forEach((k, v) -> addPCopy(k, (PhiInst) i, v));
            }
        });
    }

    void addPCopy(BasicBlock b, PhiInst p, Value v) {
        pCopy.computeIfAbsent(b, k -> new HashMap<>()).put(p, v);
    }
}
