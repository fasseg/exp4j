package net.objecthunter.exp4j;

public class Tokenizer {
    private final char[] expression;

    private final int len;

    private Token lastToken = null;

    private int offset = 0;

    public Tokenizer(final String expression) {
        this.expression = expression.trim().toCharArray();
        this.len = this.expression.length;
    }

    public Token next() {
        // skip all spaces, since they can be ignored
        int spaces = 0;

        while (offset + spaces < this.len && Character.isWhitespace(expression[offset + spaces])) {
            spaces++;
        }

        final int tokenStart = offset + spaces;

        // The end has been reached, so we return a null value
        if (tokenStart == len) {
            return null;
        }

        if (isNumericalTokenStart(this.expression[tokenStart])) {
            return this.parseNumericalToken(tokenStart);
        } else if (this.isOperatorStart(this.expression[tokenStart])) {
            return this.parseOperatorToken(tokenStart);
        } else {
            throw new TokenizerException("Unable to parse token starting with '" + this.expression[tokenStart] + "' at position " + tokenStart);
        }

    }

    private OperatorToken parseOperatorToken(final int tokenStart) {
        final StringBuilder data = new StringBuilder();
        int current = 0;
        do {
            data.append(this.expression[tokenStart + current++]);
        } while (offset + current < this.len && Operators.isOperatorChar(this.expression[tokenStart + current]));
        this.offset += current;
        return new OperatorToken(Operators.getBuiltinOperator(data.toString()));
    }

    private boolean isOperatorStart(final char ch) {
        return Operators.isOperatorChar(ch);
    }

    private NumericalToken parseNumericalToken(final int tokenStart) {
        final StringBuilder data = new StringBuilder();
        int current = 0;
        do {
            data.append(this.expression[tokenStart + current++]);
        } while (offset + current < this.len && this.isNumericalTokenChar(this.expression[tokenStart + current]));
        this.offset += current;
        return new NumericalToken(Double.parseDouble(data.toString()));
    }

    private boolean isNumericalTokenChar(final char ch) {
        return (ch >  47 && ch < 58) || ch == 46;
    }

    private boolean isNumericalTokenStart(final char ch) {
        return ch >  47 && ch < 58;
    }

}
