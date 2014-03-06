package net.objecthunter.exp4j.expression;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.objecthunter.exp4j.exception.UnparseableExpressionException;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.shuntingyard.ShuntingYard;
import net.objecthunter.exp4j.tokens.Token;

public class ExpressionBuilder {

    public static final int MODE_DOUBLE = 1;
    public static final int MODE_COMPLEX = 2;
    public static final int MODE_BIGDECIMAL = 3;
    
	private final String expression;

	private Map<String, Operator> customOperators = new HashMap<>();
	private Map<String, Function> customFunctions = new HashMap<>();
	private Map<String, Double> variables = new HashMap<>();

	public ExpressionBuilder(String expression) {
		super();
		this.expression = expression;
	}

	public ExpressionBuilder variable(String name) {
		variables.put(name, 0d);
		return this;
	}

	public ExpressionBuilder variables(String[] names) {
		for (String name : names) {
			variables.put(name, 0d);
		}
		return this;
	}

	public ExpressionBuilder variables(Collection<String> names) {
		for (String name : names) {
			variables.put(name, 0d);
		}
		return this;
	}

	public ExpressionBuilder function(Function function) {
		customFunctions.put(function.getName(), function);
		return this;
	}

	public ExpressionBuilder functions(Collection<Function> functions) {
		for (Function function : functions) {
			customFunctions.put(function.getName(), function);
		}
		return this;
	}

	public ExpressionBuilder operator(Operator operator) {
		customOperators.put(operator.getSymbol(), operator);
		return this;
	}

	public ExpressionBuilder operators(Collection<Operator> operators) {
		for (Operator operator : operators) {
			customOperators.put(operator.getSymbol(), operator);
		}
		return this;
	}

	public DoubleExpression buildDouble() throws UnparseableExpressionException {
		final List<Token> tokens = new ShuntingYard(variables, customFunctions,
				customOperators).transformRpn(expression, MODE_DOUBLE);
		return new DoubleExpression(expression, tokens);
	}
	
	public BigDecimalExpression buildBigDecimal() throws UnparseableExpressionException {
		final List<Token> tokens = new ShuntingYard(variables, customFunctions, customOperators).transformRpn(expression, MODE_BIGDECIMAL);
		return new BigDecimalExpression(expression, tokens);
	}
	
	public ComplexExpression buildComplex() throws UnparseableExpressionException {
		final List<Token> tokens = new ShuntingYard(variables, customFunctions, customOperators).transformRpn(expression, MODE_COMPLEX);
		return new ComplexExpression(expression, tokens);
	}
}
