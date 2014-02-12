package net.objecthunter.exp4j.calculable;

import static org.junit.Assert.*;
import net.objecthunter.exp4j.expression.Expression;
import net.objecthunter.exp4j.expression.DoubleExpression;
import net.objecthunter.exp4j.expression.ExpressionBuilder;
import net.objecthunter.exp4j.shuntingyard.ShuntingYard;
import net.objecthunter.exp4j.tokenizer.FastTokenizer;

import org.junit.Test;

public class DoubleCalculableTest {

	@Test
	public void testCalculation1() throws Exception {
		String expression = "1+1";
		Expression<Double> c = new DoubleExpression(expression,
				new ShuntingYard(null).transformRpn(expression,
						ExpressionBuilder.MODE_DOUBLE));
		assertEquals(2d, c.evaluate(null), 0d);
	}

	@Test
	public void testCalculation2() throws Exception {
		String expression = "sin(0)";
		Expression<Double> c = new DoubleExpression(expression,
				new ShuntingYard(null).transformRpn(expression,
						ExpressionBuilder.MODE_DOUBLE));
		assertEquals(0d, c.evaluate(null), 0d);
	}

	@Test
	public void testCalculation3() throws Exception {
		String expression = "cos(sin(0))";
		Expression<Double> c = new DoubleExpression(expression,
				new ShuntingYard(null).transformRpn(expression,
						ExpressionBuilder.MODE_DOUBLE));
		assertEquals(1d, c.evaluate(null), 0d);
	}
}
