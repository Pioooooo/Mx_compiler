package transforms.util;

import ir.BasicBlock;
import ir.Function;
import ir.Inst;
import ir.Value;
import ir.values.Constant;
import util.error.InternalError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

public class DominatorTree {
    Function f;

    public DominatorTree(Function f) {
        this.f = f;
    }

    BasicBlock root;
    ArrayList<BasicBlock> seq = new ArrayList<>();
    HashMap<BasicBlock, Integer> dfn = new HashMap<>();
    HashMap<BasicBlock, BasicBlock> semi = new HashMap<>();
    HashMap<BasicBlock, BasicBlock> iDom = new HashMap<>();
    HashMap<BasicBlock, BasicBlock> dsu = new HashMap<>();
    HashMap<BasicBlock, BasicBlock> val = new HashMap<>();
    HashMap<BasicBlock, BasicBlock> parent = new HashMap<>();
    HashMap<BasicBlock, ArrayList<BasicBlock>> domSon = new HashMap<>();
    HashMap<BasicBlock, ArrayList<BasicBlock>> domChildren = new HashMap<>();

    BasicBlock find(BasicBlock u) {
        if (u == dsu.get(u)) {
            return u;
        }
        BasicBlock v = find(dsu.get(u));
        if (dfn.get(semi.get(val.get(dsu.get(u)))) < dfn.get(semi.get(val.get(u)))) {
            val.put(u, val.get(dsu.get(u)));
        }
        dsu.put(u, v);
        return v;
    }

    void dfs(BasicBlock b) {
        dfn.put(b, seq.size());
        seq.add(b);
        val.put(b, b);
        semi.put(b, b);
        dsu.put(b, b);
        domSon.put(b, new ArrayList<>());
        domChildren.put(b, new ArrayList<>());
        b.suc.forEach(s -> {
            if (!dfn.containsKey(s)) {
                dfs(s);
                parent.put(s, b);
            }
        });
    }

    public void run() {
        root = f.getHead().get();
        dfs(root);
        for (int i = seq.size() - 1; i > 0; i--) {
            BasicBlock u = seq.get(i), p;
            u.pre.forEach(b -> {
                find(b);
                if (dfn.get(semi.get(val.get(b))) < dfn.get(semi.get(u))) {
                    semi.put(u, semi.get(val.get(b)));
                }
            });
            domSon.get(semi.get(u)).add(u);
            dsu.put(u, parent.get(u));
            p = parent.get(u);
            domSon.get(p).forEach(v -> {
                find(v);
                if (semi.get(val.get(v)) == p) {
                    iDom.put(v, p);
                } else {
                    iDom.put(v, val.get(v));
                }
            });
        }
        for (int i = 1; i < seq.size(); i++) {
            BasicBlock u = seq.get(i);
            if (iDom.get(u) != semi.get(u)) {
                iDom.put(u, iDom.get(iDom.get(u)));
            }
            domChildren.get(iDom.get(u)).add(u);
        }
        domFrontier = null;
        subDom = null;
    }

    HashMap<BasicBlock, HashSet<BasicBlock>> domFrontier;

    void calcDomFrontier() {
        domFrontier = new HashMap<>();
        seq.forEach(b -> domFrontier.put(b, new HashSet<>()));
        seq.stream().filter(b -> b.pre.size() > 1).forEach(b -> b.pre.forEach(a -> {
            BasicBlock x = a;
            while (x != iDom.get(b)) {
                domFrontier.get(x).add(b);
                x = iDom.get(x);
            }
        }));
    }

    public HashMap<BasicBlock, HashSet<BasicBlock>> getDomFrontier() {
        if (domFrontier == null) {
            calcDomFrontier();
        }
        return domFrontier;
    }

    HashMap<BasicBlock, HashSet<BasicBlock>> subDom;

    void calcSubDom() {
        subDom = new HashMap<>();
        seq.forEach(b -> subDom.put(b, new HashSet<>()));
        calcSubDom(seq.get(0));
    }

    void calcSubDom(BasicBlock b) {
        domChildren.get(b).forEach(s -> {
            calcSubDom(s);
            subDom.get(iDom.get(s)).addAll(subDom.get(s));
            subDom.get(iDom.get(s)).add(s);
        });
    }

    public HashMap<BasicBlock, HashSet<BasicBlock>> getSubDom() {
        if (subDom == null) {
            calcSubDom();
        }
        return subDom;
    }

    public boolean dominates(BasicBlock a, BasicBlock b) {
        if (subDom == null) {
            calcSubDom();
        }
        return subDom.get(a).contains(b);
    }

    public boolean dominates(Value a, Inst b) {
        if (a instanceof Constant) {
            return true;
        }
        BasicBlock block = b.getParent();
        if (((Inst) a).getParent() != block) {
            return dominates(((Inst) a).getParent(), block);
        }
        for (Inst i : block) {
            if (i == a) {
                return true;
            }
            if (i == b) {
                return false;
            }
        }
        throw new InternalError("unreachable");
    }

    public void forEach(Consumer<? super BasicBlock> action) {
        forEach(root, action);
    }

    void forEach(BasicBlock b, Consumer<? super BasicBlock> action) {
        action.accept(b);
        domChildren.get(b).forEach(c -> forEach(c, action));
    }
}
