package net.objecthunter.exp4j.tokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class FastTokenizerRealTest {
	
	private final String defaultExpression1 = "1+6.77-14*sin(x)/log(y)-14*abs(2.445)--12*cos(x)-sqrt(x)/(sin(x)*cos(x)*cos(x/2)";
	private final String defaultExpression2 = "6.77-14*sin(x)/log(y)-14*abs(2.445)--12*cos(x)-sqrt(x)/(sin(x)*cos(x)*cos(x/2)+1";
	private final String defaultExpression3 = "-14*sin(x)/log(y)-14*abs(2.445)--12*cos(x)-sqrt(x)/(sin(x)*cos(x)*cos(x/2)+1+6.77";
	private final String defaultExpression4 = "sin(x)/log(y)-14*abs(2.445)--12*cos(x)-sqrt(x)/(sin(x)*cos(x)*cos(x/2)+1+1+6.77-14";
	
	private final char[] defaultCharArray = "1+6.77-14*sin(x)/log(y)-14*abs(2.445)--12*cos(x)-sqrt(x)/(sin(x)*cos(x)*cos(x/2)".toCharArray();

	@Test
	public void testTokenization1() throws Exception {
		FastTokenizer tok = new FastTokenizer("2.2", new String[0],
				new String[0]);
		assertFalse(tok.isEOF());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("2.2", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.EOF);
	}

	@Test
	public void testTokenization2() throws Exception {
		FastTokenizer tok = new FastTokenizer("2.2 +   3.1445 - -17",
				new String[0], new String[0]);
		assertFalse(tok.isEOF());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("2.2", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("+", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("3.1445", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("-", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("-", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("17", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.EOF);
	}

	@Test
	public void testTokenization3() throws Exception {
		FastTokenizer tok = new FastTokenizer(
				"2.2 / - sin(3.1445) * 3.1445 - -17", new String[] { "sin" },
				new String[0]);
		assertFalse(tok.isEOF());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("2.2", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("/", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("-", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.FUNCTION);
		assertEquals("sin", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_OPEN);
		assertEquals("(", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("3.1445", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_CLOSE);
		assertEquals(")", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("*", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("3.1445", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("-", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("-", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("17", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.EOF);
	}
	
	@Test
	public void testTokenization4() throws Exception {
		String expression = "2 + 2";
		FastTokenizer tok = new FastTokenizer(expression,new String[0],	new String[0]);
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("2", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("+", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("2", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.EOF);
	}

	@Test
	public void testTokenization5() throws Exception {
		String expression = "sin(2)";
		FastTokenizer tok = new FastTokenizer(expression,new String[] { "sin" },	new String[0]);
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.FUNCTION);
		assertEquals("sin", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_OPEN);
		assertEquals("(", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("2", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_CLOSE);
		assertEquals(")", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.EOF);
	}

	@Test
	public void testTokenization6() throws Exception {
		String expression = "3 * sin(2)/0.5";
		FastTokenizer tok = new FastTokenizer(expression,new String[] { "sin" },	new String[0]);
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("3", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("*", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.FUNCTION);
		assertEquals("sin", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_OPEN);
		assertEquals("(", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("2", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_CLOSE);
		assertEquals(")", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("/", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("0.5", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.EOF);
	}

	@Test
	public void testTokenization7() throws Exception {
		String expression = "3 * sin(2+sin(12.745))/0.5";
		FastTokenizer tok = new FastTokenizer(expression,new String[] { "sin" },	new String[0]);
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("3", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("*", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.FUNCTION);
		assertEquals("sin", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_OPEN);
		assertEquals("(", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("2", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("+", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.FUNCTION);
		assertEquals("sin", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_OPEN);
		assertEquals("(", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("12.745", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_CLOSE);
		assertEquals(")", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_CLOSE);
		assertEquals(")", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("/", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("0.5", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.EOF);
	}

	@Test
	public void testTokenization8() throws Exception {
		String expression = "log(sin(1.0) + abs(2.5))";
		FastTokenizer tok = new FastTokenizer(expression,new String[] { "sin", "log", "abs" },	new String[0]);
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.FUNCTION);
		assertEquals("log", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_OPEN);
		assertEquals("(", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.FUNCTION);
		assertEquals("sin", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_OPEN);
		assertEquals("(", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("1.0", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_CLOSE);
		assertEquals(")", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("+", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.FUNCTION);
		assertEquals("abs", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_OPEN);
		assertEquals("(", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("2.5", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_CLOSE);
		assertEquals(")", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.PARANTHESES_CLOSE);
		assertEquals(")", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.EOF);
	}

	@Test
	public void testTokenization9() throws Exception {
		String expression = "-1";
		FastTokenizer tok = new FastTokenizer(expression,new String[0],	new String[0]);
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.OPERATOR);
		assertEquals("-", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.NUMBER);
		assertEquals("1", tok.getTokenValue());
		tok.nextToken();
		assertTrue(tok.getType() == FastTokenizer.EOF);
	}

	@Test
	public void testTokenization10() throws Exception {
		String expression = "-1 * -sin(3 * (-1.2))";
		FastTokenizer tok = new FastTokenizer(expression,new String[] { "sin" },	new String[0]);
	}
	
    @Test
    public void testFastTokenizer() throws Exception {
            String[] variables = { "x", "y" };
            String[] functions = { "sin", "cos", "abs", "log", "sqrt" };
            List<String> tokens = new ArrayList<>();
            long time = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
			FastTokenizer tok = new FastTokenizer(defaultExpression2, functions,
					variables);
                    while (!tok.isEOF()) {
                            tok.nextToken();
                            int type = tok.getType();
                            String val = tok.getTokenValue();
                            tokens.add(type + tok.getTokenValue());
                    }
            }
            System.out.println("FastTokenizer took "
                            + (System.currentTimeMillis() - time) + "ms");
    }
    @Test
    public void testFastTokenizerCharArrayReturnList() throws Exception {
    		String[] variables = { "x", "y" };
            String[] functions = { "sin", "cos", "abs", "log", "sqrt" };
            long time = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
            	int[] types = null;
            	String[] tokens = null;
                FastTokenizer.tokenize(defaultCharArray, functions, variables, tokens, types);
            }
            System.out.println("FastTokenizer from char array to List took "
                            + (System.currentTimeMillis() - time) + "ms");
    }
    
    @Test
    public void testFastTokenizerStringReturnList() throws Exception {
		String[] variables = { "x", "y" };
        String[] functions = { "sin", "cos", "abs", "log", "sqrt" };
       	int[] types = null;
       	String[] tokens = null;
       	long time = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            FastTokenizer.tokenize(defaultExpression4, functions, variables, tokens, types);
        }
        System.out.println("FastTokenizer to List took "
                + (System.currentTimeMillis() - time) + "ms");
    }

}
