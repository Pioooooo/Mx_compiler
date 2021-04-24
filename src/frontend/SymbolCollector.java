package frontend;

import ast.AstVisitor;
import ast.Nodes.*;
import frontend.scope.ClassScope;
import frontend.scope.Scope;
import frontend.symbol.Entity;
import frontend.symbol.type.ArrayType;
import frontend.symbol.type.ClassType;
import frontend.symbol.type.FuncType;
import frontend.symbol.type.FundamentalType;
import util.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SymbolCollector implements AstVisitor<Void> {
    private final Scope globalScope;
    private Scope currentScope;

    public SymbolCollector(Scope globalScope) {
        Position pos = new Position(0, 0);
        FundamentalType intType = new FundamentalType("int"), boolType = new FundamentalType("bool"),
                voidType = new FundamentalType("void"), nullType = new FundamentalType("null");
        ClassType stringType = new ClassType("string");
        stringType.varMap().put("", new Entity("", new ArrayType(intType, 1)));

        globalScope.defType("int", intType, pos);
        globalScope.defType("bool", boolType, pos);
        globalScope.defType("void", voidType, pos);
        globalScope.defType("null", nullType, pos);
        globalScope.defType("string", stringType, pos);

        globalScope.defFunc("print", new FuncType("print", voidType, new ArrayList<>(Collections.singletonList(new Entity("str", stringType)))), pos);
        globalScope.defFunc("println", new FuncType("println", voidType, new ArrayList<>(Collections.singletonList(new Entity("str", stringType)))), pos);
        globalScope.defFunc("printInt", new FuncType("printInt", voidType, new ArrayList<>(Collections.singletonList(new Entity("n", intType)))), pos);
        globalScope.defFunc("printlnInt", new FuncType("printlnInt", voidType, new ArrayList<>(Collections.singletonList(new Entity("n", intType)))), pos);
        globalScope.defFunc("getString", new FuncType("getString", stringType, new ArrayList<>()), pos);
        globalScope.defFunc("getInt", new FuncType("getInt", intType, new ArrayList<>()), pos);
        globalScope.defFunc("toString", new FuncType("toString", stringType, new ArrayList<>(Collections.singletonList(new Entity("i", intType)))), pos);

        stringType.funcMap().put("length", new FuncType("length", intType, new ArrayList<>()));
        stringType.funcMap().put("substring", new FuncType("substring", stringType, new ArrayList<>(Arrays.asList(new Entity("left", intType), new Entity("right", intType)))));
        stringType.funcMap().put("parseInt", new FuncType("parseInt", intType, new ArrayList<>()));
        stringType.funcMap().put("ord", new FuncType("ord", intType, new ArrayList<>(Collections.singletonList(new Entity("pos", intType)))));
        this.globalScope = globalScope;
    }

    @Override
    public Void visit(RootNode n) {
        currentScope = globalScope;
        n.stmts.forEach(x -> x.accept(this));
        return null;
    }

    @Override
    public Void visit(BlockStmtNode n) {
        return null;
    }

    @Override
    public Void visit(VarDefStmtNode n) {
        n.type.accept(this);
        return null;
    }

    @Override
    public Void visit(VarDefSubNode n) {
        return null;
    }

    @Override
    public Void visit(ExprStmtNode n) {
        return null;
    }

    @Override
    public Void visit(FuncDefStmtNode n) {
        currentScope.defFunc(n.funcName, new FuncType(n.funcName), n.pos);
        return null;
    }

    @Override
    public Void visit(ParamDefSubNode n) {
        n.entity = currentScope.defVar(n.name, new Entity(n.name), n.pos);
        return null;
    }

    @Override
    public Void visit(IfStmtNode n) {
        return null;
    }

    @Override
    public Void visit(ForStmtNode n) {
        return null;
    }

    @Override
    public Void visit(WhileStmtNode n) {
        return null;
    }

    @Override
    public Void visit(BreakStmtNode n) {
        return null;
    }

    @Override
    public Void visit(ContinueStmtNode n) {
        return null;
    }

    @Override
    public Void visit(ReturnStmtNode n) {
        return null;
    }

    @Override
    public Void visit(TypeNode n) {
        return null;
    }

    @Override
    public Void visit(ClassTypeNode n) {
        currentScope = new ClassScope(currentScope);
        ClassType classType = new ClassType(n.typeName);
        n.varList.forEach(x -> x.accept(this));
        n.funcList.forEach(x -> x.accept(this));
        classType.constructor(new FuncType(n.constructor != null ? n.constructor.funcName : n.typeName, classType, new ArrayList<>()))
                .varMap(currentScope.varMap()).funcMap(currentScope.funcMap());
        currentScope = currentScope.parent();
        currentScope.defType(n.typeName, classType, n.pos);
        currentScope.funcMap().put(n.typeName, classType.constructor());
        classType.classDef = n;
        return null;
    }

    @Override
    public Void visit(BinaryExprNode n) {
        return null;
    }

    @Override
    public Void visit(MemberExprNode n) {
        return null;
    }

    @Override
    public Void visit(UnaryExprNode n) {
        return null;
    }

    @Override
    public Void visit(NewExprNode n) {
        return null;
    }

    @Override
    public Void visit(FuncExprNode n) {
        return null;
    }

    @Override
    public Void visit(VarExprNode n) {
        return null;
    }

    @Override
    public Void visit(ThisExprNode n) {
        return null;
    }

    @Override
    public Void visit(IntLiteralExprNode n) {
        return null;
    }

    @Override
    public Void visit(BoolLiteralExprNode n) {
        return null;
    }

    @Override
    public Void visit(StringLiteralExprNode n) {
        return null;
    }

    @Override
    public Void visit(NullLiteralExprNode n) {
        return null;
    }
}
