/* 
 * Copyright 2014 Frank Asseg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package net.objecthunter.exp4j.tokenizer;

import java.util.Map;
import java.util.Set;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;

public class Tokenizer {

    private final char[] expression;

    private final int expressionLength;

    private final Map<String, Function> userFunctions;

    private final Map<String, Operator> userOperators;

    private final Set<String> variableNames;

    private int pos = 0;

    private Token lastToken;
    
    private static final char ARGUMENT_SEPARATOR = ',';

    public Tokenizer(String expression, final Map<String, Function> userFunctions,
            final Map<String, Operator> userOperators, final Set<String> variableNames) {
        this.expression = expression.trim().toCharArray();
        this.expressionLength = this.expression.length;
        this.userFunctions = userFunctions;
        this.userOperators = userOperators;
        this.variableNames = variableNames;
    }

    public boolean hasNext() {
        return this.expression.length > pos;
    }

    public Token nextToken(){
        char ch = expression[pos];
        while (Character.isWhitespace(ch)) {
            ch = expression[++pos];
        }
        if (Character.isDigit(ch) || ch == '.') {
            if (lastToken != null) {
                if (lastToken.getType() == Token.TOKEN_NUMBER) {
                    throw new IllegalArgumentException("Unable to parse char '" + ch + "' (Code:" + (int) ch + ") at [" + pos + "]");
                } else if ((lastToken.getType() != Token.TOKEN_OPERATOR
                        && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
                        && lastToken.getType() != Token.TOKEN_FUNCTION
                        && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
                    // insert an implicit multiplication token
                    lastToken = new OperatorToken(Operators.getBuiltinOperator('*', 2));
                    return lastToken;
                }
            }
            return parseNumberToken(ch);
        } else if (isArgumentSeparator(ch)) {
            return parseArgumentSeparatorToken(ch);
        } else if (isOpenParentheses(ch)) {
            if (lastToken != null &&
                    (lastToken.getType() != Token.TOKEN_OPERATOR
                            && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
                            && lastToken.getType() != Token.TOKEN_FUNCTION
                            && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
                // insert an implicit multiplication token
                lastToken = new OperatorToken(Operators.getBuiltinOperator('*', 2));
                return lastToken;
            }
            return parseParentheses(true);
        } else if (isCloseParentheses(ch)) {
            return parseParentheses(false);
        } else if (Operator.isAllowedOperatorChar(ch)) {
            return parseOperatorToken(ch);
        } else if (isAlphabetic(ch) || ch == '_') {
            // parse the name which can be a setVariable or a function
            if (lastToken != null &&
                    (lastToken.getType() != Token.TOKEN_OPERATOR
                            && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
                            && lastToken.getType() != Token.TOKEN_FUNCTION
                            && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
                // insert an implicit multiplication token
                lastToken = new OperatorToken(Operators.getBuiltinOperator('*', 2));
                return lastToken;
            }
            return parseFunctionOrVariable();

        }
        throw new IllegalArgumentException("Unable to parse char '" + ch + "' (Code:" + (int) ch + ") at [" + pos + "]");
    }

    private Token parseArgumentSeparatorToken(char ch) {
        this.pos++;
        this.lastToken = new ArgumentSeparatorToken();
        return lastToken;
    }

    private boolean isArgumentSeparator(char ch) {
        return ch == ARGUMENT_SEPARATOR;
    }

    private Token parseParentheses(final boolean open) {
        this.lastToken = open ? new OpenParenthesesToken() : new CloseParenthesesToken();;
        this.pos++;
        return lastToken;
    }

    private boolean isOpenParentheses(char ch) {
        return ch == '(' || ch == '{' || ch == '[';
    }

    private boolean isCloseParentheses(char ch) {
        return ch == ')' || ch == '}' || ch == ']';
    }

    private Token parseFunctionOrVariable() {
        final int offset = this.pos;
        int lastValidLen = 1;
        Token lastValidToken = null;
        int len = 1;
        if (isEndOfExpression(offset)) {
            this.pos++;
        }
        while (!isEndOfExpression(offset + len - 1) &&
                (isAlphabetic(expression[offset + len - 1]) ||
                        Character.isDigit(expression[offset + len - 1]) ||
                        expression[offset + len - 1] == '_')) {
            String name = new String(expression, offset, len);
            if (variableNames != null && variableNames.contains(name)) {
                lastValidLen = len;
                lastValidToken = new VariableToken(name);
            } else {
                final Function f = getFunction(name);
                if (f != null) {
                    lastValidLen = len;
                    lastValidToken = new FunctionToken(f);
                }
            }
            len++;
        }
        if (lastValidToken == null) {
            throw new IllegalArgumentException("Unable to parse setVariable or function starting at pos " + pos + " in expression '" + new String(expression) + "'");
        }
        pos += lastValidLen;
        lastToken = lastValidToken;
        return lastToken;
    }

    private Function getFunction(String name) {
        Function f = null;
        if (this.userFunctions != null) {
            f = this.userFunctions.get(name);
        }
        if (f == null) {
            f = Functions.getBuiltinFunction(name);
        }
        return f;
    }

    private String parseName() {
        // parse the name of a function or a setVariable
        final int offset = this.pos;
        int len = 1;
        if (isEndOfExpression(offset)) {
            this.pos++;
        }
        while (!isEndOfExpression(offset + len) &&
                (isAlphabetic(expression[offset + len]) ||
                        Character.isDigit(expression[offset + len]) ||
                expression[offset + len] == '_')) {
            len++;
        }
        pos += len;
        return new String(expression, offset, len);
    }

    private Token parseOperatorToken(char firstChar) {
        final int offset = this.pos;
        int len = 1;
        final StringBuilder symbol = new StringBuilder();
        Operator lastValid = null;
        symbol.append(firstChar);

        while (!isEndOfExpression(offset + len)  && Operator.isAllowedOperatorChar(expression[offset + len])) {
            symbol.append(expression[offset + len++]);
        }

        while (symbol.length() > 0) {
            Operator op = this.getOperator(symbol.toString());
            if (op == null) {
                symbol.setLength(symbol.length() - 1);
            }else{
                lastValid = op;
                break;
            }
        }

        pos += symbol.length();
        lastToken = new OperatorToken(lastValid);
        return lastToken;
    }

    private Operator getOperator(String symbol) {
        Operator op = null;
        if (this.userOperators != null) {
            op = this.userOperators.get(symbol);
        }
        if (op == null && symbol.length() == 1) {
            final int argc =
                    (lastToken == null || lastToken.getType() == Token.TOKEN_OPERATOR || lastToken.getType() == Token.TOKEN_PARENTHESES_OPEN)
                            ? 1 : 2;
            op = Operators.getBuiltinOperator(symbol.charAt(0), argc);
        }
        return op;
    }

    private Token parseNumberToken(final char firstChar) {
        final int offset = this.pos;
        int len = 1;
        this.pos++;
        if (isEndOfExpression(offset + len)) {
            lastToken = new NumberToken(Double.parseDouble(String.valueOf(firstChar)));
            return lastToken;
        }
        while (!isEndOfExpression(offset + len) &&
                isNumeric(expression[offset + len], expression[offset + len - 1] == 'e' ||
                        expression[offset + len - 1] == 'E')) {
            len++;
            this.pos++;
        }
        // check if the e is at the end
        if (expression[offset + len - 1] == 'e' || expression[offset + len - 1] == 'E') {
            // since the e is at the end it's not part of the number and a rollback is necessary
            len--;
            pos--;
        }
        lastToken = new NumberToken(expression, offset, len);
        return lastToken;
    }

    private static boolean isNumeric(char ch, boolean lastCharE) {
        return Character.isDigit(ch) || ch == '.' || ch == 'e' || ch == 'E' ||
                (lastCharE && (ch == '-' || ch == '+'));
    }

    public static boolean isAlphabetic(int codePoint) {
        return ((codePoint > 64 && codePoint < 91) ||
                (codePoint > 96 && codePoint < 123));
    }

    private boolean isEndOfExpression(int offset) {
        return this.expressionLength <= offset;
    }
}
