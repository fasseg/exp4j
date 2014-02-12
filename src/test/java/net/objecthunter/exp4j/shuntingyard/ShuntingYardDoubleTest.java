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

public class ShuntingYardDoubleTest {
	@Test
	public void testShuntingYard1() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("1+1",
				ExpressionBuilder.MODE_DOUBLE);
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
				ExpressionBuilder.MODE_DOUBLE);
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
				ExpressionBuilder.MODE_DOUBLE);
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
				ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(Token.FUNCTION, tokens.get(1).getType());
		assertEquals("sin", ((FunctionToken) tokens.get(1)).getFunction()
				.getName());
	}
	@Test
	public void testShuntingYard5() throws Exception {
		ShuntingYard sy = new ShuntingYard(null);
		List<Token> tokens = sy.transformRpn("sin(1+2)",
				ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(1d, (Double) ((NumberToken) tokens.get(0)).getValue(), 0d);
		assertEquals(Token.NUMBER, tokens.get(1).getType());
		assertEquals(2d, (Double) ((NumberToken) tokens.get(1)).getValue(), 0d);
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
				ExpressionBuilder.MODE_DOUBLE);
		assertEquals(Token.NUMBER, tokens.get(0).getType());
		assertEquals(3d, (Double) ((NumberToken) tokens.get(0)).getValue(), 0d);
		assertEquals(Token.NUMBER, tokens.get(1).getType());
		assertEquals(2d, (Double) ((NumberToken) tokens.get(1)).getValue(), 0d);
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
}
