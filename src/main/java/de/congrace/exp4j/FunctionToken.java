package de.congrace.exp4j;

import java.util.Map;
import java.util.Stack;

class FunctionToken extends CalculationToken {

	final String functionName;

	final CustomFunction function;

	FunctionToken(String value, CustomFunction function) throws UnknownFunctionException {
		super(value);
		if (value == null) {
			throw new UnknownFunctionException(value);
		}
		try {
			this.functionName = function.name;
			this.function = function;
		} catch (IllegalArgumentException e) {
			throw new UnknownFunctionException(value);
		}
	}

	String getName() {
		return functionName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FunctionToken) {
			return functionName.equals(((FunctionToken) obj).functionName);

		}
		return false;
	}

	@Override
	public int hashCode() {
		return functionName.hashCode();
	}

	@Override
	void mutateStackForCalculation(Stack<Double> stack, Map<String, Double> variableValues) {
		double[] args = new double[function.argc];
		for (int i = 0; i < function.argc; i++) {
			args[i] = stack.pop();
		}
		stack.push(this.function.applyFunction(ArrayUtil.reverse(args)));
	}

	@Override
	void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output) {
		operatorStack.push(this);
	}
}