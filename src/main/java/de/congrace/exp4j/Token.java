package de.congrace.exp4j;

import java.util.Stack;

abstract class Token {
	private final String value;

	/**
	 * construct a new {@link Token}
	 * 
	 * @param value
	 *            the value of the {@link Token}
	 */
	Token(String value) {
		super();
		this.value = value;
	}

	/**
	 * get the value (String representation) of the token
	 * 
	 * @return the value
	 */
	String getValue() {
		return value;
	}

	abstract void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output);
}