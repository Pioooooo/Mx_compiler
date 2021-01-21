package ast;

import util.Position;
import util.symbol.type.Type;

import java.util.ArrayList;

public class Nodes {
    public static class RootNode extends AstNode {
        public ArrayList<StmtNode> stmts;

        public RootNode(Position pos, ArrayList<StmtNode> stmts) {
            super(pos);
            this.stmts = stmts;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static abstract class StmtNode extends AstNode {
        protected StmtNode(Position pos) {
            super(pos);
        }
    }

    public static class BlockStmtNode extends StmtNode {
        public ArrayList<StmtNode> stmts;

        public BlockStmtNode(Position pos, ArrayList<StmtNode> stmts) {
            super(pos);
            this.stmts = stmts;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class VarDefStmtNode extends StmtNode {
        public TypeNode type;
        public ArrayList<VarDefSubNode> defList;

        public VarDefStmtNode(Position pos, TypeNode type, ArrayList<VarDefSubNode> defList) {
            super(pos);
            this.type = type;
            this.defList = defList;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class VarDefSubNode extends AstNode {
        public String name;
        public int dim;
        public ExprNode initVal;

        public VarDefSubNode(Position pos, String name, int dim, ExprNode initVal) {
            super(pos);
            this.name = name;
            this.dim = dim;
            this.initVal = initVal;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ExprStmtNode extends StmtNode {
        public ExprNode expr;

        public ExprStmtNode(Position pos, ExprNode expr) {
            super(pos);
            this.expr = expr;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class FuncDefStmtNode extends StmtNode {
        public String funcName;
        public TypeNode retType;
        public int dim;
        public ArrayList<ParamDefSubNode> params;
        public ArrayList<StmtNode> funcBody;
        public boolean isConstructor;

        public FuncDefStmtNode(Position pos, String funcName, TypeNode retType, int dim, ArrayList<ParamDefSubNode> params, ArrayList<StmtNode> funcBody, boolean isConstructor) {
            super(pos);
            this.funcName = funcName;
            this.retType = retType;
            this.dim = dim;
            this.params = params;
            this.funcBody = funcBody;
            this.isConstructor = isConstructor;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ParamDefSubNode extends AstNode {
        public TypeNode type;
        public String name;
        public int dim;
        public ExprNode initVal;

        public ParamDefSubNode(Position pos, TypeNode type, String name, int dim, ExprNode initVal) {
            super(pos);
            this.type = type;
            this.name = name;
            this.dim = dim;
            this.initVal = initVal;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class IfStmtNode extends StmtNode {
        public ExprNode condition;
        public StmtNode ifBody;
        public StmtNode elseBody;

        public IfStmtNode(Position pos, ExprNode condition, StmtNode ifBody, StmtNode elseBody) {
            super(pos);
            this.condition = condition;
            this.ifBody = ifBody;
            this.elseBody = elseBody;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ForStmtNode extends StmtNode {
        public StmtNode initStmt;
        public ExprNode condition;
        public ExprNode iteration;
        public StmtNode forBody;

        public ForStmtNode(Position pos, StmtNode initStmt, ExprNode condition, ExprNode iteration, StmtNode forBody) {
            super(pos);
            this.initStmt = initStmt;
            this.condition = condition;
            this.iteration = iteration;
            this.forBody = forBody;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class WhileStmtNode extends StmtNode {
        public ExprNode condition;
        public StmtNode whileBody;

        public WhileStmtNode(Position pos, ExprNode condition, StmtNode whileBody) {
            super(pos);
            this.condition = condition;
            this.whileBody = whileBody;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class BreakStmtNode extends StmtNode {
        public BreakStmtNode(Position pos) {
            super(pos);
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ContinueStmtNode extends StmtNode {
        public ContinueStmtNode(Position pos) {
            super(pos);
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ReturnStmtNode extends StmtNode {
        public ExprNode returnVal;

        public ReturnStmtNode(Position pos, ExprNode returnVal) {
            super(pos);
            this.returnVal = returnVal;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public abstract static class ExprNode extends AstNode {
        private Type type;

        protected ExprNode(Position pos) {
            super(pos);
        }

        public ExprNode type(Type type) {
            this.type = type;
            return this;
        }

        public Type type() {
            return type;
        }

        public boolean lvalue() {
            return false;
        }
    }

    public static class TypeNode extends AstNode {
        public String typeName;

        public TypeNode(Position pos, String typeName) {
            super(pos);
            this.typeName = typeName;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ClassTypeNode extends TypeNode {
        public ArrayList<ParamDefSubNode> varList;
        public ArrayList<FuncDefStmtNode> funcList;
        public FuncDefStmtNode constructor;

        public ClassTypeNode(Position pos, String typeName, ArrayList<ParamDefSubNode> varList, ArrayList<FuncDefStmtNode> funcList, FuncDefStmtNode constructor) {
            super(pos, typeName);
            this.varList = varList;
            this.funcList = funcList;
            this.constructor = constructor;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class BinaryExprNode extends ExprNode {
        public enum BinaryOpType {
            ADD, SUB, MUL, DIV, MOD,
            GREATER, LESS, GEQ, LEQ, NEQ, EQUAL,
            LAND, LOR,
            R_SHIFT, L_SHIFT, AND, OR, XOR, ASSIGN, SUBSCRIPT
        }

        public ExprNode lhs, rhs;
        public BinaryOpType binaryOpType;

        public BinaryExprNode(Position pos, BinaryOpType binaryOpType, ExprNode lhs, ExprNode rhs) {
            super(pos);
            this.binaryOpType = binaryOpType;
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public boolean lvalue() {
            return switch (this.binaryOpType) {
                case ASSIGN, SUBSCRIPT -> true;
                default -> false;
            };
        }
    }

    public static class MemberExprNode extends ExprNode {
        public ExprNode base;
        public String name;
        public boolean isFunc;

        public MemberExprNode(Position pos, ExprNode base, String name) {
            super(pos);
            this.base = base;
            this.name = name;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public boolean lvalue() {
            return !isFunc;
        }

        public MemberExprNode isFunc(boolean isFunc) {
            this.isFunc = isFunc;
            return this;
        }
    }

    public static class UnaryExprNode extends ExprNode {
        public enum UnaryOpType {
            POS, NEG,
            L_NOT,
            NOT,
            PRE_INC, PRE_DEC, POST_INC, POST_DEC
        }

        public UnaryOpType unaryOpType;
        public ExprNode expr;

        public UnaryExprNode(Position pos, UnaryOpType unaryOpType, ExprNode expr) {
            super(pos);
            this.unaryOpType = unaryOpType;
            this.expr = expr;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public boolean lvalue() {
            return switch (this.unaryOpType) {
                case PRE_INC, PRE_DEC -> true;
                default -> false;
            };
        }
    }

    public static class NewExprNode extends ExprNode {
        public TypeNode typeNode;
        public int dim;
        public ArrayList<ExprNode> sizes;

        public NewExprNode(Position pos, TypeNode typeNode, int dim, ArrayList<ExprNode> sizes) {
            super(pos);
            this.typeNode = typeNode;
            this.dim = dim;
            this.sizes = sizes;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public boolean lvalue() {
            return true;
        }
    }

    public static class FuncExprNode extends ExprNode {
        public ExprNode func;
        public ArrayList<ExprNode> params;

        public FuncExprNode(Position pos, ExprNode func, ArrayList<ExprNode> params) {
            super(pos);
            this.func = func;
            this.params = params;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public boolean lvalue() {
            return false;
        }
    }

    public static class VarExprNode extends ExprNode {
        public String name;

        public VarExprNode(Position pos, String name) {
            super(pos);
            this.name = name;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public boolean lvalue() {
            return true;
        }
    }

    public static class ThisExprNode extends ExprNode {
        public ThisExprNode(Position pos) {
            super(pos);
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public boolean lvalue() {
            return false;
        }
    }

    public static class IntLiteralExprNode extends ExprNode {
        public int val;

        public IntLiteralExprNode(Position pos, int val) {
            super(pos);
            this.val = val;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public boolean lvalue() {
            return false;
        }
    }

    public static class BoolLiteralExprNode extends ExprNode {
        public boolean val;

        public BoolLiteralExprNode(Position pos, boolean val) {
            super(pos);
            this.val = val;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public boolean lvalue() {
            return false;
        }
    }

    public static class StringLiteralExprNode extends ExprNode {
        public String val;

        public StringLiteralExprNode(Position pos, String val) {
            super(pos);
            this.val = val;
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public boolean lvalue() {
            return false;
        }
    }

    public static class NullLiteralExprNode extends ExprNode {
        public NullLiteralExprNode(Position pos) {
            super(pos);
        }

        @Override
        public void accept(AstVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public boolean lvalue() {
            return false;
        }
    }
}