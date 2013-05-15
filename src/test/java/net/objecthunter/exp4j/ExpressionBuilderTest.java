package net.objecthunter.exp4j;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class ExpressionBuilderTest {
	
	@Test
	public void testFloatExpression1(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2+2",Float.class);
		float result = e.build().calculate();
		assertTrue(4f == result);
	}

	@Test
	public void testFloatExpression2(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2-2",Float.class);
		float result = e.build().calculate();
		assertTrue(0f == result);
	}
	
	@Test
	public void testFloatExpression3(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2/2",Float.class);
		float result = e.build().calculate();
		assertTrue(1f == result);
	}
	
	@Test
	public void testFloatExpression4(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2.45-1",Float.class);
		float result = e.build().calculate();
		assertTrue(1.45f == result);
	}

	@Test
	public void testFloatExpression5(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2.45-3",Float.class);
		float result = e.build().calculate();
		float expected = 2.45f - 3f;
		assertTrue("exp4j calulated " + result, expected == result);
	}

	@Test(expected=RuntimeException.class)
	public void testFloatExpression6(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2.45(-3",Float.class);
		float result = e.build().calculate();
	}

	@Test(expected=RuntimeException.class)
	public void testFloatExpression7(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2.45)-3",Float.class);
		float result = e.build().calculate();
	}

	@Test
	public void testFloatExpression8(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("sin(1.0)",Float.class);
		float result = e.build().calculate();
		float expected = (float) Math.sin(1.0);
		assertTrue("exp4j calulated " + result, expected == result);
	}

	@Test
	public void testFloatExpression9(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("sin(1.0) * 1 + 1",Float.class);
		float result = e.build().calculate();
		float expected = (float) Math.sin(1.0) * 1 + 1;
		assertTrue("exp4j calulated " + result, expected == result);
	}

	@Test
	public void testFloatExpression10(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("-1",Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, -1f == result);
	}

	@Test
	public void testFloatExpression11(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("7+-1",Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, 6f == result);
	}

	@Test
	public void testFloatExpression12(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("7+--1",Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, 8f == result);
	}

	@Test
	public void testFloatExpression13(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("7++1",Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, 8f == result);
	}

	@Test
	public void testFloatExpression14(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("sin(-1.0)",Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, (float) Math.sin(-1.0f) == result);
	}

	@Test
	public void testExpression1(){
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("2+2",Double.class);
		System.out.println(e.build().calculate());
	}

	@Test
	public void testExpression3(){
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("2+2",BigDecimal.class);
		System.out.println(e.build().calculate());
	}

	@Test
	public void testExpression4(){
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("2+2",ComplexNumber.class);
		System.out.println(e.build().calculate());
	}
}
