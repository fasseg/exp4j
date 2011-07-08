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

import java.util.Map;
import java.util.Stack;

/**
 * A {@link Token} for representing variables
 * @author fas
 */
public class VariableToken extends CalculationToken {
    /**
     * construct a new {@link VariableToken}
     * @param value the value of the token
     */
	public VariableToken(String value) {
        super(value);
    }

    @Override
    public void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output) {
        output.append(this.getValue()).append(" ");
    }

    @Override
    public void mutateStackForCalculation(Stack<Double> stack, Map<String, Double> variableValues) {
        double value = variableValues.get(this.getValue());
        stack.push(value);
    }
}
