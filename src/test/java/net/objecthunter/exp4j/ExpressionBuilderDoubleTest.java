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
public class ExpressionBuilderDoubleTest {

	@Test
	public void testDoubleExpression1() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("2+2", Double.class);
		double result = e.build().calculate();
		assertTrue(4d == result);
	}

	@Test
	public void testDoubleExpression2() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("2-2", Double.class);
		double result = e.build().calculate();
		assertTrue(0d == result);
	}

	@Test
	public void testDoubleExpression3() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("2/2", Double.class);
		double result = e.build().calculate();
		assertTrue(1d == result);
	}

	@Test
	public void testDoubleExpression4() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("2.45-1", Double.class);
		double result = e.build().calculate();
		assertEquals(2.45d-1d,result,0d);
	}

	@Test
	public void testDoubleExpression5() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("2.45-3", Double.class);
		double result = e.build().calculate();
		double expected = 2.45d - 3d;
		assertTrue("exp4j calulated " + result, expected == result);
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testDoubleExpression6() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("2.45(-3", Double.class);
		double result = e.build().calculate();
	}

	@Test(expected = RuntimeException.class)
	public void testDoubleExpression7() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("2.45)-3", Double.class);
		double result = e.build().calculate();
	}

	@Test
	public void testDoubleExpression8() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("sin(1.0)", Double.class);
		double result = e.build().calculate();
		double expected = (double) Math.sin(1.0);
		assertTrue("exp4j calulated " + result, expected == result);
	}

	@Test
	public void testDoubleExpression9() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("sin(1.0) * 1 + 1", Double.class);
		double result = e.build().calculate();
		double expected = (double) Math.sin(1.0) * 1 + 1;
		assertTrue("exp4j calulated " + result, expected == result);
	}

	@Test
	public void testDoubleExpression10() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("-1", Double.class);
		double result = e.build().calculate();
		assertTrue("exp4j calulated " + result, -1d == result);
	}

	@Test
	public void testDoubleExpression11() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("7+-1", Double.class);
		double result = e.build().calculate();
		assertTrue("exp4j calulated " + result, 6d == result);
	}

	@Test
	public void testDoubleExpression12() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("7+--1", Double.class);
		double result = e.build().calculate();
		assertTrue("exp4j calulated " + result, 8d == result);
	}

	@Test
	public void testDoubleExpression13() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("7++1", Double.class);
		double result = e.build().calculate();
		assertTrue("exp4j calulated " + result, 8d == result);
	}

	@Test
	public void testDoubleExpression14() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("sin(-1.0)", Double.class);
		double result = e.build().calculate();
		assertTrue("exp4j calulated " + result, (double) Math.sin(-1.0d) == result);
	}

	@Test
	public void testDoubleExpression15() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("log(4) - 3 * (sqrt(3^cos(2)))", Double.class);
		double result = e.build().calculate();
		double expected = (double) Math.log(4d) - 3d * (double) (Math.sqrt((double) Math.pow(3d, (double) Math.cos(2d))));
		assertEquals(expected, result, 0d);
	}

	@Test
	public void testDoubleExpression16() throws Exception {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("sqrt(3^cos(2))", Double.class);
		double result = e.build().calculate();
		double expected = (double) Math.sqrt((double) Math.pow(3d, (double) Math.cos(2d)));
		assertEquals(expected, result, 0d);
	}

	/* Tests from exp4j v1 follow, these should of course pass */
	@Test
	public void testCustomFunction1() throws Exception {
		CustomFunction custom = new CustomFunction("timespi") {
			@Override
			public Object apply(Object... args) {
				return (double) args[0] * (double) Math.PI;
			}
		};
		Calculable<Double> calc = new ExpressionBuilder("timespi(x)", Double.class).variable("x").function(custom).build();
		calc.setVariable("x", 1d);
		double result = calc.calculate();
		assertTrue(result == (double) Math.PI);
	}

	@Test
	public void testCustomFunction2() throws Exception {
		CustomFunction custom = new CustomFunction("loglog") {
			@Override
			public Object apply(Object... values) {
				return (double) Math.log(Math.log(((Double) values[0]).doubleValue()));
			}
		};
		Calculable<Double> calc = new ExpressionBuilder("loglog(x)", Double.class).variable("x").function(custom).build();
		calc.setVariable("x", 1d);
		double result = calc.calculate();
		assertTrue(result == Math.log(Math.log(1)));
	}

	@Test
	public void testCustomFunction3() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] * (double) Math.E;
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] * (double) Math.PI;
			}
		};
		Calculable<Double> calc = new ExpressionBuilder("foo(bar(x))", Double.class).variable("x").function(custom1).function(custom2).build();
		calc.setVariable("x", 1d);
		double result = calc.calculate();
		assertTrue(result == 1d * (double) Math.E * (double) Math.PI);
	}

	@Test
	public void testCustomFunction4() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] * (double) Math.E;
			}
		};
		double varX = 32.24979131d;
		Calculable<Double> calc = new ExpressionBuilder("foo(log(x))", Double.class).variable("x").function(custom1).build();
		double result = calc.setVariable("x", varX).calculate();
		assertTrue(result == (double) Math.log(varX) * (double) Math.E);
	}

	@Test
	public void testCustomFunction5() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] * (double) Math.E;
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] * (double) Math.PI;
			}
		};
		double varX = 32.24979131d;
		Calculable<Double> calc = new ExpressionBuilder("bar(foo(log(x)))", Double.class).variable("x").function(custom1).function(custom2).build();
		calc.setVariable("x", varX);
		double result = calc.calculate();
		assertTrue(result == (double) Math.log(varX) * (double) Math.E * (double) Math.PI);
	}

	@Test
	public void testCustomFunction6() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] * (double) Math.E;
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] * (double) Math.PI;
			}
		};
		double varX = 32.24979131d;
		Calculable<Double> calc = new ExpressionBuilder("bar(foo(log(x)))", Double.class).variable("x").functions(Arrays.asList(custom1, custom2)).build();
		calc.setVariable("x", varX);
		double result = calc.calculate();
		assertTrue(result == (double) Math.log(varX) * (double) Math.E * (double) Math.PI);
	}

	@Test
	public void testCustomFunction7() throws Exception {
		CustomFunction custom1 = new CustomFunction("half") {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] / 2d;
			}
		};
		Calculable<Double> calc = new ExpressionBuilder("half(x)", Double.class).variable("x").function(custom1).build();
		calc.setVariable("x", 1d);
		assertTrue(0.5d == calc.calculate());
	}

	@Test
	public void testCustomFunction10() throws Exception {
		CustomFunction custom1 = new CustomFunction("max", 2) {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] < (double) values[1] ? (double) values[1] : (double) values[0];
			}
		};
		Calculable<Double> calc = new ExpressionBuilder("max(x,y)", Double.class).variable("x").variable("y").function(custom1).build();
		calc.setVariable("x", 1d);
		calc.setVariable("y", 2d);
		assertTrue(2d == calc.calculate());
	}

	@Test
	public void testCustomFunction11() throws Exception {
		CustomFunction custom1 = new CustomFunction("power", 2) {
			@Override
			public Object apply(Object... values) {
				return (double) Math.pow((double) values[0], (double) values[1]);
			}
		};
		Calculable<Double> calc = new ExpressionBuilder("power(x,y)", Double.class).variable("x").variable("y").function(custom1).build();
		calc.setVariable("x", 2d);
		calc.setVariable("y", 4d);
		assertTrue(Math.pow(2, 4) == calc.calculate());
	}

	@Test
	public void testCustomFunction12() throws Exception {
		CustomFunction custom1 = new CustomFunction("max", 5) {
			@Override
			public Object apply(Object... values) {
				double max = (double) values[0];
				for (int i = 1; i < getArgumentCount(); i++) {
					if ((double) values[i] > max) {
						max = (double) values[i];
					}
				}
				return max;
			}
		};
		Calculable<Double> calc = new ExpressionBuilder("max(1,2.43311,51.13,43,12)", Double.class).function(custom1).build();
		assertTrue(51.13d == calc.calculate());
	}

	@Test
	public void testCustomFunction13() throws Exception {
		CustomFunction custom1 = new CustomFunction("max", 3) {
			@Override
			public Object apply(Object... values) {
				double max = (double) values[0];
				for (int i = 1; i < getArgumentCount(); i++) {
					if ((double) values[i] > max) {
						max = (double) values[i];
					}
				}
				return max;
			}
		};
		double varX = (double) Math.E;
		Calculable<Double> calc = new ExpressionBuilder("max(log(x),sin(x),x)", Double.class).variable("x").function(custom1).build();
		calc.setVariable("x", varX);
		assertTrue(varX == calc.calculate());
	}

	@Test
	public void testCustomFunction14() throws Exception {
		CustomFunction custom1 = new CustomFunction("multiply", 2) {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] * (double) values[1];
			}
		};
		double varX = 1;
		Calculable<Double> calc = new ExpressionBuilder("multiply(sin(x),x+1)", Double.class).variable("x").function(custom1).build();
		calc.setVariable("x", varX);
		double expected = (double) Math.sin(varX) * (varX + 1);
		double actual = calc.calculate();
		assertTrue(expected == actual);
	}

	@Test
	public void testCustomFunction15() throws Exception {
		CustomFunction custom1 = new CustomFunction("timesPi") {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] * (double) Math.PI;
			}
		};
		double varX = 1;
		Calculable<Double> calc = new ExpressionBuilder("timesPi(x^2)", Double.class).variable("x").function(custom1).build();
		double expected = varX * (double) Math.PI;
		calc.setVariable("x", varX);
		double actual = calc.calculate();
		assertTrue(expected == actual);
	}

	@Test
	public void testCustomFunction16() throws Exception {
		CustomFunction custom1 = new CustomFunction("multiply", 3) {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] * (double) values[1] * (double) values[2];
			}
		};
		double varX = 1;
		Calculable<Double> calc = new ExpressionBuilder("multiply(sin(x),x+1^(-2),log(x))", Double.class).variable("x").function(custom1).build();
		calc.setVariable("x", varX);
		double expected = (double) Math.sin(varX) * (double) Math.pow((varX + 1d), -2d) * (double) Math.log(varX);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testCustomFunction17() throws Exception {
		CustomFunction custom1 = new CustomFunction("timesPi") {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] * (double) Math.PI;
			}
		};
		double varX = (double) Math.E;
		Calculable<Double> calc = new ExpressionBuilder("timesPi(log(x^(2+1)))", Double.class).variable("x").function(custom1).build();
		calc.setVariable("x", varX);
		double expected = (double) Math.log((double) Math.pow(varX, 3d)) * (double) Math.PI;
		assertEquals((Double) expected, calc.calculate());
	}

	// thanks to Marcin Domanski who issued
	// http://jira.congrace.de/jira/browse/EXP-11
	// i have this test, which fails in 0.2.9
	@Test
	public void testCustomFunction18() throws Exception {
		CustomFunction minFunction = new CustomFunction("min", 2) {
			@Override
			public Object apply(Object... values) {
				double currentMin = Double.POSITIVE_INFINITY;
				for (Object value : values) {
					currentMin = (double) Math.min(currentMin, (double) value);
				}
				return currentMin;
			}
		};
		ExpressionBuilder b = new ExpressionBuilder("-min(5, 0) + 10", Double.class).function(minFunction);
		double calculated = (double) b.build().calculate();
		assertTrue(calculated == 10);
	}

	// thanks to Sylvain Machefert who issued
	// http://jira.congrace.de/jira/browse/EXP-11
	// i have this test, which fails in 0.3.2
	@Test
	public void testCustomFunction19() throws Exception {
		CustomFunction minFunction = new CustomFunction("power", 2) {
			@Override
			public Object apply(Object... values) {
				return (double) Math.pow((double) values[0], (double) values[1]);
			}
		};
		ExpressionBuilder b = new ExpressionBuilder("power(2,3)", Double.class).function(minFunction);
		double calculated = (double) b.build().calculate();
		assertTrue(calculated == Math.pow(2, 3));
	}

	// thanks to Narendra Harmwal who noticed that getArgumentCount was not
	// implemented
	// this test has been added in 0.3.5
	@Test
	public void testCustomFunction20() throws Exception {
		CustomFunction maxFunction = new CustomFunction("max", 3) {
			@Override
			public Object apply(Object... values) {
				double max = (double) values[0];
				for (int i = 1; i < getArgumentCount(); i++) {
					if ((double) values[i] > max) {
						max = (double) values[i];
					}
				}
				return max;
			}
		};
		ExpressionBuilder b = new ExpressionBuilder("max(1,2,3)", Double.class).function(maxFunction);
		double calculated = (double) b.build().calculate();
		assertTrue(maxFunction.getArgumentCount() == 3);
		assertTrue(calculated == 3);
	}

	@Test
	public void testCustomOperators1() throws Exception {
		CustomOperator factorial = new CustomOperator("!", 6, 1, true) {
			@Override
			public Object apply(Object... args) {
				double tmp = 1d;
				int steps = 1;
				while (steps < (double) args[0]) {
					tmp = tmp * (++steps);
				}
				return tmp;
			}
		};
		Calculable<Double> calc = new ExpressionBuilder("1!", Double.class).operator(factorial).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("2!", Double.class).operator(factorial).build();
		assertTrue(2d == calc.calculate());
		calc = new ExpressionBuilder("3!", Double.class).operator(factorial).build();
		assertTrue(6d == calc.calculate());
		calc = new ExpressionBuilder("4!", Double.class).operator(factorial).build();
		assertTrue(24d == calc.calculate());
		calc = new ExpressionBuilder("5!", Double.class).operator(factorial).build();
		assertTrue(120d == calc.calculate());
		calc = new ExpressionBuilder("11!", Double.class).operator(factorial).build();
		assertTrue(39916800d == calc.calculate());
	}

	@Test
	public void testCustomOperators2() throws Exception {
		CustomOperator factorial = new CustomOperator("!", Operators.PRECEDENCE_EXPONENTATION + 100, 1, true) {
			@Override
			public Object apply(Object... args) {
				double tmp = 1d;
				int steps = 1;
				while (steps < (double) args[0]) {
					tmp = tmp * (++steps);
				}
				return tmp;
			}
		};
		Calculable<Double> calc = new ExpressionBuilder("2^2!", Double.class).operator(factorial).build();
		assertEquals((Double) 4d , calc.calculate());
		calc = new ExpressionBuilder("2!^2", Double.class).operator(factorial).build();
		assertTrue(4d == calc.calculate());
		calc = new ExpressionBuilder("-(3!)^-1", Double.class).operator(factorial).build();
		double actual = calc.calculate();
		assertTrue((double) Math.pow(-6d, -1) == actual);
	}

	@Test
	public void testCustomOperators3() throws Exception {
		CustomOperator goe = new CustomOperator(">=", 4, 2, true) {
			@Override
			public Object apply(Object... values) {
				if ((double) values[0] >= (double) values[1]) {
					return 1d;
				} else {
					return 0d;
				}
			}
		};
		Calculable<Double> calc = new ExpressionBuilder("1>=2", Double.class).operator(goe).build();
		assertTrue(0d == calc.calculate());
		calc = new ExpressionBuilder("2>=1", Double.class).operator(goe).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("-2>=1", Double.class).operator(goe).build();
		assertTrue(0d == calc.calculate());
		calc = new ExpressionBuilder("-2>=-1", Double.class).operator(goe).build();
		assertTrue(0d == calc.calculate());
	}

	@Test
	public void testModulo1() throws Exception {
		double result = (double) new ExpressionBuilder("33%(20/2)%2", Double.class).build().calculate();
		double expected = 33d % (20d / 2d) % 2d;
		assertTrue("exp4j calculated " + result + " instead of " + expected, result == expected);
	}

	@Test
	public void testModulo2() throws Exception {
		double result = (double) new ExpressionBuilder("33%(20/2)", Double.class).build().calculate();
		assertTrue("exp4j calculated " + result, result == 3d);
	}

	@Test
	public void testDivision1() throws Exception {
		double result = (double) new ExpressionBuilder("33/11", Double.class).build().calculate();
		assertTrue("exp4j calculated " + result, result == 3d);
	}

	@Test
	public void testDivision2() throws Exception {
		double result = (double) new ExpressionBuilder("20/10/5", Double.class).build().calculate();
		double expected = 20d / 10d / 5d;
		assertTrue("exp4j calculated " + result + " instead of " + expected, result == expected);
	}

	@Test
	public void testCustomOperators4() throws Exception {
		CustomOperator greaterEq = new CustomOperator(">=", 4, 2, true) {
			@Override
			public Object apply(Object... values) {
				if ((double) values[0] >= (double) values[1]) {
					return 1d;
				} else {
					return 0d;
				}
			}
		};
		CustomOperator greater = new CustomOperator(">", 4, 2, true) {
			@Override
			public Object apply(Object... values) {
				if ((double) values[0] > (double) values[1]) {
					return 1d;
				} else {
					return 0d;
				}
			}
		};
		CustomOperator newPlus = new CustomOperator(">=>", 4, 2, true) {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] + (double) values[1];
			}
		};
		Calculable<Double> calc = new ExpressionBuilder("1>2", Double.class).operator(greater).build();
		assertTrue(0d == calc.calculate());
		calc = new ExpressionBuilder("2>=2", Double.class).operator(greaterEq).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1>=>2", Double.class).operator(newPlus).build();
		assertTrue(3d == calc.calculate());
		calc = new ExpressionBuilder("1>=>2>2", Double.class).operator(greater).operator(newPlus).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1>=>2>2>=1", Double.class).operator(greater).operator(newPlus).operator(greaterEq).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1 >=> 2 > 2 >= 1", Double.class).operator(greater).operator(newPlus).operator(greaterEq).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1 >=> 2 >= 2 > 1", Double.class).operator(greater).operator(newPlus).operator(greaterEq).build();
		assertTrue(0d == calc.calculate());
		calc = new ExpressionBuilder("1 >=> 2 >= 2 > 0", Double.class).operator(greater).operator(newPlus).operator(greaterEq).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1 >=> 2 >= 2 >= 1", Double.class).operator(greater).operator(newPlus).operator(greaterEq).build();
		assertTrue(1d == calc.calculate());
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidOperator1() throws Exception {
		CustomOperator fail = new CustomOperator("2") {
			@Override
			public Object apply(Object... values) {
				return 0;
			}
		};
		new ExpressionBuilder("1", Double.class).operator(fail).build();
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidCustomFunction1() throws Exception {
		CustomFunction func = new CustomFunction("1gd") {
			@Override
			public Object apply(Object... args) {
				return 0;
			}
		};
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidCustomFunction2() throws Exception {
		CustomFunction func = new CustomFunction("+1gd") {
			@Override
			public Object apply(Object... args) {
				return 0;
			}
		};
	}

	@Test
	public void testExpressionBuilder1() throws Exception {
		Calculable<Double> calc = new ExpressionBuilder("7*x + 3*y", Double.class).variables("x", "y").build();
		calc.setVariable("x", 1d);
		calc.setVariable("y", 2d);
		double result = calc.calculate();
		assertTrue(result == 13d);
	}

	@Test
	public void testExpressionBuilder2() throws Exception {
		Calculable<Double> calc = new ExpressionBuilder("7*x + 3*y", Double.class).variables("x", "y").build();
		calc.setVariable("x", 1d);
		calc.setVariable("y", 2d);
		double result = calc.calculate();
		assertTrue(result == 13d);
	}

	@Test
	public void testExpressionBuilder3() throws Exception {
		double varX = 1.3d;
		double varY = 4.22d;
		Calculable<Double> calc = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y", Double.class).variables("x", "y").build();
		calc.setVariable("x", varX);
		calc.setVariable("y", varY);
		double result = calc.calculate();
		assertTrue(result == 7d * varX + 3 * varY - (double) Math.pow((double) Math.log(varY / varX * 12d), varY));
	}

	@Test
	public void testExpressionBuilder4() throws Exception {
		double varX = 1.3d;
		double varY = 4.22d;
		Calculable<Double> calc = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y", Double.class).variables("x", "y").build();
		calc.setVariable("x", varX);
		calc.setVariable("y", varY);
		double result = calc.calculate();
		assertTrue(result == 7d * varX + 3d * varY - (double) Math.pow((double) Math.log(varY / varX * 12d), varY));
		varX = 1.79854d;
		varY = 9281.123d;
		calc.setVariable("x", varX);
		calc.setVariable("y", varY);
		result = calc.calculate();
		assertTrue(result == 7d * varX + 3d * varY - (double) Math.pow((double) Math.log(varY / varX * 12d), varY));
	}

	@Test
	public void testExpressionBuilder5() throws Exception {
		double varY = 4.22d;
		Calculable<Double> calc = new ExpressionBuilder("3*y", Double.class).variables("y").build();
		calc.setVariable("y", varY);
		double result = calc.calculate();
		assertTrue(result == 3d * varY);
	}

	@Test
	public void testExpressionBuilder6() throws Exception {
		double varX = 1.3d;
		double varY = 4.22d;
		double varZ = 4.22d;
		Calculable<Double> calc = new ExpressionBuilder("x * y * z", Double.class).variables("x", "y", "z").build();
		calc.setVariable("x", varX);
		calc.setVariable("y", varY);
		calc.setVariable("z", varZ);
		double result = calc.calculate();
		assertTrue(result == varX * varY * varZ);
	}

	@Test
	public void testExpressionBuilder7() throws Exception {
		double varX = 1.3d;
		Calculable<Double> calc = new ExpressionBuilder("log(sin(x))", Double.class).variable("x").build();
		calc.setVariable("x", varX);
		double result = calc.calculate();
		assertTrue(result == (double) Math.log((double) Math.sin(varX)));
	}

	@Test
	public void testExpressionBuilder8() throws Exception {
		double varX = 1.3d;
		Calculable<Double> calc = new ExpressionBuilder("log(sin(x))", Double.class).variable("x").build();
		calc.setVariable("x", varX);
		double result = calc.calculate();
		assertTrue(result == (double) Math.log((double) Math.sin(varX)));
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testSameName() throws Exception {
		CustomFunction custom = new CustomFunction("bar") {
			@Override
			public Object apply(Object... values) {
				return (double) values[0] / 2d;
			}
		};
		double varBar = 1.3d;
		Calculable<Double> calc = new ExpressionBuilder("f(bar)=bar(bar)", Double.class).variable("bar").function(custom).build();
		double result = calc.calculate();
		assertTrue(result == varBar / 2);
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testInvalidFunction() throws Exception {
		double varY = 4.22d;
		Calculable<Double> calc = new ExpressionBuilder("3*invalid_function(y)", Double.class).variable("y").build();
		calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testMissingVar() throws Exception {
		double varY = 4.22d;
		Calculable<Double> calc = new ExpressionBuilder("3*y*z", Double.class).variable("y").build();
		calc.calculate();
	}

	@Test
	public void testOperatorPrecedence() throws Exception {

		ExpressionBuilder builder = new ExpressionBuilder("1", Double.class);
		Field operatorField = Operators.class.getDeclaredField("builtin");
		operatorField.setAccessible(true);
		Map<Character, CustomOperator> operators = (Map<Character, CustomOperator>) operatorField.get(builder);

		assertTrue(operators.get("+").isLeftAssociative());
		assertTrue(operators.get("*").isLeftAssociative());
		assertTrue(operators.get("-").isLeftAssociative());
		assertTrue(operators.get("/").isLeftAssociative());
		assertTrue(!operators.get("^").isLeftAssociative());

		assertTrue(operators.get("+").getPrecedence() == operators.get("-").getPrecedence());
		assertTrue(operators.get("+").getPrecedence() < operators.get("*").getPrecedence());
		assertTrue(operators.get("+").getPrecedence() < operators.get("/").getPrecedence());
		assertTrue(operators.get("+").getPrecedence() < operators.get("^").getPrecedence());

		assertTrue(operators.get("-").getPrecedence() == operators.get("+").getPrecedence());
		assertTrue(operators.get("-").getPrecedence() < operators.get("*").getPrecedence());
		assertTrue(operators.get("-").getPrecedence() < operators.get("/").getPrecedence());
		assertTrue(operators.get("-").getPrecedence() < operators.get("^").getPrecedence());

		assertTrue(operators.get("*").getPrecedence() > operators.get("+").getPrecedence());
		assertTrue(operators.get("*").getPrecedence() > operators.get("-").getPrecedence());
		assertTrue(operators.get("*").getPrecedence() == operators.get("/").getPrecedence());
		assertTrue(operators.get("*").getPrecedence() < operators.get("^").getPrecedence());

		assertTrue(operators.get("/").getPrecedence() > operators.get("+").getPrecedence());
		assertTrue(operators.get("/").getPrecedence() > operators.get("-").getPrecedence());
		assertTrue(operators.get("/").getPrecedence() == operators.get("*").getPrecedence());
		assertTrue(operators.get("/").getPrecedence() < operators.get("^").getPrecedence());

		assertTrue(operators.get("^").getPrecedence() > operators.get("+").getPrecedence());
		assertTrue(operators.get("^").getPrecedence() > operators.get("-").getPrecedence());
		assertTrue(operators.get("^").getPrecedence() > operators.get("*").getPrecedence());
		assertTrue(operators.get("^").getPrecedence() > operators.get("/").getPrecedence());

	}

	@Test
	public void testExpression1() throws Exception {
		String expr;
		double expected;
		expr = "2 + 4";
		expected = 6d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression10() throws Exception {
		String expr;
		double expected;
		expr = "1 * 1.5 + 1";
		expected = 1 * 1.5 + 1;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression11() throws Exception {
		double x = 1d;
		double y = 2d;
		String expr = "log(x) ^ sin(y)";
		double expected = (double) Math.pow((double) Math.log(x), (double) Math.sin(y));
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x", "y").build();
		calc.setVariable("x", x);
		calc.setVariable("y", y);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression12() throws Exception {
		String expr = "log(2.5333333333)^(0-1)";
		double expected = (double) Math.pow((double) Math.log(2.5333333333d), -1d);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression13() throws Exception {
		String expr = "2.5333333333^(0-1)";
		double expected = (double) Math.pow(2.5333333333d, -1d);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression14() throws Exception {
		String expr = "2 * 17.41 + (12*2)^(0-1)";
		double expected = 2d * 17.41d + (double) Math.pow((12d * 2d), -1d);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression15() throws Exception {
		String expr = "2.5333333333 * 17.41 + (12*2)^log(2.764)";
		double expected = 2.5333333333d * 17.41d + (double) Math.pow((12d * 2d), (double) Math.log(2.764d));
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression16() throws Exception {
		String expr = "2.5333333333/2 * 17.41 + (12*2)^(log(2.764) - sin(5.6664))";
		double expected = 2.5333333333d / 2d * 17.41d + (double) Math.pow((12d * 2d), (double) Math.log(2.764d)
				- (double) Math.sin(5.6664d));
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression17() throws Exception {
		String expr = "x^2 - 2 * y";
		double x = (double) Math.E;
		double y = (double) Math.PI;
		double expected = x * x - 2d * y;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x", "y").build();
		calc.setVariable("x", x);
		calc.setVariable("y", y);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression18() throws Exception {
		String expr = "-3";
		double expected = -3;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression19() throws Exception {
		String expr = "-3 * -24.23";
		double expected = -3d * -24.23d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression2() throws Exception {
		String expr;
		double expected;
		expr = "2+3*4-12";
		expected = 2 + 3 * 4 - 12;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression20() throws Exception {
		String expr = "-2 * 24/log(2) -2";
		double expected = -2d * 24d / (double) Math.log(2d) - 2d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression21() throws Exception {
		String expr = "-2 *33.34/log(x)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2d * 33.34d / (double) Math.pow((double) Math.log(x), -2d) + 14d * 6d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		calc.setVariable("x", x);
		double result = calc.calculate();
		assertTrue("expected " + expected + " but got " + result, expected == result);
	}

	@Test
	public void testExpression21_1() throws Exception {
		String expr = "log(x)^-2";
		double x = 1.334d;
		double expected = (double) Math.pow((double) Math.log(x), -2d);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variable("x").build();
		calc.setVariable("x", x);
		double result = calc.calculate();
		assertTrue("expected " + expected + " but got " + result, expected == result);
	}

	@Test
	public void testExpression22() throws Exception {
		String expr = "-2 *33.34/log(x)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2d * 33.34d / (double) Math.pow((double) Math.log(x), -2d) + 14d * 6d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		calc.setVariable("x", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression23() throws Exception {
		String expr = "-2 *33.34/(log(foo)^-2 + 14 *6) - sin(foo)";
		double x = 1.334d;
		double expected = -2d * 33.34d / ((double) Math.pow((double) Math.log(x), -2d) + 14d * 6d) - (double) Math.sin(x);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("foo").build();
		calc.setVariable("foo", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression24() throws Exception {
		String expr = "3+4-log(23.2)^(2-1) * -1";
		double expected = 3d + 4d - (double) Math.pow((double) Math.log(23.2d), (2d - 1d)) * -1d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression25() throws Exception {
		String expr = "+3+4-+log(23.2)^(2-1) * + 1";
		double expected = 3d + 4d - (double) Math.log(23.2d);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression26() throws Exception {
		String expr = "14 + -(1 / 2.22^3)";
		double expected = 14d + -(1d / (double) Math.pow(2.22d, 3d));
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression27() throws Exception {
		String expr = "12^-+-+-+-+-+-+---2";
		double expected = (double) Math.pow(12d, -2d);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression28() throws Exception {
		String expr = "12^-+-+-+-+-+-+---2 * (-14) / 2 ^ -log(2.22323) ";
		double expected = (double) Math.pow(12d, -2d) * -14d / (double) Math.pow(2d, (double) -Math.log(2.22323d));
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression29() throws Exception {
		String expr = "24.3343 % 3";
		double expected = 24.3343d % 3d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testVarname1() throws Exception {
		String expr = "12.23 * foo.bar";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variable("foo.bar").build();
		assertTrue(12.23d == calc.calculate());
	}

	public void testVarname2() throws Exception {
		String expr = "12.23 * foo.bar";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variable("_foo").build();
		assertTrue(12.23d == calc.calculate());
	}

	@Test
	public void testExpression3() throws Exception {
		String expr;
		double expected;
		expr = "2+4*5";
		expected = 2d + 4d * 5d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression30() throws Exception {
		String expr = "24.3343 % 3 * 20 ^ -(2.334 % log(2 / 14))";
		double expected = 24.3343d % 3d * (double) Math.pow(20d, -(2.334d % (double) Math.log(2d / 14d)));
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression31() throws Exception {
		String expr = "-2 *33.34/log(y_x)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2d * 33.34d / (double) Math.pow((double) Math.log(x), -2d) + 14d * 6d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("y_x").build();
		calc.setVariable("y_x", x);
		double actual = calc.calculate();
		assertTrue(expected == actual);
	}

	@Test
	public void testExpression32() throws Exception {
		String expr = "-2 *33.34/log(y_2x)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2d * 33.34d / (double) Math.pow((double) Math.log(x), -2d) + 14d * 6d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("y_2x").build();
		calc.setVariable("y_2x", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression33() throws Exception {
		String expr = "-2 *33.34/log(_y)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2d * 33.34d / (double) Math.pow((double) Math.log(x), -2d) + 14d * 6d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("_y").build();
		calc.setVariable("_y", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression34() throws Exception {
		String expr = "-2 + + (+4) +(4)";
		double expected = -2 + 4 + 4;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	@Ignore
	public void testExpression40() throws Exception {
		String expr = "1e1";
		double expected = 10d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	@Ignore
	public void testExpression41() throws Exception {
		String expr = "1e-1";
		double expected = 0.1d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	/**
	 * Added tests for expressions with scientific notation see
	 * http://jira.congrace.de/jira/browse/EXP-17
	 * 
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testExpression42() throws Exception {
		String expr = "7.2973525698e-3";
		double expected = 7.2973525698d * (double) Math.pow(10d, -3d);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	@Ignore
	public void testExpression43() throws Exception {
		String expr = "6.02214E23";
		double expected = 6.02214d * (double) Math.pow(10d, 23d);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	@Ignore
	public void testExpression44() throws Exception {
		String expr = "6.02214E23";
		double expected = 6.02214d * (double) Math.pow(10d, 23d);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test(expected = RuntimeException.class)
	@Ignore
	public void testExpression45() throws Exception {
		String expr = "6.02214E2E3";
		new ExpressionBuilder(expr, Double.class).build();
	}

	@Test(expected = RuntimeException.class)
	@Ignore
	public void testExpression46() throws Exception {
		String expr = "6.02214e2E3";
		new ExpressionBuilder(expr, Double.class).build();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression47() throws Exception {
		String expr = "6.02214y3";
		new ExpressionBuilder(expr, Double.class).build();
	}

	// tests for EXP-20: No exception is thrown for unmatched parenthesis in
	// build
	// Thanks go out to maheshkurmi for reporting
	@Test(expected = UnparseableExpressionException.class)
	public void testExpression48() throws Exception {
		String expr = "(1*2";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		double result = calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression49() throws Exception {
		String expr = "{1*2";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		double result = calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression50() throws Exception {
		String expr = "[1*2";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		double result = calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression51() throws Exception {
		String expr = "(1*{2+[3}";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		double result = calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression52() throws Exception {
		String expr = "(1*(2+(3";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		double result = calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression53() throws Exception {
		String expr = "14 * 2x";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		assertTrue(56d == calc.calculate());
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression54() throws Exception {
		String expr = "2 ((-(x)))";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		assertTrue(-4d == calc.calculate());
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression55() throws Exception {
		String expr = "2 sin(x)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		assertTrue(Math.sin(2d) * 2 == calc.calculate());
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression56() throws Exception {
		String expr = "2 sin(3x)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		assertTrue(Math.sin(6d) * 2 == calc.calculate());
	}

	// Thanks go out to Johan Björk for reporting the division by zero problem
	// EXP-22
	// https://www.objecthunter.net/jira/browse/EXP-22
	@Test(expected = ArithmeticException.class)
	public void testExpression57() throws Exception {
		String expr = "1 / 0";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(Double.POSITIVE_INFINITY == calc.calculate());
	}

	@Test
	public void testExpression58() throws Exception {
		String expr = "17 * sqrt(-1) * 12";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(Double.isNaN(calc.calculate()));
	}

	// Thanks go out to Alex Dolinsky for reporting the missing exception when
	// an empty
	// expression is passed as in new ExpressionBuilder("")
	@Test(expected = IllegalArgumentException.class)
	public void testExpression59() throws Exception {
		Calculable<Double> calc = new ExpressionBuilder("", Double.class).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExpression60() throws Exception {
		Calculable<Double> calc = new ExpressionBuilder("   ", Double.class).build();
		calc.calculate();
	}

	@Test(expected = ArithmeticException.class)
	public void testExpression61() throws Exception {
		Calculable<Double> calc = new ExpressionBuilder("14 % 0", Double.class).build();
		calc.calculate();
	}

	// https://www.objecthunter.net/jira/browse/EXP-24
	// thanks go out to Rémi for the issue report
	@Test
	@Ignore
	public void testExpression62() throws Exception {
		Calculable<Double> calc = new ExpressionBuilder("x*1.0e5+5", Double.class).variables("x").build();
		assertTrue(Math.E * 1.0 * Math.pow(10, 5) + 5 == calc.calculate());
	}

	// thanks go out to Janny for providing the tests and the bug report
	@Test
	public void testUnaryMinusInParenthesisSpace() throws Exception {
		ExpressionBuilder b = new ExpressionBuilder("( -1)^2", Double.class);
		double calculated = (double) b.build().calculate();
		assertTrue(calculated == 1d);
	}

	@Test
	public void testUnaryMinusSpace() throws Exception {
		ExpressionBuilder b = new ExpressionBuilder(" -1 + 2", Double.class);
		double calculated = (double) b.build().calculate();
		assertTrue(calculated == 1d);
	}

	@Test
	public void testUnaryMinusSpaces() throws Exception {
		ExpressionBuilder b = new ExpressionBuilder(" -1 + + 2 +   -   1", Double.class);
		double calculated = (double) b.build().calculate();
		assertTrue(calculated == 0d);
	}

	@Test
	public void testUnaryMinusSpace1() throws Exception {
		ExpressionBuilder b = new ExpressionBuilder("-1", Double.class);
		double calculated = (double) b.build().calculate();
		assertTrue(calculated == -1d);
	}

	@Test
	public void testExpression4() throws Exception {
		String expr;
		double expected;
		expr = "2+4 * 5";
		expected = 2 + 4 * 5;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	// test for https://www.objecthunter.net/jira/browse/EXP-19
	// thanks go out to Yunior Peralta for the report
	@Test
	public void testCharacterPositionInException1() throws Exception {
		String expr;
		expr = "2 + sn(4)";
		try {
			Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
			Assert.fail("Expression was parsed but should throw an Exception");
		} catch (UnparseableExpressionException e) {
			String expected = "Unable to parse name 'sn' in expression '2 + sn(4)'";
			assertEquals(expected, e.getMessage());
		}
	}

	@Test
	public void testExpression5() throws Exception {
		String expr;
		double expected;
		expr = "(2+4)*5";
		expected = (2 + 4) * 5;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression6() throws Exception {
		String expr;
		double expected;
		expr = "(2+4)*5 + 2.5*2";
		expected = (2 + 4) * 5 + 2.5 * 2;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression7() throws Exception {
		String expr;
		double expected;
		expr = "(2+4)*5 + 10/2";
		expected = (2 + 4) * 5 + 10 / 2;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression8() throws Exception {
		String expr;
		double expected;
		expr = "(2 * 3 +4)*5 + 10/2";
		expected = (2 * 3 + 4) * 5 + 10 / 2;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression9() throws Exception {
		String expr;
		double expected;
		expr = "(2 * 3 +4)*5 +4 + 10/2";
		expected = (2 * 3 + 4) * 5 + 4 + 10 / 2;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testFailUnknownFunction1() throws Exception {
		String expr;
		expr = "lig(1)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testFailUnknownFunction2() throws Exception {
		String expr;
		expr = "galength(1)";
		new ExpressionBuilder(expr, Double.class).build().calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testFailUnknownFunction3() throws Exception {
		String expr;
		expr = "f(cos) = cos(cos)";
		new ExpressionBuilder(expr, Double.class).build().calculate();
	}

	@Test
	public void testFunction1() throws Exception {
		String expr;
		expr = "cos(cos_1)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("cos_1").build();
		calc.setVariable("cos_1", 1d);
		assertTrue(calc.calculate() == (double) Math.cos(1d));
	}

	@Test
	public void testPostfix1() throws Exception {
		String expr;
		double expected;
		expr = "2.2232^0.1";
		expected = (double) Math.pow(2.2232d, 0.1d);
		double actual = (double) new ExpressionBuilder(expr, Double.class).build().calculate();
		assertTrue(expected == actual);
	}

	@Test
	public void testPostfixEverything() throws Exception {
		String expr;
		double expected;
		expr = "(sin(12) + log(34)) * 3.42 - cos(2.234-log(2))";
		expected = (double) (Math.sin(12d) + (double) Math.log(34)) * 3.42d 				- (double) Math.cos(2.234d - (double) Math.log(2d));
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixExponentation1() throws Exception {
		String expr;
		double expected;
		expr = "2^3";
		expected = (double) Math.pow(2d, 3d);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixExponentation2() throws Exception {
		String expr;
		double expected;
		expr = "24 + 4 * 2^3";
		expected = 24d + 4d * (double) Math.pow(2d, 3d);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixExponentation3() throws Exception {
		String expr;
		double expected;
		double x = 4.334d;
		expr = "24 + 4 * 2^x";
		expected = 24d + 4d * (double) Math.pow(2d, x);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		calc.setVariable("x", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixExponentation4() throws Exception {
		String expr;
		double expected;
		double x = 4.334d;
		expr = "(24 + 4) * 2^log(x)";
		expected = (24d + 4d) * (double) Math.pow(2d, (double) Math.log(x));
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		calc.setVariable("x", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction1() throws Exception {
		String expr;
		double expected;
		expr = "log(1) * sin(0)";
		expected = (double) Math.log(1d) * (double) Math.sin(0d);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction10() throws Exception {
		String expr;
		double expected;
		expr = "cbrt(x)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = (double) Math.cbrt(x);
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction11() throws Exception {
		String expr;
		double expected;
		expr = "cos(x) - (1/cbrt(x))";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			if (x == 0d)
				continue;
			expected = (double) Math.cos(x) - (1d / (double) Math.cbrt(x));
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction12() throws Exception {
		String expr;
		double expected;
		expr = "acos(x) * expm1(asin(x)) - exp(atan(x)) + floor(x) + cosh(x) - sinh(cbrt(x))";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = (double) Math.acos(x) * (double) Math.expm1((double) Math.asin(x))
					- (double) Math.exp((double) Math.atan(x)) + (double) Math.floor(x) + (double) Math.cosh(x)
					- (double) Math.sinh((double) Math.cbrt(x));
			calc.setVariable("x", x);
			if (Double.isNaN(expected)) {
				assertTrue(Double.isNaN(calc.calculate()));
			} else {
				assertTrue(expected == calc.calculate());
			}
		}
	}

	@Test
	public void testPostfixFunction13() throws Exception {
		String expr;
		double expected;
		expr = "acos(x)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = (double) Math.acos(x);
			calc.setVariable("x", x);
			if (Double.isNaN(expected)) {
				assertTrue(Double.isNaN(calc.calculate()));
			} else {
				assertTrue(expected == calc.calculate());
			}
		}
	}

	@Test
	public void testPostfixFunction14() throws Exception {
		String expr;
		double expected;
		expr = " expm1(x)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = (double) Math.expm1(x);
			calc.setVariable("x", x);
			if (Double.isNaN(expected)) {
				assertTrue(Double.isNaN(calc.calculate()));
			} else {
				assertTrue(expected == calc.calculate());
			}
		}
	}

	@Test
	public void testPostfixFunction15() throws Exception {
		String expr;
		double expected;
		expr = "asin(x)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = (double) Math.asin(x);
			calc.setVariable("x", x);
			if (Double.isNaN(expected)) {
				assertTrue(Double.isNaN(calc.calculate()));
			} else {
				assertTrue(expected == calc.calculate());
			}
		}
	}

	@Test
	public void testPostfixFunction16() throws Exception {
		String expr;
		double expected;
		expr = " exp(x)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = (double) Math.exp(x);
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction17() throws Exception {
		String expr;
		double expected;
		expr = "floor(x)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = (double) Math.floor(x);
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction18() throws Exception {
		String expr;
		double expected;
		expr = " cosh(x)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = (double) Math.cosh(x);
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction19() throws Exception {
		String expr;
		double expected;
		expr = "sinh(x)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = (double) Math.sinh(x);
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction20() throws Exception {
		String expr;
		double expected;
		expr = "cbrt(x)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = (double) Math.cbrt(x);
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction21() throws Exception {
		String expr;
		double expected;
		expr = "tanh(x)";
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = (double) Math.tanh(x);
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction2() throws Exception {
		String expr;
		double expected;
		expr = "log(1)";
		expected = 0d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction3() throws Exception {
		String expr;
		double expected;
		expr = "sin(0)";
		expected = 0d;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction5() throws Exception {
		String expr;
		double expected;
		expr = "ceil(2.3) +1";
		expected = Math.ceil(2.3) + 1;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction6() throws Exception {
		String expr;
		double expected;
		double x = 1.565d;
		double y = 2.1323d;
		expr = "ceil(x) + 1  / y * abs(1.4)";
		expected = (double) Math.ceil(x) + (double) 1d / y * (double) Math.abs(1.4d);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x", "y").build();
		calc.setVariable("x", x);
		calc.setVariable("y", y);
		double actual = calc.calculate();
		assertEquals((Double) expected, (Double) actual);
	}

	@Test
	public void testPostfixFunction7() throws Exception {
		String expr;
		double expected;
		double x = (double) Math.E;
		expr = "tan(x)";
		expected = (double) Math.tan(x);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		calc.setVariable("x", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction8() throws Exception {
		String expr;
		double expected;
		double e = (double) Math.E;
		expr = "2^3.4223232 + tan(e)";
		expected = (double) Math.pow(2d, 3.4223232d) + (double) Math.tan((double) Math.E);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("e").build();
		calc.setVariable("e", e);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction9() throws Exception {
		String expr;
		double expected;
		double x = (double) Math.E;
		expr = "cbrt(x)";
		expected = (double) Math.cbrt(x);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x").build();
		calc.setVariable("x", x);
		assertTrue(expected == calc.calculate());
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testPostfixInvalidVariableName() throws Exception {
		String expr;
		double expected;
		double x = 4.5334332d;
		double log = Math.PI;
		expr = "x * pi";
		expected = x * log;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x", "log").build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixParanthesis() throws Exception {
		String expr;
		double expected;
		expr = "(3 + 3 * 14) * (2 * (24-17) - 14)/((34) -2)";
		expected = (3 + 3 * 14) * (2 * (24 - 17) - 14) / ((34) - 2);
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixVariables() throws Exception {
		String expr;
		double expected;
		double x = 4.5334332d;
		double pi = (double) Math.PI;
		expr = "x * pi";
		expected = x * pi;
		Calculable<Double> calc = new ExpressionBuilder(expr, Double.class).variables("x", "pi").build();
		calc.setVariable("x", x);
		calc.setVariable("pi", pi);
		assertTrue(expected == calc.calculate());
	}
}
