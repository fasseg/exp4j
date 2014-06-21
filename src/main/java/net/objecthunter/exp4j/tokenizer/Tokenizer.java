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

public class Tokenizer {
    private final char[] expression;
    private final int len;
    private int pos = 0;

    public Tokenizer(char[] expression) {
        this.expression = expression;
        this.len = expression.length;
    }

    public Tokenizer(String expression) {
        this(expression.toCharArray());
    }

    public Token nextToken() throws TokenizerException {
        char ch = expression[pos];
        if (Character.isDigit(ch) || ch == '.') {
            pos++;
            return parseNumber(ch);
        }
        throw new TokenizerException("Unable to parse char " + ch + " [" + pos + "]");
    }

    private NumberToken parseNumber(char ch) {
        final StringBuilder value = new StringBuilder(8);
        value.append(ch);
        while (len > pos) {
            if (Character.isDigit(ch) || ch == '.') {
                value.append(ch);
                ch = expression[++pos];
            }else {
                break;
            }
        }
        return new NumberToken(Double.parseDouble(value.toString()));
    }
}
