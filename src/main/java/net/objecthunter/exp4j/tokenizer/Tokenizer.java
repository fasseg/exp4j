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
        } else if (Operators.isAllowedOperatorChar(ch)) {
            return parseOperatorToken(ch);
        }
        throw new TokenizerException("Unable to parse char " + ch + " [" + pos + "]");
    }

    private Token parseOperatorToken(char firstChar) {
        final int offset = this.pos;
        int len = 1;

        this.pos++;

        if (isEndOfExpression(offset)) {
            lastToken = new OperatorToken(getOperator(firstChar));
            return lastToken;
        }

        while (!isEndOfExpression(offset + len) && Operators.isAllowedOperatorChar(expression[offset + len])) {
            len++;
            this.pos++;
        }

        lastToken = new OperatorToken(getOperator(expression, offset, len));
        return lastToken;
    }

    private Operator getOperator(char[] expression, int offset, int len) {
        Operator op = null;
        final int argc =  (lastToken == null || lastToken.getType() == Token.TOKEN_OPERATOR) ? 1 : 2;
        if (len == 1) {
            op = Operators.getBuiltinOperator(expression[offset], argc);
        }
        return op;
    }

    private Operator getOperator(char firstChar) {
        final int argc =  (lastToken == null || lastToken.getType() == Token.TOKEN_OPERATOR) ? 1 : 2;
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
        if (isEndOfExpression(offset)) {
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
        return this.expressionLength == offset;
    }
}
