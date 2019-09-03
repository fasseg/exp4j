package net.objecthunter.exp4j.operator;

public class OperatorConstants {

    /**
     * The precedence value for the addition operation
     */
    static final int PRECEDENCE_ADDITION = 500;
    /**
     * The precedence value for the subtraction operation
     */
    static final int PRECEDENCE_SUBTRACTION = PRECEDENCE_ADDITION;
    /**
     * The precedence value for the multiplication operation
     */
    static final int PRECEDENCE_MULTIPLICATION = 1000;
    /**
     * The precedence value for the division operation
     */
    static final int PRECEDENCE_DIVISION = PRECEDENCE_MULTIPLICATION;
    /**
     * The precedence value for the modulo operation
     */
    static final int PRECEDENCE_MODULO = PRECEDENCE_DIVISION;
    /**
     * The precedence value for the power operation
     */
    static final int PRECEDENCE_POWER = 10000;
    /**
     * The precedence value for the unary minus operation
     */
    static final int PRECEDENCE_UNARY_MINUS = 5000;
    /**
     * The precedence value for the unary plus operation
     */
    static final int PRECEDENCE_UNARY_PLUS = PRECEDENCE_UNARY_MINUS;
    /**
     * The precendence value for the factorial operation
     */
    static final int PRECEDENCE_FACTORIAL = PRECEDENCE_POWER + 1;

    /**
     * The set of allowed operator chars
     */
    static final char[] ALLOWED_OPERATOR_CHARS = { '+', '-', '*', '/', '%', '^', '!', '#','§',
            '$', '&', ';', ':', '~', '<', '>', '|', '=', '÷', '√', '∛', '⌈', '⌊'};

    private OperatorConstants() {}

}
