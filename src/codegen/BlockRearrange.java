package codegen;

import asm.AsmBlock;
import asm.AsmFunction;
import asm.AsmRoot;
import asm.inst.Branch;
import asm.inst.J;
import asm.inst.Ret;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BlockRearrange {
    AsmRoot r;

    public BlockRearrange(AsmRoot r) {
        this.r = r;
    }

    public void run() {
        r.functions.values().stream().filter(f -> f.built).forEach(this::run);
    }

    void run(AsmFunction f) {
        HashSet<AsmBlock> fixed = new HashSet<>();
        ArrayList<AsmBlock> seq = new ArrayList<>();
        Queue<AsmBlock> worklist = new LinkedList<>();
        if (f.getHead().get() != null) {
            worklist.add(f.getHead().get());
        }
        while (!worklist.isEmpty()) {
            var b = worklist.poll();
            while (true) {
                if (fixed.contains(b)) {
                    break;
                }
                fixed.add(b);
                seq.add(b);
                var i = b.getTail().previous();
                if (i instanceof Ret) {
                    break;
                }
                if (i.getPrev() != null && i.getPrev().get() instanceof Branch) {
                    worklist.add(((Branch) i.getPrev().get()).dest);
                }
                b = ((J) i).dest;
            }
        }
        f.blocks.clear();
        seq.forEach(f.blocks::add);
    }
}
