package de.congrace.exp4j;

public abstract class Operation {

    final boolean leftAssociative;

    final char symbol;

    final int precedence;

    final int operandCount;

    Operation(final char symbol, final boolean leftAssociative, final int precedence) {
        super();
        this.leftAssociative = leftAssociative;
        this.symbol = symbol;
        this.precedence = precedence;
        this.operandCount = 2;
    }

    Operation(final char symbol, final boolean leftAssociative, final int precedence, final int operandCount) {
        super();
        this.leftAssociative = leftAssociative;
        this.symbol = symbol;
        this.precedence = precedence;
        this.operandCount = operandCount;
    }

    Operation(final char symbol) {
        super();
        this.leftAssociative = true;
        this.symbol = symbol;
        this.precedence = 1;
        this.operandCount = 2;
    }

    Operation(final char symbol, final int precedence) {
        super();
        this.leftAssociative = true;
        this.symbol = symbol;
        this.precedence = precedence;
        this.operandCount = 2;
    }

    abstract double applyOperation(double[] values);
}
