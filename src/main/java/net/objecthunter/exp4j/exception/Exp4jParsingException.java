package net.objecthunter.exp4j.exception;

public class Exp4jParsingException extends Exception {
	public Exp4jParsingException(final char[] expression, final char lastChar, final int idx) {
		super("Invalid character '" + lastChar + "' at position " + idx + " in expression '" + new String(expression) + "'");
	}
	
	public Exp4jParsingException(final char[] expression, final String name, final int start) {
		super ("Invalid variable or function '" + name + "' at position " + start + " in expression '" + new String(expression) + "'");
	}
}
