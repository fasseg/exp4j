package de.congrace.exp4j;

import java.util.Map;
import java.util.Stack;

import de.congrace.exp4j.tokens.CalculationToken;
import de.congrace.exp4j.tokens.Token;


public abstract class CustomFunction extends CalculationToken{
	
	public CustomFunction(String value) {
		super(value);
	}
	
	public abstract double applyFunction(double value);

	@Override
	public void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output) {
		operatorStack.push(this);
	}
	
	@Override
	public void mutateStackForCalculation(Stack<Double> stack, Map<String, Double> variableValues) {
		stack.push(this.applyFunction(stack.pop()));
	}
}
