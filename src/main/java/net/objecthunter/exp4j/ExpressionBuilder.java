package net.objecthunter.exp4j;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.objecthunter.exp4j.calculable.Calculable;
import net.objecthunter.exp4j.exceptions.UnparseableExpressionException;
import net.objecthunter.exp4j.function.CustomFunction;
import net.objecthunter.exp4j.operator.CustomOperator;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.Tokenizer;

public class ExpressionBuilder<T> {

	private String expression;
	private Class<T> returnType;
	private final Set<String> variables = new HashSet<>();
	private final Map<String, CustomFunction> functions = new HashMap<>();
	private final Map<String, CustomOperator> operators = new HashMap<>();

	public ExpressionBuilder(final String expression, final Class<T> returnType) {
        if (expression == null || expression.trim().length() == 0){
            throw new IllegalArgumentException("expression can not be empty");
        }
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

	public Calculable<T> build() throws UnparseableExpressionException {
		if (Double.class.equals(this.returnType) || Float.class.equals(this.returnType) ||
                BigDecimal.class.equals(this.returnType) || ComplexNumber.class.equals(this.returnType)) {
			Tokenizer<T> tok = new Tokenizer<>(this.returnType);
			List<Token> tokens = ShuntingYard.translateToReversePolishNotation(tok.tokenizeExpression(expression, variables, functions, operators));
			return new Calculable<T>(tokens);
		} else {
			throw new RuntimeException("Unparseable because calculation return type is unknown to exp4j.");
		}
	}
}
