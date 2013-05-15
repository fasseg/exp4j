package net.objecthunter.exp4j.operator;

public abstract class CustomOperator {
	private final int argc;
	private final boolean leftAssociative;
	private final String symbol;
	private final int precedence;

	public CustomOperator(String symbol, int precedence, int argc, boolean leftAssociative) {
		super();
		this.argc = argc;
		this.leftAssociative = leftAssociative;
		this.symbol = symbol;
		this.precedence = precedence;
	}

	public CustomOperator(String symbol, int precedence) {
		super();
		this.argc = 2;
		this.leftAssociative = false;
		this.symbol = symbol;
		this.precedence = precedence;
	}

	public CustomOperator(String symbol) {
		super();
		this.argc = 2;
		this.leftAssociative = false;
		this.symbol = symbol;
		this.precedence = Operators.PRECEDENCE_ADDITION;
	}

	public int getArgumentCount() {
		return argc;
	}

	public int getPrecedence() {
		return precedence;
	}

	public String getSymbol() {
		return symbol;
	}

	public boolean isLeftAssociative() {
		return leftAssociative;
	}

	public abstract Object apply(Object... args);

}
