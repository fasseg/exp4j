package net.objecthunter.exp4j.shuntingyard;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.objecthunter.exp4j.tokens.FunctionToken;
import net.objecthunter.exp4j.tokens.NumberToken;
import net.objecthunter.exp4j.tokens.OperatorToken;
import net.objecthunter.exp4j.tokens.Token;

import org.junit.Test;

public class ShuntingYardTest {
	@Test
	public void testShuntingYard1() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("1+1");
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(Token.NUMBER, tokens.get(1).getType());
		assertEquals(Token.OPERATOR, tokens.get(2).getType());
		assertEquals("+", ((OperatorToken) tokens.get(2)).getOperator().getSymbol());
		assertEquals(2, ((OperatorToken) tokens.get(2)).getOperator().getArgumentCount());
	}
	
	@Test
	public void testShuntingYard2() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("-1");
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(Token.OPERATOR, tokens.get(1).getType());
		assertEquals("-", ((OperatorToken) tokens.get(1)).getOperator().getSymbol());
		assertEquals(1, ((OperatorToken) tokens.get(1)).getOperator().getArgumentCount());
	}
	@Test
	public void testShuntingYard3() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("(-1)");
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(Token.OPERATOR, tokens.get(1).getType());
		assertEquals("-", ((OperatorToken) tokens.get(1)).getOperator().getSymbol());
		assertEquals(1, ((OperatorToken) tokens.get(1)).getOperator().getArgumentCount());
	}
	@Test
	public void testShuntingYard4() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("sin(1)");
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(Token.FUNCTION, tokens.get(1).getType());
		assertEquals("sin", ((FunctionToken) tokens.get(1)).getFunction().getName());
	}
	@Test
	public void testShuntingYard5() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("sin(1+2)");
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(1d, ((NumberToken) tokens.get(0)).getValue(),0d);
		assertEquals(Token.NUMBER, tokens.get(1).getType());
		assertEquals(2d, ((NumberToken) tokens.get(1)).getValue(),0d);
		assertEquals(Token.OPERATOR, tokens.get(2).getType());
		assertEquals("+", ((OperatorToken) tokens.get(2)).getOperator().getSymbol());
		assertEquals(2, ((OperatorToken) tokens.get(2)).getOperator().getArgumentCount());
		assertEquals(Token.FUNCTION, tokens.get(3).getType());
		assertEquals("sin", ((FunctionToken) tokens.get(3)).getFunction().getName());
	}
}
