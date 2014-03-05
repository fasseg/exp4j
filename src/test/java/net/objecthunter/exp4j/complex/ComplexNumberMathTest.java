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
}
