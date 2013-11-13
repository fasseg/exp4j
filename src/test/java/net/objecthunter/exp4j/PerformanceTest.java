package net.objecthunter.exp4j;

import java.text.DecimalFormat;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import net.objecthunter.exp4j.calculable.Calculable;

import org.junit.Test;

public class PerformanceTest {
	
	public static long BENCH_TIME = 2l;
	public static String EXPRESSION = "log(x) - y * (sqrt(x^cos(y)))";
	
	@Test
	public void testBenchDouble() throws Exception {
		Calculable<Double> calc = new ExpressionBuilder<Double>(EXPRESSION, Double.class).variables("x", "y").build();
		@SuppressWarnings("unused")
		double val;
		Random rnd = new Random();
		long timeout = BENCH_TIME;
		long time = System.currentTimeMillis() + (1000 * timeout);
		int count = 0;
		while (time > System.currentTimeMillis()) {
			calc.setVariable("x", rnd.nextDouble());
			calc.setVariable("y", rnd.nextDouble());
			val = calc.calculate();
			count++;
		}
		double rate = count / timeout;
		System.out.println("exp4j Double\t\t" + (rate > 1000 ? new DecimalFormat("#.##").format(rate / 1000) + "k" : rate) + " calc/sec");
	}
	@Test
	public void testBenchFloat() throws Exception {
		Calculable<Float> calc = new ExpressionBuilder<Float>(EXPRESSION, Float.class).variables("x", "y").build();
		@SuppressWarnings("unused")
		double val;
		Random rnd = new Random();
		long timeout = BENCH_TIME;
		long time = System.currentTimeMillis() + (1000 * timeout);
		int count = 0;
		while (time > System.currentTimeMillis()) {
			calc.setVariable("x", rnd.nextFloat());
			calc.setVariable("y", rnd.nextFloat());
			val = calc.calculate();
			count++;
		}
		double rate = count / timeout;
		System.out.println("exp4j Float\t\t" + (rate > 1000 ? new DecimalFormat("#.##").format(rate / 1000) + "k" : rate) + " calc/sec");
	}
	@Test
	public void testBenchComplex() throws Exception {
		Calculable<ComplexNumber> calc = new ExpressionBuilder<ComplexNumber>(EXPRESSION, ComplexNumber.class).variables("x", "y").build();
		@SuppressWarnings("unused")
		ComplexNumber val;
		Random rnd = new Random();
		long timeout = BENCH_TIME;
		long time = System.currentTimeMillis() + (1000 * timeout);
		int count = 0;
		while (time > System.currentTimeMillis()) {
			calc.setVariable("x", new ComplexNumber(rnd.nextDouble(), rnd.nextDouble()));
			calc.setVariable("y", new ComplexNumber(rnd.nextDouble(), rnd.nextDouble()));
			val = calc.calculate();
			count++;
		}
		double rate = count / timeout;
		System.out.println("exp4j ComplexNumber\t" + (rate > 1000 ? new DecimalFormat("#.##").format(rate / 1000) + "k" : rate) + " calc/sec");
	}
	@Test
	public void testBenchJavaMath() throws Exception {
		long timeout = BENCH_TIME;
		long time = System.currentTimeMillis() + (1000 * timeout);
		double x, y, val, rate;
		int count = 0;
		Random rnd = new Random();
		while (time > System.currentTimeMillis()) {
			x = rnd.nextDouble();
			y = rnd.nextDouble();
			val = Math.log(x) - y * (Math.sqrt(Math.pow(x, Math.cos(y))));
			count++;
		}
		rate = count / timeout;
		System.out.println("Java Math\t\t" + (rate > 1000 ? new DecimalFormat("#.##").format(rate / 1000) + "k" : rate) + " calc/sec");
	}
	@Test
	public void testBenchJavaScript() throws Exception {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		long timeout = BENCH_TIME;
		long time = System.currentTimeMillis() + (1000 * timeout);
		double x, y, val, rate;
		int count = 0;
		Random rnd = new Random();
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
			System.out.println("JSR 223(Javascript)\t" + (rate > 1000 ? new DecimalFormat("#.##").format(rate / 1000) + "k" : rate) + " calc/sec");
		}
	}

}
