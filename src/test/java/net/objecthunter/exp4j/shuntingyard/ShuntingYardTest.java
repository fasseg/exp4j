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
package net.objecthunter.exp4j.shuntingyard;

import static net.objecthunter.exp4j.TestUtil.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokenizer.Token;

import org.junit.Test;

public class ShuntingYardTest {

    @Test
    public void testShuntingYard1() throws Exception {
        String expression = "2+3";
        Token[] tokens = ShuntingYard.convertToRPN(expression, null, null, null, true, true);
        assertNumberToken(tokens[0], 2d);
        assertNumberToken(tokens[1], 3d);
        assertOperatorToken(tokens[2], "+", 2, Operator.PRECEDENCE_ADDITION);
    }

    @Test
    public void testShuntingYard2() throws Exception {
        String expression = "3*x";
        Token[] tokens = ShuntingYard.convertToRPN(expression, null, null, new HashSet<String>(Arrays.asList("x")), true, true);
        assertNumberToken(tokens[0], 3d);
        assertVariableToken(tokens[1], "x");
        assertOperatorToken(tokens[2], "*", 2, Operator.PRECEDENCE_MULTIPLICATION);
    }

    @Test
    public void testShuntingYard3() throws Exception {
        String expression = "-3";
        Token[] tokens = ShuntingYard.convertToRPN(expression, null, null, null, true, true);
        assertNumberToken(tokens[0], 3d);
        assertOperatorToken(tokens[1], "-", 1, Operator.PRECEDENCE_UNARY_MINUS);
    }

    @Test
    public void testShuntingYard4() throws Exception {
        String expression = "-2^2";
        Token[] tokens = ShuntingYard.convertToRPN(expression, null, null, null, true, true);
        assertNumberToken(tokens[0], 2d);
        assertNumberToken(tokens[1], 2d);
        assertOperatorToken(tokens[2], "^", 2, Operator.PRECEDENCE_POWER);
        assertOperatorToken(tokens[3], "-", 1, Operator.PRECEDENCE_UNARY_MINUS);
    }

    @Test
    public void testShuntingYard5() throws Exception {
        String expression = "2^-2";
        Token[] tokens = ShuntingYard.convertToRPN(expression, null, null, null, true, true);
        assertNumberToken(tokens[0], 2d);
        assertNumberToken(tokens[1], 2d);
        assertOperatorToken(tokens[2], "-", 1, Operator.PRECEDENCE_UNARY_MINUS);
        assertOperatorToken(tokens[3], "^", 2, Operator.PRECEDENCE_POWER);
    }
    @Test
    public void testShuntingYard6() throws Exception {
        String expression = "2^---+2";
        Token[] tokens = ShuntingYard.convertToRPN(expression, null, null, null, true, true);
        assertNumberToken(tokens[0], 2d);
        assertNumberToken(tokens[1], 2d);
        assertOperatorToken(tokens[2], "+", 1, Operator.PRECEDENCE_UNARY_PLUS);
        assertOperatorToken(tokens[3], "-", 1, Operator.PRECEDENCE_UNARY_MINUS);
        assertOperatorToken(tokens[4], "-", 1, Operator.PRECEDENCE_UNARY_MINUS);
        assertOperatorToken(tokens[5], "-", 1, Operator.PRECEDENCE_UNARY_MINUS);
        assertOperatorToken(tokens[6], "^", 2, Operator.PRECEDENCE_POWER);
    }
    @Test
    public void testShuntingYard7() throws Exception {
        String expression = "2^-2!";
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
        Map<String, Operator> userOperators = new HashMap<String, Operator>();
        userOperators.put("!", factorial);
        Token[] tokens = ShuntingYard.convertToRPN(expression, null, userOperators, null, true, true);
        assertNumberToken(tokens[0], 2d);
        assertNumberToken(tokens[1], 2d);
        assertOperatorToken(tokens[2], "!", 1, Operator.PRECEDENCE_POWER + 1);
        assertOperatorToken(tokens[3], "-", 1, Operator.PRECEDENCE_UNARY_MINUS);
        assertOperatorToken(tokens[4], "^", 2, Operator.PRECEDENCE_POWER);
    }

    @Test
    public void testShuntingYard8() throws Exception {
        String expression = "-3^2";
        Token[] tokens = ShuntingYard.convertToRPN(expression, null, null, null, true, true);
        assertNumberToken(tokens[0], 3d);
        assertNumberToken(tokens[1], 2d);
        assertOperatorToken(tokens[2], "^", 2, Operator.PRECEDENCE_POWER);
        assertOperatorToken(tokens[3], "-", 1, Operator.PRECEDENCE_UNARY_MINUS);
    }

    @Test
    public void testShuntingYard9() throws Exception {
        Operator reciprocal = new Operator("$", 1, true, Operator.PRECEDENCE_DIVISION) {
            @Override
            public double apply(final double... args) {
                if (args[0] == 0d) {
                    throw new ArithmeticException("Division by zero!");
                }
                return 1d / args[0];
            }
        };
        Map<String, Operator> userOperators = new HashMap<String, Operator>();
        userOperators.put("$", reciprocal);
        Token[] tokens = ShuntingYard.convertToRPN("1$", null, userOperators, null, true, true);
        assertNumberToken(tokens[0], 1d);
        assertOperatorToken(tokens[1], "$", 1, Operator.PRECEDENCE_DIVISION);
    }

}
