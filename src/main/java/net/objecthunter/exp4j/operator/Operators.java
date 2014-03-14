package net.objecthunter.exp4j.operator;

import java.math.BigDecimal;

import net.objecthunter.exp4j.bigdecimal.BigDecimalMath;
import net.objecthunter.exp4j.complex.ComplexNumber;
import net.objecthunter.exp4j.complex.ComplexNumberMath;
import net.objecthunter.exp4j.expression.ExpressionBuilder;

public class Operators {

	public static final int PRECEDENCE_ADDITION = 500;
	public static final int PRECEDENCE_SUBTRACTION = PRECEDENCE_ADDITION;
	public static final int PRECEDENCE_MULTIPLICATION = 1000;
	public static final int PRECEDENCE_DIVISION = PRECEDENCE_MULTIPLICATION;
	public static final int PRECEDENCE_MODULO = PRECEDENCE_DIVISION;
	public static final int PRECEDENCE_POWER = 10000;
	public static final int PRECEDENCE_UNARY_MINUS = 20000;

	public static final int INDEX_ADDITION = 0;
	public static final int INDEX_SUBTRACTION = 1;
	public static final int INDEX_MUTLIPLICATION = 2;
	public static final int INDEX_DIVISION = 3;
	public static final int INDEX_POWER = 4;
	public static final int INDEX_MODULO = 5;
	public static final int INDEX_UNARYMINUS = 6;
	public static final int INDEX_SCINOTATION = 7;

	private static final char[] ALLOWED_OPERATOR_CHARS = { '+', '-', '*', '/',
			'%', '^', '!', '#', '§', '$', '&', ';', ':', '~', '<', '>', '|',
			'=', 'E' };
	private static final Operator[] builtinDouble = new Operator[8];
	private static final Operator[] builtinComplex = new Operator[8];
	private static final Operator[] builtinBigDecimal = new Operator[8];

	static {
		builtinDouble[INDEX_ADDITION] = new Operator<Double>("+", 2, true,
				PRECEDENCE_ADDITION) {
			@Override
			public Double apply(Double... args) {
				return args[0] + args[1];
			}
		};
		builtinComplex[INDEX_ADDITION] = new Operator<ComplexNumber>("+", 2, true, PRECEDENCE_ADDITION) {
			public ComplexNumber apply(ComplexNumber ... args) {
				return new ComplexNumberMath().add(args[0], args[1]);
			};
		};
		builtinBigDecimal[INDEX_ADDITION] = new Operator<BigDecimal>("+", 2 , true, PRECEDENCE_ADDITION) {
			public BigDecimal apply(BigDecimal ... args) {
				return args[0].add(args[1]);
			}
		};
		builtinDouble[INDEX_SUBTRACTION] = new Operator<Double>("-", 2, true,
				PRECEDENCE_SUBTRACTION) {
			@Override
			public Double apply(Double... args) {
				return args[0] - args[1];
			}
		};
		builtinComplex[INDEX_SUBTRACTION] = new Operator<ComplexNumber>("-", 2, true, PRECEDENCE_SUBTRACTION) {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.subtract(args[0], args[1]);
			};
		};
		builtinBigDecimal[INDEX_SUBTRACTION] = new Operator<BigDecimal>("-", 2 , true, PRECEDENCE_SUBTRACTION) {
			public BigDecimal apply(BigDecimal ... args) {
				return args[0].subtract(args[1]);
			}
		};
		builtinDouble[INDEX_MUTLIPLICATION] = new Operator<Double>("*", 2, true,
				PRECEDENCE_MULTIPLICATION) {
			@Override
			public Double apply(Double... args) {
				return args[0] * args[1];
			}
		};
		builtinComplex[INDEX_MUTLIPLICATION] = new Operator<ComplexNumber>("*", 2, true, PRECEDENCE_MULTIPLICATION) {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.multiply(args[0], args[1]);
			};
		};
		builtinBigDecimal[INDEX_MUTLIPLICATION] = new Operator<BigDecimal>("*", 2 , true, PRECEDENCE_MULTIPLICATION) {
			public BigDecimal apply(BigDecimal ... args) {
				return args[0].multiply(args[1]);
			}
		};
		builtinDouble[INDEX_DIVISION] = new Operator<Double>("/", 2, true,
				PRECEDENCE_DIVISION) {
			@Override
			public Double apply(Double... args) {
				return args[0] / args[1];
			}
		};
		builtinComplex[INDEX_DIVISION] = new Operator<ComplexNumber>("/", 2, true, PRECEDENCE_DIVISION) {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.divide(args[0], args[1]);
			};
		};
		builtinBigDecimal[INDEX_DIVISION] = new Operator<BigDecimal>("/", 2 , true, PRECEDENCE_DIVISION) {
			public BigDecimal apply(BigDecimal ... args) {
				return args[0].divide(args[1]);
			}
		};
		builtinDouble[INDEX_POWER] = new Operator<Double>("^", 2, true,
				PRECEDENCE_POWER) {
			@Override
			public Double apply(Double... args) {
				return Math.pow(args[0], args[1]);
			}
		};
		builtinComplex[INDEX_POWER] = new Operator<ComplexNumber>("^", 2, true, PRECEDENCE_POWER) {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.power(args[0], args[1]);
			};
		};
		builtinBigDecimal[INDEX_POWER] = new Operator<BigDecimal>("^", 2 , true, PRECEDENCE_POWER) {
			public BigDecimal apply(BigDecimal ... args) {
				if (args[1].compareTo(new BigDecimal(args[1].toBigInteger())) == 0) {
					return BigDecimalMath.powRound(args[0],args[1].toBigInteger());
				}
				return BigDecimalMath.pow(args[0], args[1]);
			}
		};
		builtinDouble[INDEX_MODULO] = new Operator<Double>("%", 2, true,
				PRECEDENCE_MODULO) {
			@Override
			public Double apply(Double... args) {
				return args[0] % args[1];
			}
		};
		builtinComplex[INDEX_MODULO] = new Operator<ComplexNumber>("%", 2, true, PRECEDENCE_MODULO) {
			public ComplexNumber apply(ComplexNumber ... args) {
				throw new IllegalArgumentException("Not yet implemented");
			};
		};
		builtinBigDecimal[INDEX_MODULO] = new Operator<BigDecimal>("%", 2 , true, PRECEDENCE_MODULO) {
			public BigDecimal apply(BigDecimal ... args) {
				throw new IllegalArgumentException("Not yet implemented");
			}
		};
		builtinDouble[INDEX_UNARYMINUS] = new Operator<Double>("-", 1, true,
				PRECEDENCE_UNARY_MINUS) {
			@Override
			public Double apply(Double... args) {
				return -args[0];
			}
		};
		builtinComplex[INDEX_UNARYMINUS] = new Operator<ComplexNumber>("-", 1, true, PRECEDENCE_UNARY_MINUS) {
			public ComplexNumber apply(ComplexNumber ... args) {
				return new ComplexNumber(args[0].getReal() * -1d, args[0].getImaginary() * -1d);
			};
		};
		builtinBigDecimal[INDEX_UNARYMINUS] = new Operator<BigDecimal>("-", 1 , true, PRECEDENCE_UNARY_MINUS) {
			public BigDecimal apply(BigDecimal ... args) {
				return args[0].multiply(new BigDecimal(-1));
			}
		};
		builtinDouble[INDEX_SCINOTATION] = new Operator<Double>("E", 2, true,
				PRECEDENCE_POWER) {
			@Override
			public Double apply(Double... args) {
				return Math.pow(args[0], args[1]);
			}
		};
		builtinComplex[INDEX_SCINOTATION] = new Operator<ComplexNumber>("E", 2, true, PRECEDENCE_POWER) {
			public ComplexNumber apply(ComplexNumber ... args) {
				throw new IllegalArgumentException("Not yet implemented");
			};
		};
		builtinBigDecimal[INDEX_SCINOTATION] = new Operator<BigDecimal>("E", 2 , true, PRECEDENCE_POWER) {
			public BigDecimal apply(BigDecimal ... args) {
				int exponent = args[1].intValue();
				if (! new BigDecimal(exponent).equals(args[1])) {
					throw new IllegalArgumentException("Only integer powers are supported in BigDecimal mode");
				}
				return args[0].pow(exponent);
			}
		};
}

	public static char[] getAllowedOperatorChars() {
		return ALLOWED_OPERATOR_CHARS;
	}
	
	public static boolean isAllowedChar(char ch) {
		int len = ALLOWED_OPERATOR_CHARS.length;
		for (int i = 0; i < len; i++) {
			if (ch == ALLOWED_OPERATOR_CHARS[i]) {
				return true;
			}
		}
		return false;
	}

	public static Operator getBuiltinOperator(char ch, int argc, final int mode) {
		Operator[] ops;
		switch (mode) {
			case ExpressionBuilder.MODE_DOUBLE:
				ops = builtinDouble;
				break;
			case ExpressionBuilder.MODE_COMPLEX:
				ops = builtinComplex;
				break;
			case ExpressionBuilder.MODE_BIGDECIMAL:
				ops = builtinBigDecimal;
				break;
				default:
					throw new IllegalArgumentException("Mode " + mode + " can not be used");
		}
		
		switch (ch) {
		case '+':
			return ops[INDEX_ADDITION];
		case '-':
			if (argc == 1) {
				return ops[INDEX_UNARYMINUS];
			}else {
				return ops[INDEX_SUBTRACTION];
			}
		case '*':
			return ops[INDEX_MUTLIPLICATION];
		case '/':
			return ops[INDEX_DIVISION];
		case '%':
			return ops[INDEX_MODULO];
		case '^':
			return ops[INDEX_POWER];
		default:
			return null;
		}
	}

	public static boolean isBuiltinOperator(char ch) {
		return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^' || ch == 'E';
	} 
}
