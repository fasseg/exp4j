package net.objecthunter.exp4j.expression;

import net.objecthunter.exp4j.tokens.Token;

import java.util.List;
import java.util.Map;

public abstract class Expression<T> {
    protected final String expression;
    protected final List<Token> tokens;

    public Expression(String expression, List<Token> tokens) {
        super();
        this.expression = expression;
        this.tokens = tokens;
    }

    public abstract Expression setVariables(Map<String, T> variables);

    public abstract Expression setVariable(String name, T value);

    public abstract T evaluate(Map<String, T> variables);

    public abstract T evaluate();

    public String getExpression() {
        return expression;
    }
}
