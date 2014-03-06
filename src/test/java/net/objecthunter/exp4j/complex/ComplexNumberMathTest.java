package net.objecthunter.exp4j.complex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComplexNumberMathTest {
	@Test
	public void testComplexMath1() throws Exception {
		ComplexNumber z1 = new ComplexNumber(1, 1);
		ComplexNumber z2 = new ComplexNumber(1, 1);
		ComplexNumber z3 = ComplexNumberMath.add(z1, z2);
		assertEquals(2, z3.getReal(), 0d);
		assertEquals(2, z3.getImaginary(), 0d);
	}

	@Test
	public void testComplexMath2() throws Exception {
		ComplexNumber z1 = new ComplexNumber(1, 1);
		ComplexNumber z2 = new ComplexNumber(1, 1);
		ComplexNumber z3 = ComplexNumberMath.subtract(z1, z2);
		assertEquals(0d, z3.getReal(), 0d);
		assertEquals(0d, z3.getImaginary(), 0d);
	}

	@Test
	public void testComplexMath3() throws Exception {
		ComplexNumber z1 = new ComplexNumber(1, 1);
		ComplexNumber z2 = new ComplexNumber(1, 1);
		ComplexNumber z3 = ComplexNumberMath.multiply(z1, z2);
		assertEquals(0, z3.getReal(), 0d);
		assertEquals(2, z3.getImaginary(), 0d);
	}

	@Test
	public void testComplexMath4() throws Exception {
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
		z3 = ComplexNumberMath.power(z1, 3);
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
}
