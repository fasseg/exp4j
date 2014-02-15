package net.objecthunter.exp4j;

import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

import net.objecthunter.exp4j.expression.BigDecimalExpression;
import net.objecthunter.exp4j.expression.DoubleExpression;
import net.objecthunter.exp4j.expression.ExpressionBuilder;

import org.junit.AfterClass;
import org.junit.Test;

public class PerformanceTest {
	static final long timeout = 1000l;
	static final DecimalFormat format = new DecimalFormat("#.##");
	static double resultDoubleExp4j;
	static double resultBigDecimalExp4j;
	static double resultJava;

	@Test
	public void testExp4jDoublePerformance() throws Exception {
		final String expr = "log(x) - y * (sqrt(x^cos(y)))";
		final DoubleExpression res = new ExpressionBuilder(expr)
				.variables(new String[] { "x", "y" })
				.buildDouble();
		final long start = System.currentTimeMillis();
		final Random rnd = new Random();
		int count = 0;
		while (System.currentTimeMillis() - start <= timeout) {
			res.setVariable("x", rnd.nextDouble())
					.setVariable("y", rnd.nextDouble())
					.evaluate();
			count++;
		}
		resultDoubleExp4j = count;
		System.out.println("exp4j Double:\t\t\t"
				+ format.format((double) count / 1000d) + "k calculations in "
				+ timeout + " ms");
	}

	@Test
	public void testExp4jBigDecimalPerformance() throws Exception {
		final String expr = "log(x) - y * (sqrt(x^cos(y)))";
		final BigDecimalExpression res = new ExpressionBuilder(expr)
				.variables(new String[] { "x", "y" })
				.buildBigDecimal();
		final long start = System.currentTimeMillis();
		final Random rnd = new Random();
		int count = 0;
		while (System.currentTimeMillis() - start <= timeout) {
			res.setVariable("x", new BigDecimal(rnd.nextDouble()))
					.setVariable("y", new BigDecimal(rnd.nextDouble()))
					.evaluate();
			count++;
		}
		resultBigDecimalExp4j = count;
		System.out.println("exp4j BigDecimal:\t\t\t"
				+ format.format((double) count / 1000d) + "k calculations in "
				+ timeout + " ms");
	}

	@Test
	public void testJavaMathPerformance1() throws Exception {
		final long start = System.currentTimeMillis();
		final Random rnd = new Random();
		int count = 0;
		while (System.currentTimeMillis() - start <= timeout) {
			double x = rnd.nextDouble();
			double y = rnd.nextDouble();
			double res = log(x) - y * sqrt(pow(x, cos(y)));
			count++;
		}
		resultJava = count;
		System.out.println("Java Math:\t\t"
				+ format.format((double) count / 1000d) + "k calculations in "
				+ timeout + " ms");
	}

	@AfterClass
	public static void printResultIfAny() {
		if (resultDoubleExp4j > 0 && resultJava > 0) {
			System.out
					.println("Ratio Java to exp4j Double:\t"
							+ format.format((double) resultJava
									/ (double) resultDoubleExp4j));
			System.out.println("Percent of Java:\t" + format.format(100
					* (double) resultDoubleExp4j / (double) resultJava) + " %");
		}
		if (resultBigDecimalExp4j > 0 && resultJava > 0) {
			System.out
					.println("Ratio Java to exp4j BigDecimal:\t"
							+ format.format((double) resultJava
									/ (double) resultBigDecimalExp4j));
			System.out.println("Percent of Java:\t" + format.format(100
					* (double) resultBigDecimalExp4j / (double) resultJava) + " %");
		}
	}}
