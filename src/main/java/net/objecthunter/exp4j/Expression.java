package net.objecthunter.exp4j;

import net.objecthunter.exp4j.antlr.Exp4jGrammarLexer;
import net.objecthunter.exp4j.antlr.Exp4jGrammarParser;
import net.objecthunter.exp4j.function.Function;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Expression {

    private final String expression;

    private final Map<String, Double> variables;

    private final Map<String, Function> userFunctions;

    private Pattern variablePattern = Pattern.compile("[_a-zA-z]\\w*");

    public Expression(final String expression) {
        this.expression = expression;
        this.variables = new HashMap<>();
        this.userFunctions = new HashMap<>();
    }

    public Expression(final String expression, final Map<String, Double> variables) {
        this.expression = expression;
        this.variables = variables;
        this.userFunctions = new HashMap<>();
        variables.keySet().forEach(v -> this.checkName(v));
    }

    public Expression(final String expression, final Map<String, Double> variables, final Map<String, Function> userFunctions) {
        this.expression = expression;
        this.variables = variables;
        this.userFunctions = userFunctions;
        variables.keySet().forEach(v -> this.checkName(v));
    }

    public List<String> validate() {
        return this.validate(true);
    }

    public List<String> validate(boolean checkIfVariablesAreSet) {
        return null;
    }

    public void setVariable(final String name, final double value) {
        this.checkName(name);
        this.variables.put(name, value);
    }

    public void addFunction(final Function func) {
        this.checkName(func.getName());
        this.userFunctions.put(func.getName(), func);
    }

    private void checkName(final String name) {
        if (!variablePattern.matcher(name).matches()) {
            throw new IllegalArgumentException(String.format("Variable name %s is invalid", name));
        }
    }

    public ParseTree parseExpression() {
        final ExpressionErrorListener errorListener = new ExpressionErrorListener();
        final Exp4jGrammarLexer lexer = new Exp4jGrammarLexer(CharStreams.fromString(expression));
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final Exp4jGrammarParser parser = new Exp4jGrammarParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        return parser.expression();
    }

    public double evaluate() {
        final ExpressionVisitor visitor = new ExpressionVisitor(this.variables, this.userFunctions);
        return visitor.visit(this.parseExpression());
    }
}
