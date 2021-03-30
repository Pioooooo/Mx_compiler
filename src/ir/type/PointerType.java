package ir.type;

import ir.Type;

import java.util.Objects;

public class PointerType extends Type {
    final Type pointeeType;
    final boolean isResolvable;

    public PointerType(Type pointeeTy, boolean isResolvable) {
        super(pointeeTy.m, TypeID.PointerTyID);
        this.pointeeType = pointeeTy;
        this.isResolvable = isResolvable;
    }

    public static PointerType get(Type pointeeTy, boolean isResolvable) {
        PointerType pointerTy = new PointerType(pointeeTy, isResolvable), entry = pointeeTy.m.pointerTypes.putIfAbsent(pointeeTy, pointerTy);
        return Objects.requireNonNullElse(entry, pointerTy);
    }

    @Override
    public int size() {
        return 32;
    }

    @Override
    public boolean isResolvable() {
        return isResolvable;
    }

    @Override
    public Type getBaseType() {
        return pointeeType;
    }

    @Override
    public String toString() {
        return pointeeType.toString() + "*";
    }
//    @Override
//    public int size() {
//        return 32;
//    }
//
//    @Override
//    public Type baseType() {
//        return baseType;
//    }
//
//    @Override
//    public Boolean equals(Type other) {
//        return (other instanceof PointerType && other.baseType().equals(this.baseType)) || other instanceof VoidType;//TODO:maybe
//    }
//
}
