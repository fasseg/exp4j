package net.objecthunter.exp4j;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Arrays;

import net.objecthunter.exp4j.calculable.Calculable;
import net.objecthunter.exp4j.exceptions.UnparseableExpressionException;
import net.objecthunter.exp4j.function.CustomFunction;
import net.objecthunter.exp4j.operator.CustomOperator;

import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes"})
public class ExpressionBuilderBigDecimalTest {

	@Test
	public void testBigDecimalExpression1() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("2+2", BigDecimal.class);
		BigDecimal result = e.build().calculate();
		assertEquals(new BigDecimal("4") ,result);
	}

	@Test
	public void testBigDecimalExpression2() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("2-2", BigDecimal.class);
		BigDecimal result = e.build().calculate();
		assertEquals(new BigDecimal("0"), result);
	}

	@Test
	public void testBigDecimalExpression3() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("2/2", BigDecimal.class);
		BigDecimal result= e.build().calculate();
		assertEquals(new BigDecimal("1"), result);
	}

	@Test
	public void testBigDecimalExpression4() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("2.45-1", BigDecimal.class);
		BigDecimal result = e.build().calculate();
		assertEquals(new BigDecimal("2.45").subtract(BigDecimal.ONE, MathContext.UNLIMITED), result);
	}

	@Test
	public void testBigDecimalExpression5() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("2.45-3", BigDecimal.class);
		BigDecimal result = e.build().calculate();
		BigDecimal expected = new BigDecimal("2.45").subtract(new BigDecimal("3"));
		assertEquals(expected,result);
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testBigDecimalExpression6() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("2.45(-3", BigDecimal.class);
		BigDecimal result = e.build().calculate();
	}

	@Test(expected = RuntimeException.class)
	public void testBigDecimalExpression7() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("2.45)-3", BigDecimal.class);
		BigDecimal result = e.build().calculate();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testBigDecimalExpression8() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("sin(1.0)", BigDecimal.class);
		BigDecimal result = e.build().calculate();
		BigDecimal expected = new BigDecimal(String.valueOf(Math.sin(1.0)));
		assertEquals(expected, result);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testBigDecimalExpression9() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("sin(1.0) * 1 + 1", BigDecimal.class);
		BigDecimal result = e.build().calculate();
		BigDecimal expected = new BigDecimal(Math.sin(1.0) * 1 + 1);
		assertEquals(expected, result);
	}

	@Test
	public void testBigDecimalExpression10() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("-1", BigDecimal.class);
		BigDecimal result = e.build().calculate();
		assertEquals(new BigDecimal(-1), result);
	}

	@Test
	public void testBigDecimalExpression11() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("7+-1", BigDecimal.class);
		BigDecimal result = e.build().calculate();
		assertEquals( new BigDecimal(6), result);
	}

	@Test
	public void testBigDecimalExpression12() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("7+--1", BigDecimal.class);
		BigDecimal result = e.build().calculate();
		assertEquals( new BigDecimal(8), result);
	}

	@Test
	public void testBigDecimalExpression13() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("7++1", BigDecimal.class);
		BigDecimal result = e.build().calculate();
		assertEquals( new BigDecimal(8), result);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testBigDecimalExpression14() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("sin(-1.0)", BigDecimal.class);
		BigDecimal result = e.build().calculate();
		assertEquals(new BigDecimal(Math.sin(-1.0d)), result);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testBigDecimalExpression15() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("log(4) - 3 * (sqrt(3^cos(2)))", BigDecimal.class);
		BigDecimal result = e.build().calculate();
		BigDecimal expected = new BigDecimal(Math.log(4) - 3 * Math.sqrt(Math.pow(3,Math.cos(2))));
		assertEquals(expected, result);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testBigDecimalExpression16() throws Exception {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("sqrt(3^cos(2))", BigDecimal.class);
		BigDecimal result = e.build().calculate();
		BigDecimal expected = new BigDecimal(Math.sqrt(Math.pow(3, Math.cos(2))));
		assertEquals(expected, result);
	}

	/* Tests from exp4j v1 follow, these should of course pass */
	@Test
	public void testCustomFunction1() throws Exception {
		CustomFunction custom = new CustomFunction("timespi") {
			@Override
			public Object apply(Object... args) {
				return ((BigDecimal) args[0]).multiply(new BigDecimal(Math.PI));
			}
		};
		Calculable<BigDecimal> calc = new ExpressionBuilder("timespi(x)", BigDecimal.class).variable("x").function(custom).build();
		calc.setVariable("x", new BigDecimal(1));
		BigDecimal result = calc.calculate();
		assertEquals(new BigDecimal(Math.PI), result);
	}


	@Test
	public void testCustomFunction3() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public Object apply(Object... values) {
				return ((BigDecimal) values[0]).multiply(new BigDecimal(Math.E));
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public Object apply(Object... values) {
				return ((BigDecimal) values[0]).multiply(new BigDecimal(Math.PI));
			}
		};
		Calculable<BigDecimal> calc = new ExpressionBuilder("foo(bar(x))", BigDecimal.class).variable("x").function(custom1).function(custom2).build();
		calc.setVariable("x", new BigDecimal(1));
		BigDecimal result = calc.calculate();
		assertEquals(new BigDecimal(Math.PI).multiply(new BigDecimal(Math.E)), result);
	}

	@Test
	public void testCustomFunction4() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public Object apply(Object... values) {
				return ((BigDecimal) values[0]).multiply(new BigDecimal(Math.E));
			}
		};
		BigDecimal varX = new BigDecimal(32.24979131);
		Calculable<BigDecimal> calc = new ExpressionBuilder("foo(abs(x))", BigDecimal.class).variable("x").function(custom1).build();
		BigDecimal result = calc.setVariable("x", varX).calculate();
		assertEquals(new BigDecimal(Math.abs(varX.doubleValue())).multiply(new BigDecimal(Math.E)), result);
	}

	@Test
	public void testCustomFunction5() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public Object apply(Object... values) {
				return ((BigDecimal) values[0]).multiply(BigDecimal.valueOf(Math.E));
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public Object apply(Object... values) {
				return ((BigDecimal) values[0]).multiply(BigDecimal.valueOf(Math.PI));
			}
		};
		BigDecimal varX = BigDecimal.valueOf(32.24979131d);
		Calculable<BigDecimal> calc = new ExpressionBuilder("bar(foo(abs(x)))", BigDecimal.class).variable("x").function(custom1).function(custom2).build();
		calc.setVariable("x", varX);
		BigDecimal result = calc.calculate();
		assertEquals(varX.abs().multiply(BigDecimal.valueOf(Math.E).multiply(BigDecimal.valueOf(Math.PI))), result);
	}

	@Test
	public void testCustomFunction6() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public Object apply(Object... values) {
				return ((BigDecimal) values[0]).multiply(BigDecimal.valueOf(Math.E));
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public Object apply(Object... values) {
				return ((BigDecimal) values[0]).multiply(BigDecimal.valueOf(Math.PI));
			}
		};
		BigDecimal varX = new BigDecimal(32.24979131);
		Calculable<BigDecimal> calc = new ExpressionBuilder("bar(foo(abs(x)))", BigDecimal.class).variable("x").functions(Arrays.asList(custom1, custom2)).build();
		calc.setVariable("x", varX);
		BigDecimal result = calc.calculate();
		assertEquals(varX.abs().multiply(BigDecimal.valueOf(Math.E).multiply(BigDecimal.valueOf(Math.PI))), result);
	}

	@Test
	public void testCustomFunction7() throws Exception {
		CustomFunction custom1 = new CustomFunction("half") {
			@Override
			public Object apply(Object... values) {
				return ((BigDecimal) values[0]).divide(new BigDecimal(2));
			}
		};
		Calculable<BigDecimal> calc = new ExpressionBuilder("half(x)", BigDecimal.class).variable("x").function(custom1).build();
		calc.setVariable("x", new BigDecimal(1));
		assertEquals(new BigDecimal(0.5), calc.calculate());
		calc.setVariable("x", new BigDecimal(Math.E));
		assertEquals(new BigDecimal(Math.E/2d), calc.calculate());
	}

	@Test
	public void testCustomFunction10() throws Exception {
		CustomFunction custom1 = new CustomFunction("max", 2) {
			@Override
			public Object apply(Object... values) {
				return ((BigDecimal) values[0]).compareTo((BigDecimal) values[1]) > 0 ? (BigDecimal) values[0] : (BigDecimal) values[1];
			}
		};
		Calculable<BigDecimal> calc = new ExpressionBuilder("max(x,y)", BigDecimal.class).variable("x").variable("y").function(custom1).build();
		calc.setVariable("x", new BigDecimal(1));
		calc.setVariable("y", new BigDecimal(2));
		assertEquals(new BigDecimal(2), calc.calculate());
	}


	@Test
	public void testCustomFunction12() throws Exception {
		CustomFunction custom1 = new CustomFunction("max", 5) {
			@Override
			public Object apply(Object... values) {
				BigDecimal max = (BigDecimal) values[0];
				for (int i = 1; i < getArgumentCount(); i++) {
					if (((BigDecimal) values[i]).compareTo(max) > 0) {
						max = (BigDecimal) values[i];
					}
				}
				return max;
			}
		};
		Calculable<BigDecimal> calc = new ExpressionBuilder("max(1,2.43311,51.13,43,12)", BigDecimal.class).function(custom1).build();
		assertEquals(BigDecimal.valueOf(51.13d), calc.calculate());
	}

	@Test
	public void testCustomFunction13() throws Exception {
		CustomFunction custom1 = new CustomFunction("max", 3) {
			@Override
			public Object apply(Object... values) {
				BigDecimal max = (BigDecimal) values[0];
				for (int i = 1; i < getArgumentCount(); i++) {
					if (((BigDecimal) values[i]).compareTo(max) > 0) {
						max = (BigDecimal) values[i];
					}
				}
				return max;
			}
		};
		BigDecimal varX = (BigDecimal.valueOf(Math.E));
		Calculable<BigDecimal> calc = new ExpressionBuilder("max(abs(x),x/x,x)", BigDecimal.class).variable("x").function(custom1).build();
		calc.setVariable("x", varX);
		assertEquals(varX, calc.calculate());
	}

	@Test
	public void testCustomFunction14() throws Exception {
		CustomFunction custom1 = new CustomFunction("multiply", 2) {
			@Override
			public Object apply(Object... values) {
				return ((BigDecimal) values[0]).multiply((BigDecimal) values[1]);
			}
		};
		BigDecimal varX = BigDecimal.valueOf(1.0d);
		Calculable<BigDecimal> calc = new ExpressionBuilder("multiply(abs(x),x+1)", BigDecimal.class).variable("x").function(custom1).build();
		calc.setVariable("x", varX);
		BigDecimal expected = BigDecimal.valueOf(1.0d).multiply(BigDecimal.valueOf(2.0d));
		BigDecimal actual = calc.calculate();
		assertEquals(expected,actual);
	}

	@Test
	public void testCustomFunction15() throws Exception {
		CustomFunction custom1 = new CustomFunction("timesPi") {
			@Override
			public Object apply(Object... values) {
				return ((BigDecimal) values[0]).multiply(BigDecimal.valueOf(Math.PI));
			}
		};
		BigDecimal varX = BigDecimal.valueOf(3.898d);
		Calculable<BigDecimal> calc = new ExpressionBuilder("timesPi(x*x)", BigDecimal.class).variable("x").function(custom1).build();
		BigDecimal expected = varX.multiply(varX).multiply(BigDecimal.valueOf(Math.PI));
		calc.setVariable("x", varX);
		BigDecimal actual = calc.calculate();
		assertEquals(expected, actual);
	}



	@Test
	public void testCustomOperators1() throws Exception {
		CustomOperator factorial = new CustomOperator("!", 6, 1, true) {
			@Override
			public Object apply(Object... args) {
				BigDecimal tmp = BigDecimal.ONE;
				BigInteger steps = BigInteger.ONE;
				while (new BigDecimal(steps).compareTo(tmp) < 0) {
					steps = steps.add(BigInteger.ONE);
					tmp = tmp.multiply(new BigDecimal(steps));
				}
				return tmp;
			}
		};
		Calculable<BigDecimal> calc = new ExpressionBuilder("1!", BigDecimal.class).operator(factorial).build();
		assertEquals(new BigDecimal(1), calc.calculate());
		calc = new ExpressionBuilder("2!", BigDecimal.class).operator(factorial).build();
		assertEquals(new BigDecimal(2), calc.calculate());
		calc = new ExpressionBuilder("3!", BigDecimal.class).operator(factorial).build();
		assertEquals(new BigDecimal(6), calc.calculate());
		calc = new ExpressionBuilder("4!", BigDecimal.class).operator(factorial).build();
		assertEquals(new BigDecimal(24), calc.calculate());
		calc = new ExpressionBuilder("5!", BigDecimal.class).operator(factorial).build();
		assertEquals(new BigDecimal(120), calc.calculate());
		calc = new ExpressionBuilder("11!", BigDecimal.class).operator(factorial).build();
		assertEquals(new BigDecimal(39916800), calc.calculate());
	}
//
//	@Test
//	public void testCustomOperators2() throws Exception {
//		CustomOperator factorial = new CustomOperator("!", Operators.PRECEDENCE_EXPONENTATION + 100, 1, true) {
//			@Override
//			public Object apply(Object... args) {
//				BigDecimal tmp = new BigDecimal(1);
//				int steps = 1;
//				while (steps < (BigDecimal) args[0]) {
//					tmp = tmp * (++steps);
//				}
//				return tmp;
//			}
//		};
//		Calculable<BigDecimal> calc = new ExpressionBuilder("2^2!", BigDecimal.class).operator(factorial).build();
//		assertEquals((BigDecimal) new BigDecimal(4) , calc.calculate());
//		calc = new ExpressionBuilder("2!^2", BigDecimal.class).operator(factorial).build();
//		assertEquals(new BigDecimal(4) == calc.calculate());
//		calc = new ExpressionBuilder("-(3!)^-1", BigDecimal.class).operator(factorial).build();
//		BigDecimal actual = calc.calculate();
//		assertEquals((BigDecimal) Math.pow(new BigDecimal(-6), -1) == actual);
//	}
//
//	@Test
//	public void testCustomOperators3() throws Exception {
//		CustomOperator goe = new CustomOperator(">=", 4, 2, true) {
//			@Override
//			public Object apply(Object... values) {
//				if ((BigDecimal) values[0] >= (BigDecimal) values[1]) {
//					return new BigDecimal(1);
//				} else {
//					return new BigDecimal(0);
//				}
//			}
//		};
//		Calculable<BigDecimal> calc = new ExpressionBuilder("1>=2", BigDecimal.class).operator(goe).build();
//		assertEquals(new BigDecimal(0) == calc.calculate());
//		calc = new ExpressionBuilder("2>=1", BigDecimal.class).operator(goe).build();
//		assertEquals(new BigDecimal(1) == calc.calculate());
//		calc = new ExpressionBuilder("-2>=1", BigDecimal.class).operator(goe).build();
//		assertEquals(new BigDecimal(0) == calc.calculate());
//		calc = new ExpressionBuilder("-2>=-1", BigDecimal.class).operator(goe).build();
//		assertEquals(new BigDecimal(0) == calc.calculate());
//	}
//
//	@Test
//	public void testModulo1() throws Exception {
//		BigDecimal result = (BigDecimal) new ExpressionBuilder("33%(20/2)%2", BigDecimal.class).build().calculate();
//		BigDecimal expected = new BigDecimal(33) % (new BigDecimal(20) / new BigDecimal(2)) % new BigDecimal(2);
//		assertEquals("exp4j calculated " + result + " instead of " + expected, result == expected);
//	}
//
//	@Test
//	public void testModulo2() throws Exception {
//		BigDecimal result = (BigDecimal) new ExpressionBuilder("33%(20/2)", BigDecimal.class).build().calculate();
//		assertEquals("exp4j calculated " + result, result == new BigDecimal(3));
//	}
//
//	@Test
//	public void testDivision1() throws Exception {
//		BigDecimal result = (BigDecimal) new ExpressionBuilder("33/11", BigDecimal.class).build().calculate();
//		assertEquals("exp4j calculated " + result, result == new BigDecimal(3));
//	}
//
//	@Test
//	public void testDivision2() throws Exception {
//		BigDecimal result = (BigDecimal) new ExpressionBuilder("20/10/5", BigDecimal.class).build().calculate();
//		BigDecimal expected = new BigDecimal(20) / new BigDecimal(10) / new BigDecimal(5);
//		assertEquals("exp4j calculated " + result + " instead of " + expected, result == expected);
//	}
//
//	@Test
//	public void testCustomOperators4() throws Exception {
//		CustomOperator greaterEq = new CustomOperator(">=", 4, 2, true) {
//			@Override
//			public Object apply(Object... values) {
//				if ((BigDecimal) values[0] >= (BigDecimal) values[1]) {
//					return new BigDecimal(1);
//				} else {
//					return new BigDecimal(0);
//				}
//			}
//		};
//		CustomOperator greater = new CustomOperator(">", 4, 2, true) {
//			@Override
//			public Object apply(Object... values) {
//				if ((BigDecimal) values[0] > (BigDecimal) values[1]) {
//					return new BigDecimal(1);
//				} else {
//					return new BigDecimal(0);
//				}
//			}
//		};
//		CustomOperator newPlus = new CustomOperator(">=>", 4, 2, true) {
//			@Override
//			public Object apply(Object... values) {
//				return (BigDecimal) values[0] + (BigDecimal) values[1];
//			}
//		};
//		Calculable<BigDecimal> calc = new ExpressionBuilder("1>2", BigDecimal.class).operator(greater).build();
//		assertEquals(new BigDecimal(0) == calc.calculate());
//		calc = new ExpressionBuilder("2>=2", BigDecimal.class).operator(greaterEq).build();
//		assertEquals(new BigDecimal(1) == calc.calculate());
//		calc = new ExpressionBuilder("1>=>2", BigDecimal.class).operator(newPlus).build();
//		assertEquals(new BigDecimal(3) == calc.calculate());
//		calc = new ExpressionBuilder("1>=>2>2", BigDecimal.class).operator(greater).operator(newPlus).build();
//		assertEquals(new BigDecimal(1) == calc.calculate());
//		calc = new ExpressionBuilder("1>=>2>2>=1", BigDecimal.class).operator(greater).operator(newPlus).operator(greaterEq).build();
//		assertEquals(new BigDecimal(1) == calc.calculate());
//		calc = new ExpressionBuilder("1 >=> 2 > 2 >= 1", BigDecimal.class).operator(greater).operator(newPlus).operator(greaterEq).build();
//		assertEquals(new BigDecimal(1) == calc.calculate());
//		calc = new ExpressionBuilder("1 >=> 2 >= 2 > 1", BigDecimal.class).operator(greater).operator(newPlus).operator(greaterEq).build();
//		assertEquals(new BigDecimal(0) == calc.calculate());
//		calc = new ExpressionBuilder("1 >=> 2 >= 2 > 0", BigDecimal.class).operator(greater).operator(newPlus).operator(greaterEq).build();
//		assertEquals(new BigDecimal(1) == calc.calculate());
//		calc = new ExpressionBuilder("1 >=> 2 >= 2 >= 1", BigDecimal.class).operator(greater).operator(newPlus).operator(greaterEq).build();
//		assertEquals(new BigDecimal(1) == calc.calculate());
//	}
//
//	@Test(expected = RuntimeException.class)
//	public void testInvalidOperator1() throws Exception {
//		CustomOperator fail = new CustomOperator("2") {
//			@Override
//			public Object apply(Object... values) {
//				return 0;
//			}
//		};
//		new ExpressionBuilder("1", BigDecimal.class).operator(fail).build();
//	}
//
//	@Test(expected = RuntimeException.class)
//	public void testInvalidCustomFunction1() throws Exception {
//		CustomFunction func = new CustomFunction("1gd") {
//			@Override
//			public Object apply(Object... args) {
//				return 0;
//			}
//		};
//	}
//
//	@Test(expected = RuntimeException.class)
//	public void testInvalidCustomFunction2() throws Exception {
//		CustomFunction func = new CustomFunction("+1gd") {
//			@Override
//			public Object apply(Object... args) {
//				return 0;
//			}
//		};
//	}
//
//	@Test
//	public void testExpressionBuilder1() throws Exception {
//		Calculable<BigDecimal> calc = new ExpressionBuilder("7*x + 3*y", BigDecimal.class).variables("x", "y").build();
//		calc.setVariable("x", new BigDecimal(1));
//		calc.setVariable("y", new BigDecimal(2));
//		BigDecimal result = calc.calculate();
//		assertEquals(result == new BigDecimal(13));
//	}
//
//	@Test
//	public void testExpressionBuilder2() throws Exception {
//		Calculable<BigDecimal> calc = new ExpressionBuilder("7*x + 3*y", BigDecimal.class).variables("x", "y").build();
//		calc.setVariable("x", new BigDecimal(1));
//		calc.setVariable("y", new BigDecimal(2));
//		BigDecimal result = calc.calculate();
//		assertEquals(result == new BigDecimal(13));
//	}
//
//	@Test
//	public void testExpressionBuilder3() throws Exception {
//		BigDecimal varX = new BigDecimal(1.3);
//		BigDecimal varY = new BigDecimal(4.22);
//		Calculable<BigDecimal> calc = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y", BigDecimal.class).variables("x", "y").build();
//		calc.setVariable("x", varX);
//		calc.setVariable("y", varY);
//		BigDecimal result = calc.calculate();
//		assertEquals(result == new BigDecimal(7) * varX + 3 * varY - (BigDecimal) Math.pow((BigDecimal) Math.log(varY / varX * new BigDecimal(12)), varY));
//	}
//
//	@Test
//	public void testExpressionBuilder4() throws Exception {
//		BigDecimal varX = new BigDecimal(1.3);
//		BigDecimal varY = new BigDecimal(4.22);
//		Calculable<BigDecimal> calc = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y", BigDecimal.class).variables("x", "y").build();
//		calc.setVariable("x", varX);
//		calc.setVariable("y", varY);
//		BigDecimal result = calc.calculate();
//		assertEquals(result == new BigDecimal(7) * varX + new BigDecimal(3) * varY - (BigDecimal) Math.pow((BigDecimal) Math.log(varY / varX * new BigDecimal(12)), varY));
//		varX = new BigDecimal(1.79854);
//		varY = new BigDecimal(9281.123);
//		calc.setVariable("x", varX);
//		calc.setVariable("y", varY);
//		result = calc.calculate();
//		assertEquals(result == new BigDecimal(7) * varX + new BigDecimal(3) * varY - (BigDecimal) Math.pow((BigDecimal) Math.log(varY / varX * new BigDecimal(12)), varY));
//	}
//
//	@Test
//	public void testExpressionBuilder5() throws Exception {
//		BigDecimal varY = new BigDecimal(4.22);
//		Calculable<BigDecimal> calc = new ExpressionBuilder("3*y", BigDecimal.class).variables("y").build();
//		calc.setVariable("y", varY);
//		BigDecimal result = calc.calculate();
//		assertEquals(result == new BigDecimal(3) * varY);
//	}
//
//	@Test
//	public void testExpressionBuilder6() throws Exception {
//		BigDecimal varX = new BigDecimal(1.3);
//		BigDecimal varY = new BigDecimal(4.22);
//		BigDecimal varZ = new BigDecimal(4.22);
//		Calculable<BigDecimal> calc = new ExpressionBuilder("x * y * z", BigDecimal.class).variables("x", "y", "z").build();
//		calc.setVariable("x", varX);
//		calc.setVariable("y", varY);
//		calc.setVariable("z", varZ);
//		BigDecimal result = calc.calculate();
//		assertEquals(result == varX * varY * varZ);
//	}
//
//	@Test
//	public void testExpressionBuilder7() throws Exception {
//		BigDecimal varX = new BigDecimal(1.3);
//		Calculable<BigDecimal> calc = new ExpressionBuilder("log(sin(x))", BigDecimal.class).variable("x").build();
//		calc.setVariable("x", varX);
//		BigDecimal result = calc.calculate();
//		assertEquals(result == (BigDecimal) Math.log((BigDecimal) Math.sin(varX)));
//	}
//
//	@Test
//	public void testExpressionBuilder8() throws Exception {
//		BigDecimal varX = new BigDecimal(1.3);
//		Calculable<BigDecimal> calc = new ExpressionBuilder("log(sin(x))", BigDecimal.class).variable("x").build();
//		calc.setVariable("x", varX);
//		BigDecimal result = calc.calculate();
//		assertEquals(result == (BigDecimal) Math.log((BigDecimal) Math.sin(varX)));
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testSameName() throws Exception {
//		CustomFunction custom = new CustomFunction("bar") {
//			@Override
//			public Object apply(Object... values) {
//				return (BigDecimal) values[0] / new BigDecimal(2);
//			}
//		};
//		BigDecimal varBar = new BigDecimal(1.3);
//		Calculable<BigDecimal> calc = new ExpressionBuilder("f(bar)=bar(bar)", BigDecimal.class).variable("bar").function(custom).build();
//		BigDecimal result = calc.calculate();
//		assertEquals(result == varBar / 2);
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testInvalidFunction() throws Exception {
//		BigDecimal varY = new BigDecimal(4.22);
//		Calculable<BigDecimal> calc = new ExpressionBuilder("3*invalid_function(y)", BigDecimal.class).variable("y").build();
//		calc.calculate();
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testMissingVar() throws Exception {
//		BigDecimal varY = new BigDecimal(4.22);
//		Calculable<BigDecimal> calc = new ExpressionBuilder("3*y*z", BigDecimal.class).variable("y").build();
//		calc.calculate();
//	}
//
//	@Test
//	public void testOperatorPrecedence() throws Exception {
//
//		ExpressionBuilder builder = new ExpressionBuilder("1", BigDecimal.class);
//		Field operatorField = Operators.class.getDeclaredField("builtin");
//		operatorField.setAccessible(true);
//		Map<Character, CustomOperator> operators = (Map<Character, CustomOperator>) operatorField.get(builder);
//
//		assertEquals(operators.get("+").isLeftAssociative());
//		assertEquals(operators.get("*").isLeftAssociative());
//		assertEquals(operators.get("-").isLeftAssociative());
//		assertEquals(operators.get("/").isLeftAssociative());
//		assertEquals(!operators.get("^").isLeftAssociative());
//
//		assertEquals(operators.get("+").getPrecedence() == operators.get("-").getPrecedence());
//		assertEquals(operators.get("+").getPrecedence() < operators.get("*").getPrecedence());
//		assertEquals(operators.get("+").getPrecedence() < operators.get("/").getPrecedence());
//		assertEquals(operators.get("+").getPrecedence() < operators.get("^").getPrecedence());
//
//		assertEquals(operators.get("-").getPrecedence() == operators.get("+").getPrecedence());
//		assertEquals(operators.get("-").getPrecedence() < operators.get("*").getPrecedence());
//		assertEquals(operators.get("-").getPrecedence() < operators.get("/").getPrecedence());
//		assertEquals(operators.get("-").getPrecedence() < operators.get("^").getPrecedence());
//
//		assertEquals(operators.get("*").getPrecedence() > operators.get("+").getPrecedence());
//		assertEquals(operators.get("*").getPrecedence() > operators.get("-").getPrecedence());
//		assertEquals(operators.get("*").getPrecedence() == operators.get("/").getPrecedence());
//		assertEquals(operators.get("*").getPrecedence() < operators.get("^").getPrecedence());
//
//		assertEquals(operators.get("/").getPrecedence() > operators.get("+").getPrecedence());
//		assertEquals(operators.get("/").getPrecedence() > operators.get("-").getPrecedence());
//		assertEquals(operators.get("/").getPrecedence() == operators.get("*").getPrecedence());
//		assertEquals(operators.get("/").getPrecedence() < operators.get("^").getPrecedence());
//
//		assertEquals(operators.get("^").getPrecedence() > operators.get("+").getPrecedence());
//		assertEquals(operators.get("^").getPrecedence() > operators.get("-").getPrecedence());
//		assertEquals(operators.get("^").getPrecedence() > operators.get("*").getPrecedence());
//		assertEquals(operators.get("^").getPrecedence() > operators.get("/").getPrecedence());
//
//	}
//
//	@Test
//	public void testExpression1() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "2 + 4";
//		expected = new BigDecimal(6);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression10() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "1 * 1.5 + 1";
//		expected = 1 * 1.5 + 1;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression11() throws Exception {
//		BigDecimal x = new BigDecimal(1);
//		BigDecimal y = new BigDecimal(2);
//		String expr = "log(x) ^ sin(y)";
//		BigDecimal expected = (BigDecimal) Math.pow((BigDecimal) Math.log(x), (BigDecimal) Math.sin(y));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x", "y").build();
//		calc.setVariable("x", x);
//		calc.setVariable("y", y);
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression12() throws Exception {
//		String expr = "log(2.5333333333)^(0-1)";
//		BigDecimal expected = (BigDecimal) Math.pow((BigDecimal) Math.log(new BigDecimal(2.5333333333)), new BigDecimal(-1));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression13() throws Exception {
//		String expr = "2.5333333333^(0-1)";
//		BigDecimal expected = (BigDecimal) Math.pow(new BigDecimal(2.5333333333), new BigDecimal(-1));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression14() throws Exception {
//		String expr = "2 * 17.41 + (12*2)^(0-1)";
//		BigDecimal expected = new BigDecimal(2) * new BigDecimal(17.41) + (BigDecimal) Math.pow((new BigDecimal(12) * new BigDecimal(2)), new BigDecimal(-1));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression15() throws Exception {
//		String expr = "2.5333333333 * 17.41 + (12*2)^log(2.764)";
//		BigDecimal expected = new BigDecimal(2.5333333333) * new BigDecimal(17.41) + (BigDecimal) Math.pow((new BigDecimal(12) * new BigDecimal(2)), (BigDecimal) Math.log(new BigDecimal(2.764)));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression16() throws Exception {
//		String expr = "2.5333333333/2 * 17.41 + (12*2)^(log(2.764) - sin(5.6664))";
//		BigDecimal expected = new BigDecimal(2.5333333333) / new BigDecimal(2) * new BigDecimal(17.41) + (BigDecimal) Math.pow((new BigDecimal(12) * new BigDecimal(2)), (BigDecimal) Math.log(new BigDecimal(2.764))
//				- (BigDecimal) Math.sin(new BigDecimal(5.6664)));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression17() throws Exception {
//		String expr = "x^2 - 2 * y";
//		BigDecimal x = (BigDecimal) Math.E;
//		BigDecimal y = (BigDecimal) Math.PI;
//		BigDecimal expected = x * x - new BigDecimal(2) * y;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x", "y").build();
//		calc.setVariable("x", x);
//		calc.setVariable("y", y);
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression18() throws Exception {
//		String expr = "-3";
//		BigDecimal expected = -3;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression19() throws Exception {
//		String expr = "-3 * -24.23";
//		BigDecimal expected = new BigDecimal(-3) * new BigDecimal(-24.23);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression2() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "2+3*4-12";
//		expected = 2 + 3 * 4 - 12;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression20() throws Exception {
//		String expr = "-2 * 24/log(2) -2";
//		BigDecimal expected = new BigDecimal(-2) * new BigDecimal(24) / (BigDecimal) Math.log(new BigDecimal(2)) - new BigDecimal(2);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression21() throws Exception {
//		String expr = "-2 *33.34/log(x)^-2 + 14 *6";
//		BigDecimal x = new BigDecimal(1.334);
//		BigDecimal expected = new BigDecimal(-2) * new BigDecimal(33.34) / (BigDecimal) Math.pow((BigDecimal) Math.log(x), new BigDecimal(-2)) + new BigDecimal(14) * new BigDecimal(6);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		calc.setVariable("x", x);
//		BigDecimal result = calc.calculate();
//		assertEquals("expected " + expected + " but got " + result, expected == result);
//	}
//
//	@Test
//	public void testExpression21_1() throws Exception {
//		String expr = "log(x)^-2";
//		BigDecimal x = new BigDecimal(1.334);
//		BigDecimal expected = (BigDecimal) Math.pow((BigDecimal) Math.log(x), new BigDecimal(-2));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variable("x").build();
//		calc.setVariable("x", x);
//		BigDecimal result = calc.calculate();
//		assertEquals("expected " + expected + " but got " + result, expected == result);
//	}
//
//	@Test
//	public void testExpression22() throws Exception {
//		String expr = "-2 *33.34/log(x)^-2 + 14 *6";
//		BigDecimal x = new BigDecimal(1.334);
//		BigDecimal expected = new BigDecimal(-2) * new BigDecimal(33.34) / (BigDecimal) Math.pow((BigDecimal) Math.log(x), new BigDecimal(-2)) + new BigDecimal(14) * new BigDecimal(6);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		calc.setVariable("x", x);
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression23() throws Exception {
//		String expr = "-2 *33.34/(log(foo)^-2 + 14 *6) - sin(foo)";
//		BigDecimal x = new BigDecimal(1.334);
//		BigDecimal expected = new BigDecimal(-2) * new BigDecimal(33.34) / ((BigDecimal) Math.pow((BigDecimal) Math.log(x), new BigDecimal(-2)) + new BigDecimal(14) * new BigDecimal(6)) - (BigDecimal) Math.sin(x);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("foo").build();
//		calc.setVariable("foo", x);
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression24() throws Exception {
//		String expr = "3+4-log(23.2)^(2-1) * -1";
//		BigDecimal expected = new BigDecimal(3) + new BigDecimal(4) - (BigDecimal) Math.pow((BigDecimal) Math.log(new BigDecimal(23.2)), (new BigDecimal(2) - new BigDecimal(1))) * new BigDecimal(-1);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression25() throws Exception {
//		String expr = "+3+4-+log(23.2)^(2-1) * + 1";
//		BigDecimal expected = new BigDecimal(3) + new BigDecimal(4) - (BigDecimal) Math.log(new BigDecimal(23.2));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression26() throws Exception {
//		String expr = "14 + -(1 / 2.22^3)";
//		BigDecimal expected = new BigDecimal(14) + -(new BigDecimal(1) / (BigDecimal) Math.pow(new BigDecimal(2.22), new BigDecimal(3)));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression27() throws Exception {
//		String expr = "12^-+-+-+-+-+-+---2";
//		BigDecimal expected = (BigDecimal) Math.pow(new BigDecimal(12), new BigDecimal(-2));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression28() throws Exception {
//		String expr = "12^-+-+-+-+-+-+---2 * (-14) / 2 ^ -log(2.22323) ";
//		BigDecimal expected = (BigDecimal) Math.pow(new BigDecimal(12), new BigDecimal(-2)) * new BigDecimal(-14) / (BigDecimal) Math.pow(new BigDecimal(2), (BigDecimal) -Math.log(new BigDecimal(2.22323)));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression29() throws Exception {
//		String expr = "24.3343 % 3";
//		BigDecimal expected = new BigDecimal(24.3343) % new BigDecimal(3);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testVarname1() throws Exception {
//		String expr = "12.23 * foo.bar";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variable("foo.bar").build();
//		assertEquals(new BigDecimal(12.23) == calc.calculate());
//	}
//
//	public void testVarname2() throws Exception {
//		String expr = "12.23 * foo.bar";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variable("_foo").build();
//		assertEquals(new BigDecimal(12.23) == calc.calculate());
//	}
//
//	@Test
//	public void testExpression3() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "2+4*5";
//		expected = new BigDecimal(2) + new BigDecimal(4) * new BigDecimal(5);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression30() throws Exception {
//		String expr = "24.3343 % 3 * 20 ^ -(2.334 % log(2 / 14))";
//		BigDecimal expected = new BigDecimal(24.3343) % new BigDecimal(3) * (BigDecimal) Math.pow(new BigDecimal(20), -(new BigDecimal(2.334) % (BigDecimal) Math.log(new BigDecimal(2) / new BigDecimal(14))));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression31() throws Exception {
//		String expr = "-2 *33.34/log(y_x)^-2 + 14 *6";
//		BigDecimal x = new BigDecimal(1.334);
//		BigDecimal expected = new BigDecimal(-2) * new BigDecimal(33.34) / (BigDecimal) Math.pow((BigDecimal) Math.log(x), new BigDecimal(-2)) + new BigDecimal(14) * new BigDecimal(6);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("y_x").build();
//		calc.setVariable("y_x", x);
//		BigDecimal actual = calc.calculate();
//		assertEquals(expected == actual);
//	}
//
//	@Test
//	public void testExpression32() throws Exception {
//		String expr = "-2 *33.34/log(y_2x)^-2 + 14 *6";
//		BigDecimal x = new BigDecimal(1.334);
//		BigDecimal expected = new BigDecimal(-2) * new BigDecimal(33.34) / (BigDecimal) Math.pow((BigDecimal) Math.log(x), new BigDecimal(-2)) + new BigDecimal(14) * new BigDecimal(6);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("y_2x").build();
//		calc.setVariable("y_2x", x);
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression33() throws Exception {
//		String expr = "-2 *33.34/log(_y)^-2 + 14 *6";
//		BigDecimal x = new BigDecimal(1.334);
//		BigDecimal expected = new BigDecimal(-2) * new BigDecimal(33.34) / (BigDecimal) Math.pow((BigDecimal) Math.log(x), new BigDecimal(-2)) + new BigDecimal(14) * new BigDecimal(6);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("_y").build();
//		calc.setVariable("_y", x);
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression34() throws Exception {
//		String expr = "-2 + + (+4) +(4)";
//		BigDecimal expected = -2 + 4 + 4;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	@Ignore
//	public void testExpression40() throws Exception {
//		String expr = "1e1";
//		BigDecimal expected = new BigDecimal(10);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	@Ignore
//	public void testExpression41() throws Exception {
//		String expr = "1e-1";
//		BigDecimal expected = new BigDecimal(0.1);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	/**
//	 * Added tests for expressions with scientific notation see
//	 * http://jira.congrace.de/jira/browse/EXP-17
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	@Ignore
//	public void testExpression42() throws Exception {
//		String expr = "7.2973525698e-3";
//		BigDecimal expected = new BigDecimal(7.2973525698) * (BigDecimal) Math.pow(new BigDecimal(10), new BigDecimal(-3));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	@Ignore
//	public void testExpression43() throws Exception {
//		String expr = "6.02214E23";
//		BigDecimal expected = new BigDecimal(6.02214) * (BigDecimal) Math.pow(new BigDecimal(10), new BigDecimal(23));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	@Ignore
//	public void testExpression44() throws Exception {
//		String expr = "6.02214E23";
//		BigDecimal expected = new BigDecimal(6.02214) * (BigDecimal) Math.pow(new BigDecimal(10), new BigDecimal(23));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test(expected = RuntimeException.class)
//	@Ignore
//	public void testExpression45() throws Exception {
//		String expr = "6.02214E2E3";
//		new ExpressionBuilder(expr, BigDecimal.class).build();
//	}
//
//	@Test(expected = RuntimeException.class)
//	@Ignore
//	public void testExpression46() throws Exception {
//		String expr = "6.02214e2E3";
//		new ExpressionBuilder(expr, BigDecimal.class).build();
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testExpression47() throws Exception {
//		String expr = "6.02214y3";
//		new ExpressionBuilder(expr, BigDecimal.class).build();
//	}
//
//	// tests for EXP-20: No exception is thrown for unmatched parenthesis in
//	// build
//	// Thanks go out to maheshkurmi for reporting
//	@Test(expected = UnparseableExpressionException.class)
//	public void testExpression48() throws Exception {
//		String expr = "(1*2";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		BigDecimal result = calc.calculate();
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testExpression49() throws Exception {
//		String expr = "{1*2";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		BigDecimal result = calc.calculate();
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testExpression50() throws Exception {
//		String expr = "[1*2";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		BigDecimal result = calc.calculate();
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testExpression51() throws Exception {
//		String expr = "(1*{2+[3}";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		BigDecimal result = calc.calculate();
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testExpression52() throws Exception {
//		String expr = "(1*(2+(3";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		BigDecimal result = calc.calculate();
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testExpression53() throws Exception {
//		String expr = "14 * 2x";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		assertEquals(new BigDecimal(56) == calc.calculate());
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testExpression54() throws Exception {
//		String expr = "2 ((-(x)))";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		assertEquals(new BigDecimal(-4) == calc.calculate());
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testExpression55() throws Exception {
//		String expr = "2 sin(x)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		assertEquals(Math.sin(new BigDecimal(2)) * 2 == calc.calculate());
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testExpression56() throws Exception {
//		String expr = "2 sin(3x)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		assertEquals(Math.sin(new BigDecimal(6)) * 2 == calc.calculate());
//	}
//
//	// Thanks go out to Johan Björk for reporting the division by zero problem
//	// EXP-22
//	// https://www.objecthunter.net/jira/browse/EXP-22
//	@Test(expected = ArithmeticException.class)
//	public void testExpression57() throws Exception {
//		String expr = "1 / 0";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(BigDecimal.POSITIVE_INFINITY == calc.calculate());
//	}
//
//	@Test
//	public void testExpression58() throws Exception {
//		String expr = "17 * sqrt(-1) * 12";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(BigDecimal.isNaN(calc.calculate()));
//	}
//
//	// Thanks go out to Alex Dolinsky for reporting the missing exception when
//	// an empty
//	// expression is passed as in new ExpressionBuilder("")
//	@Test(expected = IllegalArgumentException.class)
//	public void testExpression59() throws Exception {
//		Calculable<BigDecimal> calc = new ExpressionBuilder("", BigDecimal.class).build();
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testExpression60() throws Exception {
//		Calculable<BigDecimal> calc = new ExpressionBuilder("   ", BigDecimal.class).build();
//		calc.calculate();
//	}
//
//	@Test(expected = ArithmeticException.class)
//	public void testExpression61() throws Exception {
//		Calculable<BigDecimal> calc = new ExpressionBuilder("14 % 0", BigDecimal.class).build();
//		calc.calculate();
//	}
//
//	// https://www.objecthunter.net/jira/browse/EXP-24
//	// thanks go out to Rémi for the issue report
//	@Test
//	@Ignore
//	public void testExpression62() throws Exception {
//		Calculable<BigDecimal> calc = new ExpressionBuilder("x*1.0e5+5", BigDecimal.class).variables("x").build();
//		assertEquals(Math.E * 1.0 * Math.pow(10, 5) + 5 == calc.calculate());
//	}
//
//	// thanks go out to Janny for providing the tests and the bug report
//	@Test
//	public void testUnaryMinusInParenthesisSpace() throws Exception {
//		ExpressionBuilder b = new ExpressionBuilder("( -1)^2", BigDecimal.class);
//		BigDecimal calculated = (BigDecimal) b.build().calculate();
//		assertEquals(calculated == new BigDecimal(1));
//	}
//
//	@Test
//	public void testUnaryMinusSpace() throws Exception {
//		ExpressionBuilder b = new ExpressionBuilder(" -1 + 2", BigDecimal.class);
//		BigDecimal calculated = (BigDecimal) b.build().calculate();
//		assertEquals(calculated == new BigDecimal(1));
//	}
//
//	@Test
//	public void testUnaryMinusSpaces() throws Exception {
//		ExpressionBuilder b = new ExpressionBuilder(" -1 + + 2 +   -   1", BigDecimal.class);
//		BigDecimal calculated = (BigDecimal) b.build().calculate();
//		assertEquals(calculated == new BigDecimal(0));
//	}
//
//	@Test
//	public void testUnaryMinusSpace1() throws Exception {
//		ExpressionBuilder b = new ExpressionBuilder("-1", BigDecimal.class);
//		BigDecimal calculated = (BigDecimal) b.build().calculate();
//		assertEquals(calculated == new BigDecimal(-1));
//	}
//
//	@Test
//	public void testExpression4() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "2+4 * 5";
//		expected = 2 + 4 * 5;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	// test for https://www.objecthunter.net/jira/browse/EXP-19
//	// thanks go out to Yunior Peralta for the report
//	@Test
//	public void testCharacterPositionInException1() throws Exception {
//		String expr;
//		expr = "2 + sn(4)";
//		try {
//			Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//			Assert.fail("Expression was parsed but should throw an Exception");
//		} catch (UnparseableExpressionException e) {
//			String expected = "Unable to parse name 'sn' in expression '2 + sn(4)'";
//			assertEquals(expected, e.getMessage());
//		}
//	}
//
//	@Test
//	public void testExpression5() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "(2+4)*5";
//		expected = (2 + 4) * 5;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression6() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "(2+4)*5 + 2.5*2";
//		expected = (2 + 4) * 5 + 2.5 * 2;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression7() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "(2+4)*5 + 10/2";
//		expected = (2 + 4) * 5 + 10 / 2;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression8() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "(2 * 3 +4)*5 + 10/2";
//		expected = (2 * 3 + 4) * 5 + 10 / 2;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testExpression9() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "(2 * 3 +4)*5 +4 + 10/2";
//		expected = (2 * 3 + 4) * 5 + 4 + 10 / 2;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testFailUnknownFunction1() throws Exception {
//		String expr;
//		expr = "lig(1)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		calc.calculate();
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testFailUnknownFunction2() throws Exception {
//		String expr;
//		expr = "galength(1)";
//		new ExpressionBuilder(expr, BigDecimal.class).build().calculate();
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testFailUnknownFunction3() throws Exception {
//		String expr;
//		expr = "f(cos) = cos(cos)";
//		new ExpressionBuilder(expr, BigDecimal.class).build().calculate();
//	}
//
//	@Test
//	public void testFunction1() throws Exception {
//		String expr;
//		expr = "cos(cos_1)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("cos_1").build();
//		calc.setVariable("cos_1", new BigDecimal(1));
//		assertEquals(calc.calculate() == (BigDecimal) Math.cos(new BigDecimal(1)));
//	}
//
//	@Test
//	public void testPostfix1() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "2.2232^0.1";
//		expected = (BigDecimal) Math.pow(new BigDecimal(2.2232), new BigDecimal(0.1));
//		BigDecimal actual = (BigDecimal) new ExpressionBuilder(expr, BigDecimal.class).build().calculate();
//		assertEquals(expected == actual);
//	}
//
//	@Test
//	public void testPostfixEverything() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "(sin(12) + log(34)) * 3.42 - cos(2.234-log(2))";
//		expected = (BigDecimal) (Math.sin(new BigDecimal(12)) + (BigDecimal) Math.log(34)) * new BigDecimal(3.42) 				- (BigDecimal) Math.cos(new BigDecimal(2.234) - (BigDecimal) Math.log(new BigDecimal(2)));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testPostfixExponentation1() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "2^3";
//		expected = (BigDecimal) Math.pow(new BigDecimal(2), new BigDecimal(3));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testPostfixExponentation2() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "24 + 4 * 2^3";
//		expected = new BigDecimal(24) + new BigDecimal(4) * (BigDecimal) Math.pow(new BigDecimal(2), new BigDecimal(3));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testPostfixExponentation3() throws Exception {
//		String expr;
//		BigDecimal expected;
//		BigDecimal x = new BigDecimal(4.334);
//		expr = "24 + 4 * 2^x";
//		expected = new BigDecimal(24) + new BigDecimal(4) * (BigDecimal) Math.pow(new BigDecimal(2), x);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		calc.setVariable("x", x);
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testPostfixExponentation4() throws Exception {
//		String expr;
//		BigDecimal expected;
//		BigDecimal x = new BigDecimal(4.334);
//		expr = "(24 + 4) * 2^log(x)";
//		expected = (new BigDecimal(24) + new BigDecimal(4)) * (BigDecimal) Math.pow(new BigDecimal(2), (BigDecimal) Math.log(x));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		calc.setVariable("x", x);
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testPostfixFunction1() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "log(1) * sin(0)";
//		expected = (BigDecimal) Math.log(new BigDecimal(1)) * (BigDecimal) Math.sin(new BigDecimal(0));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testPostfixFunction10() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "cbrt(x)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		for (BigDecimal x = -10; x < 10; x = x + new BigDecimal(0.5)) {
//			expected = (BigDecimal) Math.cbrt(x);
//			calc.setVariable("x", x);
//			assertEquals(expected == calc.calculate());
//		}
//	}
//
//	@Test
//	public void testPostfixFunction11() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "cos(x) - (1/cbrt(x))";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		for (BigDecimal x = -10; x < 10; x = x + new BigDecimal(0.5)) {
//			if (x == new BigDecimal(0))
//				continue;
//			expected = (BigDecimal) Math.cos(x) - (new BigDecimal(1) / (BigDecimal) Math.cbrt(x));
//			calc.setVariable("x", x);
//			assertEquals(expected == calc.calculate());
//		}
//	}
//
//	@Test
//	public void testPostfixFunction12() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "acos(x) * expm1(asin(x)) - exp(atan(x)) + floor(x) + cosh(x) - sinh(cbrt(x))";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		for (BigDecimal x = -10; x < 10; x = x + new BigDecimal(0.5)) {
//			expected = (BigDecimal) Math.acos(x) * (BigDecimal) Math.expm1((BigDecimal) Math.asin(x))
//					- (BigDecimal) Math.exp((BigDecimal) Math.atan(x)) + (BigDecimal) Math.floor(x) + (BigDecimal) Math.cosh(x)
//					- (BigDecimal) Math.sinh((BigDecimal) Math.cbrt(x));
//			calc.setVariable("x", x);
//			if (BigDecimal.isNaN(expected)) {
//				assertEquals(BigDecimal.isNaN(calc.calculate()));
//			} else {
//				assertEquals(expected == calc.calculate());
//			}
//		}
//	}
//
//	@Test
//	public void testPostfixFunction13() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "acos(x)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		for (BigDecimal x = -10; x < 10; x = x + new BigDecimal(0.5)) {
//			expected = (BigDecimal) Math.acos(x);
//			calc.setVariable("x", x);
//			if (BigDecimal.isNaN(expected)) {
//				assertEquals(BigDecimal.isNaN(calc.calculate()));
//			} else {
//				assertEquals(expected == calc.calculate());
//			}
//		}
//	}
//
//	@Test
//	public void testPostfixFunction14() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = " expm1(x)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		for (BigDecimal x = -10; x < 10; x = x + new BigDecimal(0.5)) {
//			expected = (BigDecimal) Math.expm1(x);
//			calc.setVariable("x", x);
//			if (BigDecimal.isNaN(expected)) {
//				assertEquals(BigDecimal.isNaN(calc.calculate()));
//			} else {
//				assertEquals(expected == calc.calculate());
//			}
//		}
//	}
//
//	@Test
//	public void testPostfixFunction15() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "asin(x)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		for (BigDecimal x = -10; x < 10; x = x + new BigDecimal(0.5)) {
//			expected = (BigDecimal) Math.asin(x);
//			calc.setVariable("x", x);
//			if (BigDecimal.isNaN(expected)) {
//				assertEquals(BigDecimal.isNaN(calc.calculate()));
//			} else {
//				assertEquals(expected == calc.calculate());
//			}
//		}
//	}
//
//	@Test
//	public void testPostfixFunction16() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = " exp(x)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		for (BigDecimal x = -10; x < 10; x = x + new BigDecimal(0.5)) {
//			expected = (BigDecimal) Math.exp(x);
//			calc.setVariable("x", x);
//			assertEquals(expected == calc.calculate());
//		}
//	}
//
//	@Test
//	public void testPostfixFunction17() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "floor(x)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		for (BigDecimal x = -10; x < 10; x = x + new BigDecimal(0.5)) {
//			expected = (BigDecimal) Math.floor(x);
//			calc.setVariable("x", x);
//			assertEquals(expected == calc.calculate());
//		}
//	}
//
//	@Test
//	public void testPostfixFunction18() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = " cosh(x)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		for (BigDecimal x = -10; x < 10; x = x + new BigDecimal(0.5)) {
//			expected = (BigDecimal) Math.cosh(x);
//			calc.setVariable("x", x);
//			assertEquals(expected == calc.calculate());
//		}
//	}
//
//	@Test
//	public void testPostfixFunction19() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "sinh(x)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		for (BigDecimal x = -10; x < 10; x = x + new BigDecimal(0.5)) {
//			expected = (BigDecimal) Math.sinh(x);
//			calc.setVariable("x", x);
//			assertEquals(expected == calc.calculate());
//		}
//	}
//
//	@Test
//	public void testPostfixFunction20() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "cbrt(x)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		for (BigDecimal x = -10; x < 10; x = x + new BigDecimal(0.5)) {
//			expected = (BigDecimal) Math.cbrt(x);
//			calc.setVariable("x", x);
//			assertEquals(expected == calc.calculate());
//		}
//	}
//
//	@Test
//	public void testPostfixFunction21() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "tanh(x)";
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		for (BigDecimal x = -10; x < 10; x = x + new BigDecimal(0.5)) {
//			expected = (BigDecimal) Math.tanh(x);
//			calc.setVariable("x", x);
//			assertEquals(expected == calc.calculate());
//		}
//	}
//
//	@Test
//	public void testPostfixFunction2() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "log(1)";
//		expected = new BigDecimal(0);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testPostfixFunction3() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "sin(0)";
//		expected = new BigDecimal(0);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testPostfixFunction5() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "ceil(2.3) +1";
//		expected = Math.ceil(2.3) + 1;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testPostfixFunction6() throws Exception {
//		String expr;
//		BigDecimal expected;
//		BigDecimal x = new BigDecimal(1.565);
//		BigDecimal y = new BigDecimal(2.1323);
//		expr = "ceil(x) + 1  / y * abs(1.4)";
//		expected = (BigDecimal) Math.ceil(x) + (BigDecimal) new BigDecimal(1) / y * (BigDecimal) Math.abs(new BigDecimal(1.4));
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x", "y").build();
//		calc.setVariable("x", x);
//		calc.setVariable("y", y);
//		BigDecimal actual = calc.calculate();
//		assertEquals((BigDecimal) expected, (BigDecimal) actual);
//	}
//
//	@Test
//	public void testPostfixFunction7() throws Exception {
//		String expr;
//		BigDecimal expected;
//		BigDecimal x = (BigDecimal) Math.E;
//		expr = "tan(x)";
//		expected = (BigDecimal) Math.tan(x);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		calc.setVariable("x", x);
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testPostfixFunction8() throws Exception {
//		String expr;
//		BigDecimal expected;
//		BigDecimal e = (BigDecimal) Math.E;
//		expr = "2^3.4223232 + tan(e)";
//		expected = (BigDecimal) Math.pow(new BigDecimal(2), new BigDecimal(3.4223232)) + (BigDecimal) Math.tan((BigDecimal) Math.E);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("e").build();
//		calc.setVariable("e", e);
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testPostfixFunction9() throws Exception {
//		String expr;
//		BigDecimal expected;
//		BigDecimal x = (BigDecimal) Math.E;
//		expr = "cbrt(x)";
//		expected = (BigDecimal) Math.cbrt(x);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x").build();
//		calc.setVariable("x", x);
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test(expected = UnparseableExpressionException.class)
//	public void testPostfixInvalidVariableName() throws Exception {
//		String expr;
//		BigDecimal expected;
//		BigDecimal x = new BigDecimal(4.5334332);
//		BigDecimal log = Math.PI;
//		expr = "x * pi";
//		expected = x * log;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x", "log").build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testPostfixParanthesis() throws Exception {
//		String expr;
//		BigDecimal expected;
//		expr = "(3 + 3 * 14) * (2 * (24-17) - 14)/((34) -2)";
//		expected = (3 + 3 * 14) * (2 * (24 - 17) - 14) / ((34) - 2);
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).build();
//		assertEquals(expected == calc.calculate());
//	}
//
//	@Test
//	public void testPostfixVariables() throws Exception {
//		String expr;
//		BigDecimal expected;
//		BigDecimal x = new BigDecimal(4.5334332);
//		BigDecimal pi = (BigDecimal) Math.PI;
//		expr = "x * pi";
//		expected = x * pi;
//		Calculable<BigDecimal> calc = new ExpressionBuilder(expr, BigDecimal.class).variables("x", "pi").build();
//		calc.setVariable("x", x);
//		calc.setVariable("pi", pi);
//		assertEquals(expected == calc.calculate());
//	}
}
