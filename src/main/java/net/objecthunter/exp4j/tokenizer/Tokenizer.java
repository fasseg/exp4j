package net.objecthunter.exp4j.tokenizer;

import net.objecthunter.exp4j.exception.Exp4jParsingException;
import net.objecthunter.exp4j.model.Function;
import net.objecthunter.exp4j.model.Functions;
import net.objecthunter.exp4j.model.Symbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tokenizer {
	
	private final char[] expression;
	
	private final int expressionSize;
	
	private final Map<String, Function> functions;
	
	private final List<String> operators;
	
	private final List<String> variables;
	
	public Tokenizer(final String expression, final Map<String, Function> functions, final List<String> operators, final List<String> variables) {
		this.expression = expression.toCharArray();
		this.expressionSize = expression.length();
		this.functions = new HashMap<>(Functions.BUILTIN_FUNCTIONS);
		if (functions != null) {
			this.functions.putAll(functions);
		}
		this.operators = operators;
		this.variables = variables == null ? Collections.EMPTY_LIST : variables;
	}
	
	public List<Symbol> tokenize() throws Exp4jParsingException {
		final List<Symbol> symbols = new ArrayList<>();
		
		// iterate overall the characters in the array and decide what to do for each of them
		for (int i = 0; i < this.expressionSize; ) {
			final char ch = this.expression[i];
			if (Character.isWhitespace(ch)) {
				i++;
			} else if (isDigit(ch)) {
				i = this.parseNumber(symbols, i);
			} else if (isOperatorSymbol(i)) {
				i = this.parseOperator(symbols, i);
			} else if (isNameCharacter(ch, true)) {
				i = this.parseFunctionOrVariable(symbols, i);
			} else if (isOpenParentheses(ch)) {
				symbols.add(new Symbol(Symbol.Type.OPEN_PARENTHESES));
				i++;
			} else if (isCloseParentheses(ch)) {
				symbols.add(new Symbol(Symbol.Type.CLOSE_PARENTHESES));
				i++;
			} else if (ch == ',') {
				symbols.add(new Symbol(Symbol.Type.SEPARATOR));
				i++;
			} else {
				throw new Exp4jParsingException(this.expression, ch, i);
			}
		}
		
		return symbols;
	}
	
	private int parseFunctionOrVariable(final List<Symbol> symbols, final int start) throws Exp4jParsingException {
		int idx = start + 1;
		while (idx < this.expressionSize) {
			final char ch = this.expression[idx];
			if (!isNameCharacter(ch, false)) {
				break;
			}
			idx++;
		}
		final String name = new String(Arrays.copyOfRange(this.expression, start, idx));
		if (this.variables.contains(name)) {
			symbols.add(new Symbol(Symbol.Type.VARIABLE, name));
		} else if (this.functions.containsKey(name)) {
			symbols.add(new Symbol(Symbol.Type.FUNCTION, name));
		} else {
			throw new Exp4jParsingException(this.expression, name, start);
		}
		return idx;
	}
	
	
	private int parseOperator(final List<Symbol> symbols, final int start) {
		int idx = start + 1;
		while (idx < this.expressionSize) {
			if (!isOperatorSymbol(idx)) {
				break;
			}
			idx++;
		}
		symbols.add(new Symbol(Symbol.Type.OPERATOR, new String(Arrays.copyOfRange(this.expression, start, idx))));
		return idx;
	}
	
	private int parseNumber(final List<Symbol> symbols, final int start) throws Exp4jParsingException {
		int idx = start + 1;
		char lastChar = 0;
		while (idx < this.expressionSize) {
			final char ch = this.expression[idx];
			if (isDigit(ch) || ch == 'E' || ch == 'e' || ((lastChar == 'E' || lastChar == 'e') && (ch == '-' || ch == '+'))) {
				lastChar = ch;
				idx++;
			} else {
				break;
			}
		}
		final char[] number = Arrays.copyOfRange(this.expression, start, idx);
		if (lastChar == 'E' || lastChar == 'e') {
			throw new Exp4jParsingException(this.expression, lastChar, idx);
		}
		symbols.add(new Symbol(Double.parseDouble(new String(number))));
		return idx;
	}
	
	private boolean isDigit(final char ch) {
		return ch > 47 && ch < 58 || ch == 56 || ch == 46;
	}
	
	private boolean isOperatorSymbol(final int idx) {
		return (Symbol.isAllowedOperatorChar(this.expression[idx]));
	}
	
	private boolean isNameCharacter(final char ch, final boolean firstChar) {
		return (ch > 64 && ch < 91) || (ch > 96 && ch < 123) || ch == '_' || (!firstChar && isDigit(ch));
	}
	
	private boolean isOpenParentheses(char ch) {
		return ch == '(' || ch == '{' || ch == '[';
	}
	
	private boolean isCloseParentheses(char ch) {
		return ch == ')' || ch == '}' || ch == ']';
	}
}
