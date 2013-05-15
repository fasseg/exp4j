package net.objecthunter.exp4j;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.objecthunter.exp4j.calculable.BigDecimalCalculable;
import net.objecthunter.exp4j.calculable.Calculable;
import net.objecthunter.exp4j.calculable.ComplexCalculable;
import net.objecthunter.exp4j.calculable.DoubleCalculable;
import net.objecthunter.exp4j.calculable.FloatCalculable;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.Tokenizer;

public class ExpressionBuilder<T> {
	private String expression;
	private Class<?> returnType;
	private final Set<String> variables = new HashSet<>();

	public ExpressionBuilder(final String expression, final Class<T> returnType) {
		this.expression = expression;
		this.returnType = returnType;
	}

	public ExpressionBuilder<T> variable(String var) {
		variables.add(var);
		return this;
	}

	public ExpressionBuilder<T> variables(Set<String> vars) {
		for (String var : vars) {
			this.variable(var);
		}
		return this;
	}
	
	public ExpressionBuilder<T> variables(String ...vars){
		for (String var:vars){
			this.variable(var);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public Calculable<T> build() {
		if (this.returnType == Double.class) {
			Tokenizer<Double> tok = new Tokenizer<>(Double.class);
			List<Token> tokens = ShuntingYard.translateToReversePolishNotation(tok.tokenizeExpression(expression,variables));
			return (Calculable<T>) new DoubleCalculable(tokens);
		} else if (this.returnType == Float.class) {
			Tokenizer<Float> tok = new Tokenizer<>(Float.class);
			List<Token> tokens = ShuntingYard.translateToReversePolishNotation(tok.tokenizeExpression(expression,variables));
			return (Calculable<T>) new FloatCalculable(tokens);
		} else if (this.returnType == BigDecimal.class) {
			Tokenizer<BigDecimal> tok = new Tokenizer<>(BigDecimal.class);
			List<Token> tokens = ShuntingYard.translateToReversePolishNotation(tok.tokenizeExpression(expression,variables));
			return (Calculable<T>) new BigDecimalCalculable(tokens);
		} else if (this.returnType == ComplexNumber.class) {
			Tokenizer<ComplexNumber> tok = new Tokenizer<>(ComplexNumber.class);
			List<Token> tokens = ShuntingYard.translateToReversePolishNotation(tok.tokenizeExpression(expression,variables));
			return (Calculable<T>) new ComplexCalculable(tokens);
		} else {
			throw new RuntimeException("Unparseable");
		}
	}

}
