package net.objecthunter.exp4j.function;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;

import net.objecthunter.exp4j.expression.ExpressionBuilder;
import net.objecthunter.math.BigDecimalMath;

import org.junit.Test;

public class BigDecimalFunctionsTest {
	@Test
	public void testFunction1() throws Exception {
		Function<BigDecimal> sine = Functions.getBuiltinFunction("sin",
				ExpressionBuilder.MODE_BIGDECIMAL);
		BigDecimal tmp = sine.apply(new BigDecimal(Math.PI));
		assertEquals(BigDecimal.ZERO, tmp);
		tmp = sine.apply(BigDecimal.ZERO);
		assertEquals(BigDecimal.ZERO, tmp);
		tmp = sine.apply(new BigDecimal(Math.PI));
		assertEquals(BigDecimal.ZERO, tmp);
		tmp = sine.apply(new BigDecimal(Math.PI / 2));
		assertEquals(BigDecimal.ONE, tmp);
		tmp = sine.apply(new BigDecimal(3 * Math.PI / 2));
		assertEquals(BigDecimal.ONE.negate(), tmp);
		BigDecimal oneThird = BigDecimal.ONE.divide(new BigDecimal(3,
				MathContext.DECIMAL128), MathContext.DECIMAL128);
		tmp = sine.apply(oneThird);
		System.out.println(tmp);
		assertEquals(0,
				new BigDecimal("0.32719469679615224417334408526762060")
						.compareTo(tmp));
	}

	@Test
	public void testFunction2() throws Exception {
		Function<BigDecimal> sqrt = Functions.getBuiltinFunction("sqrt",
				ExpressionBuilder.MODE_BIGDECIMAL);
		BigDecimal tmp = sqrt.apply(new BigDecimal(100));
		assertEquals(0, tmp.compareTo(BigDecimal.TEN));
		tmp = sqrt.apply(BigDecimal.ONE);
		assertEquals(0, tmp.compareTo(BigDecimal.ONE));
		tmp = sqrt.apply(BigDecimal.ZERO);
		assertEquals(0, tmp.compareTo(BigDecimal.ZERO));
		tmp = sqrt.apply(new BigDecimal(9d));
		assertEquals(0, tmp.compareTo(new BigDecimal(3d)));
		tmp = sqrt.apply(new BigDecimal(2d));
		assertEquals(0, tmp.compareTo(new BigDecimal(
				"1.414213562373095048801688724209698")));
	}

	@Test
	public void testFunction3() throws Exception {
		Function<BigDecimal> cos = Functions.getBuiltinFunction("cos",
				ExpressionBuilder.MODE_BIGDECIMAL);
		BigDecimal tmp = cos.apply(new BigDecimal(Math.PI));
		assertEquals(BigDecimal.ONE.negate(), tmp);
		tmp = cos.apply(BigDecimal.ZERO);
		assertEquals(BigDecimal.ONE, tmp);
		tmp = cos.apply(new BigDecimal(Math.PI / 2));
		assertEquals(BigDecimal.ZERO, tmp);
		tmp = cos.apply(new BigDecimal(3 * Math.PI / 2));
		assertEquals(BigDecimal.ZERO, tmp);
		BigDecimal oneThird = BigDecimal.ONE.divide(new BigDecimal(3,
				MathContext.DECIMAL128), MathContext.DECIMAL128);
		tmp = cos.apply(oneThird);
		System.out.println(tmp);
		assertEquals(0,
				new BigDecimal("0.944956946314737664388284007675880609")
						.compareTo(tmp));
	}

	@Test(expected = ArithmeticException.class)
	public void testSqrtWithNegativeArgument() throws Exception {
		Function<BigDecimal> sqrt = Functions.getBuiltinFunction("sqrt",
				ExpressionBuilder.MODE_BIGDECIMAL);
		BigDecimal tmp = sqrt.apply(new BigDecimal(-1));
	}
	@Test
	public void testBigDecimalMathSinSeries() throws Exception {
		BigDecimal oneThird = BigDecimal.ONE.divide(new BigDecimal(3,
				MathContext.DECIMAL128), MathContext.DECIMAL128);
		BigDecimal tmp = net.objecthunter.math.BigDecimalMath.sinBySeries(
				oneThird, MathContext.DECIMAL128);
		System.out.println(tmp);
		assertEquals(0,
				new BigDecimal("0.32719469679615224417334408526762060")
						.compareTo(tmp));
		BigDecimal scaledPi = BigDecimal.ONE.divide(
				new BigDecimal(3, MathContext.DECIMAL128),
				MathContext.DECIMAL128).add(
				BigDecimalMath.PI.multiply(
						net.objecthunter.math.BigDecimalMath.TWO,
						MathContext.DECIMAL128), MathContext.DECIMAL128);
		tmp = net.objecthunter.math.BigDecimalMath.sinBySeries(scaledPi,
				MathContext.DECIMAL128);
		System.out.println(tmp);
		assertEquals(0,
				new BigDecimal("0.32719469679615224417334408526762060")
						.compareTo(tmp));
	}

}
