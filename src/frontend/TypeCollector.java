package frontend;

import ast.AstVisitor;
import ast.Nodes.*;
import frontend.scope.Scope;
import frontend.symbol.Entity;
import frontend.symbol.type.*;
import ir.Module;
import ir.type.StructType;
import util.Position;

public class TypeCollector implements AstVisitor<Void> {
    final Scope globalScope;
    final Module module;
    String currentClassName;

    public TypeCollector(Scope globalScope, Module module) {
        this.globalScope = globalScope;
        this.module = module;
    }

    private Type getType(TypeNode typeNode, int dim, Position pos) {
        if (dim == 0) {
            return globalScope.getType(typeNode.typeName, false, pos);
        } else {
            return new ArrayType((BaseType) globalScope.getType(typeNode.typeName, false, pos), dim);
        }
    }

    @Override
    public Void visit(RootNode n) {
        currentClassName = null;
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
        FuncType func;
        if (currentClassName == null) {
            func = globalScope.getFunc(n.funcName, false, n.pos);
        } else {
            func = ((ClassType) globalScope.getType(currentClassName, false, n.pos)).funcMap().get(n.funcName);
        }
        func.retType(getType(n.retType, n.dim, n.pos));
        n.params.forEach(x -> func.param().add(new Entity(x.name, getType(x.type, x.dim, x.pos))));
        return null;
    }

    @Override
    public Void visit(ParamDefSubNode n) {
        if (currentClassName != null) {
            ((ClassType) globalScope.getType(currentClassName, false, n.pos)).varMap().get(n.name).setType(getType(n.type, n.dim, n.pos));
        }
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
        this.currentClassName = n.typeName;
        StructType structTy = (StructType) StructType.get(module, n.typeName);
        for (int i = 0; i < n.varList.size(); i++) {
            n.varList.get(i).entity.isMember = true;
            n.varList.get(i).entity.numElement = i;
            n.varList.get(i).accept(this);
            structTy.addElement(n.varList.get(i).entity.type().irType(module));
        }
        structTy.insert();
        n.funcList.forEach(x -> x.accept(this));
        ClassType currentClassType = (ClassType) globalScope.getType(currentClassName, false, n.pos);
        globalScope.getFunc(currentClassType.constructor().name(), false, n.pos).retType(currentClassType);
        currentClassName = null;
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
