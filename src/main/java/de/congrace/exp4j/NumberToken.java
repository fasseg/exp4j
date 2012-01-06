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

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;
import java.util.Stack;

/**
 * A {@link Token} for Numbers
 * 
 * @author fas@congrace.de
 * 
 */
class NumberToken extends CalculationToken {

	private final double doubleValue;

	/**
	 * construct a new {@link NumberToken}
	 * 
	 * @param value
	 *            the value of the number as a {@link String}
	 * @throws ParseException 
	 */
    NumberToken(String value) {
        super(value);
        this.doubleValue = Double.parseDouble(value);
    }

    NumberToken(double value) {
        super(value+"");
        this.doubleValue = value;
    }
    
	NumberToken(String value, NumberFormat nf) throws ParseException {
		super(value);
		this.doubleValue = nf.parse(value).doubleValue();
	}
	
	@Override
	public String toString() {
	    return "{NumberToken value:" +getValue() +" doubleValue:"+doubleValue+"}";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NumberToken) {
			final NumberToken t = (NumberToken) obj;
			return t.doubleValue == this.doubleValue;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

	@Override
	void mutateStackForCalculation(Stack<Double> stack, Map<String, Double> variableValues) {
		stack.push(this.doubleValue);
	}

	@Override
	void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output) {
		output.append(this.getValue()).append(' ');
	}
}
