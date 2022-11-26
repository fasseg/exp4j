package net.objecthunter.exp4j;

import net.objecthunter.exp4j.antlr.Exp4jGrammarBaseVisitor;
import net.objecthunter.exp4j.antlr.Exp4jGrammarParser;
import net.objecthunter.exp4j.function.Factorial;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;

import java.util.Map;

public class ExpressionVisitor extends Exp4jGrammarBaseVisitor<Double> {

    private final Map<String, Double> variables;

    private final Map<String, Function> userFunctions;

    public ExpressionVisitor(final Map<String, Double> variables, final Map<String, Function> userFunctions) {
        this.variables = variables;
        this.userFunctions = userFunctions;
    }

    @Override
    public Double visitTerm(final Exp4jGrammarParser.TermContext term) {
        if (term.decimal() != null) {
            return this.parseNumber(term.decimal());
        } else if (term.unary_prefix() != null) {
            return this.evaluateUnaryPrefix(term);
        } else if (term.unary_suffix() != null) {
            return this.evaluateUnarySuffix(term);
        } else if (term.function() != null) {
            return this.evaluateFunction(term);
        } else if (term.constant() != null) {
            return this.evaluateConstant(term);
        } else if (term.variable() != null) {
            return this.evaluateVariable(term);
        } else {
            return this.evaluateOperation(term);
        }
    }

    private Double evaluateVariable(Exp4jGrammarParser.TermContext term) {
        final Double val = variables.get(term.variable().NAME().getText());
        if (val == null) {
            throw new ExpressionSyntaxError(String.format("Unknown variable at pos %d: %s", term.getStart().getStartIndex(), term.getText()));
        }
        return val;
    }

    private Double evaluateConstant(Exp4jGrammarParser.TermContext term) {
        switch(term.constant().getText()) {
            case "pi":
            case "π":
                return Math.PI;
            case "e":
                return Math.E;
            case "phi":
            case "φ":
                return 1.61803398874d;
            default:
                throw new ExpressionSyntaxError(String.format("Invalid constant at pos %d: %s", term.getStart().getStartIndex(), term.getText()));
        }
    }

    private Double evaluateFunction(final Exp4jGrammarParser.TermContext term) {
        final String name = term.function().NAME().getText();
        Function func = this.userFunctions.get(name);
        if (func == null) {
            func = Functions.getFunction(term.function().NAME().getText());
        }
        if (func == null) {
            throw new ExpressionSyntaxError(String.format("Unknown function at pos %d: %s", term.getStart().getStartIndex(), term.getText()));
        }
        int len = term.function().term().size();
        final double[] args = new double[len];
        for (int i = 0;i < len; i++) {
            args[i] = this.visitTerm(term.function().term(i));
        }
        return func.apply(args);
    }

    private Double evaluateUnaryPrefix(final Exp4jGrammarParser.TermContext term) {
        final String operator = term.unary_prefix().getText();
        if (operator.equals("-")) {
            return - this.visitTerm(term.term(0));
        } else {
            return this.visitTerm(term.term(0));
        }
    }

    private Double evaluateUnarySuffix(final Exp4jGrammarParser.TermContext term) {
        final String operator = term.unary_suffix().getText();
        if (operator == "!") {
            return Factorial.factorial(this.visitTerm(term.term(0)));
        } else {
            throw new ExpressionSyntaxError(String.format("Invalid suffix operator in term: %s", term.getText()));
        }
    }

    private Double evaluateOperation(final Exp4jGrammarParser.TermContext term) {
        final String operator = term.multiplication() != null ? term.multiplication().getText() : term.addition().getText();
        final double left = this.visitTerm(term.term(0));
        final double right = this.visitTerm(term.term(1));
        switch (operator) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                return left / right;
            case "^":
                return Math.pow(left, right);
            case "%":
                return left % right;
            default:
                throw new ExpressionSyntaxError(String.format("Invalid operator when parsing term '%s'", term));
        }
    }

    private double parseNumber(final Exp4jGrammarParser.DecimalContext decimal) {
        return Double.parseDouble(decimal.getText());
    }
}
