package net.objecthunter.exp4j.tokens;

import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokenizer.Tokenizer;

public class OperatorToken extends Token {
	private final Operator operator;

	public OperatorToken(final Operator operator) {
		super(OPERATOR);
		this.operator = operator;
	}

	public Operator getOperator() {
		return operator;
	}

}
