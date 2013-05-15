package net.objecthunter.exp4j.tokenizer;

import net.objecthunter.exp4j.operator.CustomOperator;

public class OperatorToken extends Token {
	private final CustomOperator operator;

	public OperatorToken(final CustomOperator op) {
		super(Token.Type.OPERATOR);
		this.operator = op;
	}

	public CustomOperator getOperator() {
		return operator;
	}
}
