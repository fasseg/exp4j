package net.objecthunter.exp4j.calculable;

import java.util.List;
import java.util.Map;

import net.objecthunter.exp4j.tokens.Token;

public abstract class Calculable<T> {
	protected final String expression;
	protected final List<Token> tokens;

	public Calculable(String expression, List<Token> tokens) {
		super();
		this.expression = expression;
		this.tokens = tokens;
	}

	public abstract T calculate(Map<String,T> variables);
}
