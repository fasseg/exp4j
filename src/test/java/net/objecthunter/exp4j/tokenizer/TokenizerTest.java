package net.objecthunter.exp4j.tokenizer;

import  org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TokenizerTest {

    @Test
    public void testNextToken1() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("1.222331");
        Token tok = tokenizer.nextToken();
        assertTrue(tok.getType() == Token.TOKEN_NUMBER);
        NumberToken nt = (NumberToken) tok;
        assertEquals(1.222331d, nt.getValue(), 0d);
    }

    @Test
    public void testNextToken2() throws Exception {
        final Tokenizer tokenizer = new Tokenizer(".222331");
        Token tok = tokenizer.nextToken();
        assertTrue(tok.getType() == Token.TOKEN_NUMBER);
        NumberToken nt = (NumberToken) tok;
        assertEquals(0.222331d, nt.getValue(), 0d);
    }

    @Test
    public void testNextToken3() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("3e2");
        Token tok = tokenizer.nextToken();
        assertTrue(tok.getType() == Token.TOKEN_NUMBER);
        NumberToken nt = (NumberToken) tok;
        assertEquals(300d, nt.getValue(), 0d);
    }

    @Test
    public void testNextToken4() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("3+1");
        assertTrue(tokenizer.hasNext());
        Token tok = tokenizer.nextToken();
        assertTrue(tok.getType() == Token.TOKEN_NUMBER);
        NumberToken nt = (NumberToken) tok;
        assertEquals(3d, nt.getValue(), 0d);

        assertTrue(tokenizer.hasNext());
        tok = tokenizer.nextToken();
        assertTrue(tok.getType() == Token.TOKEN_OPERATOR);
        OperatorToken ot = (OperatorToken) tok;
        assertEquals("+", ot.getOperator().getSymbol());

        assertTrue(tokenizer.hasNext());
        tok = tokenizer.nextToken();
        assertTrue(tok.getType() == Token.TOKEN_NUMBER);
        nt = (NumberToken) tok;
        assertEquals(1d, nt.getValue(), 0d);
        assertFalse(tokenizer.hasNext());
    }
}