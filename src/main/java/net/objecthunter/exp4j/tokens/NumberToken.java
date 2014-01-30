package net.objecthunter.exp4j.tokens;

import net.objecthunter.exp4j.tokenizer.FastTokenizer;

public class NumberToken extends Token {
	private final double value;

	public NumberToken(final double value) {
		super(NUMBER);
		this.value = value;
	}

	public double getValue() {
		return value;
	}

}
