package net.objecthunter.exp4j;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import net.objecthunter.exp4j.calculable.Calculable;

import org.junit.Test;

public class PerformanceTest {

	public static long BENCH_TIME = 2l;
	public static String EXPRESSION = "log(x) - y * (sqrt(x^cos(y)))";

	@Test
	public void testBenches() throws Exception {
		System.out.println("Running naive benchmarks...");
		StringBuffer sb = new StringBuffer();
		Formatter fmt = new Formatter(sb);
		fmt.format("%n+------------------------+---------------------------+--------------------------+%n");
		fmt.format("| %-22s | %-25s | %-24s |%n", "Implementation", "Calculations per Second", "Percentage of Math");
		fmt.format("+------------------------+---------------------------+--------------------------+%n");
		System.out.print(sb.toString());
		sb.setLength(0);

		int math = benchJavaMath();
		double mathRate = (double) math / (double) BENCH_TIME;
		fmt.format("| %-22s | %25.2f | %22.2f %% |%n", "Java Math", mathRate, 100f);
		System.out.print(sb.toString());
		sb.setLength(0);

		int fl = benchFloat();
		double flRate = (double) fl / (double) BENCH_TIME;
		fmt.format("| %-22s | %25.2f | %22.2f %% |%n", "exp4j Float", flRate, flRate * 100 / mathRate);
		System.out.print(sb.toString());
		sb.setLength(0);

		int db = benchDouble();
		double dbRate = (double) db / (double) BENCH_TIME;
		fmt.format("| %-22s | %25.2f | %22.2f %% |%n", "exp4j Double", dbRate, dbRate * 100 / mathRate);
		System.out.print(sb.toString());
		sb.setLength(0);

		int cmp = benchComplex();
		double cmpRate = (double) cmp / (double) BENCH_TIME;
		fmt.format("| %-22s | %25.2f | %22.2f %% |%n", "exp4j Complex", cmpRate, cmpRate * 100 / mathRate);
		System.out.print(sb.toString());
		sb.setLength(0);

		int js = benchJavaScript();
		double jsRate = (double) js / (double) BENCH_TIME;
		fmt.format("| %-22s | %25.2f | %22.2f %% |%n", "JSR-223 (Java Script)", jsRate, jsRate * 100 / mathRate);
		fmt.format("+------------------------+---------------------------+--------------------------+%n");
		System.out.print(sb.toString());
}

	private int benchDouble() throws Exception {
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
		return count;
	}

	private int benchFloat() throws Exception {
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
		return count;
	}

	private int benchComplex() throws Exception {
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
		return count;
	}

	private int benchJavaMath() throws Exception {
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
		return count;
	}

	private int benchJavaScript() throws Exception {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		long timeout = BENCH_TIME;
		long time = System.currentTimeMillis() + (1000 * timeout);
		double x, y, val, rate;
		int count = 0;
		Random rnd = new Random();
		if (engine == null) {
			System.err.println("Unable to instantiate javascript engine. skipping naive JS bench.");
			return -1;
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
		}
		return count;
	}

}
