package net.objecthunter.exp4j.shuntingyard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import net.objecthunter.exp4j.exception.UnparseableExpressionException;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;
import net.objecthunter.exp4j.tokenizer.FastTokenizer;
import net.objecthunter.exp4j.tokens.OperatorToken;
import net.objecthunter.exp4j.tokens.Token;

public class ShuntingYard {
	private final Map<String, Operator> customOperators;

	public ShuntingYard(final Map<String, Operator> customOperators) {
		super();
		this.customOperators = customOperators;
	}

	public List<Token> transformRpn(final String expression) throws UnparseableExpressionException {
		final FastTokenizer tokenizer = new FastTokenizer(expression);
		final List<Token> output = new ArrayList<>();
		final Stack<Token> stack = new Stack<>();
		
		int lastType = 0;
		tokenizer.nextToken();
		while (!tokenizer.isEOF()) {
			switch (tokenizer.getType()) {
			case Token.NUMBER:
				output.add(tokenizer.getTokenValue());
				break;
			case Token.FUNCTION:
				stack.push(tokenizer.getTokenValue());
				break;
			case Token.ARGUMENT_SEPARATOR:
				while (stack.peek().getType() != Token.PARANTHESES_LEFT) {
					output.add(stack.pop());
				}
				break;
			case Token.OPERATOR:
			case Token.UNARY_OPERATOR:
				final Operator o1 = ((OperatorToken) tokenizer.getTokenValue()).getOperator();
				while (!stack.isEmpty() && stack.peek().getType() == Token.OPERATOR) {
					final Operator o2 = ((OperatorToken) stack.peek()).getOperator();
					if ((o1.isLeftAssociative() && o1.getPrecedence() == o2.getPrecedence()) ||
							o1.getPrecedence() < o2.getPrecedence()) {
						output.add(stack.pop());
					}
				}
				stack.push(new OperatorToken(o1));
				break;
			case Token.PARANTHESES_LEFT:
				stack.push(tokenizer.getTokenValue());
				break;
			case Token.PARANTHESES_RIGHT:
				while (stack.peek().getType() != Token.PARANTHESES_LEFT){
					output.add(stack.pop());
				}
				stack.pop();
				if (!stack.isEmpty() && stack.peek().getType() == Token.FUNCTION) {
					output.add(stack.pop());
				}
				break;
				default:
					throw new UnparseableExpressionException("Unknown token type " + tokenizer.getType());
			}
			lastType = tokenizer.getType();
			tokenizer.nextToken();
		}
		while (!stack.isEmpty()) {
			output.add(stack.pop());
		}
		return output;
	}

	private Operator getOperator(String symbol, boolean unary) {
		Operator op = null;
		if (symbol.length() == 1) {
			op = Operators.getBuiltinOperator(symbol.charAt(0), unary ? 1 : 2);
		}
		if (op == null) {
			op = customOperators.get(symbol);
		}
		return op;
	}

	private boolean isUnary(int lastType) {
		return lastType == 0 || lastType == Token.PARANTHESES_LEFT || lastType == Token.ARGUMENT_SEPARATOR || lastType == Token.OPERATOR;
	}

}
