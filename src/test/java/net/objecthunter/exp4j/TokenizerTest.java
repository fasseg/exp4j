package net.objecthunter.exp4j;

import net.objecthunter.exp4j.exception.Exp4jParsingException;
import net.objecthunter.exp4j.model.Symbol;
import net.objecthunter.exp4j.tokenizer.Tokenizer;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TokenizerTest {
	
	private static String createExpression(final String[] data) {
		final StringBuilder e = new StringBuilder();
		Arrays.stream(data).forEach(e::append);
		return e.toString();
	}
	
	@Test
	public void testSimpleExpression() throws Exception {
		final String[] data = {"1", "+", "1"};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, null).tokenize();
		assertEquals(3, symbols.size());
		assertEquals(Symbol.Type.NUMBER, symbols.get(0).getType());
		assertEquals(Double.parseDouble(data[0]), symbols.get(0).getDoubleValue(), 0D);
		assertEquals(Symbol.Type.OPERATOR, symbols.get(1).getType());
		assertEquals("+", symbols.get(1).getStringValue());
		assertEquals(Symbol.Type.NUMBER, symbols.get(2).getType());
		assertEquals(Double.parseDouble(data[2]), symbols.get(2).getDoubleValue(), 0D);
	}
	
	
	@Test
	public void testCommaIncluded() throws Exception {
		final String[] data = {"1.34", "+", "1.72777728882919099201"};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, null).tokenize();
		assertEquals(3, symbols.size());
		assertEquals(Symbol.Type.NUMBER, symbols.get(0).getType());
		assertEquals(Double.parseDouble(data[0]), symbols.get(0).getDoubleValue(), 0D);
		assertEquals(Symbol.Type.OPERATOR, symbols.get(1).getType());
		assertEquals("+", symbols.get(1).getStringValue());
		assertEquals(Symbol.Type.NUMBER, symbols.get(2).getType());
		assertEquals(Double.parseDouble(data[2]), symbols.get(2).getDoubleValue(), 0D);
	}
	
	@Test
	public void testSpacesIncluded() throws Exception {
		final String[] data = {" 1.34 ", " + ", " 1.72777728882919099201 "};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, null).tokenize();
		assertEquals(3, symbols.size());
		assertEquals(Symbol.Type.NUMBER, symbols.get(0).getType());
		assertEquals(Double.parseDouble(data[0]), symbols.get(0).getDoubleValue(), 0D);
		assertEquals(Symbol.Type.OPERATOR, symbols.get(1).getType());
		assertEquals("+", symbols.get(1).getStringValue());
		assertEquals(Symbol.Type.NUMBER, symbols.get(2).getType());
		assertEquals(Double.parseDouble(data[2]), symbols.get(2).getDoubleValue(), 0D);
	}
	
	@Test
	public void testScientificNotation() throws Exception {
		final String[] data = {"1.34E10"};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, null).tokenize();
		assertEquals(1, symbols.size());
		assertEquals(Symbol.Type.NUMBER, symbols.get(0).getType());
		assertEquals(Double.parseDouble(data[0]), symbols.get(0).getDoubleValue(), 0D);
	}
	
	@Test
	public void testScientificNotationPositiveExponent() throws Exception {
		final String[] data = {"1.34E+10"};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, null).tokenize();
		assertEquals(1, symbols.size(), 1);
		assertEquals(Symbol.Type.NUMBER, symbols.get(0).getType());
		assertEquals(Double.parseDouble(data[0]), symbols.get(0).getDoubleValue(), 0D);
	}
	
	@Test
	public void testScientificNotationNegativeExponent() throws Exception {
		final String[] data = {"1.34E-10"};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, null).tokenize();
		assertEquals(1, symbols.size());
		assertEquals(Symbol.Type.NUMBER, symbols.get(0).getType());
		assertEquals(Double.parseDouble(data[0]), symbols.get(0).getDoubleValue(), 0D);
	}
	
	@Test(expected = Exp4jParsingException.class)
	public void testScientificNotationInvalid1() throws Exception {
		final String[] data = {"1.34E"};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, null).tokenize();
	}
	
	@Test(expected = Exp4jParsingException.class)
	public void testUnknownName() throws Exception {
		final String[] data = {"1.34", "+", "foo(2)"};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, null).tokenize();
	}
	
	@Test
	public void testBuiltinFunction() throws Exception {
		final String[] data = {"1.34", "+", "sin", "(", "1", ")"};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, null).tokenize();
		assertEquals(symbols.size(), 6);
		assertEquals(Symbol.Type.NUMBER, symbols.get(0).getType());
		assertEquals(Double.parseDouble(data[0]), symbols.get(0).getDoubleValue(), 0D);
		assertEquals(Symbol.Type.OPERATOR, symbols.get(1).getType());
		assertEquals("+", symbols.get(1).getStringValue());
		assertEquals(Symbol.Type.FUNCTION, symbols.get(2).getType());
		assertEquals("sin", symbols.get(2).getStringValue());
		assertEquals(Symbol.Type.OPEN_PARENTHESES, symbols.get(3).getType());
		assertEquals(Symbol.Type.NUMBER, symbols.get(4).getType());
		assertEquals(Double.parseDouble(data[4]), symbols.get(4).getDoubleValue(), 0D);
		assertEquals(Symbol.Type.CLOSE_PARENTHESES, symbols.get(5).getType());
	}
	
	@Test
	public void testBuiltinFunctionWith2Arguments() throws Exception {
		final String[] data = {"1.34", "+", "sin", "(", "1", ",", "3.445", ")"};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, null).tokenize();
		assertEquals(symbols.size(), 8);
		assertEquals(Symbol.Type.NUMBER, symbols.get(0).getType());
		assertEquals(Double.parseDouble(data[0]), symbols.get(0).getDoubleValue(), 0D);
		assertEquals(Symbol.Type.OPERATOR, symbols.get(1).getType());
		assertEquals("+", symbols.get(1).getStringValue());
		assertEquals(Symbol.Type.FUNCTION, symbols.get(2).getType());
		assertEquals("sin", symbols.get(2).getStringValue());
		assertEquals(Symbol.Type.OPEN_PARENTHESES, symbols.get(3).getType());
		assertEquals(Symbol.Type.NUMBER, symbols.get(4).getType());
		assertEquals(Double.parseDouble(data[4]), symbols.get(4).getDoubleValue(), 0D);
		assertEquals(Symbol.Type.SEPARATOR, symbols.get(5).getType());
		assertEquals(Symbol.Type.NUMBER, symbols.get(6).getType());
		assertEquals(Double.parseDouble(data[6]), symbols.get(6).getDoubleValue(), 0D);
		assertEquals(Symbol.Type.CLOSE_PARENTHESES, symbols.get(7).getType());
	}
	
	
	@Test
	public void testVariable() throws Exception {
		final String[] data = {"1.34", "+", "x"};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, Arrays.asList("x")).tokenize();
		assertEquals(symbols.size(), 3);
		assertEquals(Symbol.Type.NUMBER, symbols.get(0).getType());
		assertEquals(Double.parseDouble(data[0]), symbols.get(0).getDoubleValue(), 0D);
		assertEquals(Symbol.Type.OPERATOR, symbols.get(1).getType());
		assertEquals("+", symbols.get(1).getStringValue());
		assertEquals(Symbol.Type.VARIABLE, symbols.get(2).getType());
		assertEquals("x", symbols.get(2).getStringValue());
	}
	
	@Test
	public void testVariableWithUnderScore() throws Exception {
		final String[] data = {"1.34", "+", "x_y"};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, Arrays.asList("x_y")).tokenize();
		assertEquals(symbols.size(), 3);
		assertEquals(Symbol.Type.NUMBER, symbols.get(0).getType());
		assertEquals(Double.parseDouble(data[0]), symbols.get(0).getDoubleValue(), 0D);
		assertEquals(Symbol.Type.OPERATOR, symbols.get(1).getType());
		assertEquals(data[1], symbols.get(1).getStringValue());
		assertEquals(Symbol.Type.VARIABLE, symbols.get(2).getType());
		assertEquals(data[2], symbols.get(2).getStringValue());
	}

	@Test
	public void testVariableWithNumber() throws Exception {
		final String[] data = {"1.34", "+", "x1"};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, Arrays.asList("x1")).tokenize();
		assertEquals(symbols.size(), 3);
		assertEquals(Symbol.Type.NUMBER, symbols.get(0).getType());
		assertEquals(Double.parseDouble(data[0]), symbols.get(0).getDoubleValue(), 0D);
		assertEquals(Symbol.Type.OPERATOR, symbols.get(1).getType());
		assertEquals(data[1], symbols.get(1).getStringValue());
		assertEquals(Symbol.Type.VARIABLE, symbols.get(2).getType());
		assertEquals(data[2], symbols.get(2).getStringValue());
	}
	
	@Test
	public void testVariableWithNumberAndUnderscore() throws Exception {
		final String[] data = {"1.34", "+", "x_18_1a"};
		final String e = createExpression(data);
		final List<Symbol> symbols = new Tokenizer(e, null, null, Arrays.asList("x_18_1a")).tokenize();
		assertEquals(symbols.size(), 3);
		assertEquals(Symbol.Type.NUMBER, symbols.get(0).getType());
		assertEquals(Double.parseDouble(data[0]), symbols.get(0).getDoubleValue(), 0D);
		assertEquals(Symbol.Type.OPERATOR, symbols.get(1).getType());
		assertEquals(data[1], symbols.get(1).getStringValue());
		assertEquals(Symbol.Type.VARIABLE, symbols.get(2).getType());
		assertEquals(data[2], symbols.get(2).getStringValue());
	}
}
