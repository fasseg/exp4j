package net.objecthunter.exp4j.operator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.objecthunter.exp4j.expression.ExpressionBuilder;
import net.objecthunter.exp4j.function.Functions;

import org.junit.Test;

public class OperatorsTest {
	@Test
	public void testgetOperatorChars() throws Exception {
		char[] chars = Operators.getAllowedOperatorChars();
		assertEquals(19, chars.length);
		for (char ch : chars) {
			assertTrue(ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch ==
					'%' || ch == '^' || ch == '!' || ch == '#' || ch == '§'
					|| ch == '$' || ch == '&' || ch == ';' || ch == ':'
					|| ch == '~' || ch == '<' || ch == '>' || ch == '|' || ch ==
					'=' || ch == 'E');
		}
	}
	
	@Test
	public void testAddition() throws Exception {
		Operator<Double> op = Operators.getBuiltinOperator('+', 2, ExpressionBuilder.MODE_DOUBLE);
		assertEquals(4d,op.apply(2d,2d),0d);
		
	}

	@Test
	public void testSubtraction() throws Exception {
		Operator<Double> op = Operators.getBuiltinOperator('-', 2, ExpressionBuilder.MODE_DOUBLE);
		assertEquals(1d,op.apply(3d,2d),0d);
		
	}
	@Test
	public void testMultiplication() throws Exception {
		Operator<Double> op = Operators.getBuiltinOperator('*', 2, ExpressionBuilder.MODE_DOUBLE);
		assertEquals(6d,op.apply(2d,3d),0d);
		
	}
	@Test
	public void testDivision() throws Exception {
		Operator<Double> op = Operators.getBuiltinOperator('/', 2, ExpressionBuilder.MODE_DOUBLE);
		assertEquals(3d,op.apply(6d,2d),0d);
		
	}
	@Test
	public void testPower() throws Exception {
		Operator<Double> op = Operators.getBuiltinOperator('^', 2, ExpressionBuilder.MODE_DOUBLE);
		assertEquals(1d,op.apply(0d,0d),0d);
		assertEquals(144d,op.apply(12d,2d),0d);
		
	}
	@Test
	public void testModulo() throws Exception {
		Operator<Double> op = Operators.getBuiltinOperator('%', 2, ExpressionBuilder.MODE_DOUBLE);
		assertEquals(1d,op.apply(3d,2d),0d);
		
	}
	@Test
	public void testUnaryMinus() throws Exception {
		Operator<Double> op = Operators.getBuiltinOperator('-', 1, ExpressionBuilder.MODE_DOUBLE);
		assertEquals(-2d,op.apply(2d),0d);
		
	}

}
