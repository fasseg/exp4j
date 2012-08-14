package de.congrace.exp4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This is Builder implementation for the exp4j API used to create a Calculable
 * instance for the user
 * 
 * @author frank asseg
 * 
 */
public class ExpressionBuilder {

	/**
	 * Property name for unary precedence choice. You can set
	 * System.getProperty(PROPERTY_UNARY_HIGH_PRECEDENCE,"false") in order to
	 * change evaluation from an expression like "-3^2" from "(-3)^2" to
	 * "-(3^2)"
	 */
	public static final String PROPERTY_UNARY_HIGH_PRECEDENCE = "exp4j.unary.precedence.high";

	private final Map<String, Double> variables = new LinkedHashMap<String, Double>();

	private final Map<String, CustomFunction> customFunctions;

	private final Map<String, CustomOperator> builtInOperators;

	private Map<String, CustomOperator> customOperators = new HashMap<String, CustomOperator>();

	private final List<Character> validOperatorSymbols;

	private final boolean highUnaryPrecedence;

	private String expression;

	/**
	 * Create a new ExpressionBuilder
	 * 
	 * @param expression
	 *            the expression to evaluate
	 */
	public ExpressionBuilder(String expression) {
		this.expression = expression;
		highUnaryPrecedence = System
				.getProperty(PROPERTY_UNARY_HIGH_PRECEDENCE) == null
				|| !System.getProperty(PROPERTY_UNARY_HIGH_PRECEDENCE).equals(
						"false");
		customFunctions = getBuiltinFunctions();
		builtInOperators = getBuiltinOperators();
		validOperatorSymbols = getValidOperators();
	}

	private List<Character> getValidOperators() {
		return Arrays.asList('!', '#', '§', '$', '&', ';', ':', '~', '<', '>',
				'|', '=');
	}

	private Map<String, CustomOperator> getBuiltinOperators() {
		CustomOperator add = new CustomOperator("+") {
			@Override
			protected double applyOperation(double[] values) {
				return values[0] + values[1];
			}
		};
		CustomOperator sub = new CustomOperator("-") {
			@Override
			protected double applyOperation(double[] values) {
				return values[0] - values[1];
			}
		};
		CustomOperator div = new CustomOperator("/", 3) {
			@Override
			protected double applyOperation(double[] values) {
				return values[0] / values[1];
			}
		};
		CustomOperator mul = new CustomOperator("*", 3) {
			@Override
			protected double applyOperation(double[] values) {
				return values[0] * values[1];
			}
		};
		CustomOperator mod = new CustomOperator("%", true, 3) {
			@Override
			protected double applyOperation(double[] values) {
				return values[0] % values[1];
			}
		};
		CustomOperator umin = new CustomOperator("\'", false,
				this.highUnaryPrecedence ? 7 : 5, 1) {
			@Override
			protected double applyOperation(double[] values) {
				return -values[0];
			}
		};
		CustomOperator pow = new CustomOperator("^", false, 5, 2) {
			@Override
			protected double applyOperation(double[] values) {
				return Math.pow(values[0], values[1]);
			}
		};
		Map<String, CustomOperator> operations = new HashMap<String, CustomOperator>();
		operations.put("+", add);
		operations.put("-", sub);
		operations.put("*", mul);
		operations.put("/", div);
		operations.put("\'", umin);
		operations.put("^", pow);
		operations.put("%", mod);
		return operations;
	}

	private Map<String, CustomFunction> getBuiltinFunctions() {
		try {
			CustomFunction abs = new CustomFunction("abs") {
				@Override
				public double applyFunction(double... args) {
					return Math.abs(args[0]);
				}
			};
			CustomFunction acos = new CustomFunction("acos") {
				@Override
				public double applyFunction(double... args) {
					return Math.acos(args[0]);
				}
			};
			CustomFunction asin = new CustomFunction("asin") {
				@Override
				public double applyFunction(double... args) {
					return Math.asin(args[0]);
				}
			};
			CustomFunction atan = new CustomFunction("atan") {
				@Override
				public double applyFunction(double... args) {
					return Math.atan(args[0]);
				}
			};
			CustomFunction cbrt = new CustomFunction("cbrt") {
				@Override
				public double applyFunction(double... args) {
					return Math.cbrt(args[0]);
				}
			};
			CustomFunction ceil = new CustomFunction("ceil") {
				@Override
				public double applyFunction(double... args) {
					return Math.ceil(args[0]);
				}
			};
			CustomFunction cos = new CustomFunction("cos") {
				@Override
				public double applyFunction(double... args) {
					return Math.cos(args[0]);
				}
			};
			CustomFunction cosh = new CustomFunction("cosh") {
				@Override
				public double applyFunction(double... args) {
					return Math.cosh(args[0]);
				}
			};
			CustomFunction exp = new CustomFunction("exp") {
				@Override
				public double applyFunction(double... args) {
					return Math.exp(args[0]);
				}
			};
			CustomFunction expm1 = new CustomFunction("expm1") {
				@Override
				public double applyFunction(double... args) {
					return Math.expm1(args[0]);
				}
			};
			CustomFunction floor = new CustomFunction("floor") {
				@Override
				public double applyFunction(double... args) {
					return Math.floor(args[0]);
				}
			};
			CustomFunction log = new CustomFunction("log") {
				@Override
				public double applyFunction(double... args) {
					return Math.log(args[0]);
				}
			};
			CustomFunction sine = new CustomFunction("sin") {
				@Override
				public double applyFunction(double... args) {
					return Math.sin(args[0]);
				}
			};
			CustomFunction sinh = new CustomFunction("sinh") {
				@Override
				public double applyFunction(double... args) {
					return Math.sinh(args[0]);
				}
			};
			CustomFunction sqrt = new CustomFunction("sqrt") {
				@Override
				public double applyFunction(double... args) {
					return Math.sqrt(args[0]);
				}
			};
			CustomFunction tan = new CustomFunction("tan") {
				@Override
				public double applyFunction(double... args) {
					return Math.tan(args[0]);
				}
			};
			CustomFunction tanh = new CustomFunction("tanh") {
				@Override
				public double applyFunction(double... args) {
					return Math.tanh(args[0]);
				}
			};
			Map<String, CustomFunction> customFunctions = new HashMap<String, CustomFunction>();
			customFunctions.put("abs", abs);
			customFunctions.put("acos", acos);
			customFunctions.put("asin", asin);
			customFunctions.put("atan", atan);
			customFunctions.put("cbrt", cbrt);
			customFunctions.put("ceil", ceil);
			customFunctions.put("cos", cos);
			customFunctions.put("cosh", cosh);
			customFunctions.put("exp", exp);
			customFunctions.put("expm1", expm1);
			customFunctions.put("floor", floor);
			customFunctions.put("log", log);
			customFunctions.put("sin", sine);
			customFunctions.put("sinh", sinh);
			customFunctions.put("sqrt", sqrt);
			customFunctions.put("tan", tan);
			customFunctions.put("tanh", tanh);
			return customFunctions;
		} catch (InvalidCustomFunctionException e) {
			// this should not happen...
			throw new RuntimeException(e);
		}
	}

	/**
	 * build a new {@link Calculable} from the expression using the supplied
	 * variables
	 * 
	 * @return the {@link Calculable} which can be used to evaluate the
	 *         expression
	 * @throws UnknownFunctionException
	 *             when an unrecognized function name is used in the expression
	 * @throws UnparsableExpressionException
	 *             if the expression could not be parsed
	 */
	public Calculable build() throws UnknownFunctionException,
			UnparsableExpressionException {
		for (CustomOperator op : customOperators.values()) {
			for (int i = 0; i < op.symbol.length(); i++) {
				if (!validOperatorSymbols.contains(op.symbol.charAt(i))) {
					throw new UnparsableExpressionException(
							""
									+ op.symbol
									+ " is not a valid symbol for an operator please choose from: !,#,§,$,&,;,:,~,<,>,|,=");
				}
			}
		}
		for (String varName : variables.keySet()) {
			checkVariableName(varName);
			if (customFunctions.containsKey(varName)) {
				throw new UnparsableExpressionException("Variable '" + varName
						+ "' cannot have the same name as a function");
			}
		}
		builtInOperators.putAll(customOperators);
		return RPNConverter.toRPNExpression(expression, variables,
				customFunctions, builtInOperators);
	}

	private void checkVariableName(String varName)
			throws UnparsableExpressionException {
		char[] name = varName.toCharArray();
		for (int i = 0; i < name.length; i++) {
			if (i == 0){
				if (!Character.isLetter(name[i]) && name[i] != '_'){
					throw new UnparsableExpressionException(varName + " is not a valid variable name: character '" + name[i] + " at " + i); 
				}
			}else{
				if (!Character.isLetter(name[i]) && !Character.isDigit(name[i]) && name[i] != '_'){
					throw new UnparsableExpressionException(varName + " is not a valid variable name: character '" + name[i] + " at " + i); 
				}
			}
		}
	}

	/**
	 * add a custom function instance for the evaluator to recognize
	 * 
	 * @param function
	 *            the {@link CustomFunction} to add
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withCustomFunction(CustomFunction function) {
		customFunctions.put(function.name, function);
		return this;
	}

	public ExpressionBuilder withCustomFunctions(
			Collection<CustomFunction> functions) {
		for (CustomFunction f : functions) {
			withCustomFunction(f);
		}
		return this;
	}

	/**
	 * set the value for a variable
	 * 
	 * @param variableName
	 *            the variable name e.g. "x"
	 * @param value
	 *            the value e.g. 2.32d
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withVariable(String variableName, double value) {
		variables.put(variableName, value);
		return this;
	}

	/**
	 * set the variables names used in the expression without setting their
	 * values
	 * 
	 * @param variableNames
	 *            vararg {@link String} of the variable names used in the
	 *            expression
	 * @return the ExpressionBuilder instance
	 */
	public ExpressionBuilder withVariableNames(String... variableNames) {
		for (String variable : variableNames) {
			variables.put(variable, null);
		}
		return this;
	}

	/**
	 * set the values for variables
	 * 
	 * @param variableMap
	 *            a map of variable names to variable values
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withVariables(Map<String, Double> variableMap) {
		for (Entry<String, Double> v : variableMap.entrySet()) {
			variables.put(v.getKey(), v.getValue());
		}
		return this;
	}

	/**
	 * set a {@link CustomOperator} to be used in the expression
	 * 
	 * @param operation
	 *            the {@link CustomOperator} to be used
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withOperation(CustomOperator operation) {
		customOperators.put(operation.symbol, operation);
		return this;
	}

	/**
	 * set a {@link Collection} of {@link CustomOperator} to use in the
	 * expression
	 * 
	 * @param operations
	 *            the {@link Collection} of {@link CustomOperator} to use
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withOperations(
			Collection<CustomOperator> operations) {
		for (CustomOperator op : operations) {
			withOperation(op);
		}
		return this;
	}

	/**
	 * set the mathematical expression for parsing
	 * 
	 * @param expression
	 *            a mathematical expression
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withExpression(String expression) {
		this.expression = expression;
		return this;
	}
}
