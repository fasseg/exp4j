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
		assertEquals(new ComplexNumber(2d,  3d), result);
	}

}
