package net.objecthunter.exp4j.expression;

import static org.junit.Assert.assertEquals;
import static java.lang.Math.*;

import java.math.BigDecimal;
import java.math.MathContext;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;

import org.junit.Test;

public class ExpressionBuilderBigDecimalTest {
	@Test
	public void testExpression1() throws Exception {
		String exp = "2+3";
		BigDecimal result = new ExpressionBuilder(exp).buildBigDecimal()
				.evaluate();
		assertEquals(new BigDecimal(5), result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExpression2() throws Exception {
		String exp = "sin(0)";
		BigDecimal result = new ExpressionBuilder(exp).buildBigDecimal()
				.evaluate();
		assertEquals(BigDecimal.ZERO, result);
	}

	@Test
	public void testExpression3() throws Exception {
		String exp = "sum(1,1)";
		BigDecimal result = new ExpressionBuilder(exp)
				.function(new Function<BigDecimal>("sum", 2) {
					@Override
					public BigDecimal apply(BigDecimal... args) {
						return args[0].add(args[1]);
					}
				}).buildBigDecimal().evaluate();
		assertEquals(new BigDecimal(2), result);
	}

	@Test
	public void testExpression4() throws Exception {
		String exp = "sum(1,2,3,4,5,6,7,8,9,10)";
		BigDecimal result = new ExpressionBuilder(exp)
				.function(new Function<BigDecimal>("sum", -1) {
					@Override
					public BigDecimal apply(BigDecimal... args) {
						BigDecimal result = BigDecimal.ZERO;
						for (BigDecimal d : args) {
							result = result.add(d);
						}
						return result;
					}
				}).buildBigDecimal().evaluate();
		assertEquals(new BigDecimal(55), result);
	}

	@Test
	public void testExpression5() throws Exception {
		String expr = "2#2";
		Operator op = new Operator<BigDecimal>("#", 2, true,
				Operators.PRECEDENCE_ADDITION) {
			@Override
			public BigDecimal apply(BigDecimal... args) {
				return args[0].add(args[1]);
			}
		};
		BigDecimal result = new ExpressionBuilder(expr)
				.operator(op)
				.buildBigDecimal()
				.evaluate();
		assertEquals(new BigDecimal(4), result);
	}

	@Test
	public void testExpression6() throws Exception {
		String expr = "11!";
		Operator op = new Operator<BigDecimal>("!", 1, false,
				Operators.PRECEDENCE_MULTIPLICATION) {
			@Override
			public BigDecimal apply(BigDecimal... args) {
				int operand = args[0].intValue();
				if (!args[0].equals(new BigDecimal(operand))) {
					throw new IllegalArgumentException(
							"Factorial can only be calculated from an integer. maybe impleent the Gamma function");
				}
				int result = 1;
				for (int i = 2; i <= operand; i++) {
					result = result * i;
				}
				return new BigDecimal(result);
			}
		};
		BigDecimal result = new ExpressionBuilder(expr)
				.operator(op)
				.buildBigDecimal()
				.evaluate();
		assertEquals(new BigDecimal(39916800), result);
	}

	@Test
	public void testExpression7() throws Exception {
		MathContext ctx = new MathContext(128);
		BigDecimal third = BigDecimal.ONE.divide(new BigDecimal(3,ctx), ctx);
		String exp = third.toString() + "+" + third.toString();
		BigDecimal result = new ExpressionBuilder(exp)
				.buildBigDecimal()
				.evaluate();
		BigDecimal should = third.add(third, ctx);
		assertEquals(should, result);
		assertEquals(ctx.getPrecision() + 2, result.toString().length());
	}
	@Test
	public void testExpression8() throws Exception {
		MathContext ctx = new MathContext(128);
		BigDecimal third = BigDecimal.ONE.divide(new BigDecimal(3,ctx), ctx);
		String exp = "min(" + third.toString() + ", " + BigDecimal.ONE + ", " + BigDecimal.TEN + ")";
		BigDecimal result = new ExpressionBuilder(exp)
				.buildBigDecimal()
				.evaluate();
		assertEquals(third, result);
		assertEquals(ctx.getPrecision() + 2, result.toString().length());
	}
	@Test
	public void testExpression9() throws Exception {
		MathContext ctx = new MathContext(128);
		BigDecimal third = BigDecimal.ONE.divide(new BigDecimal(3,ctx), ctx);
		String exp = "max(" + third.toString() + ", " + BigDecimal.ONE + ", " + BigDecimal.TEN + ")";
		BigDecimal result = new ExpressionBuilder(exp)
				.buildBigDecimal()
				.evaluate();
		assertEquals(BigDecimal.TEN, result);
	}

	@Test
	public void testExpression10() throws Exception {
		MathContext ctx = new MathContext(128);
		BigDecimal third = BigDecimal.ONE.divide(new BigDecimal(3,ctx), ctx);
		String exp = "sin(" + third.toString() + ")";
		BigDecimal result = new ExpressionBuilder(exp)
				.buildBigDecimal()
				.evaluate();
		System.out.println(result);
		assertEquals(BigDecimal.TEN, result);
	}
}
