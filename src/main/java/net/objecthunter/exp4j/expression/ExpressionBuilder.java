package net.objecthunter.exp4j.expression;

import net.objecthunter.exp4j.exception.UnparseableExpressionException;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.shuntingyard.ShuntingYard;
import net.objecthunter.exp4j.tokens.Token;

import java.util.*;

public class ExpressionBuilder<T> {

    public static final int MODE_DOUBLE = 1;
    public static final String PROPERTY_UNARY_HIGH_PRECEDENCE = "exp4j.unary.precedence.high";

    private final String expression;

    private Map<String, Operator> customOperators = new HashMap<>();
    private Map<String, Function> customFunctions = new HashMap<>();
    private Map<String, T> variables = new HashMap<>();


    public ExpressionBuilder(String expression) {
        super();
        this.expression = expression;
    }

    public ExpressionBuilder variable(String name, T value) {
        variables.put(name, value);
        return this;
    }

    public ExpressionBuilder variables(String[] names, T[] values) {
        final int len = names.length;
        if (len != values.length) {
            throw new IllegalArgumentException("The number of values has to match the number of variable names");
        }
        for (int i = 0; i < len; i++) {
            variables.put(names[i], values[i]);
        }
        return this;
    }

    public ExpressionBuilder variables(String ... names) {
        final int len = names.length;
        for (int i = 0; i < len; i++) {
            variables.put(names[i], null);
        }
        return this;
    }

    public ExpressionBuilder variables(Collection<String> names, Collection<T> values) {
        final int len = names.size();
        if (len != values.size()) {
            throw new IllegalArgumentException("The number of values has to match the number of variable names");
        }
        Iterator<String> nameIt = names.iterator();
        Iterator<T> valueIt = values.iterator();
        while (nameIt.hasNext()) {
            variables.put(nameIt.next(), valueIt.next());
        }
        return this;
    }

    public ExpressionBuilder variables(Map<String, T> variables) {
        this.variables.putAll(variables);
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

    public Expression<Double> build() throws UnparseableExpressionException {
        final List<Token> tokens = new ShuntingYard(variables, customFunctions, customOperators).transformRpn(expression, MODE_DOUBLE);
        return new DoubleExpression(expression, tokens);
    }

    public DoubleExpression buildDouble() throws UnparseableExpressionException {
        final List<Token> tokens = new ShuntingYard(variables, customFunctions, customOperators).transformRpn(expression, MODE_DOUBLE);
        return new DoubleExpression(expression, tokens);
    }
}
