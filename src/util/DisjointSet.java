package util;

import java.util.HashMap;

public class DisjointSet<Ty> {
    HashMap<Ty, Ty> parent = new HashMap<>();

    public Ty get(Ty e) {
        parent.putIfAbsent(e, e);
        if (parent.get(e) == e) {
            return e;
        }
        Ty r = get(parent.get(e));
        parent.replace(e, r);
        return r;
    }

    public boolean join(Ty a, Ty b) {
        Ty ra = get(a), rb = get(b);
        parent.replace(a, rb);
        return ra != rb;
    }

    public boolean isJoined(Ty a, Ty b) {
        return get(a) == get(b);
    }
}
