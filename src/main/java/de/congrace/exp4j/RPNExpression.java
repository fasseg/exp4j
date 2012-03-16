package de.congrace.exp4j;

import java.util.List;
import java.util.Map;
import java.util.Stack;

class RPNExpression implements Calculable {
	final List<Token> tokens;

	final String expression;

	final Map<String, Double> variables;

	public RPNExpression(List<Token> tokens, String expression, final Map<String, Double> variables) {
		super();
		this.tokens = tokens;
		this.expression = expression;
		this.variables = variables;
	}

	/**
	 * calculate the result of the expression and substitute the variables by their values beforehand
	 * 
	 * @param values
	 *            the variable values to be substituted
	 * @return the result of the calculation
	 * @throws IllegalArgumentException
	 *             if the variables are invalid
	 */
	public double calculate(double... values) throws IllegalArgumentException {
		if (variables.size() == 0 && values != null) {
			throw new IllegalArgumentException("there are no variables to set values");
		} else if (values != null && values.length != variables.size()) {
			throw new IllegalArgumentException("The are an unequal number of variables and arguments");
		}
		int i = 0;
		if (variables.size() > 0 && values != null) {
			for (Map.Entry<String, Double> entry : variables.entrySet()) {
				entry.setValue(values[i++]);
			}
		}
		final Stack<Double> stack = new Stack<Double>();
		for (final Token t : tokens) {
			((CalculationToken) t).mutateStackForCalculation(stack, variables);
		}
		return stack.pop();
	}

	public String getExpression() {
		return expression;
	}

	public void setVariable(String name, double value) {
		this.variables.put(name, value);
	}

	public double calculate() {
		return calculate(null);
	}
}
