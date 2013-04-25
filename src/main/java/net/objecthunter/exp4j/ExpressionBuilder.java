package net.objecthunter.exp4j;

import java.math.BigDecimal;

import net.objecthunter.exp4j.calculable.BigDecimalCalculable;
import net.objecthunter.exp4j.calculable.Calculable;
import net.objecthunter.exp4j.calculable.ComplexCalculable;
import net.objecthunter.exp4j.calculable.DoubleCalculable;
import net.objecthunter.exp4j.calculable.FloatCalculable;

public class ExpressionBuilder<T> {
	private String expression;
	private Class<?> returnType;
	
	public ExpressionBuilder(final String expression, final Class<T> returnType) {
		this.expression = expression;
		this.returnType = returnType;
	}

	public Calculable build(){
		if (this.returnType == Double.class){
			return buildDouble();
		}else if (this.returnType == Float.class){
			return buildFloat();
		}else if (this.returnType == BigDecimal.class){
			return buildBigDecimal();
		}else if (this.returnType == ComplexNumber.class){
			return buildComplex();
		}else{
			throw new RuntimeException("Unparseable");
		}
	}
	
	private Calculable<BigDecimal> buildBigDecimal() {
		return new BigDecimalCalculable();
	}

	private Calculable<Double> buildDouble(){
		return new DoubleCalculable();
	}
	
	private Calculable<Float> buildFloat(){
		return new FloatCalculable();
	}
	private Calculable<ComplexNumber> buildComplex(){
		return new ComplexCalculable();
	}

}
