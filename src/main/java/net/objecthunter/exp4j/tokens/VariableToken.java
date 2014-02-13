package net.objecthunter.exp4j.tokens;

import net.objecthunter.exp4j.tokenizer.FastTokenizer;

public class VariableToken extends Token {
	private final String name;

	public VariableToken(final String name) {
		super(VARIABLE);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
