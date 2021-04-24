package ir;

import ast.AstVisitor;
import ast.Nodes.*;
import frontend.scope.Scope;
import frontend.symbol.type.ArrayType;
import ir.type.FunctionType;
import ir.type.StructType;
import ir.values.Argument;
import ir.values.ConstantPointerNull;
import util.error.InternalError;

import java.util.ArrayList;

public class IRBuilder implements AstVisitor<Value> {
    final Scope globalScope;
    final Module m;
    final Builder builder;
    StructType classType;
    int loopDepth = 0;
    Function initFunction, currentFunction;

    public IRBuilder(Scope globalScope, Module m) {
        this.globalScope = globalScope;
        this.m = m;
        this.builder = new Builder(m);
        initFunction = Function.create(FunctionType.get(Type.getVoidTy(m)));
        m.addFunction("__g_init", initFunction);
        BasicBlock.create(0, m, initFunction);
        Function function = Function.create(FunctionType.get(Type.getVoidTy(m)));
        function.addArg(m.stringTy);
        m.addBuiltinFunction("g_print", function);
        function = Function.create(FunctionType.get(Type.getVoidTy(m)));
        function.addArg(m.stringTy);
        m.addBuiltinFunction("g_println", function);
        function = Function.create(FunctionType.get(Type.getVoidTy(m)));
        function.addArg(m.int32Ty);
        m.addBuiltinFunction("g_printInt", function);
        function = Function.create(FunctionType.get(Type.getVoidTy(m)));
        function.addArg(m.int32Ty);
        m.addBuiltinFunction("g_printlnInt", function);
        function = Function.create(FunctionType.get(m.stringTy));
        m.addBuiltinFunction("g_getString", function);
        function = Function.create(FunctionType.get(m.int32Ty));
        m.addBuiltinFunction("g_getInt", function);
        function = Function.create(FunctionType.get(m.stringTy));
        function.addArg(m.int32Ty);
        m.addBuiltinFunction("g_toString", function);
        function = Function.create(FunctionType.get(m.stringTy));
        function.addArg(m.int32Ty);
        m.addBuiltinFunction("__g_malloc", function);
        ArrayList<Type> arg2Str = new ArrayList<>();
        arg2Str.add(m.stringTy);
        arg2Str.add(m.stringTy);
        function = Function.create(FunctionType.get(m.stringTy, arg2Str));
        m.addBuiltinFunction("__g_str_add", function);
        function = Function.create(FunctionType.get(m.int1Ty, arg2Str));
        m.addBuiltinFunction("__g_str_gt", function);
        function = Function.create(FunctionType.get(m.int1Ty, arg2Str));
        m.addBuiltinFunction("__g_str_lt", function);
        function = Function.create(FunctionType.get(m.int1Ty, arg2Str));
        m.addBuiltinFunction("__g_str_ge", function);
        function = Function.create(FunctionType.get(m.int1Ty, arg2Str));
        m.addBuiltinFunction("__g_str_le", function);
        function = Function.create(FunctionType.get(m.int1Ty, arg2Str));
        m.addBuiltinFunction("__g_str_ne", function);
        function = Function.create(FunctionType.get(m.int1Ty, arg2Str));
        m.addBuiltinFunction("__g_str_eq", function);
        function = Function.create(FunctionType.get(m.int32Ty));
        function.addArg(m.stringTy);
        m.addBuiltinFunction("c_string_length", function);
        function = Function.create(FunctionType.get(m.stringTy));
        function.addArg(m.stringTy);
        function.addArg(m.int32Ty);
        function.addArg(m.int32Ty);
        m.addBuiltinFunction("c_string_substring", function);
        function = Function.create(FunctionType.get(m.int32Ty));
        function.addArg(m.stringTy);
        m.addBuiltinFunction("c_string_parseInt", function);
        function = Function.create(FunctionType.get(m.int32Ty));
        function.addArg(m.stringTy);
        function.addArg(m.int32Ty);
        m.addBuiltinFunction("c_string_ord", function);
    }

    public Value createArrayMalloc(Type returnType, NewExprNode n, int dim) {
        Type elementType = returnType.getBaseType();
        Value size = builder.createPointerResolve(n.sizes.get(dim).accept(this)),
                dataSize = builder.createSMul(size, builder.getInt32(elementType.size() / 8)),
                arraySize = builder.createSAdd(dataSize, builder.getInt32(m.int32Ty.size() / 8)),
                arrayPtr = builder.createMalloc(arraySize);
        arrayPtr = builder.createBitCast(arrayPtr, Type.getPointerTy(m.int32Ty, true));
        builder.createStore(size, arrayPtr);
        arrayPtr = builder.createGEP(arrayPtr, builder.getInt32(1));
        arrayPtr = builder.createBitCast(arrayPtr, returnType);
        if (dim < n.sizes.size() - 1) {
            loopDepth++;
            Function function = builder.getFunction();
            BasicBlock bodyBlock = BasicBlock.create(loopDepth, m, function),
                    destBlock = BasicBlock.create(loopDepth, m, function);
            Value i = builder.createEntryBlockAlloca(m.int32Ty);
            builder.createAssign(i, builder.getInt32(0));
            builder.createBr(bodyBlock);
            builder.setInsertPoint(bodyBlock);
            Value iVal = builder.createPointerResolve(i),
                    iPtr = builder.createGEP(arrayPtr, iVal),
                    iItem = createArrayMalloc(elementType, n, dim + 1);
            builder.createStore(iItem, iPtr);
            iVal = builder.createSAdd(iVal, builder.getInt32(1));
            builder.createStore(iVal, i);
            Value cmp = builder.createSLt(iVal, size);
            builder.createCondBr(cmp, bodyBlock, destBlock);
            builder.setInsertPoint(destBlock);
            loopDepth--;
        }
        return arrayPtr;
    }

    Value branchAdd(ExprNode n, Value v) {
        if (n.ptr != null) {
            return builder.createAssign(n.ptr, v);
        }
        if (n.thenBlock == null) {
            return v;
        } else {
            builder.createCondBr(builder.createPointerResolve(v), n.thenBlock, n.elseBlock);
            return null;
        }
    }

    @Override
    public Value visit(RootNode n) {
        n.stmts.forEach(x -> x.accept(this));
        builder.setInsertPoint(initFunction.getTail().previous());
        builder.createRet();
        return null;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public Value visit(BlockStmtNode n) {
        n.stmts.stream().filter(x -> {
            x.accept(this);
            return builder.getInsertBlock().isTerminated();
        }).findFirst();
        return null;
    }

    @Override
    public Value visit(VarDefStmtNode n) {
        if (n.type instanceof ClassTypeNode) {
            n.type.accept(this);
        }
        n.defList.forEach(x -> {
            if (currentFunction == null) {
                x.entity.setValue(builder.createGlobalVariable(x.entity.type().irType(m)));
                if (x.initVal != null) {
                    builder.setInsertPoint(initFunction);
                    x.initVal.ptr = x.entity.value();
                    builder.createAssign(x.entity.value(), x.initVal.accept(this));
                }
            } else {
                x.entity.setValue(builder.createEntryBlockAlloca(x.entity.type().irType(m)));
                if (x.initVal != null) {
                    x.initVal.ptr = x.entity.value();
                    builder.createAssign(x.entity.value(), x.initVal.accept(this));
                }
            }

        });
        return null;
    }

    @Override
    public Value visit(VarDefSubNode n) {
        return null;
    }

    @Override
    public Value visit(ExprStmtNode n) {
        if (n.expr != null) {
            n.expr.accept(this);
        }
        return null;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public Value visit(FuncDefStmtNode n) {
        Function function = currentFunction = m.getFunction(n.funcName);
        builder.setInsertPoint(BasicBlock.create(loopDepth, m, function));
        if (n.funcName.equals("main")) {
            builder.createCall(initFunction, new ArrayList<>());
        }
        n.funcBody.stream().filter(x -> {
            x.accept(this);
            return builder.getInsertBlock().isTerminated();
        }).findFirst();
        function.basicBlockList.forEach(b -> {
            if (!b.isTerminated()) {
                if (n.funcName.equals("main")) {
                    builder.createRet(builder.getInt32(0), b, b.getTail().get());
                } else if (n.function.getRetType().isVoid()) {
                    builder.createRet(null, b, b.getTail().get());
                } else if (n.isConstructor) {
                    builder.createRet(function.getClassPtr());
                }
            }
        });
        currentFunction = null;
        return null;
    }

    @Override
    public Value visit(ParamDefSubNode n) {
        return null;
    }

    @Override
    public Value visit(IfStmtNode n) {
        Function function = builder.getFunction();
        BasicBlock thenBlock = BasicBlock.create(loopDepth, m, function),
                elseBlock = BasicBlock.create(loopDepth, m),
                mergeBlock = BasicBlock.create(loopDepth, m);
        if (n.falseBody == null) {
            elseBlock = mergeBlock;
        }
        n.condition.thenBlock = thenBlock;
        n.condition.elseBlock = elseBlock;
        n.condition.accept(this);
        builder.setInsertPoint(thenBlock);
        n.trueBody.accept(this);
        thenBlock = builder.getInsertBlock();
        if (!thenBlock.isTerminated()) {
            builder.createBr(mergeBlock);
        }
        if (n.falseBody != null) {
            function.basicBlockList.add(elseBlock);
            elseBlock.setParent(function);
            builder.setInsertPoint(elseBlock);
            n.falseBody.accept(this);
            elseBlock = builder.getInsertBlock();
            if (!elseBlock.isTerminated()) {
                builder.createBr(mergeBlock);
            }
        }
        function.basicBlockList.add(mergeBlock);
        mergeBlock.setParent(function);
        builder.setInsertPoint(mergeBlock);
        return null;
    }

    @Override
    public Value visit(ForStmtNode n) {
        loopDepth++;
        Function function = builder.getFunction();
        BasicBlock condBlock = BasicBlock.create(loopDepth, m),
                loopBlock = BasicBlock.create(loopDepth, m),
                incrBlock = BasicBlock.create(loopDepth, m),
                destBlock = BasicBlock.create(loopDepth, m);
        n.nextBlock = incrBlock;
        n.destBlock = destBlock;
        if (n.initStmt != null) {
            n.initStmt.accept(this);
        }
        if (n.condition != null) {
            builder.createBr(condBlock);
            function.basicBlockList.add(condBlock);
            condBlock.setParent(function);
            builder.setInsertPoint(condBlock);
            n.condition.thenBlock = loopBlock;
            n.condition.elseBlock = destBlock;
            n.condition.accept(this);
            if (!builder.getInsertBlock().isTerminated()) {
                builder.createBr(loopBlock);
            }
        } else {
            condBlock = loopBlock;
            builder.createBr(loopBlock);
        }
        function.basicBlockList.add(loopBlock);
        loopBlock.setParent(function);
        builder.setInsertPoint(loopBlock);
        if (n.forBody != null) {
            n.forBody.accept(this);
        }
        loopBlock = builder.getInsertBlock();
        if (!loopBlock.isTerminated()) {
            builder.createBr(incrBlock);
        }
        function.basicBlockList.add(incrBlock);
        incrBlock.setParent(function);
        builder.setInsertPoint(incrBlock);
        if (n.iteration != null) {
            n.iteration.accept(this);
        }
        builder.createBr(condBlock);
        function.basicBlockList.add(destBlock);
        destBlock.setParent(function);
        builder.setInsertPoint(destBlock);
        loopDepth--;
        return null;
    }

    @Override
    public Value visit(WhileStmtNode n) {
        loopDepth++;
        Function function = builder.getFunction();
        BasicBlock condBlock = BasicBlock.create(loopDepth, m, function),
                loopBlock = BasicBlock.create(loopDepth, m, function),
                destBlock = BasicBlock.create(loopDepth, m, function);
        n.nextBlock = condBlock;
        n.destBlock = destBlock;
        builder.createBr(condBlock);
        builder.setInsertPoint(condBlock);
        n.condition.thenBlock = loopBlock;
        n.condition.elseBlock = destBlock;
        n.condition.accept(this);
        builder.setInsertPoint(loopBlock);
        if (n.whileBody != null) {
            n.whileBody.accept(this);
        }
        loopBlock = builder.getInsertBlock();
        if (!loopBlock.isTerminated()) {
            builder.createBr(condBlock);
        }
        builder.setInsertPoint(destBlock);
        loopDepth--;
        return null;
    }

    @Override
    public Value visit(BreakStmtNode n) {
        builder.createBr(n.dest.destBlock);
        return null;
    }

    @Override
    public Value visit(ContinueStmtNode n) {
        builder.createBr(n.dest.nextBlock);
        return null;
    }

    @Override
    public Value visit(ReturnStmtNode n) {
        Value val = null;
        if (n.returnVal != null) {
            val = n.returnVal.accept(this);
        }
        builder.createRet(builder.createPointerResolve(val));
        return null;
    }

    @Override
    public Value visit(TypeNode n) {
        return null;
    }

    @Override
    public Value visit(ClassTypeNode n) {
        classType = (StructType) m.namedStructTypes.get(n.typeName);
        n.varList.forEach(x -> x.accept(this));
        n.funcList.forEach(x -> x.accept(this));
        if (n.constructor != null) {
            n.constructor.accept(this);
        }
        classType = null;
        return null;
    }

    @Override
    public Value visit(BinaryExprNode n) {
        Value l, r;
        switch (n.binaryOpType) {
            case LAND, LOR, ASSIGN -> l = r = null;
            default -> {
                l = n.lhs.accept(this);
                l = builder.createPointerResolve(l);
                r = n.rhs.accept(this);
                r = builder.createPointerResolve(r);
                if (l == null || r == null) {
                    throw new InternalError("operand is null", n.pos);
                }
            }
        }
        ArrayList<Value> args = new ArrayList<>();
        args.add(l);
        args.add(r);
        switch (n.binaryOpType) {
            case ADD -> {
                if (n.lhs.type().isClass() && n.lhs.type().name().equals("string")) {
                    return builder.createCall(m.getBuiltinFunction("__g_str_add"), args);
                } else {
                    return builder.createSAdd(l, r);
                }
            }
            case SUB -> {
                return builder.createSSub(l, r);
            }
            case MUL -> {
                return builder.createSMul(l, r);
            }
            case DIV -> {
                return builder.createSDiv(l, r);
            }
            case MOD -> {
                return builder.createSRem(l, r);
            }
            case GREATER -> {
                Value val;
                if (n.lhs.type().isClass() && n.lhs.type().name().equals("string")) {
                    val = builder.createCall(m.getBuiltinFunction("__g_str_gt"), args);
                } else {
                    val = builder.createSGt(l, r);
                }
                return branchAdd(n, val);
            }
            case LESS -> {
                Value val;
                if (n.lhs.type().isClass() && n.lhs.type().name().equals("string")) {
                    val = builder.createCall(m.getBuiltinFunction("__g_str_lt"), args);
                } else {
                    val = builder.createSLt(l, r);
                }
                return branchAdd(n, val);
            }
            case GEQ -> {
                Value val;
                if (n.lhs.type().isClass() && n.lhs.type().name().equals("string")) {
                    val = builder.createCall(m.getBuiltinFunction("__g_str_ge"), args);
                } else {
                    val = builder.createSGe(l, r);
                }
                return branchAdd(n, val);
            }
            case LEQ -> {
                Value val;
                if (n.lhs.type().isClass() && n.lhs.type().name().equals("string")) {
                    val = builder.createCall(m.getBuiltinFunction("__g_str_le"), args);
                } else {
                    val = builder.createSLe(l, r);
                }
                return branchAdd(n, val);
            }
            case NEQ -> {
                Value val;
                if (n.lhs.type().isClass() && n.lhs.type().name().equals("string")) {
                    val = builder.createCall(m.getBuiltinFunction("__g_str_ne"), args);
                } else {
                    val = builder.createINe(l, r);
                }
                return branchAdd(n, val);
            }
            case EQUAL -> {
                Value val;
                if (n.lhs.type().isClass() && n.lhs.type().name().equals("string")) {
                    val = builder.createCall(m.getBuiltinFunction("__g_str_eq"), args);
                } else {
                    val = builder.createIEq(l, r);
                }
                return branchAdd(n, val);
            }
            case LAND -> {
                if (n.thenBlock != null) {
                    BasicBlock condBlock = BasicBlock.create(loopDepth, m, builder.getFunction());
                    n.lhs.thenBlock = condBlock;
                    n.lhs.elseBlock = n.elseBlock;
                    n.lhs.accept(this);
                    builder.setInsertPoint(condBlock);
                    n.rhs.thenBlock = n.thenBlock;
                    n.rhs.elseBlock = n.elseBlock;
                    n.rhs.accept(this);
                    return null;
                } else {
                    Function function = builder.getFunction();
                    BasicBlock trueBlock = BasicBlock.create(loopDepth, m, function),
                            mergeBlock = BasicBlock.create(loopDepth, m, function);
                    Value ptr = n.ptr;
                    if (ptr == null) {
                        ptr = builder.createEntryBlockAlloca(m.int1Ty);
                    }
                    builder.createAssign(ptr, builder.getInt1(0));
                    Value lValue = n.lhs.accept(this);
                    builder.createCondBr(builder.createPointerResolve(lValue), trueBlock, mergeBlock);
                    builder.setInsertPoint(trueBlock);
                    n.rhs.ptr = ptr;
                    Value rValue = n.rhs.accept(this);
                    builder.createBr(mergeBlock);
                    builder.setInsertPoint(mergeBlock);
                    return ptr;
                }
            }
            case LOR -> {
                if (n.thenBlock != null) {
                    BasicBlock condBlock = BasicBlock.create(loopDepth, m, builder.getFunction());
                    n.lhs.thenBlock = n.thenBlock;
                    n.lhs.elseBlock = condBlock;
                    n.lhs.accept(this);
                    builder.setInsertPoint(condBlock);
                    n.rhs.thenBlock = n.thenBlock;
                    n.rhs.elseBlock = n.elseBlock;
                    n.rhs.accept(this);
                    return null;
                } else {
                    Function function = builder.getFunction();
                    BasicBlock falseBlock = BasicBlock.create(loopDepth, m, function),
                            mergeBlock = BasicBlock.create(loopDepth, m, function);
                    Value ptr = n.ptr;
                    if (ptr == null) {
                        ptr = builder.createEntryBlockAlloca(m.int1Ty);
                    }
                    builder.createAssign(ptr, builder.getInt1(1));
                    n.lhs.thenBlock = mergeBlock;
                    n.lhs.elseBlock = falseBlock;
                    Value lValue = n.lhs.accept(this);
                    builder.createCondBr(builder.createPointerResolve(lValue), mergeBlock, falseBlock);
                    builder.setInsertPoint(falseBlock);
                    n.rhs.ptr = n.ptr;
                    Value rValue = n.rhs.accept(this);
                    builder.createBr(mergeBlock);
                    builder.setInsertPoint(mergeBlock);
                    return ptr;
                }
            }
            case R_SHIFT -> {
                return builder.createAshr(l, r);
            }
            case L_SHIFT -> {
                return builder.createShl(l, r);
            }
            case AND -> {
                return builder.createAnd(l, r);
            }
            case OR -> {
                return builder.createOr(l, r);
            }
            case XOR -> {
                return builder.createXor(l, r);
            }
            case ASSIGN -> {
                l = n.lhs.accept(this);
                n.rhs.ptr = l;
                r = n.rhs.accept(this);
                return branchAdd(n, builder.createAssign(l, r));
            }
            case SUBSCRIPT -> {
                Value ptr = builder.createGEP(l, r);
                ptr.setType(Type.getPointerTy(ptr.getType().getBaseType(), true));
                return branchAdd(n, ptr);
            }
        }
        return null;
    }

    @Override
    public Value visit(MemberExprNode n) {
        Value classPtr = builder.createPointerResolve(n.base.accept(this));
        ArrayList<Value> idx = new ArrayList<>();
        idx.add(builder.getInt32(0));
        idx.add(builder.getInt32(n.entity.numElement));
        return branchAdd(n, builder.createGEP(Type.getPointerTy(n.entity.type().irType(m), true), classPtr, idx));
    }

    @Override
    public Value visit(UnaryExprNode n) {
        Value src = n.expr.accept(this),
                val = builder.createPointerResolve(src);
        switch (n.unaryOpType) {
            case POS -> {
                return val;
            }
            case NEG -> {
                return builder.createSSub(builder.getInt32(0), val);
            }
            case L_NOT -> {
                val = builder.createXor(builder.getInt1(1), val);
                return branchAdd(n, val);
            }
            case NOT -> {
                return builder.createXor(builder.getInt32(-1), val);
            }
            case PRE_INC -> {
                Value res = builder.createSAdd(val, builder.getInt32(1));
                builder.createStore(res, src);
                return src;
            }
            case PRE_DEC -> {
                Value res = builder.createSSub(val, builder.getInt32(1));
                builder.createStore(res, src);
                return src;
            }
            case POST_INC -> {
                Value res = builder.createSAdd(val, builder.getInt32(1));
                builder.createStore(res, src);
                return val;
            }
            case POST_DEC -> {
                Value res = builder.createSSub(val, builder.getInt32(1));
                builder.createStore(res, src);
                return val;
            }
            default -> throw new InternalError("unexpected unaryOpType: " + n.unaryOpType, n.pos);
        }
    }

    @Override
    public Value visit(NewExprNode n) {
        if (n.dim != 0) {
            return createArrayMalloc(n.type().irType(m), n, 0);
        } else if (n.type().isClass()) {
            Value ptr = builder.createMalloc(builder.getInt32(n.type().irType(m).getBaseType().allocSize() / 8));
            ptr = builder.createBitCast(ptr, n.type().irType(m));
            Function constructor = m.getFunction("c_" + n.type().name() + "_" + n.type().name());
            if (constructor != null) {
                ArrayList<Value> params = new ArrayList<>();
                params.add(ptr);
                return builder.createCall(constructor, params);
            } else {
                return ptr;
            }
        }
        throw new InternalError("unexpected type of new", n.pos);
    }

    @Override
    public Value visit(FuncExprNode n) {
        Function callee = m.getFunction(n.functionName);
        ArrayList<Value> params = new ArrayList<>();
        if (n.func instanceof MemberExprNode) {
            params.add(builder.createPointerResolve(((MemberExprNode) n.func).base.accept(this)));
            if (((MemberExprNode) n.func).base.type() instanceof ArrayType) {
                Value ptr = builder.createBitCast(params.get(0), Type.getPointerTy(m.int32Ty, false));
                ptr = builder.createGEP(ptr, builder.getInt32(-1));
                return builder.createLoad(ptr);
            }
        } else if (n.isMember) {
            params.add(builder.getFunction().getClassPtr());
        }
        n.params.forEach(x -> params.add(builder.createPointerResolve(x.accept(this))));
        return branchAdd(n, builder.createCall(callee, params));
    }

    @Override
    public Value visit(VarExprNode n) {
        if (n.entity.isMember) {
            Value classPtr = builder.getFunction().getClassPtr();
            ArrayList<Value> idx = new ArrayList<>();
            idx.add(builder.getInt32(0));
            idx.add(builder.getInt32(n.entity.numElement));
            return branchAdd(n, builder.createGEP(Type.getPointerTy(n.type().irType(m), true), classPtr, idx));
        }
        Value o = n.entity.value();
        if (o == null) {
            throw new InternalError("undefined variable", n.pos);
        }
        if (o instanceof Argument) {
            n.entity.setValue(((Argument) o).ptr = builder.createEntryBlockAlloca(o.getType()));
            builder.createStore(o, n.entity.value(), builder.getFunction().getHead().get(), (Inst) ((Inst) n.entity.value()).getNext());
            o = n.entity.value();
        }
        return branchAdd(n, o);
    }

    @Override
    public Value visit(ThisExprNode n) {
        return builder.getFunction().getArg(0);
    }

    @Override
    public Value visit(IntLiteralExprNode n) {
        return builder.getInt32(n.val);
    }

    @Override
    public Value visit(BoolLiteralExprNode n) {
        return branchAdd(n, builder.getInt1(n.val ? 1 : 0));
    }

    @Override
    public Value visit(StringLiteralExprNode n) {
        Value val = builder.getGlobalString(n.val);
        ArrayList<Value> idx = new ArrayList<>();
        idx.add(builder.getInt32(0));
        idx.add(builder.getInt32(0));
        return builder.createGEP(m.stringTy, val, idx);
    }

    @Override
    public Value visit(NullLiteralExprNode n) {
        return ConstantPointerNull.get(m);
    }
}
