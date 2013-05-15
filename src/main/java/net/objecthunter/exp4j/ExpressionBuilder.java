package net.objecthunter.exp4j;

import java.math.BigDecimal;

import net.objecthunter.exp4j.calculable.BigDecimalCalculable;
import net.objecthunter.exp4j.calculable.Calculable;
import net.objecthunter.exp4j.calculable.ComplexCalculable;
import net.objecthunter.exp4j.calculable.DoubleCalculable;
import net.objecthunter.exp4j.calculable.FloatCalculable;
import net.objecthunter.exp4j.tokenizer.Tokenizer;

public class ExpressionBuilder<T> {
	private String expression;
	private Class<?> returnType;

	public ExpressionBuilder(final String expression, final Class<T> returnType) {
		this.expression = expression;
		this.returnType = returnType;
	}

	public Calculable<T> build() {
		if (this.returnType == Double.class) {
			Tokenizer<Double> tok = new Tokenizer<>(Double.class);
			return (Calculable<T>) new DoubleCalculable(ShuntingYard.translateToReversePolishNotation(tok.tokenizeExpression(expression)));
		} else if (this.returnType == Float.class) {
			Tokenizer<Float> tok = new Tokenizer<>(Float.class);
			return (Calculable<T>) new FloatCalculable(ShuntingYard.translateToReversePolishNotation(tok.tokenizeExpression(expression)));
		} else if (this.returnType == BigDecimal.class) {
			Tokenizer<BigDecimal> tok = new Tokenizer<>(BigDecimal.class);
			return (Calculable<T>) new BigDecimalCalculable(ShuntingYard.translateToReversePolishNotation(tok.tokenizeExpression(expression)));
		} else if (this.returnType == ComplexNumber.class) {
			Tokenizer<ComplexNumber> tok = new Tokenizer<>(ComplexNumber.class);
			return (Calculable<T>) new ComplexCalculable(ShuntingYard.translateToReversePolishNotation(tok.tokenizeExpression(expression)));
		} else {
			throw new RuntimeException("Unparseable");
		}
	}

}
