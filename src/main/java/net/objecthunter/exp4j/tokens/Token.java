package net.objecthunter.exp4j.tokens;

public abstract class Token {
	public static final int EOF = 1;
	public static final int ILLEGAL = 2;
	public static final int VARIABLE = 3;
	public static final int NUMBER = 4;
	public static final int FUNCTION = 5;
	public static final int OPERATOR = 6;
	public static final int PARANTHESES_LEFT = 7;
	public static final int PARANTHESES_RIGHT = 8;
	public static final int ARGUMENT_SEPARATOR = 9;
	public static final int UNARY_OPERATOR = 10;

	private final int type;

	public Token(int type) {
		super();
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
