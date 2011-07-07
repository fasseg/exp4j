package de.congrace.exp4j;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ExpressionBuilderTest {
	@Test
	public void testExpressionBuilder1() throws Exception {
		Calculatable calc = new ExpressionBuilder("f(x,y)=7*x + 3*y").withVariable("x", 1).withVariable("y", 2).build();
		double result = calc.calculate();
		assertTrue(result == 13d);
	}

	@Test
	public void testExpressionBuilder2() throws Exception {
		Calculatable calc = new ExpressionBuilder("7*x + 3*y").withVariable("x", 1).withVariable("y", 2).build();
		double result = calc.calculate();
		assertTrue(result == 13d);
	}

	@Test
	public void testExpressionBuilder3() throws Exception {
		double varX = 1.3d;
		double varY = 4.22d;
		Calculatable calc = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y").withVariable("x", varX).withVariable("y", varY).build();
		double result = calc.calculate();
		assertTrue(result == 7 * varX + 3 * varY - Math.pow(Math.log(varY / varX * 12), varY));
	}

	@Test
	public void testExpressionBuilder4() throws Exception {
		double varX = 1.3d;
		double varY = 4.22d;
		Calculatable calc = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y").withVariable("x", varX).withVariable("y", varY).build();
		double result = calc.calculate();
		assertTrue(result == 7 * varX + 3 * varY - Math.pow(Math.log(varY / varX * 12), varY));
		varX = 1.79854d;
		varY = 9281.123d;
		calc.setVariable("x", varX);
		calc.setVariable("y", varY);
		result = calc.calculate();
		assertTrue(result == 7 * varX + 3 * varY - Math.pow(Math.log(varY / varX * 12), varY));
	}

	@Test
	public void testExpressionBuilder5() throws Exception {
		double varX = 1.3d;
		double varY = 4.22d;
		Calculatable calc = new ExpressionBuilder("3*y").withVariable("x", varX).withVariable("y", varY).build();
		double result = calc.calculate();
		assertTrue(result == 3 * varY);
	}
	
	@Test
	public void testExpressionBuilder6() throws Exception {
		double varX = 1.3d;
		double varY = 4.22d;
		double varZ = 4.22d;
		Calculatable calc = new ExpressionBuilder("x * y * z").withVariableNames("x","y","z").build();
		calc.setVariable("x", varX);
		calc.setVariable("y", varY);
		calc.setVariable("z", varZ);
		double result = calc.calculate();
		assertTrue(result == varX * varY * varZ);
	}
	
	@Test(expected = UnparseableExpressionException.class)
	public void testMissingVar() throws Exception {
		double varY = 4.22d;
		Calculatable calc = new ExpressionBuilder("3*y*z").withVariable("y", varY).build();
		calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testInvalidFunction() throws Exception {
		double varY = 4.22d;
		Calculatable calc = new ExpressionBuilder("3*invalid_function(y)").withVariable("y", varY).build();
		calc.calculate();
	}
}
