// Generated from java-escape by ANTLR 4.11.1
package net.objecthunter.exp4j.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class Exp4jGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, NAME=16, NUMBER=17, 
		WHITESPACE=18;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "NAME", "NUMBER", 
			"WHITESPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "','", "')'", "'+'", "'-'", "'!'", "'*'", "'/'", "'^'", 
			"'%'", "'pi'", "'\\u03C0'", "'phi'", "'\\u03C6'", "'e'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, "NAME", "NUMBER", "WHITESPACE"
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


	public Exp4jGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Exp4jGrammar.g4"; }

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
		"\u0004\u0000\u0012v\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f"+
		"\u0005\u000fI\b\u000f\n\u000f\f\u000fL\t\u000f\u0001\u0010\u0004\u0010"+
		"O\b\u0010\u000b\u0010\f\u0010P\u0001\u0010\u0001\u0010\u0004\u0010U\b"+
		"\u0010\u000b\u0010\f\u0010V\u0003\u0010Y\b\u0010\u0001\u0010\u0001\u0010"+
		"\u0003\u0010]\b\u0010\u0001\u0010\u0004\u0010`\b\u0010\u000b\u0010\f\u0010"+
		"a\u0001\u0010\u0001\u0010\u0004\u0010f\b\u0010\u000b\u0010\f\u0010g\u0003"+
		"\u0010j\b\u0010\u0003\u0010l\b\u0010\u0003\u0010n\b\u0010\u0001\u0011"+
		"\u0004\u0011q\b\u0011\u000b\u0011\f\u0011r\u0001\u0011\u0001\u0011\u0000"+
		"\u0000\u0012\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b"+
		"\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b"+
		"\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012\u0001\u0000\u0005\u0003\u0000"+
		"AZ__az\u0004\u000009AZ__az\u0001\u000009\u0002\u0000EEee\u0003\u0000\t"+
		"\n\r\r  \u0080\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000"+
		"\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000"+
		"\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000"+
		"\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000"+
		"\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000"+
		"\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000"+
		"\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000"+
		"\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000"+
		"\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0001%"+
		"\u0001\u0000\u0000\u0000\u0003\'\u0001\u0000\u0000\u0000\u0005)\u0001"+
		"\u0000\u0000\u0000\u0007+\u0001\u0000\u0000\u0000\t-\u0001\u0000\u0000"+
		"\u0000\u000b/\u0001\u0000\u0000\u0000\r1\u0001\u0000\u0000\u0000\u000f"+
		"3\u0001\u0000\u0000\u0000\u00115\u0001\u0000\u0000\u0000\u00137\u0001"+
		"\u0000\u0000\u0000\u00159\u0001\u0000\u0000\u0000\u0017<\u0001\u0000\u0000"+
		"\u0000\u0019>\u0001\u0000\u0000\u0000\u001bB\u0001\u0000\u0000\u0000\u001d"+
		"D\u0001\u0000\u0000\u0000\u001fF\u0001\u0000\u0000\u0000!N\u0001\u0000"+
		"\u0000\u0000#p\u0001\u0000\u0000\u0000%&\u0005(\u0000\u0000&\u0002\u0001"+
		"\u0000\u0000\u0000\'(\u0005,\u0000\u0000(\u0004\u0001\u0000\u0000\u0000"+
		")*\u0005)\u0000\u0000*\u0006\u0001\u0000\u0000\u0000+,\u0005+\u0000\u0000"+
		",\b\u0001\u0000\u0000\u0000-.\u0005-\u0000\u0000.\n\u0001\u0000\u0000"+
		"\u0000/0\u0005!\u0000\u00000\f\u0001\u0000\u0000\u000012\u0005*\u0000"+
		"\u00002\u000e\u0001\u0000\u0000\u000034\u0005/\u0000\u00004\u0010\u0001"+
		"\u0000\u0000\u000056\u0005^\u0000\u00006\u0012\u0001\u0000\u0000\u0000"+
		"78\u0005%\u0000\u00008\u0014\u0001\u0000\u0000\u00009:\u0005p\u0000\u0000"+
		":;\u0005i\u0000\u0000;\u0016\u0001\u0000\u0000\u0000<=\u0005\u03c0\u0000"+
		"\u0000=\u0018\u0001\u0000\u0000\u0000>?\u0005p\u0000\u0000?@\u0005h\u0000"+
		"\u0000@A\u0005i\u0000\u0000A\u001a\u0001\u0000\u0000\u0000BC\u0005\u03c6"+
		"\u0000\u0000C\u001c\u0001\u0000\u0000\u0000DE\u0005e\u0000\u0000E\u001e"+
		"\u0001\u0000\u0000\u0000FJ\u0007\u0000\u0000\u0000GI\u0007\u0001\u0000"+
		"\u0000HG\u0001\u0000\u0000\u0000IL\u0001\u0000\u0000\u0000JH\u0001\u0000"+
		"\u0000\u0000JK\u0001\u0000\u0000\u0000K \u0001\u0000\u0000\u0000LJ\u0001"+
		"\u0000\u0000\u0000MO\u0007\u0002\u0000\u0000NM\u0001\u0000\u0000\u0000"+
		"OP\u0001\u0000\u0000\u0000PN\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000"+
		"\u0000Qm\u0001\u0000\u0000\u0000RT\u0005.\u0000\u0000SU\u0007\u0002\u0000"+
		"\u0000TS\u0001\u0000\u0000\u0000UV\u0001\u0000\u0000\u0000VT\u0001\u0000"+
		"\u0000\u0000VW\u0001\u0000\u0000\u0000WY\u0001\u0000\u0000\u0000XR\u0001"+
		"\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000Yk\u0001\u0000\u0000\u0000"+
		"Z\\\u0007\u0003\u0000\u0000[]\u0005-\u0000\u0000\\[\u0001\u0000\u0000"+
		"\u0000\\]\u0001\u0000\u0000\u0000]_\u0001\u0000\u0000\u0000^`\u0007\u0002"+
		"\u0000\u0000_^\u0001\u0000\u0000\u0000`a\u0001\u0000\u0000\u0000a_\u0001"+
		"\u0000\u0000\u0000ab\u0001\u0000\u0000\u0000bi\u0001\u0000\u0000\u0000"+
		"ce\u0005.\u0000\u0000df\u0007\u0002\u0000\u0000ed\u0001\u0000\u0000\u0000"+
		"fg\u0001\u0000\u0000\u0000ge\u0001\u0000\u0000\u0000gh\u0001\u0000\u0000"+
		"\u0000hj\u0001\u0000\u0000\u0000ic\u0001\u0000\u0000\u0000ij\u0001\u0000"+
		"\u0000\u0000jl\u0001\u0000\u0000\u0000kZ\u0001\u0000\u0000\u0000kl\u0001"+
		"\u0000\u0000\u0000ln\u0001\u0000\u0000\u0000mX\u0001\u0000\u0000\u0000"+
		"mn\u0001\u0000\u0000\u0000n\"\u0001\u0000\u0000\u0000oq\u0007\u0004\u0000"+
		"\u0000po\u0001\u0000\u0000\u0000qr\u0001\u0000\u0000\u0000rp\u0001\u0000"+
		"\u0000\u0000rs\u0001\u0000\u0000\u0000st\u0001\u0000\u0000\u0000tu\u0006"+
		"\u0011\u0000\u0000u$\u0001\u0000\u0000\u0000\f\u0000JPVX\\agikmr\u0001"+
		"\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}