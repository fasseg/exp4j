package net.objecthunter.exp4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import net.objecthunter.exp4j.calculable.Calculable;
import net.objecthunter.exp4j.exceptions.UnparseableExpressionException;
import net.objecthunter.exp4j.function.CustomFunction;
import net.objecthunter.exp4j.operator.CustomOperator;
import net.objecthunter.exp4j.operator.Operators;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes"})
public class ExpressionBuilderComplexNumberTest {

	@Test
	public void testComplexNumberExpression1() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("2+2", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(4.0d, 0), result);
	}

	@Test
	public void testComplexNumberExpression2() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("2+2i", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(2.0d, 2.0d), result);
	}

	@Test
	public void testComplexNumberExpression3() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("(2.11-5.522i)", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(2.11d, -5.522d), result);
	}

	@Test
	public void testComplexNumberExpression4() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("1+1i * 1-1i", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(2d, 0d), result);
	}

	@Test
	public void testComplexNumberExpression5() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("1+1i * 1+1i", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(0d, 2d), result);
	}

	@Test
	public void testComplexNumberExpression6() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("1+2i * 3+4i", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(-5d, 10d), result);
	}

	@Test
	public void testComplexNumberExpression7() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("-1 +2i", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(-1d, 2d), result);
	}

	@Test
	public void testComplexNumberExpression8() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("1+2i / 3+4i", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(0.44d, 0.08d), result);
	}
	@Test
	public void testComplexNumberExpression9() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("1+2i ^ 3+4i", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(0.12900959407446697d, 0.03392409290517014d), result);
	}
	@Test
	public void testComplexNumberExpression10() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("sin(0+2i)", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(0.d, 3.626860407847019d), result);
	}
	@Test
	public void testComplexNumberExpression11() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("sin(1+0i)", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(Math.sin(1d), 0d), result);
	}
	@Test
	public void testComplexNumberExpression12() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("sin(-1+0i)", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(Math.sin(-1d), 0d), result);
	}
	@Test
	public void testComplexNumberExpression13() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("sin(0-1i)", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(0d, -1.175201193643801456d), result);
	}
	@Test
	public void testComplexNumberExpression14() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("cos(2+3i)", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(-4.18962569096880723013d, 9.10922789375533659797d), result);
	}
	@Test
	public void testComplexNumberExpression15() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("tan(2+3i)", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(-0.003764025641504248d, 1.00323862735361d), result);
	}
	@Test
	public void testComplexNumberExpression16() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("sinh(2+3i)", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(-3.59056458998577995201d, 0.5309210862485197d), result);
	}
	@Test
	public void testComplexNumberExpression17() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("cosh(2+3i)", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(-3.72454550491532256d, 0.5118225699873846088d), result);
	}
	@Test
	public void testComplexNumberExpression18() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("tanh(2+3i)", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(0.9653858790221331d, -0.00988437503832251d), result);
	}
	@Test
	public void testComplexNumberExpression19() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("sqrt(2+3i)", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(1.6741492280355401d, 0.895977476129838d), result);
	}

}
