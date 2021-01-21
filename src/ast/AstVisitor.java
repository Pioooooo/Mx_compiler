package ast;

import ast.Nodes.*;

public interface AstVisitor {
    void visit(RootNode n);

    void visit(BlockStmtNode n);

    void visit(VarDefStmtNode n);

    void visit(VarDefSubNode n);

    void visit(ExprStmtNode n);

    void visit(FuncDefStmtNode n);

    void visit(ParamDefSubNode n);

    void visit(IfStmtNode n);

    void visit(ForStmtNode n);

    void visit(WhileStmtNode n);

    void visit(BreakStmtNode n);

    void visit(ContinueStmtNode n);

    void visit(ReturnStmtNode n);

    void visit(TypeNode n);

    void visit(ClassTypeNode n);

    void visit(BinaryExprNode n);

    void visit(MemberExprNode memberExprNode);

    void visit(UnaryExprNode n);

    void visit(NewExprNode n);

    void visit(FuncExprNode n);

    void visit(VarExprNode n);

    void visit(ThisExprNode n);

    void visit(IntLiteralExprNode n);

    void visit(BoolLiteralExprNode n);

    void visit(StringLiteralExprNode n);

    void visit(NullLiteralExprNode n);
}
