package net.objecthunter.exp4j.complex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ComplexNumberMathTest {
	@Test
	public void testComplexAddition() throws Exception {
		ComplexNumber z1 = new ComplexNumber(1, 1);
		ComplexNumber z2 = new ComplexNumber(1, 1);
		ComplexNumber z3 = ComplexNumberMath.add(z1, z2);
		assertEquals(2, z3.getReal(), 0d);
		assertEquals(2, z3.getImaginary(), 0d);
	}

	@Test
	public void testComplexSubtraction() throws Exception {
		ComplexNumber z1 = new ComplexNumber(1, 1);
		ComplexNumber z2 = new ComplexNumber(1, 1);
		ComplexNumber z3 = ComplexNumberMath.subtract(z1, z2);
		assertEquals(0d, z3.getReal(), 0d);
		assertEquals(0d, z3.getImaginary(), 0d);
	}

	@Test
	public void testComplexMultiplication() throws Exception {
		ComplexNumber z1 = new ComplexNumber(1, 1);
		ComplexNumber z2 = new ComplexNumber(1, 1);
		ComplexNumber z3 = ComplexNumberMath.multiply(z1, z2);
		assertEquals(0, z3.getReal(), 0d);
		assertEquals(2, z3.getImaginary(), 0d);
	}

	@Test
	public void testComplexDivision() throws Exception {
		ComplexNumber z1 = new ComplexNumber(1, 1);
		ComplexNumber z2 = new ComplexNumber(1, 1);
		ComplexNumber z3 = ComplexNumberMath.divide(z1, z2);
		assertEquals(1, z3.getReal(), 0d);
		assertEquals(0, z3.getImaginary(), 0d);
	}
	
	@Test
	public void testComplexScale() throws Exception {
		ComplexNumber z1 = new ComplexNumber(2, 3);
		ComplexNumber z2 = ComplexNumberMath.scale(z1,3);
		assertEquals(new ComplexNumber(6, 9), z2);
	}
	
	@Test
	public void testComplexPower() throws Exception {
		ComplexNumber z1 = new ComplexNumber(1, 1);
		ComplexNumber z2 = new ComplexNumber(1, 1);
		ComplexNumber z3 = ComplexNumberMath.power(z1, z2);
		ComplexNumber should = new ComplexNumber(
				0.2739572538301211d,
				0.5837007587586147d);
		assertEquals(should, z3);
		z1 = new ComplexNumber(-1, -1);
		z2 = new ComplexNumber(-1, -1);
		z3 = ComplexNumberMath.power(z1, z2);
		should = new ComplexNumber(
				-0.02847505893221185d,
				0.06066973322317954d);
		assertEquals(should, z3);
		z1 = new ComplexNumber(2, 3);
		z2 = new ComplexNumber(4, 5);
		z3 = ComplexNumberMath.power(z1, z2);
		should = new ComplexNumber(
				-0.7530458367485596d,
				-0.9864287886477449d);
		assertEquals(should, z3);
		z3 = ComplexNumberMath.power(z1, new ComplexNumber(3d, 0d));
		assertEquals(-46d,z3.getReal(),1/100000000000000d);
		assertEquals(9d,z3.getImaginary(),1/100000000000000d);
	}

	@Test
	public void testComplexArg() throws Exception {
		double arg = ComplexNumberMath.arg(new ComplexNumber(0, 1));
		assertEquals(Math.PI / 2d, arg, 0d);
		arg = ComplexNumberMath.arg(new ComplexNumber(0d, 0d));
		assertEquals(0, arg, 0);
		arg = ComplexNumberMath.arg(new ComplexNumber(1d, 0d));
		assertEquals(0, arg, 0);
		arg = ComplexNumberMath.arg(new ComplexNumber(-1d, 0d));
		assertEquals(Math.PI, arg, 0d);
		arg = ComplexNumberMath.arg(new ComplexNumber(0d, -1d));
		assertEquals(-Math.PI / 2d, arg, 0d);
	}

	@Test
	public void testComplexMod() throws Exception {
		double arg = ComplexNumberMath.mod(new ComplexNumber(1, 0));
		assertEquals(1d, arg, 0);
		arg = ComplexNumberMath.mod(new ComplexNumber(0, 1));
		assertEquals(1d, arg, 0);
		arg = ComplexNumberMath.mod(new ComplexNumber(0d, 0d));
		assertEquals(0, arg, 0);
		arg = ComplexNumberMath.mod(new ComplexNumber(1, 1));
		assertEquals(Math.sqrt(2d), arg, 0);
		arg = ComplexNumberMath.mod(new ComplexNumber(-1, -1));
		assertEquals(Math.sqrt(2d), arg, 0);
		arg = ComplexNumberMath.mod(new ComplexNumber(Math.sqrt(2d), Math
				.sqrt(2d)));
		assertEquals(2, arg, 0);
	}
	@Test
	public void testComplexSin() throws Exception {
		ComplexNumber result = ComplexNumberMath.sin(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.sin(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.sin(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(-11.548739357257748d,result.getImaginary(),0d);
		assertEquals(0d,result.getReal(),1/100000000000000d);
	}
	@Test
	public void testComplexCos() throws Exception {
		ComplexNumber result = ComplexNumberMath.cos(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.cos(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.cos(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(-11.591953275521519d,result.getReal(),0d);
		assertEquals(0d,result.getImaginary(),1/100000000000000d);
	}
	@Test
	public void testComplexTan() throws Exception {
		ComplexNumber result = ComplexNumberMath.tan(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.tan(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.tan(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(0.99627207622075d,result.getImaginary(),0d);
		assertEquals(0d,result.getReal(),1/100000000000000d);
	}
	@Test
	public void testComplexArcSin() throws Exception {
		ComplexNumber result = ComplexNumberMath.asin(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.asin(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.asin(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(new ComplexNumber(
				0.7727397791274837d,
				2.1846910408275138d), result);
	}
	@Test
	public void testComplexArcCos() throws Exception {
		ComplexNumber result = ComplexNumberMath.acos(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.acos(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.acos(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(new ComplexNumber(
				0.798056547667413d,
				-2.1846910408275138d), result);
	}
	@Test
	public void testComplexArcTan() throws Exception {
		ComplexNumber result = ComplexNumberMath.atan(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.atan(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.atan(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(new ComplexNumber(
				1.4090382850237617d,
				0.1563886887812962d), result);
	}
	@Test
	public void testComplexLog() throws Exception {
		ComplexNumber result = ComplexNumberMath.log(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.log(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.log(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(new ComplexNumber(
				1.49130347612937282d,
				0.78539816339744830d), result);
	}
	@Test
	public void testComplexLog1p() throws Exception {
		ComplexNumber result = ComplexNumberMath.log1p(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.log1p(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.log1p(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(new ComplexNumber(
				1.6483329668452165d,
				0.6489487808147751d), result);
	}

	@Test
	public void testComplexSqrt() throws Exception {
		ComplexNumber result = ComplexNumberMath.sqrt(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.sqrt(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.sqrt(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(new ComplexNumber(
				1.9473668878447326d,
				0.8066257758615741d), result);
	}
	@Test
	public void testComplexCbrt() throws Exception {
		ComplexNumber result = ComplexNumberMath.cbrt(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.cbrt(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.cbrt(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(new ComplexNumber(
				1.5879326127240887d,
				0.4254852612144623d), result);
	}
	@Test
	public void testComplexCeil() throws Exception {
		ComplexNumber result = ComplexNumberMath.ceil(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.ceil(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.ceil(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(new ComplexNumber(4d,4d), result);
	}
	@Test
	public void testComplexFloor() throws Exception {
		ComplexNumber result = ComplexNumberMath.floor(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.floor(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.floor(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(new ComplexNumber(3d,3d), result);
	}
	@Test
	public void testComplexSinh() throws Exception {
		ComplexNumber result = ComplexNumberMath.sinh(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.sinh(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.sinh(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(-11.548739357257748d,result.getReal(),0d);
		assertEquals(0d,result.getImaginary(),1/100000000000000d);
	}
	@Test
	public void testComplexCosh() throws Exception {
		ComplexNumber result = ComplexNumberMath.cosh(new ComplexNumber(Math.PI, 0d));
		assertTrue(result.isReal());
		assertFalse(result.isImaginary());
		assertEquals(Math.cosh(Math.PI),result.getReal(),0d);
		result = ComplexNumberMath.cosh(new ComplexNumber(Math.PI, Math.PI));
		assertFalse(result.isReal());
		assertEquals(-11.591953275521519d,result.getReal(),0d);
		assertEquals(0d,result.getImaginary(),1/100000000000000d);
	}
}
