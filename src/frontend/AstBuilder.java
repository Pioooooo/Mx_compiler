package frontend;

import ast.AstNode;
import ast.Nodes.*;
import org.antlr.v4.runtime.ParserRuleContext;
import recognizer.MxBaseVisitor;
import recognizer.MxParser.*;
import util.Position;
import util.error.InternalError;
import util.error.SyntaxError;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class AstBuilder extends MxBaseVisitor<AstNode> {
    @Override
    public AstNode visitTranslationUnit(TranslationUnitContext ctx) {
        ArrayList<StmtNode> stmtNodes = new ArrayList<>();
        if (ctx.declarationSeq() != null) {
            ctx.declarationSeq().declaration().forEach(x -> stmtNodes.add((StmtNode) visit(x)));
        }
        return new RootNode(new Position(ctx), stmtNodes);
    }

    @Override
    public AstNode visitExpression(ExpressionContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public AstNode visitAssignmentExpression(AssignmentExpressionContext ctx) {
        ExprNode ret = (ExprNode) visit(ctx.logicalOrExpression(ctx.logicalOrExpression().size() - 1));
        for (int i = ctx.logicalOrExpression().size() - 2; i >= 0; i--) {
            Position pos = new Position(ctx.logicalOrExpression(i));
            ret = new BinaryExprNode(pos, BinaryExprNode.BinaryOpType.ASSIGN,
                    (ExprNode) visit(ctx.logicalOrExpression(i)), ret);
        }
        return ret;
    }

    @Override
    public AstNode visitLogicalOrExpression(LogicalOrExpressionContext ctx) {
        ExprNode ret = (ExprNode) visit(ctx.logicalAndExpression(0));
        for (int i = 1; i < ctx.logicalAndExpression().size(); i++) {
            Position pos = new Position(ctx.logicalAndExpression(i));
            ret = new BinaryExprNode(pos, BinaryExprNode.BinaryOpType.LAND, ret,
                    (ExprNode) visit(ctx.logicalAndExpression(i)));
        }
        return ret;
    }

    @Override
    public AstNode visitLogicalAndExpression(LogicalAndExpressionContext ctx) {
        ExprNode ret = (ExprNode) visit(ctx.inclusiveOrExpression(0));
        for (int i = 1; i < ctx.inclusiveOrExpression().size(); i++) {
            Position pos = new Position(ctx.inclusiveOrExpression(i));
            ret = new BinaryExprNode(pos, BinaryExprNode.BinaryOpType.LOR, ret,
                    (ExprNode) visit(ctx.inclusiveOrExpression(i)));
        }
        return ret;
    }

    @Override
    public AstNode visitInclusiveOrExpression(InclusiveOrExpressionContext ctx) {
        ExprNode ret = (ExprNode) visit(ctx.exclusiveOrExpression(0));
        for (int i = 1; i < ctx.exclusiveOrExpression().size(); i++) {
            Position pos = new Position(ctx.exclusiveOrExpression(i));
            ret = new BinaryExprNode(pos, BinaryExprNode.BinaryOpType.OR, ret,
                    (ExprNode) visit(ctx.exclusiveOrExpression(i)));
        }
        return ret;
    }

    @Override
    public AstNode visitExclusiveOrExpression(ExclusiveOrExpressionContext ctx) {
        ExprNode ret = (ExprNode) visit(ctx.andExpression(0));
        for (int i = 1; i < ctx.andExpression().size(); i++) {
            Position pos = new Position(ctx.andExpression(i));
            ret = new BinaryExprNode(pos, BinaryExprNode.BinaryOpType.XOR, ret, (ExprNode) visit(ctx.andExpression(i)));
        }
        return ret;
    }

    @Override
    public AstNode visitAndExpression(AndExpressionContext ctx) {
        ExprNode ret = (ExprNode) visit(ctx.equalityExpression(0));
        for (int i = 1; i < ctx.equalityExpression().size(); i++) {
            Position pos = new Position(ctx.equalityExpression(i));
            ret = new BinaryExprNode(pos, BinaryExprNode.BinaryOpType.AND, ret,
                    (ExprNode) visit(ctx.equalityExpression(i)));
        }
        return ret;
    }

    @Override
    public AstNode visitEqualityExpression(EqualityExpressionContext ctx) {
        ExprNode ret = (ExprNode) visit(ctx.relationalExpression(0));
        for (int i = 1; i < ctx.relationalExpression().size(); i++) {
            Position pos = new Position(ctx.relationalExpression(i));
            ret = new BinaryExprNode(pos, switch (ctx.op.get(i - 1).getText()) {
                case "==" -> BinaryExprNode.BinaryOpType.EQUAL;
                case "!=" -> BinaryExprNode.BinaryOpType.NEQ;
                default -> throw new InternalError("unexpected value: " + ctx.op.get(i - 1).getText(), pos);
            }, ret, (ExprNode) visit(ctx.relationalExpression(i)));
        }
        return ret;
    }

    @Override
    public AstNode visitRelationalExpression(RelationalExpressionContext ctx) {
        ExprNode ret = (ExprNode) visit(ctx.shiftExpression(0));
        for (int i = 1; i < ctx.shiftExpression().size(); i++) {
            Position pos = new Position(ctx.shiftExpression(i));
            ret = new BinaryExprNode(pos, switch (ctx.op.get(i - 1).getText()) {
                case "<" -> BinaryExprNode.BinaryOpType.LESS;
                case "<=" -> BinaryExprNode.BinaryOpType.LEQ;
                case ">" -> BinaryExprNode.BinaryOpType.GREATER;
                case ">=" -> BinaryExprNode.BinaryOpType.GEQ;
                default -> throw new InternalError("unexpected value: " + ctx.op.get(i - 1).getText(), pos);
            }, ret, (ExprNode) visit(ctx.shiftExpression(i)));
        }
        return ret;
    }

    @Override
    public AstNode visitShiftExpression(ShiftExpressionContext ctx) {
        ExprNode ret = (ExprNode) visit(ctx.additiveExpression(0));
        for (int i = 1; i < ctx.additiveExpression().size(); i++) {
            Position pos = new Position(ctx.additiveExpression(i));
            ret = new BinaryExprNode(pos, switch (ctx.op.get(i - 1).getText()) {
                case "<<" -> BinaryExprNode.BinaryOpType.L_SHIFT;
                case ">>" -> BinaryExprNode.BinaryOpType.R_SHIFT;
                default -> throw new InternalError("unexpected value: " + ctx.op.get(i - 1).getText(), pos);
            }, ret, (ExprNode) visit(ctx.additiveExpression(i)));
        }
        return ret;
    }

    @Override
    public AstNode visitAdditiveExpression(AdditiveExpressionContext ctx) {
        ExprNode ret = (ExprNode) visit(ctx.multiplicativeExpression(0));
        for (int i = 1; i < ctx.multiplicativeExpression().size(); i++) {
            Position pos = new Position(ctx.multiplicativeExpression(i));
            ret = new BinaryExprNode(pos, switch (ctx.op.get(i - 1).getText()) {
                case "+" -> BinaryExprNode.BinaryOpType.ADD;
                case "-" -> BinaryExprNode.BinaryOpType.SUB;
                default -> throw new InternalError("unexpected value: " + ctx.op.get(i - 1).getText(), pos);
            }, ret, (ExprNode) visit(ctx.multiplicativeExpression(i)));
        }
        return ret;
    }

    @Override
    public AstNode visitMultiplicativeExpression(MultiplicativeExpressionContext ctx) {
        ExprNode ret = (ExprNode) visit(ctx.unaryExpression(0));
        for (int i = 1; i < ctx.unaryExpression().size(); i++) {
            Position pos = new Position(ctx.unaryExpression(i));
            ret = new BinaryExprNode(pos, switch (ctx.op.get(i - 1).getText()) {
                case "*" -> BinaryExprNode.BinaryOpType.MUL;
                case "/" -> BinaryExprNode.BinaryOpType.DIV;
                case "%" -> BinaryExprNode.BinaryOpType.MOD;
                default -> throw new InternalError("unexpected value: " + ctx.op.get(i - 1).getText(), pos);
            }, ret, (ExprNode) visit(ctx.unaryExpression(i)));
        }
        return ret;
    }

    @Override
    public AstNode visitUnaryExpression(UnaryExpressionContext ctx) {
        Position pos = new Position(ctx);
        if (ctx.unaryExpression() != null) {
            return new UnaryExprNode(pos, switch (ctx.op.getText()) {
                case "++" -> UnaryExprNode.UnaryOpType.PRE_INC;
                case "--" -> UnaryExprNode.UnaryOpType.PRE_DEC;
                case "+" -> UnaryExprNode.UnaryOpType.POS;
                case "-" -> UnaryExprNode.UnaryOpType.NEG;
                case "!" -> UnaryExprNode.UnaryOpType.L_NOT;
                case "~" -> UnaryExprNode.UnaryOpType.NOT;
                default -> throw new InternalError("unexpected value: " + ctx.op.getText(), pos);
            }, (ExprNode) visit(ctx.unaryExpression()));
        } else {
            return visitChildren(ctx);
        }
    }

    @Override
    public AstNode visitNewExpression(NewExpressionContext ctx) {
        ArrayList<ExprNode> sizes = new ArrayList<>();
        if (ctx.expression() != null) {
            ctx.expression().forEach(x -> sizes.add((ExprNode) visit(x)));
        }
        return new NewExprNode(new Position(ctx), (TypeNode) visit(ctx.typeSpecifier()), ctx.LEFT_BRACKET().size(),
                sizes);
    }

    @Override
    public AstNode visitPostfixExpression(PostfixExpressionContext ctx) {
        Position pos = new Position(ctx);
        if (ctx.op != null) {
            return new UnaryExprNode(pos, switch (ctx.op.getText()) {
                case "++" -> UnaryExprNode.UnaryOpType.POST_INC;
                case "--" -> UnaryExprNode.UnaryOpType.POST_DEC;
                default -> throw new InternalError("unexpected value: " + ctx.op.getText(), pos);
            }, (ExprNode) visit(ctx.postfixExpression()));
        } else if (ctx.LEFT_PAREN() != null) {
            ArrayList<ExprNode> params = new ArrayList<>();
            if (ctx.expressionList() != null) {
                ctx.expressionList().assignmentExpression().forEach(x -> params.add((ExprNode) visit(x)));
            }
            ExprNode expr = (ExprNode) visit(ctx.postfixExpression());
            if (expr instanceof MemberExprNode) {
                ((MemberExprNode) expr).isFunc(true);
            }
            return new FuncExprNode(pos, expr, params);
        } else if (ctx.LEFT_BRACKET() != null) {
            return new BinaryExprNode(pos, BinaryExprNode.BinaryOpType.SUBSCRIPT,
                    (ExprNode) visit(ctx.postfixExpression()), (ExprNode) visit(ctx.expression()));
        } else if (ctx.DOT() != null) {
            return new MemberExprNode(pos, (ExprNode) visit(ctx.postfixExpression()), ctx.idExpression().getText());
        } else {
            return visitChildren(ctx);
        }
    }

    @Override
    public AstNode visitExpressionList(ExpressionListContext ctx) {
        throw new InternalError("call on visitExpressionList", new Position(ctx));
    }

    @Override
    public AstNode visitPrimaryExpression(PrimaryExpressionContext ctx) {
        if (ctx.THIS() != null) {
            return new ThisExprNode(new Position(ctx));
        } else if (ctx.expression() != null) {
            return visit(ctx.expression());
        } else {
            return visitChildren(ctx);
        }
    }

    @Override
    public AstNode visitIdExpression(IdExpressionContext ctx) {
        return new VarExprNode(new Position(ctx), ctx.getText());
    }

    @Override
    public AstNode visitLiteral(LiteralContext ctx) {
        Position pos = new Position(ctx);
        if (ctx.boolean_literal() != null) {
            return new BoolLiteralExprNode(pos, Boolean.parseBoolean(ctx.getText()));
        } else if (ctx.INTEGER_LITERAL() != null) {
            return new IntLiteralExprNode(pos, Integer.parseInt(ctx.getText()));
        } else if (ctx.STRING_LITERAL() != null) {
            return new StringLiteralExprNode(pos, ctx.getText());
        } else {
            return new NullLiteralExprNode(pos);
        }
    }

    @Override
    public AstNode visitStatement(StatementContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public AstNode visitExpressionStatement(ExpressionStatementContext ctx) {
        return new ExprStmtNode(new Position(ctx), ctx.expression() != null ? (ExprNode) visit(ctx.expression()) : null);
    }

    @Override
    public AstNode visitCompoundStatement(CompoundStatementContext ctx) {
        ArrayList<StmtNode> stmts = new ArrayList<>();
        if (ctx.statementSeq() != null) {
            ctx.statementSeq().statement().forEach(x -> {
                StmtNode stmt = (StmtNode) visit(x);
                if (stmt != null) {
                    stmts.add(stmt);
                }
            });
        }
        return new BlockStmtNode(new Position(ctx), stmts);
    }

    @Override
    public AstNode visitStatementSeq(StatementSeqContext ctx) {
        throw new InternalError("call on visitStatementSeq", new Position(ctx));
    }

    @Override
    public AstNode visitSelectionStatement(SelectionStatementContext ctx) {
        return new IfStmtNode(new Position(ctx), (ExprNode) visit(ctx.expression()), (StmtNode) visit(ctx.ifBody),
                ctx.elseBody != null ? (StmtNode) visit(ctx.elseBody) : null);
    }

    @Override
    public AstNode visitIterationStatement(IterationStatementContext ctx) {
        Position pos = new Position(ctx);
        if (ctx.WHILE() != null) {
            return new WhileStmtNode(pos, (ExprNode) visit(ctx.whileCondition), (StmtNode) visit(ctx.statement()));
        } else {
            return new ForStmtNode(pos, (StmtNode) visit(ctx.forInitStatement()),
                    ctx.forCondition != null ? (ExprNode) visit(ctx.forCondition) : null,
                    ctx.forIteration != null ? (ExprNode) visit(ctx.forIteration) : null,
                    (StmtNode) visit(ctx.statement()));
        }
    }

    @Override
    public AstNode visitForInitStatement(ForInitStatementContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public AstNode visitJumpStatement(JumpStatementContext ctx) {
        Position pos = new Position(ctx);
        if (ctx.BREAK() != null) {
            return new BreakStmtNode(pos);
        } else if (ctx.CONTINUE() != null) {
            return new ContinueStmtNode(pos);
        } else {
            return new ReturnStmtNode(pos, ctx.expression() != null ? (ExprNode) visit(ctx.expression()) : null);
        }
    }

    @Override
    public AstNode visitDeclarationStatement(DeclarationStatementContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public AstNode visitDeclarationSeq(DeclarationSeqContext ctx) {
        throw new InternalError("call of visitDeclarationSeq", new Position(ctx));
    }

    @Override
    public AstNode visitDeclaration(DeclarationContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public AstNode visitSimpleDeclaration(SimpleDeclarationContext ctx) {
        if (ctx.typeSpecifier() != null) {
            ArrayList<VarDefSubNode> defList = new ArrayList<>();
            if (ctx.initDeclaratorList() != null) {
                ctx.initDeclaratorList().initDeclarator().forEach(x -> defList.add((VarDefSubNode) visit(x)));
            }
            return new VarDefStmtNode(new Position(ctx), (TypeNode) visit(ctx.typeSpecifier()), defList);
        } else {
            return null;
        }
    }

    @Override
    public AstNode visitInitDeclaratorList(InitDeclaratorListContext ctx) {
        throw new InternalError("call of visitInitDeclaratorList", new Position(ctx));
    }

    @Override
    public AstNode visitInitDeclarator(InitDeclaratorContext ctx) {
        return new VarDefSubNode(new Position(ctx), ctx.declarator().idExpression().getText(),
                ctx.declarator().LEFT_BRACKET().size(),
                ctx.initializer() != null ? (ExprNode) visit(ctx.initializer()) : null);
    }

    @Override
    public AstNode visitDeclarator(DeclaratorContext ctx) {
        throw new InternalError("call of visitDeclarator", new Position(ctx));
    }

    @Override
    public AstNode visitParametersAndQualifiers(ParametersAndQualifiersContext ctx) {
        throw new InternalError("call of visitParametersAndQualifiers", new Position(ctx));
    }

    @Override
    public AstNode visitParameterDeclarationList(ParameterDeclarationListContext ctx) {
        throw new InternalError("call of visitParameterDeclarationList", new Position(ctx));
    }

    @Override
    public AstNode visitParameterDeclaration(ParameterDeclarationContext ctx) {
        return new ParamDefSubNode(new Position(ctx), (TypeNode) visit(ctx.typeSpecifier()),
                ctx.declarator().idExpression().getText(), ctx.declarator().LEFT_BRACKET().size(), null);
    }

    @Override
    public AstNode visitInitializer(InitializerContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public AstNode visitTypeSpecifier(TypeSpecifierContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public AstNode visitSimpleTypeSpecifier(SimpleTypeSpecifierContext ctx) {
        return new TypeNode(new Position(ctx), ctx.getText());
    }

    @Override
    public AstNode visitClassSpecifier(ClassSpecifierContext ctx) {
        ArrayList<ParamDefSubNode> varList = new ArrayList<>();
        ArrayList<FuncDefStmtNode> funcList = new ArrayList<>();
        AtomicReference<FuncDefStmtNode> constructor = new AtomicReference<>(null);
        if (ctx.declarationSeq() != null) {
            ctx.declarationSeq().declaration().forEach(x -> {
                if (x.simpleDeclaration() != null) {
                    x.simpleDeclaration().initDeclaratorList().initDeclarator().forEach(
                            y -> varList.add(new ParamDefSubNode(new Position(y),
                                    (TypeNode) visit(x.simpleDeclaration().typeSpecifier()),
                                    y.declarator().idExpression().getText(), y.declarator().LEFT_BRACKET().size(),
                                    null)));
                } else if (x.functionDefinition().simpleTypeSpecifier() != null) {
                    funcList.add((FuncDefStmtNode) visit(x));
                } else {
                    constructor.set((FuncDefStmtNode) visit(x));
                }
            });
        }
        return new ClassTypeNode(new Position(ctx), ctx.className.getText(), varList, funcList, constructor.get());
    }

    @Override
    public AstNode visitFunctionDefinition(FunctionDefinitionContext ctx) {
        if (ctx.declarator().parametersAndQualifiers() == null) {
            throw new SyntaxError("expected (", new Position(ctx.declarator().parametersAndQualifiers()));
        }
        ArrayList<ParamDefSubNode> params = new ArrayList<>();
        if (ctx.declarator().parametersAndQualifiers().parameterDeclarationList() != null) {
            ctx.declarator().parametersAndQualifiers().parameterDeclarationList().parameterDeclaration().forEach(
                    x -> params.add((ParamDefSubNode) visit(x))
            );
        }
        return new FuncDefStmtNode(new Position(ctx), ctx.declarator().idExpression().getText(),
                ctx.simpleTypeSpecifier() != null ? (TypeNode) visit(ctx.simpleTypeSpecifier()) : null,
                ctx.declarator().LEFT_BRACKET().size(), params, ((BlockStmtNode) visit(ctx.compoundStatement())).stmts,
                ctx.simpleTypeSpecifier() == null);
    }
}
