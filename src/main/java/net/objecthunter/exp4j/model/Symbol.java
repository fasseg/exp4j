package net.objecthunter.exp4j.model;

public class Symbol {
	
	/**
	 * The set of allowed operator chars
	 */
	public static final char[] ALLOWED_OPERATOR_CHARS = { '+', '-', '*', '/', '%', '^', '!', '#','§', '$', '&', ';', ':', '~', '<', '>', '|', '='};
	
	public enum Type {
		OPERATOR, FUNCTION, NUMBER
	}
	
	private final Type type;
	
	private final double doubleValue;
	
	private final String stringValue;
	
	public Symbol(final double doubleValue) {
		this.doubleValue = doubleValue;
		this.type = Type.NUMBER;
		this.stringValue = null;
	}
	
	public Symbol(final Type type, final String stringValue) {
		this.type = type;
		this.stringValue = stringValue;
		this.doubleValue = 0f;
	}
	
	public Type getType() {
		return type;
	}
	
	public double getDoubleValue() {
		return doubleValue;
	}
	
	public String getStringValue() {
		return stringValue;
	}
	
	public static boolean isAllowedOperatorChar(char ch) {
		final int len = ALLOWED_OPERATOR_CHARS.length;
		for (int i = 0; i < len; i++) {
			if (ALLOWED_OPERATOR_CHARS[i] == ch) {
				return true;
			}
		}
		return false;
	}
}
