/*
   Copyright 2011 frank asseg

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package de.congrace.exp4j;

import static de.congrace.exp4j.OperatorToken.Operation.ADDITION;
import static de.congrace.exp4j.OperatorToken.Operation.DIVISION;
import static de.congrace.exp4j.OperatorToken.Operation.MULTIPLICATION;
import static de.congrace.exp4j.OperatorToken.Operation.SUBTRACTION;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

import de.congrace.exp4j.FunctionToken.Function;

public class TokenizerTest {
	private final Tokenizer tokenizer = new Tokenizer();

	@Test(expected = UnknownFunctionException.class)
	public void testFunctionException() throws Exception {
		FunctionToken tok = new FunctionToken("foo");
		tok.getFunction();
	}

	@Test
	public void testGetFunction() throws Exception {
		FunctionToken token = new FunctionToken("acos");
		assertTrue(token.getFunction() == Function.ACOS);
	}

	@Test
	public void testInfixTokenize() throws Exception {
		String expr = "2+45 -   12";
		Token[] expected = new Token[] { new NumberToken("2"), new OperatorToken("+", ADDITION), new NumberToken("45"), new OperatorToken("-", SUBTRACTION),
				new NumberToken("12") };
		Token[] actual = tokenizer.tokenize(expr);
		assertArrayEquals(expected, actual);
		expr = "2*4412+23/12";
		expected = new Token[] { new NumberToken("2"), new OperatorToken("*", MULTIPLICATION), new NumberToken("4412"), new OperatorToken("+", ADDITION),
				new NumberToken("23"), new OperatorToken("/", DIVISION), new NumberToken("12") };
		actual = tokenizer.tokenize(expr);
		assertArrayEquals(expected, actual);
		expr = "2*4.412+2.3/12";
		expected = new Token[] { new NumberToken("2"), new OperatorToken("*", MULTIPLICATION), new NumberToken("4.412"), new OperatorToken("+", ADDITION),
				new NumberToken("2.3"), new OperatorToken("/", DIVISION), new NumberToken("12") };
		actual = tokenizer.tokenize(expr);
		assertArrayEquals(expected, actual);
		expr = "2*4.4+(2.3/12)*4+(20-2)";
		expected = new Token[] { new NumberToken("2"), new OperatorToken("*", MULTIPLICATION), new NumberToken("4.4"), new OperatorToken("+", ADDITION),
				new ParenthesisToken("("), new NumberToken("2.3"), new OperatorToken("/", DIVISION), new NumberToken("12"), new ParenthesisToken(")"),
				new OperatorToken("*", MULTIPLICATION), new NumberToken("4"), new OperatorToken("+", ADDITION), new ParenthesisToken("("),
				new NumberToken("20"), new OperatorToken("-", SUBTRACTION), new NumberToken("2"), new ParenthesisToken(")") };
		actual = tokenizer.tokenize(expr);
		assertArrayEquals(expected, actual);
	}
	
//	NumberFormat.getInstance(locale);

    @Test
    public void testDecimalPlaceLocaleInfixTokenise() throws Exception {
        String expr = "1.2*3000.5";
        Token[] expected = new Token[] { new NumberToken("1.2"), new OperatorToken("*", MULTIPLICATION), new NumberToken("3000.5")};
        Token[] actual = tokenizer.tokenize(expr);
        assertArrayEquals(expected, actual);
        
        expr = "1,2*3000,5";
        expected = new Token[] { new NumberToken("1.2"), new OperatorToken("*", MULTIPLICATION), new NumberToken("3000.5")};
        actual = tokenizer.tokenize(expr, new Locale("ru"));
        
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testGroupingSeparaterLocaleInfixTokenise() throws Exception {
        String expr = "1,000*3,000";
        Token[] expected = new Token[] { new NumberToken("1000"), new OperatorToken("*", MULTIPLICATION), new NumberToken("3000")};
        Token[] actual = tokenizer.tokenize(expr);
        assertArrayEquals(expected, actual);
        
        expr = "1.000*3.000";
        expected = new Token[] { new NumberToken("1000"), new OperatorToken("*", MULTIPLICATION), new NumberToken("3000")};
        actual = tokenizer.tokenize(expr, new Locale("es"));
        
        assertArrayEquals(expected, actual);
    }

	@Test(expected = UnparsableExpressionException.class)
	public void testInfixTokenizeError() throws Exception {
		String expr = "2+45~-12";
		tokenizer.tokenize(expr);
	}
}
