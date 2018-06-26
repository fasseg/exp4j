package net.objecthunter.exp4j.operator;

public class Precedence {
	
	private Precedence() {}
	
	/**
     * The precedence value for the addition operation
     */
    public static final int ADDITION = 500;
    /**
     * The precedence value for the subtraction operation
     */
    public static final int SUBTRACTION = ADDITION;
    /**
     * The precedence value for the multiplication operation
     */
    public static final int MULTIPLICATION = 1000;
    /**
     * The precedence value for the division operation
     */
    public static final int DIVISION = MULTIPLICATION;
    /**
     * The precedence value for the modulo operation
     */
    public static final int MODULO = DIVISION;
    /**
     * The precedence value for the power operation
     */
    public static final int POWER = 10000;
    /**
     * The precedence value for the unary minus operation
     */
    public static final int UNARY_MINUS = 5000;
    /**
     * The precedence value for the unary plus operation
     */
    public static final int UNARY_PLUS = UNARY_MINUS;
	
}
