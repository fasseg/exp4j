package net.objecthunter.exp4j.tokenizer;

public class ParanthesesToken extends Token {
	private final boolean open;

	public ParanthesesToken(final boolean open) {
		super(Token.Type.PARANTHESES);
		this.open = open;
	}

	public boolean isOpen() {
		return open;
	}

}