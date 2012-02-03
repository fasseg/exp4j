package de.congrace.exp4j;

public abstract class CustomOperator {

    final boolean leftAssociative;

    final char symbol;

    final int precedence;

    final int operandCount;

    CustomOperator(final char symbol, final boolean leftAssociative, final int precedence) {
        super();
        this.leftAssociative = leftAssociative;
        this.symbol = symbol;
        this.precedence = precedence;
        this.operandCount = 2;
    }

    CustomOperator(final char symbol, final boolean leftAssociative, final int precedence, final int operandCount) {
        super();
        this.leftAssociative = leftAssociative;
        this.symbol = symbol;
        this.precedence = precedence;
        this.operandCount = operandCount;
    }

    CustomOperator(final char symbol) {
        super();
        this.leftAssociative = true;
        this.symbol = symbol;
        this.precedence = 1;
        this.operandCount = 2;
    }

    CustomOperator(final char symbol, final int precedence) {
        super();
        this.leftAssociative = true;
        this.symbol = symbol;
        this.precedence = precedence;
        this.operandCount = 2;
    }

    abstract double applyOperation(double[] values);
}
