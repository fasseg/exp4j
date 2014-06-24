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
        }else  if (Operators.isAllowedOperatorChar(ch)) {
            return parseOperatorToken(ch);
        }
        throw new TokenizerException("Unable to parse char " + ch + " [" + pos + "]");
    }

    private Token parseOperatorToken(char firstChar) {
        final int offset = this.pos;
        int len = 1;
        this.pos++;
        if (isEndOfExpression(offset)) {
            return new OperatorToken(getOperator(firstChar));
        }
        while (!isEndOfExpression(offset + len) && Operators.isAllowedOperatorChar(expression[offset + len])) {
            len++;
            this.pos++;
        }
        return new OperatorToken(getOperator(expression, offset, len));
    }

    private Operator getOperator(char[] expression, int offset, int len) {
        Operator op = null;
        if (len == 1) {
            op = Operators.getBuiltinOperator(expression[offset], 2);
        }
        return op;
    }

    private Operator getOperator(char firstChar) {
        Operator op = Operators.getBuiltinOperator(firstChar, 2);
        if (op != null) {
            return op;
        }
        return null;
    }

    private NumberToken parseNumberToken(final char firstChar) {
        final int offset = this.pos;
        int len = 1;
        this.pos++;
        if (isEndOfExpression(offset)) {
            return new NumberToken(Double.parseDouble(String.valueOf(firstChar)));
        }
        while (!isEndOfExpression(offset + len) && isNumeric(expression[offset + len])) {
            len++;
            this.pos++;
        }
        return new NumberToken(expression, offset, len);
    }

    private boolean isNumeric(char ch) {
        return Character.isDigit(ch) || ch == '.' || ch =='e';
    }

    private boolean isEndOfExpression(int offset) {
        return this.expressionLength == offset;
    }
}
