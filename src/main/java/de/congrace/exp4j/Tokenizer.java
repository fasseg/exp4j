package de.congrace.exp4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Tokenizer {

	private final Set<String> variableNames;

	private final Map<String, CustomFunction> functions;

	private final Map<String, CustomOperator> operators;

	Tokenizer(Set<String> variableNames, Map<String, CustomFunction> functions, Map<String, CustomOperator> operators) {
		super();
		this.variableNames = variableNames;
		this.functions = functions;
		this.operators = operators;
	}

	private boolean isDigitOrDecimalSeparator(char c) {
		return Character.isDigit(c) || c == '.';
	}

	private boolean isNotationSeparator(char c) {
		return c == 'e' || c == 'E';
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
		int openBraces=0;
		int openCurly=0;
		int openSquare=0;
		// iterate over the chars and fork on different types of input
		Token lastToken=null;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == ' ')
				continue;
			if (Character.isDigit(c)) {
				final StringBuilder valueBuilder = new StringBuilder(1);
				// handle the numbers of the expression
				valueBuilder.append(c);
				int numberLen = 1;
				boolean lastCharNotationSeparator = false;
				while (chars.length > i + numberLen) {
					if (isDigitOrDecimalSeparator(chars[i + numberLen])) {
						valueBuilder.append(chars[i + numberLen]);
					}else if (isNotationSeparator(chars[i+numberLen])){
						if (lastCharNotationSeparator == true){
							throw new UnparsableExpressionException("Expression can have only one notation separator");
						}
						valueBuilder.append(chars[i + numberLen]);
						lastCharNotationSeparator = true;
					}else if (lastCharNotationSeparator && (chars[i+numberLen] == '-' || chars[i+numberLen] == '+')){
						valueBuilder.append(chars[i + numberLen]);
					}else {
						break; // break out of the while loop here, since the number seem finished
					}
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
					throw new UnparsableExpressionException(expression, c, i + 1);
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
					throw new UnparsableExpressionException(expression,  c, i + 1);
				}
			}else if (c == '('){
				openBraces++;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else if (c == '{'){
				openCurly++;
				lastToken = new ParenthesesToken(String.valueOf(c));
			}else if( c == '['){
				openSquare++;
				lastToken = new ParenthesesToken(String.valueOf(c));
			}else if ( c == ')'){
				openBraces--;
				lastToken = new ParenthesesToken(String.valueOf(c));
			}else if ( c == '}'){
				openCurly--;
				lastToken = new ParenthesesToken(String.valueOf(c));
			}else if ( c == ']'){
				openSquare--;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else {
				// an unknown symbol was encountered
				throw new UnparsableExpressionException(expression, c, i + 1);
			}
			tokens.add(lastToken);
		}
		if (openCurly != 0 || openBraces != 0 | openSquare != 0){
			StringBuilder errorBuilder=new StringBuilder();
			errorBuilder.append("There are ");
			boolean first=true;
			if (openBraces != 0) {
				errorBuilder.append(Math.abs(openBraces) + " unmatched parantheses ");
				first=false;
			}
			if (openCurly != 0) {
				if (!first){
					errorBuilder.append(" and ");
				}
				errorBuilder.append(Math.abs(openCurly) + " unmatched curly brackets ");
				first=false;
			}
			if (openSquare != 0){
				if (!first){
					errorBuilder.append(" and ");
				}
				errorBuilder.append(Math.abs(openSquare) + " unmatched square brackets ");
				first=false;
			}
			errorBuilder.append("in expression '" + expression + "'");
			throw new UnparsableExpressionException(errorBuilder.toString());
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
