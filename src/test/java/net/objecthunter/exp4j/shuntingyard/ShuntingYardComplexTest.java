package net.objecthunter.exp4j.shuntingyard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.objecthunter.exp4j.expression.ExpressionBuilder;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;
import net.objecthunter.exp4j.tokens.FunctionToken;
import net.objecthunter.exp4j.tokens.NumberToken;
import net.objecthunter.exp4j.tokens.OperatorToken;
import net.objecthunter.exp4j.tokens.Token;

import org.junit.Test;

public class ShuntingYardComplexTest {
	@Test
	public void testShuntingYard1() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("1+1i",
				ExpressionBuilder.MODE_COMPLEX);
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(Token.NUMBER, tokens.get(1).getType());
		assertEquals(Token.OPERATOR, tokens.get(2).getType());
		assertEquals("+", ((OperatorToken) tokens.get(2)).getOperator()
				.getSymbol());
		assertEquals(2, ((OperatorToken) tokens.get(2)).getOperator()
				.getArgumentCount());
	}
}
