package de.congrace.exp4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Tokenizer {

	private final Set<String> variableNames;

	private final Map<String, CustomFunction> functions;

	private final Map<String, CustomOperator> operators;

	Tokenizer(Set<String> variableNames, Map<String, CustomFunction> functions,
			Map<String, CustomOperator> operators) {
		super();
		this.variableNames = variableNames;
		this.functions = functions;
		this.operators = operators;
	}

	private boolean isDigitOrDecimalSeparator(char c) {
		return Character.isDigit(c) || c == '.';
	}

	private boolean isVariable(String name) {
		if (variableNames != null) {
			for (String var : variableNames) {
				if (name.equals(var)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isFunction(String name) {
		return functions.containsKey(name);
	}

	private boolean isOperatorCharacter(char c) {
		for (String symbol : operators.keySet()) {
			if (symbol.indexOf(c) != -1) {
				return true;
			}
		}
		return false;
	}

	List<Token> getTokens(final String expression) throws UnparsableExpressionException, UnknownFunctionException {
		final List<Token> tokens = new ArrayList<Token>();
		final char[] chars = expression.toCharArray();
		// iterate over the chars and fork on different types of input
		Token lastToken;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == ' ')
				continue;
			if (Character.isDigit(c)) {
				final StringBuilder valueBuilder = new StringBuilder(1);
				// handle the numbers of the expression
				valueBuilder.append(c);
				int numberLen = 1;
				while (chars.length > i + numberLen && isDigitOrDecimalSeparator(chars[i + numberLen])) {
					valueBuilder.append(chars[i + numberLen]);
					numberLen++;
				}
				i += numberLen - 1;
				lastToken = new NumberToken(valueBuilder.toString());
			} else if (Character.isLetter(c) || c == '_') {
				// can be a variable or function
				final StringBuilder nameBuilder = new StringBuilder();
				nameBuilder.append(c);
				int offset = 1;
				while (chars.length > i + offset
						&& (Character.isLetter(chars[i + offset]) || Character.isDigit(chars[i + offset]) || chars[i
								+ offset] == '_')) {
					nameBuilder.append(chars[i + offset++]);
				}
				String name = nameBuilder.toString();
				if (this.isVariable(name)) {
					// a variable
					i += offset - 1;
					lastToken = new VariableToken(name);
				} else if (this.isFunction(name)) {
					// might be a function
					i += offset - 1;
					lastToken = new FunctionToken(name, functions.get(name));
				} else {
					// an unknown symbol was encountered
					throw new UnparsableExpressionException(c, i);
				}
			} else if (c == ',') {
				// a function separator, hopefully
				lastToken = new FunctionSeparatorToken();
			} else if (isOperatorCharacter(c)) {
				// might be an operation
				StringBuilder symbolBuilder = new StringBuilder();
				symbolBuilder.append(c);
				int offset = 1;
				while (chars.length > i + offset && (isOperatorCharacter(chars[i + offset]))
						&& isOperatorStart(symbolBuilder.toString() + chars[i + offset])) {
					symbolBuilder.append(chars[i + offset]);
					offset++;
				}
				String symbol = symbolBuilder.toString();
				if (operators.containsKey(symbol)) {
					i += offset - 1;
					lastToken = new OperatorToken(symbol, operators.get(symbol));
				} else {
					throw new UnparsableExpressionException(c, i);
				}
			} else if (c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}') {
				lastToken = new ParenthesisToken(String.valueOf(c));
			} else {
				// an unknown symbol was encountered
				throw new UnparsableExpressionException(c, i);
			}
			tokens.add(lastToken);
		}
		return tokens;

	}

	private boolean isOperatorStart(String op) {
		for (String operatorName : operators.keySet()) {
			if (operatorName.startsWith(op)) {
				return true;
			}
		}
		return false;
	}

}
