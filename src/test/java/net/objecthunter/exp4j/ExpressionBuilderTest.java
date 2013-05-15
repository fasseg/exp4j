package net.objecthunter.exp4j;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import net.objecthunter.exp4j.calculable.Calculable;

import org.junit.Test;

public class ExpressionBuilderTest {

	@Test
	public void testFloatExpression1() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2+2", Float.class);
		float result = e.build().calculate();
		assertTrue(4f == result);
	}

	@Test
	public void testFloatExpression2() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2-2", Float.class);
		float result = e.build().calculate();
		assertTrue(0f == result);
	}

	@Test
	public void testFloatExpression3() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2/2", Float.class);
		float result = e.build().calculate();
		assertTrue(1f == result);
	}

	@Test
	public void testFloatExpression4() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2.45-1", Float.class);
		float result = e.build().calculate();
		assertTrue(1.45f == result);
	}

	@Test
	public void testFloatExpression5() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2.45-3", Float.class);
		float result = e.build().calculate();
		float expected = 2.45f - 3f;
		assertTrue("exp4j calulated " + result, expected == result);
	}

	@Test(expected = RuntimeException.class)
	public void testFloatExpression6() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2.45(-3", Float.class);
		float result = e.build().calculate();
	}

	@Test(expected = RuntimeException.class)
	public void testFloatExpression7() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2.45)-3", Float.class);
		float result = e.build().calculate();
	}

	@Test
	public void testFloatExpression8() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("sin(1.0)", Float.class);
		float result = e.build().calculate();
		float expected = (float) Math.sin(1.0);
		assertTrue("exp4j calulated " + result, expected == result);
	}

	@Test
	public void testFloatExpression9() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("sin(1.0) * 1 + 1", Float.class);
		float result = e.build().calculate();
		float expected = (float) Math.sin(1.0) * 1 + 1;
		assertTrue("exp4j calulated " + result, expected == result);
	}

	@Test
	public void testFloatExpression10() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("-1", Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, -1f == result);
	}

	@Test
	public void testFloatExpression11() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("7+-1", Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, 6f == result);
	}

	@Test
	public void testFloatExpression12() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("7+--1", Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, 8f == result);
	}

	@Test
	public void testFloatExpression13() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("7++1", Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, 8f == result);
	}

	@Test
	public void testFloatExpression14() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("sin(-1.0)", Float.class);
		float result = e.build().calculate();
		assertTrue("exp4j calulated " + result, (float) Math.sin(-1.0f) == result);
	}

	@Test
	public void testFloatExpression15() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("log(4) - 3 * (sqrt(3^cos(2)))", Float.class);
		float result = e.build().calculate();
		float expected = (float) Math.log(4f) - 3f * (float) (Math.sqrt((float) Math.pow(3f, (float) Math.cos(2f))));
		assertTrue("exp4j calulated " + result + " but expected was " + expected, expected == result);
	}

	@Test
	public void testFloatExpression16() {
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("sqrt(3^cos(2))", Float.class);
		float result = e.build().calculate();
		float expected = (float) Math.sqrt((float) Math.pow(3f, (float) Math.cos(2f)));
		assertTrue("exp4j calulated " + result + " but expected was " + expected, expected == result);
	}

	@Test
	public void testExpression1() {
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("2+2", Double.class);
		System.out.println(e.build().calculate());
	}

	@Test
	public void testExpression3() {
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("2+2", BigDecimal.class);
		System.out.println(e.build().calculate());
	}

	@Test
	public void testExpression4() {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("2+2", ComplexNumber.class);
		System.out.println(e.build().calculate());
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
			System.out.println("JSR 223(Javascript)\t" + count + " [" + (rate > 1000 ? new DecimalFormat("#.##").format(rate / 1000) + "k" : rate) + " calc/sec]");
		}
	}

}
