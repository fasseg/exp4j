package net.objecthunter.exp4j.tokenizer;

import junit.framework.TestCase;

public class TokenizerTest extends TestCase {

    public void testNextToken() throws Exception {
        final Tokenizer tokenizer = new Tokenizer("1.222331");
        Token tok = tokenizer.nextToken();
        assertTrue(tok.getType() == Token.TOKEN_NUMBER);
        NumberToken nt = (NumberToken) tok;
        assertEquals(1.222331d, nt.getValue());
    }
}