package net.objecthunter.exp4j;

import net.objecthunter.exp4j.expression.DoubleExpression;
import net.objecthunter.exp4j.expression.ExpressionBuilder;
import org.junit.AfterClass;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Random;

import static java.lang.Math.*;

public class PerformanceTest {
    static final long timeout = 1000l;
    static final DecimalFormat format = new DecimalFormat("#.##");
    static double resultDoubleExp4j;
    static double resultJava;

    @Test
    public void testExp4jDoublePerformance() throws Exception {
        final String expr = "log(x) - y * (sqrt(x^cos(y)))";
        final DoubleExpression res = new ExpressionBuilder(expr)
                .variables(new String[]{"x", "y"}, new Double[]{0d, 0d})
                .buildDouble();
        final long start = System.currentTimeMillis();
        final Random rnd = new Random();
        int count = 0;
        while (System.currentTimeMillis() - start <= timeout) {
            //noinspection unchecked
            res.setVariable("x", rnd.nextDouble())
                    .setVariable("y", rnd.nextDouble())
                    .evaluate();
            count++;
        }
        resultDoubleExp4j = count;
        System.out.println("exp4j Double:\t\t"
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
        System.out.println("Java Math:\t\t\t"
                + format.format((double) count / 1000d) + "k calculations in "
                + timeout + " ms");
    }

    @AfterClass
    public static void printResultIfAny() {
        if (resultDoubleExp4j > 0 && resultJava > 0) {
            System.out
                    .println("Ratio exp4j Double to Java:\t"
                            + format.format((double) resultJava
                            / (double) resultDoubleExp4j));
            System.out.println("Perc. exp4j Double of Java:\t" + format.format(100
                    * (double) resultDoubleExp4j / (double) resultJava) + " %");
        }
    }
}
