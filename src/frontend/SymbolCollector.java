package frontend;

import ast.AstVisitor;
import ast.Nodes.*;
import util.Position;
import util.scope.ClassScope;
import util.scope.Scope;
import util.symbol.Entity;
import util.symbol.type.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SymbolCollector implements AstVisitor {
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
    public void visit(RootNode n) {
        currentScope = globalScope;
        n.stmts.forEach(x -> x.accept(this));
    }

    @Override
    public void visit(BlockStmtNode n) {
    }

    @Override
    public void visit(VarDefStmtNode n) {
        n.type.accept(this);
    }

    @Override
    public void visit(VarDefSubNode n) {

    }

    @Override
    public void visit(ExprStmtNode n) {
    }

    @Override
    public void visit(FuncDefStmtNode n) {
        currentScope.defFunc(n.funcName, new FuncType(n.funcName), n.pos);
    }

    @Override
    public void visit(ParamDefSubNode n) {
        currentScope.defVar(n.name, new Entity(n.name), n.pos);
    }

    @Override
    public void visit(IfStmtNode n) {
    }

    @Override
    public void visit(ForStmtNode n) {
    }

    @Override
    public void visit(WhileStmtNode n) {
    }

    @Override
    public void visit(BreakStmtNode n) {
    }

    @Override
    public void visit(ContinueStmtNode n) {
    }

    @Override
    public void visit(ReturnStmtNode n) {
    }

    @Override
    public void visit(TypeNode n) {
    }

    @Override
    public void visit(ClassTypeNode n) {
        currentScope = new ClassScope(currentScope);
        ClassType classType = new ClassType(n.typeName);
        n.varList.forEach(x -> x.accept(this));
        n.funcList.forEach(x -> x.accept(this));
        classType.constructor(new FuncType(n.constructor != null ? n.constructor.funcName : n.typeName, classType, new ArrayList<>()))
                .varMap(currentScope.varMap()).funcMap(currentScope.funcMap());
        currentScope = currentScope.parent();
        currentScope.defType(n.typeName, classType, n.pos);
        currentScope.funcMap().put(n.typeName, classType.constructor());
    }

    @Override
    public void visit(BinaryExprNode n) {
    }

    @Override
    public void visit(MemberExprNode n) {
    }

    @Override
    public void visit(UnaryExprNode n) {
    }

    @Override
    public void visit(NewExprNode n) {
    }

    @Override
    public void visit(FuncExprNode n) {
    }

    @Override
    public void visit(VarExprNode n) {
    }

    @Override
    public void visit(ThisExprNode n) {
    }

    @Override
    public void visit(IntLiteralExprNode n) {
    }

    @Override
    public void visit(BoolLiteralExprNode n) {
    }

    @Override
    public void visit(StringLiteralExprNode n) {
    }

    @Override
    public void visit(NullLiteralExprNode n) {
    }
}
