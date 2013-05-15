package net.objecthunter.exp4j.tokenizer;


public abstract class Token {

	public enum Type {
		NUMBER, FUNCTION, OPERATOR, PARANTHESES, ARGUMENT_SEPARATOR;
	}

	private final Type type;

	public Token(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}
}