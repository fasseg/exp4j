package de.congrace.exp4j;

import java.util.List;
import java.util.Map;
import java.util.Stack;

abstract class RPNConverter {

	private static String substituteUnaryOperators(String expr, Map<String, CustomOperator> operators) {
		StringBuilder resultBuilder = new StringBuilder();
		int whitespaceCount = 0;
		for (int i = 0; i < expr.length(); i++) {
			boolean afterOperator = false;
			boolean afterParantheses = false;
			boolean expressionStart = false;
			final char c = expr.charAt(i);
			if (Character.isWhitespace(c)) {
				whitespaceCount++;
				resultBuilder.append(c);
				continue;
			}
			if (resultBuilder.length() == whitespaceCount){
				expressionStart = true;
			}
			// check if last char in the result is an operator
			if (resultBuilder.length() > whitespaceCount){
				if (isOperatorCharacter(resultBuilder.charAt(resultBuilder.length() - 1 - whitespaceCount), operators)){
					afterOperator = true;
				}else if (resultBuilder.charAt(resultBuilder.length() - 1 - whitespaceCount) == '('){
					afterParantheses = true;
				}
			}
			switch (c) {
			case '+':
				if (resultBuilder.length() > 0 && !afterOperator && !afterParantheses && !expressionStart) {
					// not an unary plus so append the char
					resultBuilder.append(c);
				}
				break;
			case '-':
				if (resultBuilder.length() > 0 && !afterOperator && !afterParantheses && !expressionStart) {
					// not unary 
					resultBuilder.append(c);
				}else{
					//unary so we substitute it
					resultBuilder.append('\'');
				}
				break;
			default:
				resultBuilder.append(c);
			}
			whitespaceCount = 0;
		}
		return resultBuilder.toString();
	}

	static RPNExpression toRPNExpression(String infix, Map<String, Double> variables,
			Map<String, CustomFunction> customFunctions, Map<String, CustomOperator> operators)
			throws UnknownFunctionException, UnparsableExpressionException {
		final Tokenizer tokenizer = new Tokenizer(variables.keySet(), customFunctions, operators);
		final StringBuilder output = new StringBuilder(infix.length());
		final Stack<Token> operatorStack = new Stack<Token>();
		List<Token> tokens = tokenizer.getTokens(substituteUnaryOperators(infix, operators));
		validateRPNExpression(tokens, operators);
		for (final Token token : tokens) {
			token.mutateStackForInfixTranslation(operatorStack, output);
		}
		// all tokens read, put the rest of the operations on the output;
		while (operatorStack.size() > 0) {
			output.append(operatorStack.pop().getValue()).append(" ");
		}
		String postfix = output.toString().trim();
		tokens = tokenizer.getTokens(postfix);
		return new RPNExpression(tokens, postfix, variables);
	}
	
	static FutureRPNExpression toFutureRPNExpression(String infix, Map<String, Double> variables,
			Map<String, CustomFunction> customFunctions, Map<String, CustomOperator> operators)
			throws UnknownFunctionException, UnparsableExpressionException {
		final Tokenizer tokenizer = new Tokenizer(variables.keySet(), customFunctions, operators);
		tokenizer.setStoreFutures(true);
		final StringBuilder output = new StringBuilder(infix.length());
		final Stack<Token> operatorStack = new Stack<Token>();
		List<Token> tokens = tokenizer.getTokens(substituteUnaryOperators(infix, operators));
		validateRPNExpression(tokens, operators);
		for (final Token token : tokens) {
			token.mutateStackForInfixTranslation(operatorStack, output);
		}
		// all tokens read, put the rest of the operations on the output;
		while (operatorStack.size() > 0) {
			output.append(operatorStack.pop().getValue()).append(" ");
		}
		String postfix = output.toString().trim();
		tokens = tokenizer.getTokens(postfix);
		return new FutureRPNExpression(tokens, postfix, variables);
	}

	private static void validateRPNExpression(List<Token> tokens, Map<String, CustomOperator> operators)
			throws UnparsableExpressionException {
		for (int i = 1; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			if (tokens.get(i - 1) instanceof NumberToken) {
				if (t instanceof VariableToken || (t instanceof ParenthesesToken && ((ParenthesesToken) t).isOpen())
						|| t instanceof FunctionToken) {
					throw new UnparsableExpressionException(
							"Implicit multiplication is not supported. E.g. always use '2*x' instead of '2x'");
				}
			}
		}
	}

	private static boolean isOperatorCharacter(char c, Map<String, CustomOperator> operators) {
		for (String symbol : operators.keySet()) {
			if (symbol.indexOf(c) != -1) {
				return true;
			}
		}
		return false;
	}

}
