package ir.type;

import ir.Module;
import ir.Type;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class StructType extends Type {
    final ArrayList<Type> elements;
    int allocSize = 0;
    public int num;

    StructType(Module m, ArrayList<Type> elements) {
        super(m, TypeID.StructTyID);
        this.elements = elements;
        elements.forEach(x -> allocSize += x.size());
    }

    public void addElement(Type type) {
        elements.add(type);
        allocSize += type.size();
    }

    public void insert() {
        m.structTypes.put(elements, this);
    }

    public int getOffset(int i) {
        return i * 32;
    }

    public String getFullString() {
        return "{ " + elements.stream().map(Type::toString).collect(Collectors.joining(", ")) + " }";
    }

    @Override
    public int size() {
        return 32;
    }

    @Override
    public int allocSize() {
        return allocSize;
    }

    public static StructType get(Module m, ArrayList<Type> elements) {
        StructType structTy = new StructType(m, elements), entry = m.structTypes.putIfAbsent(elements, structTy);
        if (entry == null) {
            structTy.num = m.structCnt++;
            return structTy;
        }
        return entry;
    }

    public static Type get(Module m, String typename) {
        StructType structTy = new StructType(m, new ArrayList<>());
        Type entry = m.namedStructTypes.putIfAbsent(typename, structTy);
        if (entry == null) {
            structTy.num = m.structCnt++;
            return structTy;
        }
        return entry;
    }

    @Override
    public String toString() {
        return "%struct." + num;
    }
}
