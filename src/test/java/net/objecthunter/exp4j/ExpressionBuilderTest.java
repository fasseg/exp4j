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
public class ExpressionBuilderTest {

	@Test
	public void testFloatExpression1() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2+2", Float.class);
		float result = e.build().calculate();
		assertTrue(4f == result);
	}

	@Test
	public void testFloatExpression2() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2-2", Float.class);
		float result = e.build().calculate();
		assertTrue(0f == result);
	}

	@Test
	public void testFloatExpression3() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2/2", Float.class);
		float result = e.build().calculate();
		assertTrue(1f == result);
	}

	@Test
	public void testFloatExpression4() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2.45-1", Float.class);
		float result = e.build().calculate();
		assertTrue(1.45f == result);
	}

	@Test
	public void testFloatExpression5() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2.45-3", Float.class);
		float result = e.build().calculate();
		float expected = 2.45f - 3f;
		assertTrue("exp4j calulated " + result, expected == result);
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testFloatExpression6() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2.45(-3", Float.class);
		float result = e.build().calculate();
	}

	@Test(expected = RuntimeException.class)
	public void testFloatExpression7() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2.45)-3", Float.class);
		float result = e.build().calculate();
	}

	@Test
	public void testFloatExpression8() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("sin(1.0)", Float.class);
		float result = e.build().calculate();
		float expected = (float) Math.sin(1.0);
		assertTrue("exp4j calulated " + result, expected == result);
	}

	@Test
	public void testFloatExpression9() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("sin(1.0) * 1 + 1", Float.class);
		float result = e.build().calculate();
		float expected = (float) Math.sin(1.0) * 1 + 1;
		assertTrue("exp4j calulated " + result, expected == result);
	}

	@Test
	public void testFloatExpression10() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("-1", Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, -1f == result);
	}

	@Test
	public void testFloatExpression11() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("7+-1", Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, 6f == result);
	}

	@Test
	public void testFloatExpression12() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("7+--1", Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, 8f == result);
	}

	@Test
	public void testFloatExpression13() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("7++1", Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, 8f == result);
	}

	@Test
	public void testFloatExpression14() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("sin(-1.0)", Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, (float) Math.sin(-1.0f) == result);
	}

	@Test
	public void testFloatExpression15() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("log(4) - 3 * (sqrt(3^cos(2)))", Float.class);
		float result = e.build().calculate();
		float expected = (float) Math.log(4f) - 3f * (float) (Math.sqrt((float) Math.pow(3f, (float) Math.cos(2f))));
		assertTrue("exp4j calulated " + result + " but expected was " + expected, expected == result);
	}

	@Test
	public void testFloatExpression16() throws Exception {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("sqrt(3^cos(2))", Float.class);
		float result = e.build().calculate();
		float expected = (float) Math.sqrt((float) Math.pow(3f, (float) Math.cos(2f)));
		assertTrue("exp4j calulated " + result + " but expected was " + expected, expected == result);
	}

	/* Tests from exp4j v1 follow, these should of course pass */
	@Test
	public void testCustomFunction1() throws Exception {
		CustomFunction custom = new CustomFunction("timespi") {
			@Override
			public Object apply(Object... args) {
				return (float) args[0] * (float) Math.PI;
			}
		};
		Calculable<Float> calc = new ExpressionBuilder("timespi(x)", Float.class).variable("x").function(custom).build();
		calc.setVariable("x", 1f);
		float result = calc.calculate();
		assertTrue(result == (float) Math.PI);
	}

	@Test
	public void testCustomFunction2() throws Exception {
		CustomFunction custom = new CustomFunction("loglog") {
			@Override
			public Object apply(Object... values) {
				return (float) Math.log(Math.log(((Float) values[0]).doubleValue()));
			}
		};
		Calculable<Float> calc = new ExpressionBuilder("loglog(x)", Float.class).variable("x").function(custom).build();
		calc.setVariable("x", 1f);
		float result = calc.calculate();
		assertTrue(result == Math.log(Math.log(1)));
	}

	@Test
	public void testCustomFunction3() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] * (float) Math.E;
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] * (float) Math.PI;
			}
		};
		Calculable<Float> calc = new ExpressionBuilder("foo(bar(x))", Float.class).variable("x").function(custom1).function(custom2).build();
		calc.setVariable("x", 1f);
		float result = calc.calculate();
		assertTrue(result == 1f * (float) Math.E * (float) Math.PI);
	}

	@Test
	public void testCustomFunction4() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] * (float) Math.E;
			}
		};
		float varX = 32.24979131f;
		Calculable<Float> calc = new ExpressionBuilder("foo(log(x))", Float.class).variable("x").function(custom1).build();
		float result = calc.setVariable("x", varX).calculate();
		assertTrue(result == (float) Math.log(varX) * (float) Math.E);
	}

	@Test
	public void testCustomFunction5() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] * (float) Math.E;
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] * (float) Math.PI;
			}
		};
		float varX = 32.24979131f;
		Calculable<Float> calc = new ExpressionBuilder("bar(foo(log(x)))", Float.class).variable("x").function(custom1).function(custom2).build();
		calc.setVariable("x", varX);
		float result = calc.calculate();
		assertTrue(result == (float) Math.log(varX) * (float) Math.E * (float) Math.PI);
	}

	@Test
	public void testCustomFunction6() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] * (float) Math.E;
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] * (float) Math.PI;
			}
		};
		float varX = 32.24979131f;
		Calculable<Float> calc = new ExpressionBuilder("bar(foo(log(x)))", Float.class).variable("x").functions(Arrays.asList(custom1, custom2)).build();
		calc.setVariable("x", varX);
		float result = calc.calculate();
		assertTrue(result == (float) Math.log(varX) * (float) Math.E * (float) Math.PI);
	}

	@Test
	public void testCustomFunction7() throws Exception {
		CustomFunction custom1 = new CustomFunction("half") {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] / 2f;
			}
		};
		Calculable<Float> calc = new ExpressionBuilder("half(x)", Float.class).variable("x").function(custom1).build();
		calc.setVariable("x", 1f);
		assertTrue(0.5d == calc.calculate());
	}

	@Test
	public void testCustomFunction10() throws Exception {
		CustomFunction custom1 = new CustomFunction("max", 2) {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] < (float) values[1] ? (float) values[1] : (float) values[0];
			}
		};
		Calculable<Float> calc = new ExpressionBuilder("max(x,y)", Float.class).variable("x").variable("y").function(custom1).build();
		calc.setVariable("x", 1f);
		calc.setVariable("y", 2f);
		assertTrue(2f == calc.calculate());
	}

	@Test
	public void testCustomFunction11() throws Exception {
		CustomFunction custom1 = new CustomFunction("power", 2) {
			@Override
			public Object apply(Object... values) {
				return (float) Math.pow((float) values[0], (float) values[1]);
			}
		};
		Calculable<Float> calc = new ExpressionBuilder("power(x,y)", Float.class).variable("x").variable("y").function(custom1).build();
		calc.setVariable("x", 2f);
		calc.setVariable("y", 4f);
		assertTrue(Math.pow(2, 4) == calc.calculate());
	}

	@Test
	public void testCustomFunction12() throws Exception {
		CustomFunction custom1 = new CustomFunction("max", 5) {
			@Override
			public Object apply(Object... values) {
				float max = (float) values[0];
				for (int i = 1; i < getArgumentCount(); i++) {
					if ((float) values[i] > max) {
						max = (float) values[i];
					}
				}
				return max;
			}
		};
		Calculable<Float> calc = new ExpressionBuilder("max(1,2.43311,51.13,43,12)", Float.class).function(custom1).build();
		assertTrue(51.13f == calc.calculate());
	}

	@Test
	public void testCustomFunction13() throws Exception {
		CustomFunction custom1 = new CustomFunction("max", 3) {
			@Override
			public Object apply(Object... values) {
				float max = (float) values[0];
				for (int i = 1; i < getArgumentCount(); i++) {
					if ((float) values[i] > max) {
						max = (float) values[i];
					}
				}
				return max;
			}
		};
		float varX = (float) Math.E;
		Calculable<Float> calc = new ExpressionBuilder("max(log(x),sin(x),x)", Float.class).variable("x").function(custom1).build();
		calc.setVariable("x", varX);
		assertTrue(varX == calc.calculate());
	}

	@Test
	public void testCustomFunction14() throws Exception {
		CustomFunction custom1 = new CustomFunction("multiply", 2) {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] * (float) values[1];
			}
		};
		float varX = 1;
		Calculable<Float> calc = new ExpressionBuilder("multiply(sin(x),x+1)", Float.class).variable("x").function(custom1).build();
		calc.setVariable("x", varX);
		float expected = (float) Math.sin(varX) * (varX + 1);
		float actual = calc.calculate();
		assertTrue(expected == actual);
	}

	@Test
	public void testCustomFunction15() throws Exception {
		CustomFunction custom1 = new CustomFunction("timesPi") {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] * (float) Math.PI;
			}
		};
		float varX = 1;
		Calculable<Float> calc = new ExpressionBuilder("timesPi(x^2)", Float.class).variable("x").function(custom1).build();
		float expected = varX * (float) Math.PI;
		calc.setVariable("x", varX);
		float actual = calc.calculate();
		assertTrue(expected == actual);
	}

	@Test
	public void testCustomFunction16() throws Exception {
		CustomFunction custom1 = new CustomFunction("multiply", 3) {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] * (float) values[1] * (float) values[2];
			}
		};
		float varX = 1;
		Calculable<Float> calc = new ExpressionBuilder("multiply(sin(x),x+1^(-2),log(x))", Float.class).variable("x").function(custom1).build();
		calc.setVariable("x", varX);
		float expected = (float) Math.sin(varX) * (float) Math.pow((varX + 1f), -2f) * (float) Math.log(varX);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testCustomFunction17() throws Exception {
		CustomFunction custom1 = new CustomFunction("timesPi") {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] * (float) Math.PI;
			}
		};
		float varX = (float) Math.E;
		Calculable<Float> calc = new ExpressionBuilder("timesPi(log(x^(2+1)))", Float.class).variable("x").function(custom1).build();
		calc.setVariable("x", varX);
		float expected = (float) Math.log((float) Math.pow(varX, 3)) * (float) Math.PI;
		assertTrue(expected == calc.calculate());
	}

	// thanks to Marcin Domanski who issued
	// http://jira.congrace.de/jira/browse/EXP-11
	// i have this test, which fails in 0.2.9
	@Test
	public void testCustomFunction18() throws Exception {
		CustomFunction minFunction = new CustomFunction("min", 2) {
			@Override
			public Object apply(Object... values) {
				float currentMin = Float.POSITIVE_INFINITY;
				for (Object value : values) {
					currentMin = (float) Math.min(currentMin, (float) value);
				}
				return currentMin;
			}
		};
		ExpressionBuilder b = new ExpressionBuilder("-min(5, 0) + 10", Float.class).function(minFunction);
		float calculated = (float) b.build().calculate();
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
				return (float) Math.pow((float) values[0], (float) values[1]);
			}
		};
		ExpressionBuilder b = new ExpressionBuilder("power(2,3)", Float.class).function(minFunction);
		double calculated = (float) b.build().calculate();
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
				float max = (float) values[0];
				for (int i = 1; i < getArgumentCount(); i++) {
					if ((float) values[i] > max) {
						max = (float) values[i];
					}
				}
				return max;
			}
		};
		ExpressionBuilder b = new ExpressionBuilder("max(1,2,3)", Float.class).function(maxFunction);
		double calculated = (float) b.build().calculate();
		assertTrue(maxFunction.getArgumentCount() == 3);
		assertTrue(calculated == 3);
	}

	@Test
	public void testCustomOperators1() throws Exception {
		CustomOperator factorial = new CustomOperator("!", 6, 1, true) {
			@Override
			public Object apply(Object... args) {
				float tmp = 1f;
				int steps = 1;
				while (steps < (float) args[0]) {
					tmp = tmp * (++steps);
				}
				return tmp;
			}
		};
		Calculable<Float> calc = new ExpressionBuilder("1!", Float.class).operator(factorial).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("2!", Float.class).operator(factorial).build();
		assertTrue(2d == calc.calculate());
		calc = new ExpressionBuilder("3!", Float.class).operator(factorial).build();
		assertTrue(6d == calc.calculate());
		calc = new ExpressionBuilder("4!", Float.class).operator(factorial).build();
		assertTrue(24d == calc.calculate());
		calc = new ExpressionBuilder("5!", Float.class).operator(factorial).build();
		assertTrue(120d == calc.calculate());
		calc = new ExpressionBuilder("11!", Float.class).operator(factorial).build();
		assertTrue(39916800d == calc.calculate());
	}

	@Test
	public void testCustomOperators2() throws Exception {
		CustomOperator factorial = new CustomOperator("!", 6, 1, true) {
			@Override
			public Object apply(Object... args) {
				float tmp = 1f;
				int steps = 1;
				while (steps < (float) args[0]) {
					tmp = tmp * (++steps);
				}
				return tmp;
			}
		};
		Calculable<Float> calc = new ExpressionBuilder("2^2!", Float.class).operator(factorial).build();
		assertTrue(4f == calc.calculate());
		calc = new ExpressionBuilder("2!^2", Float.class).operator(factorial).build();
		assertTrue(4f == calc.calculate());
		calc = new ExpressionBuilder("-(3!)^-1", Float.class).operator(factorial).build();
		float actual = calc.calculate();
		assertTrue((float) Math.pow(-6f, -1) == actual);
	}

	@Test
	public void testCustomOperators3() throws Exception {
		CustomOperator goe = new CustomOperator(">=", 4, 2, true) {
			@Override
			public Object apply(Object... values) {
				if ((float) values[0] >= (float) values[1]) {
					return 1f;
				} else {
					return 0f;
				}
			}
		};
		Calculable<Float> calc = new ExpressionBuilder("1>=2", Float.class).operator(goe).build();
		assertTrue(0f == calc.calculate());
		calc = new ExpressionBuilder("2>=1", Float.class).operator(goe).build();
		assertTrue(1f == calc.calculate());
		calc = new ExpressionBuilder("-2>=1", Float.class).operator(goe).build();
		assertTrue(0f == calc.calculate());
		calc = new ExpressionBuilder("-2>=-1", Float.class).operator(goe).build();
		assertTrue(0f == calc.calculate());
	}

	@Test
	public void testModulo1() throws Exception {
		float result = (float) new ExpressionBuilder("33%(20/2)%2", Float.class).build().calculate();
		float expected = 33f % (20f / 2f) % 2f;
		assertTrue("exp4j calculated " + result + " instead of " + expected, result == expected);
	}

	@Test
	public void testModulo2() throws Exception {
		float result = (float) new ExpressionBuilder("33%(20/2)", Float.class).build().calculate();
		assertTrue("exp4j calculated " + result, result == 3f);
	}

	@Test
	public void testDivision1() throws Exception {
		float result = (float) new ExpressionBuilder("33/11", Float.class).build().calculate();
		assertTrue("exp4j calculated " + result, result == 3f);
	}

	@Test
	public void testDivision2() throws Exception {
		float result = (float) new ExpressionBuilder("20/10/5", Float.class).build().calculate();
		float expected = 20f / 10f / 5f;
		assertTrue("exp4j calculated " + result + " instead of " + expected, result == expected);
	}

	@Test
	public void testCustomOperators4() throws Exception {
		CustomOperator greaterEq = new CustomOperator(">=", 4, 2, true) {
			@Override
			public Object apply(Object... values) {
				if ((float) values[0] >= (float) values[1]) {
					return 1f;
				} else {
					return 0f;
				}
			}
		};
		CustomOperator greater = new CustomOperator(">", 4, 2, true) {
			@Override
			public Object apply(Object... values) {
				if ((float) values[0] > (float) values[1]) {
					return 1f;
				} else {
					return 0f;
				}
			}
		};
		CustomOperator newPlus = new CustomOperator(">=>", 4, 2, true) {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] + (float) values[1];
			}
		};
		Calculable<Float> calc = new ExpressionBuilder("1>2", Float.class).operator(greater).build();
		assertTrue(0d == calc.calculate());
		calc = new ExpressionBuilder("2>=2", Float.class).operator(greaterEq).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1>=>2", Float.class).operator(newPlus).build();
		assertTrue(3d == calc.calculate());
		calc = new ExpressionBuilder("1>=>2>2", Float.class).operator(greater).operator(newPlus).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1>=>2>2>=1", Float.class).operator(greater).operator(newPlus).operator(greaterEq).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1 >=> 2 > 2 >= 1", Float.class).operator(greater).operator(newPlus).operator(greaterEq).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1 >=> 2 >= 2 > 1", Float.class).operator(greater).operator(newPlus).operator(greaterEq).build();
		assertTrue(0d == calc.calculate());
		calc = new ExpressionBuilder("1 >=> 2 >= 2 > 0", Float.class).operator(greater).operator(newPlus).operator(greaterEq).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1 >=> 2 >= 2 >= 1", Float.class).operator(greater).operator(newPlus).operator(greaterEq).build();
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
		new ExpressionBuilder("1", Float.class).operator(fail).build();
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
		Calculable<Float> calc = new ExpressionBuilder("7*x + 3*y", Float.class).variables("x", "y").build();
		calc.setVariable("x", 1f);
		calc.setVariable("y", 2f);
		float result = calc.calculate();
		assertTrue(result == 13d);
	}

	@Test
	public void testExpressionBuilder2() throws Exception {
		Calculable<Float> calc = new ExpressionBuilder("7*x + 3*y", Float.class).variables("x", "y").build();
		calc.setVariable("x", 1f);
		calc.setVariable("y", 2f);
		float result = calc.calculate();
		assertTrue(result == 13d);
	}

	@Test
	public void testExpressionBuilder3() throws Exception {
		float varX = 1.3f;
		float varY = 4.22f;
		Calculable<Float> calc = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y", Float.class).variables("x", "y").build();
		calc.setVariable("x", varX);
		calc.setVariable("y", varY);
		float result = calc.calculate();
		assertTrue(result == 7f * varX + 3 * varY - (float) Math.pow((float) Math.log(varY / varX * 12f), varY));
	}

	@Test
	public void testExpressionBuilder4() throws Exception {
		float varX = 1.3f;
		float varY = 4.22f;
		Calculable<Float> calc = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y", Float.class).variables("x", "y").build();
		calc.setVariable("x", varX);
		calc.setVariable("y", varY);
		float result = calc.calculate();
		assertTrue(result == 7f * varX + 3f * varY - (float) Math.pow((float) Math.log(varY / varX * 12f), varY));
		varX = 1.79854f;
		varY = 9281.123f;
		calc.setVariable("x", varX);
		calc.setVariable("y", varY);
		result = calc.calculate();
		assertTrue(result == 7f * varX + 3f * varY - (float) Math.pow((float) Math.log(varY / varX * 12f), varY));
	}

	@Test
	public void testExpressionBuilder5() throws Exception {
		float varY = 4.22f;
		Calculable<Float> calc = new ExpressionBuilder("3*y", Float.class).variables("y").build();
		calc.setVariable("y", varY);
		float result = calc.calculate();
		assertTrue(result == 3f * varY);
	}

	@Test
	public void testExpressionBuilder6() throws Exception {
		float varX = 1.3f;
		float varY = 4.22f;
		float varZ = 4.22f;
		Calculable<Float> calc = new ExpressionBuilder("x * y * z", Float.class).variables("x", "y", "z").build();
		calc.setVariable("x", varX);
		calc.setVariable("y", varY);
		calc.setVariable("z", varZ);
		float result = calc.calculate();
		assertTrue(result == varX * varY * varZ);
	}

	@Test
	public void testExpressionBuilder7() throws Exception {
		float varX = 1.3f;
		Calculable<Float> calc = new ExpressionBuilder("log(sin(x))", Float.class).variable("x").build();
		calc.setVariable("x", varX);
		float result = calc.calculate();
		assertTrue(result == (float) Math.log((float) Math.sin(varX)));
	}

	@Test
	public void testExpressionBuilder8() throws Exception {
		float varX = 1.3f;
		Calculable<Float> calc = new ExpressionBuilder("log(sin(x))", Float.class).variable("x").build();
		calc.setVariable("x", varX);
		float result = calc.calculate();
		assertTrue(result == (float) Math.log((float) Math.sin(varX)));
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testSameName() throws Exception {
		CustomFunction custom = new CustomFunction("bar") {
			@Override
			public Object apply(Object... values) {
				return (float) values[0] / 2f;
			}
		};
		float varBar = 1.3f;
		Calculable<Float> calc = new ExpressionBuilder("f(bar)=bar(bar)", Float.class).variable("bar").function(custom).build();
		float result = calc.calculate();
		assertTrue(result == varBar / 2);
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testInvalidFunction() throws Exception {
		float varY = 4.22f;
		Calculable<Float> calc = new ExpressionBuilder("3*invalid_function(y)", Float.class).variable("y").build();
		calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testMissingVar() throws Exception {
		float varY = 4.22f;
		Calculable<Float> calc = new ExpressionBuilder("3*y*z", Float.class).variable("y").build();
		calc.calculate();
	}

	@Test
	public void testOperatorPrecedence() throws Exception {

		ExpressionBuilder builder = new ExpressionBuilder("1", Float.class);
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
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression10() throws Exception {
		String expr;
		double expected;
		expr = "1 * 1.5 + 1";
		expected = 1 * 1.5 + 1;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression11() throws Exception {
		float x = 1f;
		float y = 2f;
		String expr = "log(x) ^ sin(y)";
		float expected = (float) Math.pow((float) Math.log(x), (float) Math.sin(y));
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x", "y").build();
		calc.setVariable("x", x);
		calc.setVariable("y", y);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression12() throws Exception {
		String expr = "log(2.5333333333)^(0-1)";
		float expected = (float) Math.pow((float) Math.log(2.5333333333f), -1f);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression13() throws Exception {
		String expr = "2.5333333333^(0-1)";
		float expected = (float) Math.pow(2.5333333333f, -1f);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression14() throws Exception {
		String expr = "2 * 17.41 + (12*2)^(0-1)";
		float expected = 2f * 17.41f + (float) Math.pow((12f * 2f), -1f);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression15() throws Exception {
		String expr = "2.5333333333 * 17.41 + (12*2)^log(2.764)";
		float expected = 2.5333333333f * 17.41f + (float) Math.pow((12f * 2f), (float) Math.log(2.764f));
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression16() throws Exception {
		String expr = "2.5333333333/2 * 17.41 + (12*2)^(log(2.764) - sin(5.6664))";
		float expected = 2.5333333333f / 2f * 17.41f + (float) Math.pow((12f * 2f), (float) Math.log(2.764f)
				- (float) Math.sin(5.6664f));
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression17() throws Exception {
		String expr = "x^2 - 2 * y";
		float x = (float) Math.E;
		float y = (float) Math.PI;
		float expected = x * x - 2f * y;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x", "y").build();
		calc.setVariable("x", x);
		calc.setVariable("y", y);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression18() throws Exception {
		String expr = "-3";
		float expected = -3;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression19() throws Exception {
		String expr = "-3 * -24.23";
		float expected = -3 * -24.23f;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression2() throws Exception {
		String expr;
		double expected;
		expr = "2+3*4-12";
		expected = 2 + 3 * 4 - 12;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression20() throws Exception {
		String expr = "-2 * 24/log(2) -2";
		float expected = -2f * 24f / (float) Math.log(2f) - 2f;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression21() throws Exception {
		String expr = "-2 *33.34/log(x)^-2 + 14 *6";
		float x = 1.334f;
		float expected = -2f * 33.34f / (float) Math.pow((float) Math.log(x), -2f) + 14f * 6f;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		calc.setVariable("x", x);
		float result = calc.calculate();
		assertTrue("expected " + expected + " but got " + result, expected == result);
	}

	@Test
	public void testExpression21_1() throws Exception {
		String expr = "log(x)^-2";
		float x = 1.334f;
		float expected = (float) Math.pow((float) Math.log(x), -2f);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variable("x").build();
		calc.setVariable("x", x);
		float result = calc.calculate();
		assertTrue("expected " + expected + " but got " + result, expected == result);
	}

	@Test
	public void testExpression22() throws Exception {
		String expr = "-2 *33.34/log(x)^-2 + 14 *6";
		float x = 1.334f;
		float expected = -2f * 33.34f / (float) Math.pow((float) Math.log(x), -2f) + 14f * 6f;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		calc.setVariable("x", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression23() throws Exception {
		String expr = "-2 *33.34/(log(foo)^-2 + 14 *6) - sin(foo)";
		float x = 1.334f;
		float expected = -2f * 33.34f / ((float) Math.pow((float) Math.log(x), -2f) + 14f * 6f) - (float) Math.sin(x);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("foo").build();
		calc.setVariable("foo", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression24() throws Exception {
		String expr = "3+4-log(23.2)^(2-1) * -1";
		float expected = 3f + 4f - (float) Math.pow((float) Math.log(23.2f), (2f - 1f)) * -1f;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression25() throws Exception {
		String expr = "+3+4-+log(23.2)^(2-1) * + 1";
		float expected = 3f + 4f - (float) Math.log(23.2f);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression26() throws Exception {
		String expr = "14 + -(1 / 2.22^3)";
		float expected = 14f + -(1f / (float) Math.pow(2.22f, 3f));
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression27() throws Exception {
		String expr = "12^-+-+-+-+-+-+---2";
		float expected = (float) Math.pow(12d, -2d);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression28() throws Exception {
		String expr = "12^-+-+-+-+-+-+---2 * (-14) / 2 ^ -log(2.22323) ";
		float expected = (float) Math.pow(12f, -2f) * -14f / (float) Math.pow(2f, (float) -Math.log(2.22323f));
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression29() throws Exception {
		String expr = "24.3343 % 3";
		float expected = 24.3343f % 3f;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testVarname1() throws Exception {
		String expr = "12.23 * foo.bar";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variable("foo.bar").build();
		assertTrue(12.23f == calc.calculate());
	}

	public void testVarname2() throws Exception {
		String expr = "12.23 * foo.bar";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variable("_foo").build();
		assertTrue(12.23f == calc.calculate());
	}

	@Test
	public void testExpression3() throws Exception {
		String expr;
		float expected;
		expr = "2+4*5";
		expected = 2f + 4f * 5f;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression30() throws Exception {
		String expr = "24.3343 % 3 * 20 ^ -(2.334 % log(2 / 14))";
		float expected = 24.3343f % 3f * (float) Math.pow(20f, -(2.334f % (float) Math.log(2f / 14f)));
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression31() throws Exception {
		String expr = "-2 *33.34/log(y_x)^-2 + 14 *6";
		float x = 1.334f;
		float expected = -2f * 33.34f / (float) Math.pow((float) Math.log(x), -2f) + 14f * 6f;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("y_x").build();
		calc.setVariable("y_x", x);
		float actual = calc.calculate();
		assertTrue(expected == actual);
	}

	@Test
	public void testExpression32() throws Exception {
		String expr = "-2 *33.34/log(y_2x)^-2 + 14 *6";
		float x = 1.334f;
		float expected = -2f * 33.34f / (float) Math.pow((float) Math.log(x), -2f) + 14f * 6f;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("y_2x").build();
		calc.setVariable("y_2x", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression33() throws Exception {
		String expr = "-2 *33.34/log(_y)^-2 + 14 *6";
		float x = 1.334f;
		float expected = -2f * 33.34f / (float) Math.pow((float) Math.log(x), -2f) + 14f * 6f;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("_y").build();
		calc.setVariable("_y", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression34() throws Exception {
		String expr = "-2 + + (+4) +(4)";
		float expected = -2 + 4 + 4;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	@Ignore
	public void testExpression40() throws Exception {
		String expr = "1e1";
		float expected = 10f;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	@Ignore
	public void testExpression41() throws Exception {
		String expr = "1e-1";
		float expected = 0.1f;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
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
		float expected = 7.2973525698f * (float) Math.pow(10f, -3f);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	@Ignore
	public void testExpression43() throws Exception {
		String expr = "6.02214E23";
		float expected = 6.02214f * (float) Math.pow(10f, 23f);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	@Ignore
	public void testExpression44() throws Exception {
		String expr = "6.02214E23";
		float expected = 6.02214f * (float) Math.pow(10f, 23f);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test(expected = RuntimeException.class)
	@Ignore
	public void testExpression45() throws Exception {
		String expr = "6.02214E2E3";
		new ExpressionBuilder(expr, Float.class).build();
	}

	@Test(expected = RuntimeException.class)
	@Ignore
	public void testExpression46() throws Exception {
		String expr = "6.02214e2E3";
		new ExpressionBuilder(expr, Float.class).build();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression47() throws Exception {
		String expr = "6.02214y3";
		new ExpressionBuilder(expr, Float.class).build();
	}

	// tests for EXP-20: No exception is thrown for unmatched parenthesis in
	// build
	// Thanks go out to maheshkurmi for reporting
	@Test(expected = UnparseableExpressionException.class)
	public void testExpression48() throws Exception {
		String expr = "(1*2";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		float result = calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression49() throws Exception {
		String expr = "{1*2";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		float result = calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression50() throws Exception {
		String expr = "[1*2";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		float result = calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression51() throws Exception {
		String expr = "(1*{2+[3}";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		float result = calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testExpression52() throws Exception {
		String expr = "(1*(2+(3";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		float result = calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	@Ignore
	public void testExpression53() throws Exception {
		String expr = "14 * 2x";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		assertTrue(56d == calc.calculate());
	}

	@Test(expected = UnparseableExpressionException.class)
	@Ignore
	public void testExpression54() throws Exception {
		String expr = "2 ((-(x)))";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		assertTrue(-4d == calc.calculate());
	}

	@Test(expected = UnparseableExpressionException.class)
	@Ignore
	public void testExpression55() throws Exception {
		String expr = "2 sin(x)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		assertTrue(Math.sin(2d) * 2 == calc.calculate());
	}

	@Test(expected = UnparseableExpressionException.class)
	@Ignore
	public void testExpression56() throws Exception {
		String expr = "2 sin(3x)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		assertTrue(Math.sin(6d) * 2 == calc.calculate());
	}

	// Thanks go out to Johan Björk for reporting the division by zero problem
	// EXP-22
	// https://www.objecthunter.net/jira/browse/EXP-22
	@Test(expected = ArithmeticException.class)
	public void testExpression57() throws Exception {
		String expr = "1 / 0";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(Double.POSITIVE_INFINITY == calc.calculate());
	}

	@Test
	public void testExpression58() throws Exception {
		String expr = "17 * sqrt(-1) * 12";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(Double.isNaN(calc.calculate()));
	}

	// Thanks go out to Alex Dolinsky for reporting the missing exception when
	// an empty
	// expression is passed as in new ExpressionBuilder("")
	@Test(expected = IllegalArgumentException.class)
	public void testExpression59() throws Exception {
		Calculable<Float> calc = new ExpressionBuilder("", Float.class).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExpression60() throws Exception {
		Calculable<Float> calc = new ExpressionBuilder("   ", Float.class).build();
		calc.calculate();
	}

	@Test(expected = ArithmeticException.class)
	public void testExpression61() throws Exception {
		Calculable<Float> calc = new ExpressionBuilder("14 % 0", Float.class).build();
		calc.calculate();
	}

	// https://www.objecthunter.net/jira/browse/EXP-24
	// thanks go out to Rémi for the issue report
	@Test
	@Ignore
	public void testExpression62() throws Exception {
		Calculable<Float> calc = new ExpressionBuilder("x*1.0e5+5", Float.class).variables("x").build();
		assertTrue(Math.E * 1.0 * Math.pow(10, 5) + 5 == calc.calculate());
	}

	// thanks go out to Janny for providing the tests and the bug report
	@Test
	public void testUnaryMinusInParenthesisSpace() throws Exception {
		ExpressionBuilder b = new ExpressionBuilder("( -1)^2", Float.class);
		double calculated = (float) b.build().calculate();
		assertTrue(calculated == 1d);
	}

	@Test
	public void testUnaryMinusSpace() throws Exception {
		ExpressionBuilder b = new ExpressionBuilder(" -1 + 2", Float.class);
		double calculated = (float) b.build().calculate();
		assertTrue(calculated == 1d);
	}

	@Test
	public void testUnaryMinusSpaces() throws Exception {
		ExpressionBuilder b = new ExpressionBuilder(" -1 + + 2 +   -   1", Float.class);
		double calculated = (float) b.build().calculate();
		assertTrue(calculated == 0d);
	}

	@Test
	public void testUnaryMinusSpace1() throws Exception {
		ExpressionBuilder b = new ExpressionBuilder("-1", Float.class);
		double calculated = (float) b.build().calculate();
		assertTrue(calculated == -1d);
	}

	@Test
	public void testExpression4() throws Exception {
		String expr;
		double expected;
		expr = "2+4 * 5";
		expected = 2 + 4 * 5;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	// test for https://www.objecthunter.net/jira/browse/EXP-19
	// thanks go out to Yunior Peralta for the report
	@Test
	public void testCharacterPositionInException1() throws Exception {
		String expr;
		expr = "2 + sn(4)";
		try {
			Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
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
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression6() throws Exception {
		String expr;
		double expected;
		expr = "(2+4)*5 + 2.5*2";
		expected = (2 + 4) * 5 + 2.5 * 2;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression7() throws Exception {
		String expr;
		double expected;
		expr = "(2+4)*5 + 10/2";
		expected = (2 + 4) * 5 + 10 / 2;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression8() throws Exception {
		String expr;
		double expected;
		expr = "(2 * 3 +4)*5 + 10/2";
		expected = (2 * 3 + 4) * 5 + 10 / 2;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression9() throws Exception {
		String expr;
		double expected;
		expr = "(2 * 3 +4)*5 +4 + 10/2";
		expected = (2 * 3 + 4) * 5 + 4 + 10 / 2;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testFailUnknownFunction1() throws Exception {
		String expr;
		expr = "lig(1)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		calc.calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testFailUnknownFunction2() throws Exception {
		String expr;
		expr = "galength(1)";
		new ExpressionBuilder(expr, Float.class).build().calculate();
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testFailUnknownFunction3() throws Exception {
		String expr;
		expr = "f(cos) = cos(cos)";
		new ExpressionBuilder(expr, Float.class).build().calculate();
	}

	@Test
	public void testFunction1() throws Exception {
		String expr;
		expr = "cos(cos_1)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("cos_1").build();
		calc.setVariable("cos_1", 1f);
		assertTrue(calc.calculate() == (float) Math.cos(1f));
	}

	@Test
	public void testPostfix1() throws Exception {
		String expr;
		float expected;
		expr = "2.2232^0.1";
		expected = (float) Math.pow(2.2232f, 0.1f);
		float actual = (float) new ExpressionBuilder(expr, Float.class).build().calculate();
		assertTrue(expected == actual);
	}

	@Test
	public void testPostfixEverything() throws Exception {
		String expr;
		float expected;
		expr = "(sin(12) + log(34)) * 3.42 - cos(2.234-log(2))";
		expected = (float) (Math.sin(12f) + (float) Math.log(34)) * 3.42f
				- (float) Math.cos(2.234f - (float) Math.log(2f));
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixExponentation1() throws Exception {
		String expr;
		float expected;
		expr = "2^3";
		expected = (float) Math.pow(2f, 3f);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixExponentation2() throws Exception {
		String expr;
		float expected;
		expr = "24 + 4 * 2^3";
		expected = 24f + 4f * (float) Math.pow(2f, 3f);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixExponentation3() throws Exception {
		String expr;
		float expected;
		float x = 4.334f;
		expr = "24 + 4 * 2^x";
		expected = 24f + 4f * (float) Math.pow(2f, x);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		calc.setVariable("x", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixExponentation4() throws Exception {
		String expr;
		float expected;
		float x = 4.334f;
		expr = "(24 + 4) * 2^log(x)";
		expected = (24f + 4f) * (float) Math.pow(2f, (float) Math.log(x));
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		calc.setVariable("x", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction1() throws Exception {
		String expr;
		float expected;
		expr = "log(1) * sin(0)";
		expected = (float) Math.log(1f) * (float) Math.sin(0f);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction10() throws Exception {
		String expr;
		float expected;
		expr = "cbrt(x)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		for (float x = -10; x < 10; x = x + 0.5f) {
			expected = (float) Math.cbrt(x);
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction11() throws Exception {
		String expr;
		float expected;
		expr = "cos(x) - (1/cbrt(x))";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		for (float x = -10; x < 10; x = x + 0.5f) {
			if (x == 0d)
				continue;
			expected = (float) Math.cos(x) - (1f / (float) Math.cbrt(x));
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction12() throws Exception {
		String expr;
		float expected;
		expr = "acos(x) * expm1(asin(x)) - exp(atan(x)) + floor(x) + cosh(x) - sinh(cbrt(x))";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		for (float x = -10; x < 10; x = x + 0.5f) {
			expected = (float) Math.acos(x) * (float) Math.expm1((float) Math.asin(x))
					- (float) Math.exp((float) Math.atan(x)) + (float) Math.floor(x) + (float) Math.cosh(x)
					- (float) Math.sinh((float) Math.cbrt(x));
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
		float expected;
		expr = "acos(x)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		for (float x = -10; x < 10; x = x + 0.5f) {
			expected = (float) Math.acos(x);
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
		float expected;
		expr = " expm1(x)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		for (float x = -10; x < 10; x = x + 0.5f) {
			expected = (float) Math.expm1(x);
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
		float expected;
		expr = "asin(x)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		for (float x = -10; x < 10; x = x + 0.5f) {
			expected = (float) Math.asin(x);
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
		float expected;
		expr = " exp(x)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		for (float x = -10; x < 10; x = x + 0.5f) {
			expected = (float) Math.exp(x);
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction17() throws Exception {
		String expr;
		float expected;
		expr = "floor(x)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		for (float x = -10; x < 10; x = x + 0.5f) {
			expected = (float) Math.floor(x);
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction18() throws Exception {
		String expr;
		float expected;
		expr = " cosh(x)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		for (float x = -10; x < 10; x = x + 0.5f) {
			expected = (float) Math.cosh(x);
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction19() throws Exception {
		String expr;
		float expected;
		expr = "sinh(x)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		for (float x = -10; x < 10; x = x + 0.5f) {
			expected = (float) Math.sinh(x);
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction20() throws Exception {
		String expr;
		float expected;
		expr = "cbrt(x)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		for (float x = -10; x < 10; x = x + 0.5f) {
			expected = (float) Math.cbrt(x);
			calc.setVariable("x", x);
			assertTrue(expected == calc.calculate());
		}
	}

	@Test
	public void testPostfixFunction21() throws Exception {
		String expr;
		float expected;
		expr = "tanh(x)";
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		for (float x = -10; x < 10; x = x + 0.5f) {
			expected = (float) Math.tanh(x);
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
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction3() throws Exception {
		String expr;
		double expected;
		expr = "sin(0)";
		expected = 0d;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction5() throws Exception {
		String expr;
		double expected;
		expr = "ceil(2.3) +1";
		expected = Math.ceil(2.3) + 1;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction6() throws Exception {
		String expr;
		float expected;
		float x = 1.565f;
		float y = 2.1323f;
		expr = "ceil(x) + 1 / y * abs(1.4)";
		expected = (float) Math.ceil(x) + ((float) 1f / y) * (float) Math.abs(1.4f);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x", "y").build();
		calc.setVariable("x", x);
		calc.setVariable("y", y);
		float actual = calc.calculate();
		assertTrue(expected == actual);
	}

	@Test
	public void testPostfixFunction7() throws Exception {
		String expr;
		float expected;
		float x = (float) Math.E;
		expr = "tan(x)";
		expected = (float) Math.tan(x);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		calc.setVariable("x", x);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction8() throws Exception {
		String expr;
		float expected;
		float e = (float) Math.E;
		expr = "2^3.4223232 + tan(e)";
		expected = (float) Math.pow(2f, 3.4223232f) + (float) Math.tan((float) Math.E);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("e").build();
		calc.setVariable("e", e);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction9() throws Exception {
		String expr;
		float expected;
		float x = (float) Math.E;
		expr = "cbrt(x)";
		expected = (float) Math.cbrt(x);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x").build();
		calc.setVariable("x", x);
		assertTrue(expected == calc.calculate());
	}

	@Test(expected = UnparseableExpressionException.class)
	public void testPostfixInvalidVariableName() throws Exception {
		String expr;
		double expected;
		float x = 4.5334332f;
		double log = Math.PI;
		expr = "x * pi";
		expected = x * log;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x", "log").build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixParanthesis() throws Exception {
		String expr;
		double expected;
		expr = "(3 + 3 * 14) * (2 * (24-17) - 14)/((34) -2)";
		expected = (3 + 3 * 14) * (2 * (24 - 17) - 14) / ((34) - 2);
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixVariables() throws Exception {
		String expr;
		float expected;
		float x = 4.5334332f;
		float pi = (float) Math.PI;
		expr = "x * pi";
		expected = x * pi;
		Calculable<Float> calc = new ExpressionBuilder(expr, Float.class).variables("x", "pi").build();
		calc.setVariable("x", x);
		calc.setVariable("pi", pi);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testBench1() throws Exception {
		if (System.getProperty("skipBenchmark") != null) {
			System.out.println(":: skipping naive benchmarks...");
			return;
		}
		System.out.println(":: running naive benchmarks, set -DskipBenchmark to skip");
		String expr = "log(x) - y * (sqrt(x^cos(y)))";
		Calculable<Float> calc = new ExpressionBuilder<Float>(expr, Float.class).variables("x", "y").build();
		@SuppressWarnings("unused")
		double val;
		Random rnd = new Random();
		long timeout = 2;
		long time = System.currentTimeMillis() + (1000 * timeout);
		int count = 0;
		while (time > System.currentTimeMillis()) {
			calc.setVariable("x", rnd.nextFloat());
			calc.setVariable("y", rnd.nextFloat());
			val = calc.calculate();
			count++;
		}
		System.out.println("\n:: running simple benchmarks [" + timeout + " seconds]");
		System.out.println("expression\t\t" + expr);
		double rate = count / timeout;
		System.out.println("exp4j\t\t\t" + count + " ["
				+ (rate > 1000 ? new DecimalFormat("#.##").format(rate / 1000) + "k" : rate) + " calc/sec]");
		time = System.currentTimeMillis() + (1000 * timeout);
		double x, y;
		count = 0;
		while (time > System.currentTimeMillis()) {
			x = rnd.nextDouble();
			y = rnd.nextDouble();
			val = Math.log(x) - y * (Math.sqrt(Math.pow(x, Math.cos(y))));
			count++;
		}
		rate = count / timeout;
		System.out.println("Java Math\t\t" + count + " ["
				+ (rate > 1000 ? new DecimalFormat("#.##").format(rate / 1000) + "k" : rate) + " calc/sec]");
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		if (engine == null) {
			System.err.println("Unable to instantiate javascript engine. skipping naive JS bench.");
		} else {
			time = System.currentTimeMillis() + (1000 * timeout);
			count = 0;
			while (time > System.currentTimeMillis()) {
				x = rnd.nextDouble();
				y = rnd.nextDouble();
				engine.eval("Math.log(" + x + ") - " + y + "* (Math.sqrt(" + x + "^Math.cos(" + y + ")))");
				count++;
			}
			rate = count / timeout;
			System.out.println("JSR 223(Javascript)\t" + count + " ["
					+ (rate > 1000 ? new DecimalFormat("#.##").format(rate / 1000) + "k" : rate) + " calc/sec]");
		}
	}

}
