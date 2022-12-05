package net.objecthunter.exp4j;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;

import java.util.*;

public class ExpressionBuilder {

    private final String expression;

    private final Map<String, Function> userFunctions;

    private final Set<String> variableNames;

    private boolean implicitMultiplication = true;

    /**
     * Create a new ExpressionBuilder instance and initialize it with a given expression string.
     *
     * @param expression the expression to be parsed
     */
    public ExpressionBuilder(String expression) {
        if (expression == null || expression.trim().length() == 0) {
            throw new IllegalArgumentException("Expression can not be empty");
        }
        this.expression = expression;
        this.userFunctions = new HashMap<>(4);
        this.variableNames = new HashSet<>(4);
    }

    /**
     * Add a {@link net.objecthunter.exp4j.function.Function} implementation available for use in the expression
     *
     * @param function the custom {@link net.objecthunter.exp4j.function.Function} implementation that should be available for use in the expression.
     * @return the ExpressionBuilder instance
     */
    public ExpressionBuilder function(Function function) {
        this.userFunctions.put(function.getName(), function);
        return this;
    }

    /**
     * Add multiple {@link net.objecthunter.exp4j.function.Function} implementations available for use in the expression
     *
     * @param functions the custom {@link net.objecthunter.exp4j.function.Function} implementations
     * @return the ExpressionBuilder instance
     */
    public ExpressionBuilder functions(Function... functions) {
        for (Function f : functions) {
            this.userFunctions.put(f.getName(), f);
        }
        return this;
    }

    /**
     * Add multiple {@link net.objecthunter.exp4j.function.Function} implementations available for use in the expression
     *
     * @param functions A {@link java.util.List} of custom {@link net.objecthunter.exp4j.function.Function} implementations
     * @return the ExpressionBuilder instance
     */
    public ExpressionBuilder functions(List<Function> functions) {
        for (Function f : functions) {
            this.userFunctions.put(f.getName(), f);
        }
        return this;
    }

    /**
     * Build the {@link Expression} instance using the custom operators and functions set.
     *
     * @return an {@link Expression} instance which can be used to evaluate the result of the expression
     */
    public Expression build() {
        if (expression.length() == 0) {
            throw new IllegalArgumentException("The expression can not be empty");
        }

        /* set the constants' variable names */
        variableNames.add("pi");
        variableNames.add("π");
        variableNames.add("e");
        variableNames.add("φ");


        return new Expression(this.expression, this.userFunctions);
    }

}
