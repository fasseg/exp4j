package net.objecthunter.exp4j.tokens;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.tokenizer.Tokenizer;

public class FunctionToken extends Token {
	private final Function func;

	public FunctionToken(final Function func) {
		super(FUNCTION);
		this.func = func;
	}

	public Function getFunction() {
		return func;
	}
}
