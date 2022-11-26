package net.objecthunter.exp4j;

public class ExpressionSyntaxError extends RuntimeException {
    public ExpressionSyntaxError(String msg) {
        super(msg);
    }

    public ExpressionSyntaxError(final int charPositionInLine, final String msg) {
        super(String.format("Unable to parse expression. Syntax error at position %d: %s", charPositionInLine, msg));
    }
}
