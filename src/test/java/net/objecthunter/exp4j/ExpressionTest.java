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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;
import net.objecthunter.exp4j.tokenizer.*;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ExpressionTest {
    @Test
    public void testExpression1() throws Exception{
        Token[] tokens = new Token[] {
            new NumberToken(3d),
            new NumberToken(2d),
            new OperatorToken(Operators.getBuiltinOperator('+', 2))
        };
        Expression exp = new Expression(tokens);
        assertEquals(5d, exp.evaluate(), 0d);
    }

    @Test
    public void testExpression2() throws Exception{
        Token[] tokens = new Token[] {
                new NumberToken(1d),
                new FunctionToken(Functions.getBuiltinFunction("log")),
        };
        Expression exp = new Expression(tokens);
        assertEquals(0d, exp.evaluate(), 0d);
    }

    @Test
    public void testGetVariableNames1() throws Exception{
        Token[] tokens = new Token[] {
                new VariableToken("a"),
                new VariableToken("b"),
                new OperatorToken(Operators.getBuiltinOperator('+', 2))
        };
        Expression exp = new Expression(tokens);

        assertEquals(2, exp.getVariableNames().size());
    }

    @Test
    public void testFactorial() throws Exception {
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

        Expression e = new ExpressionBuilder("2!+3!")
                .operator(factorial)
                .build();
        assertEquals(8d, e.evaluate(), 0d);

        e = new ExpressionBuilder("3!-2!")
                .operator(factorial)
                .build();
        assertEquals(4d, e.evaluate(), 0d);

        e = new ExpressionBuilder("3!")
                .operator(factorial)
                .build();
        assertEquals(6, e.evaluate(), 0);

        e = new ExpressionBuilder("3!!")
                .operator(factorial)
                .build();
        assertEquals(720, e.evaluate(), 0);

        e = new ExpressionBuilder("4 + 3!")
                .operator(factorial)
                .build();
        assertEquals(10, e.evaluate(), 0);

        e = new ExpressionBuilder("3! * 2")
                .operator(factorial)
                .build();
        assertEquals(12, e.evaluate(), 0);
        
        e = new ExpressionBuilder("3!")
                .operator(factorial)
                .build();
        assertTrue(e.validate().isValid());
        assertEquals(6, e.evaluate(), 0);

        e = new ExpressionBuilder("3!!")
                .operator(factorial)
                .build();
        assertTrue(e.validate().isValid());
        assertEquals(720, e.evaluate(), 0);

        e = new ExpressionBuilder("4 + 3!")
                .operator(factorial)
                .build();
        assertTrue(e.validate().isValid());
        assertEquals(10, e.evaluate(), 0);

        e = new ExpressionBuilder("3! * 2")
                .operator(factorial)
                .build();
        assertTrue(e.validate().isValid());
        assertEquals(12, e.evaluate(), 0);

        e = new ExpressionBuilder("2 * 3!")
                .operator(factorial)
                .build();
        assertTrue(e.validate().isValid());
        assertEquals(12, e.evaluate(), 0);

        e = new ExpressionBuilder("4 + (3!)")
                .operator(factorial)
                .build();
        assertTrue(e.validate().isValid());
        assertEquals(10, e.evaluate(), 0);

        e = new ExpressionBuilder("4 + 3! + 2 * 6")
                .operator(factorial)
                .build();
        assertTrue(e.validate().isValid());
        assertEquals(22, e.evaluate(), 0);
    }

    @Test
    public void testCotangent1() {
        Expression e = new ExpressionBuilder("cot(1)")
                .build();
        assertEquals(1/Math.tan(1), e.evaluate(), 0d);

    }

    @Test(expected = ArithmeticException.class)
    public void testInvalidCotangent1() {
        Expression e = new ExpressionBuilder("cot(0)")
                .build();
        e.evaluate();

    }
    @Test(expected = IllegalArgumentException.class)
	public void testOperatorFactorial2() throws Exception {
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

        Expression e = new ExpressionBuilder("!3").build();
        assertFalse(e.validate().isValid());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidFactorial2() {
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

        Expression e = new ExpressionBuilder("!!3").build();
        assertFalse(e.validate().isValid());
    }

    @Test
    @Ignore
    // If Expression should be threads safe this test must pass
    public void evaluateFamily() throws Exception {
        final Expression e = new ExpressionBuilder("sin(x)")
                .variable("x")
                .build();
        Executor executor = Executors.newFixedThreadPool(100);
        for (int i = 0 ; i < 100000; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    double x = Math.random();
                    e.setVariable("x", x);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    assertEquals(Math.sin(x), e.evaluate(), 0f);
                }
            });
        }
    }
}

