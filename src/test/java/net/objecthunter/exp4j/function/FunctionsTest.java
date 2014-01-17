package net.objecthunter.exp4j.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
		Function sin = Functions.getFunction("sin");
		assertEquals(0d, sin.apply(0d), 0d);
		assertEquals(Math.sin(1d), sin.apply(1d), 0d);
		assertEquals(Math.sin(-31.4), sin.apply(-31.4), 0d);
	}

	@Test
	public void testCos() throws Exception {
		Function cos = Functions.getFunction("cos");
		assertEquals(1d, cos.apply(0d), 0d);
		assertEquals(Math.cos(1d), cos.apply(1d), 0d);
		assertEquals(Math.cos(-31.4), cos.apply(-31.4), 0d);
	}

	@Test
	public void testTan() throws Exception {
		Function tan = Functions.getFunction("tan");
		assertEquals(Math.tan(0d), tan.apply(0d), 0d);
		assertEquals(Math.tan(1d), tan.apply(1d), 0d);
		assertEquals(Math.tan(-31.4), tan.apply(-31.4), 0d);
	}

	@Test
	public void testAsin() throws Exception {
		Function asin = Functions.getFunction("asin");
		assertEquals(Math.asin(0d), asin.apply(0d), 0d);
		assertEquals(Math.asin(1d), asin.apply(1d), 0d);
		assertEquals(Math.asin(-31.4), asin.apply(-31.4), 0d);
	}

	@Test
	public void testAcos() throws Exception {
		Function acos = Functions.getFunction("acos");
		assertEquals(Math.acos(0d), acos.apply(0d), 0d);
		assertEquals(Math.acos(1d), acos.apply(1d), 0d);
		assertEquals(Math.acos(-31.4), acos.apply(-31.4), 0d);
	}

	@Test
	public void testAtan() throws Exception {
		Function atan = Functions.getFunction("atan");
		assertEquals(Math.atan(0d), atan.apply(0d), 0d);
		assertEquals(Math.atan(1d), atan.apply(1d), 0d);
		assertEquals(Math.atan(-31.4), atan.apply(-31.4), 0d);
	}

	@Test
	public void testSinh() throws Exception {
		Function sinh = Functions.getFunction("sinh");
		assertEquals(Math.sinh(0d), sinh.apply(0d), 0d);
		assertEquals(Math.sinh(1d), sinh.apply(1d), 0d);
		assertEquals(Math.sinh(-31.4), sinh.apply(-31.4), 0d);
	}

	@Test
	public void testCosh() throws Exception {
		Function cosh = Functions.getFunction("cosh");
		assertEquals(Math.cosh(0d), cosh.apply(0d), 0d);
		assertEquals(Math.cosh(1d), cosh.apply(1d), 0d);
		assertEquals(Math.cosh(-31.4), cosh.apply(-31.4), 0d);
	}

	@Test
	public void testTanh() throws Exception {
		Function tanh = Functions.getFunction("tanh");
		assertEquals(Math.tanh(0d), tanh.apply(0d), 0d);
		assertEquals(Math.tanh(1d), tanh.apply(1d), 0d);
		assertEquals(Math.tanh(-31.4), tanh.apply(-31.4), 0d);
	}

	@Test
	public void testCeil() throws Exception {
		Function ceil = Functions.getFunction("ceil");
		assertEquals(Math.ceil(0d), ceil.apply(0d), 0d);
		assertEquals(Math.ceil(1d), ceil.apply(1d), 0d);
		assertEquals(Math.ceil(-31.4), ceil.apply(-31.4), 0d);
	}

	@Test
	public void testFloor() throws Exception {
		Function floor = Functions.getFunction("floor");
		assertEquals(Math.floor(0d), floor.apply(0d), 0d);
		assertEquals(Math.floor(1d), floor.apply(1d), 0d);
		assertEquals(Math.floor(-31.4), floor.apply(-31.4), 0d);
	}

	@Test
	public void testLog() throws Exception {
		Function log = Functions.getFunction("log");
		assertEquals(Math.log(0d), log.apply(0d), 0d);
		assertEquals(Math.log(1d), log.apply(1d), 0d);
		assertEquals(Math.log(-31.4), log.apply(-31.4), 0d);
	}

	@Test
	public void testLog1p() throws Exception {
		Function log1p = Functions.getFunction("log1p");
		assertEquals(Math.log1p(0d), log1p.apply(0d), 0d);
		assertEquals(Math.log1p(1d), log1p.apply(1d), 0d);
		assertEquals(Math.log1p(-31.4), log1p.apply(-31.4), 0d);
	}

	@Test
	public void testMax() throws Exception {
		Function max = Functions.getFunction("max");
		assertEquals(14d, max.apply(0,1,14,-9), 0d);
	}

	@Test
	public void testMin() throws Exception {
		Function min = Functions.getFunction("min");
		assertEquals(-9d, min.apply(0,1,14,-9), 0d);
	}

	@Test
	public void testSqrt() throws Exception {
		Function sqrt = Functions.getFunction("sqrt");
		assertEquals(Math.sqrt(0d), sqrt.apply(0d), 0d);
		assertEquals(Math.sqrt(1d), sqrt.apply(1d), 0d);
		assertEquals(Math.sqrt(-31.4), sqrt.apply(-31.4), 0d);
	}

	@Test
	public void testCbrt() throws Exception {
		Function cbrt = Functions.getFunction("cbrt");
		assertEquals(Math.cbrt(0d), cbrt.apply(0d), 0d);
		assertEquals(Math.cbrt(1d), cbrt.apply(1d), 0d);
		assertEquals(Math.cbrt(-31.4), cbrt.apply(-31.4), 0d);
	}

	@Test
	public void testAbs() throws Exception {
		Function abs = Functions.getFunction("abs");
		assertEquals(Math.abs(0d), abs.apply(0d), 0d);
		assertEquals(Math.abs(1d), abs.apply(1d), 0d);
		assertEquals(Math.abs(-31.4), abs.apply(-31.4), 0d);
	}
}
