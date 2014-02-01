package net.objecthunter.exp4j.expression;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExpressionBuilderTest {
	@Test
	public void testExpression1() throws Exception {
		String exp = "2+3";
		double result = new ExpressionBuilder(exp)
			.buildDouble()
			.evaluate();
		assertEquals(5d,result,0d);
	}
}
