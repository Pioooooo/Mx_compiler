package ir.values;

import ir.Module;
import ir.Type;
import ir.type.PointerType;

public class GlobalString extends Constant {
    final public String val, src;

    GlobalString(Module m, String val, String src) {
        super(PointerType.get(Type.getArrayTy(Type.getIntNTy(m, 8), val.length()), false));
        this.val = val;
        this.src = src;
    }

    public String getIRString() {
        return src.substring(1, src.length() - 1)
                .replace("\\\\", "\\5C")
                .replace("\\n", "\\0A")
                .replace("\\t", "\\09")
                .replace("\\\"", "\\22") + "\\00";
    }

    public static GlobalString get(Module m, String src) {
        GlobalString ptr = new GlobalString(m, src.substring(1, src.length() - 1)
                .replace("\\\\", "\\")
                .replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\\"", "\"") + '\0',
                src), entry = m.constantStrings.putIfAbsent(src, ptr);
        if (entry == null) {
            ptr.num = m.structCnt++;
            return ptr;
        }
        return entry;
    }

    public static GlobalString getFromVal(Module m, String val) {
        return get(m, "\"" + val.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\t", "\\t")
                .replace("\"", "\\\"") + "\"");
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
