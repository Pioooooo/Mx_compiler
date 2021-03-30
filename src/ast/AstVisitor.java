package ast;

import ast.Nodes.*;

public interface AstVisitor<Ty> {
    Ty visit(RootNode n);

    Ty visit(BlockStmtNode n);

    Ty visit(VarDefStmtNode n);

    Ty visit(VarDefSubNode n);

    Ty visit(ExprStmtNode n);

    Ty visit(FuncDefStmtNode n);

    Ty visit(ParamDefSubNode n);

    Ty visit(IfStmtNode n);

    Ty visit(ForStmtNode n);

    Ty visit(WhileStmtNode n);

    Ty visit(BreakStmtNode n);

    Ty visit(ContinueStmtNode n);

    Ty visit(ReturnStmtNode n);

    Ty visit(TypeNode n);

    Ty visit(ClassTypeNode n);

    Ty visit(BinaryExprNode n);

    Ty visit(MemberExprNode n);

    Ty visit(UnaryExprNode n);

    Ty visit(NewExprNode n);

    Ty visit(FuncExprNode n);

    Ty visit(VarExprNode n);

    Ty visit(ThisExprNode n);

    Ty visit(IntLiteralExprNode n);

    Ty visit(BoolLiteralExprNode n);

    Ty visit(StringLiteralExprNode n);

    Ty visit(NullLiteralExprNode n);
}
