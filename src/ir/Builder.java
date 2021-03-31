package ir;

import ir.inst.*;
import ir.type.IntegerType;
import ir.values.ConstantInt;
import ir.values.GlobalPointer;
import ir.values.GlobalString;
import org.antlr.v4.runtime.misc.Pair;
import util.list.ListIterator;

import java.util.ArrayList;
import java.util.Collections;

public class Builder {
    BasicBlock bb;
    Module m;
    ListIterator<Inst> insertPoint;

    public Builder(Module m) {
        this.m = m;
    }

    public BasicBlock getInsertBlock() {
        return bb;
    }

    public Function getFunction() {
        return bb.getParent();
    }

    public void setInsertPoint(BasicBlock basicBlock) {
        bb = basicBlock;
        insertPoint = basicBlock.getTail();
    }

    public GlobalString getGlobalString(String val) {
        return GlobalString.get(m, val);
    }

    public GlobalPointer createGlobalVariable(Type type) {
        return GlobalPointer.create(Type.getPointerTy(type, true));
    }

    public ConstantInt getInt1(int val) {
        return ConstantInt.get(m, 1, val);
    }

    public ConstantInt getInt8(int val) {
        return ConstantInt.get(m, 8, val);
    }

    public ConstantInt getInt32(int val) {
        return ConstantInt.get(m, 32, val);
    }

    public ConstantInt getIntN(int n, int val) {
        return ConstantInt.get(m, n, val);
    }

    public Type getVoidTy() {
        return Type.getVoidTy(m);
    }

    public Type getLabelTy() {
        return Type.getLabelTy(m);
    }

    public IntegerType getIntNTy(int n) {
        return Type.getIntNTy(m, n);
    }

    public Value createPointerResolve(Value ptr) {
        if (ptr == null || !ptr.getType().isResolvable()) {
            return ptr;
        } else {
            return createLoad(ptr);
        }
    }

    public Value createAssign(Value ptr, Value val) {
        val = createPointerResolve(val);
        createStore(val, ptr);
        return ptr;
    }

    public CallInst createMalloc(Value size) {
        ArrayList<Value> args = new ArrayList<>();
        args.add(size);
        return CallInst.create(size.getContext().getBuiltinFunction("__g_malloc"), args, bb, insertPoint.get());
    }

    public PhiInst createPhi(Type type, ArrayList<Pair<BasicBlock, Value>> blocks) {
        return PhiInst.create(type, blocks, bb, insertPoint.get());
    }

    public PhiInst createPhi(Type type) {
        return PhiInst.create(type, new ArrayList<>(), bb, insertPoint.get());
    }

    public CallInst createCall(Function callee, ArrayList<Value> args) {
        return CallInst.create(callee, args, bb, insertPoint.get());
    }

    public RetInst createRet(Value val) {
        return RetInst.create(val, bb, insertPoint.get());
    }

    public RetInst createRet() {
        return RetInst.create(null, bb, insertPoint.get());
    }

    public BrInst createBr(BasicBlock dest) {
        return BrInst.create(dest, bb, insertPoint.get());
    }

    public Inst createCondBr(Value cond, BasicBlock trueDest, BasicBlock falseDest) {
        if (trueDest == falseDest) {
            return createBr(trueDest);
        }
        return BrInst.create(cond, trueDest, falseDest, bb, insertPoint.get());
    }

    public Binary createSAdd(Value lhs, Value rhs) {
        return Binary.create(lhs, rhs, Binary.OpType.add, bb, insertPoint.get());
    }

    public Binary createSSub(Value lhs, Value rhs) {
        return Binary.create(lhs, rhs, Binary.OpType.sub, bb, insertPoint.get());
    }

    public Binary createSMul(Value lhs, Value rhs) {
        return Binary.create(lhs, rhs, Binary.OpType.mul, bb, insertPoint.get());
    }

    public Binary createSDiv(Value lhs, Value rhs) {
        return Binary.create(lhs, rhs, Binary.OpType.sdiv, bb, insertPoint.get());
    }

    public Binary createSRem(Value lhs, Value rhs) {
        return Binary.create(lhs, rhs, Binary.OpType.srem, bb, insertPoint.get());
    }

    public Icmp createSGt(Value lhs, Value rhs) {
        return Icmp.create(lhs, rhs, Icmp.OpType.sgt, bb, insertPoint.get());
    }

    public Icmp createSLt(Value lhs, Value rhs) {
        return Icmp.create(lhs, rhs, Icmp.OpType.slt, bb, insertPoint.get());
    }

    public Icmp createSGe(Value lhs, Value rhs) {
        return Icmp.create(lhs, rhs, Icmp.OpType.sge, bb, insertPoint.get());
    }

    public Icmp createSLe(Value lhs, Value rhs) {
        return Icmp.create(lhs, rhs, Icmp.OpType.sle, bb, insertPoint.get());
    }

    public Icmp createINe(Value lhs, Value rhs) {
        return Icmp.create(lhs, rhs, Icmp.OpType.ne, bb, insertPoint.get());
    }

    public Icmp createIEq(Value lhs, Value rhs) {
        return Icmp.create(lhs, rhs, Icmp.OpType.eq, bb, insertPoint.get());
    }

    public Binary createShl(Value lhs, Value rhs) {
        return Binary.create(lhs, rhs, Binary.OpType.shl, bb, insertPoint.get());
    }

    public Binary createAshr(Value lhs, Value rhs) {
        return Binary.create(lhs, rhs, Binary.OpType.ashr, bb, insertPoint.get());
    }

    public Binary createAnd(Value lhs, Value rhs) {
        return Binary.create(lhs, rhs, Binary.OpType.and, bb, insertPoint.get());
    }

    public Binary createOr(Value lhs, Value rhs) {
        return Binary.create(lhs, rhs, Binary.OpType.or, bb, insertPoint.get());
    }

    public Binary createXor(Value lhs, Value rhs) {
        return Binary.create(lhs, rhs, Binary.OpType.xor, bb, insertPoint.get());
    }

    public AllocaInst createAlloca(Type type) {
        return AllocaInst.create(type, bb, insertPoint.get());
    }

    public AllocaInst createEntryBlockAlloca(Type type) {
        return AllocaInst.create(type, getFunction().getHead().get());
    }

    public LoadInst createLoad(Value ptr) {
        return LoadInst.create(ptr, bb, insertPoint.get());
    }

    public StoreInst createStore(Value val, Value ptr) {
        return StoreInst.create(val, ptr, bb, insertPoint.get());
    }

    public StoreInst createStore(Value val, Value ptr, Inst inst) {
        return StoreInst.create(val, ptr, inst);
    }

    public GetElementPtrInst createGEP(Type type, Value ptr, ArrayList<Value> idxList) {
        return GetElementPtrInst.create(type, ptr, idxList, bb, insertPoint.get());
    }

    public GetElementPtrInst createGEP(Value ptr, Value idx) {
        return GetElementPtrInst.create(ptr.getType(), ptr, new ArrayList<>(Collections.singletonList(idx)), bb, insertPoint.get());
    }

    public BitCastInst createBitCast(Value val, Type type) {
        return BitCastInst.create(val, type, bb, insertPoint.get());
    }
}
