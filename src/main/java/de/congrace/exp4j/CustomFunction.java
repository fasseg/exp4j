package de.congrace.exp4j;

import java.util.Map;
import java.util.Stack;

import de.congrace.exp4j.FunctionToken.Function;

/**
 * this classed is used to create custom functions for exp4j<br/>
 * <br/>
 * <b>Example</b><br/>
 * <code><pre>{@code 
 * CustomFunction fooFunc = new CustomFunction("foo") {
 * 		public double applyFunction(double value) {
 * 			return value*Math.E;
 * 		}
 * };
 * double varX=12d;
 * Calculable calc = new ExpressionBuilder("foo(x)").withCustomFunction(fooFunc).withVariable("x",varX).build();
 * assertTrue(calc.calculate() == Math.E * varX);
 * }</pre></code>
 * 
 * @author ruckus
 * 
 */
public abstract class CustomFunction extends CalculationToken {

	/**
	 * create a new CustomFunction with a set name
	 * 
	 * @param value
	 *            the name of the function (e.g. foo)
	 */
	protected CustomFunction(String value) throws InvalidCustomFunctionException{
		super(value);
		for (Function f:Function.values()) {
			if (value.equalsIgnoreCase(f.toString())){
				throw new InvalidCustomFunctionException(value + " is already reserved as a function name");
			}
		}
	}

	/**
	 * apply the function to a value
	 * 
	 * @param value
	 *            the value the function should be applied to
	 * @return the function value
	 */
	public abstract double applyFunction(double value);

	@Override
	void mutateStackForCalculation(Stack<Double> stack, Map<String, Double> variableValues) {
		stack.push(this.applyFunction(stack.pop()));
	}

	@Override
	void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output) {
		operatorStack.push(this);
	}
}
