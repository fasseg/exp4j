package net.objecthunter.exp4j.shuntingyard;

import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.shuntingyard.ShuntingYard;
import net.objecthunter.exp4j.tokenizer.Token;
import org.junit.Test;

import static net.objecthunter.exp4j.TestUtil.*;

/**
 * Created by ruckus on 30.06.14.
 */
public class ShuntingYardTest {
    @Test
    public void testShuntingYard1() throws Exception {
        String expression = "2+3";
        Token[] tokens = ShuntingYard.convertToRPN(expression);
        assertNumberToken(tokens[0], 2d);
        assertNumberToken(tokens[1], 3d);
        assertOperatorToken(tokens[2], "+", 2, Operator.PRECEDENCE_ADDITION);
    }

    @Test
    public void testShuntingYard2() throws Exception {
        String expression = "3*x";
        Token[] tokens = ShuntingYard.convertToRPN(expression);
        assertNumberToken(tokens[0], 3d);
        assertVariableToken(tokens[1], "x");
        assertOperatorToken(tokens[2], "*", 2, Operator.PRECEDENCE_MULTIPLICATION);
    }

    @Test
    public void testShuntingYard3() throws Exception {
        String expression = "-3";
        Token[] tokens = ShuntingYard.convertToRPN(expression);
        assertNumberToken(tokens[0], 3d);
        assertOperatorToken(tokens[1], "-", 1, Operator.PRECEDENCE_UNARY_MINUS);
    }
}
