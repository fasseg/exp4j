package net.objecthunter.exp4j.antlr;

import net.objecthunter.exp4j.ExpressionError;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Exp4jExpressionValidator extends Exp4jGrammarBaseVisitor<List<ExpressionError>> {

    private final List<ExpressionError> errors = new LinkedList<>();

    private final Map<String, Double> variables = new HashMap<>();

    @Override
    public List<ExpressionError> visit(final ParseTree tree) {
        errors.clear();
        super.visit(tree);
        return errors;
    }

    @Override
    public List<ExpressionError> visitVariable(final Exp4jGrammarParser.VariableContext ctx) {
        final String var = ctx.NAME().getText();
        if (!variables.containsKey(var)) {
            this.errors.add(new ExpressionError(String.format("Variable %s is not set", var), ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()));
        }
        super.visitVariable(ctx);
        return errors;
    }
}
