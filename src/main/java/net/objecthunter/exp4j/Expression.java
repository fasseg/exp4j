package net.objecthunter.exp4j;

import net.objecthunter.exp4j.antlr.Exp4jGrammarLexer;
import net.objecthunter.exp4j.antlr.Exp4jGrammarParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Expression {

    private final String expression;

    public Expression(final String expression) {
        this.expression = expression;
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
        final ExpressionVisitor visitor = new ExpressionVisitor();
        return visitor.visit(parseTree);
    }
}
