package net.objecthunter.exp4j.calculable;

import static org.junit.Assert.*;

import net.objecthunter.exp4j.shuntingyard.ShuntingYard;

import org.junit.Test;

public class DoubleCalculableTest {

	@Test
	public void testCalculation1() throws Exception{
		String expression = "1+1";
		Calculable<Double> c = new DoubleCalculable(expression,
				new ShuntingYard(null).transformRpn(expression));
		assertEquals(2d,c.calculate(null),0d);
	}

	@Test
	public void testCalculation2() throws Exception{
		String expression = "sin(0)";
		Calculable<Double> c = new DoubleCalculable(expression,
				new ShuntingYard(null).transformRpn(expression));
		assertEquals(0d,c.calculate(null),0d);
	}
	@Test
	public void testCalculation3() throws Exception{
		String expression = "cos(sin(0))";
		Calculable<Double> c = new DoubleCalculable(expression,
				new ShuntingYard(null).transformRpn(expression));
		assertEquals(1d,c.calculate(null),0d);
	}
}
