package de.congrace.exp4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.junit.Test;

public class ExpressionBuilderTest {
	@Test
	public void testCustomFunction1() throws Exception {
		CustomFunction custom = new CustomFunction("timespi") {
			@Override
			public double applyFunction(double... values) {
				return values[0] * Math.PI;
			}
		};
		Calculable calc = new ExpressionBuilder("timespi(x)").withVariable("x", 1).withCustomFunction(custom).build();
		double result = calc.calculate();
		assertTrue(result == Math.PI);
	}

	@Test
	public void testCustomFunction2() throws Exception {
		CustomFunction custom = new CustomFunction("loglog") {
			@Override
			public double applyFunction(double... values) {
				return Math.log(Math.log(values[0]));
			}
		};
		Calculable calc = new ExpressionBuilder("loglog(x)").withVariable("x", 1).withCustomFunction(custom).build();
		double result = calc.calculate();
		assertTrue(result == Math.log(Math.log(1)));
	}

	@Test
	public void testCustomFunction3() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public double applyFunction(double... values) {
				return values[0] * Math.E;
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public double applyFunction(double... values) {
				return values[0] * Math.PI;
			}
		};
		Calculable calc = new ExpressionBuilder("foo(bar(x))").withVariable("x", 1).withCustomFunction(custom1)
				.withCustomFunction(custom2).build();
		double result = calc.calculate();
		assertTrue(result == 1 * Math.E * Math.PI);
	}

	@Test
	public void testCustomFunction4() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public double applyFunction(double... values) {
				return values[0] * Math.E;
			}
		};
		double varX = 32.24979131d;
		Calculable calc = new ExpressionBuilder("foo(log(x))").withVariable("x", varX).withCustomFunction(custom1)
				.build();
		double result = calc.calculate();
		assertTrue(result == Math.log(varX) * Math.E);
	}

	@Test
	public void testCustomFunction5() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public double applyFunction(double... values) {
				return values[0] * Math.E;
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public double applyFunction(double... values) {
				return values[0] * Math.PI;
			}
		};
		double varX = 32.24979131d;
		Calculable calc = new ExpressionBuilder("bar(foo(log(x)))").withVariable("x", varX).withCustomFunction(custom1)
				.withCustomFunction(custom2).build();
		double result = calc.calculate();
		assertTrue(result == Math.log(varX) * Math.E * Math.PI);
	}

	@Test
	public void testCustomFunction6() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public double applyFunction(double... values) {
				return values[0] * Math.E;
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public double applyFunction(double... values) {
				return values[0] * Math.PI;
			}
		};
		double varX = 32.24979131d;
		Calculable calc = new ExpressionBuilder("bar(foo(log(x)))").withVariable("x", varX)
				.withCustomFunctions(Arrays.asList(custom1, custom2)).build();
		double result = calc.calculate();
		assertTrue(result == Math.log(varX) * Math.E * Math.PI);
	}

	@Test
	public void testCustomFunction7() throws Exception {
		CustomFunction custom1 = new CustomFunction("half") {
			@Override
			public double applyFunction(double... values) {
				return values[0] / 2;
			}
		};
		Calculable calc = new ExpressionBuilder("half(x)").withVariable("x", 1d).withCustomFunction(custom1).build();
		assertTrue(0.5d == calc.calculate());
	}

	@Test
	public void testCustomFunction10() throws Exception {
		CustomFunction custom1 = new CustomFunction("max", 2) {
			@Override
			public double applyFunction(double... values) {
				return values[0] < values[1] ? values[1] : values[0];
			}
		};
		Calculable calc = new ExpressionBuilder("max(x,y)").withVariable("x", 1d).withVariable("y", 2)
				.withCustomFunction(custom1).build();
		assertTrue(2 == calc.calculate());
	}

	@Test
	public void testCustomFunction11() throws Exception {
		CustomFunction custom1 = new CustomFunction("power", 2) {
			@Override
			public double applyFunction(double... values) {
				return Math.pow(values[0], values[1]);
			}
		};
		Calculable calc = new ExpressionBuilder("power(x,y)").withVariable("x", 2d).withVariable("y", 4d)
				.withCustomFunction(custom1).build();
		assertTrue(Math.pow(2, 4) == calc.calculate());
	}

	@Test
	public void testCustomFunction12() throws Exception {
		CustomFunction custom1 = new CustomFunction("max", 5) {
			@Override
			public double applyFunction(double... values) {
				double max = values[0];
				for (int i = 1; i < argc; i++) {
					if (values[i] > max) {
						max = values[i];
					}
				}
				return max;
			}
		};
		Calculable calc = new ExpressionBuilder("max(1,2.43311,51.13,43,12)").withCustomFunction(custom1).build();
		assertTrue(51.13d == calc.calculate());
	}

	@Test
	public void testCustomFunction13() throws Exception {
		CustomFunction custom1 = new CustomFunction("max", 3) {
			@Override
			public double applyFunction(double... values) {
				double max = values[0];
				for (int i = 1; i < argc; i++) {
					if (values[i] > max) {
						max = values[i];
					}
				}
				return max;
			}
		};
		double varX = Math.E;
		Calculable calc = new ExpressionBuilder("max(log(x),sin(x),x)").withVariable("x", varX)
				.withCustomFunction(custom1).build();
		assertTrue(varX == calc.calculate());
	}

	@Test
	public void testCustomFunction14() throws Exception {
		CustomFunction custom1 = new CustomFunction("multiply", 2) {
			@Override
			public double applyFunction(double... values) {
				return values[0] * values[1];
			}
		};
		double varX = 1;
		Calculable calc = new ExpressionBuilder("multiply(sin(x),x+1)").withVariable("x", varX)
				.withCustomFunction(custom1).build();
		double expected = Math.sin(varX) * (varX + 1);
		double actual = calc.calculate();
		assertTrue(expected == actual);
	}

	@Test
	public void testCustomFunction15() throws Exception {
		CustomFunction custom1 = new CustomFunction("timesPi") {
			@Override
			public double applyFunction(double... values) {
				return values[0] * Math.PI;
			}
		};
		double varX = 1;
		Calculable calc = new ExpressionBuilder("timesPi(x^2)").withVariable("x", varX).withCustomFunction(custom1)
				.build();
		double expected = varX * Math.PI;
		double actual = calc.calculate();
		assertTrue(expected == actual);
	}

	@Test
	public void testCustomFunction16() throws Exception {
		CustomFunction custom1 = new CustomFunction("multiply", 3) {
			@Override
			public double applyFunction(double... values) {
				return values[0] * values[1] * values[2];
			}
		};
		double varX = 1;
		Calculable calc = new ExpressionBuilder("multiply(sin(x),x+1^(-2),log(x))").withVariable("x", varX)
				.withCustomFunction(custom1).build();
		double expected = Math.sin(varX) * Math.pow((varX + 1), -2) * Math.log(varX);
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testCustomFunction17() throws Exception {
		CustomFunction custom1 = new CustomFunction("timesPi") {
			@Override
			public double applyFunction(double... values) {
				return values[0] * Math.PI;
			}
		};
		double varX = Math.E;
		Calculable calc = new ExpressionBuilder("timesPi(log(x^(2+1)))").withVariable("x", varX)
				.withCustomFunction(custom1).build();
		double expected = Math.log(Math.pow(varX, 3)) * Math.PI;
		assertTrue(expected == calc.calculate());
	}

	// thanks to Marcin Domanski who issued http://jira.congrace.de/jira/browse/EXP-11
	// i have this test, which fails in 0.2.9
	@Test
	public void testCustomFunction18() throws Exception {
		CustomFunction minFunction = new CustomFunction("min", 2) {
			@Override
			public double applyFunction(double[] values) {
				double currentMin = Double.POSITIVE_INFINITY;
				for (double value : values) {
					currentMin = Math.min(currentMin, value);
				}
				return currentMin;
			}
		};
		ExpressionBuilder b = new ExpressionBuilder("-min(5, 0) + 10").withCustomFunction(minFunction);
		double calculated = b.build().calculate();
		assertTrue(calculated == 10);
	}

	// thanks to Sylvain Machefert who issued http://jira.congrace.de/jira/browse/EXP-11
    // i have this test, which fails in 0.3.2
    @Test
    public void testCustomFunction19() throws Exception {
        CustomFunction minFunction = new CustomFunction("power", 2) {
            @Override
            public double applyFunction(double[] values) {
                return Math.pow(values[0], values[1]);
            }
        };
        ExpressionBuilder b = new ExpressionBuilder("power(2,3)").withCustomFunction(minFunction);
        double calculated = b.build().calculate();
        assertTrue(calculated == Math.pow(2, 3));
    }

    @Test
	public void testCustomOperators1() throws Exception {
		CustomOperator factorial = new CustomOperator("!", true, 6, 1) {
			@Override
			protected double applyOperation(double[] values) {
				double tmp = 1d;
				int steps = 1;
				while (steps < values[0]) {
					tmp = tmp * (++steps);
				}
				return tmp;
			}
		};
		Calculable calc = new ExpressionBuilder("1!").withOperation(factorial).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("2!").withOperation(factorial).build();
		assertTrue(2d == calc.calculate());
		calc = new ExpressionBuilder("3!").withOperation(factorial).build();
		assertTrue(6d == calc.calculate());
		calc = new ExpressionBuilder("4!").withOperation(factorial).build();
		assertTrue(24d == calc.calculate());
		calc = new ExpressionBuilder("5!").withOperation(factorial).build();
		assertTrue(120d == calc.calculate());
		calc = new ExpressionBuilder("11!").withOperation(factorial).build();
		assertTrue(39916800d == calc.calculate());
	}

	@Test
	public void testCustomOperators2() throws Exception {
		CustomOperator factorial = new CustomOperator("!", true, 6, 1) {
			@Override
			protected double applyOperation(double[] values) {
				double tmp = 1d;
				int steps = 1;
				while (steps < values[0]) {
					tmp = tmp * (++steps);
				}
				return tmp;
			}
		};
		Calculable calc = new ExpressionBuilder("2^2!").withOperation(factorial).build();
		assertTrue(4d == calc.calculate());
		calc = new ExpressionBuilder("2!^2").withOperation(factorial).build();
		assertTrue(4d == calc.calculate());
		calc = new ExpressionBuilder("-(3!)^-1").withOperation(factorial).build();
		double actual = calc.calculate();
		assertEquals(Math.pow(-6d, -1), actual, 0d);
	}

	@Test
	public void testCustomOperators3() throws Exception {
		CustomOperator factorial = new CustomOperator(">=", true, 4, 2) {
			@Override
			protected double applyOperation(double[] values) {
				if (values[0] >= values[1]) {
					return 1d;
				} else {
					return 0d;
				}
			}
		};
		Calculable calc = new ExpressionBuilder("1>=2").withOperation(factorial).build();
		assertTrue(0d == calc.calculate());
		calc = new ExpressionBuilder("2>=1").withOperation(factorial).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("-2>=1").withOperation(factorial).build();
		assertTrue(0d == calc.calculate());
		calc = new ExpressionBuilder("-2>=-1").withOperation(factorial).build();
		assertTrue(0d == calc.calculate());
	}

	@Test
	public void testModulo1() throws Exception {
		double result = new ExpressionBuilder("33%(20/2)%2").build().calculate();
		assertTrue(result == 1d);
	}

	@Test
	public void testCustomOperators4() throws Exception {
		CustomOperator greaterEq = new CustomOperator(">=", true, 4, 2) {
			@Override
			protected double applyOperation(double[] values) {
				if (values[0] >= values[1]) {
					return 1d;
				} else {
					return 0d;
				}
			}
		};
		CustomOperator greater = new CustomOperator(">", true, 4, 2) {
			@Override
			protected double applyOperation(double[] values) {
				if (values[0] > values[1]) {
					return 1d;
				} else {
					return 0d;
				}
			}
		};
		CustomOperator newPlus = new CustomOperator(">=>", true, 4, 2) {
			@Override
			protected double applyOperation(double[] values) {
				return values[0] + values[1];
			}
		};
		Calculable calc = new ExpressionBuilder("1>2").withOperation(greater).build();
		assertTrue(0d == calc.calculate());
		calc = new ExpressionBuilder("2>=2").withOperation(greaterEq).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1>=>2").withOperation(newPlus).build();
		assertTrue(3d == calc.calculate());
		calc = new ExpressionBuilder("1>=>2>2").withOperation(greater).withOperation(newPlus).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1>=>2>2>=1").withOperation(greater).withOperation(newPlus)
				.withOperation(greaterEq).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1 >=> 2 > 2 >= 1").withOperation(greater).withOperation(newPlus)
				.withOperation(greaterEq).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1 >=> 2 >= 2 > 1").withOperation(greater).withOperation(newPlus)
				.withOperation(greaterEq).build();
		assertTrue(0d == calc.calculate());
		calc = new ExpressionBuilder("1 >=> 2 >= 2 > 0").withOperation(greater).withOperation(newPlus)
				.withOperation(greaterEq).build();
		assertTrue(1d == calc.calculate());
		calc = new ExpressionBuilder("1 >=> 2 >= 2 >= 1").withOperation(greater).withOperation(newPlus)
				.withOperation(greaterEq).build();
		assertTrue(1d == calc.calculate());
	}

	@Test(expected = UnparsableExpressionException.class)
	public void testInvalidOperator1() throws Exception {
		CustomOperator fail = new CustomOperator("2") {
			@Override
			protected double applyOperation(double[] values) {
				return 0;
			}
		};
		new ExpressionBuilder("1").withOperation(fail).build();
	}

	@Test(expected = InvalidCustomFunctionException.class)
	public void testInvalidCustomFunction1() throws Exception {
		CustomFunction func = new CustomFunction("1gd") {
			@Override
			public double applyFunction(double... args) {
				return 0;
			}
		};
	}

	@Test(expected = InvalidCustomFunctionException.class)
	public void testInvalidCustomFunction2() throws Exception {
		CustomFunction func = new CustomFunction("+1gd") {
			@Override
			public double applyFunction(double... args) {
				return 0;
			}
		};
	}

	@Test
	public void testExpressionBuilder1() throws Exception {
		Calculable calc = new ExpressionBuilder("7*x + 3*y").withVariable("x", 1).withVariable("y", 2).build();
		double result = calc.calculate();
		assertTrue(result == 13d);
	}

	@Test
	public void testExpressionBuilder2() throws Exception {
		Calculable calc = new ExpressionBuilder("7*x + 3*y").withVariable("x", 1).withVariable("y", 2).build();
		double result = calc.calculate();
		assertTrue(result == 13d);
	}

	@Test
	public void testExpressionBuilder3() throws Exception {
		double varX = 1.3d;
		double varY = 4.22d;
		Calculable calc = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y").withVariable("x", varX)
				.withVariable("y", varY).build();
		double result = calc.calculate();
		assertTrue(result == 7 * varX + 3 * varY - Math.pow(Math.log(varY / varX * 12), varY));
	}

	@Test
	public void testExpressionBuilder4() throws Exception {
		double varX = 1.3d;
		double varY = 4.22d;
		Calculable calc = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y").withVariable("x", varX)
				.withVariable("y", varY).build();
		double result = calc.calculate();
		assertTrue(result == 7 * varX + 3 * varY - Math.pow(Math.log(varY / varX * 12), varY));
		varX = 1.79854d;
		varY = 9281.123d;
		calc.setVariable("x", varX);
		calc.setVariable("y", varY);
		result = calc.calculate();
		assertTrue(result == 7 * varX + 3 * varY - Math.pow(Math.log(varY / varX * 12), varY));
	}

	@Test
	public void testExpressionBuilder5() throws Exception {
		double varX = 1.3d;
		double varY = 4.22d;
		Calculable calc = new ExpressionBuilder("3*y").withVariable("x", varX).withVariable("y", varY).build();
		double result = calc.calculate();
		assertTrue(result == 3 * varY);
	}

	@Test
	public void testExpressionBuilder6() throws Exception {
		double varX = 1.3d;
		double varY = 4.22d;
		double varZ = 4.22d;
		Calculable calc = new ExpressionBuilder("x * y * z").withVariableNames("x", "y", "z").build();
		calc.setVariable("x", varX);
		calc.setVariable("y", varY);
		calc.setVariable("z", varZ);
		double result = calc.calculate();
		assertTrue(result == varX * varY * varZ);
	}

	@Test
	public void testExpressionBuilder7() throws Exception {
		double varX = 1.3d;
		Calculable calc = new ExpressionBuilder("log(sin(x))").withVariable("x", varX).build();
		double result = calc.calculate();
		assertTrue(result == Math.log(Math.sin(varX)));
	}

	@Test
	public void testExpressionBuilder8() throws Exception {
		double varX = 1.3d;
		Calculable calc = new ExpressionBuilder("log(sin(x))").withVariable("x", varX).build();
		double result = calc.calculate();
		assertTrue(result == Math.log(Math.sin(varX)));
	}

	@Test(expected = UnparsableExpressionException.class)
	public void testSameName() throws Exception {
		CustomFunction custom = new CustomFunction("bar") {
			@Override
			public double applyFunction(double... values) {
				return values[0] / 2;
			}
		};
		double varBar = 1.3d;
		Calculable calc = new ExpressionBuilder("f(bar)=bar(bar)").withVariable("bar", varBar)
				.withCustomFunction(custom).build();
		double result = calc.calculate();
		assertTrue(result == varBar / 2);
	}

	@Test(expected = UnparsableExpressionException.class)
	public void testInvalidFunction() throws Exception {
		double varY = 4.22d;
		Calculable calc = new ExpressionBuilder("3*invalid_function(y)").withVariable("y", varY).build();
		calc.calculate();
	}

	@Test(expected = UnparsableExpressionException.class)
	public void testMissingVar() throws Exception {
		double varY = 4.22d;
		Calculable calc = new ExpressionBuilder("3*y*z").withVariable("y", varY).build();
		calc.calculate();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testOperatorPrecedence() throws Exception {

		ExpressionBuilder builder = new ExpressionBuilder("1");
		Field operatorField = ExpressionBuilder.class.getDeclaredField("builtInOperators");
		operatorField.setAccessible(true);
		Map<Character, CustomOperator> operators = (Map<Character, CustomOperator>) operatorField.get(builder);

		assertTrue(operators.get("+").leftAssociative);
		assertTrue(operators.get("*").leftAssociative);
		assertTrue(operators.get("-").leftAssociative);
		assertTrue(operators.get("/").leftAssociative);
		assertTrue(!operators.get("^").leftAssociative);
		assertTrue(!operators.get("\'").leftAssociative);

		assertTrue(operators.get("+").precedence == operators.get("-").precedence);
		assertTrue(operators.get("+").precedence < operators.get("*").precedence);
		assertTrue(operators.get("+").precedence < operators.get("/").precedence);
		assertTrue(operators.get("+").precedence < operators.get("^").precedence);
		assertTrue(operators.get("+").precedence < operators.get("\'").precedence);

		assertTrue(operators.get("-").precedence == operators.get("+").precedence);
		assertTrue(operators.get("-").precedence < operators.get("*").precedence);
		assertTrue(operators.get("-").precedence < operators.get("/").precedence);
		assertTrue(operators.get("-").precedence < operators.get("^").precedence);
		assertTrue(operators.get("-").precedence < operators.get("\'").precedence);

		assertTrue(operators.get("*").precedence > operators.get("+").precedence);
		assertTrue(operators.get("*").precedence > operators.get("-").precedence);
		assertTrue(operators.get("*").precedence == operators.get("/").precedence);
		assertTrue(operators.get("*").precedence < operators.get("^").precedence);
		assertTrue(operators.get("*").precedence < operators.get("\'").precedence);

		assertTrue(operators.get("/").precedence > operators.get("+").precedence);
		assertTrue(operators.get("/").precedence > operators.get("-").precedence);
		assertTrue(operators.get("/").precedence == operators.get("*").precedence);
		assertTrue(operators.get("/").precedence < operators.get("^").precedence);
		assertTrue(operators.get("/").precedence < operators.get("\'").precedence);

		assertTrue(operators.get("^").precedence > operators.get("+").precedence);
		assertTrue(operators.get("^").precedence > operators.get("-").precedence);
		assertTrue(operators.get("^").precedence > operators.get("*").precedence);
		assertTrue(operators.get("^").precedence > operators.get("/").precedence);
		assertTrue(operators.get("^").precedence < operators.get("\'").precedence);

		assertTrue(operators.get("\'").precedence > operators.get("+").precedence);
		assertTrue(operators.get("\'").precedence > operators.get("-").precedence);
		assertTrue(operators.get("\'").precedence > operators.get("*").precedence);
		assertTrue(operators.get("\'").precedence > operators.get("/").precedence);
		assertTrue(operators.get("\'").precedence > operators.get("^").precedence);
	}

	@Test
	public void testExpression1() throws Exception {
		String expr;
		double expected;
		expr = "2 + 4";
		expected = 6d;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression10() throws Exception {
		String expr;
		double expected;
		expr = "1 * 1.5 + 1";
		expected = 1 * 1.5 + 1;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression11() throws Exception {
		double x = 1d;
		double y = 2d;
		String expr = "log(x) ^ sin(y)";
		double expected = Math.pow(Math.log(x), Math.sin(y));
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x", "y").build();
		assertTrue(expected == calc.calculate(x, y));
	}

	@Test
	public void testExpression12() throws Exception {
		String expr = "log(2.5333333333)^(0-1)";
		double expected = Math.pow(Math.log(2.5333333333d), -1);
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression13() throws Exception {
		String expr = "2.5333333333^(0-1)";
		double expected = Math.pow(2.5333333333d, -1);
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression14() throws Exception {
		String expr = "2 * 17.41 + (12*2)^(0-1)";
		double expected = 2 * 17.41d + Math.pow((12 * 2), -1);
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression15() throws Exception {
		String expr = "2.5333333333 * 17.41 + (12*2)^log(2.764)";
		double expected = 2.5333333333d * 17.41d + Math.pow((12 * 2), Math.log(2.764d));
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression16() throws Exception {
		String expr = "2.5333333333/2 * 17.41 + (12*2)^(log(2.764) - sin(5.6664))";
		double expected = 2.5333333333d / 2 * 17.41d + Math.pow((12 * 2), Math.log(2.764d) - Math.sin(5.6664d));
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression17() throws Exception {
		String expr = "x^2 - 2 * y";
		double x = Math.E;
		double y = Math.PI;
		double expected = x * x - 2 * y;
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x", "y").build();
		assertTrue(expected == calc.calculate(x, y));
	}

	@Test
	public void testExpression18() throws Exception {
		String expr = "-3";
		double expected = -3;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression19() throws Exception {
		String expr = "-3 * -24.23";
		double expected = -3 * -24.23d;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression2() throws Exception {
		String expr;
		double expected;
		expr = "2+3*4-12";
		expected = 2 + 3 * 4 - 12;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression20() throws Exception {
		String expr = "-2 * 24/log(2) -2";
		double expected = -2 * 24 / Math.log(2) - 2;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression21() throws Exception {
		String expr = "-2 *33.34/log(x)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		assertTrue(expected == calc.calculate(x));
	}

	@Test
	public void testExpression22() throws Exception {
		String expr = "-2 *33.34/log(x)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		assertTrue(expected == calc.calculate(x));
	}

	@Test
	public void testExpression23() throws Exception {
		String expr = "-2 *33.34/(log(foo)^-2 + 14 *6) - sin(foo)";
		double x = 1.334d;
		double expected = -2 * 33.34 / (Math.pow(Math.log(x), -2) + 14 * 6) - Math.sin(x);
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("foo").build();
		assertTrue(expected == calc.calculate(x));
	}

	@Test
	public void testExpression24() throws Exception {
		String expr = "3+4-log(23.2)^(2-1) * -1";
		double expected = 3 + 4 - Math.pow(Math.log(23.2), (2 - 1)) * -1;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression25() throws Exception {
		String expr = "+3+4-+log(23.2)^(2-1) * + 1";
		double expected = 3 + 4 - Math.log(23.2d);
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression26() throws Exception {
		String expr = "14 + -(1 / 2.22^3)";
		double expected = 14 + -(1d / Math.pow(2.22d, 3d));
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression27() throws Exception {
		String expr = "12^-+-+-+-+-+-+---2";
		double expected = Math.pow(12, -2);
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression28() throws Exception {
		String expr = "12^-+-+-+-+-+-+---2 * (-14) / 2 ^ -log(2.22323) ";
		double expected = Math.pow(12, -2) * -14 / Math.pow(2, -Math.log(2.22323));
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression29() throws Exception {
		String expr = "24.3343 % 3";
		double expected = 24.3343 % 3;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test(expected = UnparsableExpressionException.class)
	public void testVarname1() throws Exception {
		String expr = "12.23 * foo.bar";
		Calculable calc = new ExpressionBuilder(expr)
			.withVariable("foo.bar", 1d)
			.build();
		assertTrue(12.23 == calc.calculate());
	}

	public void testVarname2() throws Exception {
		String expr = "12.23 * foo.bar";
		Calculable calc = new ExpressionBuilder(expr)
			.withVariable("_foo", 1d)
			.build();
		assertTrue(12.23 == calc.calculate());
	}

	@Test
	public void testExpression3() throws Exception {
		String expr;
		double expected;
		expr = "2+4*5";
		expected = 2 + 4 * 5;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression30() throws Exception {
		String expr = "24.3343 % 3 * 20 ^ -(2.334 % log(2 / 14))";
		double expected = 24.3343d % 3 * Math.pow(20, -(2.334 % Math.log(2d / 14d)));
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression31() throws Exception {
		String expr = "-2 *33.34/log(y_x)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("y_x").build();
		assertTrue(expected == calc.calculate(x));
	}

	@Test
	public void testExpression32() throws Exception {
		String expr = "-2 *33.34/log(y_2x)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("y_2x").build();
		assertTrue(expected == calc.calculate(x));
	}

	@Test
	public void testExpression33() throws Exception {
		String expr = "-2 *33.34/log(_y)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("_y").build();
		assertTrue(expected == calc.calculate(x));
	}

	@Test
	public void testExpression34() throws Exception {
		String expr = "-2 + + (+4) +(4)";
		double expected = -2 + 4 + 4;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression35() throws Exception {
		String expr = "-3^2";
		double expected = -Math.pow(3, 2);
		System.setProperty(ExpressionBuilder.PROPERTY_UNARY_HIGH_PRECEDENCE, "false");
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
		System.clearProperty(ExpressionBuilder.PROPERTY_UNARY_HIGH_PRECEDENCE);
		calc = new ExpressionBuilder(expr).build();
		assertTrue(9d == calc.calculate());
	}

	@Test
	public void testExpression36() throws Exception {
		String expr = "-3^-2";
		double expected = -Math.pow(3, -2);
		System.setProperty(ExpressionBuilder.PROPERTY_UNARY_HIGH_PRECEDENCE, "false");
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
		System.clearProperty(ExpressionBuilder.PROPERTY_UNARY_HIGH_PRECEDENCE);
		calc = new ExpressionBuilder(expr).build();
		assertTrue(Math.pow(-3, -2) == calc.calculate());
	}

	@Test
	public void testExpression37() throws Exception {
		String expr = "-3^-2^-4";
		double expected = -Math.pow(3, -Math.pow(2, -4));
		System.setProperty(ExpressionBuilder.PROPERTY_UNARY_HIGH_PRECEDENCE, "false");
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
		System.clearProperty(ExpressionBuilder.PROPERTY_UNARY_HIGH_PRECEDENCE);
	}

	@Test
	public void testExpression38() throws Exception {
		String expr = "-3^(-2*3)";
		double expected = -Math.pow(3, -6);
		System.setProperty(ExpressionBuilder.PROPERTY_UNARY_HIGH_PRECEDENCE, "false");
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
		System.clearProperty(ExpressionBuilder.PROPERTY_UNARY_HIGH_PRECEDENCE);
		calc = new ExpressionBuilder(expr).build();
		assertTrue(Math.pow(-3, -6) == calc.calculate());
	}

	@Test
	public void testExpression39() throws Exception {
		String expr = "3^(2*-3)";
		double expected = Math.pow(3, -6);
		System.setProperty(ExpressionBuilder.PROPERTY_UNARY_HIGH_PRECEDENCE, "false");
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
		System.clearProperty(ExpressionBuilder.PROPERTY_UNARY_HIGH_PRECEDENCE);
		calc = new ExpressionBuilder(expr).build();
		assertTrue(Math.pow(3, 2 * -3) == calc.calculate());
	}

	@Test
	public void testExpression4() throws Exception {
		String expr;
		double expected;
		expr = "2+4 * 5";
		expected = 2 + 4 * 5;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test(expected=UnparsableExpressionException.class)
	public void testExpression40() throws Exception {
		String expr;
		double expected;
		// invalid!! exp4j still needs the explicit * operator
		expr = "2x + 5";
		expected = 2 * 4 + 5;
		
		Calculable calc = new ExpressionBuilder(expr)
			.withVariable("x", 4)
			.build();
	}

	@Test
	public void testExpression5() throws Exception {
		String expr;
		double expected;
		expr = "(2+4)*5";
		expected = (2 + 4) * 5;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression6() throws Exception {
		String expr;
		double expected;
		expr = "(2+4)*5 + 2.5*2";
		expected = (2 + 4) * 5 + 2.5 * 2;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression7() throws Exception {
		String expr;
		double expected;
		expr = "(2+4)*5 + 10/2";
		expected = (2 + 4) * 5 + 10 / 2;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression8() throws Exception {
		String expr;
		double expected;
		expr = "(2 * 3 +4)*5 + 10/2";
		expected = (2 * 3 + 4) * 5 + 10 / 2;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testExpression9() throws Exception {
		String expr;
		double expected;
		expr = "(2 * 3 +4)*5 +4 + 10/2";
		expected = (2 * 3 + 4) * 5 + 4 + 10 / 2;
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test(expected = UnparsableExpressionException.class)
	public void testFailUnknownFunction1() throws Exception {
		String expr;
		expr = "lig(1)";
		Calculable calc = new ExpressionBuilder(expr).build();
		calc.calculate();
	}

	@Test(expected = UnparsableExpressionException.class)
	public void testFailUnknownFunction2() throws Exception {
		String expr;
		expr = "galength(1)";
		new ExpressionBuilder(expr).build().calculate();
	}

	@Test(expected = UnparsableExpressionException.class)
	public void testFailUnknownFunction3() throws Exception {
		String expr;
		expr = "f(cos) = cos(cos)";
		new ExpressionBuilder(expr).build().calculate();
	}

	@Test
	public void testFunction1() throws Exception {
		String expr;
		expr = "cos(cos_1)";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("cos_1").build();
		assertTrue(calc.calculate(1d) == Math.cos(1d));
	}

	@Test
	public void testGetExpression() throws Exception {
		String expr;
		expr = "2 + 4 - cbrt(2^3)";
		String expected = "2 4 + 2 3 ^ cbrt -";
		Calculable calc = new ExpressionBuilder(expr).build();
		assertEquals(expected, calc.getExpression());
	}

	@Test
	public void testPostfix1() throws Exception {
		String expr;
		double expected;
		expr = "2.2232^0.1";
		expected = Math.pow(2.2232d, 0.1d);
		double actual = new ExpressionBuilder(expr).build().calculate();
		assertTrue(expected == actual);
	}

	@Test
	public void testPostfixEverything() throws Exception {
		String expr;
		double expected;
		expr = "(sin(12) + log(34)) * 3.42 - cos(2.234-log(2))";
		expected = (Math.sin(12) + Math.log(34)) * 3.42 - Math.cos(2.234 - Math.log(2));
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixExponentation1() throws Exception {
		String expr;
		double expected;
		expr = "2^3";
		expected = Math.pow(2, 3);
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixExponentation2() throws Exception {
		String expr;
		double expected;
		expr = "24 + 4 * 2^3";
		expected = 24 + 4 * Math.pow(2, 3);
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixExponentation3() throws Exception {
		String expr;
		double expected;
		double x = 4.334d;
		expr = "24 + 4 * 2^x";
		expected = 24 + 4 * Math.pow(2, x);
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		assertTrue(expected == calc.calculate(x));
	}

	@Test
	public void testPostfixExponentation4() throws Exception {
		String expr;
		double expected;
		double x = 4.334d;
		expr = "(24 + 4) * 2^log(x)";
		expected = (24 + 4) * Math.pow(2, Math.log(x));
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		assertTrue(expected == calc.calculate(x));
	}

	@Test
	public void testPostfixFunction1() throws Exception {
		String expr;
		double expected;
		expr = "log(1) * sin(0)";
		expected = Math.log(1) * Math.sin(0);
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction10() throws Exception {
		String expr;
		double expected;
		expr = "cbrt(x)";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.cbrt(x);
			assertTrue(expected == calc.calculate(x));
		}
	}

	@Test
	public void testPostfixFunction11() throws Exception {
		String expr;
		double expected;
		expr = "cos(x) - (1/cbrt(x))";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.cos(x) - (1 / Math.cbrt(x));
			assertTrue(expected == calc.calculate(x));
		}
	}

	@Test
	public void testPostfixFunction12() throws Exception {
		String expr;
		double expected;
		expr = "acos(x) * expm1(asin(x)) - exp(atan(x)) + floor(x) + cosh(x) - sinh(cbrt(x))";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.acos(x) * Math.expm1(Math.asin(x)) - Math.exp(Math.atan(x)) + Math.floor(x) + Math.cosh(x)
					- Math.sinh(Math.cbrt(x));
			if (Double.isNaN(expected)) {
				assertTrue(Double.isNaN(calc.calculate(x)));
			} else {
				assertTrue(expected == calc.calculate(x));
			}
		}
	}

	@Test
	public void testPostfixFunction13() throws Exception {
		String expr;
		double expected;
		expr = "acos(x)";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.acos(x);
			if (Double.isNaN(expected)) {
				assertTrue(Double.isNaN(calc.calculate(x)));
			} else {
				assertTrue(expected == calc.calculate(x));
			}
		}
	}

	@Test
	public void testPostfixFunction14() throws Exception {
		String expr;
		double expected;
		expr = " expm1(x)";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.expm1(x);
			if (Double.isNaN(expected)) {
				assertTrue(Double.isNaN(calc.calculate(x)));
			} else {
				assertTrue(expected == calc.calculate(x));
			}
		}
	}

	@Test
	public void testPostfixFunction15() throws Exception {
		String expr;
		double expected;
		expr = "asin(x)";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.asin(x);
			if (Double.isNaN(expected)) {
				assertTrue(Double.isNaN(calc.calculate(x)));
			} else {
				assertTrue(expected == calc.calculate(x));
			}
		}
	}

	@Test
	public void testPostfixFunction16() throws Exception {
		String expr;
		double expected;
		expr = " exp(x)";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.exp(x);
			assertTrue(expected == calc.calculate(x));
		}
	}

	@Test
	public void testPostfixFunction17() throws Exception {
		String expr;
		double expected;
		expr = "floor(x)";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.floor(x);
			assertTrue(expected == calc.calculate(x));
		}
	}

	@Test
	public void testPostfixFunction18() throws Exception {
		String expr;
		double expected;
		expr = " cosh(x)";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.cosh(x);
			assertTrue(expected == calc.calculate(x));
		}
	}

	@Test
	public void testPostfixFunction19() throws Exception {
		String expr;
		double expected;
		expr = "sinh(x)";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.sinh(x);
			assertTrue(expected == calc.calculate(x));
		}
	}

	@Test
	public void testPostfixFunction20() throws Exception {
		String expr;
		double expected;
		expr = "cbrt(x)";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.cbrt(x);
			assertTrue(expected == calc.calculate(x));
		}
	}

	@Test
	public void testPostfixFunction21() throws Exception {
		String expr;
		double expected;
		expr = "tanh(x)";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.tanh(x);
			assertTrue(expected == calc.calculate(x));
		}
	}

	@Test
	public void testPostfixFunction2() throws Exception {
		String expr;
		double expected;
		expr = "log(1)";
		expected = 0d;
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction3() throws Exception {
		String expr;
		double expected;
		expr = "sin(0)";
		expected = 0d;
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction5() throws Exception {
		String expr;
		double expected;
		expr = "ceil(2.3) +1";
		expected = Math.ceil(2.3) + 1;
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixFunction6() throws Exception {
		String expr;
		double expected;
		double x = 1.565d;
		double y = 2.1323d;
		expr = "ceil(x) + 1 / y * abs(1.4)";
		expected = Math.ceil(x) + 1 / y * Math.abs(1.4);
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x", "y").build();
		assertTrue(expected == calc.calculate(x, y));
	}

	@Test
	public void testPostfixFunction7() throws Exception {
		String expr;
		double expected;
		double x = Math.E;
		expr = "tan(x)";
		expected = Math.tan(x);
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		assertTrue(expected == calc.calculate(x));
	}

	@Test
	public void testPostfixFunction8() throws Exception {
		String expr;
		double expected;
		double e = Math.E;
		expr = "2^3.4223232 + tan(e)";
		expected = Math.pow(2, 3.4223232d) + Math.tan(Math.E);
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("e").build();
		assertTrue(expected == calc.calculate(e));
	}

	@Test
	public void testPostfixFunction9() throws Exception {
		String expr;
		double expected;
		double x = Math.E;
		expr = "cbrt(x)";
		expected = Math.cbrt(x);
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x").build();
		assertTrue(expected == calc.calculate(x));
	}

	@Test(expected = UnparsableExpressionException.class)
	public void testPostfixInvalidVariableName() throws Exception {
		String expr;
		double expected;
		double x = 4.5334332d;
		double log = Math.PI;
		expr = "x * pi";
		expected = x * log;
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x", "log").build();
		assertTrue(expected == calc.calculate(x, log));
	}

	@Test
	public void testPostfixParanthesis() throws Exception {
		String expr;
		double expected;
		expr = "(3 + 3 * 14) * (2 * (24-17) - 14)/((34) -2)";
		expected = (3 + 3 * 14) * (2 * (24 - 17) - 14) / ((34) - 2);
		Calculable calc = new ExpressionBuilder(expr).build();
		assertTrue(expected == calc.calculate());
	}

	@Test
	public void testPostfixVariables() throws Exception {
		String expr;
		double expected;
		double x = 4.5334332d;
		double pi = Math.PI;
		expr = "x * pi";
		expected = x * pi;
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x", "pi").build();
		assertTrue(expected == calc.calculate(x, pi));
	}

	@Test
	public void testBench1() throws Exception {
		if (System.getProperty("skipBenchmark") != null) {
			System.out.println(":: skipping naive benchmarks...");
			return;
		}
		System.out.println(":: running naive benchmarks, set -DskipBenchmark to skip");
		String expr = "log(x) - y * (sqrt(x^cos(y)))";
		Calculable calc = new ExpressionBuilder(expr).withVariableNames("x", "y").build();
		@SuppressWarnings("unused")
		double val;
		Random rnd = new Random();
		long timeout = 2;
		long time = System.currentTimeMillis() + (1000 * timeout);
		int count = 0;
		while (time > System.currentTimeMillis()) {
			calc.setVariable("x", rnd.nextDouble());
			calc.setVariable("y", rnd.nextDouble());
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
