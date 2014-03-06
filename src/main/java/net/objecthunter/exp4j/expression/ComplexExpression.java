package net.objecthunter.exp4j.expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import net.objecthunter.exp4j.complex.ComplexNumber;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokens.FunctionToken;
import net.objecthunter.exp4j.tokens.NumberToken;
import net.objecthunter.exp4j.tokens.OperatorToken;
import net.objecthunter.exp4j.tokens.Token;
import net.objecthunter.exp4j.tokens.VariableToken;

public class ComplexExpression extends Expression<ComplexNumber> {
	final Map<String, ComplexNumber> variables = new HashMap<String, ComplexNumber>();
	
	public ComplexExpression(String expression, List<Token> tokens) {
		super(expression, tokens);
	}
	
	@Override
	public Expression setVariable(String name, ComplexNumber value) {
		variables.put(name, value);
		return this;
	}
	
	@Override
	public Expression setVariables(Map<String, ComplexNumber> variables) {
		this.variables.putAll(variables);
		return this;
	}

	@Override
	public ComplexNumber evaluate() {
		return evaluate(null);
	}

	@Override
	public ComplexNumber evaluate(Map<String, ComplexNumber> variables) {
		if (variables != null) {
			this.setVariables(variables);
		}
		final Stack<ComplexNumber> stack = new Stack<>();
		for (Token t : tokens) {
			switch (t.getType()) {
			case Token.NUMBER:
				stack.push((ComplexNumber) ((NumberToken) t).getValue());
				break;
			case Token.VARIABLE:
				stack.push(this.variables.get(((VariableToken) t).getName()));
				break;
			case Token.OPERATOR:
				Operator<ComplexNumber> op = ((OperatorToken) t).getOperator();
				if (stack.size() < op.getArgumentCount()) {
					throw new IllegalArgumentException(
							"Not enough operands for operator "
									+ op.getSymbol());
				}
				ComplexNumber[] operands = new ComplexNumber[op.getArgumentCount()];
				for (int i = op.getArgumentCount() - 1; i >= 0; i--) {
					operands[i] = stack.pop();
				}
				stack.push(op.apply(operands));
				break;
			case Token.FUNCTION:
				final Function<ComplexNumber> func = ((FunctionToken) t).getFunction();
				ComplexNumber[] args;
				if (func.getArgumentCount() > 0) {
					if (stack.size() < func.getArgumentCount()) {
						throw new IllegalArgumentException(
								"Not enough operands for function "
										+ func.getName());
					}
					args = new ComplexNumber[func.getArgumentCount()];
					for (int i = func.getArgumentCount() - 1; i >= 0; i--) {
						args[i] = stack.pop();
					}
				} else {
					args = new ComplexNumber[stack.size()];
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
