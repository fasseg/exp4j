package net.objecthunter.exp4j.tokenizer;

import org.junit.Assert;
import org.junit.Test;


/**
 * This test is to check if {@link UnknownFunctionOrVariableException} generated when expression
 * contains unknown function or variable contains necessary expected details.
 *
 * @author Bartosz Firyn (sarxos)
 */
public class TokenizerUnknownTokenOrVariableTest {

	@Test(expected = UnknownFunctionOrVariableException.class)
	public void testTokenizationOfUnknownVariable() throws Exception {
		final Tokenizer tokenizer = new Tokenizer("3 + x", null, null, null);
		while (tokenizer.hasNext()) {
			tokenizer.nextToken();
		}
	}

	@Test
	public void testTokenizationOfUnknownVariable1Details() throws Exception {

		final Tokenizer tokenizer = new Tokenizer("3 + x", null, null, null);
		tokenizer.nextToken(); // 3
		tokenizer.nextToken(); // +

		try {
			tokenizer.nextToken(); // x
			Assert.fail("Variable 'x' should be unknown!");
		} catch (UnknownFunctionOrVariableException e) {
			Assert.assertEquals("x", e.getToken());
			Assert.assertEquals(4, e.getPosition());
			Assert.assertEquals("3 + x", e.getExpression());
		}
	}

	@Test
	public void testTokenizationOfUnknownVariable2Details() throws Exception {

		final Tokenizer tokenizer = new Tokenizer("x + 3", null, null, null);

		try {
			tokenizer.nextToken(); // x
			Assert.fail("Variable 'x' should be unknown!");
		} catch (UnknownFunctionOrVariableException e) {
			Assert.assertEquals("x", e.getToken());
			Assert.assertEquals(0, e.getPosition());
			Assert.assertEquals("x + 3", e.getExpression());
		}
	}

	@Test(expected = UnknownFunctionOrVariableException.class)
	public void testTokenizationOfUnknownFunction() throws Exception {
		final Tokenizer tokenizer = new Tokenizer("3 + p(1)", null, null, null);
		while (tokenizer.hasNext()) {
			tokenizer.nextToken();
		}
	}

	@Test
	public void testTokenizationOfUnknownFunction1Details() throws Exception {

		final Tokenizer tokenizer = new Tokenizer("3 + p(1)", null, null, null);
		tokenizer.nextToken(); // 3
		tokenizer.nextToken(); // +

		try {
			tokenizer.nextToken(); // p
			Assert.fail("Function 'p' should be unknown!");
		} catch (UnknownFunctionOrVariableException e) {
			Assert.assertEquals("p", e.getToken());
			Assert.assertEquals(4, e.getPosition());
			Assert.assertEquals("3 + p(1)", e.getExpression());
		}
	}

	@Test
	public void testTokenizationOfUnknownFunction2Details() throws Exception {

		final Tokenizer tokenizer = new Tokenizer("p(1) + 3", null, null, null);

		try {
			tokenizer.nextToken(); // p
			Assert.fail("Function 'p' should be unknown!");
		} catch (UnknownFunctionOrVariableException e) {
			Assert.assertEquals("p", e.getToken());
			Assert.assertEquals(0, e.getPosition());
			Assert.assertEquals("p(1) + 3", e.getExpression());
		}
	}

	@Test
	public void testTokenizationOfUnknownVariableWithoutException() throws Exception {

		final Tokenizer tokenizer = new Tokenizer("xiop+3", null, null, null, false, false);

		try {
			Token token = tokenizer.nextToken(); // x
			Assert.assertEquals(Token.TOKEN_VARIABLE, token.getType());
			Assert.assertEquals("xiop", ((VariableToken)token).getName() );
		} catch (UnknownFunctionOrVariableException e) {
			Assert.fail("No exception should be thrown!");
		}
	}

	@Test
	public void testTokenizationOfUnknownVariableWithoutException2() throws Exception {

		final Tokenizer tokenizer = new Tokenizer("x + 3 + y", null, null, null, false, false);

		try {
			Token token = tokenizer.nextToken(); // x
			Assert.assertEquals(Token.TOKEN_VARIABLE, token.getType());
			Assert.assertEquals("x", ((VariableToken)token).getName() );

			tokenizer.nextToken(); // +
			tokenizer.nextToken(); // 3
			tokenizer.nextToken(); // +

			token = tokenizer.nextToken(); // y
			Assert.assertEquals(Token.TOKEN_VARIABLE, token.getType());
			Assert.assertEquals("y", ((VariableToken)token).getName() );

		} catch (UnknownFunctionOrVariableException e) {
			Assert.fail("No exception should be thrown!");
		}
	}

	@Test
	public void testTokenizationOfUnknownVariableWithoutExceptionAndImplicit() throws Exception {

		final Tokenizer tokenizer = new Tokenizer("xyz + 3", null, null, null, true, false);

		try {
			Token token = tokenizer.nextToken(); // xyz
			Assert.assertEquals(Token.TOKEN_VARIABLE, token.getType());
			Assert.assertEquals("xyz", ((VariableToken)token).getName() );
		} catch (UnknownFunctionOrVariableException e) {
			Assert.fail("No exception should be thrown!");
		}
	}

	@Test(expected = UnknownFunctionOrVariableException.class)
	public void testTokenizationOfUnknownFunctionWithoutException() throws Exception {
		final Tokenizer tokenizer = new Tokenizer("3 + p(1)", null, null, null, false, false);
		while (tokenizer.hasNext()) {
			tokenizer.nextToken();
		}
	}

	@Test
	public void testTokenizationOfUnknownFunction1DetailsWithoutException() throws Exception {

		final Tokenizer tokenizer = new Tokenizer("3 + p(1)", null, null, null, false, false);
		tokenizer.nextToken(); // 3
		tokenizer.nextToken(); // +

		try {
			tokenizer.nextToken(); // p
			Assert.fail("Function 'p' should be unknown!");
		} catch (UnknownFunctionOrVariableException e) {
			Assert.assertEquals("p", e.getToken());
			Assert.assertEquals(4, e.getPosition());
			Assert.assertEquals("3 + p(1)", e.getExpression());
		}
	}

	@Test
	public void testTokenizationOfUnknownFunction2DetailsWithoutException() throws Exception {

		final Tokenizer tokenizer = new Tokenizer("p(1) + 3", null, null, null, false, false);

		try {
			tokenizer.nextToken(); // p
			Assert.fail("Function 'p' should be unknown!");
		} catch (UnknownFunctionOrVariableException e) {
			Assert.assertEquals("p", e.getToken());
			Assert.assertEquals(0, e.getPosition());
			Assert.assertEquals("p(1) + 3", e.getExpression());
		}
	}
}
