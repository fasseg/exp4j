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
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, NAME=15, NUMBER=16, WHITESPACE=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "NAME", "NUMBER", "WHITESPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "','", "'+'", "'-'", "'*'", "'/'", "'^'", "'%'", 
			"'pi'", "'\\u03C0'", "'phi'", "'\\u03C6'", "'e'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, "NAME", "NUMBER", "WHITESPACE"
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
		"\u0004\u0000\u0011r\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001"+
		"\r\u0001\r\u0001\u000e\u0001\u000e\u0005\u000eE\b\u000e\n\u000e\f\u000e"+
		"H\t\u000e\u0001\u000f\u0004\u000fK\b\u000f\u000b\u000f\f\u000fL\u0001"+
		"\u000f\u0001\u000f\u0004\u000fQ\b\u000f\u000b\u000f\f\u000fR\u0003\u000f"+
		"U\b\u000f\u0001\u000f\u0001\u000f\u0003\u000fY\b\u000f\u0001\u000f\u0004"+
		"\u000f\\\b\u000f\u000b\u000f\f\u000f]\u0001\u000f\u0001\u000f\u0004\u000f"+
		"b\b\u000f\u000b\u000f\f\u000fc\u0003\u000ff\b\u000f\u0003\u000fh\b\u000f"+
		"\u0003\u000fj\b\u000f\u0001\u0010\u0004\u0010m\b\u0010\u000b\u0010\f\u0010"+
		"n\u0001\u0010\u0001\u0010\u0000\u0000\u0011\u0001\u0001\u0003\u0002\u0005"+
		"\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n"+
		"\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011"+
		"\u0001\u0000\u0005\u0003\u0000AZ__az\u0004\u000009AZ__az\u0001\u00000"+
		"9\u0002\u0000EEee\u0003\u0000\t\n\r\r  |\u0000\u0001\u0001\u0000\u0000"+
		"\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000"+
		"\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000"+
		"\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000"+
		"\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000"+
		"\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000"+
		"\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000"+
		"\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000"+
		"\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0001#\u0001"+
		"\u0000\u0000\u0000\u0003%\u0001\u0000\u0000\u0000\u0005\'\u0001\u0000"+
		"\u0000\u0000\u0007)\u0001\u0000\u0000\u0000\t+\u0001\u0000\u0000\u0000"+
		"\u000b-\u0001\u0000\u0000\u0000\r/\u0001\u0000\u0000\u0000\u000f1\u0001"+
		"\u0000\u0000\u0000\u00113\u0001\u0000\u0000\u0000\u00135\u0001\u0000\u0000"+
		"\u0000\u00158\u0001\u0000\u0000\u0000\u0017:\u0001\u0000\u0000\u0000\u0019"+
		">\u0001\u0000\u0000\u0000\u001b@\u0001\u0000\u0000\u0000\u001dB\u0001"+
		"\u0000\u0000\u0000\u001fJ\u0001\u0000\u0000\u0000!l\u0001\u0000\u0000"+
		"\u0000#$\u0005(\u0000\u0000$\u0002\u0001\u0000\u0000\u0000%&\u0005)\u0000"+
		"\u0000&\u0004\u0001\u0000\u0000\u0000\'(\u0005,\u0000\u0000(\u0006\u0001"+
		"\u0000\u0000\u0000)*\u0005+\u0000\u0000*\b\u0001\u0000\u0000\u0000+,\u0005"+
		"-\u0000\u0000,\n\u0001\u0000\u0000\u0000-.\u0005*\u0000\u0000.\f\u0001"+
		"\u0000\u0000\u0000/0\u0005/\u0000\u00000\u000e\u0001\u0000\u0000\u0000"+
		"12\u0005^\u0000\u00002\u0010\u0001\u0000\u0000\u000034\u0005%\u0000\u0000"+
		"4\u0012\u0001\u0000\u0000\u000056\u0005p\u0000\u000067\u0005i\u0000\u0000"+
		"7\u0014\u0001\u0000\u0000\u000089\u0005\u03c0\u0000\u00009\u0016\u0001"+
		"\u0000\u0000\u0000:;\u0005p\u0000\u0000;<\u0005h\u0000\u0000<=\u0005i"+
		"\u0000\u0000=\u0018\u0001\u0000\u0000\u0000>?\u0005\u03c6\u0000\u0000"+
		"?\u001a\u0001\u0000\u0000\u0000@A\u0005e\u0000\u0000A\u001c\u0001\u0000"+
		"\u0000\u0000BF\u0007\u0000\u0000\u0000CE\u0007\u0001\u0000\u0000DC\u0001"+
		"\u0000\u0000\u0000EH\u0001\u0000\u0000\u0000FD\u0001\u0000\u0000\u0000"+
		"FG\u0001\u0000\u0000\u0000G\u001e\u0001\u0000\u0000\u0000HF\u0001\u0000"+
		"\u0000\u0000IK\u0007\u0002\u0000\u0000JI\u0001\u0000\u0000\u0000KL\u0001"+
		"\u0000\u0000\u0000LJ\u0001\u0000\u0000\u0000LM\u0001\u0000\u0000\u0000"+
		"Mi\u0001\u0000\u0000\u0000NP\u0005.\u0000\u0000OQ\u0007\u0002\u0000\u0000"+
		"PO\u0001\u0000\u0000\u0000QR\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000"+
		"\u0000RS\u0001\u0000\u0000\u0000SU\u0001\u0000\u0000\u0000TN\u0001\u0000"+
		"\u0000\u0000TU\u0001\u0000\u0000\u0000Ug\u0001\u0000\u0000\u0000VX\u0007"+
		"\u0003\u0000\u0000WY\u0005-\u0000\u0000XW\u0001\u0000\u0000\u0000XY\u0001"+
		"\u0000\u0000\u0000Y[\u0001\u0000\u0000\u0000Z\\\u0007\u0002\u0000\u0000"+
		"[Z\u0001\u0000\u0000\u0000\\]\u0001\u0000\u0000\u0000][\u0001\u0000\u0000"+
		"\u0000]^\u0001\u0000\u0000\u0000^e\u0001\u0000\u0000\u0000_a\u0005.\u0000"+
		"\u0000`b\u0007\u0002\u0000\u0000a`\u0001\u0000\u0000\u0000bc\u0001\u0000"+
		"\u0000\u0000ca\u0001\u0000\u0000\u0000cd\u0001\u0000\u0000\u0000df\u0001"+
		"\u0000\u0000\u0000e_\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000"+
		"fh\u0001\u0000\u0000\u0000gV\u0001\u0000\u0000\u0000gh\u0001\u0000\u0000"+
		"\u0000hj\u0001\u0000\u0000\u0000iT\u0001\u0000\u0000\u0000ij\u0001\u0000"+
		"\u0000\u0000j \u0001\u0000\u0000\u0000km\u0007\u0004\u0000\u0000lk\u0001"+
		"\u0000\u0000\u0000mn\u0001\u0000\u0000\u0000nl\u0001\u0000\u0000\u0000"+
		"no\u0001\u0000\u0000\u0000op\u0001\u0000\u0000\u0000pq\u0006\u0010\u0000"+
		"\u0000q\"\u0001\u0000\u0000\u0000\f\u0000FLRTX]cegin\u0001\u0006\u0000"+
		"\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}