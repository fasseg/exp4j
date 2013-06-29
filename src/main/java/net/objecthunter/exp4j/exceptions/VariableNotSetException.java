package net.objecthunter.exp4j.exceptions;

/**
 * User: fasseg
 */
public class VariableNotSetException extends CalculationException {
    public VariableNotSetException(String variable) {
        super("The variable '" + variable + " has no value assigned.");
    }
}
