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

import net.objecthunter.exp4j.function.*;
import net.objecthunter.exp4j.operator.*;
import net.objecthunter.exp4j.tokenizer.*;

public class Expression {

    private final Token[] tokens;

    private final Map<String, Double> variables;

    private final Set<String> userFunctionNames;

    private static Map<String, Double> createDefaultVariables() {
        final Map<String, Double> vars = new HashMap<String, Double>(4);
        vars.put("pi", Math.PI);
        vars.put("π", Math.PI);
        vars.put("φ", 1.61803398874d);
        vars.put("e", Math.E);
        return vars;
    }
    
    /**
     * Creates a new expression that is a copy of the existing one.
     * 
     * @param existing the expression to copy
     */
    public Expression(final Expression existing) {
    	this.tokens = Arrays.copyOf(existing.tokens, existing.tokens.length);
    	this.variables = new HashMap<String,Double>();
    	this.variables.putAll(existing.variables);
    	this.userFunctionNames = new HashSet<String>(existing.userFunctionNames);
    }

    Expression(final Token[] tokens) {
        this.tokens = tokens;
        this.variables = createDefaultVariables();
        this.userFunctionNames = Collections.<String>emptySet();
    }

    Expression(final Token[] tokens, Set<String> userFunctionNames) {
        this.tokens = tokens;
        this.variables = createDefaultVariables();
        this.userFunctionNames = userFunctionNames;
    }

    public Expression setVariable(final String name, final double value) {
        this.checkVariableName(name);
        this.variables.put(name, value);
        return this;
    }

    private void checkVariableName(String name) {
        if (this.userFunctionNames.contains(name) || Functions.getBuiltinFunction(name) != null) {
            throw new IllegalArgumentException("The variable name '" + name + "' is invalid. Since there exists a function with the same name");
        }
    }

    public Expression setVariables(Map<String, Double> variables) {
        for (Map.Entry<String, Double> v : variables.entrySet()) {
            this.setVariable(v.getKey(), v.getValue());
        }
        return this;
    }

    public ValidationResult validate(boolean checkVariablesSet) {
        final List<String> errors = new ArrayList<String>(0);
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
                    final int argsNum = func.getNumArguments(); 
                    if (argsNum > count) {
                        errors.add("Not enough arguments for '" + func.getName() + "'");
                    }
                    if (argsNum > 1) {
                        count -= argsNum - 1;
                    }
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
        final ArrayStack output = new ArrayStack();
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
                    throw new IllegalArgumentException("Invalid number of operands available for '" + op.getOperator().getSymbol() + "' operator");
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
                if (output.size() < func.getFunction().getNumArguments()) {
                    throw new IllegalArgumentException("Invalid number of arguments available for '" + func.getFunction().getName() + "' function");
                }
                /* collect the arguments from the stack */
                double[] args = new double[func.getFunction().getNumArguments()];
                for (int j = 0; j < func.getFunction().getNumArguments(); j++) {
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
    
    
    public String toString() {
    	return toString(new ArrayList<Token>(Arrays.asList(tokens)));
	}
    
    private String toString(List<Token> tokens) {
    	if(tokens.size() == 0)
    		return "";
    	Token token = tokens.get(tokens.size()-1);
    	
    	switch (token.getType()) {
			case Token.TOKEN_OPERATOR:
				Operator operator = ((OperatorToken) token).getOperator();
				List<List<Token>> args = getTokensArguments(tokens.subList(0, tokens.size()-1), operator.getNumOperands());
				List<Token> leftTokens = args.get(0),
							reightTokens = args.get(1);
				if(operator.getNumOperands() == 1 && !operator.isLeftAssociative()) {
					leftTokens = args.get(1);
					reightTokens = args.get(0);
				}
				
				boolean	parenthesis_left = leftTokens.size() > 1 && leftTokens.get(leftTokens.size()-1).getType() != Token.TOKEN_FUNCTION,
						parenthesis_right = reightTokens.size() > 1 && reightTokens.get(reightTokens.size()-1).getType() != Token.TOKEN_FUNCTION;
				if(parenthesis_left && leftTokens.get(leftTokens.size()-1).getType() == Token.TOKEN_OPERATOR) {
					parenthesis_left = operator.getPrecedence() > ((OperatorToken) leftTokens.get(leftTokens.size()-1)).getOperator().getPrecedence() 
							|| ((OperatorToken) leftTokens.get(leftTokens.size()-1)).getOperator().getNumOperands() == 1;
				}
				if(parenthesis_right  && reightTokens.get(reightTokens.size()-1).getType() == Token.TOKEN_OPERATOR) {
					parenthesis_right = operator.getPrecedence() > ((OperatorToken) reightTokens.get(reightTokens.size()-1)).getOperator().getPrecedence()
							|| ((OperatorToken) reightTokens.get(reightTokens.size()-1)).getOperator().getNumOperands() == 1;
				}
				
				if(!parenthesis_left && leftTokens.size() == 1 && leftTokens.get(0).getType() == Token.TOKEN_NUMBER) {
					parenthesis_left = ((NumberToken) leftTokens.get(0)).getValue() < 0;
				}
				if(!parenthesis_right && reightTokens.size() == 1 && reightTokens.get(0).getType() == Token.TOKEN_NUMBER) {
					parenthesis_right = ((NumberToken) reightTokens.get(0)).getValue() < 0;
				}
						
				return (parenthesis_left?"(":"")+toString(leftTokens)+(parenthesis_left?")":"")+operator.getSymbol()+(parenthesis_right?"(":"")+toString(reightTokens)+(parenthesis_right?")":"");
			
			case Token.TOKEN_FUNCTION:
				return ((FunctionToken) token).getFunction().getName()+"("+toString(tokens.subList(0, tokens.size()-1))+")";
				
			case Token.TOKEN_VARIABLE:
				return ((VariableToken) token).getName();
				
			case Token.TOKEN_NUMBER:
				return ""+((NumberToken) token).getValue();
				
			default:
				throw new UnsupportedOperationException("The token type '"+token.getClass().getName()+"' is not supported in this function yet");
		}
    }
    
    
    private List<List<Token>> getTokensArguments(List<Token> tokens, int numOperands) {
    	List<List<Token>> tArgs = new ArrayList<List<Token>>(2);
    	if(numOperands == 1) {
    		tArgs.add(tokens);
	        tArgs.add(new ArrayList<Token>(0));
    	}
    	else {
	    	int last = 0;
			final ArrayStack output = new ArrayStack();
	        for (int i = 0; i < tokens.size()-1; i++) {
	            Token t = tokens.get(i);
	            switch (t.getType()) {
		            case Token.TOKEN_NUMBER:
		                output.push(((NumberToken) t).getValue());
		                break;
		                
		            case Token.TOKEN_VARIABLE:
		                output.push(1d);
		                break;
		                
		            case Token.TOKEN_OPERATOR:
		                Operator operator = ((OperatorToken) t).getOperator();
		                if (operator.getNumOperands() == 2) 
		                    output.push(operator.apply(output.pop(), output.pop()));
		                else if (operator.getNumOperands() == 1) 
		                    output.push(operator.apply(output.pop()));
		                break;
		                
		            case Token.TOKEN_FUNCTION:
		                FunctionToken func = (FunctionToken) t;
		                double[] args = new double[func.getFunction().getNumArguments()];
		                for (int j = 0; j < func.getFunction().getNumArguments(); j++) {
		                    args[j] = output.pop();
		                }
		                output.push(func.getFunction().apply(this.reverseInPlace(args)));
		                break;
		        }
	            if(output.size() == 1) {
	            	last = i;
	            }
	        }
	        
	        tArgs.add(tokens.subList(0, last+1));
	        tArgs.add(tokens.subList(last+1, tokens.size()));
    	}
    	
    	return tArgs;
	}
    
}