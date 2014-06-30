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

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;

public class Tokenizer {
    private final char[] expression;
    private final int expressionLength;
    private int pos = 0;
    private Token lastToken;

    public Tokenizer(char[] expression) {
        this.expression = expression;
        this.expressionLength = expression.length;
    }

    public boolean hasNext() {
        return this.expression.length > pos;
    }

    public Tokenizer(String expression) {
        this(expression.toCharArray());
    }

    public Token nextToken() throws TokenizerException {
        char ch = expression[pos];
        if (isNumeric(ch)) {
            return parseNumberToken(ch);
        } else if (isOpenParantheses(ch)) {
            return parseParantheses(true);
        } else if (isCloseParantheses(ch)) {
            return parseParantheses(false);
        } else if (Operator.isAllowedOperatorChar(ch)) {
            return parseOperatorToken(ch);
        } else if (Character.isAlphabetic(ch) || ch == '_') {
            // parse the name which can be a variable or a function
            return parseFunctionOrVariable();

        }
        throw new TokenizerException("Unable to parse char " + ch + " [" + pos + "]");
    }

    private Token parseParantheses(final boolean open) {
        if (open) {
            this.lastToken = ParanthesesToken.openParantheses();
        } else {
            this.lastToken = ParanthesesToken.closeParantheses();
        }
        this.pos++;
        return lastToken;
    }

    private boolean isOpenParantheses(char ch) {
        return ch == '(' || ch == '{' || ch == '[';
    }

    private boolean isCloseParantheses(char ch) {
        return ch == ')' || ch == '}' || ch == ']';
    }

    private Token parseFunctionOrVariable() {
        final String name = parseName();
        final Function f = Functions.getBuiltinFunction(name);
        if (f != null) {
            this.lastToken = new FunctionToken(f);
            return lastToken;
        } else {
            // TODO return variable
            this.lastToken = new VariableToken(name);
            return lastToken;
        }
    }

    private String parseName() {
        // parse the name of a function or a variable
        final int offset = this.pos;
        int len = 1;
        if (isEndOfExpression(offset)) {
            this.pos++;
        }
        while (!isEndOfExpression(offset + len) && Character.isAlphabetic(expression[offset + len])) {
            len++;
        }
        pos += len;
        return new String(expression, offset, len);
    }

    private Token parseOperatorToken(char firstChar) {
        final int offset = this.pos;
        int len = 1;
        Operator lastValid = getOperator(firstChar);

        if (isEndOfExpression(offset)) {
            this.pos++;
            lastToken = new OperatorToken(getOperator(firstChar));
            return lastToken;
        }

        while (!isEndOfExpression(offset + len) && Operator.isAllowedOperatorChar(expression[offset + len])) {
            final Operator tmp = getOperator(expression, offset, len + 1);
            if (tmp == null) {
                break;
            } else {
                lastValid = tmp;
                len++;
            }
        }

        pos += len;
        lastToken = new OperatorToken(lastValid);
        return lastToken;
    }

    private Operator getOperator(char[] expression, int offset, int len) {
        Operator op = null;
        final int argc = (lastToken == null || lastToken.getType() == Token.TOKEN_OPERATOR) ? 1 : 2;
        if (len == 1) {
            op = Operators.getBuiltinOperator(expression[offset], argc);
        }
        return op;
    }

    private Operator getOperator(char firstChar) {
        final int argc = (lastToken == null || lastToken.getType() == Token.TOKEN_OPERATOR) ? 1 : 2;
        Operator op = Operators.getBuiltinOperator(firstChar, argc);
        if (op != null) {
            return op;
        }
        return null;
    }

    private Token parseNumberToken(final char firstChar) {
        final int offset = this.pos;
        int len = 1;
        this.pos++;
        if (isEndOfExpression(offset + len)) {
            lastToken = new NumberToken(Double.parseDouble(String.valueOf(firstChar)));
            return lastToken;
        }
        while (!isEndOfExpression(offset + len) && isNumeric(expression[offset + len])) {
            len++;
            this.pos++;
        }
        lastToken = new NumberToken(expression, offset, len);
        return lastToken;
    }

    private boolean isNumeric(char ch) {
        return Character.isDigit(ch) || ch == '.' || ch == 'e';
    }

    private boolean isEndOfExpression(int offset) {
        return this.expressionLength <= offset;
    }
}
