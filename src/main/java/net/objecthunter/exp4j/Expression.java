package net.objecthunter.exp4j;

import net.objecthunter.exp4j.antlr.Exp4jGrammarLexer;
import net.objecthunter.exp4j.antlr.Exp4jGrammarParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Expression {

    private final String expression;

    private final Map<String, Double> variables;

    private Pattern variablePattern = Pattern.compile("[_a-zA-z]\\w*");

    public Expression(final String expression) {
        this.expression = expression;
        this.variables = new HashMap<>();
    }

    public Expression(final String expression, final Map<String, Double> variables) {
        this.expression = expression;
        this.variables = variables;
        variables.keySet().forEach(v -> this.checkVariableName(v));
    }

    public void setVariable(final String name, final double value) {
        this.checkVariableName(name);
        this.variables.put(name, value);
    }

    private void checkVariableName(final String name) {
        if (!variablePattern.matcher(name).matches()) {
            throw new IllegalArgumentException(String.format("Variable name %s is invalid", name));
        }
    }

    public double evaluate() {
        final ExpressionErrorListener errorListener = new ExpressionErrorListener();
        final Exp4jGrammarLexer lexer = new Exp4jGrammarLexer(CharStreams.fromString(expression));
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final Exp4jGrammarParser parser = new Exp4jGrammarParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        final ParseTree parseTree = parser.expression();
        final ExpressionVisitor visitor = new ExpressionVisitor(variables);
        return visitor.visit(parseTree);
    }
}
