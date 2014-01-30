package net.objecthunter.exp4j.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.objecthunter.exp4j.exception.UnparseableExpressionException;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;
import net.objecthunter.exp4j.tokens.ArgumentSeparatorToken;
import net.objecthunter.exp4j.tokens.FunctionToken;
import net.objecthunter.exp4j.tokens.NumberToken;
import net.objecthunter.exp4j.tokens.OperatorToken;
import net.objecthunter.exp4j.tokens.LeftParanthesesToken;
import net.objecthunter.exp4j.tokens.RightParanthesesToken;
import net.objecthunter.exp4j.tokens.Token;

public class FastTokenizer {

	private final char[] data;
	private final int expressionLength;
	private final int functionsLength;
	private final int variablesLength;
	private int index = 0;
	private int lastType = 0;
	private String currentValue;
	private int currentType = 0;
	private final String[] functions;
	private final String[] variables;
	private final String[] operators;
	private final StringBuilder valBuilder = new StringBuilder();

	public FastTokenizer(final char[] data, final String[] functions,
			final String[] variables, final String[] operators) {
		super();
		this.data = data;
		this.expressionLength = data.length;
		this.functions = functions;
		this.variables = variables;
		this.functionsLength = functions.length;
		this.variablesLength = variables.length;
		this.operators = operators;
	}

	public FastTokenizer(final String expression, final String[] functions,
			final String[] variables, final String[] operators) {
		this(expression.toCharArray(), functions, variables, operators);
	}

	public FastTokenizer(final String expression) {
		this(expression, new String[0], new String[0], new String[0]);
	}

	public FastTokenizer(final String expression, final String[] functions) {
		this(expression, functions, new String[0], new String[0]);
	}

	public void nextToken() throws UnparseableExpressionException {
		valBuilder.setLength(0);
		if (index >= expressionLength) {
			this.currentType = Token.EOF;
			currentValue = null;
			return;
		}
		char ch = this.data[this.index++];

		// skip whitespace
		do {
			if (Character.isWhitespace(ch)) {
				if (index >= expressionLength) {
					this.currentType = Token.EOF;
				} else {
					ch = this.data[index++];
				}
			}
		} while (Character.isWhitespace(ch));

		// try parse a number from the stream
		if (Character.isDigit(ch) || ch == '.') {
			valBuilder.append(ch);
			// read all chars into value and set the type to number
			this.currentType = Token.NUMBER;
			int offset = this.index;
			while (true) {
				if (offset >= expressionLength) {
					currentValue = valBuilder.toString();
					break;
				} else {
					ch = data[offset];
					if (Character.isDigit(ch) || ch == '.') {
						valBuilder.append(ch);
						this.index = ++offset;
					} else {
						this.currentValue = valBuilder.toString();
						break;
					}
				}
			}
		} else if (Operators.isAllowedChar(ch)) {
			if (isOperatorPrefix(ch, operators)) {
				valBuilder.append(ch);
				int offset = this.index;
				while (true) {
					if (offset >= expressionLength) {
						break;
					} else {
						ch = data[offset];
						final String tmp = valBuilder.toString() + ch;
						if (isOperatorPrefix(tmp, operators)) {
							valBuilder.append(ch);
							this.index = ++offset;
						} else {
							break;
						}
					}
				}
				final String tmp = valBuilder.toString();
				if (isOperator(tmp, operators)) {
					currentValue = valBuilder.toString();
				} else {
					throw new UnparseableExpressionException(
							"Unknown operator '"
									+ tmp);
				}
				if (this.currentType == Token.OPERATOR || this.currentType == 0 || this.currentType == Token.PARANTHESES_LEFT || this.currentType == Token.ARGUMENT_SEPARATOR) {
					this.currentType = Token.UNARY_OPERATOR;
				}else {
					this.currentType = Token.OPERATOR;
				}
			} else {
				throw new UnparseableExpressionException("Unknown operator '"
						+ valBuilder.toString());
			}
		} else if (Character.isAlphabetic(ch) || ch == '_') {
			// might be a function or a variable since ch is alphabetic
			valBuilder.append(ch);
			int offset = this.index;
			while (true) {
				if (offset >= expressionLength) {
					break;
				} else {
					ch = data[offset];
					if (Character.isAlphabetic(ch) || ch == '_') {
						valBuilder.append(ch);
						this.index = ++offset;
					} else {
						break;
					}
				}
			}
			this.currentValue = valBuilder.toString();
			if (isFunction(currentValue)) {
				this.currentType = Token.FUNCTION;
			} else if (isVariable(currentValue)) {
				this.currentType = Token.VARIABLE;
			} else {
				throw new UnparseableExpressionException("Unknown name '"
						+ valBuilder.toString());
			}

		} else if (ch == '(' || ch == '[' || ch == '{') {
			this.currentType = Token.PARANTHESES_LEFT;
			this.currentValue = "(";
		} else if (ch == ')' || ch == ']' || ch == '}') {
			this.currentType = Token.PARANTHESES_RIGHT;
			this.currentValue = ")";
		} else if (ch == ',') {
			this.currentType = Token.ARGUMENT_SEPARATOR;
			this.currentValue = ",";
		}
	}

	private boolean isOperator(String symbols, String[] operators2) {
		final int opLen = operators.length;
		final int symbolsLen = symbols.length();
		if (symbolsLen == 1 && Operators.isBuiltinOperator(symbols.charAt(0))) {
			return true;
		}
		for (int i = 0; i < opLen; i++) {
			if (operators[i].equals(symbols)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isOperatorPrefix(String prefix, String[] operators) {
		final int opLen = operators.length;
		final int prefixLen = prefix.length();
		if (prefixLen == 1 && Operators.isBuiltinOperator(prefix.charAt(0))) {
			return true;
		}
		for (int i = 0; i < opLen; i++) {
			if (operators[i].startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isOperatorPrefix(char prefix, String[] operators) {
		int len = operators.length;
		if (Operators.isBuiltinOperator(prefix)) {
			return true;
		}
		for (int i = 0; i < len; i++) {
			if (operators[i].charAt(0) == prefix) {
				return true;
			}
		}
		return false;
	}

	private boolean isVariable(final String name) {
		for (int i = 0; i < variablesLength; i++) {
			if (variables[i].equals(name)) {
				return true;
			}
		}
		return false;
	}

	private boolean isFunction(final String name) {
		if (Functions.getFunction(name) != null) {
			return true;
		}
		for (int i = 0; i < functionsLength; i++) {
			if (functions[i].equals(name)) {
				return true;
			}
		}
		return false;
	}

	public int getType() {
		return this.currentType;
	}

	public Token getTokenValue() {
		switch (this.currentType) {
		case Token.ARGUMENT_SEPARATOR:
			return new ArgumentSeparatorToken();
		case Token.FUNCTION:
			return new FunctionToken(this.getFunction(this.currentValue));
		case Token.OPERATOR:
			return new OperatorToken(this.getOperator(this.currentValue, 2));
		case Token.UNARY_OPERATOR:
			return new OperatorToken(this.getOperator(this.currentValue, 1));
		case Token.NUMBER:
			return new NumberToken(Double.parseDouble(this.currentValue));
		case Token.PARANTHESES_RIGHT:
			return new RightParanthesesToken();
		case Token.PARANTHESES_LEFT:
			return new LeftParanthesesToken();
		default:
			return null;
		}
	}

	private Operator getOperator(final String symbol, final int argc) {
		Operator op = null;
		if (symbol.length() == 1) {
			op = Operators.getBuiltinOperator(symbol.charAt(0), argc);
		}
		return op;
	}

	private Function getFunction(String name) {
		return Functions.getFunction(name);
	}

	public boolean isEOF() {
		return this.currentType == Token.EOF;
	}
}
