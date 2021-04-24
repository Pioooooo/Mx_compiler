package ast;

import ast.Nodes.*;

import java.io.PrintStream;

public class AstPrinter implements AstVisitor<Void> {
    int depth = 0;
    PrintStream out;

    public AstPrinter() {
        this(System.out);
    }

    public AstPrinter(PrintStream out) {
        this.out = out;
    }

    <T> void println(T msg) {
        out.print("\t".repeat(depth));
        out.println(msg);
    }

    @Override
    public Void visit(RootNode n) {
        println("[RootNode]@" + n.pos);
        println("stmts:");
        depth++;
        n.stmts.forEach(x -> x.accept(this));
        depth--;
        return null;
    }

    @Override
    public Void visit(BlockStmtNode n) {
        println("[BlockStmtNode]@" + n.pos);
        println("stmts:");
        depth++;
        n.stmts.forEach(x -> x.accept(this));
        depth--;
        return null;
    }

    @Override
    public Void visit(VarDefStmtNode n) {
        println("[VarDefStmtNode]@" + n.pos);
        println("type:");
        depth++;
        n.type.accept(this);
        depth--;
        println("defList:");
        depth++;
        n.defList.forEach(x -> x.accept(this));
        depth--;
        return null;
    }

    @Override
    public Void visit(VarDefSubNode n) {
        println("[VarDefSubNode]@" + n.pos);
        println("name: " + n.name);
        println("dim: " + n.dim);
        if (n.initVal != null) {
            println("initVal:");
            depth++;
            n.initVal.accept(this);
            depth--;
        }
        return null;
    }

    @Override
    public Void visit(ExprStmtNode n) {
        println("[ExprStmtNode]@" + n.pos);
        if (n.expr != null) {
            println("expr:");
            depth++;
            n.expr.accept(this);
            depth--;
        }
        return null;
    }

    @Override
    public Void visit(FuncDefStmtNode n) {
        println("[FuncDefStmtNode]@" + n.pos);
        println("dim: " + n.dim);
        println("funcName: " + n.funcName);
        println("params:");
        if (n.retType != null) {
            println("retType:");
            depth++;
            n.retType.accept(this);
            depth--;
        }
        depth++;
        n.params.forEach(x -> x.accept(this));
        depth--;
        println("funcBody:");
        depth++;
        n.funcBody.forEach(x -> x.accept(this));
        depth--;
        return null;
    }

    @Override
    public Void visit(ParamDefSubNode n) {
        println("[ParamDefSubNode]@" + n.pos);
        println("name: " + n.name);
        println("dim: " + n.dim);
        println("type:");
        depth++;
        n.type.accept(this);
        depth--;
        if (n.initVal != null) {
            println("initVal:");
            depth++;
            n.initVal.accept(this);
            depth--;
        }
        return null;
    }

    @Override
    public Void visit(IfStmtNode n) {
        println("[IfStmtNode]@" + n.pos);
        println("condition:");
        depth++;
        n.condition.accept(this);
        depth--;
        println("ifBody:");
        depth++;
        n.trueBody.accept(this);
        depth--;
        if (n.falseBody != null) {
            println("elseBody:");
            depth++;
            n.falseBody.accept(this);
            depth--;
        }
        return null;
    }

    @Override
    public Void visit(ForStmtNode n) {
        println("[ForStmtNode]@" + n.pos);
        println("initStmt:");
        depth++;
        n.initStmt.accept(this);
        depth--;
        if (n.condition != null) {
            println("condition:");
            depth++;
            n.condition.accept(this);
            depth--;
        }
        if (n.iteration != null) {
            println("iteration:");
            depth++;
            n.iteration.accept(this);
            depth--;
        }
        println("forBody:");
        depth++;
        n.forBody.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void visit(WhileStmtNode n) {
        println("[WhileStmtNode]@" + n.pos);
        println("condition:");
        depth++;
        n.condition.accept(this);
        depth--;
        println("whileBody:");
        depth++;
        n.whileBody.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void visit(BreakStmtNode n) {
        println("[BreakStmtNode]@" + n.pos);
        return null;
    }

    @Override
    public Void visit(ContinueStmtNode n) {
        println("[ContinueStmtNode]@" + n.pos);
        return null;
    }

    @Override
    public Void visit(ReturnStmtNode n) {
        println("[ReturnStmtNode]@" + n.pos);
        if (n.returnVal != null) {
            println("returnVal:");
            depth++;
            n.returnVal.accept(this);
            depth--;
        }
        return null;
    }

    @Override
    public Void visit(TypeNode n) {
        println("[TypeNode]@" + n.pos);
        println("typeName: " + n.typeName);
        return null;
    }

    @Override
    public Void visit(ClassTypeNode n) {
        println("[ClassTypeNode]@" + n.pos);
        println("varList:");
        depth++;
        n.varList.forEach(x -> x.accept(this));
        depth--;
        println("funcList:");
        depth++;
        n.funcList.forEach(x -> x.accept(this));
        depth--;
        if (n.constructor != null) {
            println("constructor:");
            depth++;
            n.constructor.accept(this);
            depth--;
        }
        return null;
    }

    @Override
    public Void visit(BinaryExprNode n) {
        println("[BinaryExprNode]@" + n.pos);
        println("binaryOpType: " + n.binaryOpType);
        println("lhs:");
        depth++;
        n.lhs.accept(this);
        depth--;
        println("rhs:");
        depth++;
        n.rhs.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void visit(MemberExprNode n) {
        println("[MemberExprNode]@" + n.pos);
        println("name: " + n.name);
        println("base:");
        depth++;
        n.base.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void visit(UnaryExprNode n) {
        println("[UnaryExprNode]@" + n.pos);
        println("unaryOpType: " + n.unaryOpType);
        println("expr:");
        depth++;
        n.expr.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void visit(NewExprNode n) {
        println("[NewExprNode]@" + n.pos);
        println("dim: " + n.dim);
        println("type:");
        depth++;
        n.type.accept(this);
        depth--;
        println("sizes:");
        depth++;
        n.sizes.forEach(x -> x.accept(this));
        depth--;
        return null;
    }

    @Override
    public Void visit(FuncExprNode n) {
        println("[FuncExprNode]@" + n.pos);
        println("func:");
        depth++;
        n.func.accept(this);
        depth--;
        println("params:");
        depth++;
        n.params.forEach(x -> x.accept(this));
        depth--;
        return null;
    }

    @Override
    public Void visit(VarExprNode n) {
        println("[VarExprNode]@" + n.pos);
        println("name: " + n.name);
        return null;
    }

    @Override
    public Void visit(ThisExprNode n) {
        println("[ThisExprNode]@" + n.pos);
        return null;
    }

    @Override
    public Void visit(IntLiteralExprNode n) {
        println("[IntLiteralExprNode]@" + n.pos);
        println("val: " + n.val);
        return null;
    }

    @Override
    public Void visit(BoolLiteralExprNode n) {
        println("[BoolLiteralExprNode]@" + n.pos);
        println("val: " + n.val);
        return null;
    }

    @Override
    public Void visit(StringLiteralExprNode n) {
        println("[StringLiteralExprNode]@" + n.pos);
        println("val: " + n.val);
        return null;
    }

    @Override
    public Void visit(NullLiteralExprNode n) {
        println("[NullLiteralExprNode]@" + n.pos);
        return null;
    }
}
