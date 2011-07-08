/*
   Copyright 2011 frank asseg

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package de.congrace.exp4j;

import org.junit.Test;
import static org.junit.Assert.*;

import de.congrace.exp4j.OperatorToken.Operation;

public class OperatorTest {
    @Test
    public void testOperatorPrecedence(){
        OperatorToken add=new OperatorToken("+", Operation.ADDITION);
        OperatorToken mult=new OperatorToken("*",Operation.MULTIPLICATION);
        OperatorToken sub=new OperatorToken("-",Operation.SUBTRACTION);
        OperatorToken div=new OperatorToken("/",Operation.DIVISION);
        OperatorToken exp=new OperatorToken("^",Operation.EXPONENTIATION);
        OperatorToken una=new OperatorToken("#",Operation.UNARY_MINUS);
        
        assertTrue(add.isLeftAssociative());
        assertTrue(mult.isLeftAssociative());
        assertTrue(sub.isLeftAssociative());
        assertTrue(div.isLeftAssociative());
        assertTrue(!exp.isLeftAssociative());
        assertTrue(!una.isLeftAssociative());
        
        assertTrue(add.getPrecedence() == sub.getPrecedence());
        assertTrue(add.getPrecedence() < mult.getPrecedence());
        assertTrue(add.getPrecedence() < div.getPrecedence());
        assertTrue(add.getPrecedence() < exp.getPrecedence());
        assertTrue(add.getPrecedence() < una.getPrecedence());

        assertTrue(sub.getPrecedence() == add.getPrecedence());
        assertTrue(sub.getPrecedence() < mult.getPrecedence());
        assertTrue(sub.getPrecedence() < div.getPrecedence());
        assertTrue(sub.getPrecedence() < exp.getPrecedence());
        assertTrue(sub.getPrecedence() < una.getPrecedence());

        assertTrue(mult.getPrecedence() > add.getPrecedence());
        assertTrue(mult.getPrecedence() > sub.getPrecedence());
        assertTrue(mult.getPrecedence() == div.getPrecedence());
        assertTrue(mult.getPrecedence() < exp.getPrecedence());
        assertTrue(mult.getPrecedence() < una.getPrecedence());
        
        assertTrue(div.getPrecedence() > add.getPrecedence());
        assertTrue(div.getPrecedence() > sub.getPrecedence());
        assertTrue(div.getPrecedence() == mult.getPrecedence());
        assertTrue(div.getPrecedence() < exp.getPrecedence());
        assertTrue(div.getPrecedence() < una.getPrecedence());
        
        assertTrue(exp.getPrecedence() > add.getPrecedence());
        assertTrue(exp.getPrecedence() > sub.getPrecedence());
        assertTrue(exp.getPrecedence() > mult.getPrecedence());
        assertTrue(exp.getPrecedence() > div.getPrecedence());
        assertTrue(exp.getPrecedence() < una.getPrecedence());

        assertTrue(una.getPrecedence() > add.getPrecedence());
        assertTrue(una.getPrecedence() > sub.getPrecedence());
        assertTrue(una.getPrecedence() > mult.getPrecedence());
        assertTrue(una.getPrecedence() > div.getPrecedence());
        assertTrue(una.getPrecedence() > exp.getPrecedence());
    }
}
