package net.objecthunter.exp4j;

import java.util.Map;
import java.util.Set;

class Tokenizer {
    private final char[] expression;

    private final Set<String> declaredVariables;

    private final Map<String, Function> customFunctions;

    private final Map<String, Operator> customOperators;

    private final int len;

    private Token lastToken = null;

    private int offset = 0;

    Tokenizer(final String expression, final Set<String> declaredVariables, final Map<String, Function> customFunctions,
              final Map<String, Operator> customOperators) {
        this.expression = expression.trim().toCharArray();
        this.len = this.expression.length;
        this.declaredVariables = declaredVariables;
        this.customFunctions = customFunctions;
        this.customOperators = customOperators;
    }

    Token next() {
        // skip all spaces, since they can be ignored
        int spaces = 0;

        while (offset + spaces < this.len && Character.isWhitespace(expression[offset + spaces])) {
            spaces++;
        }

        final int tokenStart = offset + spaces;

        // The end has been reached, so we return a null value
        if (tokenStart >= len) {
            return null;
        }

        if (isNumericalTokenStart(this.expression[tokenStart])) {
            return this.parseNumericalToken(tokenStart);
        } else if (this.isOperatorStart(this.expression[tokenStart])) {
            return this.parseOperatorToken(tokenStart);
        } else if (this.isParentheses(this.expression[tokenStart])) {
            return this.parseParanthesesToken(tokenStart);
        } else if (this.isFunctionOrVariableChar(this.expression[tokenStart])) {
            final String name = this.parseFunctionOrVariableName(tokenStart);
            final Function candidate = this.getFunction(name);
            if (candidate != null) {
                return new FunctionToken(candidate);
            }
            if (this.declaredVariables.contains(name)) {
                return new VariableToken(name);
            }
            throw new TokenizerException("Unable parse to symbol '" + name + "' at position " + tokenStart);
        } else {
            throw new TokenizerException("Unable to parse token starting with '" + this.expression[tokenStart] + "' at position " + tokenStart);
        }

    }

    private Function getFunction(final String name) {
        Function func = this.customFunctions.get(name);
        if (func == null) {
            func = Functions.getBuiltinFunction(name);
        }
        return func;
    }

    private String parseFunctionOrVariableName(final int tokenStart) {
        final StringBuilder data = new StringBuilder();
        int current = 0;
        do {
            data.append(this.expression[tokenStart + current]);
            current++;
        } while (tokenStart + current < this.len && this.isFunctionOrVariableChar(this.expression[tokenStart + current]));
        this.offset = tokenStart + current;
        return data.toString();
    }

    private boolean isFunctionOrVariableChar(final char ch) {
        return Functions.isFunctionChar(ch);
    }

    private Token parseParanthesesToken(final int tokenStart) {
        final char ch = this.expression[tokenStart];
        final ParanthesesToken token;
        if (ch == '(' || ch == '[' || ch == '{') {
            token = new ParanthesesToken(true);
        } else {
            token = new ParanthesesToken(false);
        }
        this.offset++;
        return token;
    }

    private boolean isParentheses(final char c) {
        return c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}';
    }

    private OperatorToken parseOperatorToken(final int tokenStart) {
        final StringBuilder data = new StringBuilder();
        int current = 0;
        do {
            data.append(this.expression[tokenStart + current]);
            current++;
        } while (tokenStart + current < this.len && Operators.isOperatorChar(this.expression[tokenStart + current]));
        this.offset = tokenStart + current;
        final String symbol = data.toString();
        Operator operator = this.customOperators.get(symbol);
        if (operator == null) {
            operator = Operators.getBuiltinOperator(symbol);
        }
        return new OperatorToken(operator);
    }

    private boolean isOperatorStart(final char ch) {
        return Operators.isOperatorChar(ch);
    }

    private NumericalToken parseNumericalToken(final int tokenStart) {
        final StringBuilder data = new StringBuilder();
        int current = 0;
        do {
            data.append(this.expression[tokenStart + current]);
            current++;
        } while (tokenStart + current < this.len && this.isNumericalTokenChar(this.expression[tokenStart + current]));
        this.offset = tokenStart + current;
        return new NumericalToken(Double.parseDouble(data.toString()));
    }

    private boolean isNumericalTokenChar(final char ch) {
        return (ch > 47 && ch < 58) || ch == 46;
    }

    private boolean isNumericalTokenStart(final char ch) {
        return ch > 47 && ch < 58;
    }

}
