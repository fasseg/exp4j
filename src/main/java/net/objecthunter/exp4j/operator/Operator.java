package net.objecthunter.exp4j.operator;

public abstract class Operator {

	private final int argc;
	private final boolean leftAssociative;
	private final String symbol;
	private final int precedence;

	public Operator(String symbol, int argc, boolean leftAssociative,
			int precedence) {
		super();
		this.argc = argc;
		this.leftAssociative = leftAssociative;
		this.symbol = symbol;
		this.precedence = precedence;
	}
	
	public boolean isLeftAssociative() {
		return leftAssociative;
	}
	
	public int getPrecedence() {
		return precedence;
	}
	
	public abstract double apply(double ... args);
	
	public String getSymbol() {
		return symbol;
	}
	public int getArgumentCount() {
		return argc;
	}
}
