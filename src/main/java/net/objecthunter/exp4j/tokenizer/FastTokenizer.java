package net.objecthunter.exp4j.tokenizer;

import net.objecthunter.exp4j.operator.Operators;

public class FastTokenizer {
	public static final int EOF = 1;
	public static final int ILLEGAL = 2;
	public static final int VARIABLE = 3;
	public static final int NUMBER = 4;
	public static final int FUNCTION = 5;
	public static final int OPERATOR = 6;
	public static final int PARANTHESES_OPEN = 7;
	public static final int PARANTHESES_CLOSE = 8;

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

	public FastTokenizer(final char[] data, final String[] functions, final String[] variables) {
		super();
		this.data = data;
		this.expressionLength = data.length;
		this.functions = functions;
		this.variables = variables;
		this.functionsLength = functions.length;
		this.variablesLength = variables.length;
	}

	public FastTokenizer(final String data, final String[] functions, final String[] variables) {
		this(data.toCharArray(), functions, variables);
	}

	public void nextToken() {
		final StringBuilder valBuilder = new StringBuilder();
		if (index >= expressionLength) {
			this.currentType = EOF;
			currentValue = null;
			return;
		}
		char ch = this.data[this.index++];

		// skip whitespace
		do {
			if (Character.isWhitespace(ch)) {
				if (index >= expressionLength) {
					this.currentType = EOF;
				} else {
					ch = this.data[index++];
				}
			}
		} while (Character.isWhitespace(ch));

		// try parse a number from the stream
		if (Character.isDigit(ch) || ch == '.') {
			valBuilder.append(ch);
			// read all chars into value and set the type to number
			this.currentType = NUMBER;
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
		} else if (Operators.isAllowedOperatorSymbol(ch)) {
			valBuilder.append(ch);
			currentValue = valBuilder.toString();
			this.currentType = OPERATOR;
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
				this.currentType = FUNCTION;
			} else if (isVariable(currentValue)) {
				this.currentType = VARIABLE;
			}

		} else if (ch == '(' || ch == '[' || ch == '{'){
			this.currentType= PARANTHESES_OPEN;
			this.currentValue="(";
		} else if (ch == ')' || ch == ']' || ch == '}'){
			this.currentType= PARANTHESES_CLOSE;
			this.currentValue=")";
		}
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

	public String getTokenValue() {
		return this.currentValue;
	}

	public boolean isEOF() {
		return this.currentType == EOF;
	}
}
