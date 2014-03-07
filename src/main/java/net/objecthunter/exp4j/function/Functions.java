package net.objecthunter.exp4j.function;

import java.math.BigDecimal;
import java.math.MathContext;

import net.objecthunter.exp4j.bigdecimal.BigDecimalMath;
import net.objecthunter.exp4j.complex.ComplexNumber;
import net.objecthunter.exp4j.complex.ComplexNumberMath;
import net.objecthunter.exp4j.expression.ExpressionBuilder;

public class Functions {
	public static final int INDEX_SIN = 0;
	public static final int INDEX_COS = 1;
	public static final int INDEX_TAN = 2;
	public static final int INDEX_LOG = 3;
	public static final int INDEX_LOG1P = 4;
	public static final int INDEX_ABS = 5;
	public static final int INDEX_ACOS = 6;
	public static final int INDEX_ASIN = 7;
	public static final int INDEX_ATAN = 8;
	public static final int INDEX_CBRT = 9;
	public static final int INDEX_CEIL = 10;
	public static final int INDEX_FLOOR = 11;
	public static final int INDEX_SINH = 12;
	public static final int INDEX_SQRT = 13;
	public static final int INDEX_TANH = 14;
	public static final int INDEX_COSH = 15;
	public static final int INDEX_MAX = 16;
	public static final int INDEX_MIN = 17;
	
	private static final Function<Double>[] builtinDouble = new Function[18];
	private static final Function<ComplexNumber>[] builtinComplex = new Function[18];
	private static final Function<BigDecimal>[] builtinBigDecimal = new Function[18];

	
	static {
		builtinDouble[INDEX_SIN] = new Function<Double>("sin") {
			@Override
			public Double apply(Double... args) {
				return Math.sin(args[0]);
			}
		};
		builtinDouble[INDEX_COS] = new Function<Double>("cos") {
			@Override
			public Double apply(Double... args) {
				return Math.cos(args[0]);
			}
		};
		builtinDouble[INDEX_TAN] = new Function<Double>("tan") {
			@Override
			public Double apply(Double... args) {
				return Math.tan(args[0]);
			}
		};
		builtinDouble[INDEX_LOG] = new Function<Double>("log") {
			@Override
			public Double apply(Double... args) {
				return Math.log(args[0]);
			}
		};
		builtinDouble[INDEX_LOG1P] = new Function<Double>("log1p") {
			@Override
			public Double apply(Double... args) {
				return Math.log1p(args[0]);
			}
		};
		builtinDouble[INDEX_ABS] = new Function<Double>("abs") {
			@Override
			public Double apply(Double... args) {
				return Math.abs(args[0]);
			}
		};
		builtinDouble[INDEX_ACOS] = new Function<Double>("acos") {
			@Override
			public Double apply(Double... args) {
				return Math.acos(args[0]);
			}
		};
		builtinDouble[INDEX_ASIN] = new Function<Double>("asin") {
			@Override
			public Double apply(Double... args) {
				return Math.asin(args[0]);
			}
		};
		builtinDouble[INDEX_ATAN] = new Function<Double>("atan") {
			@Override
			public Double apply(Double... args) {
				return Math.atan(args[0]);
			}
		};
		builtinDouble[INDEX_CBRT] = new Function<Double>("cbrt") {
			@Override
			public Double apply(Double... args) {
				return Math.cbrt(args[0]);
			}
		};
		builtinDouble[INDEX_FLOOR] = new Function<Double>("floor") {
			@Override
			public Double apply(Double... args) {
				return Math.floor(args[0]);
			}
		};
		builtinDouble[INDEX_SINH] = new Function<Double>("sinh") {
			@Override
			public Double apply(Double... args) {
				return Math.sinh(args[0]);
			}
		};
		builtinDouble[INDEX_SQRT] = new Function<Double>("sqrt") {
			@Override
			public Double apply(Double... args) {
				return Math.sqrt(args[0]);
			}
		};
		builtinDouble[INDEX_TANH] = new Function<Double>("tanh") {
			@Override
			public Double apply(Double... args) {
				return Math.tanh(args[0]);
			}
		};
		builtinDouble[INDEX_COSH] = new Function<Double>("cosh") {
			@Override
			public Double apply(Double... args) {
				return Math.cosh(args[0]);
			}
		};
		builtinDouble[INDEX_CEIL] = new Function<Double>("ceil") {
			@Override
			public Double apply(Double... args) {
				return Math.ceil(args[0]);
			}
		};
		builtinDouble[INDEX_MAX] = new Function<Double>("max",0) {
			@Override
			public Double apply(Double... args) {
				double max = Double.MIN_VALUE;
				for (double arg: args) {
					max = (arg > max) ? arg : max;
				}
				return max;
			}
		};
		builtinDouble[INDEX_MIN] = new Function<Double>("min",0) {
			@Override
			public Double apply(Double... args) {
				double min = Double.MAX_VALUE;
				for (double arg: args) {
					min = (arg < min) ? arg : min;
				}
				return min;
			}
		};

		builtinBigDecimal[INDEX_SIN]= new Function<BigDecimal> ("sin") {
			public BigDecimal apply(BigDecimal ... args) {
				return BigDecimalMath.sin(args[0]);
			}
		};
		builtinBigDecimal[INDEX_COS]= new Function<BigDecimal> ("cos") {
			public BigDecimal apply(BigDecimal ... args) {
				return BigDecimalMath.cos(args[0]);
			}
		};
		builtinBigDecimal[INDEX_TAN]= new Function<BigDecimal> ("tan") {
			public BigDecimal apply(BigDecimal ... args) {
				return BigDecimalMath.tan(args[0]);
			}
		};
		builtinBigDecimal[INDEX_LOG]= new Function<BigDecimal> ("log") {
			public BigDecimal apply(BigDecimal ... args) {
				return BigDecimalMath.log(args[0]);
			}
		};
		builtinBigDecimal[INDEX_LOG1P]= new Function<BigDecimal> ("log1p") {
			public BigDecimal apply(BigDecimal ... args) {
				throw new RuntimeException("Not yet implemented");
			}
		};
		builtinBigDecimal[INDEX_ABS]= new Function<BigDecimal> ("abs") {
			public BigDecimal apply(BigDecimal ... args) {
				return args[0].abs();
			}
		};
		builtinBigDecimal[INDEX_ACOS]= new Function<BigDecimal> ("acos") {
			public BigDecimal apply(BigDecimal ... args) {
				return BigDecimalMath.acos(args[0]);
			}
		};
		builtinBigDecimal[INDEX_ASIN]= new Function<BigDecimal> ("asin") {
			public BigDecimal apply(BigDecimal ... args) {
				return BigDecimalMath.asin(args[0]);
			}
		};
		builtinBigDecimal[INDEX_ATAN]= new Function<BigDecimal> ("atan") {
			public BigDecimal apply(BigDecimal ... args) {
				return BigDecimalMath.atan(args[0]);
			}
		};
		builtinBigDecimal[INDEX_CBRT]= new Function<BigDecimal> ("cbrt") {
			public BigDecimal apply(BigDecimal ... args) {
				return BigDecimalMath.cbrt(args[0]);
			}
		};
		builtinBigDecimal[INDEX_FLOOR]= new Function<BigDecimal> ("floor") {
			public BigDecimal apply(BigDecimal ... args) {
				throw new RuntimeException("Not yet implemented");
			}
		};
		builtinBigDecimal[INDEX_SINH]= new Function<BigDecimal> ("sinh") {
			public BigDecimal apply(BigDecimal ... args) {
				return BigDecimalMath.sinh(args[0]);
			}
		};
		builtinBigDecimal[INDEX_SQRT]= new Function<BigDecimal> ("sqrt") {
			public BigDecimal apply(BigDecimal ... args) {
				return BigDecimalMath.sqrt(args[0], MathContext.DECIMAL128);
			}
		};
		builtinBigDecimal[INDEX_TANH]= new Function<BigDecimal> ("tanh") {
			public BigDecimal apply(BigDecimal ... args) {
				return BigDecimalMath.tanh(args[0]);
			}
		};
		builtinBigDecimal[INDEX_COSH]= new Function<BigDecimal> ("cosh") {
			public BigDecimal apply(BigDecimal ... args) {
				return BigDecimalMath.cosh(args[0]);
			}
		};
		builtinBigDecimal[INDEX_CEIL]= new Function<BigDecimal> ("ceil") {
			public BigDecimal apply(BigDecimal ... args) {
				throw new RuntimeException("Not yet implemented");
			}
		};
		builtinBigDecimal[INDEX_MAX]= new Function<BigDecimal> ("max",0) {
			public BigDecimal apply(BigDecimal ... args) {
				BigDecimal max = args[0];
				for (int i = 1; i < args.length; i++) {
					if (max.compareTo(args[i]) < 1) {
						max = args[i];
					}
				}
				return max;
			}
		};
		builtinBigDecimal[INDEX_MIN]= new Function<BigDecimal> ("min",0) {
			public BigDecimal apply(BigDecimal ... args) {
				BigDecimal min = args[0];
				for (int i = 1; i < args.length; i++) {
					if (min.compareTo(args[i]) == 1) {
						min = args[i];
					}
				}
				return min;
			}
		};

		builtinComplex[INDEX_SIN] = new Function<ComplexNumber> ("sin") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.sin(args[0]);
			}
		};
		builtinComplex[INDEX_COS] = new Function<ComplexNumber> ("cos") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.cos(args[0]);
			}
		};
		builtinComplex[INDEX_TAN] = new Function<ComplexNumber> ("tan") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.tan(args[0]);
			}
		};

		builtinComplex[INDEX_LOG] = new Function<ComplexNumber> ("log") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.log(args[0]);
			}
		};
		builtinComplex[INDEX_LOG1P] = new Function<ComplexNumber> ("log1p") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.log1p(args[0]);
			}
		};
		builtinComplex[INDEX_ASIN] = new Function<ComplexNumber> ("asin") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.asin(args[0]);
			}
		};
		builtinComplex[INDEX_ABS] = new Function<ComplexNumber> ("abs") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return new ComplexNumber(ComplexNumberMath.mod(args[0]), 0d);
			}
		};
		builtinComplex[INDEX_ACOS] = new Function<ComplexNumber> ("acos") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.acos(args[0]);
			}
		};
		builtinComplex[INDEX_ATAN] = new Function<ComplexNumber> ("atan") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.atan(args[0]);
			}
		};
		builtinComplex[INDEX_CBRT] = new Function<ComplexNumber> ("cbrt") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.cbrt(args[0]);
			}
		};
		builtinComplex[INDEX_FLOOR] = new Function<ComplexNumber> ("floor") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.floor(args[0]);
			}
		};

		builtinComplex[INDEX_SINH] = new Function<ComplexNumber> ("sinh") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.sinh(args[0]);
			}
		};

		builtinComplex[INDEX_SQRT] = new Function<ComplexNumber> ("sqrt") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.sqrt(args[0]);
			}
		};
		builtinComplex[INDEX_TANH] = new Function<ComplexNumber> ("tanh") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.tanh(args[0]);
			}
		};
		builtinComplex[INDEX_COSH] = new Function<ComplexNumber> ("cosh") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.cosh(args[0]);
			}
		};
		builtinComplex[INDEX_CEIL] = new Function<ComplexNumber> ("ceil") {
			public ComplexNumber apply(ComplexNumber ... args) {
				return ComplexNumberMath.ceil(args[0]);
			}
		};
		builtinComplex[INDEX_MAX] = new Function<ComplexNumber> ("max") {
			public ComplexNumber apply(ComplexNumber ... args) {
				throw new IllegalArgumentException("Complex numbers are not well ordered. So max() is not implemented");
			}
		};

		builtinComplex[INDEX_MIN] = new Function<ComplexNumber> ("min") {
			public ComplexNumber apply(ComplexNumber ... args) {
				throw new IllegalArgumentException("Complex numbers are not well ordered. So min() is not implemented");
			}
		};
	}

	public static char[] getAllowedFunctionCharacters() {
		char[] chars = new char[53];
		int count = 0;
		for (int i = 65; i < 91; i++) {
			chars[count++] = (char) i;
		}
		for (int i = 97; i < 123; i++) {
			chars[count++] = (char) i;
		}
		chars[count] = '_';
		return chars;
	}
	
	public static Function getBuiltinFunction(final String name, final int mode) {
		Function[] funcs;
		switch(mode) {
			case ExpressionBuilder.MODE_DOUBLE:
				funcs = builtinDouble;
				break;
			case ExpressionBuilder.MODE_COMPLEX:
				funcs = builtinComplex;
				break;
			case ExpressionBuilder.MODE_BIGDECIMAL:
				funcs = builtinBigDecimal;
				break;
				default:
					throw new IllegalArgumentException("Mode " + mode + " is not available");
		}
		
		switch(name) {
		case "sin":
			return funcs[INDEX_SIN];
		case "cos":
			return funcs[INDEX_COS];
		case "tan":
			return funcs[INDEX_TAN];
		case "asin":
			return funcs[INDEX_ASIN];
		case "acos":
			return funcs[INDEX_ACOS];
		case "atan":
			return funcs[INDEX_ATAN];
		case "sinh":
			return funcs[INDEX_SINH];
		case "cosh":
			return funcs[INDEX_COSH];
		case "tanh":
			return funcs[INDEX_TANH];
		case "abs":
			return funcs[INDEX_ABS];
		case "log":
			return funcs[INDEX_LOG];
		case "log1p":
			return funcs[INDEX_LOG1P];
		case "ceil":
			return funcs[INDEX_CEIL];
		case "floor":
			return funcs[INDEX_FLOOR];
		case "max":
			return funcs[INDEX_MAX];
		case "min":
			return funcs[INDEX_MIN];
		case "sqrt":
			return funcs[INDEX_SQRT];
		case "cbrt":
			return funcs[INDEX_CBRT];
			default:
				return null;
		}
	}
}
