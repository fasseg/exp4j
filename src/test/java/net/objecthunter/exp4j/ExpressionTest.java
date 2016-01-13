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

import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.Operators;
import net.objecthunter.exp4j.tokenizer.FunctionToken;
import net.objecthunter.exp4j.tokenizer.NumberToken;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.Token;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ExpressionTest {
    @Test
    public void testExpression1() throws Exception{
        Token[] tokens = new Token[] {
            new NumberToken(3d),
            new NumberToken(2d),
            new OperatorToken(Operators.getBuiltinOperator('+', 2))
        };
        Expression exp = new Expression(tokens);
        assertEquals(5d, exp.evaluate(), 0d);
    }

    @Test
    public void testExpression2() throws Exception{
        Token[] tokens = new Token[] {
                new NumberToken(1d),
                new FunctionToken(Functions.getBuiltinFunction("log")),
        };
        Expression exp = new Expression(tokens);
        assertEquals(0d, exp.evaluate(), 0d);
    }

    @Test
    public void testClearVariables() {
        ExpressionBuilder builder = new ExpressionBuilder("a + b");
        builder.variable("a");
        builder.variable("b");

        Expression expression = builder.build();
        Map<String, Double> values = new HashMap<String, Double>();
        values.put("a", 1.0);
        values.put("b", 2.0);
        expression.setVariables(values);


        double result = expression.evaluate();
        assertEquals(3.0, result, 3.0-result);

        expression.clearVariables();
        try{
                result = expression.evaluate();
                assertEquals("should have failed here because there shouldn't be any values in the expression.", false, true);
        }catch(Exception e){

        }
        Map<String, Double> emptyMap = new HashMap<String, Double>();
        expression.setVariables(emptyMap);
        try{
                result = expression.evaluate();
                assertEquals("should have failed here because there shouldn't be any values in the expression.", false, true);
        }catch(Exception e){

        }

    }


    
    @Test
    @Ignore
    // If Expression should be threads safe this test must pass
    public void evaluateFamily() throws Exception {
        final Expression e = new ExpressionBuilder("sin(x)")
                .variable("x")
                .build();
        Executor executor = Executors.newFixedThreadPool(100);
        for (int i = 0 ; i < 100000; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    double x = Math.random();
                    e.setVariable("x", x);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    assertEquals(Math.sin(x), e.evaluate(), 0f);
                }
            });
        }
    }
}
