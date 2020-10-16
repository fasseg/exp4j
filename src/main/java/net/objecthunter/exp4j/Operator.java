package net.objecthunter.exp4j;

public abstract class Operator {

    public static final short PRECEDENCE_ADDITION = 10;

    public static final short PRECEDENCE_MULTIPLICATION = 50;

    final String symbol;
    final int numOperands;
    final boolean leftAssociative;
    final int precedence;

    public Operator(final String symbol, final short precedence) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.numOperands = 2;
        this.leftAssociative = true;
    }

    public Operator(final String symbol, final short precedence, final int numOperands, final boolean leftAssociative) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.numOperands = numOperands;
        this.leftAssociative = leftAssociative;
    }

    abstract double apply(final double ... values);
}
