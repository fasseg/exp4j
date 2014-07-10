/* 
 * Copyright 2014 Frank Asseg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package net.objecthunter.exp4j;

import static java.lang.Math.*;
import static org.junit.Assert.*;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

import org.junit.Test;

public class ExpressionBuilderTest {

    @Test
    public void testExpressionBuilder1() throws Exception {
        double result = new ExpressionBuilder("2+1")
                .build()
                .evaluate();
        assertEquals(3d, result, 0d);
    }

    @Test
    public void testExpressionBuilder2() throws Exception {
        double result = new ExpressionBuilder("cos(x)")
                .build()
                .variable("x", Math.PI)
                .evaluate();
        double expected = cos(Math.PI);
        assertEquals(-1d, result, 0d);
    }

    @Test
    public void testExpressionBuilder3() throws Exception {
        double x = Math.PI;
        double result = new ExpressionBuilder("sin(x)-log(3*x/4)")
                .build()
                .variable("x", x)
                .evaluate();

        double expected = sin(x) - log(3 * x / 4);
        assertEquals(expected, result, 0d);
    }

    @Test
    public void testExpressionBuilder4() throws Exception {
        Function log2 = new Function("log2", 1) {

            @Override
            public double apply(double... args) {
                return Math.log(args[0]) / Math.log(2);
            }
        };
        double result = new ExpressionBuilder("log2(4)")
                .function(log2)
                .build()
                .evaluate();

        double expected = 2;
        assertEquals(expected, result, 0d);
    }

    @Test
    public void testExpressionBuilder5() throws Exception {
        Function avg = new Function("avg", 4) {

            @Override
            public double apply(double... args) {
                double sum = 0;
                for (double arg : args) {
                    sum += arg;
                }
                return sum / args.length;
            }
        };
        double result = new ExpressionBuilder("avg(1,2,3,4)")
                .function(avg)
                .build()
                .evaluate();

        double expected = 2.5d;
        assertEquals(expected, result, 0d);
    }

    @Test
    public void testExpressionBuilder6() throws Exception {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        double result = new ExpressionBuilder("3!")
                .operator(factorial)
                .build()
                .evaluate();

        double expected = 6d;
        assertEquals(expected, result, 0d);
    }

    @Test
    public void testExpressionBuilder7() throws Exception {
        ValidationResult res = new ExpressionBuilder("x")
                .build()
                .validate();
        assertFalse(res.isValid());
        assertEquals(res.getErrors().size(), 1);
    }

    @Test
    public void testExpressionBuilder8() throws Exception {
        ValidationResult res = new ExpressionBuilder("x*y*z")
                .build()
                .validate();
        assertFalse(res.isValid());
        assertEquals(res.getErrors().size(), 3);
    }

    @Test
    public void testExpressionBuilder9() throws Exception {
        ValidationResult res = new ExpressionBuilder("x")
                .build()
                .variable("x", 1d)
                .validate();
        assertTrue(res.isValid());
    }

    @Test
    public void testExpressionBuilder10() throws Exception {
        double result = new ExpressionBuilder("1e1")
                .build()
                .evaluate();
        assertEquals(10d, result, 0d);
    }

    @Test
    public void testExpressionBuilder11() throws Exception {
        double result = new ExpressionBuilder("1.11e-1")
                .build()
                .evaluate();
        assertEquals(0.111d, result, 0d);
    }

    @Test
    public void testExpressionBuilder12() throws Exception {
        double result = new ExpressionBuilder("1.11e+1")
                .build()
                .evaluate();
        assertEquals(11.1d, result, 0d);
    }

    @Test
    public void testExpressionBuilder13() throws Exception {
        double result = new ExpressionBuilder("-3^2")
                .build()
                .evaluate();
        assertEquals(9d, result, 0d);
    }

    @Test
    public void testExpressionBuilder14() throws Exception {
        double result = new ExpressionBuilder("-(3^2)")
                .build()
                .evaluate();
        assertEquals(-9d, result, 0d);
    }

    @Test(expected = ArithmeticException.class)
    public void testExpressionBuilder15() throws Exception {
        double result = new ExpressionBuilder("-3/0")
                .build()
                .evaluate();
    }

    @Test
    public void testExpressionBuilder16() throws Exception {
        double result = new ExpressionBuilder("log(x) - y * (sqrt(x^cos(y)))")
                .build()
                .variable("x", 1d)
                .variable("y", 2d)
                .evaluate();
    }

    /* legacy tests from earlier exp4j versions */

    @Test
    public void testFunction1() throws Exception {
        Function custom = new Function("timespi") {

            @Override
            public double apply(double... values) {
                return values[0] * Math.PI;
            }
        };
        Expression e = new ExpressionBuilder("timespi(x)").function(custom).build().variable("x", 1);
        double result = e.evaluate();
        assertTrue(result == Math.PI);
    }

    @Test
    public void testFunction2() throws Exception {
        Function custom = new Function("loglog") {

            @Override
            public double apply(double... values) {
                return Math.log(Math.log(values[0]));
            }
        };
        Expression e = new ExpressionBuilder("loglog(x)").function(custom).build().variable("x", 1);
        double result = e.evaluate();
        assertTrue(result == Math.log(Math.log(1)));
    }

    @Test
    public void testFunction3() throws Exception {
        Function custom1 = new Function("foo") {

            @Override
            public double apply(double... values) {
                return values[0] * Math.E;
            }
        };
        Function custom2 = new Function("bar") {

            @Override
            public double apply(double... values) {
                return values[0] * Math.PI;
            }
        };
        Expression e = new ExpressionBuilder("foo(bar(x))").function(custom1)
                .function(custom2).build().variable("x", 1);
        double result = e.evaluate();
        assertTrue(result == 1 * Math.E * Math.PI);
    }

    @Test
    public void testFunction4() throws Exception {
        Function custom1 = new Function("foo") {

            @Override
            public double apply(double... values) {
                return values[0] * Math.E;
            }
        };
        double varX = 32.24979131d;
        Expression e = new ExpressionBuilder("foo(log(x))").function(custom1)
                .build().variable("x", varX);
        double result = e.evaluate();
        assertTrue(result == Math.log(varX) * Math.E);
    }

    @Test
    public void testFunction5() throws Exception {
        Function custom1 = new Function("foo") {

            @Override
            public double apply(double... values) {
                return values[0] * Math.E;
            }
        };
        Function custom2 = new Function("bar") {

            @Override
            public double apply(double... values) {
                return values[0] * Math.PI;
            }
        };
        double varX = 32.24979131d;
        Expression e = new ExpressionBuilder("bar(foo(log(x)))").function(custom1)
                .function(custom2).build().variable("x", varX);
        double result = e.evaluate();
        assertTrue(result == Math.log(varX) * Math.E * Math.PI);
    }

    @Test
    public void testFunction6() throws Exception {
        Function custom1 = new Function("foo") {

            @Override
            public double apply(double... values) {
                return values[0] * Math.E;
            }
        };
        Function custom2 = new Function("bar") {

            @Override
            public double apply(double... values) {
                return values[0] * Math.PI;
            }
        };
        double varX = 32.24979131d;
        Expression e = new ExpressionBuilder("bar(foo(log(x)))")
                .functions(custom1, custom2).build().variable("x", varX);
        double result = e.evaluate();
        assertTrue(result == Math.log(varX) * Math.E * Math.PI);
    }

    @Test
    public void testFunction7() throws Exception {
        Function custom1 = new Function("half") {

            @Override
            public double apply(double... values) {
                return values[0] / 2;
            }
        };
        Expression e = new ExpressionBuilder("half(x)").function(custom1).build().variable("x", 1d);
        assertTrue(0.5d == e.evaluate());
    }

    @Test
    public void testFunction10() throws Exception {
        Function custom1 = new Function("max", 2) {

            @Override
            public double apply(double... values) {
                return values[0] < values[1] ? values[1] : values[0];
            }
        };
        Expression e =
                new ExpressionBuilder("max(x,y)").function(custom1).build().variable("x", 1d).variable("y", 2d);
        assertTrue(2 == e.evaluate());
    }

    @Test
    public void testFunction11() throws Exception {
        Function custom1 = new Function("power", 2) {

            @Override
            public double apply(double... values) {
                return Math.pow(values[0], values[1]);
            }
        };
        Expression e =
                new ExpressionBuilder("power(x,y)").function(custom1).build().variable("x", 1d).variable("y", 4d);
        assertTrue(Math.pow(2, 4) == e.evaluate());
    }

    @Test
    public void testFunction12() throws Exception {
        Function custom1 = new Function("max", 5) {

            @Override
            public double apply(double... values) {
                double max = values[0];
                for (int i = 1; i < numArguments; i++) {
                    if (values[i] > max) {
                        max = values[i];
                    }
                }
                return max;
            }
        };
        Expression e = new ExpressionBuilder("max(1,2.43311,51.13,43,12)").function(custom1).build();
        assertTrue(51.13d == e.evaluate());
    }

    @Test
    public void testFunction13() throws Exception {
        Function custom1 = new Function("max", 3) {

            @Override
            public double apply(double... values) {
                double max = values[0];
                for (int i = 1; i < numArguments; i++) {
                    if (values[i] > max) {
                        max = values[i];
                    }
                }
                return max;
            }
        };
        double varX = Math.E;
        Expression e = new ExpressionBuilder("max(log(x),sin(x),x)")
                .function(custom1).build().variable("x", varX);
        assertTrue(varX == e.evaluate());
    }

    @Test
    public void testFunction14() throws Exception {
        Function custom1 = new Function("multiply", 2) {

            @Override
            public double apply(double... values) {
                return values[0] * values[1];
            }
        };
        double varX = 1;
        Expression e = new ExpressionBuilder("multiply(sin(x),x+1)")
                .function(custom1).build().variable("x", varX);
        double expected = Math.sin(varX) * (varX + 1);
        double actual = e.evaluate();
        assertTrue(expected == actual);
    }

    @Test
    public void testFunction15() throws Exception {
        Function custom1 = new Function("timesPi") {

            @Override
            public double apply(double... values) {
                return values[0] * Math.PI;
            }
        };
        double varX = 1;
        Expression e = new ExpressionBuilder("timesPi(x^2)").function(custom1)
                .build().variable("x", varX);
        double expected = varX * Math.PI;
        double actual = e.evaluate();
        assertTrue(expected == actual);
    }

    @Test
    public void testFunction16() throws Exception {
        Function custom1 = new Function("multiply", 3) {

            @Override
            public double apply(double... values) {
                return values[0] * values[1] * values[2];
            }
        };
        double varX = 1;
        Expression e = new ExpressionBuilder("multiply(sin(x),x+1^(-2),log(x))")
                .function(custom1).build().variable("x", varX);
        double expected = Math.sin(varX) * Math.pow((varX + 1), -2) * Math.log(varX);
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testFunction17() throws Exception {
        Function custom1 = new Function("timesPi") {

            @Override
            public double apply(double... values) {
                return values[0] * Math.PI;
            }
        };
        double varX = Math.E;
        Expression e = new ExpressionBuilder("timesPi(log(x^(2+1)))")
                .function(custom1).build().variable("x", varX);
        double expected = Math.log(Math.pow(varX, 3)) * Math.PI;
        assertTrue(expected == e.evaluate());
    }

    // thanks to Marcin Domanski who issued
    // http://jira.congrace.de/jira/browse/EXP-11
    // i have this test, which fails in 0.2.9
    @Test
    public void testFunction18() throws Exception {
        Function minFunction = new Function("min", 2) {

            @Override
            public double apply(double[] values) {
                double currentMin = Double.POSITIVE_INFINITY;
                for (double value : values) {
                    currentMin = Math.min(currentMin, value);
                }
                return currentMin;
            }
        };
        ExpressionBuilder b = new ExpressionBuilder("-min(5, 0) + 10").function(minFunction);
        double calculated = b.build().evaluate();
        assertTrue(calculated == 10);
    }

    // thanks to Sylvain Machefert who issued
    // http://jira.congrace.de/jira/browse/EXP-11
    // i have this test, which fails in 0.3.2
    @Test
    public void testFunction19() throws Exception {
        Function minFunction = new Function("power", 2) {

            @Override
            public double apply(double[] values) {
                return Math.pow(values[0], values[1]);
            }
        };
        ExpressionBuilder b = new ExpressionBuilder("power(2,3)").function(minFunction);
        double calculated = b.build().evaluate();
        assertTrue(calculated == Math.pow(2, 3));
    }

    // thanks to Narendra Harmwal who noticed that getArgumentCount was not
    // implemented
    // this test has been added in 0.3.5
    @Test
    public void testFunction20() throws Exception {
        Function maxFunction = new Function("max", 3) {

            @Override
            public double apply(double... values) {
                double max = values[0];
                for (int i = 1; i < numArguments; i++) {
                    if (values[i] > max) {
                        max = values[i];
                    }
                }
                return max;
            }
        };
        ExpressionBuilder b = new ExpressionBuilder("max(1,2,3)").function(maxFunction);
        double calculated = b.build().evaluate();
        assertTrue(maxFunction.getNumArguments() == 3);
        assertTrue(calculated == 3);
    }

    @Test
    public void testOperators1() throws Exception {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        Expression e = new ExpressionBuilder("1!").operator(factorial).build();
        assertTrue(1d == e.evaluate());
        e = new ExpressionBuilder("2!").operator(factorial).build();
        assertTrue(2d == e.evaluate());
        e = new ExpressionBuilder("3!").operator(factorial).build();
        assertTrue(6d == e.evaluate());
        e = new ExpressionBuilder("4!").operator(factorial).build();
        assertTrue(24d == e.evaluate());
        e = new ExpressionBuilder("5!").operator(factorial).build();
        assertTrue(120d == e.evaluate());
        e = new ExpressionBuilder("11!").operator(factorial).build();
        assertTrue(39916800d == e.evaluate());
    }

    @Test
    public void testOperators2() throws Exception {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };
        Expression e = new ExpressionBuilder("2^2!").operator(factorial).build();
        assertTrue(4d == e.evaluate());
        e = new ExpressionBuilder("2!^2").operator(factorial).build();
        assertTrue(4d == e.evaluate());
        e = new ExpressionBuilder("-(3!)^-1").operator(factorial).build();
        double actual = e.evaluate();
        assertEquals(Math.pow(-6d, -1), actual, 0d);
    }

    @Test
    public void testOperators3() throws Exception {
        Operator gteq = new Operator(">=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {

            @Override
            public double apply(double[] values) {
                if (values[0] >= values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };
        Expression e = new ExpressionBuilder("1>=2").operator(gteq).build();
        assertTrue(0d == e.evaluate());
        e = new ExpressionBuilder("2>=1").operator(gteq).build();
        assertTrue(1d == e.evaluate());
        e = new ExpressionBuilder("-2>=1").operator(gteq).build();
        assertTrue(0d == e.evaluate());
        e = new ExpressionBuilder("-2>=-1").operator(gteq).build();
        assertTrue(0d == e.evaluate());
    }

    @Test
    public void testModulo1() throws Exception {
        double result = new ExpressionBuilder("33%(20/2)%2").build().evaluate();
        assertTrue(result == 1d);
    }

    @Test
    public void testOperators4() throws Exception {
        Operator greaterEq = new Operator(">=", 2, true, 4) {

            @Override
            public double apply(double[] values) {
                if (values[0] >= values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };
        Operator greater = new Operator(">", 2, true, 4) {

            @Override
            public double apply(double[] values) {
                if (values[0] > values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };
        Operator newPlus = new Operator(">=>", 2, true, 4) {

            @Override
            public double apply(double[] values) {
                return values[0] + values[1];
            }
        };
        Expression e = new ExpressionBuilder("1>2").operator(greater).build();
        assertTrue(0d == e.evaluate());
        e = new ExpressionBuilder("2>=2").operator(greaterEq).build();
        assertTrue(1d == e.evaluate());
        e = new ExpressionBuilder("1>=>2").operator(newPlus).build();
        assertTrue(3d == e.evaluate());
        e = new ExpressionBuilder("1>=>2>2").operator(greater).operator(newPlus).build();
        assertTrue(1d == e.evaluate());
        e = new ExpressionBuilder("1>=>2>2>=1").operator(greater).operator(newPlus)
                .operator(greaterEq).build();
        assertTrue(1d == e.evaluate());
        e = new ExpressionBuilder("1 >=> 2 > 2 >= 1").operator(greater).operator(newPlus)
                .operator(greaterEq).build();
        assertTrue(1d == e.evaluate());
        e = new ExpressionBuilder("1 >=> 2 >= 2 > 1").operator(greater).operator(newPlus)
                .operator(greaterEq).build();
        assertTrue(0d == e.evaluate());
        e = new ExpressionBuilder("1 >=> 2 >= 2 > 0").operator(greater).operator(newPlus)
                .operator(greaterEq).build();
        assertTrue(1d == e.evaluate());
        e = new ExpressionBuilder("1 >=> 2 >= 2 >= 1").operator(greater).operator(newPlus)
                .operator(greaterEq).build();
        assertTrue(1d == e.evaluate());
    }

    @Test(expected = Exp4jException.class)
    public void testInvalidOperator1() throws Exception {
        Operator fail = new Operator("2", 2, true, 1) {

            @Override
            public double apply(double[] values) {
                return 0;
            }
        };
        new ExpressionBuilder("1").operator(fail).build();
    }

    @Test(expected = Exp4jException.class)
    public void testInvalidFunction1() throws Exception {
        Function func = new Function("1gd") {

            @Override
            public double apply(double... args) {
                return 0;
            }
        };
    }

    @Test(expected = Exp4jException.class)
    public void testInvalidFunction2() throws Exception {
        Function func = new Function("+1gd") {

            @Override
            public double apply(double... args) {
                return 0;
            }
        };
    }

    @Test
    public void testExpressionBuilder01() throws Exception {
        Expression e = new ExpressionBuilder("7*x + 3*y").build().variable("x",1).variable("y",2);
        double result = e.evaluate();
        assertTrue(result == 13d);
    }

    @Test
    public void testExpressionBuilder02() throws Exception {
        Expression e = new ExpressionBuilder("7*x + 3*y").build().variable("x",1).variable("y",2);
        double result = e.evaluate();
        assertTrue(result == 13d);
    }

    @Test
    public void testExpressionBuilder03() throws Exception {
        double varX = 1.3d;
        double varY = 4.22d;
        Expression e = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y").build().variable("x",varX).variable("y",varY);
        double result = e.evaluate();
        assertTrue(result == 7 * varX + 3 * varY - Math.pow(Math.log(varY / varX * 12), varY));
    }

    @Test
    public void testExpressionBuilder04() throws Exception {
        double varX = 1.3d;
        double varY = 4.22d;
        Expression e = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y").build().variable("x",varX).variable("y",varY);
        double result = e.evaluate();
        assertTrue(result == 7 * varX + 3 * varY - Math.pow(Math.log(varY / varX * 12), varY));
        varX = 1.79854d;
        varY = 9281.123d;
        e.variable("x", varX);
        e.variable("y", varY);
        result = e.evaluate();
        assertTrue(result == 7 * varX + 3 * varY - Math.pow(Math.log(varY / varX * 12), varY));
    }

    @Test
    public void testExpressionBuilder05() throws Exception {
        double varX = 1.3d;
        double varY = 4.22d;
        Expression e = new ExpressionBuilder("3*y").build().variable("x",varX).variable("y",varY);
        double result = e.evaluate();
        assertTrue(result == 3 * varY);
    }

    @Test
    public void testExpressionBuilder06() throws Exception {
        double varX = 1.3d;
        double varY = 4.22d;
        double varZ = 4.22d;
        Expression e = new ExpressionBuilder("x * y * z").build();
        e.variable("x", varX);
        e.variable("y", varY);
        e.variable("z", varZ);
        double result = e.evaluate();
        assertTrue(result == varX * varY * varZ);
    }

    @Test
    public void testExpressionBuilder07() throws Exception {
        double varX = 1.3d;
        Expression e = new ExpressionBuilder("log(sin(x))").build().variable("x",varX);
        double result = e.evaluate();
        assertTrue(result == Math.log(Math.sin(varX)));
    }

    @Test
    public void testExpressionBuilder08() throws Exception {
        double varX = 1.3d;
        Expression e = new ExpressionBuilder("log(sin(x))").build().variable("x",varX);
        double result = e.evaluate();
        assertTrue(result == Math.log(Math.sin(varX)));
    }

    @Test(expected = Exp4jException.class)
    public void testSameName() throws Exception {
        Function custom = new Function("bar") {

            @Override
            public double apply(double... values) {
                return values[0] / 2;
            }
        };
        double varBar = 1.3d;
        Expression e = new ExpressionBuilder("f(bar)=bar(bar)")
                .function(custom).build().variable("bar",varBar);
        double result = e.evaluate();
        assertTrue(result == varBar / 2);
    }

    @Test(expected = Exp4jException.class)
    public void testInvalidFunction() throws Exception {
        double varY = 4.22d;
        Expression e = new ExpressionBuilder("3*invalid_function(y)").build().variable("y", varY);
        e.evaluate();
    }

    @Test(expected = Exp4jException.class)
    public void testMissingVar() throws Exception {
        double varY = 4.22d;
        Expression e = new ExpressionBuilder("3*y*z").build().variable("y",varY);
        e.evaluate();
    }

    @Test
    public void testExpression1() throws Exception {
        String expr;
        double expected;
        expr = "2 + 4";
        expected = 6d;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression10() throws Exception {
        String expr;
        double expected;
        expr = "1 * 1.5 + 1";
        expected = 1 * 1.5 + 1;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression11() throws Exception {
        double x = 1d;
        double y = 2d;
        String expr = "log(x) ^ sin(y)";
        double expected = Math.pow(Math.log(x), Math.sin(y));
        Expression e = new ExpressionBuilder(expr).build().variable("x",x).variable("y",y);
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression12() throws Exception {
        String expr = "log(2.5333333333)^(0-1)";
        double expected = Math.pow(Math.log(2.5333333333d), -1);
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression13() throws Exception {
        String expr = "2.5333333333^(0-1)";
        double expected = Math.pow(2.5333333333d, -1);
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression14() throws Exception {
        String expr = "2 * 17.41 + (12*2)^(0-1)";
        double expected = 2 * 17.41d + Math.pow((12 * 2), -1);
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression15() throws Exception {
        String expr = "2.5333333333 * 17.41 + (12*2)^log(2.764)";
        double expected = 2.5333333333d * 17.41d + Math.pow((12 * 2), Math.log(2.764d));
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression16() throws Exception {
        String expr = "2.5333333333/2 * 17.41 + (12*2)^(log(2.764) - sin(5.6664))";
        double expected = 2.5333333333d / 2 * 17.41d + Math.pow((12 * 2), Math.log(2.764d) - Math.sin(5.6664d));
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression17() throws Exception {
        String expr = "x^2 - 2 * y";
        double x = Math.E;
        double y = Math.PI;
        double expected = x * x - 2 * y;
        Expression e = new ExpressionBuilder(expr).build().variable("x",x).variable("y",y);
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression18() throws Exception {
        String expr = "-3";
        double expected = -3;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression19() throws Exception {
        String expr = "-3 * -24.23";
        double expected = -3 * -24.23d;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression2() throws Exception {
        String expr;
        double expected;
        expr = "2+3*4-12";
        expected = 2 + 3 * 4 - 12;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression20() throws Exception {
        String expr = "-2 * 24/log(2) -2";
        double expected = -2 * 24 / Math.log(2) - 2;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression21() throws Exception {
        String expr = "-2 *33.34/log(x)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr).build().variable("x",x);
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression22() throws Exception {
        String expr = "-2 *33.34/log(x)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr).build().variable("x",x);
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression23() throws Exception {
        String expr = "-2 *33.34/(log(foo)^-2 + 14 *6) - sin(foo)";
        double x = 1.334d;
        double expected = -2 * 33.34 / (Math.pow(Math.log(x), -2) + 14 * 6) - Math.sin(x);
        Expression e = new ExpressionBuilder(expr).build().variable("foo",x);
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression24() throws Exception {
        String expr = "3+4-log(23.2)^(2-1) * -1";
        double expected = 3 + 4 - Math.pow(Math.log(23.2), (2 - 1)) * -1;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression25() throws Exception {
        String expr = "+3+4-+log(23.2)^(2-1) * + 1";
        double expected = 3 + 4 - Math.log(23.2d);
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression26() throws Exception {
        String expr = "14 + -(1 / 2.22^3)";
        double expected = 14 + -(1d / Math.pow(2.22d, 3d));
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression27() throws Exception {
        String expr = "12^-+-+-+-+-+-+---2";
        double expected = Math.pow(12, -2);
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression28() throws Exception {
        String expr = "12^-+-+-+-+-+-+---2 * (-14) / 2 ^ -log(2.22323) ";
        double expected = Math.pow(12, -2) * -14 / Math.pow(2, -Math.log(2.22323));
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression29() throws Exception {
        String expr = "24.3343 % 3";
        double expected = 24.3343 % 3;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test(expected = Exp4jException.class)
    public void testVarname1() throws Exception {
        String expr = "12.23 * foo.bar";
        Expression e = new ExpressionBuilder(expr)
                .build()
                .variable("foo.bar", 1d);
        assertTrue(12.23 == e.evaluate());
    }

    public void testVarname2() throws Exception {
        String expr = "12.23 * foo.bar";
        Expression e = new ExpressionBuilder(expr)
                .build()
                .variable("_foo", 1d);
        assertTrue(12.23 == e.evaluate());
    }

    @Test
    public void testExpression3() throws Exception {
        String expr;
        double expected;
        expr = "2+4*5";
        expected = 2 + 4 * 5;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression30() throws Exception {
        String expr = "24.3343 % 3 * 20 ^ -(2.334 % log(2 / 14))";
        double expected = 24.3343d % 3 * Math.pow(20, -(2.334 % Math.log(2d / 14d)));
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression31() throws Exception {
        String expr = "-2 *33.34/log(y_x)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr).build().variable("y_x", x);
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression32() throws Exception {
        String expr = "-2 *33.34/log(y_2x)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr).build().variable("y_2x",x);
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression33() throws Exception {
        String expr = "-2 *33.34/log(_y)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr).build().variable("_y",x);
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression34() throws Exception {
        String expr = "-2 + + (+4) +(4)";
        double expected = -2 + 4 + 4;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }
    @Test
    public void testExpression40() throws Exception {
        String expr = "1e1";
        double expected = 10d;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression41() throws Exception {
        String expr = "1e-1";
        double expected = 0.1d;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    /**
     * Added tests for expressions with scientific notation see http://jira.congrace.de/jira/browse/EXP-17
     * 
     * @throws Exception
     */
    @Test
    public void testExpression42() throws Exception {
        String expr = "7.2973525698e-3";
        double expected = 7.2973525698e-3d;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression43() throws Exception {
        String expr = "6.02214E23";
        double expected = 6.02214e23d;
        Expression e = new ExpressionBuilder(expr).build();
        double result = e.evaluate();
        assertTrue(expected == result);
    }

    @Test
    public void testExpression44() throws Exception {
        String expr = "6.02214E23";
        double expected = 6.02214e23d;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test(expected = NumberFormatException.class)
    public void testExpression45() throws Exception {
        String expr = "6.02214E2E3";
        new ExpressionBuilder(expr).build();
    }

    @Test(expected = NumberFormatException.class)
    public void testExpression46() throws Exception {
        String expr = "6.02214e2E3";
        new ExpressionBuilder(expr).build();
    }

    // tests for EXP-20: No exception is thrown for unmatched parenthesis in
    // build
    // Thanks go out to maheshkurmi for reporting
    @Test(expected = Exp4jException.class)
    public void testExpression48() throws Exception {
        String expr = "(1*2";
        Expression e = new ExpressionBuilder(expr).build();
        double result = e.evaluate();
    }

    @Test(expected = Exp4jException.class)
    public void testExpression49() throws Exception {
        String expr = "{1*2";
        Expression e = new ExpressionBuilder(expr).build();
        double result = e.evaluate();
    }

    @Test(expected = Exp4jException.class)
    public void testExpression50() throws Exception {
        String expr = "[1*2";
        Expression e = new ExpressionBuilder(expr).build();
        double result = e.evaluate();
    }

    @Test(expected = Exp4jException.class)
    public void testExpression51() throws Exception {
        String expr = "(1*{2+[3}";
        Expression e = new ExpressionBuilder(expr).build();
        double result = e.evaluate();
    }

    @Test(expected = Exp4jException.class)
    public void testExpression52() throws Exception {
        String expr = "(1*(2+(3";
        Expression e = new ExpressionBuilder(expr).build();
        double result = e.evaluate();
    }

    @Test(expected = Exp4jException.class)
    public void testExpression53() throws Exception {
        String expr = "14 * 2x";
        new ExpressionBuilder(expr).build().evaluate();
    }

    @Test(expected = Exp4jException.class)
    public void testExpression54() throws Exception {
        String expr = "2 ((-(x)))";
        Expression e = new ExpressionBuilder(expr).build();
    }

    @Test(expected = Exp4jException.class)
    public void testExpression55() throws Exception {
        String expr = "2 sin(x)";
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(Math.sin(2d) * 2 == e.evaluate());
    }

    @Test(expected = Exp4jException.class)
    public void testExpression56() throws Exception {
        String expr = "2 sin(3x)";
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(Math.sin(6d) * 2 == e.evaluate());
    }

    // Thanks go out to Johan Björk for reporting the division by zero problem EXP-22
    // https://www.objecthunter.net/jira/browse/EXP-22
    @Test(expected = ArithmeticException.class)
    public void testExpression57() throws Exception {
        String expr = "1 / 0";
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(Double.POSITIVE_INFINITY == e.evaluate());
    }

    @Test
    public void testExpression58() throws Exception {
        String expr = "17 * sqrt(-1) * 12";
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(Double.isNaN(e.evaluate()));
    }

    // Thanks go out to Alex Dolinsky for reporting the missing exception when an empty
    // expression is passed as in new ExpressionBuilder("")
    @Test(expected = IllegalArgumentException.class)
    public void testExpression59() throws Exception {
        Expression e = new ExpressionBuilder("").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpression60() throws Exception {
        Expression e = new ExpressionBuilder("   ").build();
        e.evaluate();
    }

    @Test(expected = ArithmeticException.class)
    public void testExpression61() throws Exception {
        Expression e = new ExpressionBuilder("14 % 0").build();
        e.evaluate();
    }

    // https://www.objecthunter.net/jira/browse/EXP-24
    // thanks go out to Rémi for the issue report
    @Test
    public void testExpression62() throws Exception {
        Expression e = new ExpressionBuilder("x*1.0e5+5")
                .build()
                .variable("x", Math.E);
        assertTrue(Math.E * 1.0 * Math.pow(10, 5) + 5 == e.evaluate());
    }

    // thanks go out to Janny for providing the tests and the bug report
    @Test
    public void testUnaryMinusInParenthesisSpace() throws Exception {
        ExpressionBuilder b = new ExpressionBuilder("( -1)^2");
        double calculated = b.build().evaluate();
        assertTrue(calculated == 1d);
    }

    @Test
    public void testUnaryMinusSpace() throws Exception {
        ExpressionBuilder b = new ExpressionBuilder(" -1 + 2");
        double calculated = b.build().evaluate();
        assertTrue(calculated == 1d);
    }

    @Test
    public void testUnaryMinusSpaces() throws Exception {
        ExpressionBuilder b = new ExpressionBuilder(" -1 + + 2 +   -   1");
        double calculated = b.build().evaluate();
        assertTrue(calculated == 0d);
    }

    @Test
    public void testUnaryMinusSpace1() throws Exception {
        ExpressionBuilder b = new ExpressionBuilder("-1");
        double calculated = b.build().evaluate();
        assertTrue(calculated == -1d);
    }

    @Test
    public void testExpression4() throws Exception {
        String expr;
        double expected;
        expr = "2+4 * 5";
        expected = 2 + 4 * 5;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    // test for https://www.objecthunter.net/jira/browse/EXP-19
    // thanks go out to Yunior Peralta for the report
    @Test
    public void testCharacterPositionInException1() throws Exception {
        String expr;
        expr = "2 + sn(4)";
        try {
            Expression e = new ExpressionBuilder(expr).build();
            fail("Expression was parsed but should throw an Exception");
        } catch (Exp4jException e) {
            String expected = "Unable to parse character 's' at position 5 in expression '2 + sn(4)'";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void testExpression5() throws Exception {
        String expr;
        double expected;
        expr = "(2+4)*5";
        expected = (2 + 4) * 5;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression6() throws Exception {
        String expr;
        double expected;
        expr = "(2+4)*5 + 2.5*2";
        expected = (2 + 4) * 5 + 2.5 * 2;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression7() throws Exception {
        String expr;
        double expected;
        expr = "(2+4)*5 + 10/2";
        expected = (2 + 4) * 5 + 10 / 2;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression8() throws Exception {
        String expr;
        double expected;
        expr = "(2 * 3 +4)*5 + 10/2";
        expected = (2 * 3 + 4) * 5 + 10 / 2;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testExpression9() throws Exception {
        String expr;
        double expected;
        expr = "(2 * 3 +4)*5 +4 + 10/2";
        expected = (2 * 3 + 4) * 5 + 4 + 10 / 2;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test(expected = Exp4jException.class)
    public void testFailUnknownFunction1() throws Exception {
        String expr;
        expr = "lig(1)";
        Expression e = new ExpressionBuilder(expr).build();
        e.evaluate();
    }

    @Test(expected = Exp4jException.class)
    public void testFailUnknownFunction2() throws Exception {
        String expr;
        expr = "galength(1)";
        new ExpressionBuilder(expr).build().evaluate();
    }

    @Test(expected = Exp4jException.class)
    public void testFailUnknownFunction3() throws Exception {
        String expr;
        expr = "f(cos) = cos(cos)";
        new ExpressionBuilder(expr).build().evaluate();
    }

    @Test
    public void testFunction22() throws Exception {
        String expr;
        expr = "cos(cos_1)";
        Expression e = new ExpressionBuilder(expr).build().variable("cos_1", 1d);
        assertTrue(e.evaluate() == Math.cos(1d));
    }

    @Test
    public void testPostfix1() throws Exception {
        String expr;
        double expected;
        expr = "2.2232^0.1";
        expected = Math.pow(2.2232d, 0.1d);
        double actual = new ExpressionBuilder(expr).build().evaluate();
        assertTrue(expected == actual);
    }

    @Test
    public void testPostfixEverything() throws Exception {
        String expr;
        double expected;
        expr = "(sin(12) + log(34)) * 3.42 - cos(2.234-log(2))";
        expected = (Math.sin(12) + Math.log(34)) * 3.42 - Math.cos(2.234 - Math.log(2));
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testPostfixExponentation1() throws Exception {
        String expr;
        double expected;
        expr = "2^3";
        expected = Math.pow(2, 3);
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testPostfixExponentation2() throws Exception {
        String expr;
        double expected;
        expr = "24 + 4 * 2^3";
        expected = 24 + 4 * Math.pow(2, 3);
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testPostfixExponentation3() throws Exception {
        String expr;
        double expected;
        double x = 4.334d;
        expr = "24 + 4 * 2^x";
        expected = 24 + 4 * Math.pow(2, x);
        Expression e = new ExpressionBuilder(expr).build().variable("x", x);
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testPostfixExponentation4() throws Exception {
        String expr;
        double expected;
        double x = 4.334d;
        expr = "(24 + 4) * 2^log(x)";
        expected = (24 + 4) * Math.pow(2, Math.log(x));
        Expression e = new ExpressionBuilder(expr).build().variable("x", x);
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testPostfixFunction1() throws Exception {
        String expr;
        double expected;
        expr = "log(1) * sin(0)";
        expected = Math.log(1) * Math.sin(0);
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testPostfixFunction10() throws Exception {
        String expr;
        double expected;
        expr = "cbrt(x)";
        Expression e = new ExpressionBuilder(expr).build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = Math.cbrt(x);
            assertTrue(expected == e.variable("x",x).evaluate());
        }
    }

    @Test
    public void testPostfixFunction11() throws Exception {
        String expr;
        double expected;
        expr = "cos(x) - (1/cbrt(x))";
        Expression e = new ExpressionBuilder(expr).build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            if (x == 0d) continue;
            expected = Math.cos(x) - (1 / Math.cbrt(x));
            assertTrue(expected == e.variable("x", x).evaluate());
        }
    }

    @Test
    public void testPostfixFunction12() throws Exception {
        String expr;
        double expected;
        expr = "acos(x) * expm1(asin(x)) - exp(atan(x)) + floor(x) + cosh(x) - sinh(cbrt(x))";
        Expression e = new ExpressionBuilder(expr).build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected =
                    Math.acos(x) * Math.expm1(Math.asin(x)) - Math.exp(Math.atan(x)) + Math.floor(x) + Math.cosh(x)
                            - Math.sinh(Math.cbrt(x));
            if (Double.isNaN(expected)) {
                assertTrue(Double.isNaN(e.variable("x", x).evaluate()));
            } else {
                assertTrue(expected == e.variable("x", x).evaluate());
            }
        }
    }

    @Test
    public void testPostfixFunction13() throws Exception {
        String expr;
        double expected;
        expr = "acos(x)";
        Expression e = new ExpressionBuilder(expr).build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = Math.acos(x);
            if (Double.isNaN(expected)) {
                assertTrue(Double.isNaN(e.variable("x", x).evaluate()));
            } else {
                assertTrue(expected == e.variable("x", x).evaluate());
            }
        }
    }

    @Test
    public void testPostfixFunction14() throws Exception {
        String expr;
        double expected;
        expr = " expm1(x)";
        Expression e = new ExpressionBuilder(expr).build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = Math.expm1(x);
            if (Double.isNaN(expected)) {
                assertTrue(Double.isNaN(e.variable("x", x).evaluate()));
            } else {
                assertTrue(expected == e.variable("x", x).evaluate());
            }
        }
    }

    @Test
    public void testPostfixFunction15() throws Exception {
        String expr;
        double expected;
        expr = "asin(x)";
        Expression e = new ExpressionBuilder(expr).build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = Math.asin(x);
            if (Double.isNaN(expected)) {
                assertTrue(Double.isNaN(e.variable("x", x).evaluate()));
            } else {
                assertTrue(expected == e.variable("x", x).evaluate());
            }
        }
    }

    @Test
    public void testPostfixFunction16() throws Exception {
        String expr;
        double expected;
        expr = " exp(x)";
        Expression e = new ExpressionBuilder(expr).build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = Math.exp(x);
            assertTrue(expected == e.variable("x", x).evaluate());
        }
    }

    @Test
    public void testPostfixFunction17() throws Exception {
        String expr;
        double expected;
        expr = "floor(x)";
        Expression e = new ExpressionBuilder(expr).build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = Math.floor(x);
            assertTrue(expected == e.variable("x", x).evaluate());
        }
    }

    @Test
    public void testPostfixFunction18() throws Exception {
        String expr;
        double expected;
        expr = " cosh(x)";
        Expression e = new ExpressionBuilder(expr).build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = Math.cosh(x);
            assertTrue(expected == e.variable("x", x).evaluate());
        }
    }

    @Test
    public void testPostfixFunction19() throws Exception {
        String expr;
        double expected;
        expr = "sinh(x)";
        Expression e = new ExpressionBuilder(expr).build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = Math.sinh(x);
            assertTrue(expected == e.variable("x", x).evaluate());
        }
    }

    @Test
    public void testPostfixFunction20() throws Exception {
        String expr;
        double expected;
        expr = "cbrt(x)";
        Expression e = new ExpressionBuilder(expr).build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = Math.cbrt(x);
            assertTrue(expected == e.variable("x", x).evaluate());
        }
    }

    @Test
    public void testPostfixFunction21() throws Exception {
        String expr;
        double expected;
        expr = "tanh(x)";
        Expression e = new ExpressionBuilder(expr).build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = Math.tanh(x);
            assertTrue(expected == e.variable("x", x).evaluate());
        }
    }

    @Test
    public void testPostfixFunction2() throws Exception {
        String expr;
        double expected;
        expr = "log(1)";
        expected = 0d;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testPostfixFunction3() throws Exception {
        String expr;
        double expected;
        expr = "sin(0)";
        expected = 0d;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testPostfixFunction5() throws Exception {
        String expr;
        double expected;
        expr = "ceil(2.3) +1";
        expected = Math.ceil(2.3) + 1;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testPostfixFunction6() throws Exception {
        String expr;
        double expected;
        double x = 1.565d;
        double y = 2.1323d;
        expr = "ceil(x) + 1 / y * abs(1.4)";
        expected = Math.ceil(x) + 1 / y * Math.abs(1.4);
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.variable("x", x).variable("y",y).evaluate());
    }

    @Test
    public void testPostfixFunction7() throws Exception {
        String expr;
        double expected;
        double x = Math.E;
        expr = "tan(x)";
        expected = Math.tan(x);
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.variable("x", x).evaluate());
    }

    @Test
    public void testPostfixFunction8() throws Exception {
        String expr;
        double expected;
        double varE = Math.E;
        expr = "2^3.4223232 + tan(e)";
        expected = Math.pow(2, 3.4223232d) + Math.tan(Math.E);
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.variable("e", varE).evaluate());
    }

    @Test
    public void testPostfixFunction9() throws Exception {
        String expr;
        double expected;
        double x = Math.E;
        expr = "cbrt(x)";
        expected = Math.cbrt(x);
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.variable("x", x).evaluate());
    }

    @Test(expected = Exp4jException.class)
    public void testPostfixInvalidVariableName() throws Exception {
        String expr;
        double expected;
        double x = 4.5334332d;
        double log = Math.PI;
        expr = "x * pi";
        expected = x * log;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.variable("x", x).variable("log", log).evaluate());
    }

    @Test
    public void testPostfixParanthesis() throws Exception {
        String expr;
        double expected;
        expr = "(3 + 3 * 14) * (2 * (24-17) - 14)/((34) -2)";
        expected = (3 + 3 * 14) * (2 * (24 - 17) - 14) / ((34) - 2);
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.evaluate());
    }

    @Test
    public void testPostfixVariables() throws Exception {
        String expr;
        double expected;
        double x = 4.5334332d;
        double pi = Math.PI;
        expr = "x * pi";
        expected = x * pi;
        Expression e = new ExpressionBuilder(expr).build();
        assertTrue(expected == e.variable("x", pi).evaluate());
    }
}
