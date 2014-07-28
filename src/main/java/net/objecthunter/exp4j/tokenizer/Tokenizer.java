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

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;

public class Tokenizer {

    private final char[] expression;

    private final int expressionLength;

    private final Map<String, Function> userFunctions;

    private final Map<String, Operator> userOperators;

    private int pos = 0;

    private Token lastToken;

    public Tokenizer(String expression, final Map<String, Function> userFunctions,
            final Map<String, Operator> userOperators) {
        this.expression = expression.trim().toCharArray();
        this.expressionLength = this.expression.length;
        this.userFunctions = userFunctions;
        this.userOperators = userOperators;
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
            return parseNumberToken(ch);
        } else if (isArgumentSeparator(ch)) {
            return parseArgumentSeparatorToken(ch);
        } else if (isOpenParentheses(ch)) {
            if (lastToken != null &&
                    (lastToken.getType() != Token.TOKEN_OPERATOR && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN && lastToken.getType() != Token.TOKEN_FUNCTION && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
                // insert an implicit multiplication token
                lastToken = new OperatorToken(Operators.getBuiltinOperator('*', 2));
                return lastToken;
            }
            return parseParentheses(true);
        } else if (isCloseParentheses(ch)) {
            return parseParentheses(false);
        } else if (Operator.isAllowedOperatorChar(ch)) {
            return parseOperatorToken(ch);
        } else if (Character.isAlphabetic(ch) || ch == '_') {
            // parse the name which can be a variable or a function
            if (lastToken != null &&
                    (lastToken.getType() != Token.TOKEN_OPERATOR && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
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
        return ch == ',';
    }

    private Token parseParentheses(final boolean open) {
        if (open) {
            this.lastToken = new OpenParenthesesToken();
        } else {
            this.lastToken = new CloseParenthesesToken();
        }
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
        final String name = parseName();
        final Function f = getFunction(name);
        if (f != null) {
            this.lastToken = new FunctionToken(f);
            return lastToken;
        } else {
            // TODO return variable
            this.lastToken = new VariableToken(name);
            return lastToken;
        }
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
        // parse the name of a function or a variable
        final int offset = this.pos;
        int len = 1;
        if (isEndOfExpression(offset)) {
            this.pos++;
        }
        while (!isEndOfExpression(offset + len) &&
                (Character.isAlphabetic(expression[offset + len]) ||
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
        lastToken = new NumberToken(expression, offset, len);
        return lastToken;
    }

    private boolean isNumeric(char ch, boolean lastCharE) {
        return Character.isDigit(ch) || ch == '.' || ch == 'e' || ch == 'E' ||
                (lastCharE && (ch == '-' || ch == '+'));
    }

    private boolean isEndOfExpression(int offset) {
        return this.expressionLength <= offset;
    }
}
