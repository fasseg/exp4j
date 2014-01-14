package net.objecthunter.exp4j.tokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.objecthunter.exp4j.ComplexNumber;
import net.objecthunter.exp4j.tokenizer.Token.Type;

import org.junit.Test;

public class NextGenComplexTokenizerTest {
	@Test
	public void testComplexTokenization1() throws Exception {
		NextGenTokenizer<ComplexNumber> tokenizer = new NextGenTokenizer<ComplexNumber>(ComplexNumber.class);
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
		NextGenTokenizer<ComplexNumber> tokenizer = new NextGenTokenizer<ComplexNumber>(ComplexNumber.class);
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
		NextGenTokenizer<ComplexNumber> tokenizer = new NextGenTokenizer<ComplexNumber>(ComplexNumber.class);
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
		NextGenTokenizer<ComplexNumber> tokenizer = new NextGenTokenizer<ComplexNumber>(ComplexNumber.class);
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
		NextGenTokenizer<ComplexNumber> tokenizer = new NextGenTokenizer<ComplexNumber>(ComplexNumber.class);
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
		NextGenTokenizer<ComplexNumber> tokenizer = new NextGenTokenizer<ComplexNumber>(ComplexNumber.class);
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
		NextGenTokenizer<ComplexNumber> tokenizer = new NextGenTokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "1 - 0i";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(1, tokens.size());
		assertEquals(tokens.get(0).getType(), Type.NUMBER);
		assertFalse(((NumberToken) tokens.get(0)).imaginary);
		assertEquals(new ComplexNumber(1d,0d), (ComplexNumber) ((NumberToken) tokens.get(0)).getValue());
	}

	@Test
	public void testComplexTokenization8() throws Exception {
		NextGenTokenizer<ComplexNumber> tokenizer = new NextGenTokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "1 - -1i";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(1, tokens.size());
		assertEquals(tokens.get(0).getType(), Type.NUMBER);
		assertTrue(((NumberToken) tokens.get(0)).imaginary);
		assertEquals(new ComplexNumber(1d,1d), (ComplexNumber) ((NumberToken) tokens.get(0)).getValue());
	}
	@Test
	public void testComplexTokenization9() throws Exception {
		NextGenTokenizer<ComplexNumber> tokenizer = new NextGenTokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "-- 1 -- 0i";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(3, tokens.size());
		assertEquals(tokens.get(2).getType(), Type.NUMBER);
		assertFalse(((NumberToken) tokens.get(2)).imaginary);
		assertEquals(new ComplexNumber(1d,0d), (ComplexNumber) ((NumberToken) tokens.get(2)).getValue());
	}

	@Test
	public void testComplexTokenization10() throws Exception {
		NextGenTokenizer<ComplexNumber> tokenizer = new NextGenTokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "--1";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		assertEquals(3, tokens.size());
		assertEquals(tokens.get(0).getType(), Type.OPERATOR);
		assertEquals(tokens.get(1).getType(), Type.OPERATOR);
		assertEquals(tokens.get(2).getType(), Type.NUMBER);
		assertFalse(((NumberToken) tokens.get(2)).imaginary);
		assertEquals(new ComplexNumber(1d,0d), (ComplexNumber) ((NumberToken) tokens.get(2)).getValue());
	}

}
