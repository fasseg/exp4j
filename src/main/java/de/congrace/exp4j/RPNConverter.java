package de.congrace.exp4j;

import java.util.Map;
import java.util.Stack;

abstract class RPNConverter {

    private static String substituteUnaryOperators(String expr, Map<Character, Operation> operators) {
        final StringBuilder exprBuilder = new StringBuilder(expr.length());
        final char[] data = expr.toCharArray();
        char lastChar = ' ';
        for (int i = 0; i < expr.length(); i++) {
            if (exprBuilder.length() > 0) {
                lastChar = exprBuilder.charAt(exprBuilder.length() - 1);
            }
            final char c = data[i];
            switch (c) {
            case '+':
                if (i > 0 && lastChar != '(' && !(operators.get(lastChar) != null)) {
                    exprBuilder.append(c);
                }
                break;
            case '-':
                if (i > 0 && lastChar != '(' && !(operators.get(lastChar) != null)) {
                    exprBuilder.append(c);
                } else {
                    exprBuilder.append('\'');
                }
                break;
            default:
                if (!Character.isWhitespace(c)) {
                    exprBuilder.append(c);
                }
            }
        }
        return exprBuilder.toString();
    }

    static RPNExpression toRPNExpression(String infix, Map<String, Double> variables, Map<String, CustomFunction> customFunctions,
            Map<Character, Operation> operators) throws UnknownFunctionException, UnparsableExpressionException {
        final Tokenizer tokenizer = new Tokenizer(variables.keySet(), customFunctions, operators);
        final StringBuilder output = new StringBuilder(infix.length());
        final Stack<Token> operatorStack = new Stack<Token>();
        for (final Token token : tokenizer.getTokens(substituteUnaryOperators(infix, operators))) {
            token.mutateStackForInfixTranslation(operatorStack, output);
        }
        // all tokens read, put the rest of the operations on the output;
        while (operatorStack.size() > 0) {
            output.append(operatorStack.pop().getValue()).append(" ");
        }
        String postfix = output.toString().trim();
        return new RPNExpression(tokenizer.getTokens(postfix), postfix, variables);
    }
}
