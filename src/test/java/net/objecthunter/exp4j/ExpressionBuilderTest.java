package net.objecthunter.exp4j;

import java.math.BigDecimal;

import org.junit.Test;

public class ExpressionBuilderTest {
	
	@Test
	public void testExpression1(){
		ExpressionBuilder<Double> e = new ExpressionBuilder<>("2+2",Double.class);
		System.out.println(e.build().calculate());
	}
	@Test
	public void testExpression2(){
		ExpressionBuilder<Float> e = new ExpressionBuilder<>("2+2",Float.class);
		System.out.println(e.build().calculate());
	}

	@Test
	public void testExpression3(){
		ExpressionBuilder<BigDecimal> e = new ExpressionBuilder<>("2+2",BigDecimal.class);
		System.out.println(e.build().calculate());
	}

	@Test
	public void testExpression4(){
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("2+2",ComplexNumber.class);
		System.out.println(e.build().calculate());
	}
}
