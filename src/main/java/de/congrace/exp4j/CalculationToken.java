package de.congrace.exp4j;

import java.util.Map;
import java.util.Stack;

abstract class CalculationToken extends Token {

	CalculationToken(String value) {
		super(value);
	}

	abstract void mutateStackForCalculation(Stack<Double> stack, Map<String, Double> variableValues);

}