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

import java.util.*;
import java.util.concurrent.*;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokenizer.*;

public class Expression {

    private final Token[] tokens;

    private final Map<String, Double> variables;

    private final Set<String> userFunctionNames;


    Expression(final Token[] tokens) {
        this.tokens = tokens;
        this.variables = new HashMap<>(4);
        this.userFunctionNames = Collections.<String>emptySet();
    }

    Expression(final Token[] tokens, Set<String> userFunctionNames) {
        this.tokens = tokens;
        this.variables = new HashMap<>(4);
        this.userFunctionNames = userFunctionNames;
    }

    public Expression setVariable(final String name, final double value) {
        this.checkVariableName(name);
        this.variables.put(name, value);
        return this;
    }

    private void checkVariableName(String name) {
        if (this.userFunctionNames.contains(name)) {
            throw new IllegalArgumentException("The setVariable name '" + name + "' is invalid. Since there exists a function with the same name");
        }
    }

    public Expression setVariables(Map<String, Double> variables) {
        for (Map.Entry<String, Double> v : variables.entrySet()) {
            this.setVariable(v.getKey(), v.getValue());
        }
        return this;
    }

    public ValidationResult validate(boolean checkVariablesSet) {
        final List<String> errors = new ArrayList<>(0);
        if (checkVariablesSet) {
            /* check that all vars have a value set */
            for (final Token t : this.tokens) {
                if (t.getType() == Token.TOKEN_VARIABLE) {
                    final String var = ((VariableToken) t).getName();
                    if (!variables.containsKey(var)) {
                        errors.add("The setVariable '" + var + "' has not been set");
                    }
                }
            }
        }

        /* Check if the number of operands, functions and operators match.
           The idea is to increment a counter for operands and decrease it for operators.
           When a function occurs the number of available arguments has to be greater
           than or equals to the function's expected number of arguments.
           The count has to be larger than 1 at all times and exactly 1 after all tokens
           have been processed */
        int count = 0;
        for (Token tok : this.tokens) {
            switch (tok.getType()) {
                case Token.TOKEN_NUMBER:
                case Token.TOKEN_VARIABLE:
                    count++;
                    break;
                case Token.TOKEN_FUNCTION:
                    final Function func = ((FunctionToken) tok).getFunction();

                    if (func.getNumArguments() > count) {
                        errors.add("Not enough arguments for '" + func.getName() + "'");
                    }

                    int passedArguments = ((FunctionToken) tok).getPassedArgumentCount();
                    count -= passedArguments - 1;
                    break;
                case Token.TOKEN_OPERATOR:
                    Operator op = ((OperatorToken) tok).getOperator();
                    if (op.getNumOperands() == 2) {
                        count--;
                    }
                    break;
            }
            if (count < 1) {
                errors.add("Too many operators");
                return new ValidationResult(false, errors);
            }
        }
        if (count > 1) {
            errors.add("Too many operands");
        }
        return errors.size() == 0 ? ValidationResult.SUCCESS : new ValidationResult(false, errors);

    }

    public ValidationResult validate() {
        return validate(true);
    }

    public Future<Double> evaluateAsync(ExecutorService executor) {
        return executor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return evaluate();
            }
        });
    }

    public double evaluate() {
        final Stack<Double> output = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            Token t = tokens[i];
            if (t.getType() == Token.TOKEN_NUMBER) {
                output.push(((NumberToken) t).getValue());
            } else if (t.getType() == Token.TOKEN_VARIABLE) {
                final String name = ((VariableToken) t).getName();
                final Double value = this.variables.get(name);
                if (value == null) {
                    throw new IllegalArgumentException("No value has been set for the setVariable '" + name + "'.");
                }
                output.push(value);
            } else if (t.getType() == Token.TOKEN_OPERATOR) {
                OperatorToken op = (OperatorToken) t;
                if (output.size() < op.getOperator().getNumOperands()) {
                    throw new IllegalArgumentException("Invalid number of operands available");
                }
                if (op.getOperator().getNumOperands() == 2) {
                    /* pop the operands and push the result of the operation */
                    double rightArg = output.pop();
                    double leftArg = output.pop();
                    output.push(op.getOperator().apply(leftArg, rightArg));
                } else if (op.getOperator().getNumOperands() == 1) {
                    /* pop the operand and push the result of the operation */
                    double arg = output.pop();
                    output.push(op.getOperator().apply(arg));
                }
            } else if (t.getType() == Token.TOKEN_FUNCTION) {
                FunctionToken func = (FunctionToken) t;

                if (func.getFunction().getNumArguments() > func.getPassedArgumentCount()) {
                    throw new IllegalArgumentException("Function " + func.getFunction().getName() +
                            " requires at least " + func.getFunction().getNumArguments() +
                            " arguments (" + func.getPassedArgumentCount() + " passed)");
                }

                if (output.size() < func.getFunction().getNumArguments()) {
                    throw new IllegalArgumentException("Invalid number of arguments available");
                }

                /* collect the arguments from the stack */
                double[] args = new double[func.getPassedArgumentCount()];
                for (int j = 0; j < func.getPassedArgumentCount(); j++) {
                    args[j] = output.pop();
                }
                output.push(func.getFunction().apply(this.reverseInPlace(args)));
            }
        }
        if (output.size() > 1) {
            throw new IllegalArgumentException("Invalid number of items on the output queue. Might be caused by an invalid number of arguments for a function.");
        }
        return output.pop();
    }

    private double[] reverseInPlace(double[] args) {
        int len = args.length;
        for (int i = 0; i < len / 2; i++) {
            double tmp = args[i];
            args[i] = args[len - i - 1];
            args[len - i - 1] = tmp;
        }
        return args;
    }
}
