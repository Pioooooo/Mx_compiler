package frontend;

import ast.AstVisitor;
import ast.Nodes.*;
import frontend.scope.ClassScope;
import frontend.scope.Scope;
import frontend.symbol.Entity;
import frontend.symbol.type.*;
import ir.Function;
import ir.Module;
import ir.type.FunctionType;
import util.Position;
import util.error.SemanticError;

import java.util.ArrayList;
import java.util.Stack;

public class SemanticChecker implements AstVisitor<Void> {
    final Scope globalScope;
    final Module module;
    Scope currentScope;
    FuncDefStmtNode currentFunction;
    ClassType currentClass;
    Type currentRetType;
    boolean returned;
    Stack<LoopStmtNode> loopStack = new Stack<>();

    public SemanticChecker(Scope globalScope, Module module) {
        this.globalScope = globalScope;
        this.module = module;
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
    public Void visit(RootNode n) {
        FuncType main = globalScope.getFunc("main", false, n.pos);
        if (!main.retType().isInt()) {
            throw new SemanticError("main with return type " + main.retType().name(), n.pos);
        } else if (main.param().size() != 0) {
            throw new SemanticError("main with parameter(s)", n.pos);
        }
        currentScope = globalScope;
        n.stmts.forEach(x -> x.accept(this));
        return null;
    }

    @Override
    public Void visit(BlockStmtNode n) {
        currentScope = new Scope(currentScope);
        n.stmts.forEach(x -> x.accept(this));
        currentScope = currentScope.parent();
        return null;
    }

    @Override
    public Void visit(VarDefStmtNode n) {
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
            x.entity = currentScope.defVar(x.name, new Entity(x.name, varType), x.pos);
        });
        return null;
    }

    @Override
    public Void visit(VarDefSubNode n) {
        return null;
    }

    @Override
    public Void visit(ExprStmtNode n) {
        if (n.expr != null) {
            n.expr.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(FuncDefStmtNode n) {
        if (n.retType != null) {
            currentRetType = getType(n.retType, n.dim, n.pos);
        } else {
            currentRetType = globalScope.getType("void", false, n.pos);
        }
        currentFunction = n;
        returned = false;
        currentScope = new Scope(currentScope);
        n.params.forEach(x -> x.entity = currentScope.defVar(x.name, new Entity(x.name, getType(x.type, x.dim, x.pos)), x.pos));
        n.funcBody.forEach(x -> x.accept(this));
        currentScope = currentScope.parent();
        if (!n.funcName.equals("main") && !currentRetType.isVoid() && !returned) {
            throw new SemanticError("function " + n.funcName + " not returned", n.pos);
        }
        FuncType funcType = currentScope.getFunc(n.funcName, true, n.pos);
        if (!n.funcName.equals("main")) {
            n.funcName = (currentClass == null ? "g_" : "c_" + currentClass.name() + "_") + n.funcName;
        }
        ArrayList<ir.Type> params = new ArrayList<>();
        if (currentClass != null) {
            params.add(currentClass.irType(module));
        }
        funcType.param().forEach(x -> params.add(x.type().irType(module)));
        n.function = Function.create(FunctionType.get(funcType.retType().irType(module), params), module, n.funcName);
        if (currentClass != null) {
            n.function.addArg(params.get(0));
        }
        n.params.forEach(x -> x.entity.setValue(n.function.addArg(x.entity.type().irType(module))));
        currentRetType = null;
        return null;
    }

    @Override
    public Void visit(ParamDefSubNode n) {
        return null;
    }

    @Override
    public Void visit(IfStmtNode n) {
        n.condition.accept(this);
        if (!n.condition.type().isBool()) {
            throw mismatchedType(globalScope.getType("bool", false, n.pos), n.condition.type(), n.pos);
        }
        currentScope = new Scope(currentScope);
        if (n.trueBody instanceof BlockStmtNode) {
            ((BlockStmtNode) n.trueBody).stmts.forEach(x -> x.accept(this));
        } else if (n.trueBody != null) {
            n.trueBody.accept(this);
        }
        currentScope = currentScope.parent();
        if (n.falseBody != null) {
            currentScope = new Scope(currentScope);
            if (n.falseBody instanceof BlockStmtNode) {
                ((BlockStmtNode) n.falseBody).stmts.forEach(x -> x.accept(this));
            } else {
                n.falseBody.accept(this);
            }
            currentScope = currentScope.parent();
        }
        return null;
    }

    @Override
    public Void visit(ForStmtNode n) {
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
        loopStack.push(n);
        if (n.forBody instanceof BlockStmtNode) {
            ((BlockStmtNode) n.forBody).stmts.forEach(x -> x.accept(this));
        } else if (n.forBody != null) {
            n.forBody.accept(this);
        }
        loopStack.pop();
        currentScope = currentScope.parent();
        return null;
    }

    @Override
    public Void visit(WhileStmtNode n) {
        currentScope = new Scope(currentScope);
        n.condition.accept(this);
        if (!n.condition.type().isBool()) {
            throw mismatchedType(globalScope.getType("bool", false, n.pos), n.condition.type(), n.pos);
        }
        loopStack.push(n);
        if (n.whileBody != null) {
            if (n.whileBody instanceof BlockStmtNode) {
                ((BlockStmtNode) n.whileBody).stmts.forEach(x -> x.accept(this));
            } else {
                n.whileBody.accept(this);
            }
        }
        loopStack.pop();
        currentScope = currentScope.parent();
        return null;
    }

    @Override
    public Void visit(BreakStmtNode n) {
        if (loopStack.empty()) {
            throw new SemanticError("nothing to break", n.pos);
        }
        n.dest = loopStack.peek();
        return null;
    }

    @Override
    public Void visit(ContinueStmtNode n) {
        if (loopStack.empty()) {
            throw new SemanticError("nothing to continue", n.pos);
        }
        n.dest = loopStack.peek();
        return null;
    }

    @Override
    public Void visit(ReturnStmtNode n) {
        if (currentRetType == null) {
            throw new SemanticError("nothing to return", n.pos);
        }
        n.dest = currentFunction;
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
        return null;
    }

    @Override
    public Void visit(TypeNode n) {
        return null;
    }

    @Override
    public Void visit(ClassTypeNode n) {
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
        return null;
    }

    @Override
    public Void visit(BinaryExprNode n) {
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
                n.type(n.lhs.type());
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
        return null;
    }

    @Override
    public Void visit(MemberExprNode n) {
        n.base.accept(this);
        if (n.base.type() instanceof ArrayType && n.isFunc && n.name.equals("size")) {
            n.type(new FuncType("size", globalScope.getType("int", false, n.pos), new ArrayList<>()));
            return null;
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
            n.entity = classType.varMap().get(n.name);
            n.type(n.entity.type());
        }
        return null;
    }

    @Override
    public Void visit(UnaryExprNode n) {
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
        return null;
    }

    @Override
    public Void visit(NewExprNode n) {
        n.sizes.forEach(x -> {
            x.accept(this);
            if (!x.type().isInt()) {
                throw mismatchedType(globalScope.getType("int", false, x.pos), x.type(), x.pos);
            }
        });
        n.type(getType(n.type, n.dim, n.pos));
        if (n.type().equals(globalScope.getType("void", false, n.pos))) {
            throw new SemanticError("new variable(s) with void type", n.pos);
        }
        return null;
    }

    @Override
    public Void visit(FuncExprNode n) {
        if (n.func instanceof VarExprNode) {
            n.func.type(currentScope.getFunc(((VarExprNode) n.func).name, true, n.pos));
            if (!globalScope.containsFunc(((VarExprNode) n.func).name, false)) {
                n.functionName = "c_" + currentClass.name() + "_" + ((VarExprNode) n.func).name;
                n.isMember = true;
            } else {
                n.functionName = "g_" + ((VarExprNode) n.func).name;
            }
        } else {
            n.func.accept(this);
            n.functionName = "c_" + ((MemberExprNode) n.func).base.type().name() + "_" + ((MemberExprNode) n.func).name;
        }
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
        return null;
    }

    @Override
    public Void visit(VarExprNode n) {
        n.entity = currentScope.getVar(n.name, true, n.pos);
        n.type(n.entity.type());
        return null;
    }

    @Override
    public Void visit(ThisExprNode n) {
        if (currentClass == null) {
            throw new SemanticError("this not in a class", n.pos);
        }
        n.type(currentClass);
        return null;
    }

    @Override
    public Void visit(IntLiteralExprNode n) {
        n.type(globalScope.getType("int", false, n.pos));
        return null;
    }

    @Override
    public Void visit(BoolLiteralExprNode n) {
        n.type(globalScope.getType("bool", false, n.pos));
        return null;
    }

    @Override
    public Void visit(StringLiteralExprNode n) {
        n.type(globalScope.getType("string", false, n.pos));
        return null;
    }

    @Override
    public Void visit(NullLiteralExprNode n) {
        n.type(globalScope.getType("null", false, n.pos));
        return null;
    }
}
