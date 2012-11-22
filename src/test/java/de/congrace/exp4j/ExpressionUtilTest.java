package de.congrace.exp4j;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExpressionUtilTest {

	@Test
	public void testNormalizeNumber1() throws Exception{
		String expr="4e-6";
		String expected="4*10^-6";
		assertEquals(expected, ExpressionUtil.normalizeNumber(expr));
	}

	@Test
	public void testNormalizeNumber2() throws Exception{
		String expr="4E-0.6";
		String expected="4*10^-0.6";
		assertEquals(expected, ExpressionUtil.normalizeNumber(expr));
	}

}
