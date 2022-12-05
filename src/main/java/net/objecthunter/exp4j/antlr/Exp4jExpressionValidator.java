package net.objecthunter.exp4j.antlr;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exp4jExpressionValidator extends Exp4jGrammarBaseVisitor<Void> {

    private final List<String> errors = new ArrayList<>();

    private final Map<String, Double> variables = new HashMap<>();

    @Override
    public Void visit(final ParseTree tree) {
        super.visit(tree);
        return null;
    }

    @Override
    public Void visitVariable(final Exp4jGrammarParser.VariableContext ctx) {
        final String var = ctx.NAME().getText();
        if (!variables.containsKey(var)) {
            errors.add(String.format("Variable %s is not set", var));
        }
        super.visitVariable(ctx);
        return null;
    }
}
