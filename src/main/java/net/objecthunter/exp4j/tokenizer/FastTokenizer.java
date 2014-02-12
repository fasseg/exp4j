package net.objecthunter.exp4j.tokenizer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.objecthunter.exp4j.exception.UnparseableExpressionException;
import net.objecthunter.exp4j.expression.ExpressionBuilder;
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

	private int index = 0;

	private String currentValue;

	private int currentType = 0;

	private final Map<String, Operator> customOperators;

	private final Map<String, Function> customFunctions;

	private final Map<String, Double> variables;

	private final StringBuilder valBuilder = new StringBuilder();

	private final int mode;

	public FastTokenizer(final char[] data, final int mode,
			final Map<String, Double> variables,
			final Map<String, Function> customFunctions,
			final Map<String, Operator> customOperators) {
		super();
		this.mode = mode;
		this.data = data;
		this.expressionLength = data.length;
		this.customFunctions = customFunctions;
		this.variables = variables;
		this.customOperators = customOperators;
	}

	public FastTokenizer(final String expression, final int mode,
			final Map<String, Double> variables,
			final Map<String, Function> customFunctions,
			final Map<String, Operator> customOperators) {
		this(expression.toCharArray(), mode, variables, customFunctions,
				customOperators);
	}

	public FastTokenizer(final String expression, final int mode) {
		this(expression, mode, null, null, null);
	}

	public FastTokenizer(final String expression, final int mode,
			final Map<String, Double> variables) {
		this(expression.toCharArray(), mode, variables, null, null);
	}

	public FastTokenizer(final String expression, final int mode,
			final Map<String, Double> variables,
			final Map<String, Function> customFunctions) {
		this(expression.toCharArray(), mode, variables, customFunctions, null);
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
			if (isOperatorPrefix(ch)) {
				valBuilder.append(ch);
				int offset = this.index;
				while (true) {
					if (offset >= expressionLength) {
						break;
					} else {
						ch = data[offset];
						final String tmp = valBuilder.toString() + ch;
						if (isOperatorPrefix(tmp)) {
							valBuilder.append(ch);
							this.index = ++offset;
						} else {
							break;
						}
					}
				}
				final String tmp = valBuilder.toString();
				if (isOperator(tmp)) {
					currentValue = valBuilder.toString();
				} else {
					throw new UnparseableExpressionException(
							"Unknown operator '" + tmp);
				}
				if (this.currentType == Token.OPERATOR || this.currentType == 0
						|| this.currentType == Token.PARANTHESES_LEFT
						|| this.currentType == Token.ARGUMENT_SEPARATOR) {
					this.currentType = Token.UNARY_OPERATOR;
				} else {
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

	private boolean isOperator(String symbol) {
		final int symbolsLen = symbol.length();
		if (symbolsLen == 1 && Operators.isBuiltinOperator(symbol.charAt(0))) {
			return true;
		}
		if (customOperators != null) {
			return customOperators.containsKey(symbol);
		}
		return false;
	}

	private boolean isOperatorPrefix(String prefix) {
		final int prefixLen = prefix.length();
		if (prefixLen == 1 && Operators.isBuiltinOperator(prefix.charAt(0))) {
			return true;
		}
		if (customOperators != null) {
			for (String symbol : customOperators.keySet()) {
				if (symbol.startsWith(prefix)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isOperatorPrefix(char prefix) {
		if (Operators.isBuiltinOperator(prefix)) {
			return true;
		}
		if (customOperators != null) {
			for (String symbol : customOperators.keySet()) {
				if (symbol.charAt(0) == prefix) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isVariable(final String name) {
		if (variables == null) {
			return false;
		}
		return variables.containsKey(name);
	}

	private boolean isFunction(final String name) {
		if (Functions.getBuiltinFunction(name, this.mode) != null) {
			return true;
		}
		if (customFunctions == null) {
			return false;
		}
		return customFunctions.containsKey(name);
	}

	public int getType() {
		return this.currentType;
	}

	public Token getTokenValue() {
		switch (this.currentType) {
			case Token.ARGUMENT_SEPARATOR :
				return new ArgumentSeparatorToken();
			case Token.FUNCTION :
				return new FunctionToken(this.getFunction(this.currentValue));
			case Token.OPERATOR :
				return new OperatorToken(this.getOperator(this.currentValue, 2,
						mode));
			case Token.UNARY_OPERATOR :
				return new OperatorToken(this.getOperator(this.currentValue, 1,
						mode));
			case Token.NUMBER :
				switch (mode) {
					case ExpressionBuilder.MODE_DOUBLE :
						return new NumberToken(
								Double.parseDouble(this.currentValue));
					case ExpressionBuilder.MODE_BIGDECIMAL :
						return new NumberToken(
								new BigDecimal(this.currentValue));
					case ExpressionBuilder.MODE_COMPLEX :
						throw new IllegalArgumentException(
								"Not yet implemented");
				}
			case Token.PARANTHESES_RIGHT :
				return new RightParanthesesToken();
			case Token.PARANTHESES_LEFT :
				return new LeftParanthesesToken();
			default :
				return null;
		}
	}

	private Operator getOperator(final String symbol, final int argc,
			final int mode) {
		Operator op = null;
		if (symbol.length() == 1) {
			op = Operators.getBuiltinOperator(symbol.charAt(0), argc, mode);
			if (op != null) {
				return op;
			}
		}
		if (customOperators == null) {
			return null;
		}
		return customOperators.get(symbol);
	}

	private Function getFunction(String name) {
		Function func = Functions.getBuiltinFunction(name, this.mode);
		if (func == null && this.customFunctions != null) {
			func = this.customFunctions.get(name);
		}
		return func;
	}

	public boolean isEOF() {
		return this.currentType == Token.EOF;
	}
}
