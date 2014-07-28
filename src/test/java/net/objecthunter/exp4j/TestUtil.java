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

package net.objecthunter.exp4j;

import static org.junit.Assert.assertEquals;

import net.objecthunter.exp4j.tokenizer.*;

import org.junit.Assert;

public abstract class TestUtil {

    public static void assertVariableToken(Token token, String name) {
        assertEquals(Token.TOKEN_VARIABLE, token.getType());
        Assert.assertEquals(name, ((VariableToken) token).getName());
    }

    public static void assertOpenParenthesesToken(Token token) {
        assertEquals(Token.TOKEN_PARENTHESES_OPEN, token.getType());
    }

    public static void assertCloseParenthesesToken(Token token) {
        assertEquals(Token.TOKEN_PARENTHESES_CLOSE, token.getType());
    }

    public static void assertFunctionToken(Token token, String name, int i) {
        assertEquals(token.getType(), Token.TOKEN_FUNCTION);
        FunctionToken f = (FunctionToken) token;
        assertEquals(i, f.getFunction().getNumArguments());
        assertEquals(name, f.getFunction().getName());
    }

    public static void assertOperatorToken(Token tok, String symbol, int numArgs, int precedence) {
        assertEquals(tok.getType(), Token.TOKEN_OPERATOR);
        Assert.assertEquals(numArgs, ((OperatorToken) tok).getOperator().getNumOperands());
        assertEquals(symbol, ((OperatorToken) tok).getOperator().getSymbol());
        assertEquals(precedence, ((OperatorToken) tok).getOperator().getPrecedence());
    }

    public static void assertNumberToken(Token tok, double v) {
        assertEquals(tok.getType(), Token.TOKEN_NUMBER);
        Assert.assertEquals(v, ((NumberToken) tok).getValue(), 0d);
    }

    public static void assertFunctionSeparatorToken(Token t) {
        assertEquals(t.getType(), Token.TOKEN_SEPARATOR);
    }
}
