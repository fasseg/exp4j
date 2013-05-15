package net.objecthunter.exp4j.tokenizer;

import net.objecthunter.exp4j.function.CustomFunction;

public class FunctionToken extends Token {
	private final CustomFunction function;

	public FunctionToken(final CustomFunction func) {
		super(Token.Type.FUNCTION);
		this.function = func;
	}

	public CustomFunction getFunction() {
		return function;
	}
}
