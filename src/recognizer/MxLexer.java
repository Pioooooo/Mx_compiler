// Generated from D:/College/2-2/Computer-Architecture/Mx_compiler/src/recognizer\Mx.g4 by ANTLR 4.9
package recognizer;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"BOOL", "INT", "STRING", "NULL", "VOID", "TRUE", "FALSE", "IF", "ELSE", 
			"FOR", "WHILE", "BREAK", "CONTINUE", "RETURN", "NEW", "CLASS", "THIS", 
			"LEFT_PAREN", "RIGHT_PAREN", "LEFT_BRACKET", "RIGHT_BRACKET", "LEFT_BRACE", 
			"RIGHT_BRACE", "PLUS", "MINUS", "STAR", "DIV", "MOD", "CARET", "AND", 
			"OR", "TILDE", "NOT", "LESS_LESS", "GREATER_GREATER", "ASSIGN", "LESS", 
			"GREATER", "EQUAL", "NOT_EQUAL", "LESS_EQUAL", "GREATER_EQUAL", "AND_AND", 
			"OR_OR", "PLUS_PLUS", "MINUS_MINUS", "COMMA", "DOT", "SEMI", "IDENTIFIER", 
			"INTEGER_LITERAL", "STRING_LITERAL", "DIGIT", "NONZERO_DIGIT", "DECIMAL_LITERAL", 
			"LETTER", "NONDIGIT", "WHITESPACE", "NEWLINE", "BLOCK_COMMENT", "LINE_COMMENT"
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


	public MxLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Mx.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2;\u0173\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20"+
		"\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23"+
		"\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32"+
		"\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\""+
		"\3#\3#\3#\3$\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3*\3+\3"+
		"+\3+\3,\3,\3,\3-\3-\3-\3.\3.\3.\3/\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62"+
		"\3\63\3\63\3\63\7\63\u0127\n\63\f\63\16\63\u012a\13\63\3\64\3\64\3\65"+
		"\3\65\3\65\3\65\7\65\u0132\n\65\f\65\16\65\u0135\13\65\3\65\3\65\3\66"+
		"\3\66\3\67\3\67\38\38\78\u013f\n8\f8\168\u0142\138\38\58\u0145\n8\39\3"+
		"9\3:\3:\3;\6;\u014c\n;\r;\16;\u014d\3;\3;\3<\3<\5<\u0154\n<\3<\5<\u0157"+
		"\n<\3<\3<\3=\3=\3=\3=\7=\u015f\n=\f=\16=\u0162\13=\3=\3=\3=\3=\3=\3>\3"+
		">\3>\3>\7>\u016d\n>\f>\16>\u0170\13>\3>\3>\3\u0160\2?\3\3\5\4\7\5\t\6"+
		"\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24"+
		"\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K"+
		"\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\2m\2o\67q\2s\2u8w9y:{"+
		";\3\2\n\6\2\f\f\17\17$$^^\6\2$$^^pptt\3\2\62;\3\2\63;\4\2C\\c|\5\2C\\"+
		"aac|\4\2\13\13\"\"\4\2\f\f\17\17\2\u0179\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3"+
		"\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2"+
		"\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35"+
		"\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)"+
		"\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2"+
		"\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2"+
		"A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3"+
		"\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2"+
		"\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2"+
		"g\3\2\2\2\2i\3\2\2\2\2o\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3"+
		"\2\2\2\3}\3\2\2\2\5\u0082\3\2\2\2\7\u0086\3\2\2\2\t\u008d\3\2\2\2\13\u0092"+
		"\3\2\2\2\r\u0097\3\2\2\2\17\u009c\3\2\2\2\21\u00a2\3\2\2\2\23\u00a5\3"+
		"\2\2\2\25\u00aa\3\2\2\2\27\u00ae\3\2\2\2\31\u00b4\3\2\2\2\33\u00ba\3\2"+
		"\2\2\35\u00c3\3\2\2\2\37\u00ca\3\2\2\2!\u00ce\3\2\2\2#\u00d4\3\2\2\2%"+
		"\u00d9\3\2\2\2\'\u00db\3\2\2\2)\u00dd\3\2\2\2+\u00df\3\2\2\2-\u00e1\3"+
		"\2\2\2/\u00e3\3\2\2\2\61\u00e5\3\2\2\2\63\u00e7\3\2\2\2\65\u00e9\3\2\2"+
		"\2\67\u00eb\3\2\2\29\u00ed\3\2\2\2;\u00ef\3\2\2\2=\u00f1\3\2\2\2?\u00f3"+
		"\3\2\2\2A\u00f5\3\2\2\2C\u00f7\3\2\2\2E\u00f9\3\2\2\2G\u00fc\3\2\2\2I"+
		"\u00ff\3\2\2\2K\u0101\3\2\2\2M\u0103\3\2\2\2O\u0105\3\2\2\2Q\u0108\3\2"+
		"\2\2S\u010b\3\2\2\2U\u010e\3\2\2\2W\u0111\3\2\2\2Y\u0114\3\2\2\2[\u0117"+
		"\3\2\2\2]\u011a\3\2\2\2_\u011d\3\2\2\2a\u011f\3\2\2\2c\u0121\3\2\2\2e"+
		"\u0123\3\2\2\2g\u012b\3\2\2\2i\u012d\3\2\2\2k\u0138\3\2\2\2m\u013a\3\2"+
		"\2\2o\u0144\3\2\2\2q\u0146\3\2\2\2s\u0148\3\2\2\2u\u014b\3\2\2\2w\u0156"+
		"\3\2\2\2y\u015a\3\2\2\2{\u0168\3\2\2\2}~\7d\2\2~\177\7q\2\2\177\u0080"+
		"\7q\2\2\u0080\u0081\7n\2\2\u0081\4\3\2\2\2\u0082\u0083\7k\2\2\u0083\u0084"+
		"\7p\2\2\u0084\u0085\7v\2\2\u0085\6\3\2\2\2\u0086\u0087\7u\2\2\u0087\u0088"+
		"\7v\2\2\u0088\u0089\7t\2\2\u0089\u008a\7k\2\2\u008a\u008b\7p\2\2\u008b"+
		"\u008c\7i\2\2\u008c\b\3\2\2\2\u008d\u008e\7p\2\2\u008e\u008f\7w\2\2\u008f"+
		"\u0090\7n\2\2\u0090\u0091\7n\2\2\u0091\n\3\2\2\2\u0092\u0093\7x\2\2\u0093"+
		"\u0094\7q\2\2\u0094\u0095\7k\2\2\u0095\u0096\7f\2\2\u0096\f\3\2\2\2\u0097"+
		"\u0098\7v\2\2\u0098\u0099\7t\2\2\u0099\u009a\7w\2\2\u009a\u009b\7g\2\2"+
		"\u009b\16\3\2\2\2\u009c\u009d\7h\2\2\u009d\u009e\7c\2\2\u009e\u009f\7"+
		"n\2\2\u009f\u00a0\7u\2\2\u00a0\u00a1\7g\2\2\u00a1\20\3\2\2\2\u00a2\u00a3"+
		"\7k\2\2\u00a3\u00a4\7h\2\2\u00a4\22\3\2\2\2\u00a5\u00a6\7g\2\2\u00a6\u00a7"+
		"\7n\2\2\u00a7\u00a8\7u\2\2\u00a8\u00a9\7g\2\2\u00a9\24\3\2\2\2\u00aa\u00ab"+
		"\7h\2\2\u00ab\u00ac\7q\2\2\u00ac\u00ad\7t\2\2\u00ad\26\3\2\2\2\u00ae\u00af"+
		"\7y\2\2\u00af\u00b0\7j\2\2\u00b0\u00b1\7k\2\2\u00b1\u00b2\7n\2\2\u00b2"+
		"\u00b3\7g\2\2\u00b3\30\3\2\2\2\u00b4\u00b5\7d\2\2\u00b5\u00b6\7t\2\2\u00b6"+
		"\u00b7\7g\2\2\u00b7\u00b8\7c\2\2\u00b8\u00b9\7m\2\2\u00b9\32\3\2\2\2\u00ba"+
		"\u00bb\7e\2\2\u00bb\u00bc\7q\2\2\u00bc\u00bd\7p\2\2\u00bd\u00be\7v\2\2"+
		"\u00be\u00bf\7k\2\2\u00bf\u00c0\7p\2\2\u00c0\u00c1\7w\2\2\u00c1\u00c2"+
		"\7g\2\2\u00c2\34\3\2\2\2\u00c3\u00c4\7t\2\2\u00c4\u00c5\7g\2\2\u00c5\u00c6"+
		"\7v\2\2\u00c6\u00c7\7w\2\2\u00c7\u00c8\7t\2\2\u00c8\u00c9\7p\2\2\u00c9"+
		"\36\3\2\2\2\u00ca\u00cb\7p\2\2\u00cb\u00cc\7g\2\2\u00cc\u00cd\7y\2\2\u00cd"+
		" \3\2\2\2\u00ce\u00cf\7e\2\2\u00cf\u00d0\7n\2\2\u00d0\u00d1\7c\2\2\u00d1"+
		"\u00d2\7u\2\2\u00d2\u00d3\7u\2\2\u00d3\"\3\2\2\2\u00d4\u00d5\7v\2\2\u00d5"+
		"\u00d6\7j\2\2\u00d6\u00d7\7k\2\2\u00d7\u00d8\7u\2\2\u00d8$\3\2\2\2\u00d9"+
		"\u00da\7*\2\2\u00da&\3\2\2\2\u00db\u00dc\7+\2\2\u00dc(\3\2\2\2\u00dd\u00de"+
		"\7]\2\2\u00de*\3\2\2\2\u00df\u00e0\7_\2\2\u00e0,\3\2\2\2\u00e1\u00e2\7"+
		"}\2\2\u00e2.\3\2\2\2\u00e3\u00e4\7\177\2\2\u00e4\60\3\2\2\2\u00e5\u00e6"+
		"\7-\2\2\u00e6\62\3\2\2\2\u00e7\u00e8\7/\2\2\u00e8\64\3\2\2\2\u00e9\u00ea"+
		"\7,\2\2\u00ea\66\3\2\2\2\u00eb\u00ec\7\61\2\2\u00ec8\3\2\2\2\u00ed\u00ee"+
		"\7\'\2\2\u00ee:\3\2\2\2\u00ef\u00f0\7`\2\2\u00f0<\3\2\2\2\u00f1\u00f2"+
		"\7(\2\2\u00f2>\3\2\2\2\u00f3\u00f4\7~\2\2\u00f4@\3\2\2\2\u00f5\u00f6\7"+
		"\u0080\2\2\u00f6B\3\2\2\2\u00f7\u00f8\7#\2\2\u00f8D\3\2\2\2\u00f9\u00fa"+
		"\7>\2\2\u00fa\u00fb\7>\2\2\u00fbF\3\2\2\2\u00fc\u00fd\7@\2\2\u00fd\u00fe"+
		"\7@\2\2\u00feH\3\2\2\2\u00ff\u0100\7?\2\2\u0100J\3\2\2\2\u0101\u0102\7"+
		">\2\2\u0102L\3\2\2\2\u0103\u0104\7@\2\2\u0104N\3\2\2\2\u0105\u0106\7?"+
		"\2\2\u0106\u0107\7?\2\2\u0107P\3\2\2\2\u0108\u0109\7#\2\2\u0109\u010a"+
		"\7?\2\2\u010aR\3\2\2\2\u010b\u010c\7>\2\2\u010c\u010d\7?\2\2\u010dT\3"+
		"\2\2\2\u010e\u010f\7@\2\2\u010f\u0110\7?\2\2\u0110V\3\2\2\2\u0111\u0112"+
		"\7(\2\2\u0112\u0113\7(\2\2\u0113X\3\2\2\2\u0114\u0115\7~\2\2\u0115\u0116"+
		"\7~\2\2\u0116Z\3\2\2\2\u0117\u0118\7-\2\2\u0118\u0119\7-\2\2\u0119\\\3"+
		"\2\2\2\u011a\u011b\7/\2\2\u011b\u011c\7/\2\2\u011c^\3\2\2\2\u011d\u011e"+
		"\7.\2\2\u011e`\3\2\2\2\u011f\u0120\7\60\2\2\u0120b\3\2\2\2\u0121\u0122"+
		"\7=\2\2\u0122d\3\2\2\2\u0123\u0128\5q9\2\u0124\u0127\5s:\2\u0125\u0127"+
		"\5k\66\2\u0126\u0124\3\2\2\2\u0126\u0125\3\2\2\2\u0127\u012a\3\2\2\2\u0128"+
		"\u0126\3\2\2\2\u0128\u0129\3\2\2\2\u0129f\3\2\2\2\u012a\u0128\3\2\2\2"+
		"\u012b\u012c\5o8\2\u012ch\3\2\2\2\u012d\u0133\7$\2\2\u012e\u0132\n\2\2"+
		"\2\u012f\u0130\7^\2\2\u0130\u0132\t\3\2\2\u0131\u012e\3\2\2\2\u0131\u012f"+
		"\3\2\2\2\u0132\u0135\3\2\2\2\u0133\u0131\3\2\2\2\u0133\u0134\3\2\2\2\u0134"+
		"\u0136\3\2\2\2\u0135\u0133\3\2\2\2\u0136\u0137\7$\2\2\u0137j\3\2\2\2\u0138"+
		"\u0139\t\4\2\2\u0139l\3\2\2\2\u013a\u013b\t\5\2\2\u013bn\3\2\2\2\u013c"+
		"\u0140\5m\67\2\u013d\u013f\5k\66\2\u013e\u013d\3\2\2\2\u013f\u0142\3\2"+
		"\2\2\u0140\u013e\3\2\2\2\u0140\u0141\3\2\2\2\u0141\u0145\3\2\2\2\u0142"+
		"\u0140\3\2\2\2\u0143\u0145\5k\66\2\u0144\u013c\3\2\2\2\u0144\u0143\3\2"+
		"\2\2\u0145p\3\2\2\2\u0146\u0147\t\6\2\2\u0147r\3\2\2\2\u0148\u0149\t\7"+
		"\2\2\u0149t\3\2\2\2\u014a\u014c\t\b\2\2\u014b\u014a\3\2\2\2\u014c\u014d"+
		"\3\2\2\2\u014d\u014b\3\2\2\2\u014d\u014e\3\2\2\2\u014e\u014f\3\2\2\2\u014f"+
		"\u0150\b;\2\2\u0150v\3\2\2\2\u0151\u0153\7\17\2\2\u0152\u0154\7\f\2\2"+
		"\u0153\u0152\3\2\2\2\u0153\u0154\3\2\2\2\u0154\u0157\3\2\2\2\u0155\u0157"+
		"\7\f\2\2\u0156\u0151\3\2\2\2\u0156\u0155\3\2\2\2\u0157\u0158\3\2\2\2\u0158"+
		"\u0159\b<\2\2\u0159x\3\2\2\2\u015a\u015b\7\61\2\2\u015b\u015c\7,\2\2\u015c"+
		"\u0160\3\2\2\2\u015d\u015f\13\2\2\2\u015e\u015d\3\2\2\2\u015f\u0162\3"+
		"\2\2\2\u0160\u0161\3\2\2\2\u0160\u015e\3\2\2\2\u0161\u0163\3\2\2\2\u0162"+
		"\u0160\3\2\2\2\u0163\u0164\7,\2\2\u0164\u0165\7\61\2\2\u0165\u0166\3\2"+
		"\2\2\u0166\u0167\b=\2\2\u0167z\3\2\2\2\u0168\u0169\7\61\2\2\u0169\u016a"+
		"\7\61\2\2\u016a\u016e\3\2\2\2\u016b\u016d\n\t\2\2\u016c\u016b\3\2\2\2"+
		"\u016d\u0170\3\2\2\2\u016e\u016c\3\2\2\2\u016e\u016f\3\2\2\2\u016f\u0171"+
		"\3\2\2\2\u0170\u016e\3\2\2\2\u0171\u0172\b>\2\2\u0172|\3\2\2\2\16\2\u0126"+
		"\u0128\u0131\u0133\u0140\u0144\u014d\u0153\u0156\u0160\u016e\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}