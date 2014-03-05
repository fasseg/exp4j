package net.objecthunter.exp4j.complex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComplexNumberTest {
	@Test
	public void testComplexNumber1() throws Exception {
		ComplexNumber z = ComplexNumber.parseComplex("2+3i");
		assertEquals(2d,z.getReal(),0d);
		assertEquals(3d, z.getImaginary(),0d);
	}
	@Test
	public void testComplexNumber2() throws Exception {
		ComplexNumber z = ComplexNumber.parseComplex("3i");
		assertEquals(0d,z.getReal(),0d);
		assertEquals(3d, z.getImaginary(),0d);
	}
	@Test
	public void testComplexNumber3() throws Exception {
		ComplexNumber z = ComplexNumber.parseComplex("2-3i");
		assertEquals(2d,z.getReal(),0d);
		assertEquals(-3d, z.getImaginary(),0d);
	}
	@Test
	public void testComplexNumber4() throws Exception {
		ComplexNumber z = ComplexNumber.parseComplex("2.2223+3.14i");
		assertEquals(2.2223d,z.getReal(),0d);
		assertEquals(3.14d, z.getImaginary(),0d);
	}
	@Test
	public void testComplexNumber5() throws Exception {
		ComplexNumber z = ComplexNumber.parseComplex("2-i3");
		assertEquals(2d,z.getReal(),0d);
		assertEquals(-3d, z.getImaginary(),0d);
	}
	@Test
	public void testComplexNumber6() throws Exception {
		ComplexNumber z = ComplexNumber.parseComplex("1.23");
		assertEquals(1.23d,z.getReal(),0d);
		assertEquals(0d, z.getImaginary(),0d);
	}
	@Test
	public void testComplexNumber7() throws Exception {
		ComplexNumber z = ComplexNumber.parseComplex("1");
		assertEquals(1d,z.getReal(),0d);
		assertEquals(0d, z.getImaginary(),0d);
	}

}
