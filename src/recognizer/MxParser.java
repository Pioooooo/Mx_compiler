// Generated from D:/College/2-2/Computer-Architecture/Mx_compiler/src/recognizer\Mx.g4 by ANTLR 4.9
package recognizer;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BOOL=1, INT=2, STRING=3, NULL=4, VOID=5, TRUE=6, FALSE=7, IF=8, ELSE=9, 
		FOR=10, WHILE=11, BREAK=12, CONTINUE=13, RETURN=14, NEW=15, CLASS=16, 
		THIS=17, LEFT_PAREN=18, RIGHT_PAREN=19, LEFT_BRACKET=20, RIGHT_BRACKET=21, 
		LEFT_BRACE=22, RIGHT_BRACE=23, PLUS=24, MINUS=25, STAR=26, DIV=27, MOD=28, 
		CARET=29, AND=30, OR=31, TILDE=32, NOT=33, LESS_LESS=34, GREATER_GREATER=35, 
		ASSIGN=36, LESS=37, GREATER=38, EQUAL=39, NOT_EQUAL=40, LESS_EQUAL=41, 
		GREATER_EQUAL=42, AND_AND=43, OR_OR=44, PLUS_PLUS=45, MINUS_MINUS=46, 
		COMMA=47, DOT=48, SEMI=49, IDENTIFIER=50, INTEGER_LITERAL=51, STRING_LITERAL=52, 
		DECIMAL_LITERAL=53, WHITESPACE=54, NEWLINE=55, BLOCK_COMMENT=56, LINE_COMMENT=57;
	public static final int
		RULE_translationUnit = 0, RULE_expression = 1, RULE_assignmentExpression = 2, 
		RULE_logicalOrExpression = 3, RULE_logicalAndExpression = 4, RULE_inclusiveOrExpression = 5, 
		RULE_exclusiveOrExpression = 6, RULE_andExpression = 7, RULE_equalityExpression = 8, 
		RULE_relationalExpression = 9, RULE_shiftExpression = 10, RULE_additiveExpression = 11, 
		RULE_multiplicativeExpression = 12, RULE_unaryExpression = 13, RULE_newExpression = 14, 
		RULE_postfixExpression = 15, RULE_expressionList = 16, RULE_primaryExpression = 17, 
		RULE_idExpression = 18, RULE_literal = 19, RULE_boolean_literal = 20, 
		RULE_statement = 21, RULE_expressionStatement = 22, RULE_compoundStatement = 23, 
		RULE_statementSeq = 24, RULE_selectionStatement = 25, RULE_iterationStatement = 26, 
		RULE_forInitStatement = 27, RULE_jumpStatement = 28, RULE_declarationStatement = 29, 
		RULE_declarationSeq = 30, RULE_declaration = 31, RULE_simpleDeclaration = 32, 
		RULE_initDeclaratorList = 33, RULE_initDeclarator = 34, RULE_declarator = 35, 
		RULE_parametersAndQualifiers = 36, RULE_parameterDeclarationList = 37, 
		RULE_parameterDeclaration = 38, RULE_initializer = 39, RULE_typeSpecifier = 40, 
		RULE_simpleTypeSpecifier = 41, RULE_classSpecifier = 42, RULE_functionDefinition = 43;
	private static String[] makeRuleNames() {
		return new String[] {
			"translationUnit", "expression", "assignmentExpression", "logicalOrExpression", 
			"logicalAndExpression", "inclusiveOrExpression", "exclusiveOrExpression", 
			"andExpression", "equalityExpression", "relationalExpression", "shiftExpression", 
			"additiveExpression", "multiplicativeExpression", "unaryExpression", 
			"newExpression", "postfixExpression", "expressionList", "primaryExpression", 
			"idExpression", "literal", "boolean_literal", "statement", "expressionStatement", 
			"compoundStatement", "statementSeq", "selectionStatement", "iterationStatement", 
			"forInitStatement", "jumpStatement", "declarationStatement", "declarationSeq", 
			"declaration", "simpleDeclaration", "initDeclaratorList", "initDeclarator", 
			"declarator", "parametersAndQualifiers", "parameterDeclarationList", 
			"parameterDeclaration", "initializer", "typeSpecifier", "simpleTypeSpecifier", 
			"classSpecifier", "functionDefinition"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'bool'", "'int'", "'string'", "'null'", "'void'", "'true'", "'false'", 
			"'if'", "'else'", "'for'", "'while'", "'break'", "'continue'", "'return'", 
			"'new'", "'class'", "'this'", "'('", "')'", "'['", "']'", "'{'", "'}'", 
			"'+'", "'-'", "'*'", "'/'", "'%'", "'^'", "'&'", "'|'", "'~'", "'!'", 
			"'<<'", "'>>'", "'='", "'<'", "'>'", "'=='", "'!='", "'<='", "'>='", 
			"'&&'", "'||'", "'++'", "'--'", "','", "'.'", "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BOOL", "INT", "STRING", "NULL", "VOID", "TRUE", "FALSE", "IF", 
			"ELSE", "FOR", "WHILE", "BREAK", "CONTINUE", "RETURN", "NEW", "CLASS", 
			"THIS", "LEFT_PAREN", "RIGHT_PAREN", "LEFT_BRACKET", "RIGHT_BRACKET", 
			"LEFT_BRACE", "RIGHT_BRACE", "PLUS", "MINUS", "STAR", "DIV", "MOD", "CARET", 
			"AND", "OR", "TILDE", "NOT", "LESS_LESS", "GREATER_GREATER", "ASSIGN", 
			"LESS", "GREATER", "EQUAL", "NOT_EQUAL", "LESS_EQUAL", "GREATER_EQUAL", 
			"AND_AND", "OR_OR", "PLUS_PLUS", "MINUS_MINUS", "COMMA", "DOT", "SEMI", 
			"IDENTIFIER", "INTEGER_LITERAL", "STRING_LITERAL", "DECIMAL_LITERAL", 
			"WHITESPACE", "NEWLINE", "BLOCK_COMMENT", "LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Mx.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MxParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class TranslationUnitContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MxParser.EOF, 0); }
		public DeclarationSeqContext declarationSeq() {
			return getRuleContext(DeclarationSeqContext.class,0);
		}
		public TranslationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_translationUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterTranslationUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitTranslationUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitTranslationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TranslationUnitContext translationUnit() throws RecognitionException {
		TranslationUnitContext _localctx = new TranslationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_translationUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << STRING) | (1L << VOID) | (1L << CLASS) | (1L << LEFT_BRACKET) | (1L << SEMI) | (1L << IDENTIFIER))) != 0)) {
				{
				setState(88);
				declarationSeq();
				}
			}

			setState(91);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public List<AssignmentExpressionContext> assignmentExpression() {
			return getRuleContexts(AssignmentExpressionContext.class);
		}
		public AssignmentExpressionContext assignmentExpression(int i) {
			return getRuleContext(AssignmentExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MxParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MxParser.COMMA, i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			assignmentExpression();
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(94);
				match(COMMA);
				setState(95);
				assignmentExpression();
				}
				}
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentExpressionContext extends ParserRuleContext {
		public List<LogicalOrExpressionContext> logicalOrExpression() {
			return getRuleContexts(LogicalOrExpressionContext.class);
		}
		public LogicalOrExpressionContext logicalOrExpression(int i) {
			return getRuleContext(LogicalOrExpressionContext.class,i);
		}
		public List<TerminalNode> ASSIGN() { return getTokens(MxParser.ASSIGN); }
		public TerminalNode ASSIGN(int i) {
			return getToken(MxParser.ASSIGN, i);
		}
		public AssignmentExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterAssignmentExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitAssignmentExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitAssignmentExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentExpressionContext assignmentExpression() throws RecognitionException {
		AssignmentExpressionContext _localctx = new AssignmentExpressionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_assignmentExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			logicalOrExpression();
			setState(106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ASSIGN) {
				{
				{
				setState(102);
				match(ASSIGN);
				setState(103);
				logicalOrExpression();
				}
				}
				setState(108);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalOrExpressionContext extends ParserRuleContext {
		public List<LogicalAndExpressionContext> logicalAndExpression() {
			return getRuleContexts(LogicalAndExpressionContext.class);
		}
		public LogicalAndExpressionContext logicalAndExpression(int i) {
			return getRuleContext(LogicalAndExpressionContext.class,i);
		}
		public List<TerminalNode> OR_OR() { return getTokens(MxParser.OR_OR); }
		public TerminalNode OR_OR(int i) {
			return getToken(MxParser.OR_OR, i);
		}
		public LogicalOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOrExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterLogicalOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitLogicalOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitLogicalOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalOrExpressionContext logicalOrExpression() throws RecognitionException {
		LogicalOrExpressionContext _localctx = new LogicalOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_logicalOrExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			logicalAndExpression();
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR_OR) {
				{
				{
				setState(110);
				match(OR_OR);
				setState(111);
				logicalAndExpression();
				}
				}
				setState(116);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalAndExpressionContext extends ParserRuleContext {
		public List<InclusiveOrExpressionContext> inclusiveOrExpression() {
			return getRuleContexts(InclusiveOrExpressionContext.class);
		}
		public InclusiveOrExpressionContext inclusiveOrExpression(int i) {
			return getRuleContext(InclusiveOrExpressionContext.class,i);
		}
		public List<TerminalNode> AND_AND() { return getTokens(MxParser.AND_AND); }
		public TerminalNode AND_AND(int i) {
			return getToken(MxParser.AND_AND, i);
		}
		public LogicalAndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalAndExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterLogicalAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitLogicalAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitLogicalAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalAndExpressionContext logicalAndExpression() throws RecognitionException {
		LogicalAndExpressionContext _localctx = new LogicalAndExpressionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_logicalAndExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			inclusiveOrExpression();
			setState(122);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND_AND) {
				{
				{
				setState(118);
				match(AND_AND);
				setState(119);
				inclusiveOrExpression();
				}
				}
				setState(124);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InclusiveOrExpressionContext extends ParserRuleContext {
		public List<ExclusiveOrExpressionContext> exclusiveOrExpression() {
			return getRuleContexts(ExclusiveOrExpressionContext.class);
		}
		public ExclusiveOrExpressionContext exclusiveOrExpression(int i) {
			return getRuleContext(ExclusiveOrExpressionContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(MxParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(MxParser.OR, i);
		}
		public InclusiveOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inclusiveOrExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterInclusiveOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitInclusiveOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitInclusiveOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InclusiveOrExpressionContext inclusiveOrExpression() throws RecognitionException {
		InclusiveOrExpressionContext _localctx = new InclusiveOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_inclusiveOrExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			exclusiveOrExpression();
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(126);
				match(OR);
				setState(127);
				exclusiveOrExpression();
				}
				}
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExclusiveOrExpressionContext extends ParserRuleContext {
		public List<AndExpressionContext> andExpression() {
			return getRuleContexts(AndExpressionContext.class);
		}
		public AndExpressionContext andExpression(int i) {
			return getRuleContext(AndExpressionContext.class,i);
		}
		public List<TerminalNode> CARET() { return getTokens(MxParser.CARET); }
		public TerminalNode CARET(int i) {
			return getToken(MxParser.CARET, i);
		}
		public ExclusiveOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exclusiveOrExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterExclusiveOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitExclusiveOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitExclusiveOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExclusiveOrExpressionContext exclusiveOrExpression() throws RecognitionException {
		ExclusiveOrExpressionContext _localctx = new ExclusiveOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_exclusiveOrExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			andExpression();
			setState(138);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CARET) {
				{
				{
				setState(134);
				match(CARET);
				setState(135);
				andExpression();
				}
				}
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndExpressionContext extends ParserRuleContext {
		public List<EqualityExpressionContext> equalityExpression() {
			return getRuleContexts(EqualityExpressionContext.class);
		}
		public EqualityExpressionContext equalityExpression(int i) {
			return getRuleContext(EqualityExpressionContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(MxParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(MxParser.AND, i);
		}
		public AndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AndExpressionContext andExpression() throws RecognitionException {
		AndExpressionContext _localctx = new AndExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_andExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			equalityExpression();
			setState(146);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(142);
				match(AND);
				setState(143);
				equalityExpression();
				}
				}
				setState(148);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqualityExpressionContext extends ParserRuleContext {
		public Token EQUAL;
		public List<Token> op = new ArrayList<Token>();
		public Token NOT_EQUAL;
		public Token _tset116;
		public List<RelationalExpressionContext> relationalExpression() {
			return getRuleContexts(RelationalExpressionContext.class);
		}
		public RelationalExpressionContext relationalExpression(int i) {
			return getRuleContext(RelationalExpressionContext.class,i);
		}
		public List<TerminalNode> EQUAL() { return getTokens(MxParser.EQUAL); }
		public TerminalNode EQUAL(int i) {
			return getToken(MxParser.EQUAL, i);
		}
		public List<TerminalNode> NOT_EQUAL() { return getTokens(MxParser.NOT_EQUAL); }
		public TerminalNode NOT_EQUAL(int i) {
			return getToken(MxParser.NOT_EQUAL, i);
		}
		public EqualityExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterEqualityExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitEqualityExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitEqualityExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExpressionContext equalityExpression() throws RecognitionException {
		EqualityExpressionContext _localctx = new EqualityExpressionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_equalityExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			relationalExpression();
			setState(154);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EQUAL || _la==NOT_EQUAL) {
				{
				{
				setState(150);
				((EqualityExpressionContext)_localctx)._tset116 = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((EqualityExpressionContext)_localctx)._tset116 = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				((EqualityExpressionContext)_localctx).op.add(((EqualityExpressionContext)_localctx)._tset116);
				setState(151);
				relationalExpression();
				}
				}
				setState(156);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelationalExpressionContext extends ParserRuleContext {
		public Token LESS;
		public List<Token> op = new ArrayList<Token>();
		public Token LESS_EQUAL;
		public Token GREATER;
		public Token GREATER_EQUAL;
		public Token _tset139;
		public List<ShiftExpressionContext> shiftExpression() {
			return getRuleContexts(ShiftExpressionContext.class);
		}
		public ShiftExpressionContext shiftExpression(int i) {
			return getRuleContext(ShiftExpressionContext.class,i);
		}
		public List<TerminalNode> LESS() { return getTokens(MxParser.LESS); }
		public TerminalNode LESS(int i) {
			return getToken(MxParser.LESS, i);
		}
		public List<TerminalNode> LESS_EQUAL() { return getTokens(MxParser.LESS_EQUAL); }
		public TerminalNode LESS_EQUAL(int i) {
			return getToken(MxParser.LESS_EQUAL, i);
		}
		public List<TerminalNode> GREATER() { return getTokens(MxParser.GREATER); }
		public TerminalNode GREATER(int i) {
			return getToken(MxParser.GREATER, i);
		}
		public List<TerminalNode> GREATER_EQUAL() { return getTokens(MxParser.GREATER_EQUAL); }
		public TerminalNode GREATER_EQUAL(int i) {
			return getToken(MxParser.GREATER_EQUAL, i);
		}
		public RelationalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterRelationalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitRelationalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitRelationalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalExpressionContext relationalExpression() throws RecognitionException {
		RelationalExpressionContext _localctx = new RelationalExpressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_relationalExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(157);
			shiftExpression();
			setState(162);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LESS) | (1L << GREATER) | (1L << LESS_EQUAL) | (1L << GREATER_EQUAL))) != 0)) {
				{
				{
				setState(158);
				((RelationalExpressionContext)_localctx)._tset139 = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LESS) | (1L << GREATER) | (1L << LESS_EQUAL) | (1L << GREATER_EQUAL))) != 0)) ) {
					((RelationalExpressionContext)_localctx)._tset139 = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				((RelationalExpressionContext)_localctx).op.add(((RelationalExpressionContext)_localctx)._tset139);
				setState(159);
				shiftExpression();
				}
				}
				setState(164);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ShiftExpressionContext extends ParserRuleContext {
		public Token LESS_LESS;
		public List<Token> op = new ArrayList<Token>();
		public Token GREATER_GREATER;
		public Token _tset170;
		public List<AdditiveExpressionContext> additiveExpression() {
			return getRuleContexts(AdditiveExpressionContext.class);
		}
		public AdditiveExpressionContext additiveExpression(int i) {
			return getRuleContext(AdditiveExpressionContext.class,i);
		}
		public List<TerminalNode> LESS_LESS() { return getTokens(MxParser.LESS_LESS); }
		public TerminalNode LESS_LESS(int i) {
			return getToken(MxParser.LESS_LESS, i);
		}
		public List<TerminalNode> GREATER_GREATER() { return getTokens(MxParser.GREATER_GREATER); }
		public TerminalNode GREATER_GREATER(int i) {
			return getToken(MxParser.GREATER_GREATER, i);
		}
		public ShiftExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shiftExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterShiftExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitShiftExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitShiftExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShiftExpressionContext shiftExpression() throws RecognitionException {
		ShiftExpressionContext _localctx = new ShiftExpressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_shiftExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			additiveExpression();
			setState(170);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LESS_LESS || _la==GREATER_GREATER) {
				{
				{
				setState(166);
				((ShiftExpressionContext)_localctx)._tset170 = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==LESS_LESS || _la==GREATER_GREATER) ) {
					((ShiftExpressionContext)_localctx)._tset170 = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				((ShiftExpressionContext)_localctx).op.add(((ShiftExpressionContext)_localctx)._tset170);
				setState(167);
				additiveExpression();
				}
				}
				setState(172);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AdditiveExpressionContext extends ParserRuleContext {
		public Token PLUS;
		public List<Token> op = new ArrayList<Token>();
		public Token MINUS;
		public Token _tset193;
		public List<MultiplicativeExpressionContext> multiplicativeExpression() {
			return getRuleContexts(MultiplicativeExpressionContext.class);
		}
		public MultiplicativeExpressionContext multiplicativeExpression(int i) {
			return getRuleContext(MultiplicativeExpressionContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(MxParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(MxParser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(MxParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(MxParser.MINUS, i);
		}
		public AdditiveExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterAdditiveExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitAdditiveExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitAdditiveExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveExpressionContext additiveExpression() throws RecognitionException {
		AdditiveExpressionContext _localctx = new AdditiveExpressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_additiveExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			multiplicativeExpression();
			setState(178);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MINUS) {
				{
				{
				setState(174);
				((AdditiveExpressionContext)_localctx)._tset193 = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
					((AdditiveExpressionContext)_localctx)._tset193 = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				((AdditiveExpressionContext)_localctx).op.add(((AdditiveExpressionContext)_localctx)._tset193);
				setState(175);
				multiplicativeExpression();
				}
				}
				setState(180);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiplicativeExpressionContext extends ParserRuleContext {
		public Token STAR;
		public List<Token> op = new ArrayList<Token>();
		public Token DIV;
		public Token MOD;
		public Token _tset215;
		public List<UnaryExpressionContext> unaryExpression() {
			return getRuleContexts(UnaryExpressionContext.class);
		}
		public UnaryExpressionContext unaryExpression(int i) {
			return getRuleContext(UnaryExpressionContext.class,i);
		}
		public List<TerminalNode> STAR() { return getTokens(MxParser.STAR); }
		public TerminalNode STAR(int i) {
			return getToken(MxParser.STAR, i);
		}
		public List<TerminalNode> DIV() { return getTokens(MxParser.DIV); }
		public TerminalNode DIV(int i) {
			return getToken(MxParser.DIV, i);
		}
		public List<TerminalNode> MOD() { return getTokens(MxParser.MOD); }
		public TerminalNode MOD(int i) {
			return getToken(MxParser.MOD, i);
		}
		public MultiplicativeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterMultiplicativeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitMultiplicativeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitMultiplicativeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeExpressionContext multiplicativeExpression() throws RecognitionException {
		MultiplicativeExpressionContext _localctx = new MultiplicativeExpressionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_multiplicativeExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			unaryExpression();
			setState(186);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STAR) | (1L << DIV) | (1L << MOD))) != 0)) {
				{
				{
				setState(182);
				((MultiplicativeExpressionContext)_localctx)._tset215 = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STAR) | (1L << DIV) | (1L << MOD))) != 0)) ) {
					((MultiplicativeExpressionContext)_localctx)._tset215 = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				((MultiplicativeExpressionContext)_localctx).op.add(((MultiplicativeExpressionContext)_localctx)._tset215);
				setState(183);
				unaryExpression();
				}
				}
				setState(188);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryExpressionContext extends ParserRuleContext {
		public Token op;
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public TerminalNode PLUS_PLUS() { return getToken(MxParser.PLUS_PLUS, 0); }
		public TerminalNode MINUS_MINUS() { return getToken(MxParser.MINUS_MINUS, 0); }
		public TerminalNode PLUS() { return getToken(MxParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MxParser.MINUS, 0); }
		public TerminalNode NOT() { return getToken(MxParser.NOT, 0); }
		public TerminalNode TILDE() { return getToken(MxParser.TILDE, 0); }
		public NewExpressionContext newExpression() {
			return getRuleContext(NewExpressionContext.class,0);
		}
		public UnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExpressionContext unaryExpression() throws RecognitionException {
		UnaryExpressionContext _localctx = new UnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_unaryExpression);
		int _la;
		try {
			setState(193);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NULL:
			case TRUE:
			case FALSE:
			case THIS:
			case LEFT_PAREN:
			case IDENTIFIER:
			case INTEGER_LITERAL:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(189);
				postfixExpression(0);
				}
				break;
			case PLUS:
			case MINUS:
			case TILDE:
			case NOT:
			case PLUS_PLUS:
			case MINUS_MINUS:
				enterOuterAlt(_localctx, 2);
				{
				setState(190);
				((UnaryExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << MINUS) | (1L << TILDE) | (1L << NOT) | (1L << PLUS_PLUS) | (1L << MINUS_MINUS))) != 0)) ) {
					((UnaryExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(191);
				unaryExpression();
				}
				break;
			case NEW:
				enterOuterAlt(_localctx, 3);
				{
				setState(192);
				newExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewExpressionContext extends ParserRuleContext {
		public TerminalNode NEW() { return getToken(MxParser.NEW, 0); }
		public TypeSpecifierContext typeSpecifier() {
			return getRuleContext(TypeSpecifierContext.class,0);
		}
		public TerminalNode LEFT_PAREN() { return getToken(MxParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(MxParser.RIGHT_PAREN, 0); }
		public List<TerminalNode> LEFT_BRACKET() { return getTokens(MxParser.LEFT_BRACKET); }
		public TerminalNode LEFT_BRACKET(int i) {
			return getToken(MxParser.LEFT_BRACKET, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> RIGHT_BRACKET() { return getTokens(MxParser.RIGHT_BRACKET); }
		public TerminalNode RIGHT_BRACKET(int i) {
			return getToken(MxParser.RIGHT_BRACKET, i);
		}
		public NewExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterNewExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitNewExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitNewExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewExpressionContext newExpression() throws RecognitionException {
		NewExpressionContext _localctx = new NewExpressionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_newExpression);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			match(NEW);
			setState(196);
			typeSpecifier();
			setState(214);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LEFT_BRACKET:
				{
				setState(201); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(197);
						match(LEFT_BRACKET);
						setState(198);
						expression();
						setState(199);
						match(RIGHT_BRACKET);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(203); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(209);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==LEFT_BRACKET) {
					{
					{
					setState(205);
					match(LEFT_BRACKET);
					setState(206);
					match(RIGHT_BRACKET);
					}
					}
					setState(211);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case LEFT_PAREN:
				{
				{
				setState(212);
				match(LEFT_PAREN);
				setState(213);
				match(RIGHT_PAREN);
				}
				}
				break;
			case RIGHT_PAREN:
			case RIGHT_BRACKET:
			case PLUS:
			case MINUS:
			case STAR:
			case DIV:
			case MOD:
			case CARET:
			case AND:
			case OR:
			case LESS_LESS:
			case GREATER_GREATER:
			case ASSIGN:
			case LESS:
			case GREATER:
			case EQUAL:
			case NOT_EQUAL:
			case LESS_EQUAL:
			case GREATER_EQUAL:
			case AND_AND:
			case OR_OR:
			case COMMA:
			case SEMI:
				break;
			default:
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PostfixExpressionContext extends ParserRuleContext {
		public Token op;
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public PostfixExpressionContext postfixExpression() {
			return getRuleContext(PostfixExpressionContext.class,0);
		}
		public TerminalNode PLUS_PLUS() { return getToken(MxParser.PLUS_PLUS, 0); }
		public TerminalNode MINUS_MINUS() { return getToken(MxParser.MINUS_MINUS, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(MxParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(MxParser.RIGHT_PAREN, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public TerminalNode LEFT_BRACKET() { return getToken(MxParser.LEFT_BRACKET, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RIGHT_BRACKET() { return getToken(MxParser.RIGHT_BRACKET, 0); }
		public TerminalNode DOT() { return getToken(MxParser.DOT, 0); }
		public IdExpressionContext idExpression() {
			return getRuleContext(IdExpressionContext.class,0);
		}
		public PostfixExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterPostfixExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitPostfixExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitPostfixExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PostfixExpressionContext postfixExpression() throws RecognitionException {
		return postfixExpression(0);
	}

	private PostfixExpressionContext postfixExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PostfixExpressionContext _localctx = new PostfixExpressionContext(_ctx, _parentState);
		PostfixExpressionContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_postfixExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(217);
			primaryExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(237);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(235);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(219);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(220);
						((PostfixExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS_PLUS || _la==MINUS_MINUS) ) {
							((PostfixExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					case 2:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(221);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(222);
						match(LEFT_PAREN);
						setState(224);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NULL) | (1L << TRUE) | (1L << FALSE) | (1L << NEW) | (1L << THIS) | (1L << LEFT_PAREN) | (1L << PLUS) | (1L << MINUS) | (1L << TILDE) | (1L << NOT) | (1L << PLUS_PLUS) | (1L << MINUS_MINUS) | (1L << IDENTIFIER) | (1L << INTEGER_LITERAL) | (1L << STRING_LITERAL))) != 0)) {
							{
							setState(223);
							expressionList();
							}
						}

						setState(226);
						match(RIGHT_PAREN);
						}
						break;
					case 3:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(227);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(228);
						match(LEFT_BRACKET);
						setState(229);
						expression();
						setState(230);
						match(RIGHT_BRACKET);
						}
						break;
					case 4:
						{
						_localctx = new PostfixExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpression);
						setState(232);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(233);
						match(DOT);
						setState(234);
						idExpression();
						}
						break;
					}
					} 
				}
				setState(239);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<AssignmentExpressionContext> assignmentExpression() {
			return getRuleContexts(AssignmentExpressionContext.class);
		}
		public AssignmentExpressionContext assignmentExpression(int i) {
			return getRuleContext(AssignmentExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MxParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MxParser.COMMA, i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterExpressionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitExpressionList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			assignmentExpression();
			setState(245);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(241);
				match(COMMA);
				setState(242);
				assignmentExpression();
				}
				}
				setState(247);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryExpressionContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode THIS() { return getToken(MxParser.THIS, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(MxParser.LEFT_PAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(MxParser.RIGHT_PAREN, 0); }
		public IdExpressionContext idExpression() {
			return getRuleContext(IdExpressionContext.class,0);
		}
		public PrimaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterPrimaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitPrimaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitPrimaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryExpressionContext primaryExpression() throws RecognitionException {
		PrimaryExpressionContext _localctx = new PrimaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_primaryExpression);
		try {
			setState(255);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NULL:
			case TRUE:
			case FALSE:
			case INTEGER_LITERAL:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(248);
				literal();
				}
				break;
			case THIS:
				enterOuterAlt(_localctx, 2);
				{
				setState(249);
				match(THIS);
				}
				break;
			case LEFT_PAREN:
				enterOuterAlt(_localctx, 3);
				{
				setState(250);
				match(LEFT_PAREN);
				setState(251);
				expression();
				setState(252);
				match(RIGHT_PAREN);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 4);
				{
				setState(254);
				idExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdExpressionContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(MxParser.IDENTIFIER, 0); }
		public IdExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterIdExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitIdExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitIdExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdExpressionContext idExpression() throws RecognitionException {
		IdExpressionContext _localctx = new IdExpressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_idExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public Boolean_literalContext boolean_literal() {
			return getRuleContext(Boolean_literalContext.class,0);
		}
		public TerminalNode INTEGER_LITERAL() { return getToken(MxParser.INTEGER_LITERAL, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(MxParser.STRING_LITERAL, 0); }
		public TerminalNode NULL() { return getToken(MxParser.NULL, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_literal);
		try {
			setState(263);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
			case FALSE:
				enterOuterAlt(_localctx, 1);
				{
				setState(259);
				boolean_literal();
				}
				break;
			case INTEGER_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(260);
				match(INTEGER_LITERAL);
				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(261);
				match(STRING_LITERAL);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 4);
				{
				setState(262);
				match(NULL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Boolean_literalContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(MxParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(MxParser.FALSE, 0); }
		public Boolean_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolean_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterBoolean_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitBoolean_literal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitBoolean_literal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Boolean_literalContext boolean_literal() throws RecognitionException {
		Boolean_literalContext _localctx = new Boolean_literalContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_boolean_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public SelectionStatementContext selectionStatement() {
			return getRuleContext(SelectionStatementContext.class,0);
		}
		public IterationStatementContext iterationStatement() {
			return getRuleContext(IterationStatementContext.class,0);
		}
		public JumpStatementContext jumpStatement() {
			return getRuleContext(JumpStatementContext.class,0);
		}
		public DeclarationStatementContext declarationStatement() {
			return getRuleContext(DeclarationStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_statement);
		try {
			setState(273);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(267);
				expressionStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(268);
				compoundStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(269);
				selectionStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(270);
				iterationStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(271);
				jumpStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(272);
				declarationStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public TerminalNode SEMI() { return getToken(MxParser.SEMI, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_expressionStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NULL) | (1L << TRUE) | (1L << FALSE) | (1L << NEW) | (1L << THIS) | (1L << LEFT_PAREN) | (1L << PLUS) | (1L << MINUS) | (1L << TILDE) | (1L << NOT) | (1L << PLUS_PLUS) | (1L << MINUS_MINUS) | (1L << IDENTIFIER) | (1L << INTEGER_LITERAL) | (1L << STRING_LITERAL))) != 0)) {
				{
				setState(275);
				expression();
				}
			}

			setState(278);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompoundStatementContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(MxParser.LEFT_BRACE, 0); }
		public TerminalNode RIGHT_BRACE() { return getToken(MxParser.RIGHT_BRACE, 0); }
		public StatementSeqContext statementSeq() {
			return getRuleContext(StatementSeqContext.class,0);
		}
		public CompoundStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoundStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterCompoundStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitCompoundStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitCompoundStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompoundStatementContext compoundStatement() throws RecognitionException {
		CompoundStatementContext _localctx = new CompoundStatementContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_compoundStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			match(LEFT_BRACE);
			setState(282);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << STRING) | (1L << NULL) | (1L << VOID) | (1L << TRUE) | (1L << FALSE) | (1L << IF) | (1L << FOR) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << NEW) | (1L << CLASS) | (1L << THIS) | (1L << LEFT_PAREN) | (1L << LEFT_BRACE) | (1L << PLUS) | (1L << MINUS) | (1L << TILDE) | (1L << NOT) | (1L << PLUS_PLUS) | (1L << MINUS_MINUS) | (1L << SEMI) | (1L << IDENTIFIER) | (1L << INTEGER_LITERAL) | (1L << STRING_LITERAL))) != 0)) {
				{
				setState(281);
				statementSeq();
				}
			}

			setState(284);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementSeqContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StatementSeqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementSeq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterStatementSeq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitStatementSeq(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitStatementSeq(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementSeqContext statementSeq() throws RecognitionException {
		StatementSeqContext _localctx = new StatementSeqContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_statementSeq);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(287); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(286);
				statement();
				}
				}
				setState(289); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << STRING) | (1L << NULL) | (1L << VOID) | (1L << TRUE) | (1L << FALSE) | (1L << IF) | (1L << FOR) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << NEW) | (1L << CLASS) | (1L << THIS) | (1L << LEFT_PAREN) | (1L << LEFT_BRACE) | (1L << PLUS) | (1L << MINUS) | (1L << TILDE) | (1L << NOT) | (1L << PLUS_PLUS) | (1L << MINUS_MINUS) | (1L << SEMI) | (1L << IDENTIFIER) | (1L << INTEGER_LITERAL) | (1L << STRING_LITERAL))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectionStatementContext extends ParserRuleContext {
		public StatementContext ifBody;
		public StatementContext elseBody;
		public TerminalNode IF() { return getToken(MxParser.IF, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(MxParser.LEFT_PAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(MxParser.RIGHT_PAREN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(MxParser.ELSE, 0); }
		public SelectionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterSelectionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitSelectionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitSelectionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectionStatementContext selectionStatement() throws RecognitionException {
		SelectionStatementContext _localctx = new SelectionStatementContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_selectionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(291);
			match(IF);
			setState(292);
			match(LEFT_PAREN);
			setState(293);
			expression();
			setState(294);
			match(RIGHT_PAREN);
			setState(295);
			((SelectionStatementContext)_localctx).ifBody = statement();
			setState(298);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(296);
				match(ELSE);
				setState(297);
				((SelectionStatementContext)_localctx).elseBody = statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IterationStatementContext extends ParserRuleContext {
		public ExpressionContext whileCondition;
		public ExpressionContext forCondition;
		public ExpressionContext forIteration;
		public TerminalNode WHILE() { return getToken(MxParser.WHILE, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(MxParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(MxParser.RIGHT_PAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode FOR() { return getToken(MxParser.FOR, 0); }
		public ForInitStatementContext forInitStatement() {
			return getRuleContext(ForInitStatementContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(MxParser.SEMI, 0); }
		public IterationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iterationStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterIterationStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitIterationStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitIterationStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IterationStatementContext iterationStatement() throws RecognitionException {
		IterationStatementContext _localctx = new IterationStatementContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_iterationStatement);
		int _la;
		try {
			setState(319);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WHILE:
				enterOuterAlt(_localctx, 1);
				{
				setState(300);
				match(WHILE);
				setState(301);
				match(LEFT_PAREN);
				setState(302);
				((IterationStatementContext)_localctx).whileCondition = expression();
				setState(303);
				match(RIGHT_PAREN);
				setState(304);
				statement();
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 2);
				{
				setState(306);
				match(FOR);
				setState(307);
				match(LEFT_PAREN);
				setState(308);
				forInitStatement();
				setState(310);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NULL) | (1L << TRUE) | (1L << FALSE) | (1L << NEW) | (1L << THIS) | (1L << LEFT_PAREN) | (1L << PLUS) | (1L << MINUS) | (1L << TILDE) | (1L << NOT) | (1L << PLUS_PLUS) | (1L << MINUS_MINUS) | (1L << IDENTIFIER) | (1L << INTEGER_LITERAL) | (1L << STRING_LITERAL))) != 0)) {
					{
					setState(309);
					((IterationStatementContext)_localctx).forCondition = expression();
					}
				}

				setState(312);
				match(SEMI);
				setState(314);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NULL) | (1L << TRUE) | (1L << FALSE) | (1L << NEW) | (1L << THIS) | (1L << LEFT_PAREN) | (1L << PLUS) | (1L << MINUS) | (1L << TILDE) | (1L << NOT) | (1L << PLUS_PLUS) | (1L << MINUS_MINUS) | (1L << IDENTIFIER) | (1L << INTEGER_LITERAL) | (1L << STRING_LITERAL))) != 0)) {
					{
					setState(313);
					((IterationStatementContext)_localctx).forIteration = expression();
					}
				}

				setState(316);
				match(RIGHT_PAREN);
				setState(317);
				statement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForInitStatementContext extends ParserRuleContext {
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public SimpleDeclarationContext simpleDeclaration() {
			return getRuleContext(SimpleDeclarationContext.class,0);
		}
		public ForInitStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInitStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterForInitStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitForInitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitForInitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitStatementContext forInitStatement() throws RecognitionException {
		ForInitStatementContext _localctx = new ForInitStatementContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_forInitStatement);
		try {
			setState(323);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(321);
				expressionStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(322);
				simpleDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JumpStatementContext extends ParserRuleContext {
		public TerminalNode SEMI() { return getToken(MxParser.SEMI, 0); }
		public TerminalNode BREAK() { return getToken(MxParser.BREAK, 0); }
		public TerminalNode CONTINUE() { return getToken(MxParser.CONTINUE, 0); }
		public TerminalNode RETURN() { return getToken(MxParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public JumpStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterJumpStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitJumpStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitJumpStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpStatementContext jumpStatement() throws RecognitionException {
		JumpStatementContext _localctx = new JumpStatementContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_jumpStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(331);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BREAK:
				{
				setState(325);
				match(BREAK);
				}
				break;
			case CONTINUE:
				{
				setState(326);
				match(CONTINUE);
				}
				break;
			case RETURN:
				{
				setState(327);
				match(RETURN);
				setState(329);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NULL) | (1L << TRUE) | (1L << FALSE) | (1L << NEW) | (1L << THIS) | (1L << LEFT_PAREN) | (1L << PLUS) | (1L << MINUS) | (1L << TILDE) | (1L << NOT) | (1L << PLUS_PLUS) | (1L << MINUS_MINUS) | (1L << IDENTIFIER) | (1L << INTEGER_LITERAL) | (1L << STRING_LITERAL))) != 0)) {
					{
					setState(328);
					expression();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(333);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationStatementContext extends ParserRuleContext {
		public SimpleDeclarationContext simpleDeclaration() {
			return getRuleContext(SimpleDeclarationContext.class,0);
		}
		public DeclarationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterDeclarationStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitDeclarationStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitDeclarationStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationStatementContext declarationStatement() throws RecognitionException {
		DeclarationStatementContext _localctx = new DeclarationStatementContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_declarationStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(335);
			simpleDeclaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationSeqContext extends ParserRuleContext {
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public DeclarationSeqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationSeq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterDeclarationSeq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitDeclarationSeq(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitDeclarationSeq(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationSeqContext declarationSeq() throws RecognitionException {
		DeclarationSeqContext _localctx = new DeclarationSeqContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_declarationSeq);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(338); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(337);
				declaration();
				}
				}
				setState(340); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << STRING) | (1L << VOID) | (1L << CLASS) | (1L << LEFT_BRACKET) | (1L << SEMI) | (1L << IDENTIFIER))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public SimpleDeclarationContext simpleDeclaration() {
			return getRuleContext(SimpleDeclarationContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_declaration);
		try {
			setState(344);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(342);
				simpleDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(343);
				functionDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleDeclarationContext extends ParserRuleContext {
		public TerminalNode SEMI() { return getToken(MxParser.SEMI, 0); }
		public TypeSpecifierContext typeSpecifier() {
			return getRuleContext(TypeSpecifierContext.class,0);
		}
		public InitDeclaratorListContext initDeclaratorList() {
			return getRuleContext(InitDeclaratorListContext.class,0);
		}
		public SimpleDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterSimpleDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitSimpleDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitSimpleDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleDeclarationContext simpleDeclaration() throws RecognitionException {
		SimpleDeclarationContext _localctx = new SimpleDeclarationContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_simpleDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(350);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << STRING) | (1L << VOID) | (1L << CLASS) | (1L << IDENTIFIER))) != 0)) {
				{
				setState(346);
				typeSpecifier();
				setState(348);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LEFT_BRACKET || _la==IDENTIFIER) {
					{
					setState(347);
					initDeclaratorList();
					}
				}

				}
			}

			setState(352);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitDeclaratorListContext extends ParserRuleContext {
		public List<InitDeclaratorContext> initDeclarator() {
			return getRuleContexts(InitDeclaratorContext.class);
		}
		public InitDeclaratorContext initDeclarator(int i) {
			return getRuleContext(InitDeclaratorContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MxParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MxParser.COMMA, i);
		}
		public InitDeclaratorListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initDeclaratorList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterInitDeclaratorList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitInitDeclaratorList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitInitDeclaratorList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InitDeclaratorListContext initDeclaratorList() throws RecognitionException {
		InitDeclaratorListContext _localctx = new InitDeclaratorListContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_initDeclaratorList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(354);
			initDeclarator();
			setState(359);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(355);
				match(COMMA);
				setState(356);
				initDeclarator();
				}
				}
				setState(361);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitDeclaratorContext extends ParserRuleContext {
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public InitializerContext initializer() {
			return getRuleContext(InitializerContext.class,0);
		}
		public InitDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initDeclarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterInitDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitInitDeclarator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitInitDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InitDeclaratorContext initDeclarator() throws RecognitionException {
		InitDeclaratorContext _localctx = new InitDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_initDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(362);
			declarator();
			setState(364);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(363);
				initializer();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclaratorContext extends ParserRuleContext {
		public IdExpressionContext idExpression() {
			return getRuleContext(IdExpressionContext.class,0);
		}
		public List<TerminalNode> LEFT_BRACKET() { return getTokens(MxParser.LEFT_BRACKET); }
		public TerminalNode LEFT_BRACKET(int i) {
			return getToken(MxParser.LEFT_BRACKET, i);
		}
		public List<TerminalNode> RIGHT_BRACKET() { return getTokens(MxParser.RIGHT_BRACKET); }
		public TerminalNode RIGHT_BRACKET(int i) {
			return getToken(MxParser.RIGHT_BRACKET, i);
		}
		public ParametersAndQualifiersContext parametersAndQualifiers() {
			return getRuleContext(ParametersAndQualifiersContext.class,0);
		}
		public DeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitDeclarator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclaratorContext declarator() throws RecognitionException {
		DeclaratorContext _localctx = new DeclaratorContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_declarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(370);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LEFT_BRACKET) {
				{
				{
				setState(366);
				match(LEFT_BRACKET);
				setState(367);
				match(RIGHT_BRACKET);
				}
				}
				setState(372);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(373);
			idExpression();
			setState(375);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LEFT_PAREN) {
				{
				setState(374);
				parametersAndQualifiers();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParametersAndQualifiersContext extends ParserRuleContext {
		public TerminalNode LEFT_PAREN() { return getToken(MxParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(MxParser.RIGHT_PAREN, 0); }
		public ParameterDeclarationListContext parameterDeclarationList() {
			return getRuleContext(ParameterDeclarationListContext.class,0);
		}
		public ParametersAndQualifiersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parametersAndQualifiers; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterParametersAndQualifiers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitParametersAndQualifiers(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitParametersAndQualifiers(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametersAndQualifiersContext parametersAndQualifiers() throws RecognitionException {
		ParametersAndQualifiersContext _localctx = new ParametersAndQualifiersContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_parametersAndQualifiers);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(377);
			match(LEFT_PAREN);
			setState(379);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << STRING) | (1L << VOID) | (1L << CLASS) | (1L << IDENTIFIER))) != 0)) {
				{
				setState(378);
				parameterDeclarationList();
				}
			}

			setState(381);
			match(RIGHT_PAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterDeclarationListContext extends ParserRuleContext {
		public List<ParameterDeclarationContext> parameterDeclaration() {
			return getRuleContexts(ParameterDeclarationContext.class);
		}
		public ParameterDeclarationContext parameterDeclaration(int i) {
			return getRuleContext(ParameterDeclarationContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MxParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MxParser.COMMA, i);
		}
		public ParameterDeclarationListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterDeclarationList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterParameterDeclarationList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitParameterDeclarationList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitParameterDeclarationList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterDeclarationListContext parameterDeclarationList() throws RecognitionException {
		ParameterDeclarationListContext _localctx = new ParameterDeclarationListContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_parameterDeclarationList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(383);
			parameterDeclaration();
			setState(388);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(384);
				match(COMMA);
				setState(385);
				parameterDeclaration();
				}
				}
				setState(390);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterDeclarationContext extends ParserRuleContext {
		public TypeSpecifierContext typeSpecifier() {
			return getRuleContext(TypeSpecifierContext.class,0);
		}
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public ParameterDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterParameterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitParameterDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitParameterDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterDeclarationContext parameterDeclaration() throws RecognitionException {
		ParameterDeclarationContext _localctx = new ParameterDeclarationContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_parameterDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(391);
			typeSpecifier();
			setState(392);
			declarator();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitializerContext extends ParserRuleContext {
		public TerminalNode ASSIGN() { return getToken(MxParser.ASSIGN, 0); }
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public InitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitInitializer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InitializerContext initializer() throws RecognitionException {
		InitializerContext _localctx = new InitializerContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_initializer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(394);
			match(ASSIGN);
			setState(395);
			assignmentExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeSpecifierContext extends ParserRuleContext {
		public SimpleTypeSpecifierContext simpleTypeSpecifier() {
			return getRuleContext(SimpleTypeSpecifierContext.class,0);
		}
		public ClassSpecifierContext classSpecifier() {
			return getRuleContext(ClassSpecifierContext.class,0);
		}
		public TypeSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterTypeSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitTypeSpecifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitTypeSpecifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeSpecifierContext typeSpecifier() throws RecognitionException {
		TypeSpecifierContext _localctx = new TypeSpecifierContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_typeSpecifier);
		try {
			setState(399);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOL:
			case INT:
			case STRING:
			case VOID:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(397);
				simpleTypeSpecifier();
				}
				break;
			case CLASS:
				enterOuterAlt(_localctx, 2);
				{
				setState(398);
				classSpecifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleTypeSpecifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(MxParser.IDENTIFIER, 0); }
		public TerminalNode INT() { return getToken(MxParser.INT, 0); }
		public TerminalNode BOOL() { return getToken(MxParser.BOOL, 0); }
		public TerminalNode STRING() { return getToken(MxParser.STRING, 0); }
		public TerminalNode VOID() { return getToken(MxParser.VOID, 0); }
		public SimpleTypeSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleTypeSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterSimpleTypeSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitSimpleTypeSpecifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitSimpleTypeSpecifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleTypeSpecifierContext simpleTypeSpecifier() throws RecognitionException {
		SimpleTypeSpecifierContext _localctx = new SimpleTypeSpecifierContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_simpleTypeSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(401);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << STRING) | (1L << VOID) | (1L << IDENTIFIER))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassSpecifierContext extends ParserRuleContext {
		public Token className;
		public TerminalNode CLASS() { return getToken(MxParser.CLASS, 0); }
		public TerminalNode LEFT_BRACE() { return getToken(MxParser.LEFT_BRACE, 0); }
		public TerminalNode RIGHT_BRACE() { return getToken(MxParser.RIGHT_BRACE, 0); }
		public TerminalNode IDENTIFIER() { return getToken(MxParser.IDENTIFIER, 0); }
		public DeclarationSeqContext declarationSeq() {
			return getRuleContext(DeclarationSeqContext.class,0);
		}
		public ClassSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterClassSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitClassSpecifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitClassSpecifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassSpecifierContext classSpecifier() throws RecognitionException {
		ClassSpecifierContext _localctx = new ClassSpecifierContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_classSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(403);
			match(CLASS);
			setState(404);
			((ClassSpecifierContext)_localctx).className = match(IDENTIFIER);
			setState(405);
			match(LEFT_BRACE);
			setState(407);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << STRING) | (1L << VOID) | (1L << CLASS) | (1L << LEFT_BRACKET) | (1L << SEMI) | (1L << IDENTIFIER))) != 0)) {
				{
				setState(406);
				declarationSeq();
				}
			}

			setState(409);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public SimpleTypeSpecifierContext simpleTypeSpecifier() {
			return getRuleContext(SimpleTypeSpecifierContext.class,0);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).enterFunctionDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxListener ) ((MxListener)listener).exitFunctionDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxVisitor ) return ((MxVisitor<? extends T>)visitor).visitFunctionDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_functionDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(412);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				{
				setState(411);
				simpleTypeSpecifier();
				}
				break;
			}
			setState(414);
			declarator();
			setState(415);
			compoundStatement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 15:
			return postfixExpression_sempred((PostfixExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean postfixExpression_sempred(PostfixExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 2);
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3;\u01a4\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\3\2\5\2\\\n\2\3\2\3\2\3\3\3\3\3\3\7\3c\n\3\f\3\16\3f\13\3\3"+
		"\4\3\4\3\4\7\4k\n\4\f\4\16\4n\13\4\3\5\3\5\3\5\7\5s\n\5\f\5\16\5v\13\5"+
		"\3\6\3\6\3\6\7\6{\n\6\f\6\16\6~\13\6\3\7\3\7\3\7\7\7\u0083\n\7\f\7\16"+
		"\7\u0086\13\7\3\b\3\b\3\b\7\b\u008b\n\b\f\b\16\b\u008e\13\b\3\t\3\t\3"+
		"\t\7\t\u0093\n\t\f\t\16\t\u0096\13\t\3\n\3\n\3\n\7\n\u009b\n\n\f\n\16"+
		"\n\u009e\13\n\3\13\3\13\3\13\7\13\u00a3\n\13\f\13\16\13\u00a6\13\13\3"+
		"\f\3\f\3\f\7\f\u00ab\n\f\f\f\16\f\u00ae\13\f\3\r\3\r\3\r\7\r\u00b3\n\r"+
		"\f\r\16\r\u00b6\13\r\3\16\3\16\3\16\7\16\u00bb\n\16\f\16\16\16\u00be\13"+
		"\16\3\17\3\17\3\17\3\17\5\17\u00c4\n\17\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\6\20\u00cc\n\20\r\20\16\20\u00cd\3\20\3\20\7\20\u00d2\n\20\f\20\16\20"+
		"\u00d5\13\20\3\20\3\20\5\20\u00d9\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\5\21\u00e3\n\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\7\21\u00ee\n\21\f\21\16\21\u00f1\13\21\3\22\3\22\3\22\7\22\u00f6\n\22"+
		"\f\22\16\22\u00f9\13\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u0102"+
		"\n\23\3\24\3\24\3\25\3\25\3\25\3\25\5\25\u010a\n\25\3\26\3\26\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\5\27\u0114\n\27\3\30\5\30\u0117\n\30\3\30\3\30\3"+
		"\31\3\31\5\31\u011d\n\31\3\31\3\31\3\32\6\32\u0122\n\32\r\32\16\32\u0123"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u012d\n\33\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\5\34\u0139\n\34\3\34\3\34\5\34\u013d\n"+
		"\34\3\34\3\34\3\34\5\34\u0142\n\34\3\35\3\35\5\35\u0146\n\35\3\36\3\36"+
		"\3\36\3\36\5\36\u014c\n\36\5\36\u014e\n\36\3\36\3\36\3\37\3\37\3 \6 \u0155"+
		"\n \r \16 \u0156\3!\3!\5!\u015b\n!\3\"\3\"\5\"\u015f\n\"\5\"\u0161\n\""+
		"\3\"\3\"\3#\3#\3#\7#\u0168\n#\f#\16#\u016b\13#\3$\3$\5$\u016f\n$\3%\3"+
		"%\7%\u0173\n%\f%\16%\u0176\13%\3%\3%\5%\u017a\n%\3&\3&\5&\u017e\n&\3&"+
		"\3&\3\'\3\'\3\'\7\'\u0185\n\'\f\'\16\'\u0188\13\'\3(\3(\3(\3)\3)\3)\3"+
		"*\3*\5*\u0192\n*\3+\3+\3,\3,\3,\3,\5,\u019a\n,\3,\3,\3-\5-\u019f\n-\3"+
		"-\3-\3-\3-\2\3 .\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62"+
		"\64\668:<>@BDFHJLNPRTVX\2\13\3\2)*\4\2\'(+,\3\2$%\3\2\32\33\3\2\34\36"+
		"\5\2\32\33\"#/\60\3\2/\60\3\2\b\t\5\2\3\5\7\7\64\64\2\u01b3\2[\3\2\2\2"+
		"\4_\3\2\2\2\6g\3\2\2\2\bo\3\2\2\2\nw\3\2\2\2\f\177\3\2\2\2\16\u0087\3"+
		"\2\2\2\20\u008f\3\2\2\2\22\u0097\3\2\2\2\24\u009f\3\2\2\2\26\u00a7\3\2"+
		"\2\2\30\u00af\3\2\2\2\32\u00b7\3\2\2\2\34\u00c3\3\2\2\2\36\u00c5\3\2\2"+
		"\2 \u00da\3\2\2\2\"\u00f2\3\2\2\2$\u0101\3\2\2\2&\u0103\3\2\2\2(\u0109"+
		"\3\2\2\2*\u010b\3\2\2\2,\u0113\3\2\2\2.\u0116\3\2\2\2\60\u011a\3\2\2\2"+
		"\62\u0121\3\2\2\2\64\u0125\3\2\2\2\66\u0141\3\2\2\28\u0145\3\2\2\2:\u014d"+
		"\3\2\2\2<\u0151\3\2\2\2>\u0154\3\2\2\2@\u015a\3\2\2\2B\u0160\3\2\2\2D"+
		"\u0164\3\2\2\2F\u016c\3\2\2\2H\u0174\3\2\2\2J\u017b\3\2\2\2L\u0181\3\2"+
		"\2\2N\u0189\3\2\2\2P\u018c\3\2\2\2R\u0191\3\2\2\2T\u0193\3\2\2\2V\u0195"+
		"\3\2\2\2X\u019e\3\2\2\2Z\\\5> \2[Z\3\2\2\2[\\\3\2\2\2\\]\3\2\2\2]^\7\2"+
		"\2\3^\3\3\2\2\2_d\5\6\4\2`a\7\61\2\2ac\5\6\4\2b`\3\2\2\2cf\3\2\2\2db\3"+
		"\2\2\2de\3\2\2\2e\5\3\2\2\2fd\3\2\2\2gl\5\b\5\2hi\7&\2\2ik\5\b\5\2jh\3"+
		"\2\2\2kn\3\2\2\2lj\3\2\2\2lm\3\2\2\2m\7\3\2\2\2nl\3\2\2\2ot\5\n\6\2pq"+
		"\7.\2\2qs\5\n\6\2rp\3\2\2\2sv\3\2\2\2tr\3\2\2\2tu\3\2\2\2u\t\3\2\2\2v"+
		"t\3\2\2\2w|\5\f\7\2xy\7-\2\2y{\5\f\7\2zx\3\2\2\2{~\3\2\2\2|z\3\2\2\2|"+
		"}\3\2\2\2}\13\3\2\2\2~|\3\2\2\2\177\u0084\5\16\b\2\u0080\u0081\7!\2\2"+
		"\u0081\u0083\5\16\b\2\u0082\u0080\3\2\2\2\u0083\u0086\3\2\2\2\u0084\u0082"+
		"\3\2\2\2\u0084\u0085\3\2\2\2\u0085\r\3\2\2\2\u0086\u0084\3\2\2\2\u0087"+
		"\u008c\5\20\t\2\u0088\u0089\7\37\2\2\u0089\u008b\5\20\t\2\u008a\u0088"+
		"\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d"+
		"\17\3\2\2\2\u008e\u008c\3\2\2\2\u008f\u0094\5\22\n\2\u0090\u0091\7 \2"+
		"\2\u0091\u0093\5\22\n\2\u0092\u0090\3\2\2\2\u0093\u0096\3\2\2\2\u0094"+
		"\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\21\3\2\2\2\u0096\u0094\3\2\2"+
		"\2\u0097\u009c\5\24\13\2\u0098\u0099\t\2\2\2\u0099\u009b\5\24\13\2\u009a"+
		"\u0098\3\2\2\2\u009b\u009e\3\2\2\2\u009c\u009a\3\2\2\2\u009c\u009d\3\2"+
		"\2\2\u009d\23\3\2\2\2\u009e\u009c\3\2\2\2\u009f\u00a4\5\26\f\2\u00a0\u00a1"+
		"\t\3\2\2\u00a1\u00a3\5\26\f\2\u00a2\u00a0\3\2\2\2\u00a3\u00a6\3\2\2\2"+
		"\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\25\3\2\2\2\u00a6\u00a4"+
		"\3\2\2\2\u00a7\u00ac\5\30\r\2\u00a8\u00a9\t\4\2\2\u00a9\u00ab\5\30\r\2"+
		"\u00aa\u00a8\3\2\2\2\u00ab\u00ae\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ac\u00ad"+
		"\3\2\2\2\u00ad\27\3\2\2\2\u00ae\u00ac\3\2\2\2\u00af\u00b4\5\32\16\2\u00b0"+
		"\u00b1\t\5\2\2\u00b1\u00b3\5\32\16\2\u00b2\u00b0\3\2\2\2\u00b3\u00b6\3"+
		"\2\2\2\u00b4\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\31\3\2\2\2\u00b6"+
		"\u00b4\3\2\2\2\u00b7\u00bc\5\34\17\2\u00b8\u00b9\t\6\2\2\u00b9\u00bb\5"+
		"\34\17\2\u00ba\u00b8\3\2\2\2\u00bb\u00be\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc"+
		"\u00bd\3\2\2\2\u00bd\33\3\2\2\2\u00be\u00bc\3\2\2\2\u00bf\u00c4\5 \21"+
		"\2\u00c0\u00c1\t\7\2\2\u00c1\u00c4\5\34\17\2\u00c2\u00c4\5\36\20\2\u00c3"+
		"\u00bf\3\2\2\2\u00c3\u00c0\3\2\2\2\u00c3\u00c2\3\2\2\2\u00c4\35\3\2\2"+
		"\2\u00c5\u00c6\7\21\2\2\u00c6\u00d8\5R*\2\u00c7\u00c8\7\26\2\2\u00c8\u00c9"+
		"\5\4\3\2\u00c9\u00ca\7\27\2\2\u00ca\u00cc\3\2\2\2\u00cb\u00c7\3\2\2\2"+
		"\u00cc\u00cd\3\2\2\2\u00cd\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00d3"+
		"\3\2\2\2\u00cf\u00d0\7\26\2\2\u00d0\u00d2\7\27\2\2\u00d1\u00cf\3\2\2\2"+
		"\u00d2\u00d5\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d9"+
		"\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d6\u00d7\7\24\2\2\u00d7\u00d9\7\25\2\2"+
		"\u00d8\u00cb\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\37"+
		"\3\2\2\2\u00da\u00db\b\21\1\2\u00db\u00dc\5$\23\2\u00dc\u00ef\3\2\2\2"+
		"\u00dd\u00de\f\6\2\2\u00de\u00ee\t\b\2\2\u00df\u00e0\f\5\2\2\u00e0\u00e2"+
		"\7\24\2\2\u00e1\u00e3\5\"\22\2\u00e2\u00e1\3\2\2\2\u00e2\u00e3\3\2\2\2"+
		"\u00e3\u00e4\3\2\2\2\u00e4\u00ee\7\25\2\2\u00e5\u00e6\f\4\2\2\u00e6\u00e7"+
		"\7\26\2\2\u00e7\u00e8\5\4\3\2\u00e8\u00e9\7\27\2\2\u00e9\u00ee\3\2\2\2"+
		"\u00ea\u00eb\f\3\2\2\u00eb\u00ec\7\62\2\2\u00ec\u00ee\5&\24\2\u00ed\u00dd"+
		"\3\2\2\2\u00ed\u00df\3\2\2\2\u00ed\u00e5\3\2\2\2\u00ed\u00ea\3\2\2\2\u00ee"+
		"\u00f1\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0!\3\2\2\2"+
		"\u00f1\u00ef\3\2\2\2\u00f2\u00f7\5\6\4\2\u00f3\u00f4\7\61\2\2\u00f4\u00f6"+
		"\5\6\4\2\u00f5\u00f3\3\2\2\2\u00f6\u00f9\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7"+
		"\u00f8\3\2\2\2\u00f8#\3\2\2\2\u00f9\u00f7\3\2\2\2\u00fa\u0102\5(\25\2"+
		"\u00fb\u0102\7\23\2\2\u00fc\u00fd\7\24\2\2\u00fd\u00fe\5\4\3\2\u00fe\u00ff"+
		"\7\25\2\2\u00ff\u0102\3\2\2\2\u0100\u0102\5&\24\2\u0101\u00fa\3\2\2\2"+
		"\u0101\u00fb\3\2\2\2\u0101\u00fc\3\2\2\2\u0101\u0100\3\2\2\2\u0102%\3"+
		"\2\2\2\u0103\u0104\7\64\2\2\u0104\'\3\2\2\2\u0105\u010a\5*\26\2\u0106"+
		"\u010a\7\65\2\2\u0107\u010a\7\66\2\2\u0108\u010a\7\6\2\2\u0109\u0105\3"+
		"\2\2\2\u0109\u0106\3\2\2\2\u0109\u0107\3\2\2\2\u0109\u0108\3\2\2\2\u010a"+
		")\3\2\2\2\u010b\u010c\t\t\2\2\u010c+\3\2\2\2\u010d\u0114\5.\30\2\u010e"+
		"\u0114\5\60\31\2\u010f\u0114\5\64\33\2\u0110\u0114\5\66\34\2\u0111\u0114"+
		"\5:\36\2\u0112\u0114\5<\37\2\u0113\u010d\3\2\2\2\u0113\u010e\3\2\2\2\u0113"+
		"\u010f\3\2\2\2\u0113\u0110\3\2\2\2\u0113\u0111\3\2\2\2\u0113\u0112\3\2"+
		"\2\2\u0114-\3\2\2\2\u0115\u0117\5\4\3\2\u0116\u0115\3\2\2\2\u0116\u0117"+
		"\3\2\2\2\u0117\u0118\3\2\2\2\u0118\u0119\7\63\2\2\u0119/\3\2\2\2\u011a"+
		"\u011c\7\30\2\2\u011b\u011d\5\62\32\2\u011c\u011b\3\2\2\2\u011c\u011d"+
		"\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u011f\7\31\2\2\u011f\61\3\2\2\2\u0120"+
		"\u0122\5,\27\2\u0121\u0120\3\2\2\2\u0122\u0123\3\2\2\2\u0123\u0121\3\2"+
		"\2\2\u0123\u0124\3\2\2\2\u0124\63\3\2\2\2\u0125\u0126\7\n\2\2\u0126\u0127"+
		"\7\24\2\2\u0127\u0128\5\4\3\2\u0128\u0129\7\25\2\2\u0129\u012c\5,\27\2"+
		"\u012a\u012b\7\13\2\2\u012b\u012d\5,\27\2\u012c\u012a\3\2\2\2\u012c\u012d"+
		"\3\2\2\2\u012d\65\3\2\2\2\u012e\u012f\7\r\2\2\u012f\u0130\7\24\2\2\u0130"+
		"\u0131\5\4\3\2\u0131\u0132\7\25\2\2\u0132\u0133\5,\27\2\u0133\u0142\3"+
		"\2\2\2\u0134\u0135\7\f\2\2\u0135\u0136\7\24\2\2\u0136\u0138\58\35\2\u0137"+
		"\u0139\5\4\3\2\u0138\u0137\3\2\2\2\u0138\u0139\3\2\2\2\u0139\u013a\3\2"+
		"\2\2\u013a\u013c\7\63\2\2\u013b\u013d\5\4\3\2\u013c\u013b\3\2\2\2\u013c"+
		"\u013d\3\2\2\2\u013d\u013e\3\2\2\2\u013e\u013f\7\25\2\2\u013f\u0140\5"+
		",\27\2\u0140\u0142\3\2\2\2\u0141\u012e\3\2\2\2\u0141\u0134\3\2\2\2\u0142"+
		"\67\3\2\2\2\u0143\u0146\5.\30\2\u0144\u0146\5B\"\2\u0145\u0143\3\2\2\2"+
		"\u0145\u0144\3\2\2\2\u01469\3\2\2\2\u0147\u014e\7\16\2\2\u0148\u014e\7"+
		"\17\2\2\u0149\u014b\7\20\2\2\u014a\u014c\5\4\3\2\u014b\u014a\3\2\2\2\u014b"+
		"\u014c\3\2\2\2\u014c\u014e\3\2\2\2\u014d\u0147\3\2\2\2\u014d\u0148\3\2"+
		"\2\2\u014d\u0149\3\2\2\2\u014e\u014f\3\2\2\2\u014f\u0150\7\63\2\2\u0150"+
		";\3\2\2\2\u0151\u0152\5B\"\2\u0152=\3\2\2\2\u0153\u0155\5@!\2\u0154\u0153"+
		"\3\2\2\2\u0155\u0156\3\2\2\2\u0156\u0154\3\2\2\2\u0156\u0157\3\2\2\2\u0157"+
		"?\3\2\2\2\u0158\u015b\5B\"\2\u0159\u015b\5X-\2\u015a\u0158\3\2\2\2\u015a"+
		"\u0159\3\2\2\2\u015bA\3\2\2\2\u015c\u015e\5R*\2\u015d\u015f\5D#\2\u015e"+
		"\u015d\3\2\2\2\u015e\u015f\3\2\2\2\u015f\u0161\3\2\2\2\u0160\u015c\3\2"+
		"\2\2\u0160\u0161\3\2\2\2\u0161\u0162\3\2\2\2\u0162\u0163\7\63\2\2\u0163"+
		"C\3\2\2\2\u0164\u0169\5F$\2\u0165\u0166\7\61\2\2\u0166\u0168\5F$\2\u0167"+
		"\u0165\3\2\2\2\u0168\u016b\3\2\2\2\u0169\u0167\3\2\2\2\u0169\u016a\3\2"+
		"\2\2\u016aE\3\2\2\2\u016b\u0169\3\2\2\2\u016c\u016e\5H%\2\u016d\u016f"+
		"\5P)\2\u016e\u016d\3\2\2\2\u016e\u016f\3\2\2\2\u016fG\3\2\2\2\u0170\u0171"+
		"\7\26\2\2\u0171\u0173\7\27\2\2\u0172\u0170\3\2\2\2\u0173\u0176\3\2\2\2"+
		"\u0174\u0172\3\2\2\2\u0174\u0175\3\2\2\2\u0175\u0177\3\2\2\2\u0176\u0174"+
		"\3\2\2\2\u0177\u0179\5&\24\2\u0178\u017a\5J&\2\u0179\u0178\3\2\2\2\u0179"+
		"\u017a\3\2\2\2\u017aI\3\2\2\2\u017b\u017d\7\24\2\2\u017c\u017e\5L\'\2"+
		"\u017d\u017c\3\2\2\2\u017d\u017e\3\2\2\2\u017e\u017f\3\2\2\2\u017f\u0180"+
		"\7\25\2\2\u0180K\3\2\2\2\u0181\u0186\5N(\2\u0182\u0183\7\61\2\2\u0183"+
		"\u0185\5N(\2\u0184\u0182\3\2\2\2\u0185\u0188\3\2\2\2\u0186\u0184\3\2\2"+
		"\2\u0186\u0187\3\2\2\2\u0187M\3\2\2\2\u0188\u0186\3\2\2\2\u0189\u018a"+
		"\5R*\2\u018a\u018b\5H%\2\u018bO\3\2\2\2\u018c\u018d\7&\2\2\u018d\u018e"+
		"\5\6\4\2\u018eQ\3\2\2\2\u018f\u0192\5T+\2\u0190\u0192\5V,\2\u0191\u018f"+
		"\3\2\2\2\u0191\u0190\3\2\2\2\u0192S\3\2\2\2\u0193\u0194\t\n\2\2\u0194"+
		"U\3\2\2\2\u0195\u0196\7\22\2\2\u0196\u0197\7\64\2\2\u0197\u0199\7\30\2"+
		"\2\u0198\u019a\5> \2\u0199\u0198\3\2\2\2\u0199\u019a\3\2\2\2\u019a\u019b"+
		"\3\2\2\2\u019b\u019c\7\31\2\2\u019cW\3\2\2\2\u019d\u019f\5T+\2\u019e\u019d"+
		"\3\2\2\2\u019e\u019f\3\2\2\2\u019f\u01a0\3\2\2\2\u01a0\u01a1\5H%\2\u01a1"+
		"\u01a2\5\60\31\2\u01a2Y\3\2\2\2\61[dlt|\u0084\u008c\u0094\u009c\u00a4"+
		"\u00ac\u00b4\u00bc\u00c3\u00cd\u00d3\u00d8\u00e2\u00ed\u00ef\u00f7\u0101"+
		"\u0109\u0113\u0116\u011c\u0123\u012c\u0138\u013c\u0141\u0145\u014b\u014d"+
		"\u0156\u015a\u015e\u0160\u0169\u016e\u0174\u0179\u017d\u0186\u0191\u0199"+
		"\u019e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}