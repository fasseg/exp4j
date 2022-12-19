package net.objecthunter.exp4j;

public class ExpressionError {
    private final String message;
    private final int line;
    private final int col;

    public ExpressionError(final String message, final int line, final int col) {
        this.message = message;
        this.line = line;
        this.col = col;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public String getMessage() {
        return message;
    }
}
