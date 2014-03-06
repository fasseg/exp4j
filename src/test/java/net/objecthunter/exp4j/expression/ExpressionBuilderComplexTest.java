package net.objecthunter.exp4j.expression;

import static org.junit.Assert.assertEquals;
import static java.lang.Math.*;

import java.math.BigDecimal;
import java.math.MathContext;

import net.objecthunter.exp4j.complex.ComplexNumber;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;

import org.junit.Test;

public class ExpressionBuilderComplexTest {
	@Test
	public void testExpression1() throws Exception {
		String exp = "2+3i";
		ComplexNumber result = new ExpressionBuilder(exp)
				.buildComplex()
				.evaluate();
		assertEquals(new ComplexNumber(2d, 3d), result);
	}

	@Test
	public void testExpression2() throws Exception {
		String exp = "2+3i-4i";
		ComplexNumber result = new ExpressionBuilder(exp)
				.buildComplex()
				.evaluate();
		assertEquals(new ComplexNumber(2d, -1d), result);
	}

	@Test
	public void testExpression3() throws Exception {
		String exp = "2*(1+2i)";
		ComplexNumber result = new ExpressionBuilder(exp)
				.buildComplex()
				.evaluate();
		assertEquals(new ComplexNumber(2d, 4d), result);
	}

	@Test
	public void testExpression4() throws Exception {
		String exp = "(1-2i)*-(3+4i)";
		ComplexNumber result = new ExpressionBuilder(exp)
				.buildComplex()
				.evaluate();
		assertEquals(new ComplexNumber(-11d, 2d), result);
	}

	@Test
	public void testExpression5() throws Exception {
		String exp = "log((1-2i))";
		ComplexNumber result = new ExpressionBuilder(exp)
				.buildComplex()
				.evaluate();
		ComplexNumber should = new ComplexNumber(
				0.8047189562170503d,
				-1.1071487177940904d);
		assertEquals(should, result);
	}

	@Test
	public void testExpression6() throws Exception {
		String exp = "(1-2i)^4";
		ComplexNumber result = new ExpressionBuilder(exp)
				.buildComplex()
				.evaluate();
		ComplexNumber should = new ComplexNumber(
				-7d,
				24d);
		assertEquals(should.getReal(), result.getReal(), 1 / 100000000000000d);
		assertEquals(should.getImaginary(), result.getImaginary(),
				1 / 100000000000000d);
	}

	@Test
	public void testExpression7() throws Exception {
		String exp = "i^2";
		ComplexNumber result = new ExpressionBuilder(exp)
				.buildComplex()
				.evaluate();
		ComplexNumber should = new ComplexNumber(-1d, 0d);
		assertEquals(should.getReal(), result.getReal(), 1 / 100000000000000d);
		assertEquals(should.getImaginary(), result.getImaginary(),
				1 / 100000000000000d);
	}

	@Test
	public void testExpression8() throws Exception {
		String exp = "(1-2i)^3";
		ComplexNumber result = new ExpressionBuilder(exp)
				.buildComplex()
				.evaluate();
		ComplexNumber should = new ComplexNumber(-11d, 2d);
		assertEquals(should.getReal(), result.getReal(), 1 / 100000000000000d);
		assertEquals(should.getImaginary(), result.getImaginary(),
				1 / 100000000000000d);
	}
	@Test
	public void testExpression9() throws Exception {
		String exp = "i^7";
		ComplexNumber result = new ExpressionBuilder(exp)
				.buildComplex()
				.evaluate();
		ComplexNumber should = new ComplexNumber(0d, -1d);
		assertEquals(should.getReal(), result.getReal(), 1 / 100000000000000d);
		assertEquals(should.getImaginary(), result.getImaginary(),
				1 / 100000000000000d);
	}
}
