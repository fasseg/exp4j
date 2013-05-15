package net.objecthunter.exp4j;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.objecthunter.exp4j.calculable.BigDecimalCalculable;
import net.objecthunter.exp4j.calculable.Calculable;
import net.objecthunter.exp4j.calculable.ComplexCalculable;
import net.objecthunter.exp4j.calculable.DoubleCalculable;
import net.objecthunter.exp4j.calculable.FloatCalculable;
import net.objecthunter.exp4j.function.CustomFunction;
import net.objecthunter.exp4j.operator.CustomOperator;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.Tokenizer;

public class ExpressionBuilder<T> {
	private String expression;
	private Class<?> returnType;
	private final Set<String> variables = new HashSet<>();
	private final Map<String, CustomFunction> functions = new HashMap<>();
	private final Map<String, CustomOperator> operators = new HashMap<>();

	public ExpressionBuilder(final String expression, final Class<T> returnType) {
		this.expression = expression;
		this.returnType = returnType;
	}

	public ExpressionBuilder<T> function(CustomFunction f) {
		functions.put(f.getName(), f);
		return this;
	}

	public ExpressionBuilder<T> functions(CustomFunction... funcs) {
		for (CustomFunction f : funcs) {
			this.function(f);
		}
		return this;
	}

	public ExpressionBuilder<T> operators(Collection<CustomOperator> ops) {
		for (CustomOperator op : ops) {
			this.operator(op);
		}
		return this;
	}

	public ExpressionBuilder<T> operator(CustomOperator op) {
		operators.put(op.getSymbol(), op);
		return this;
	}

	public ExpressionBuilder<T> operators(CustomOperator... ops) {
		for (CustomOperator op : ops) {
			this.operator(op);
		}
		return this;
	}

	public ExpressionBuilder<T> functions(Collection<CustomFunction> funcs) {
		for (CustomFunction f : funcs) {
			this.function(f);
		}
		return this;
	}

	public ExpressionBuilder<T> variable(String var) {
		variables.add(var);
		return this;
	}

	public ExpressionBuilder<T> variables(Collection<String> vars) {
		for (String var : vars) {
			this.variable(var);
		}
		return this;
	}

	public ExpressionBuilder<T> variables(String... vars) {
		for (String var : vars) {
			this.variable(var);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public Calculable<T> build() {
		if (this.returnType == Double.class) {
			Tokenizer<Double> tok = new Tokenizer<>(Double.class);
			List<Token> tokens = ShuntingYard.translateToReversePolishNotation(tok.tokenizeExpression(expression, variables));
			return (Calculable<T>) new DoubleCalculable(tokens);
		} else if (this.returnType == Float.class) {
			Tokenizer<Float> tok = new Tokenizer<>(Float.class);
			List<Token> tokens = ShuntingYard.translateToReversePolishNotation(tok.tokenizeExpression(expression, variables));
			return (Calculable<T>) new FloatCalculable(tokens);
		} else if (this.returnType == BigDecimal.class) {
			Tokenizer<BigDecimal> tok = new Tokenizer<>(BigDecimal.class);
			List<Token> tokens = ShuntingYard.translateToReversePolishNotation(tok.tokenizeExpression(expression, variables));
			return (Calculable<T>) new BigDecimalCalculable(tokens);
		} else if (this.returnType == ComplexNumber.class) {
			Tokenizer<ComplexNumber> tok = new Tokenizer<>(ComplexNumber.class);
			List<Token> tokens = ShuntingYard.translateToReversePolishNotation(tok.tokenizeExpression(expression, variables));
			return (Calculable<T>) new ComplexCalculable(tokens);
		} else {
			throw new RuntimeException("Unparseable");
		}
	}

}
