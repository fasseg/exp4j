package net.objecthunter.exp4j;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.LinkedList;
import java.util.List;

public class ValidationErrorListener extends BaseErrorListener {

    final List<ExpressionError> errors = new LinkedList<>();

    @Override
    public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line, final int charPositionInLine, final String msg, final RecognitionException e) {
        this.errors.add(new ExpressionError(msg, line, charPositionInLine));
    }

    public void addError(final String msg, final int line, final int col) {
        this.errors.add(new ExpressionError(msg, line, col));
    }

    public List<ExpressionError> getErrors() {
        return errors;
    }

    public boolean hasError() {
        return this.errors.size() < 1;
    }
}
