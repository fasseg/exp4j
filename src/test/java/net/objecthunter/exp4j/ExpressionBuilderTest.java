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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertEquals(res.getErrors().size(),1);
    }
    @Test
    public void testExpressionBuilder8() throws Exception {
        ValidationResult res = new ExpressionBuilder("x*y*z")
                .build()
                .validate();
        assertFalse(res.isValid());
        assertEquals(res.getErrors().size(),3);
    }
    @Test
    public void testExpressionBuilder9() throws Exception {
        ValidationResult res = new ExpressionBuilder("x")
                .build()
                .variable("x",1d)
                .validate();
        assertTrue(res.isValid());
    }
    @Test
    public void testExpressionBuilder10() throws Exception {
        double result = new ExpressionBuilder("1e1")
                .build()
                .evaluate();
        assertEquals(10d,result, 0d);
    }

    @Test
    public void testExpressionBuilder11() throws Exception {
        double result = new ExpressionBuilder("1.11e-1")
                .build()
                .evaluate();
        assertEquals(0.111d,result, 0d);
    }

    @Test
    public void testExpressionBuilder12() throws Exception {
        double result = new ExpressionBuilder("1.11e+1")
                .build()
                .evaluate();
        assertEquals(11.1d,result, 0d);
    }
}
