package net.objecthunter.exp4j.tokenizer;

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
		Assert.assertTrue(tokens.size() == 3);
		Assert.assertTrue(tokens.get(0).getType() == Type.NUMBER);
		Assert.assertTrue(tokens.get(1).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(2).getType() == Type.NUMBER);
	}

	@Test
	public void testTokenization2() throws Exception {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "sin(2)";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		Assert.assertTrue(tokens.size() == 4);
		Assert.assertTrue(tokens.get(0).getType() == Type.FUNCTION);
		Assert.assertTrue(tokens.get(1).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(2).getType() == Type.NUMBER);
		Assert.assertTrue(tokens.get(3).getType() == Type.PARANTHESES);
	}
	@Test
	public void testTokenization3() throws Exception {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "3 * sin(2)/0.5";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		Assert.assertTrue(tokens.size() == 8);
		Assert.assertTrue(tokens.get(0).getType() == Type.NUMBER);
		Assert.assertTrue(tokens.get(1).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(2).getType() == Type.FUNCTION);
		Assert.assertTrue(tokens.get(3).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(4).getType() == Type.NUMBER);
		Assert.assertTrue(tokens.get(5).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(6).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(7).getType() == Type.NUMBER);
		Assert.assertTrue(((NumberToken<Float>)tokens.get(7)).getValue() == 0.5d);

	}

	@Test
	public void testTokenization4() throws Exception {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "3 * sin(2+sin(12.745))/0.5";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		Assert.assertTrue(tokens.size() == 13);
		Assert.assertTrue(tokens.get(0).getType() == Type.NUMBER);
		Assert.assertTrue(tokens.get(1).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(2).getType() == Type.FUNCTION);
		Assert.assertTrue(tokens.get(3).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(4).getType() == Type.NUMBER);
		Assert.assertTrue(tokens.get(5).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(6).getType() == Type.FUNCTION);
		Assert.assertTrue(tokens.get(7).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(8).getType() == Type.NUMBER);
		Assert.assertTrue(tokens.get(9).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(10).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(11).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(12).getType() == Type.NUMBER);

	}

	@Test
	public void testTokenization5() throws Exception {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "log(sin(1.0) + abs(2.5))";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		Assert.assertTrue(tokens.size() == 12);
		Assert.assertTrue(tokens.get(0).getType() == Type.FUNCTION);
		Assert.assertTrue(tokens.get(1).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(2).getType() == Type.FUNCTION);
		Assert.assertTrue(tokens.get(3).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(4).getType() == Type.NUMBER);
		Assert.assertTrue(tokens.get(5).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(6).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(7).getType() == Type.FUNCTION);
		Assert.assertTrue(tokens.get(8).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(9).getType() == Type.NUMBER);
		Assert.assertTrue(tokens.get(10).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(11).getType() == Type.PARANTHESES);
	}

	@Test
	public void testTokenization6() throws Exception {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "-1";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		Assert.assertTrue(tokens.size() == 2);
		Assert.assertTrue(tokens.get(0).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(1).getType() == Type.NUMBER);
		Assert.assertTrue(((NumberToken<Float>) tokens.get(1)).getValue() == 1d);
	}

	@Test
	public void testTokenization7() throws Exception {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "-1 * -sin(3 * (-1.2))";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		Assert.assertTrue(tokens.size() == 13);
		Assert.assertTrue(tokens.get(0).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(1).getType() == Type.NUMBER);
		Assert.assertTrue(tokens.get(2).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(3).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(4).getType() == Type.FUNCTION);
		Assert.assertTrue(tokens.get(5).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(6).getType() == Type.NUMBER);
		Assert.assertTrue(tokens.get(7).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(8).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(9).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(10).getType() == Type.NUMBER);
		Assert.assertTrue(tokens.get(11).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(12).getType() == Type.PARANTHESES);
	}

	@Test
	public void testComplexTokenization1() throws Exception {
		Tokenizer<ComplexNumber> tokenizer = new Tokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "1 + 1i";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		Assert.assertTrue(tokens.size() == 3);
		Assert.assertTrue(((NumberToken<Double>) tokens.get(0)).getValue() == 1d);
		Assert.assertFalse(((NumberToken<Double>) tokens.get(0)).isImaginary());
		Assert.assertTrue(tokens.get(1).getType() == Type.OPERATOR);
		Assert.assertTrue(((NumberToken<Double>) tokens.get(2)).getValue() == 1d);
		Assert.assertTrue(((NumberToken<Double>) tokens.get(2)).isImaginary());
	}
	
	@Test
	public void testComplexTokenization2() throws Exception {
		Tokenizer<ComplexNumber> tokenizer = new Tokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "10.4 + 17.8i";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		Assert.assertTrue(tokens.size() == 3);
		Assert.assertTrue(((NumberToken<Double>) tokens.get(0)).getValue() == 10.4d);
		Assert.assertTrue(tokens.get(1).getType() == Type.OPERATOR);
		Assert.assertTrue(((NumberToken<Double>) tokens.get(2)).getValue() == 17.8d);
	}

	@Test
	public void testComplexTokenization3() throws Exception {
		Tokenizer<ComplexNumber> tokenizer = new Tokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "10.4 - 17.8i";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		Assert.assertTrue(tokens.size() == 3);
		Assert.assertTrue(((NumberToken<Double>) tokens.get(0)).getValue() == 10.4d);
		Assert.assertTrue(tokens.get(1).getType() == Type.OPERATOR);
		Assert.assertTrue(((NumberToken<Double>) tokens.get(2)).getValue() == 17.8d);
	}

	@Test
	public void testComplexTokenization4() throws Exception {
		Tokenizer<ComplexNumber> tokenizer = new Tokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "3 * 10.4 - 17.8i";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		Assert.assertTrue(tokens.size() == 5);
		Assert.assertTrue(((NumberToken<Double>) tokens.get(0)).getValue() == 3d);
		Assert.assertTrue(tokens.get(1).getType() == Type.OPERATOR);
		Assert.assertTrue(((NumberToken<Double>) tokens.get(2)).getValue() == 10.4d);
		Assert.assertTrue(tokens.get(3).getType() == Type.OPERATOR);
		Assert.assertTrue(((NumberToken<Double>) tokens.get(4)).getValue() == 17.8d);
	}

	@Test
	public void testComplexTokenization5() throws Exception {
		Tokenizer<ComplexNumber> tokenizer = new Tokenizer<ComplexNumber>(ComplexNumber.class);
		String expression = "(3 * 10.4) - 17.8i";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		Assert.assertTrue(tokens.size() == 7);
		Assert.assertTrue(tokens.get(0).getType() == Type.PARANTHESES);
		Assert.assertTrue(((NumberToken<Double>) tokens.get(1)).getValue() == 3d);
		Assert.assertTrue(tokens.get(2).getType() == Type.OPERATOR);
		Assert.assertTrue(((NumberToken<Double>) tokens.get(3)).getValue() == 10.4d);
		Assert.assertTrue(tokens.get(4).getType() == Type.PARANTHESES);
		Assert.assertTrue(tokens.get(5).getType() == Type.OPERATOR);
		Assert.assertTrue(((NumberToken<Double>) tokens.get(6)).getValue() == 17.8d);
	}
}
