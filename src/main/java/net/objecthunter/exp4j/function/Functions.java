package net.objecthunter.exp4j.function;

public class Functions {

    private static final int INDEX_SIN = 0;
    private static final int INDEX_COS = 1;
    private static final int INDEX_TAN = 2;
    private static final int INDEX_CSC = 3;
    private static final int INDEX_SEC = 4;
    private static final int INDEX_COT = 5;
    private static final int INDEX_SINH = 6;
    private static final int INDEX_COSH = 7;
    private static final int INDEX_TANH = 8;
    private static final int INDEX_CSCH = 9;
    private static final int INDEX_SECH = 10;
    private static final int INDEX_COTH = 11;
    private static final int INDEX_ASIN = 12;
    private static final int INDEX_ACOS = 13;
    private static final int INDEX_ATAN = 14;
    private static final int INDEX_SQRT = 15;
    private static final int INDEX_CBRT = 16;
    private static final int INDEX_ABS = 17;
    private static final int INDEX_CEIL = 18;
    private static final int INDEX_FLOOR = 19;
    private static final int INDEX_POW = 20;
    private static final int INDEX_EXP = 21;
    private static final int INDEX_EXPM1 = 22;
    private static final int INDEX_LOG10 = 23;
    private static final int INDEX_LOG2 = 24;
    private static final int INDEX_LOG = 25;
    private static final int INDEX_LOG1P = 26;
    private static final int INDEX_LOGB = 27;
    private static final int INDEX_SGN = 28;
    private static final int INDEX_TO_RADIAN = 29;
    private static final int INDEX_TO_DEGREE = 30;

    private static final Function[] functions = new Function[31];

    static {
        functions[INDEX_SIN] = new Function("sin") {
            @Override
            public double apply(double... args) {
                return Math.sin(args[0]);
            }
        };
        functions[INDEX_COS] = new Function("cos") {
            @Override
            public double apply(double... args) {
                return Math.cos(args[0]);
            }
        };
        functions[INDEX_TAN] = new Function("tan") {
            @Override
            public double apply(double... args) {
                return Math.tan(args[0]);
            }
        };
        functions[INDEX_COT] = new Function("cot") {
            @Override
            public double apply(double... args) {
                double tan = Math.tan(args[0]);
                if (tan == 0D) {
                    throw new ArithmeticException("Argument for cotangent must not be zero");
                }
                return 1d / tan;
            }
        };
        functions[INDEX_LOG] = new Function("log") {
            @Override
            public double apply(double... args) {
                if (args[0] <= 0D) {
                    throw new IllegalArgumentException("Argument for logarithm must be greater than zero");
                }
                return Math.log(args[0]);
            }
        };
        functions[INDEX_LOG2] = new Function("log2") {
            @Override
            public double apply(double... args) {
                if (args[0] <= 0D) {
                    throw new IllegalArgumentException("Argument for logarithm must be greater than zero");
                }
                return Math.log(args[0]) / Math.log(2d);
            }
        };
        functions[INDEX_LOG10] = new Function("log10") {
            @Override
            public double apply(double... args) {
                if (args[0] <= 0D) {
                    throw new IllegalArgumentException("Argument for logarithm must be greater than zero");
                }
                return Math.log10(args[0]);
            }
        };
        functions[INDEX_LOG1P] = new Function("log1p") {
            @Override
            public double apply(double... args) {
                if (args[0] <= -1D) {
                    throw new IllegalArgumentException("Argument for logarithm must be greater than zero");
                }
                return Math.log1p(args[0]);
            }
        };
        functions[INDEX_ABS] = new Function("abs") {
            @Override
            public double apply(double... args) {
                return Math.abs(args[0]);
            }
        };
        functions[INDEX_ACOS] = new Function("acos") {
            @Override
            public double apply(double... args) {
                if (args[0] > 1D || args[0] < -1D) {
                    throw new IllegalArgumentException("Argument for arc cosine must be in the interval [-1, 1]");
                }
                return Math.acos(args[0]);
            }
        };
        functions[INDEX_ASIN] = new Function("asin") {
            @Override
            public double apply(double... args) {
                if (args[0] > 1D || args[0] < -1D) {
                    throw new IllegalArgumentException("Argument for arc cosine must be in the interval [-1, 1]");
                }
                return Math.asin(args[0]);
            }
        };
        functions[INDEX_ATAN] = new Function("atan") {
            @Override
            public double apply(double... args) {
                return Math.atan(args[0]);
            }
        };
        functions[INDEX_CBRT] = new Function("cbrt") {
            @Override
            public double apply(double... args) {
                return Math.cbrt(args[0]);
            }
        };
        functions[INDEX_FLOOR] = new Function("floor") {
            @Override
            public double apply(double... args) {
                return Math.floor(args[0]);
            }
        };
        functions[INDEX_SINH] = new Function("sinh") {
            @Override
            public double apply(double... args) {
                return Math.sinh(args[0]);
            }
        };
        functions[INDEX_SQRT] = new Function("sqrt") {
            @Override
            public double apply(double... args) {
                if (args[0] < 0D) {
                    throw new IllegalArgumentException("Argument for square root must not be negative");
                }
                return Math.sqrt(args[0]);
            }
        };
        functions[INDEX_TANH] = new Function("tanh") {
            @Override
            public double apply(double... args) {
                return Math.tanh(args[0]);
            }
        };
        functions[INDEX_COSH] = new Function("cosh") {
            @Override
            public double apply(double... args) {
                return Math.cosh(args[0]);
            }
        };
        functions[INDEX_CEIL] = new Function("ceil") {
            @Override
            public double apply(double... args) {
                return Math.ceil(args[0]);
            }
        };
        functions[INDEX_POW] = new Function("pow", 2) {
            @Override
            public double apply(double... args) {
                if (args[0] == 0D && args[1] == 0D) {
                    throw new IllegalArgumentException("Zero to the power of zero is undefined");
                }
                return Math.pow(args[0], args[1]);
            }
        };
        functions[INDEX_EXP] = new Function("exp", 1) {
            @Override
            public double apply(double... args) {
                return Math.exp(args[0]);
            }
        };
        functions[INDEX_EXPM1] = new Function("expm1", 1) {
            @Override
            public double apply(double... args) {
                return Math.expm1(args[0]);
            }
        };
        functions[INDEX_SGN] = new Function("signum", 1) {
            @Override
            public double apply(double... args) {
                if (args[0] > 0) {
                    return 1;
                } else if (args[0] < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        functions[INDEX_CSC] = new Function("csc") {
            @Override
            public double apply(double... args) {
                double sin = Math.sin(args[0]);
                if (sin == 0d) {
                    throw new ArithmeticException("Division by zero in cosecant!");
                }
                return 1d / sin;
            }
        };
        functions[INDEX_SEC] = new Function("sec") {
            @Override
            public double apply(double... args) {
                double cos = Math.cos(args[0]);
                if (cos == 0d) {
                    throw new ArithmeticException("Division by zero in secant!");
                }
                return 1d / cos;
            }
        };
        functions[INDEX_CSCH] = new Function("csch") {
            @Override
            public double apply(double... args) {
                final double sinh = Math.sinh(args[0]);
                if (sinh == 0d) {
                    throw new ArithmeticException("Division by zero in hyperbolic cosecant!");
                }
                return 1d / Math.sinh(args[0]);
            }
        };
        functions[INDEX_SECH] = new Function("sech") {
            @Override
            public double apply(double... args) {
                return 1d / Math.cosh(args[0]);
            }
        };
        functions[INDEX_COTH] = new Function("coth") {
            @Override
            public double apply(double... args) {
                final double sinh = Math.sinh(args[0]);
                if (sinh == 0d) {
                    throw new ArithmeticException("Division by zero in hyperbolic cotangent!");
                }
                return Math.cosh(args[0]) / sinh;
            }
        };
        functions[INDEX_LOGB] = new Function("logb", 2) {
            @Override
            public double apply(double... args) {
                if (args[0] <= 0) {
                    throw new IllegalArgumentException("Base of logarithm must be greater than zero");
                }
                if (args[1] <= 0) {
                    throw new IllegalArgumentException("Logarithm of number equal to or less than zero is undefined");
                }
                return Math.log(args[1]) / Math.log(args[0]);
            }
        };
        functions[INDEX_TO_RADIAN] = new Function("toradian") {
            @Override
            public double apply(double... args) {
                return Math.toRadians(args[0]);
            }
        };
        functions[INDEX_TO_DEGREE] = new Function("todegree") {
            @Override
            public double apply(double... args) {
                return Math.toDegrees(args[0]);
            }
        };

    }

    /**
     * Get the builtin function for a given name
     *
     * @param name te name of the function
     * @return a Function instance
     */
    public static Function getFunction(final String name) {

        switch (name) {
            case "sin":
                return functions[INDEX_SIN];
            case "cos":
                return functions[INDEX_COS];
            case "tan":
                return functions[INDEX_TAN];
            case "cot":
                return functions[INDEX_COT];
            case "asin":
                return functions[INDEX_ASIN];
            case "acos":
                return functions[INDEX_ACOS];
            case "atan":
                return functions[INDEX_ATAN];
            case "sinh":
                return functions[INDEX_SINH];
            case "cosh":
                return functions[INDEX_COSH];
            case "tanh":
                return functions[INDEX_TANH];
            case "abs":
                return functions[INDEX_ABS];
            case "log":
                return functions[INDEX_LOG];
            case "log10":
                return functions[INDEX_LOG10];
            case "log2":
                return functions[INDEX_LOG2];
            case "log1p":
                return functions[INDEX_LOG1P];
            case "logb":
                return functions[INDEX_LOGB];
            case "ceil":
                return functions[INDEX_CEIL];
            case "floor":
                return functions[INDEX_FLOOR];
            case "sqrt":
                return functions[INDEX_SQRT];
            case "cbrt":
                return functions[INDEX_CBRT];
            case "pow":
                return functions[INDEX_POW];
            case "exp":
                return functions[INDEX_EXP];
            case "expm1":
                return functions[INDEX_EXPM1];
            case "signum":
                return functions[INDEX_SGN];
            case "csc":
                return functions[INDEX_CSC];
            case "sec":
                return functions[INDEX_SEC];
            case "csch":
                return functions[INDEX_CSCH];
            case "sech":
                return functions[INDEX_SECH];
            case "coth":
                return functions[INDEX_COTH];
            case "toradian":
                return functions[INDEX_TO_RADIAN];
            case "todegree":
                return functions[INDEX_TO_DEGREE];
            default:
                return null;
        }
    }
}
