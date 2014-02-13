package net.objecthunter.exp4j;

import static java.lang.Math.*;

import java.text.DecimalFormat;
import java.util.Random;

import net.objecthunter.exp4j.expression.DoubleExpression;
import net.objecthunter.exp4j.expression.ExpressionBuilder;

import org.junit.AfterClass;
import org.junit.Test;

public class PerformanceTest {
	static final long timeout = 1000l;
	static final DecimalFormat format = new DecimalFormat("#.##");
	static double resultExp4j;
	static double resultJava;

	@Test
	public void testExp4jPerformance1() throws Exception {
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
		resultExp4j = count;
		System.out.println("exp4j:\t\t\t"
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
		if (resultExp4j > 0 && resultJava > 0) {
			System.out
					.println("Ratio Java to exp4j:\t"
							+ format.format((double) resultJava
									/ (double) resultExp4j));
			System.out.println("Percent of Java:\t" + format.format(100
					* (double) resultExp4j / (double) resultJava) + " %");
		}
	}}
