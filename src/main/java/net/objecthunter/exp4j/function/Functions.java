package net.objecthunter.exp4j.function;

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
	
	private static final Function[] builtin = new Function[18];
	
	static {
		builtin[INDEX_SIN] = new Function("sin") {
			@Override
			public double apply(double... args) {
				return Math.sin(args[0]);
			}
		};
		builtin[INDEX_COS] = new Function("cos") {
			@Override
			public double apply(double... args) {
				return Math.cos(args[0]);
			}
		};
		builtin[INDEX_TAN] = new Function("tan") {
			@Override
			public double apply(double... args) {
				return Math.tan(args[0]);
			}
		};
		builtin[INDEX_LOG] = new Function("log") {
			@Override
			public double apply(double... args) {
				return Math.log(args[0]);
			}
		};
		builtin[INDEX_LOG1P] = new Function("log1p") {
			@Override
			public double apply(double... args) {
				return Math.log1p(args[0]);
			}
		};
		builtin[INDEX_ABS] = new Function("abs") {
			@Override
			public double apply(double... args) {
				return Math.abs(args[0]);
			}
		};
		builtin[INDEX_ACOS] = new Function("acos") {
			@Override
			public double apply(double... args) {
				return Math.acos(args[0]);
			}
		};
		builtin[INDEX_ASIN] = new Function("asin") {
			@Override
			public double apply(double... args) {
				return Math.asin(args[0]);
			}
		};
		builtin[INDEX_ATAN] = new Function("atan") {
			@Override
			public double apply(double... args) {
				return Math.atan(args[0]);
			}
		};
		builtin[INDEX_CBRT] = new Function("cbrt") {
			@Override
			public double apply(double... args) {
				return Math.cbrt(args[0]);
			}
		};
		builtin[INDEX_FLOOR] = new Function("floor") {
			@Override
			public double apply(double... args) {
				return Math.floor(args[0]);
			}
		};
		builtin[INDEX_SINH] = new Function("sinh") {
			@Override
			public double apply(double... args) {
				return Math.sinh(args[0]);
			}
		};
		builtin[INDEX_SQRT] = new Function("sqrt") {
			@Override
			public double apply(double... args) {
				return Math.sqrt(args[0]);
			}
		};
		builtin[INDEX_TANH] = new Function("tanh") {
			@Override
			public double apply(double... args) {
				return Math.tanh(args[0]);
			}
		};
		builtin[INDEX_COSH] = new Function("cosh") {
			@Override
			public double apply(double... args) {
				return Math.cosh(args[0]);
			}
		};
		builtin[INDEX_CEIL] = new Function("ceil") {
			@Override
			public double apply(double... args) {
				return Math.ceil(args[0]);
			}
		};
		builtin[INDEX_MAX] = new Function("max") {
			@Override
			public double apply(double... args) {
				double max = Double.MIN_VALUE;
				for (double arg: args) {
					max = (arg > max) ? arg : max;
				}
				return max;
			}
		};
		builtin[INDEX_MIN] = new Function("min") {
			@Override
			public double apply(double... args) {
				double min = Double.MAX_VALUE;
				for (double arg: args) {
					min = (arg < min) ? arg : min;
				}
				return min;
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
	
	public static Function getFunction(String name) {
		switch(name) {
		case "sin":
			return builtin[INDEX_SIN];
		case "cos":
			return builtin[INDEX_COS];
		case "tan":
			return builtin[INDEX_TAN];
		case "asin":
			return builtin[INDEX_ASIN];
		case "acos":
			return builtin[INDEX_ACOS];
		case "atan":
			return builtin[INDEX_ATAN];
		case "sinh":
			return builtin[INDEX_SINH];
		case "cosh":
			return builtin[INDEX_COSH];
		case "tanh":
			return builtin[INDEX_TANH];
		case "abs":
			return builtin[INDEX_ABS];
		case "log":
			return builtin[INDEX_LOG];
		case "log1p":
			return builtin[INDEX_LOG1P];
		case "ceil":
			return builtin[INDEX_CEIL];
		case "floor":
			return builtin[INDEX_FLOOR];
		case "max":
			return builtin[INDEX_MAX];
		case "min":
			return builtin[INDEX_MIN];
		case "sqrt":
			return builtin[INDEX_SQRT];
		case "cbrt":
			return builtin[INDEX_CBRT];
			default:
				return null;
		}
	}
}
