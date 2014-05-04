// Generated from Expression.g4 by ANTLR 4.0

package Pingball;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, NEWLINE1=2, NEWLINE2=3, EQUALS=4, BOARD_WORD=5, BALL_WORD=6, 
		FIRE_WORD=7, BOARDELEMENT=8, FIELD=9, INTEGER=10, FLOAT=11, NAME=12, COMM=13;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"WHITESPACE", "'\n'", "'\r'", "'='", "'board'", "'ball'", "'fire'", "BOARDELEMENT", 
		"FIELD", "INTEGER", "FLOAT", "NAME", "COMM"
	};
	public static final String[] ruleNames = {
		"WHITESPACE", "NEWLINE1", "NEWLINE2", "EQUALS", "BOARD_WORD", "BALL_WORD", 
		"FIRE_WORD", "BOARDELEMENT", "FIELD", "INTEGER", "FLOAT", "NAME", "TEXT", 
		"COMM"
	};


	    /**
	     * Call this method to have the lexer or parser throw a RuntimeException if
	     * it encounters an error.
	     */
	    public void reportErrorsAsExceptions() {
	        addErrorListener(new ExceptionThrowingErrorListener());
	    }
	    
	    private static class ExceptionThrowingErrorListener extends BaseErrorListener {
	        @Override
	        public void syntaxError(Recognizer<?, ?> recognizer,
	                Object offendingSymbol, int line, int charPositionInLine,
	                String msg, RecognitionException e) {
	            throw new RuntimeException(msg);
	        }
	    }


	public ExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Expression.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 0: WHITESPACE_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WHITESPACE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\17\u00ff\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b"+
		"\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2"+
		"\6\2!\n\2\r\2\16\2\"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u0082\n\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u00d7\n\n\3\13\6\13\u00da\n"+
		"\13\r\13\16\13\u00db\3\f\5\f\u00df\n\f\3\f\7\f\u00e2\n\f\f\f\16\f\u00e5"+
		"\13\f\3\f\3\f\6\f\u00e9\n\f\r\f\16\f\u00ea\3\r\3\r\7\r\u00ef\n\r\f\r\16"+
		"\r\u00f2\13\r\3\16\6\16\u00f5\n\16\r\16\16\16\u00f6\3\17\3\17\7\17\u00fb"+
		"\n\17\f\17\16\17\u00fe\13\17\2\20\3\3\2\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1"+
		"\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33\2\1\35\17\1\3\2\t\4\13"+
		"\13\"\"\3\62;\3\62;\3\62;\5C\\aac|\6\62;C\\aac|\6\f\f\17\17))~~\u0115"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\35\3\2\2\2\3 \3\2\2\2\5&\3\2\2\2\7(\3\2\2\2\t*\3\2\2\2"+
		"\13,\3\2\2\2\r\62\3\2\2\2\17\67\3\2\2\2\21\u0081\3\2\2\2\23\u00d6\3\2"+
		"\2\2\25\u00d9\3\2\2\2\27\u00de\3\2\2\2\31\u00ec\3\2\2\2\33\u00f4\3\2\2"+
		"\2\35\u00f8\3\2\2\2\37!\t\2\2\2 \37\3\2\2\2!\"\3\2\2\2\" \3\2\2\2\"#\3"+
		"\2\2\2#$\3\2\2\2$%\b\2\2\2%\4\3\2\2\2&\'\7\f\2\2\'\6\3\2\2\2()\7\17\2"+
		"\2)\b\3\2\2\2*+\7?\2\2+\n\3\2\2\2,-\7d\2\2-.\7q\2\2./\7c\2\2/\60\7t\2"+
		"\2\60\61\7f\2\2\61\f\3\2\2\2\62\63\7d\2\2\63\64\7c\2\2\64\65\7n\2\2\65"+
		"\66\7n\2\2\66\16\3\2\2\2\678\7h\2\289\7k\2\29:\7t\2\2:;\7g\2\2;\20\3\2"+
		"\2\2<=\7u\2\2=>\7s\2\2>?\7w\2\2?@\7c\2\2@A\7t\2\2AB\7g\2\2BC\7D\2\2CD"+
		"\7w\2\2DE\7o\2\2EF\7r\2\2FG\7g\2\2G\u0082\7t\2\2HI\7e\2\2IJ\7k\2\2JK\7"+
		"t\2\2KL\7e\2\2LM\7n\2\2MN\7g\2\2NO\7D\2\2OP\7w\2\2PQ\7o\2\2QR\7r\2\2R"+
		"S\7g\2\2S\u0082\7t\2\2TU\7v\2\2UV\7t\2\2VW\7k\2\2WX\7c\2\2XY\7p\2\2YZ"+
		"\7i\2\2Z[\7n\2\2[\\\7g\2\2\\]\7D\2\2]^\7w\2\2^_\7o\2\2_`\7r\2\2`a\7g\2"+
		"\2a\u0082\7t\2\2bc\7t\2\2cd\7k\2\2de\7i\2\2ef\7j\2\2fg\7v\2\2gh\7H\2\2"+
		"hi\7n\2\2ij\7k\2\2jk\7r\2\2kl\7r\2\2lm\7g\2\2m\u0082\7t\2\2no\7n\2\2o"+
		"p\7g\2\2pq\7h\2\2qr\7v\2\2rs\7H\2\2st\7n\2\2tu\7k\2\2uv\7r\2\2vw\7r\2"+
		"\2wx\7g\2\2x\u0082\7t\2\2yz\7c\2\2z{\7d\2\2{|\7u\2\2|}\7q\2\2}~\7t\2\2"+
		"~\177\7d\2\2\177\u0080\7g\2\2\u0080\u0082\7t\2\2\u0081<\3\2\2\2\u0081"+
		"H\3\2\2\2\u0081T\3\2\2\2\u0081b\3\2\2\2\u0081n\3\2\2\2\u0081y\3\2\2\2"+
		"\u0082\22\3\2\2\2\u0083\u0084\7p\2\2\u0084\u0085\7c\2\2\u0085\u0086\7"+
		"o\2\2\u0086\u00d7\7g\2\2\u0087\u0088\7i\2\2\u0088\u0089\7t\2\2\u0089\u008a"+
		"\7c\2\2\u008a\u008b\7x\2\2\u008b\u008c\7k\2\2\u008c\u008d\7v\2\2\u008d"+
		"\u00d7\7{\2\2\u008e\u00d7\4z{\2\u008f\u0090\7h\2\2\u0090\u0091\7t\2\2"+
		"\u0091\u0092\7k\2\2\u0092\u0093\7e\2\2\u0093\u0094\7v\2\2\u0094\u0095"+
		"\7k\2\2\u0095\u0096\7q\2\2\u0096\u0097\7p\2\2\u0097\u00d7\7\63\2\2\u0098"+
		"\u0099\7h\2\2\u0099\u009a\7t\2\2\u009a\u009b\7k\2\2\u009b\u009c\7e\2\2"+
		"\u009c\u009d\7v\2\2\u009d\u009e\7k\2\2\u009e\u009f\7q\2\2\u009f\u00a0"+
		"\7p\2\2\u00a0\u00d7\7\64\2\2\u00a1\u00a2\7y\2\2\u00a2\u00a3\7k\2\2\u00a3"+
		"\u00a4\7f\2\2\u00a4\u00a5\7v\2\2\u00a5\u00d7\7j\2\2\u00a6\u00a7\7j\2\2"+
		"\u00a7\u00a8\7g\2\2\u00a8\u00a9\7k\2\2\u00a9\u00aa\7i\2\2\u00aa\u00ab"+
		"\7j\2\2\u00ab\u00d7\7v\2\2\u00ac\u00ad\7v\2\2\u00ad\u00ae\7t\2\2\u00ae"+
		"\u00af\7k\2\2\u00af\u00b0\7i\2\2\u00b0\u00b1\7i\2\2\u00b1\u00b2\7g\2\2"+
		"\u00b2\u00d7\7t\2\2\u00b3\u00b4\7c\2\2\u00b4\u00b5\7e\2\2\u00b5\u00b6"+
		"\7v\2\2\u00b6\u00b7\7k\2\2\u00b7\u00b8\7q\2\2\u00b8\u00d7\7p\2\2\u00b9"+
		"\u00ba\7z\2\2\u00ba\u00bb\7X\2\2\u00bb\u00bc\7g\2\2\u00bc\u00bd\7n\2\2"+
		"\u00bd\u00be\7q\2\2\u00be\u00bf\7e\2\2\u00bf\u00c0\7k\2\2\u00c0\u00c1"+
		"\7v\2\2\u00c1\u00d7\7{\2\2\u00c2\u00c3\7{\2\2\u00c3\u00c4\7X\2\2\u00c4"+
		"\u00c5\7g\2\2\u00c5\u00c6\7n\2\2\u00c6\u00c7\7q\2\2\u00c7\u00c8\7e\2\2"+
		"\u00c8\u00c9\7k\2\2\u00c9\u00ca\7v\2\2\u00ca\u00d7\7{\2\2\u00cb\u00cc"+
		"\7q\2\2\u00cc\u00cd\7t\2\2\u00cd\u00ce\7k\2\2\u00ce\u00cf\7g\2\2\u00cf"+
		"\u00d0\7p\2\2\u00d0\u00d1\7v\2\2\u00d1\u00d2\7c\2\2\u00d2\u00d3\7v\2\2"+
		"\u00d3\u00d4\7k\2\2\u00d4\u00d5\7q\2\2\u00d5\u00d7\7p\2\2\u00d6\u0083"+
		"\3\2\2\2\u00d6\u0087\3\2\2\2\u00d6\u008e\3\2\2\2\u00d6\u008f\3\2\2\2\u00d6"+
		"\u0098\3\2\2\2\u00d6\u00a1\3\2\2\2\u00d6\u00a6\3\2\2\2\u00d6\u00ac\3\2"+
		"\2\2\u00d6\u00b3\3\2\2\2\u00d6\u00b9\3\2\2\2\u00d6\u00c2\3\2\2\2\u00d6"+
		"\u00cb\3\2\2\2\u00d7\24\3\2\2\2\u00d8\u00da\t\3\2\2\u00d9\u00d8\3\2\2"+
		"\2\u00da\u00db\3\2\2\2\u00db\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\26"+
		"\3\2\2\2\u00dd\u00df\7/\2\2\u00de\u00dd\3\2\2\2\u00de\u00df\3\2\2\2\u00df"+
		"\u00e3\3\2\2\2\u00e0\u00e2\t\4\2\2\u00e1\u00e0\3\2\2\2\u00e2\u00e5\3\2"+
		"\2\2\u00e3\u00e1\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e6\3\2\2\2\u00e5"+
		"\u00e3\3\2\2\2\u00e6\u00e8\7\60\2\2\u00e7\u00e9\t\5\2\2\u00e8\u00e7\3"+
		"\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb"+
		"\30\3\2\2\2\u00ec\u00f0\t\6\2\2\u00ed\u00ef\t\7\2\2\u00ee\u00ed\3\2\2"+
		"\2\u00ef\u00f2\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\32"+
		"\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f3\u00f5\n\b\2\2\u00f4\u00f3\3\2\2\2\u00f5"+
		"\u00f6\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\34\3\2\2"+
		"\2\u00f8\u00fc\7%\2\2\u00f9\u00fb\5\33\16\2\u00fa\u00f9\3\2\2\2\u00fb"+
		"\u00fe\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd\36\3\2\2"+
		"\2\u00fe\u00fc\3\2\2\2\r\2\"\u0081\u00d6\u00db\u00de\u00e3\u00ea\u00f0"+
		"\u00f6\u00fc";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}