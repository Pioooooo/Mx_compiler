package codegen;

import ir.BasicBlock;
import ir.Function;
import ir.Value;
import ir.inst.BrInst;
import ir.inst.PhiInst;

import java.util.HashMap;

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
        for (int i = 0; i < b.pre.size(); i++) {
            BasicBlock p = b.pre.get(i);
            if (p.suc.size() > 1) {
                BasicBlock t = BasicBlock.create(b.loopDepth, f.getParent(), f);
                p.replaceSuc(b, t);
                BrInst.create(b, t);
                b.pre.set(i, t);
                b.instList.forEach(inst -> {
                    if (inst instanceof PhiInst && ((PhiInst) inst).blocks.containsKey(p)) {
                        Value v = ((PhiInst) inst).blocks.get(p);
                        ((PhiInst) inst).blocks.put(t, v);
                        ((PhiInst) inst).blocks.remove(p);
                    }
                });
            }
        }
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
