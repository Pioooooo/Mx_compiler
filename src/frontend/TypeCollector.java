package frontend;

import ast.AstVisitor;
import ast.Nodes.*;
import util.Position;
import util.scope.Scope;
import util.symbol.Entity;
import util.symbol.type.*;

public class TypeCollector implements AstVisitor {
    private final Scope globalScope;
    private String currentClassName;

    public TypeCollector(Scope globalScope) {
        this.globalScope = globalScope;
    }

    private Type getType(TypeNode typeNode, int dim, Position pos) {
        if (dim == 0) {
            return globalScope.getType(typeNode.typeName, false, pos);
        } else {
            return new ArrayType((BaseType) globalScope.getType(typeNode.typeName, false, pos), dim);
        }
    }

    @Override
    public void visit(RootNode n) {
        currentClassName = null;
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
        FuncType func;
        if (currentClassName == null) {
            func = globalScope.getFunc(n.funcName, false, n.pos);
        } else {
            func = ((ClassType) globalScope.getType(currentClassName, false, n.pos)).funcMap().get(n.funcName);
        }
        func.retType(getType(n.retType, n.dim, n.pos));
        n.params.forEach(x -> func.param().add(new Entity(x.name, getType(x.type, x.dim, x.pos))));
    }

    @Override
    public void visit(ParamDefSubNode n) {
        if (currentClassName != null) {
            ((ClassType) globalScope.getType(currentClassName, false, n.pos)).varMap().get(n.name).type(getType(n.type, n.dim, n.pos));
        }
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
        this.currentClassName = n.typeName;
        n.varList.forEach(x -> x.accept(this));
        n.funcList.forEach(x -> x.accept(this));
        ClassType currentClassType = (ClassType) globalScope.getType(currentClassName, false, n.pos);
        globalScope.getFunc(currentClassType.constructor().name(), false, n.pos).retType(currentClassType);
        currentClassName = null;
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
