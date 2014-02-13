package net.objecthunter.exp4j.expression;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokens.FunctionToken;
import net.objecthunter.exp4j.tokens.NumberToken;
import net.objecthunter.exp4j.tokens.OperatorToken;
import net.objecthunter.exp4j.tokens.Token;
import net.objecthunter.exp4j.tokens.VariableToken;

public class BigDecimalExpression extends Expression<BigDecimal> {
	private final Map<String, BigDecimal> variables = new HashMap<>();

	public BigDecimalExpression(String expression, List<Token> tokens) {
		super(expression, tokens);
	}

	@Override
	public Expression setVariable(String name, BigDecimal value) {
		this.variables.put(name, value);
		return this;
	}
	
	@Override
	public Expression setVariables(Map<String, BigDecimal> variables) {
		this.variables.putAll(variables);
		return this;
	}
	
	@Override
	public BigDecimal evaluate() {
		return evaluate(null);
	}

	@Override
	public BigDecimal evaluate(Map<String, BigDecimal> variables) {
		if (variables != null) {
			setVariables(variables);
		}
		final Stack<BigDecimal> stack = new Stack<>();
		for (Token t : tokens) {
			switch (t.getType()) {
			case Token.NUMBER:
				stack.push((BigDecimal) ((NumberToken) t).getValue());
				break;
			case Token.VARIABLE:
				stack.push(this.variables.get(((VariableToken)t).getName()));
				break;
			case Token.OPERATOR:
				Operator<BigDecimal> op = ((OperatorToken) t).getOperator();
				if (stack.size() < op.getArgumentCount()) {
					throw new IllegalArgumentException(
							"Not enough operands for operator "
									+ op.getSymbol());
				}
				BigDecimal[] operands = new BigDecimal[op.getArgumentCount()];
				for (int i = 0; i < op.getArgumentCount(); i++) {
					operands[i] = stack.pop();
				}
				stack.push(op.apply(operands));
				break;
			case Token.FUNCTION:
				final Function<BigDecimal> func = ((FunctionToken) t).getFunction();
				BigDecimal[] args;
				if (func.getArgumentCount() > 0) {
					if (stack.size() < func.getArgumentCount()) {
						throw new IllegalArgumentException(
								"Not enough operands for function "
										+ func.getName());
					}
					args = new BigDecimal[func.getArgumentCount()];
					for (int i = 0; i < func.getArgumentCount(); i++) {
						args[i] = stack.pop();
					}
				} else {
					args = new BigDecimal[stack.size()];
					int count = 0;
					while (!stack.isEmpty()) {
						args[count++] = stack.pop();
					}
				}
				stack.push(func.apply(args));
				break;
			}
		}
		if (stack.size() != 1) {
			throw new IllegalArgumentException(
					"The user has input too many values");
		}
		return stack.pop();
	}
}
