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

import net.objecthunter.exp4j.operator.Operators;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.VariableToken;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class VariablesTest {

    @Test
    public void testExpression1() throws Exception{
        Token[] tokens = new Token[] {
            new VariableToken("a"),
            new VariableToken("b"),
            new OperatorToken(Operators.getBuiltinOperator('+', 2))
        };
        Expression exp = new Expression(tokens);

        assertEquals(2, exp.getVariableTokenNames().size());
    }

}
