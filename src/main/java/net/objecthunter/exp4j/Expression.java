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

import net.objecthunter.exp4j.tokenizer.*;

import java.util.*;

public class Expression {
    private final Token[] tokens;
    private final Map<String, Double> variables;

    Expression(final Token[] tokens) {
        this.tokens = tokens;
        this.variables = new HashMap<>();
    }

    Expression(final Token[] tokens, Map<String, Double> variables) {
        this.tokens = tokens;
        this.variables = variables;
    }

    public Expression variable(final String name, final double value) {
        this.variables.put(name, value);
        return this;
    }

    public Expression variables(Map<String, Double> variables) {
        this.variables.putAll(variables);
        return this;
    }


    public double evaluate() throws Exp4jException {
        final Stack<Double> output = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            Token t = tokens[i];
            if (t.getType() == Token.TOKEN_NUMBER) {
                output.push(((NumberToken) t).getValue());
            } else if (t.getType() == Token.TOKEN_VARIABLE) {
                output.push(this.variables.get(((VariableToken) t).getName()));
            } else if (t.getType() == Token.TOKEN_OPERATOR) {
                OperatorToken op = (OperatorToken) t;
                if (output.size() < op.getOperator().getNumArgs()) {
                    throw new RuntimeException("Invalid number of operands available");
                }
                if (op.getOperator().getNumArgs() == 2) {
                    /* pop the operands and push the result of the operation */
                    double rightArg = output.pop();
                    double leftArg = output.pop();
                    output.push(op.getOperator().apply(leftArg, rightArg));
                } else if (op.getOperator().getNumArgs() == 1) {
					/* pop the operand and push the result of the operation */
                    double arg = output.pop();
                    output.push(op.getOperator().apply(arg));
                }

            } else if (t.getType() == Token.TOKEN_FUNCTION) {
                FunctionToken func = (FunctionToken) t;
                if (output.size() < func.getFunction().getNumArguments()) {
                    throw new RuntimeException("Invalid number of arguments available");
                }
				/* collect the arguments from the stack */
                double[] args = new double[func.getFunction().getNumArguments()];
                for (int j = func.getFunction().getNumArguments() - 1; j >= 0; j--) {
                    args[j] = output.pop();
                }
                output.push(func.getFunction().apply(args));
            }
        }
        if (output.size() > 1) {
            throw new Exp4jException("Invalid number of items on the output queue. This should not happen");
        }
        return output.pop();
    }

    public ValidationResult validate() throws Exp4jException {
        final List<String> errors = new ArrayList<>();
        for (Token t : tokens) {
            if (t.getType() == Token.TOKEN_VARIABLE) {
                final String name = ((VariableToken) t).getName();
                if (!this.variables.containsKey(name)) {
                    errors.add("Variable '" + name + "' has not been set to a concrete value");
                }
            }
        }
        return new ValidationResult(errors.size() == 0, errors);
    }
}
