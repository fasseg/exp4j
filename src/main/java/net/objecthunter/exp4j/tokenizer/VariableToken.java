package net.objecthunter.exp4j.tokenizer;

public class VariableToken extends Token {

	private final String name;

	public VariableToken(String name) {
		super(Token.Type.VARIABLE);
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
