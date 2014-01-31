package net.objecthunter.exp4j.expression;

import java.util.List;
import java.util.Map;

import net.objecthunter.exp4j.tokens.Token;

public abstract class Expression<T> {
	protected final String expression;
	protected final List<Token> tokens;

	public Expression(String expression, List<Token> tokens) {
		super();
		this.expression = expression;
		this.tokens = tokens;
	}

	public abstract T calculate(Map<String,T> variables);
}
