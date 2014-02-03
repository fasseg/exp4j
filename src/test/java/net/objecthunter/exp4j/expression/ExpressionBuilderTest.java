package net.objecthunter.exp4j.expression;

import static org.junit.Assert.assertEquals;

import java.rmi.server.Operation;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;

import org.junit.Test;

public class ExpressionBuilderTest {
    @Test
    public void testExpression1() throws Exception {
        String exp = "2+3";
        double result = new ExpressionBuilder(exp).buildDouble().evaluate();
        assertEquals(5d, result, 0d);
    }

    @Test
    public void testExpression2() throws Exception {
        String exp = "sin(0)";
        double result = new ExpressionBuilder(exp).buildDouble().evaluate();
        assertEquals(0d, result, 0d);
    }

    @Test
    public void testExpression3() throws Exception {
        String exp = "sum(1,1)";
        double result = new ExpressionBuilder(exp).function(new Function("sum",2) {
            @Override
            public double apply(double... args) {
                return args[0] + args[1];
            }
        }).buildDouble().evaluate();
        assertEquals(2d, result, 0d);
    }
    @Test
    public void testExpression4() throws Exception {
        String exp = "sum(1,2,3,4,5,6,7,8,9,10)";
        double result = new ExpressionBuilder(exp).function(new Function("sum",-1) {
            @Override
            public double apply(double... args) {
                double result = 0d;
                for (double d : args) {
                    result += d;
                }
                return result;
            }
        }).buildDouble().evaluate();
        assertEquals(55d, result, 0d);
    }
    @Test
    public void testExpression5() throws Exception {
        String expr = "2#2";
        Operator op = new Operator("#",2,true,Operators.PRECEDENCE_ADDITION) {
            @Override
            public double apply(double... args) {
                return args[0] + args[1];
            }
        };
        double result = new ExpressionBuilder(expr)
            .operator(op)
            .buildDouble()
            .evaluate();
        assertEquals(4d,result,0d);
    }
    @Test
    public void testExpression6() throws Exception {
        String expr = "11!";
        Operator op = new Operator("!",1,false,Operators.PRECEDENCE_MULTIPLICATION) {
            @Override
            public double apply(double... args) {
                int result = (int) args[0];
                if ((double) result != args[0]) {
                    throw new IllegalArgumentException("Factorial can only be calculated from an integer. maybe impleent the Gamma function");
                }
                result = 1;
                for (int i = 2; i<= args[0]; i++) {
                    result = result * i;
                }
                return (double) result;
            }
        };
        double result = new ExpressionBuilder(expr)
            .operator(op)
            .buildDouble()
            .evaluate();
        assertEquals(39916800d,result,0d);
    }
}
