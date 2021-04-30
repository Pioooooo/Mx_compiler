package transforms;

import ir.Module;
import ir.*;
import ir.inst.BrInst;
import ir.inst.CallInst;
import ir.inst.PhiInst;
import util.IRCloner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Inliner {
    Module m;

    public Inliner(Module m) {
        this.m = m;
    }

    static int maxBlockCnt = 50, maxInstCnt = 500;

    void run() {
        HashSet<Function> inlined = new HashSet<>();
        Queue<Function> worklist = new LinkedList<>();
        inlined.add(m.getFunction("main"));
        m.functions.values().stream().filter(f -> f.getSuc().isEmpty() && !f.getName().equals("main"))
                .forEach(worklist::add);
        while (!worklist.isEmpty()) {
            Function f = worklist.poll();
            inlined.add(f);
            HashSet<Inst> u = new HashSet<>(f.use);
            u.forEach(i -> {
                inline((CallInst) i);
                Function p = i.getParent().getParent();
                if (!inlined.contains(p) && p.getSuc().isEmpty()) {
                    worklist.add(p);
                }
            });
        }
    }

    void inline(CallInst c) {
        BasicBlock callBlock = c.getParent();
        Function caller = callBlock.getParent(), callee = c.function;
        int instCnt = 0, blockCnt = 0;
        for (BasicBlock basicBlock : callee.basicBlockList) {
            blockCnt++;
            for (Inst ignored : basicBlock) {
                instCnt++;
            }
        }
        if (blockCnt > maxBlockCnt || instCnt > maxInstCnt) {
            return;
        }
        IRCloner cloner = new IRCloner(IntStream.range(0, c.args.size()).boxed().
                collect(Collectors.toMap(callee::getArg, i -> c.args.get(i), (a, b) -> b, HashMap::new)));
        int loopDepth = callBlock.loopDepth;
        callee.basicBlockList.forEach(b -> cloner.setClone(b, BasicBlock.create(b.loopDepth + loopDepth, m, caller)));
        BasicBlock entry = cloner.getClone(callee.getHead().get()), exit = BasicBlock.create(loopDepth, m, caller);
        cloner.setClone(callee, exit);
        PhiInst phi = null;
        if (!callee.getRetType().isVoid()) {
            c.replaceUseWith(phi = PhiInst.create(callee.getRetType(), exit));
        }
        callee.basicBlockList.forEach(b -> b.forEach(i -> i.getClone(cloner)));
        cloner.phi.forEach(p -> p.blocks.forEach((b, v) -> ((PhiInst) cloner.getClone(p)).addIncoming(cloner.getClone(b), cloner.getClone(v))));
        exit.suc.clear();
        exit.suc.addAll(callBlock.suc);
        callBlock.suc.forEach(s -> {
            s.pre.remove(callBlock);
            s.pre.add(exit);
        });
        callBlock.suc.clear();
        callBlock.use.stream().filter(i -> i instanceof PhiInst).forEach(i -> {
            i.replaceUse(callBlock, exit);
            exit.addUse(i);
        });
        callBlock.use.removeIf(i -> i instanceof PhiInst);
        callBlock.instList.splitTo(exit.instList, c);
        exit.forEach(i -> i.setParent(exit));
        callBlock.unTerminate();
        exit.terminate();
        c.removeSelfAndDef();
        BrInst.create(entry, callBlock, callBlock.getTail().get());
        if (phi != null) {
            Value v = phi.simplify();
            if (v != null) {
                phi.replaceUseWith(v);
                phi.removeSelfAndDef();
            }
        }
    }
}
