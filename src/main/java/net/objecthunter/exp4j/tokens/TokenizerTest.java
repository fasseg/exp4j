package net.objecthunter.exp4j.tokens;

import java.util.List;

import junit.framework.Assert;

import net.objecthunter.exp4j.tokens.Tokenizer.NumberToken;
import net.objecthunter.exp4j.tokens.Tokenizer.Token;
import net.objecthunter.exp4j.tokens.Tokenizer.Token.Type;

import org.junit.Test;

public class TokenizerTest {

	@Test
	public void testTokenization1() {
		Tokenizer<Float> tokenizer = new Tokenizer<>(Float.class);
		String expression = "2 + 2";
		List<Token> tokens = tokenizer.tokenizeExpression(expression);
		Assert.assertTrue(tokens.size() == 3);
		Assert.assertTrue(tokens.get(0).getType() == Type.NUMBER);
		Assert.assertTrue(tokens.get(1).getType() == Type.OPERATOR);
		Assert.assertTrue(tokens.get(2).getType() == Type.NUMBER);
	}

	@Test
	public void testTokenization2() {
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
	public void testTokenization3() {
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
	public void testTokenization4() {
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
}
