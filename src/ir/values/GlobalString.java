package ir.values;

import ir.Module;
import ir.Type;
import ir.type.PointerType;

public class GlobalString extends Constant {
    final public String val;

    GlobalString(Module m, String val) {
        super(PointerType.get(Type.getArrayTy(Type.getIntNTy(m, 8), val.length()), true));
        this.val = val;
    }

    public String getIRString() {
        return val.replace("\\", "\\5m")
                .replace("\n", "\\0A")
                .replace("\0", "\\00")
                .replace("\t", "\\09")
                .replace("\"", "\\22");
    }

    public static GlobalString get(Module m, String val) {
        GlobalString ptr = new GlobalString(m, val.replace("\"", "") + '\0'), entry = m.constantStrings.putIfAbsent(val, ptr);
        if (entry == null) {
            ptr.num = m.structCnt++;
            return ptr;
        }
        return entry;
    }

    @Override
    public String getName() {
        return ".str." + num;
    }

    @Override
    public String toString() {
        return "@.str." + num;
    }
}
