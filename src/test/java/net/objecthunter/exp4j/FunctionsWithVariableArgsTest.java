package net.objecthunter.exp4j;

import net.objecthunter.exp4j.function.Function;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by fre on 28/05/15.
 */
public class FunctionsWithVariableArgsTest {
    List<Function> variableArgsFunctions = new LinkedList<Function>();
    @Before
    public void fillWithSomeFunctions(){
        Function avg = new Function("avg", 1,100) {

            @Override
            public double apply(double... args) {
                double sum = 0;
                for (double arg : args) {
                    sum += arg;
                }
                return sum / args.length;
            }
        };

        Function sum = new Function("sum", 1,100) {

            @Override
            public double apply(double... args) {
                double sum = 0;
                for (double arg : args) {
                    sum += arg;
                }
                return sum;
            }
        };

        Function max = new Function("max", 1,100) {

            @Override
            public double apply(double... args) {
                double max = args[0];
                for (double arg : args) {
                    if (arg > max)
                        max = arg;
                }
                return max;
            }
        };

        Function min = new Function("min", 1,100) {

            @Override
            public double apply(double... args) {
                double min = args[0];
                for (double arg : args) {
                    if (arg < min)
                        min = arg;
                }
                return min;
            }
        };

        variableArgsFunctions.add(min);
        variableArgsFunctions.add(max);
        variableArgsFunctions.add(avg);
        variableArgsFunctions.add(sum);
    }

    @Test
    public void testFunctionsWithVariableArgs1() throws Exception {
        double result,expected;
        result = new ExpressionBuilder("avg(1,2,3,4)")
                .functions(variableArgsFunctions)
                .build()
                .evaluate();

        expected = 2.5d;
        assertEquals(expected, result, 0d);

        result = new ExpressionBuilder("avg(1,1,1,1,1,1,1,1,1)")
                .functions(variableArgsFunctions)
                .build()
                .evaluate();

        expected = 1.0d;
        assertEquals(expected, result, 0d);

    }

    @Test
    public void testFunctionsWithVariableArgs2() throws Exception {
        double result,expected;
        result = new ExpressionBuilder("min(1,1,1,1,1,1,1,1)")
                .functions(variableArgsFunctions)
                .build()
                .evaluate();

        expected = 1.0d;
        assertEquals(expected, result, 0d);

        result = new ExpressionBuilder("min(1,1,1,1,1,1,1)")
                .functions(variableArgsFunctions)
                .build()
                .evaluate();

        expected = 1.0d;
        assertEquals(expected, result, 0d);

        result = new ExpressionBuilder("min(1,1,1,1)")
                .functions(variableArgsFunctions)
                .build()
                .evaluate();

        expected = 1.0d;
        assertEquals(expected, result, 0d);

        result = new ExpressionBuilder("max(1,1,1,1,1,1,1,1)")
                .functions(variableArgsFunctions)
                .build()
                .evaluate();

        expected = 1.0d;
        assertEquals(expected, result, 0d);

        result = new ExpressionBuilder("max(1,1,1,1,1,1)")
                .functions(variableArgsFunctions)
                .build()
                .evaluate();

        expected = 1.0d;
        assertEquals(expected, result, 0d);

    }
}
