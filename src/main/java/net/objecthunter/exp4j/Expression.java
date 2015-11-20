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
    
    
    /**
     * Convert the <code>Expression</code> back to string expression.
     * 
     * @return a string representation of the <code>Expression</code>.
     */
    public String toString() {
    	return toString(Arrays.asList(tokens));
	}
    
    private String toString(List<Token> tokens) {
    	if(tokens.size() == 0)
    		return "";
    	Token token = tokens.get(tokens.size()-1);
    	
    	switch (token.getType()) {
			case Token.TOKEN_OPERATOR:
				Operator operator = ((OperatorToken) token).getOperator();
				List<List<Token>> operands = getTokensArguments(tokens.subList(0, tokens.size()-1), operator.getNumOperands());
				List<Token> leftTokens,
							rightTokens;
				if(operator.getNumOperands() == 1) { 
					if(operator.isLeftAssociative()) {
						leftTokens = operands.get(0);
						rightTokens = new ArrayList<Token>();
					}
					else {
						leftTokens = new ArrayList<Token>();
						rightTokens = operands.get(0);
					}
				}
				else {
					leftTokens = operands.get(0);
					rightTokens = operands.get(1);
				}
				
				boolean	parentheses_left = leftTokens.size() > 1 && leftTokens.get(leftTokens.size()-1).getType() != Token.TOKEN_FUNCTION,
						parentheses_right = rightTokens.size() > 1 && rightTokens.get(rightTokens.size()-1).getType() != Token.TOKEN_FUNCTION;
				if(parentheses_left && leftTokens.get(leftTokens.size()-1).getType() == Token.TOKEN_OPERATOR) {
					Operator leftOperator = ((OperatorToken) leftTokens.get(leftTokens.size()-1)).getOperator();
					if(leftOperator.getNumOperands() == 1 && leftOperator.getSymbol().matches("\\+|-")) {
						parentheses_left = true;
					}
					else {
						if(leftOperator.getSymbol().matches("\\+|\\*")) {
							parentheses_left = operator.getPrecedence() > leftOperator.getPrecedence();
						}
						else {
							parentheses_left = operator.getPrecedence() >= leftOperator.getPrecedence();							
						}
					}
				}
				if(parentheses_right  && rightTokens.get(rightTokens.size()-1).getType() == Token.TOKEN_OPERATOR) {
					Operator rightOperator = ((OperatorToken) rightTokens.get(rightTokens.size()-1)).getOperator();
					if(rightOperator.getNumOperands() == 1 && rightOperator.getSymbol().matches("\\+|-")) {
						parentheses_right = true;
					}
					else {
						if(operator.getSymbol().matches("\\+|\\*") && rightOperator.getSymbol().matches("\\+|\\*")) {
							parentheses_right = operator.getPrecedence() > rightOperator.getPrecedence();
						}
						else {
							parentheses_right = operator.getPrecedence() >= rightOperator.getPrecedence();							
						}
					}
				}
				
				if(!parentheses_left && leftTokens.size() == 1 && leftTokens.get(0).getType() == Token.TOKEN_NUMBER) {
					parentheses_left = ((NumberToken) leftTokens.get(0)).getValue() < 0;
				}
				if(!parentheses_right && rightTokens.size() == 1 && rightTokens.get(0).getType() == Token.TOKEN_NUMBER) {
					parentheses_right = ((NumberToken) rightTokens.get(0)).getValue() < 0;
				}
						
				return (parentheses_left?"(":"")+toString(leftTokens)+(parentheses_left?")":"")+operator.getSymbol()+(parentheses_right?"(":"")+toString(rightTokens)+(parentheses_right?")":"");
			
			case Token.TOKEN_FUNCTION:
				Function function = ((FunctionToken) token).getFunction();
				
				String stringArgs = "";
				List<List<Token>> args = getTokensArguments(tokens.subList(0, tokens.size()-1), function.getNumArguments());
				for (List<Token> argument : args) {
					stringArgs += ", "+toString(argument);
				}
				stringArgs = stringArgs.substring(2);
				return function.getName()+"("+stringArgs+")";
				
			case Token.TOKEN_VARIABLE:
				return ((VariableToken) token).getName();
				
			case Token.TOKEN_NUMBER:
				return String.valueOf(((NumberToken) token).getValue());
				
			default:
				throw new UnsupportedOperationException("The token type '"+token.getClass().getName()+"' is not supported in this function yet");
		}
    }
    
    
    private List<List<Token>> getTokensArguments(List<Token> tokens, int numOperands) {
    	List<List<Token>> tArgs = new ArrayList<List<Token>>(2);
    	if(numOperands == 1) {
    		tArgs.add(tokens);
    	}
    	else {
	    	int size = 0;
	    	int[] pos = new int[numOperands-1];
	        for (int i = 0; i < tokens.size()-1; i++) {
	            Token t = tokens.get(i);
	            switch (t.getType()) {
		            case Token.TOKEN_NUMBER:
		                size++;
		                break;
		                
		            case Token.TOKEN_VARIABLE:
		                size++;
		                break;
		                
		            case Token.TOKEN_OPERATOR:
		                Operator operator = ((OperatorToken) t).getOperator();
		                if (operator.getNumOperands() == 2) 
		                    size --;
		                break;
		                
		            case Token.TOKEN_FUNCTION:
		                FunctionToken func = (FunctionToken) t;
		                for (int j = 0; j < func.getFunction().getNumArguments(); j++) {
		                    size--;
		                }
		                size++;
		                break;
		        }
	            for (int j = 0; j < pos.length; j++) {
					if(size == j+1) {
						pos[j] = i;
					}
				}
	        }
	        
	        tArgs.add(tokens.subList(0, pos[0]+1));
	        for (int i = 1; i < pos.length; i++) {
        		tArgs.add(tokens.subList(pos[i-1]+1, pos[i]+1));
			}
	        tArgs.add(tokens.subList(pos[pos.length-1]+1, tokens.size()));
    	}
    	
    	return tArgs;
	}
    
}