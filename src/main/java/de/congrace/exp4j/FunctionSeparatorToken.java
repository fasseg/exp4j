package de.congrace.exp4j;

import java.util.Stack;

class FunctionSeparatorToken extends Token {

	FunctionSeparatorToken() {
		super(",");
	}

	@Override
	void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output) {
		Token token;
		while (!((token = operatorStack.peek()) instanceof ParenthesisToken) && !token.getValue().equals("(")) {
			output.append(operatorStack.pop().getValue()).append(" ");
		}
	}

}