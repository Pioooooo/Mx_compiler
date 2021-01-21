// Generated from D:/College/2-2/Computer-Architecture/Mx_compiler/src/recognizer\Mx.g4 by ANTLR 4.9
package recognizer;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxParser#translationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranslationUnit(MxParser.TranslationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MxParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpression(MxParser.AssignmentExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpression(MxParser.LogicalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpression(MxParser.LogicalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclusiveOrExpression(MxParser.InclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExclusiveOrExpression(MxParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#andExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(MxParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(MxParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(MxParser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#shiftExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpression(MxParser.ShiftExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(MxParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(MxParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(MxParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#newExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpression(MxParser.NewExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpression(MxParser.PostfixExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(MxParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpression(MxParser.PrimaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#idExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpression(MxParser.IdExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MxParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#boolean_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_literal(MxParser.Boolean_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MxParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(MxParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#compoundStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStatement(MxParser.CompoundStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#statementSeq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementSeq(MxParser.StatementSeqContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#selectionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectionStatement(MxParser.SelectionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#iterationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIterationStatement(MxParser.IterationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#forInitStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInitStatement(MxParser.ForInitStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpStatement(MxParser.JumpStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#declarationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationStatement(MxParser.DeclarationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#declarationSeq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationSeq(MxParser.DeclarationSeqContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(MxParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#simpleDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleDeclaration(MxParser.SimpleDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#initDeclaratorList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitDeclaratorList(MxParser.InitDeclaratorListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#initDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitDeclarator(MxParser.InitDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarator(MxParser.DeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#parametersAndQualifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametersAndQualifiers(MxParser.ParametersAndQualifiersContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#parameterDeclarationList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterDeclarationList(MxParser.ParameterDeclarationListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterDeclaration(MxParser.ParameterDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#initializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializer(MxParser.InitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#typeSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeSpecifier(MxParser.TypeSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#simpleTypeSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleTypeSpecifier(MxParser.SimpleTypeSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#classSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassSpecifier(MxParser.ClassSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(MxParser.FunctionDefinitionContext ctx);
}