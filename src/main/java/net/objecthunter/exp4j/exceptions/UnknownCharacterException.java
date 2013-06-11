package net.objecthunter.exp4j.exceptions;

public class UnknownCharacterException extends UnparseableExpressionException {
	public UnknownCharacterException(Character c, int pos, String expression) {
		super("Unknown character '" + c + "' at position " + pos + " in expression '" + expression + "'");
	}
}
