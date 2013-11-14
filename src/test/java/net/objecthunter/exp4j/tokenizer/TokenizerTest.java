package net.objecthunter.exp4j.tokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import junit.framework.Assert;
import net.objecthunter.exp4j.ComplexNumber;
import net.objecthunter.exp4j.tokenizer.Token.Type;

import org.junit.Test;

public class TokenizerTest {

	@Test
	public void testTokenization1() throws Exception {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "2 + 2";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(tokens.size(), 3);
		assertEquals(tokens.get(0).getType(), Type.NUMBER);
		assertEquals(tokens.get(1).getType(), Type.OPERATOR);
		assertEquals(tokens.get(2).getType(), Type.NUMBER);
	}

	@Test
	public void testTokenization2() throws Exception {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "sin(2)";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(tokens.size(), 4);
		assertEquals(tokens.get(0).getType(), Type.FUNCTION);
		assertEquals(tokens.get(1).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(2).getType(), Type.NUMBER);
		assertEquals(tokens.get(3).getType(), Type.PARANTHESES);
	}

	@Test
	public void testTokenization3() throws Exception {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "3 * sin(2)/0.5";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(tokens.size(), 8);
		assertEquals(tokens.get(0).getType(), Type.NUMBER);
		assertEquals(tokens.get(1).getType(), Type.OPERATOR);
		assertEquals(tokens.get(2).getType(), Type.FUNCTION);
		assertEquals(tokens.get(3).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(4).getType(), Type.NUMBER);
		assertEquals(tokens.get(5).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(6).getType(), Type.OPERATOR);
		assertEquals(tokens.get(7).getType(), Type.NUMBER);
		assertEquals(((NumberToken<Float>) tokens.get(7)).getValue(), new Float(0.5d));

	}

	@Test
	public void testTokenization4() throws Exception {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "3 * sin(2+sin(12.745))/0.5";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(tokens.size(), 13);
		assertEquals(tokens.get(0).getType(), Type.NUMBER);
		assertEquals(tokens.get(1).getType(), Type.OPERATOR);
		assertEquals(tokens.get(2).getType(), Type.FUNCTION);
		assertEquals(tokens.get(3).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(4).getType(), Type.NUMBER);
		assertEquals(tokens.get(5).getType(), Type.OPERATOR);
		assertEquals(tokens.get(6).getType(), Type.FUNCTION);
		assertEquals(tokens.get(7).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(8).getType(), Type.NUMBER);
		assertEquals(tokens.get(9).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(10).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(11).getType(), Type.OPERATOR);
		assertEquals(tokens.get(12).getType(), Type.NUMBER);

	}

	@Test
	public void testTokenization5() throws Exception {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "log(sin(1.0) + abs(2.5))";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(tokens.size(), 12);
		assertEquals(tokens.get(0).getType(), Type.FUNCTION);
		assertEquals(tokens.get(1).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(2).getType(), Type.FUNCTION);
		assertEquals(tokens.get(3).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(4).getType(), Type.NUMBER);
		assertEquals(tokens.get(5).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(6).getType(), Type.OPERATOR);
		assertEquals(tokens.get(7).getType(), Type.FUNCTION);
		assertEquals(tokens.get(8).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(9).getType(), Type.NUMBER);
		assertEquals(tokens.get(10).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(11).getType(), Type.PARANTHESES);
	}

	@Test
	public void testTokenization6() throws Exception {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "-1";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(tokens.size(), 2);
		assertEquals(tokens.get(0).getType(), Type.OPERATOR);
		assertEquals(tokens.get(1).getType(), Type.NUMBER);
		assertEquals(((NumberToken<Float>) tokens.get(1)).getValue(), new Float(1f));
	}

	@Test
	public void testTokenization7() throws Exception {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "-1 * -sin(3 * (-1.2))";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(tokens.size(), 13);
		assertEquals(tokens.get(0).getType(), Type.OPERATOR);
		assertEquals(tokens.get(1).getType(), Type.NUMBER);
		assertEquals(tokens.get(2).getType(), Type.OPERATOR);
		assertEquals(tokens.get(3).getType(), Type.OPERATOR);
		assertEquals(tokens.get(4).getType(), Type.FUNCTION);
		assertEquals(tokens.get(5).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(6).getType(), Type.NUMBER);
		assertEquals(tokens.get(7).getType(), Type.OPERATOR);
		assertEquals(tokens.get(8).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(9).getType(), Type.OPERATOR);
		assertEquals(tokens.get(10).getType(), Type.NUMBER);
		assertEquals(tokens.get(11).getType(), Type.PARANTHESES);
		assertEquals(tokens.get(12).getType(), Type.PARANTHESES);
	}

	@Test
	public void testComplexTokenization1() throws Exception {
		Tokenizer<ComplexNumber> tokenizer = new Tokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "1 + 1i";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(tokens.size(), 1);
		assertEquals(tokens.get(0).getType(), Type.NUMBER);
		assertTrue(((NumberToken) tokens.get(0)).imaginary);
		assertEquals(new Double(1), (Double) ((ComplexNumber) ((NumberToken) tokens.get(0)).getValue()).getReal());
		assertEquals(new Double(1), (Double) ((ComplexNumber) ((NumberToken) tokens.get(0)).getValue()).getImaginary());
	}

	@Test
	public void testComplexTokenization2() throws Exception {
		Tokenizer<ComplexNumber> tokenizer = new Tokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "1 + 1";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(tokens.size(), 3);
		assertEquals(tokens.get(0).getType(), Type.NUMBER);
		assertEquals(tokens.get(1).getType(), Type.OPERATOR);
		assertEquals(tokens.get(2).getType(), Type.NUMBER);
		assertFalse(((NumberToken) tokens.get(0)).imaginary);
		assertFalse(((NumberToken) tokens.get(2)).imaginary);
	}

	@Test
	public void testComplexTokenization3() throws Exception {
		Tokenizer<ComplexNumber> tokenizer = new Tokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "1 * 1";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(tokens.size(), 3);
		assertEquals(tokens.get(0).getType(), Type.NUMBER);
		assertEquals(tokens.get(1).getType(), Type.OPERATOR);
		assertEquals(tokens.get(2).getType(), Type.NUMBER);
		assertFalse(((NumberToken) tokens.get(0)).imaginary);
		assertFalse(((NumberToken) tokens.get(2)).imaginary);
	}

	@Test
	public void testComplexTokenization4() throws Exception {
		Tokenizer<ComplexNumber> tokenizer = new Tokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "1 - 2i";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(tokens.size(), 1);
		assertEquals(tokens.get(0).getType(), Type.NUMBER);
		assertTrue(((NumberToken) tokens.get(0)).imaginary);
		assertEquals(new Double(1), (Double) ((ComplexNumber) ((NumberToken) tokens.get(0)).getValue()).getReal());
		assertEquals(new Double(-2), (Double) ((ComplexNumber) ((NumberToken) tokens.get(0)).getValue()).getImaginary());
	}

	@Test
	public void testComplexTokenization5() throws Exception {
		Tokenizer<ComplexNumber> tokenizer = new Tokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "1 - 2i * sin(3+2i)";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(6, tokens.size());
		assertEquals(tokens.get(0).getType(), Type.NUMBER);
		assertTrue(((NumberToken) tokens.get(0)).imaginary);
		assertEquals(new Double(1), (Double) ((ComplexNumber) ((NumberToken) tokens.get(0)).getValue()).getReal());
		assertEquals(new Double(-2), (Double) ((ComplexNumber) ((NumberToken) tokens.get(0)).getValue()).getImaginary());
	}

	@Test
	public void testComplexTokenization6() throws Exception {
		Tokenizer<ComplexNumber> tokenizer = new Tokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "1.73 - 16i * sin(3+2i)";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(6, tokens.size());
		assertEquals(tokens.get(0).getType(), Type.NUMBER);
		assertTrue(((NumberToken) tokens.get(0)).imaginary);
		assertEquals(new Double(1.73d), (Double) ((ComplexNumber) ((NumberToken) tokens.get(0)).getValue()).getReal());
		assertEquals(new Double(-16d), (Double) ((ComplexNumber) ((NumberToken) tokens.get(0)).getValue()).getImaginary());

		assertTrue(((NumberToken) tokens.get(4)).imaginary);
		assertEquals(new Double(3d), (Double) ((ComplexNumber) ((NumberToken) tokens.get(4)).getValue()).getReal());
		assertEquals(new Double(2d), (Double) ((ComplexNumber) ((NumberToken) tokens.get(4)).getValue()).getImaginary());
	}

	@Test
	public void testComplexTokenization7() throws Exception {
		Tokenizer<ComplexNumber> tokenizer = new Tokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "1 - 0i";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(1, tokens.size());
		assertEquals(tokens.get(0).getType(), Type.NUMBER);
		assertTrue(((NumberToken) tokens.get(0)).imaginary);
		assertEquals(new ComplexNumber(1d,0d), (ComplexNumber) ((NumberToken) tokens.get(0)).getValue());
	}
}
