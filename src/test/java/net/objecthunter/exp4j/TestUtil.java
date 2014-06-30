package net.objecthunter.exp4j;

import net.objecthunter.exp4j.tokenizer.*;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

/**
 * Created by ruckus on 30.06.14.
 */

public abstract class TestUtil {
    public static void assertVariableToken(Token token, String name) {
        assertEquals(Token.TOKEN_VARIABLE, token.getType());
        Assert.assertEquals(name, ((VariableToken) token).getName());
    }

    public static void assertOpenParanthesesToken(Token token) {
        assertEquals(Token.TOKEN_PARANTHESES_OPEN, token.getType());
    }

    public static void assertCloseParanthesesToken(Token token) {
        assertEquals(Token.TOKEN_PARANTHESES_CLOSE, token.getType());
    }

    public static void assertFunctionToken(Token token, String log, int i) {
        assertEquals(token.getType(), Token.TOKEN_FUNCTION);
        FunctionToken f = (FunctionToken) token;
        assertEquals(i, f.getFunction().getNumArguments());
    }

    public static void assertOperatorToken(Token tok, String symbol, int numArgs, int precedence) {
        assertEquals(tok.getType(), Token.TOKEN_OPERATOR);
        Assert.assertEquals(numArgs, ((OperatorToken) tok).getOperator().getNumArgs());
        assertEquals(symbol, ((OperatorToken) tok).getOperator().getSymbol());
        assertEquals(precedence, ((OperatorToken) tok).getOperator().getPrecedence());
    }

    public static void assertNumberToken(Token tok, double v) {
        assertEquals(tok.getType(), Token.TOKEN_NUMBER);
        Assert.assertEquals(v, ((NumberToken) tok).getValue(), 0d);
    }
}
