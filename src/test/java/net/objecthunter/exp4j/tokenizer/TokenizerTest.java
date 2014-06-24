package net.objecthunter.exp4j.tokenizer;

import net.objecthunter.exp4j.operator.Operator;
import org.junit.Test;

import static org.junit.Assert.*;


public class TokenizerTest {

    @Test
    public void testTokenization1() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("1.222331");
        Token tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_NUMBER);
        assertEquals(1.222331d, ((NumberToken) tok).getValue(), 0d);
    }

    @Test
    public void testTokenization2() throws Exception {
        final Tokenizer tokenizer = new Tokenizer(".222331");
        Token tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_NUMBER);
        assertEquals(0.222331d, ((NumberToken) tok).getValue(), 0d);
    }

    @Test
    public void testTokenization3() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("3e2");
        Token tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_NUMBER);
        assertEquals(300d, ((NumberToken) tok).getValue(), 0d);
    }

    @Test
    public void testTokenization4() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("3+1");

        assertTrue(tokenizer.hasNext());
        Token tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_NUMBER);
        assertEquals(3d, ((NumberToken) tok).getValue(), 0d);

        assertTrue(tokenizer.hasNext());
        tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_OPERATOR);
        assertEquals("+", ((OperatorToken) tok).getOperator().getSymbol());

        assertTrue(tokenizer.hasNext());
        tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_NUMBER);
        assertEquals(1d, ((NumberToken) tok).getValue(), 0d);
        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization5() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("+3");

        assertTrue(tokenizer.hasNext());
        Token tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_OPERATOR);
        assertEquals(1, ((OperatorToken) tok).getOperator().getNumArgs());
        assertEquals("+", ((OperatorToken) tok).getOperator().getSymbol());
        assertEquals(Operator.PRECEDENCE_UNARY_PLUS, ((OperatorToken) tok).getOperator().getPrecedence());

        assertTrue(tokenizer.hasNext());
        tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_NUMBER);
        assertEquals(3d, ((NumberToken) tok).getValue(), 0d);

        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void testTokenization6() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("-3");

        assertTrue(tokenizer.hasNext());
        Token tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_OPERATOR);
        assertEquals(1, ((OperatorToken) tok).getOperator().getNumArgs());
        assertEquals("-", ((OperatorToken) tok).getOperator().getSymbol());
        assertEquals(Operator.PRECEDENCE_UNARY_MINUS, ((OperatorToken) tok).getOperator().getPrecedence());

        assertTrue(tokenizer.hasNext());
        tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_NUMBER);
        assertEquals(3d, ((NumberToken) tok).getValue(), 0d);

        assertFalse(tokenizer.hasNext());
    }
    @Test
    public void testTokenization7() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("---++-3");

        assertTrue(tokenizer.hasNext());
        Token tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_OPERATOR);
        assertEquals(1, ((OperatorToken) tok).getOperator().getNumArgs());
        assertEquals("-", ((OperatorToken) tok).getOperator().getSymbol());
        assertEquals(Operator.PRECEDENCE_UNARY_MINUS, ((OperatorToken) tok).getOperator().getPrecedence());

        assertTrue(tokenizer.hasNext());
        tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_OPERATOR);
        assertEquals(1, ((OperatorToken) tok).getOperator().getNumArgs());
        assertEquals("-", ((OperatorToken) tok).getOperator().getSymbol());
        assertEquals(Operator.PRECEDENCE_UNARY_MINUS, ((OperatorToken) tok).getOperator().getPrecedence());

        assertTrue(tokenizer.hasNext());
        tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_OPERATOR);
        assertEquals(1, ((OperatorToken) tok).getOperator().getNumArgs());
        assertEquals("-", ((OperatorToken) tok).getOperator().getSymbol());
        assertEquals(Operator.PRECEDENCE_UNARY_MINUS, ((OperatorToken) tok).getOperator().getPrecedence());

        assertTrue(tokenizer.hasNext());
        tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_OPERATOR);
        assertEquals(1, ((OperatorToken) tok).getOperator().getNumArgs());
        assertEquals("+", ((OperatorToken) tok).getOperator().getSymbol());
        assertEquals(Operator.PRECEDENCE_UNARY_PLUS, ((OperatorToken) tok).getOperator().getPrecedence());

        assertTrue(tokenizer.hasNext());
        tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_OPERATOR);
        assertEquals(1, ((OperatorToken) tok).getOperator().getNumArgs());
        assertEquals("+", ((OperatorToken) tok).getOperator().getSymbol());
        assertEquals(Operator.PRECEDENCE_UNARY_PLUS, ((OperatorToken) tok).getOperator().getPrecedence());

        assertTrue(tokenizer.hasNext());
        tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_OPERATOR);
        assertEquals(1, ((OperatorToken) tok).getOperator().getNumArgs());
        assertEquals("-", ((OperatorToken) tok).getOperator().getSymbol());
        assertEquals(Operator.PRECEDENCE_UNARY_MINUS, ((OperatorToken) tok).getOperator().getPrecedence());

        assertTrue(tokenizer.hasNext());
        tok = tokenizer.nextToken();
        assertEquals(tok.getType(), Token.TOKEN_NUMBER);
        assertEquals(3d, ((NumberToken) tok).getValue(), 0d);

        assertFalse(tokenizer.hasNext());
    }
}