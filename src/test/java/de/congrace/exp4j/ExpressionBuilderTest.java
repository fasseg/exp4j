package de.congrace.exp4j;

import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.junit.Test;

public class ExpressionBuilderTest {
	@Test
	public void testCustomFunction1() throws Exception {
		CustomFunction custom = new CustomFunction("timespi") {
			@Override
			public double applyFunction(double ...values) {
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
			public double applyFunction(double ... values) {
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
			public double applyFunction(double ... values) {
				return values[0] * Math.E;
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public double applyFunction(double ... values) {
				return values[0] * Math.PI;
			}
		};
		Calculable calc = new ExpressionBuilder("foo(bar(x))").withVariable("x", 1).withCustomFunction(custom1).withCustomFunction(custom2).build();
		double result = calc.calculate();
		assertTrue(result == 1 * Math.E * Math.PI);
	}

	@Test
	public void testCustomFunction4() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public double applyFunction(double ... values) {
				return values[0] * Math.E;
			}
		};
		double varX = 32.24979131d;
		Calculable calc = new ExpressionBuilder("foo(log(x))").withVariable("x", varX).withCustomFunction(custom1).build();
		double result = calc.calculate();
		assertTrue(result == Math.log(varX) * Math.E);
	}

	@Test
	public void testCustomFunction5() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public double applyFunction(double ... values) {
				return values[0] * Math.E;
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public double applyFunction(double ... values) {
				return values[0] * Math.PI;
			}
		};
		double varX = 32.24979131d;
		Calculable calc = new ExpressionBuilder("bar(foo(log(x)))").withVariable("x", varX).withCustomFunction(custom1).withCustomFunction(custom2).build();
		double result = calc.calculate();
		assertTrue(result == Math.log(varX) * Math.E * Math.PI);
	}

	@Test
	public void testCustomFunction6() throws Exception {
		CustomFunction custom1 = new CustomFunction("foo") {
			@Override
			public double applyFunction(double ... values) {
				return values[0] * Math.E;
			}
		};
		CustomFunction custom2 = new CustomFunction("bar") {
			@Override
			public double applyFunction(double ... values) {
				return values[0] * Math.PI;
			}
		};
		double varX = 32.24979131d;
		Calculable calc = new ExpressionBuilder("bar(foo(log(x)))").withVariable("x", varX).withCustomFunctions(Arrays.asList(custom1, custom2)).build();
		double result = calc.calculate();
		assertTrue(result == Math.log(varX) * Math.E * Math.PI);
	}

	@Test
	public void testCustomFunction7() throws Exception {
		CustomFunction custom1 = new CustomFunction("half") {
			@Override
			public double applyFunction(double ... values) {
				return values[0]/2;
			}
		};
		Calculable calc = new ExpressionBuilder("half(x)").withVariable("x", 1d).withCustomFunction(custom1).build();
		assertTrue(0.5d == calc.calculate());
	}

	@Test(expected=InvalidCustomFunctionException.class)
	public void testCustomFunction8() throws Exception {
		CustomFunction custom1 = new CustomFunction("log") {
			@Override
			public double applyFunction(double ... values) {
				return values[0]/2;
			}
		};
		Calculable calc = new ExpressionBuilder("half(x)").withVariable("x", 1d).withCustomFunction(custom1).build();
		assertTrue(0.5d == calc.calculate());
	}

    @Test
    public void testCustomFunction10() throws Exception {
        CustomFunction custom1 = new CustomFunction("max",2) {
            @Override
            public double applyFunction(double ... values) {
                return values[0] < values[1] ? values[1] : values[0];
            }
        };
        Calculable calc = new ExpressionBuilder("max(x,y)").withVariable("x", 1d).withVariable("y", 2).withCustomFunction(custom1).build();
        assertTrue(2 == calc.calculate());
    }

    @Test
    public void testCustomFunction11() throws Exception {
        CustomFunction custom1 = new CustomFunction("power",2) {
            @Override
            public double applyFunction(double ... values) {
                return Math.pow(values[0],values[1]);
            }
        };
        Calculable calc = new ExpressionBuilder("power(x,y)").withVariable("x", 2d).withVariable("y", 4d).withCustomFunction(custom1).build();
        assertTrue(Math.pow(2,4) == calc.calculate());
    }

    @Test
    public void testCustomFunction12() throws Exception {
        CustomFunction custom1 = new CustomFunction("max",5) {
            @Override
            public double applyFunction(double ... values) {
                double max=values[0];
                for (int i=1;i<this.getArgumentCount();i++) {
                    if (values[i] > max) {
                        max=values[i];
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
        CustomFunction custom1 = new CustomFunction("max",3) {
            @Override
            public double applyFunction(double ... values) {
                double max=values[0];
                for (int i=1;i<this.getArgumentCount();i++) {
                    if (values[i] > max) {
                        max=values[i];
                    }
                }
                return max;
            }
        };
        double varX=Math.E;
        Calculable calc = new ExpressionBuilder("max(log(x),sin(x),x)").withVariable("x", varX).withCustomFunction(custom1).build();
        assertTrue(varX == calc.calculate());
    }

    @Test
    public void testCustomFunction14() throws Exception {
        CustomFunction custom1 = new CustomFunction("multiply",2) {
            @Override
            public double applyFunction(double ... values) {
                return values[0] * values[1];
            }
        };
        double varX=1;
        Calculable calc = new ExpressionBuilder("multiply(sin(x),x+1)").withVariable("x", varX).withCustomFunction(custom1).build();
        double expected=Math.sin(varX) * (varX +1);
        System.out.println("expected: "+ expected);
        System.out.println("exp4j: " + calc.calculate());
        System.out.println(calc.getExpression());
        assertTrue(expected == calc.calculate());
    }

    @Test
	public void testExpressionBuilder1() throws Exception {
		Calculable calc = new ExpressionBuilder("f(x,y)=7*x + 3*y").withVariable("x", 1).withVariable("y", 2).build();
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
		Calculable calc = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y").withVariable("x", varX).withVariable("y", varY).build();
		double result = calc.calculate();
		assertTrue(result == 7 * varX + 3 * varY - Math.pow(Math.log(varY / varX * 12), varY));
	}

	@Test
	public void testExpressionBuilder4() throws Exception {
		double varX = 1.3d;
		double varY = 4.22d;
		Calculable calc = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y").withVariable("x", varX).withVariable("y", varY).build();
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
		Calculable calc = new ExpressionBuilder("f(x)=log(sin(x))").withVariable("x", varX).build();
		double result = calc.calculate();
		assertTrue(result == Math.log(Math.sin(varX)));
	}

	@Test(expected = UnparsableExpressionException.class)
	public void testSameName() throws Exception {
		CustomFunction custom = new CustomFunction("bar") {
			@Override
			public double applyFunction(double ... values) {
				return values[0] / 2;
			}
		};
		double varBar = 1.3d;
		Calculable calc = new ExpressionBuilder("f(bar)=bar(bar)").withVariable("bar", varBar).withCustomFunction(custom).build();
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
	public void testBench1() throws Exception {
		if (System.getProperty("skipBenchmark") != null) {
			System.out.println(":: skipping naive benchmarks...");
			return;
		}
		System.out.println(":: running naive benchmarks, set -DskipBenchmark to skip");
		String expr = "foo(x,y)=log(x) - y * (sqrt(x^cos(y)))";
		Calculable calc = new ExpressionBuilder(expr).build();
		double val = 0;
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
		System.out.println("exp4j\t\t\t" + count + " [" + (rate > 1000 ? new DecimalFormat("#.##").format(rate / 1000) + "k" : rate) + " calc/sec]");
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
		System.out.println("Java Math\t\t" + count + " [" + (rate > 1000 ? new DecimalFormat("#.##").format(rate / 1000) + "k" : rate) + " calc/sec]");
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
			System.out.println("JSR 223(Javascript)\t" + count + " [" + (rate > 1000 ? new DecimalFormat("#.##").format(rate / 1000) + "k" : rate)
					+ " calc/sec]");
		}
	}
}
