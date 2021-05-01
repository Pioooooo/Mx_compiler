package transforms;

import ir.Module;
import ir.*;
import ir.inst.BrInst;
import ir.inst.CallInst;
import ir.inst.PhiInst;
import util.IRCloner;
import util.list.List;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Inliner {
    Module m;

    public Inliner(Module m) {
        this.m = m;
    }

    static int maxBlockCnt = 50, maxInstCnt = 500;

    static class CloneData {
        IRCloner cloner;
        BasicBlock entry, exit;
        PhiInst ret;

        CloneData(CallInst c, List<BasicBlock> blockList) {
            BasicBlock callBlock = c.getParent();
            Function caller = callBlock.getParent(), callee = c.function;
            cloner = new IRCloner(IntStream.range(0, c.args.size()).boxed().
                    collect(Collectors.toMap(callee::getArg, i -> c.args.get(i), (a, b) -> b, HashMap::new)));
            int loopDepth = callBlock.loopDepth;
            blockList.forEach(b -> cloner.setClone(b, BasicBlock.create(b.loopDepth + loopDepth, c.getContext(), caller)));
            entry = cloner.getClone(callee.getHead().get());
            exit = BasicBlock.create(loopDepth, c.getContext(), caller);
            cloner.setClone(callee, exit);
            if (!callee.getRetType().isVoid()) {
                ret = PhiInst.create(callee.getRetType(), exit);
            }
            blockList.forEach(b -> b.forEach(i -> i.getClone(cloner)));
            cloner.phi.forEach(p -> p.blocks.forEach((b, v) -> ((PhiInst) cloner.getClone(p)).addIncoming(cloner.getClone(b), cloner.getClone(v))));
        }

        CloneData(CallInst c) {
            this(c, c.function.basicBlockList);
        }
    }

    void run() {
        HashSet<Function> inlined = new HashSet<>();
        Queue<Function> worklist = new LinkedList<>();
        inlined.add(m.getFunction("main"));
        m.functions.values().stream().filter(f -> f.getSuc().isEmpty() && !f.getName().equals("main"))
                .forEach(worklist::add);
        while (!worklist.isEmpty()) {
            Function f = worklist.poll();
            inlined.add(f);
            int instCnt = 0, blockCnt = 0;
            for (BasicBlock b : f.basicBlockList) {
                blockCnt++;
                for (Inst ignored : b) {
                    instCnt++;
                }
            }
            if (blockCnt > maxBlockCnt || instCnt > maxInstCnt) {
                continue;
            }
            HashSet<Inst> u = new HashSet<>(f.use);
            HashMap<CallInst, CloneData> clones = u.stream().filter(i -> {
                BasicBlock callBlock = i.getParent();
                Function caller = callBlock.getParent(), callee = ((CallInst) i).function;
                caller.infiniteLoop |= callee.infiniteLoop;
                return caller.doOptimize();
            }).collect(Collectors.toMap(i -> (CallInst) i, i -> new CloneData((CallInst) i), (a, b) -> b, HashMap::new));
            clones.forEach((c, d) -> {
                inline(c, d);
                Function p = c.getParent().getParent();
                if (!inlined.contains(p) && p.getSuc().isEmpty()) {
                    worklist.add(p);
                }
            });
        }
        m.functions.values().stream().filter(f -> f.getSuc().contains(f)).forEach(f -> {
            for (int i = 0; i < 10; i++) {
                int blockCnt = 0, instCnt = 0;
                for (BasicBlock b : f.basicBlockList) {
                    blockCnt++;
                    for (Inst ignored : b) {
                        instCnt++;
                    }
                }
                if (blockCnt > maxBlockCnt || instCnt > maxInstCnt) {
                    break;
                }
                var calls = f.use.stream().map(u -> (CallInst) u)
                        .collect(Collectors.toCollection(ArrayList::new));
                var blockList = new List<>(f.basicBlockList);
                HashMap<CallInst, CloneData> clones = calls.stream()
                        .collect(Collectors.toMap(c -> c, c -> new CloneData(c, blockList), (a, b) -> b, HashMap::new));
                clones.forEach(this::inline);
            }
        });
    }

    void inline(CallInst c, CloneData d) {
        BasicBlock callBlock = c.getParent(), entry = d.entry, exit = d.exit;
        PhiInst ret = d.ret;
        if (ret != null) {
            c.replaceUseWith(ret);
            Value v = ret.simplify();
            if (v != null) {
                ret.replaceUseWith(v);
                ret.removeSelfAndDef();
            }
        }
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
    }
}
