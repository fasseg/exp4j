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

public class ShuntingYardBigDecimalTest {
	@Test
	public void testShuntingYard1() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("1+1",
				ExpressionBuilder.MODE_BIGDECIMAL);
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(Token.NUMBER, tokens.get(1).getType());
		assertEquals(Token.OPERATOR, tokens.get(2).getType());
		assertEquals("+", ((OperatorToken) tokens.get(2)).getOperator()
				.getSymbol());
		assertEquals(2, ((OperatorToken) tokens.get(2)).getOperator()
				.getArgumentCount());
	}

	@Test
	public void testShuntingYard2() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("-1",
				ExpressionBuilder.MODE_BIGDECIMAL);
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(Token.OPERATOR, tokens.get(1).getType());
		assertEquals("-", ((OperatorToken) tokens.get(1)).getOperator()
				.getSymbol());
		assertEquals(1, ((OperatorToken) tokens.get(1)).getOperator()
				.getArgumentCount());
	}
	@Test
	public void testShuntingYard3() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("(-1)",
				ExpressionBuilder.MODE_BIGDECIMAL);
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(Token.OPERATOR, tokens.get(1).getType());
		assertEquals("-", ((OperatorToken) tokens.get(1)).getOperator()
				.getSymbol());
		assertEquals(1, ((OperatorToken) tokens.get(1)).getOperator()
				.getArgumentCount());
	}
	@Test
	public void testShuntingYard4() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("sin(1)",
				ExpressionBuilder.MODE_BIGDECIMAL);
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(Token.FUNCTION, tokens.get(1).getType());
		assertEquals("sin", ((FunctionToken) tokens.get(1)).getFunction()
				.getName());
	}
	@Test
	public void testShuntingYard5() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("sin(1+2)",
				ExpressionBuilder.MODE_BIGDECIMAL);
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(BigDecimal.ONE, (BigDecimal) ((NumberToken) tokens.get(0)).getValue());
		assertEquals(Token.NUMBER, tokens.get(1).getType());
		assertEquals(new BigDecimal(2), (BigDecimal) ((NumberToken) tokens.get(1)).getValue());
		assertEquals(Token.OPERATOR, tokens.get(2).getType());
		assertEquals("+", ((OperatorToken) tokens.get(2)).getOperator()
				.getSymbol());
		assertEquals(2, ((OperatorToken) tokens.get(2)).getOperator()
				.getArgumentCount());
		assertEquals(Token.FUNCTION, tokens.get(3).getType());
		assertEquals("sin", ((FunctionToken) tokens.get(3)).getFunction()
				.getName());
	}
	@Test
	public void testShuntingYard6() throws Exception {
		Operator<Double> op = new Operator<Double>("#", 2, true, Operators.PRECEDENCE_ADDITION) {

			@Override
			public Double apply(Double... args) {
				return args[0] + args[1];
			}
		};
		Map<String, Operator> ops = new HashMap<>();
		ops.put(op.getSymbol(), op);
		ShuntingYard sy = new ShuntingYard(null, null, ops);
		List<Token> tokens = sy.transformRpn("3#2",
				ExpressionBuilder.MODE_BIGDECIMAL);
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(new BigDecimal(3), (BigDecimal) ((NumberToken) tokens.get(0)).getValue());
		assertEquals(Token.NUMBER, tokens.get(1).getType());
		assertEquals(new BigDecimal(2), (BigDecimal) ((NumberToken) tokens.get(1)).getValue());
		assertEquals(Token.OPERATOR, tokens.get(2).getType());
		assertEquals("#", ((OperatorToken) tokens.get(2)).getOperator()
				.getSymbol());
		assertEquals(2, ((OperatorToken) tokens.get(2)).getOperator()
				.getArgumentCount());
		assertTrue(((OperatorToken) tokens.get(2)).getOperator()
				.isLeftAssociative());
		assertEquals(Operators.PRECEDENCE_ADDITION,
				((OperatorToken) tokens.get(2)).getOperator().getPrecedence());
	}
	@Test
	public void testShuntingYard7() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("sin(1+2)",
				ExpressionBuilder.MODE_BIGDECIMAL);
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertTrue(((NumberToken) tokens.get(0)).getValue().getClass() == BigDecimal.class);
		assertEquals(BigDecimal.ONE, (BigDecimal) ((NumberToken) tokens.get(0)).getValue());
		assertEquals(Token.NUMBER, tokens.get(1).getType());
		assertTrue(((NumberToken) tokens.get(1)).getValue().getClass() == BigDecimal.class);
		assertEquals(BigDecimal.ONE.add(BigDecimal.ONE), (BigDecimal) ((NumberToken) tokens.get(1)).getValue());
		assertEquals(Token.OPERATOR, tokens.get(2).getType());
		assertEquals("+", ((OperatorToken) tokens.get(2)).getOperator()
				.getSymbol());
		assertEquals(2, ((OperatorToken) tokens.get(2)).getOperator()
				.getArgumentCount());
		assertEquals(Token.FUNCTION, tokens.get(3).getType());
		assertEquals("sin", ((FunctionToken) tokens.get(3)).getFunction()
				.getName());
	}
}
