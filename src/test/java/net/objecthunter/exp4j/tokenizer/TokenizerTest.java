
package net.objecthunter.exp4j.tokenizer;

import static net.objecthunter.exp4j.TestUtil.*;
import static org.junit.Assert.*;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TokenizerTest {

    private static final Logger log = LoggerFactory.getLogger(TokenizerTest.class);

    @Test
    public void testTokenization1() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("1.222331".toCharArray(),null, null);
        assertNumberToken(tokenizer.nextToken(), 1.222331d);
    }

    @Test
    public void testTokenization2() throws Exception {
        final Tokenizer tokenizer = new Tokenizer(".222331".toCharArray(),null, null);
        assertNumberToken(tokenizer.nextToken(), .222331d);
    }

    @Test
    public void testTokenization3() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("3e2".toCharArray(),null, null);
        assertNumberToken(tokenizer.nextToken(), 300d);
    }

    @Test
    public void testTokenization4() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("3+1".toCharArray(),null, null);

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
        final Tokenizer tokenizer = new Tokenizer("+3".toCharArray(),null, null);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 1, Operator.PRECEDENCE_UNARY_PLUS);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization6() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("-3".toCharArray(),null, null);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "-", 1, Operator.PRECEDENCE_UNARY_MINUS);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization7() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("---++-3".toCharArray(),null, null);

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
        final Tokenizer tokenizer = new Tokenizer("---++-3.004".toCharArray(),null, null);

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
        final Tokenizer tokenizer = new Tokenizer("3+-1".toCharArray(),null, null);

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
        final Tokenizer tokenizer = new Tokenizer("3+-1-.32++2".toCharArray(),null, null);

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
        final Tokenizer tokenizer = new Tokenizer("2+".toCharArray(),null, null);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 2d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "+", 2, Operator.PRECEDENCE_ADDITION);

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization12() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("log(1)".toCharArray(),null, null);

        assertTrue(tokenizer.hasNext());
        assertFunctionToken(tokenizer.nextToken(), "log", 1);

        assertTrue(tokenizer.hasNext());
        assertOpenParanthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 1d);

        assertTrue(tokenizer.hasNext());
        assertCloseParanthesesToken(tokenizer.nextToken());

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization13() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("x".toCharArray(),null, null);

        assertTrue(tokenizer.hasNext());
        assertVariableToken(tokenizer.nextToken(), "x");

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization14() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("2*x-log(3)".toCharArray(),null, null);

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
        assertOpenParanthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertTrue(tokenizer.hasNext());
        assertCloseParanthesesToken(tokenizer.nextToken());

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization15() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("2*xlog+log(3)".toCharArray(),null, null);

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
        assertOpenParanthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertTrue(tokenizer.hasNext());
        assertCloseParanthesesToken(tokenizer.nextToken());

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization16() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("2*x+-log(3)".toCharArray(),null, null);

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
        assertOpenParanthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertTrue(tokenizer.hasNext());
        assertCloseParanthesesToken(tokenizer.nextToken());

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization17() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("2 * x + -log(3)".toCharArray(),null, null);

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
        assertOpenParanthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 3d);

        assertTrue(tokenizer.hasNext());
        assertCloseParanthesesToken(tokenizer.nextToken());

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

        Map<String, Function> funcs = new HashMap<>(1);
        funcs.put(log2.getName(), log2);
        final Tokenizer tokenizer = new Tokenizer("log2(4)".toCharArray(), funcs, null);

        assertTrue(tokenizer.hasNext());
        assertFunctionToken(tokenizer.nextToken(), "log2", 1);

        assertTrue(tokenizer.hasNext());
        assertOpenParanthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 4d);

        assertTrue(tokenizer.hasNext());
        assertCloseParanthesesToken(tokenizer.nextToken());

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
        Map<String, Function> funcs = new HashMap<>(1);
        funcs.put(avg.getName(), avg);
        final Tokenizer tokenizer = new Tokenizer("avg(1,2)".toCharArray(), funcs, null);

        assertTrue(tokenizer.hasNext());
        assertFunctionToken(tokenizer.nextToken(), "avg", 2);

        assertTrue(tokenizer.hasNext());
        assertOpenParanthesesToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 1d);

        assertTrue(tokenizer.hasNext());
        assertFunctionSeparatorToken(tokenizer.nextToken());

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 2d);

        assertTrue(tokenizer.hasNext());
        assertCloseParanthesesToken(tokenizer.nextToken());

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
        Map<String, Operator> operators = new HashMap<>(1);
        operators.put(factorial.getSymbol(), factorial);

        final Tokenizer tokenizer = new Tokenizer("2!".toCharArray(), null, operators);

        assertTrue(tokenizer.hasNext());
        assertNumberToken(tokenizer.nextToken(), 2d);

        assertTrue(tokenizer.hasNext());
        assertOperatorToken(tokenizer.nextToken(), "!", factorial.getNumArgs(), factorial.getPrecedence());

        assertFalse(tokenizer.hasNext());
    }

    private static void assertSeparatorToken(Token token) {
        assertEquals(Token.TOKEN_SEPARATOR, token.getType());
    }

    @Test
    public void testTokenizerPerformance() throws Exception {
        final String expression = "cos(x)*14-((log(x)*3.445) / 17.8889) +x -sqrt(2+x) - x^(3-log(x))";

        // warmup
        Tokenizer tokenizer = new Tokenizer(expression.toCharArray(), null, null);
        while (tokenizer.hasNext()) {
            tokenizer.nextToken();
        }

        // 100 tokenizations
        tokenizer = new Tokenizer(expression.toCharArray(), null, null);
        final long time = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            while (tokenizer.hasNext()) {
                tokenizer.nextToken();
            }
        }
        final long duration = System.currentTimeMillis() - time;
        log.info("+------------------------------+");
        log.info("| Tokenizer performance result |");
        log.info("+------------------------------+-------------------------------------------------");
        log.info("| Expression: {}", expression);
        log.info("| Finished 100,000 tokenizations in\t{} ms", duration);
        log.info("+--------------------------------------------------------------------------------");
    }
}
