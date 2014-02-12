package net.objecthunter.exp4j.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.objecthunter.exp4j.expression.ExpressionBuilder;
import net.objecthunter.exp4j.function.Functions;

import org.junit.Test;

public class FunctionsTest {
	@Test
	public void testGetFunctionChars() throws Exception {
		char[] chars = Functions.getAllowedFunctionCharacters();
		assertEquals(53, chars.length);
		for (char ch : chars) {
			assertTrue(Character.isAlphabetic(ch) || ch == '_');
		}
	}

	@Test
	public void testSin() throws Exception {
		Function<Double> sin = Functions.getBuiltinFunction("sin", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(0d, sin.apply(0d), 0d);
		assertEquals(Math.sin(1d), sin.apply(1d), 0d);
		assertEquals(Math.sin(-31.4), sin.apply(-31.4), 0d);
	}

	@Test
	public void testCos() throws Exception {
		Function<Double> cos = Functions.getBuiltinFunction("cos", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(1d, cos.apply(0d), 0d);
		assertEquals(Math.cos(1d), cos.apply(1d), 0d);
		assertEquals(Math.cos(-31.4), cos.apply(-31.4), 0d);
	}

	@Test
	public void testTan() throws Exception {
		Function<Double> tan = Functions.getBuiltinFunction("tan", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.tan(0d), tan.apply(0d), 0d);
		assertEquals(Math.tan(1d), tan.apply(1d), 0d);
		assertEquals(Math.tan(-31.4), tan.apply(-31.4), 0d);
	}

	@Test
	public void testAsin() throws Exception {
		Function<Double> asin = Functions.getBuiltinFunction("asin", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.asin(0d), asin.apply(0d), 0d);
		assertEquals(Math.asin(1d), asin.apply(1d), 0d);
		assertEquals(Math.asin(-31.4), asin.apply(-31.4), 0d);
	}

	@Test
	public void testAcos() throws Exception {
		Function<Double> acos = Functions.getBuiltinFunction("acos", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.acos(0d), acos.apply(0d), 0d);
		assertEquals(Math.acos(1d), acos.apply(1d), 0d);
		assertEquals(Math.acos(-31.4), acos.apply(-31.4), 0d);
	}

	@Test
	public void testAtan() throws Exception {
		Function<Double> atan = Functions.getBuiltinFunction("atan", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.atan(0d), atan.apply(0d), 0d);
		assertEquals(Math.atan(1d), atan.apply(1d), 0d);
		assertEquals(Math.atan(-31.4), atan.apply(-31.4), 0d);
	}

	@Test
	public void testSinh() throws Exception {
		Function<Double> sinh = Functions.getBuiltinFunction("sinh", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.sinh(0d), sinh.apply(0d), 0d);
		assertEquals(Math.sinh(1d), sinh.apply(1d), 0d);
		assertEquals(Math.sinh(-31.4), sinh.apply(-31.4), 0d);
	}

	@Test
	public void testCosh() throws Exception {
		Function<Double> cosh = Functions.getBuiltinFunction("cosh", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.cosh(0d), cosh.apply(0d), 0d);
		assertEquals(Math.cosh(1d), cosh.apply(1d), 0d);
		assertEquals(Math.cosh(-31.4), cosh.apply(-31.4), 0d);
	}

	@Test
	public void testTanh() throws Exception {
		Function<Double> tanh = Functions.getBuiltinFunction("tanh", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.tanh(0d), tanh.apply(0d), 0d);
		assertEquals(Math.tanh(1d), tanh.apply(1d), 0d);
		assertEquals(Math.tanh(-31.4), tanh.apply(-31.4), 0d);
	}

	@Test
	public void testCeil() throws Exception {
		Function<Double> ceil = Functions.getBuiltinFunction("ceil", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.ceil(0d), ceil.apply(0d), 0d);
		assertEquals(Math.ceil(1d), ceil.apply(1d), 0d);
		assertEquals(Math.ceil(-31.4), ceil.apply(-31.4), 0d);
	}

	@Test
	public void testFloor() throws Exception {
		Function<Double> floor = Functions.getBuiltinFunction("floor", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.floor(0d), floor.apply(0d), 0d);
		assertEquals(Math.floor(1d), floor.apply(1d), 0d);
		assertEquals(Math.floor(-31.4), floor.apply(-31.4), 0d);
	}

	@Test
	public void testLog() throws Exception {
		Function<Double> log = Functions.getBuiltinFunction("log", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.log(0d), log.apply(0d), 0d);
		assertEquals(Math.log(1d), log.apply(1d), 0d);
		assertEquals(Math.log(-31.4), log.apply(-31.4), 0d);
	}

	@Test
	public void testLog1p() throws Exception {
		Function<Double> log1p = Functions.getBuiltinFunction("log1p", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.log1p(0d), log1p.apply(0d), 0d);
		assertEquals(Math.log1p(1d), log1p.apply(1d), 0d);
		assertEquals(Math.log1p(-31.4), log1p.apply(-31.4), 0d);
	}

	@Test
	public void testMax() throws Exception {
		Function<Double> max = Functions.getBuiltinFunction("max", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(14d, max.apply(0d,1d,14d,-9d), 0d);
	}

	@Test
	public void testMin() throws Exception {
		Function<Double> min = Functions.getBuiltinFunction("min", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(-9d, min.apply(0d,1d,14d,-9d), 0d);
	}

	@Test
	public void testSqrt() throws Exception {
		Function<Double> sqrt = Functions.getBuiltinFunction("sqrt", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.sqrt(0d), sqrt.apply(0d), 0d);
		assertEquals(Math.sqrt(1d), sqrt.apply(1d), 0d);
		assertEquals(Math.sqrt(-31.4), sqrt.apply(-31.4), 0d);
	}

	@Test
	public void testCbrt() throws Exception {
		Function<Double> cbrt = Functions.getBuiltinFunction("cbrt", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.cbrt(0d), cbrt.apply(0d), 0d);
		assertEquals(Math.cbrt(1d), cbrt.apply(1d), 0d);
		assertEquals(Math.cbrt(-31.4), cbrt.apply(-31.4), 0d);
	}

	@Test
	public void testAbs() throws Exception {
		Function<Double> abs = Functions.getBuiltinFunction("abs", ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Math.abs(0d), abs.apply(0d), 0d);
		assertEquals(Math.abs(1d), abs.apply(1d), 0d);
		assertEquals(Math.abs(-31.4), abs.apply(-31.4), 0d);
	}
}
