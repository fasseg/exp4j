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
package net.objecthunter.exp4j.tokenizer;

import static net.objecthunter.exp4j.TestUtil.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

import org.junit.Test;

public class TokenizerTest {

    @Test
    public void testTokenization1() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("1.222331",null, null, null);
        assertNumberToken(tokenizer.nextToken(), 1.222331d);
    }

    @Test
    public void testTokenization2() throws Exception {
        final Tokenizer tokenizer = new Tokenizer(".222331",null, null, null);
        assertNumberToken(tokenizer.nextToken(), .222331d);
    }

    @Test
    public void testTokenization3() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("3e2",null, null, null);
        assertNumberToken(tokenizer.nextToken(), 300d);
    }

    @Test
    public void testTokenization4() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("3+1",null, null, null);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 2, Operator.PRECEDENCE_ADDITION);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 1d);

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization5() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("+3",null, null, null);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 1, Operator.PRECEDENCE_UNARY_PLUS);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization6() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("-3",null, null, null);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization7() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("---++-3",null, null, null);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 1, Operator.PRECEDENCE_UNARY_PLUS);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 1, Operator.PRECEDENCE_UNARY_PLUS);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization8() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("---++-3.004",null, null, null);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 1, Operator.PRECEDENCE_UNARY_PLUS);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 1, Operator.PRECEDENCE_UNARY_PLUS);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3.004d);

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization9() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("3+-1",null, null, null);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 2, Operator.PRECEDENCE_ADDITION);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 1d);

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization10() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("3+-1-.32++2",null, null, null);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 2, Operator.PRECEDENCE_ADDITION);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 1d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 2, Operator.PRECEDENCE_SUBTRACTION);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 0.32d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 2, Operator.PRECEDENCE_ADDITION);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 1, Operator.PRECEDENCE_UNARY_PLUS);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 2d);

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization11() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("2+",null, null, null);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 2d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 2, Operator.PRECEDENCE_ADDITION);

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization12() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("log(1)",null, null, null);

        assertTrue(tokenizer.hasNext());
        assertFunctionToken(tokenizer.nextToken(), "log", 1);

        assertTrue(tokenizer.hasNext());
        assertOpenParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 1d);

        assertTrue(tokenizer.hasNext());
        assertCloseParenthesesToken(tokenizer.nextToken());

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization13() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("x",null, null, new HashSet<String>(Arrays.asList("x")));

        assertTrue(tokenizer.hasNext());
        assertVariableToken(tokenizer.nextToken(), "x");

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization14() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("2*x-log(3)",null, null, new HashSet<String>(Arrays.asList("x")));

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 2d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "*", 2, Operator.PRECEDENCE_MULTIPLICATION);

        assertTrue(tokenizer.hasNext());
        assertVariableToken(tokenizer.nextToken(), "x");

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 2, Operator.PRECEDENCE_SUBTRACTION);

        assertTrue(tokenizer.hasNext());
        assertFunctionToken(tokenizer.nextToken(), "log", 1);

        assertTrue(tokenizer.hasNext());
        assertOpenParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertTrue(tokenizer.hasNext());
        assertCloseParenthesesToken(tokenizer.nextToken());

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization15() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("2*xlog+log(3)",null, null, new HashSet<String>(Arrays.asList("xlog")));

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 2d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "*", 2, Operator.PRECEDENCE_MULTIPLICATION);

        assertTrue(tokenizer.hasNext());
        assertVariableToken(tokenizer.nextToken(), "xlog");

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 2, Operator.PRECEDENCE_ADDITION);

        assertTrue(tokenizer.hasNext());
        assertFunctionToken(tokenizer.nextToken(), "log", 1);

        assertTrue(tokenizer.hasNext());
        assertOpenParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertTrue(tokenizer.hasNext());
        assertCloseParenthesesToken(tokenizer.nextToken());

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization16() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("2*x+-log(3)",null, null, new HashSet<String>(Arrays.asList("x")));

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 2d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "*", 2, Operator.PRECEDENCE_MULTIPLICATION);

        assertTrue(tokenizer.hasNext());
        assertVariableToken(tokenizer.nextToken(), "x");

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 2, Operator.PRECEDENCE_ADDITION);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertFunctionToken(tokenizer.nextToken(), "log", 1);

        assertTrue(tokenizer.hasNext());
        assertOpenParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertTrue(tokenizer.hasNext());
        assertCloseParenthesesToken(tokenizer.nextToken());

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization17() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("2 * x + -log(3)",null, null, new HashSet<String>(Arrays.asList("x")));

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 2d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "*", 2, Operator.PRECEDENCE_MULTIPLICATION);

        assertTrue(tokenizer.hasNext());
        assertVariableToken(tokenizer.nextToken(), "x");

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 2, Operator.PRECEDENCE_ADDITION);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertFunctionToken(tokenizer.nextToken(), "log", 1);

        assertTrue(tokenizer.hasNext());
        assertOpenParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertTrue(tokenizer.hasNext());
        assertCloseParenthesesToken(tokenizer.nextToken());

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization18() throws Exception {
        final Function log2 = new Function("log2") {

            @Override
            public double apply(double... args) {
                return Math.log(args[0]) / Math.log(2d);
            }
        };

        Map<String, Function> funcs = new HashMap<String, Function>(1);
        funcs.put(log2.getName(), log2);
        final Tokenizer tokenizer = new Tokenizer("log2(4)", funcs, null, null);

        assertTrue(tokenizer.hasNext());
        assertFunctionToken(tokenizer.nextToken(), "log2", 1);

        assertTrue(tokenizer.hasNext());
        assertOpenParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 4d);

        assertTrue(tokenizer.hasNext());
        assertCloseParenthesesToken(tokenizer.nextToken());

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization19() throws Exception {
        Function avg = new Function("avg", 2) {

            @Override
            public double apply(double... args) {
                double sum = 0;
                for (double arg : args) {
                    sum += arg;
                }
                return sum / args.length;
            }
        };
        Map<String, Function> funcs = new HashMap<String, Function>(1);
        funcs.put(avg.getName(), avg);
        final Tokenizer tokenizer = new Tokenizer("avg(1,2)", funcs, null, null);

        assertTrue(tokenizer.hasNext());
        assertFunctionToken(tokenizer.nextToken(), "avg", 2);

        assertTrue(tokenizer.hasNext());
        assertOpenParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 1d);

        assertTrue(tokenizer.hasNext());
        assertFunctionSeparatorToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 2d);

        assertTrue(tokenizer.hasNext());
        assertCloseParenthesesToken(tokenizer.nextToken());

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization20() throws Exception {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                return 0d;
            }
        };
        Map<String, Operator> operators = new HashMap<String, Operator>(1);
        operators.put(factorial.getSymbol(), factorial);

        final Tokenizer tokenizer = new Tokenizer("2!", null, operators, null);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 2d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "!", factorial.getNumOperands(), factorial.getPrecedence());

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization21() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("log(x) - y * (sqrt(x^cos(y)))", null, null, new HashSet<String>(Arrays.asList("x", "y")));

        assertTrue(tokenizer.hasNext());
        assertFunctionToken(tokenizer.nextToken(), "log", 1);

        assertTrue(tokenizer.hasNext());
        assertOpenParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertVariableToken(tokenizer.nextToken(), "x");

        assertTrue(tokenizer.hasNext());
        assertCloseParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 2, Operator.PRECEDENCE_SUBTRACTION);

        assertTrue(tokenizer.hasNext());
        assertVariableToken(tokenizer.nextToken(), "y");

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "*", 2, Operator.PRECEDENCE_MULTIPLICATION);

        assertTrue(tokenizer.hasNext());
        assertOpenParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertFunctionToken(tokenizer.nextToken(), "sqrt", 1);

        assertTrue(tokenizer.hasNext());
        assertOpenParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertVariableToken(tokenizer.nextToken(), "x");

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "^", 2, Operator.PRECEDENCE_POWER);

        assertTrue(tokenizer.hasNext());
        assertFunctionToken(tokenizer.nextToken(), "cos", 1);

        assertTrue(tokenizer.hasNext());
        assertOpenParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertVariableToken(tokenizer.nextToken(), "y");

        assertTrue(tokenizer.hasNext());
        assertCloseParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertCloseParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertCloseParenthesesToken(tokenizer.nextToken());

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization22() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("--2 * (-14)", null, null, null);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 2d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "*", 2, Operator.PRECEDENCE_MULTIPLICATION);

        assertTrue(tokenizer.hasNext());
        assertOpenParenthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 14d);

        assertTrue(tokenizer.hasNext());
        assertCloseParenthesesToken(tokenizer.nextToken());

        assertFalse(tokenizer.hasNext());
    }
}
