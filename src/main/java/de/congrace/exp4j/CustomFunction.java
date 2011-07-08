package de.congrace.exp4j;

import java.util.Map;
import java.util.Stack;

/**
 * this classed is used to create custom functions for exp4j<br/><br/>
 * <b>Example</b><br/>
 * <code>{@code 
 * CustomFunction fooFunc = new CustomFunction("foo") {
 * 		public double applyFunction(double value) {
 * 			return value*Math.E;
 * 		}
 * };
 * double varX=12d;
 * Calculable calc = new ExpressionBuilder("foo(x)").withCustomFunction(fooFunc).withVariable("x",varX).build();
 * assertTrue(calc.calculate() == Math.E * varX);
 * }</code>
 * 
 * @author ruckus
 * 
 */
public abstract class CustomFunction extends CalculationToken {

	/**
	 * create a new Customfunction with a set name
	 * @param value the name of the function (e.g. foo)
	 */
	protected CustomFunction(String value) {
		super(value);
	}

	/**
	 * apply the function to a value
	 * @param value the value the function should be applied to
	 * @return the function value
	 */
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
