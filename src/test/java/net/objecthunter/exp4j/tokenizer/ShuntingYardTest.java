package net.objecthunter.exp4j.tokenizer;

import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.shuntingyard.ShuntingYard;
import org.junit.Test;

import static net.objecthunter.exp4j.TestUtil.assertNumberToken;
import static net.objecthunter.exp4j.TestUtil.assertOperatorToken;

/**
 * Created by ruckus on 30.06.14.
 */
public class ShuntingYardTest {
    @Test
    public void testShuntingYard1() throws Exception {
        String expression="2+3";
        Token[] tokens = ShuntingYard.convertToRPN(expression);
        assertNumberToken(tokens[0], 2d);
        assertNumberToken(tokens[1], 3d);
        assertOperatorToken(tokens[2], "+", 2, Operator.PRECEDENCE_ADDITION);
    }
}
