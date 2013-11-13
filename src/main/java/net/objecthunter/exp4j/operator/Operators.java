package net.objecthunter.exp4j.operator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.objecthunter.exp4j.ComplexNumber;
import net.objecthunter.exp4j.function.ComplexNumberFunctions;

public class Operators {
	public static final int PRECEDENCE_ADDITION = 500;
	public static final int PRECEDENCE_SUBTRACTION = PRECEDENCE_ADDITION;
	public static final int PRECEDENCE_MULTIPLICATION = 1000;
	public static final int PRECEDENCE_DIVISION = PRECEDENCE_MULTIPLICATION;
	public static final int PRECEDENCE_MODULO = PRECEDENCE_DIVISION;
	public static final int PRECEDENCE_EXPONENTATION = 10000;

	private static Map<String, CustomOperator> builtin = new HashMap<String, CustomOperator>();

	static {
		builtin.put("+", new CustomOperator("+", PRECEDENCE_ADDITION) {
			@Override
			public Object apply(Object... args) {
				if (args[0] instanceof Float) {
					return (float) args[0] + (float) args[1];
				} else if (args[0] instanceof Double) {
					return (double) args[0] + (double) args[1];
				} else if (args[0] instanceof BigDecimal) {
					return ((BigDecimal) args[0]).add((BigDecimal) args[1]);
				} else if (args[0] instanceof ComplexNumber) {
					return ComplexNumberFunctions.add((ComplexNumber) args[0], (ComplexNumber) args[1]);
				} else {
					throw new RuntimeException("Unknown type " + args[0].getClass().getName());
				}
			}
		});
		builtin.put("-", new CustomOperator("-", PRECEDENCE_SUBTRACTION) {
			@Override
			public Object apply(Object... args) {
				if (args[0] instanceof Float) {
					return (float) args[0] - (float) args[1];
				} else if (args[0] instanceof Double) {
					return (double) args[0] - (double) args[1];
				} else if (args[0] instanceof BigDecimal) {
					return ((BigDecimal) args[0]).subtract((BigDecimal) args[1]);
				} else if (args[0] instanceof ComplexNumber) {
					return ComplexNumberFunctions.subtract((ComplexNumber) args[0], (ComplexNumber) args[1]);
				} else {
					throw new RuntimeException("Unknown type " + args[0].getClass().getName());
				}
			}
		});
		builtin.put("*", new CustomOperator("*", PRECEDENCE_MULTIPLICATION) {
			@Override
			public Object apply(Object... args) {
				if (args[0] instanceof Float) {
					return (float) args[0] * (float) args[1];
				} else if (args[0] instanceof Double) {
					return (double) args[0] * (double) args[1];
				} else if (args[0] instanceof BigDecimal) {
					return ((BigDecimal) args[0]).multiply((BigDecimal) args[1]);
				} else if (args[0] instanceof ComplexNumber) {
					return ComplexNumberFunctions.multiply((ComplexNumber) args[0], (ComplexNumber) args[1]);
				} else {
					throw new RuntimeException("Unknown type " + args[0].getClass().getName());
				}
			}
		});
		builtin.put("/", new CustomOperator("/", PRECEDENCE_DIVISION) {
			@Override
			public Object apply(Object... args) {
				if (args[0] instanceof Float) {
					if ((float) args[1] == 0f) {
						throw new ArithmeticException("Division by zero");
					}
					return (float) args[0] / (float) args[1];
				} else if (args[0] instanceof Double) {
					if ((double) args[1] == 0d) {
						throw new ArithmeticException("Division by zero");
					}
					return (double) args[0] / (double) args[1];
				} else if (args[0] instanceof BigDecimal) {
					return ((BigDecimal) args[0]).divide((BigDecimal) args[1]);
				} else if (args[0] instanceof ComplexNumber) {
					return ComplexNumberFunctions.divide((ComplexNumber) args[0],(ComplexNumber) args[1]);
				} else {
					throw new RuntimeException("Unknown type " + args[0].getClass().getName());
				}
			}
		});
		builtin.put("%", new CustomOperator("%", PRECEDENCE_MODULO) {
			@Override
			public Object apply(Object... args) {
				if (args[0] instanceof Float) {
					if ((float) args[1] == 0f) {
						throw new ArithmeticException("Division by zero");
					}
					return (float) args[0] % (float) args[1];
				} else if (args[0] instanceof Double) {
					if ((double) args[1] == 0d) {
						throw new ArithmeticException("Division by zero");
					}
					return (double) args[0] % (double) args[1];
				} else if (args[0] instanceof BigDecimal) {
					throw new RuntimeException("No support for big decimals");
				} else if (args[0] instanceof ComplexNumber) {
					throw new RuntimeException("No support for complex numbers");
				} else {
					throw new RuntimeException("Unknown type " + args[0].getClass().getName());
				}
			}
		});
		builtin.put("^", new CustomOperator("^", PRECEDENCE_EXPONENTATION, 2, false) {
			@Override
			public Object apply(Object... args) {
				if (args[0] instanceof Float) {
					return (float) Math.pow(((Float) args[0]).doubleValue(), ((Float) args[1]).doubleValue());
				} else if (args[0] instanceof Double) {
					return Math.pow((double) args[0], (double) args[1]);
				} else if (args[0] instanceof BigDecimal) {
					throw new UnsupportedOperationException("builtin power operator is not available for BigDecimal");
				} else if (args[0] instanceof ComplexNumber) {
					return ComplexNumberFunctions.power((ComplexNumber) args[0], (ComplexNumber) args[1]);
				} else {
					throw new RuntimeException("Unknown type " + args[0].getClass().getName());
				}
			}
		});
	}

	public static boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^';
	}

	public static CustomOperator getOperator(char c) {
		return builtin.get(String.valueOf(c));
	}

	public static CustomOperator getOperator(String symbols) {
		return builtin.get(symbols);
	}

	public static CustomOperator getUnaryMinusOperator() {
		return new CustomOperator("-", PRECEDENCE_EXPONENTATION, 1, false) {
			@Override
			public Object apply(Object... args) {
				if (args[0] instanceof Float) {
					return (float) args[0] * -1f;
				} else if (args[0] instanceof Double) {
					return (double) args[0] * -1d;
				} else if (args[0] instanceof BigDecimal) {
					return ((BigDecimal) args[0]).negate();
				} else if (args[0] instanceof ComplexNumber) {
					ComplexNumber c = (ComplexNumber) args[0];
					return new ComplexNumber(c.getReal() * -1d, c.getImaginary() * -1d);
				} else {
					throw new RuntimeException("Unknown type " + args[0].getClass().getName());
				}
			}
		};
	}

	public static boolean isAllowedOperatorSymbol(char c) {
		return c == '>' || c == '<' || c == '#' || c == '~' || c == '&' || c == '$' || c == '!' || c == '§' || c == '=';
	}
}
