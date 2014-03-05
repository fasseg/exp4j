package net.objecthunter.exp4j.tokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.objecthunter.exp4j.complex.ComplexNumber;
import net.objecthunter.exp4j.exception.UnparseableExpressionException;
import net.objecthunter.exp4j.expression.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;
import net.objecthunter.exp4j.tokens.NumberToken;
import net.objecthunter.exp4j.tokens.Token;

import org.junit.Test;

public class FastTokenizerComplexTest {
	@Test
	public void testTokenization1() throws Exception {
		FastTokenizer tok = new FastTokenizer("2.2i", ExpressionBuilder.MODE_COMPLEX);
		assertFalse(tok.isEOF());
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		assertTrue(((NumberToken) tok.getTokenValue()).getValue().getClass() == ComplexNumber.class);
		ComplexNumber z = (ComplexNumber) ((NumberToken) tok.getTokenValue()).getValue();
		assertEquals(0d, z.getReal(), 0d);
		assertEquals(2.2d, z.getImaginary(), 0d);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}
	@Test
	public void testTokenization2() throws Exception {
		FastTokenizer tok = new FastTokenizer("3+2.2i", ExpressionBuilder.MODE_COMPLEX);
		assertFalse(tok.isEOF());
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		assertTrue(((NumberToken) tok.getTokenValue()).getValue().getClass() == ComplexNumber.class);
		ComplexNumber z = (ComplexNumber) ((NumberToken) tok.getTokenValue()).getValue();
		assertEquals(3d, z.getReal(), 0d);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		assertTrue(((NumberToken) tok.getTokenValue()).getValue().getClass() == ComplexNumber.class);
		ComplexNumber z2 = (ComplexNumber) ((NumberToken) tok.getTokenValue()).getValue();
		assertEquals(0d, z2.getReal(), 0d);
		assertEquals(2.2d, z2.getImaginary(), 0d);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}
	@Test
	public void testTokenization3() throws Exception {
		FastTokenizer tok = new FastTokenizer("3.1-2.2i", ExpressionBuilder.MODE_COMPLEX);
		assertFalse(tok.isEOF());
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		assertTrue(((NumberToken) tok.getTokenValue()).getValue().getClass() == ComplexNumber.class);
		ComplexNumber z = (ComplexNumber) ((NumberToken) tok.getTokenValue()).getValue();
		assertEquals(3.1d, z.getReal(), 0d);
		tok.nextToken();
		assertTrue(tok.getType() == Token.OPERATOR);
		tok.nextToken();
		assertTrue(tok.getType() == Token.NUMBER);
		assertTrue(((NumberToken) tok.getTokenValue()).getValue().getClass() == ComplexNumber.class);
		ComplexNumber z2 = (ComplexNumber) ((NumberToken) tok.getTokenValue()).getValue();
		assertEquals(0d, z2.getReal(), 0d);
		assertEquals(g2.2d, z2.getImaginary(), 0d);
		tok.nextToken();
		assertTrue(tok.getType() == Token.EOF);
	}

}
