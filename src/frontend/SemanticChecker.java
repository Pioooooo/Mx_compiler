package frontend;

import ast.AstVisitor;
import ast.Nodes.*;
import util.Position;
import util.error.SemanticError;
import util.scope.ClassScope;
import util.scope.Scope;
import util.symbol.Entity;
import util.symbol.type.*;

import java.util.ArrayList;

public class SemanticChecker implements AstVisitor {
    private final Scope globalScope;
    private Scope currentScope;
    private ClassType currentClass;
    private Type currentRetType;
    private boolean returned;
    private int loopDepth;

    public SemanticChecker(Scope globalScope) {
        this.globalScope = globalScope;
        currentClass = null;
        currentRetType = null;
    }

    private Type getType(TypeNode typeNode, int dim, Position pos) {
        if (dim == 0) {
            return globalScope.getType(typeNode.typeName, false, pos);
        } else {
            return new ArrayType((BaseType) globalScope.getType(typeNode.typeName, false, pos), dim);
        }
    }

    private SemanticError mismatchedType(Type a, Type b, Position pos) {
        return new SemanticError("type error : type " + b + " got, expecting type: " + a.name(), pos);
    }

    private SemanticError invalidOperation(Type a, Type b, String op, Position pos) {
        return new SemanticError("invalid operation: " + a.name() + " " + op + " " + b.name(), pos);
    }

    @Override
    public void visit(RootNode n) {
        FuncType main = globalScope.getFunc("main", false, n.pos);
        if (!main.retType().isInt()) {
            throw new SemanticError("main with return type " + main.retType().name(), n.pos);
        } else if (main.param().size() != 0) {
            throw new SemanticError("main with parameter(s)", n.pos);
        }
        currentScope = globalScope;
        loopDepth = 0;
        n.stmts.forEach(x -> x.accept(this));
    }

    @Override
    public void visit(BlockStmtNode n) {
        currentScope = new Scope(currentScope);
        n.stmts.forEach(x -> x.accept(this));
        currentScope = currentScope.parent();
    }

    @Override
    public void visit(VarDefStmtNode n) {
        n.type.accept(this);
        if (getType(n.type, 0, n.pos).isVoid()) {
            throw new SemanticError("variable(s) with void type", n.pos);
        }
        n.defList.forEach(x -> {
            Type varType = getType(n.type, x.dim, x.pos);
            if (x.initVal != null) {
                x.initVal.accept(this);
                if (!x.initVal.type().equals(varType)) {
                    throw mismatchedType(varType, x.initVal.type(), x.initVal.pos);
                }
            }
            currentScope.defVar(x.name, new Entity(x.name, varType), x.pos);
        });
    }

    @Override
    public void visit(VarDefSubNode n) {

    }

    @Override
    public void visit(ExprStmtNode n) {
        if (n.expr != null) {
            n.expr.accept(this);
        }
    }

    @Override
    public void visit(FuncDefStmtNode n) {
        if (n.retType != null) {
            currentRetType = getType(n.retType, n.dim, n.pos);
        } else {
            currentRetType = globalScope.getType("void", false, n.pos);
        }
        returned = false;
        currentScope = new Scope(currentScope);
        n.params.forEach(x -> currentScope.defVar(x.name, new Entity(x.name, getType(x.type, x.dim, x.pos)), x.pos));
        n.funcBody.forEach(x -> x.accept(this));
        currentScope = currentScope.parent();
        if (!n.funcName.equals("main") && !currentRetType.isVoid() && !returned) {
            throw new SemanticError("function " + n.funcName + " not returned", n.pos);
        }
        currentRetType = null;
    }

    @Override
    public void visit(ParamDefSubNode n) {
    }

    @Override
    public void visit(IfStmtNode n) {
        n.condition.accept(this);
        if (!n.condition.type().isBool()) {
            throw mismatchedType(globalScope.getType("bool", false, n.pos), n.condition.type(), n.pos);
        }
        currentScope = new Scope(currentScope);
        if (n.ifBody instanceof BlockStmtNode) {
            ((BlockStmtNode) n.ifBody).stmts.forEach(x -> x.accept(this));
        } else if (n.ifBody != null) {
            n.ifBody.accept(this);
        }
        currentScope = currentScope.parent();
        if (n.elseBody != null) {
            currentScope = new Scope(currentScope);
            if (n.elseBody instanceof BlockStmtNode) {
                ((BlockStmtNode) n.elseBody).stmts.forEach(x -> x.accept(this));
            } else {
                n.elseBody.accept(this);
            }
            currentScope = currentScope.parent();
        }
    }

    @Override
    public void visit(ForStmtNode n) {
        currentScope = new Scope(currentScope);
        if (n.initStmt != null) {
            n.initStmt.accept(this);
        }
        if (n.condition != null) {
            n.condition.accept(this);
            if (!n.condition.type().isBool()) {
                throw mismatchedType(globalScope.getType("bool", false, n.pos), n.condition.type(), n.pos);
            }
        }
        if (n.iteration != null) {
            n.iteration.accept(this);
        }
        loopDepth++;
        if (n.forBody instanceof BlockStmtNode) {
            ((BlockStmtNode) n.forBody).stmts.forEach(x -> x.accept(this));
        } else if (n.forBody != null) {
            n.forBody.accept(this);
        }
        loopDepth--;
        currentScope = currentScope.parent();
    }

    @Override
    public void visit(WhileStmtNode n) {
        currentScope = new Scope(currentScope);
        n.condition.accept(this);
        if (!n.condition.type().isBool()) {
            throw mismatchedType(globalScope.getType("bool", false, n.pos), n.condition.type(), n.pos);
        }
        loopDepth++;
        if (n.whileBody != null) {
            if (n.whileBody instanceof BlockStmtNode) {
                ((BlockStmtNode) n.whileBody).stmts.forEach(x -> x.accept(this));
            } else {
                n.whileBody.accept(this);
            }
        }
        loopDepth--;
        currentScope = currentScope.parent();
    }

    @Override
    public void visit(BreakStmtNode n) {
        if (loopDepth == 0) {
            throw new SemanticError("nothing to break", n.pos);
        }
    }

    @Override
    public void visit(ContinueStmtNode n) {
        if (loopDepth == 0) {
            throw new SemanticError("nothing to continue", n.pos);
        }
    }

    @Override
    public void visit(ReturnStmtNode n) {
        if (currentRetType == null) {
            throw new SemanticError("nothing to return", n.pos);
        }
        returned = true;
        if (n.returnVal == null) {
            if (!currentRetType.isVoid()) {
                throw mismatchedType(currentRetType, globalScope.getType("null", false, n.pos), n.pos);
            }
        } else {
            n.returnVal.accept(this);
            if (!n.returnVal.type().equals(currentRetType)) {
                throw mismatchedType(currentRetType, n.returnVal.type(), n.pos);
            }
        }
    }

    @Override
    public void visit(TypeNode n) {
    }

    @Override
    public void visit(ClassTypeNode n) {
        currentClass = (ClassType) globalScope.getType(n.typeName, false, n.pos);
        currentScope = new ClassScope(currentScope);
        currentClass.varMap().forEach((k, v) -> currentScope.defVar(k, v, n.pos));
        currentClass.funcMap().forEach((k, v) -> currentScope.defFunc(k, v, n.pos));
        n.funcList.forEach(x -> x.accept(this));
        if (n.constructor != null) {
            if (!n.constructor.funcName.equals(n.typeName)) {
                throw new SemanticError("mismatched constructor name with class name", n.constructor.pos);
            } else {
                n.constructor.accept(this);
            }
        }
        currentClass = null;
        currentScope = currentScope.parent();
    }

    @Override
    public void visit(BinaryExprNode n) {
        n.lhs.accept(this);
        n.rhs.accept(this);
        switch (n.binaryOpType) {
            case ADD -> {
                if (!(n.lhs.type().isInt() && n.rhs.type().isInt())
                        && !(n.lhs.type().equals(globalScope.getType("string", false, n.pos))
                        && n.rhs.type().equals(globalScope.getType("string", false, n.pos)))) {
                    throw invalidOperation(n.lhs.type(), n.rhs.type(), n.binaryOpType.name(), n.pos);
                } else {
                    n.type(n.lhs.type());
                }
            }
            case SUB, MUL, DIV, MOD, R_SHIFT, L_SHIFT, AND, OR, XOR -> {
                if (!(n.lhs.type().isInt() && n.rhs.type().isInt())) {
                    throw invalidOperation(n.lhs.type(), n.rhs.type(), n.binaryOpType.name(), n.pos);
                } else {
                    n.type(globalScope.getType("int", false, n.pos));
                }
            }
            case LAND, LOR -> {
                if (!(n.lhs.type().isBool() && n.rhs.type().isBool())) {
                    throw invalidOperation(n.lhs.type(), n.rhs.type(), n.binaryOpType.name(), n.pos);
                } else {
                    n.type(globalScope.getType("bool", false, n.pos));
                }
            }
            case GREATER, LESS, GEQ, LEQ -> {
                if (!(n.lhs.type().isInt() && n.rhs.type().isInt())
                        && !(n.lhs.type().equals(globalScope.getType("string", false, n.pos))
                        && n.rhs.type().equals(globalScope.getType("string", false, n.pos)))) {
                    throw invalidOperation(n.lhs.type(), n.rhs.type(), n.binaryOpType.name(), n.pos);
                } else {
                    n.type(globalScope.getType("bool", false, n.pos));
                }
            }
            case NEQ, EQUAL -> {
                if (!n.lhs.type().equals(n.rhs.type())) {
                    throw invalidOperation(n.lhs.type(), n.rhs.type(), n.binaryOpType.name(), n.pos);
                } else {
                    n.type(globalScope.getType("bool", false, n.pos));
                }
            }
            case ASSIGN -> {
                if (!n.lhs.type().equals(n.rhs.type())) {
                    throw invalidOperation(n.lhs.type(), n.rhs.type(), n.binaryOpType.name(), n.pos);
                } else if (!n.lhs.lvalue()) {
                    throw new SemanticError("lvalue expected, got rvalue", n.pos);
                }
            }
            case SUBSCRIPT -> {
                if (!(n.lhs.type() instanceof ArrayType)) {
                    throw new SemanticError(n.lhs.type() + " is not an array type", n.pos);
                } else if (!n.rhs.type().isInt()) {
                    throw mismatchedType(globalScope.getType("int", false, n.pos), n.rhs.type(), n.pos);
                } else {
                    ArrayType arrayType = (ArrayType) n.lhs.type();
                    if (arrayType.dim() == 1) {
                        n.type(arrayType.base());
                    } else {
                        n.type(new ArrayType(arrayType.base(), arrayType.dim() - 1));
                    }
                }
            }
        }
    }

    @Override
    public void visit(MemberExprNode n) {
        n.base.accept(this);
        if (n.base.type() instanceof ArrayType && n.isFunc && n.name.equals("size")) {
            n.type(new FuncType("size", globalScope.getType("int", false, n.pos), new ArrayList<>()));
            return;
        }
        if (!(n.base.type() instanceof ClassType)) {
            throw new SemanticError(n.base.type().name() + " is not a class type", n.pos);
        }
        ClassType classType = (ClassType) n.base.type();
        if (n.isFunc) {
            n.type(classType.funcMap().get(n.name));
            if (n.type() == null) {
                throw new SemanticError("member function " + n.name + " not found", n.pos);
            }
        } else {
            n.type(classType.varMap().get(n.name).type());
        }
    }

    @Override
    public void visit(UnaryExprNode n) {
        n.expr.accept(this);
        switch (n.unaryOpType) {
            case POS, NEG, NOT -> {
                if (!n.expr.type().isInt()) {
                    throw mismatchedType(globalScope.getType("int", false, n.pos), n.expr.type(), n.pos);
                }
            }
            case PRE_INC, PRE_DEC, POST_INC, POST_DEC -> {
                if (!n.expr.type().isInt()) {
                    throw mismatchedType(globalScope.getType("int", false, n.pos), n.expr.type(), n.pos);
                }
                if (!n.expr.lvalue()) {
                    throw new SemanticError("lvalue expected, got rvalue", n.pos);
                }
            }
            case L_NOT -> {
                if (!n.expr.type().isBool()) {
                    throw mismatchedType(globalScope.getType("bool", false, n.pos), n.expr.type(), n.pos);
                }
            }
        }
        n.type(n.expr.type());
    }

    @Override
    public void visit(NewExprNode n) {
        n.sizes.forEach(x -> {
            x.accept(this);
            if (!x.type().isInt()) {
                throw mismatchedType(globalScope.getType("int", false, x.pos), x.type(), x.pos);
            }
        });
        n.type(getType(n.typeNode, n.dim, n.pos));
        if (n.type().equals(globalScope.getType("void", false, n.pos))) {
            throw new SemanticError("new variable(s) with void type", n.pos);
        }
    }

    @Override
    public void visit(FuncExprNode n) {
        if (n.func instanceof VarExprNode) {
            n.func.type(currentScope.getFunc(((VarExprNode) n.func).name, true, n.pos));
        } else n.func.accept(this);
        if (!(n.func.type().base() instanceof FuncType)) {
            throw new SemanticError(n.func.type().name() + " not a function", n.pos);
        }
        FuncType funcType = (FuncType) n.func.type();
        if (n.params.size() != funcType.param().size()) {
            throw new SemanticError("parameter size mismatched", n.pos);
        }
        n.params.forEach(x -> x.accept(this));
        for (int i = 0; i < n.params.size(); i++) {
            if (!n.params.get(i).type().equals(funcType.param().get(i).type())) {
                throw mismatchedType(funcType.param().get(i).type(), n.params.get(i).type(), n.params.get(i).pos);
            }
        }
        n.type(funcType.retType());
    }

    @Override
    public void visit(VarExprNode n) {
        n.type(currentScope.getVar(n.name, true, n.pos).type());
    }

    @Override
    public void visit(ThisExprNode n) {
        if (currentClass == null) {
            throw new SemanticError("this not in a class", n.pos);
        }
        n.type(currentClass);
    }

    @Override
    public void visit(IntLiteralExprNode n) {
        n.type(globalScope.getType("int", false, n.pos));
    }

    @Override
    public void visit(BoolLiteralExprNode n) {
        n.type(globalScope.getType("bool", false, n.pos));
    }

    @Override
    public void visit(StringLiteralExprNode n) {
        n.type(globalScope.getType("string", false, n.pos));
    }

    @Override
    public void visit(NullLiteralExprNode n) {
        n.type(globalScope.getType("null", false, n.pos));
    }
}
