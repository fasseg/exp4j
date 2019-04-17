package net.objecthunter.exp4j.exception;

import net.objecthunter.exp4j.model.Symbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {
	
	private final char[] expression;
	
	private final int expressionSize;
	
	private final List<String> functions;
	
	private final List<String> operators;
	
	private final List<String> variables;
	
	public Tokenizer(final String expression, final List<String> functions, final List<String> operators, final List<String> variables) {
		this.expression = expression.toCharArray();
		this.expressionSize = expression.length();
		this.functions = functions;
		this.operators = operators;
		this.variables = variables;
	}
	
	public List<Symbol> tokenize() {
		final List<Symbol> symbols = new ArrayList<>();
		
		// iterate overall the characters in the array and decide what to do for each of them
		for (int i = 0; i < this.expressionSize;) {
			if (isDigit(i)) {
				i = this.parseNumber(symbols, i);
			} else if(isOperatorSymbol(i)) {
				i = this.parseOperator(symbols, i);
			} else {
				System.out.println("Unknown char: " + this.expression[i]);
				i++;
			}
		}
		
		return symbols;
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
	
	private int parseNumber(final List<Symbol> symbols, final int start) {
		int idx = start + 1;
		while (idx < this.expressionSize) {
			if (!isDigit(idx)) {
				break;
			}
			idx++;
		}
		symbols.add(new Symbol(Double.parseDouble(new String(Arrays.copyOfRange(this.expression, start, idx)))));
		return idx;
	}
	
	private boolean isDigit(final int idx) {
		final char ch = this.expression[idx];
		return ch > 48 && ch < 58 || ch == 56;
	}
	
	private boolean isOperatorSymbol(final int idx) {
		return (Symbol.isAllowedOperatorChar(this.expression[idx]));
	}
}
