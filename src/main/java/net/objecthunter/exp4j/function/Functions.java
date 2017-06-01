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
package net.objecthunter.exp4j.function;

/**
 * Class representing the builtin functions available for use in expressions
 */
public class Functions {
    
    private static final int INDEX_SIN = 0;
    private static final int INDEX_COS = 1;
    private static final int INDEX_TAN = 2;
    private static final int INDEX_COT = 3;
    private static final int INDEX_LOG = 4;
    private static final int INDEX_LOG1P = 5;
    private static final int INDEX_ABS = 6;
    private static final int INDEX_ACOS = 7;
    private static final int INDEX_ASIN = 8;
    private static final int INDEX_ATAN = 9;
    private static final int INDEX_CBRT = 10;
    private static final int INDEX_CEIL = 11;
    private static final int INDEX_FLOOR = 12;
    private static final int INDEX_SINH = 13;
    private static final int INDEX_SQRT = 14;
    private static final int INDEX_TANH = 15;
    private static final int INDEX_COSH = 16;
    private static final int INDEX_POW = 17;
    private static final int INDEX_EXP = 18;
    private static final int INDEX_EXPM1 = 19;
    private static final int INDEX_LOG10 = 20;
    private static final int INDEX_LOG2 = 21;
    private static final int INDEX_SGN = 22;

    private static final Function[] BUILTIN_FUNCTIONS = new Function[23];

    static {
        BUILTIN_FUNCTIONS[INDEX_SIN] = new Function("sin") {
            @Override
            public double apply(double... args) {
                return Math.sin(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_COS] = new Function("cos") {
            @Override
            public double apply(double... args) {
                return Math.cos(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_TAN] = new Function("tan") {
            @Override
            public double apply(double... args) {
                return Math.tan(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_COT] = new Function("cot") {
            @Override
            public double apply(double... args) {
                double tan = Math.tan(args[0]);
                if (tan == 0d) {
                    throw new ArithmeticException("Division by zero in cotangent!");
                }
                return 1d/Math.tan(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_LOG] = new Function("log") {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_LOG2] = new Function("log2") {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]) / Math.log(2d);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_LOG10] = new Function("log10") {
            @Override
            public double apply(double... args) {
                return Math.log10(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_LOG1P] = new Function("log1p") {
            @Override
            public double apply(double... args) {
                return Math.log1p(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_ABS] = new Function("abs") {
            @Override
            public double apply(double... args) {
                return Math.abs(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_ACOS] = new Function("acos") {
            @Override
            public double apply(double... args) {
                return Math.acos(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_ASIN] = new Function("asin") {
            @Override
            public double apply(double... args) {
                return Math.asin(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_ATAN] = new Function("atan") {
            @Override
            public double apply(double... args) {
                return Math.atan(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_CBRT] = new Function("cbrt") {
            @Override
            public double apply(double... args) {
                return Math.cbrt(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_FLOOR] = new Function("floor") {
            @Override
            public double apply(double... args) {
                return Math.floor(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_SINH] = new Function("sinh") {
            @Override
            public double apply(double... args) {
                return Math.sinh(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_SQRT] = new Function("sqrt") {
            @Override
            public double apply(double... args) {
                return Math.sqrt(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_TANH] = new Function("tanh") {
            @Override
            public double apply(double... args) {
                return Math.tanh(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_COSH] = new Function("cosh") {
            @Override
            public double apply(double... args) {
                return Math.cosh(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_CEIL] = new Function("ceil") {
            @Override
            public double apply(double... args) {
                return Math.ceil(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_POW] = new Function("pow", 2) {
            @Override
            public double apply(double... args) {
                return Math.pow(args[0], args[1]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_EXP] = new Function("exp", 1) {
            @Override
            public double apply(double... args) {
                return Math.exp(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_EXPM1] = new Function("expm1", 1) {
            @Override
            public double apply(double... args) {
                return Math.expm1(args[0]);
            }
        };
        BUILTIN_FUNCTIONS[INDEX_SGN] = new Function("signum", 1) {
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
    }

    /**
     * Get the builtin function for a given name
     * @param name te name of the function
     * @return a Function instance
     */
    public static Function getBuiltinFunction(final String name) {

        if (name.equals("sin")) {
            return BUILTIN_FUNCTIONS[INDEX_SIN];
        } else if (name.equals("cos")) {
            return BUILTIN_FUNCTIONS[INDEX_COS];
        } else if (name.equals("tan")) {
            return BUILTIN_FUNCTIONS[INDEX_TAN];
        } else if (name.equals("cot")) {
            return BUILTIN_FUNCTIONS[INDEX_COT];
        } else if (name.equals("asin")) {
            return BUILTIN_FUNCTIONS[INDEX_ASIN];
        } else if (name.equals("acos")) {
            return BUILTIN_FUNCTIONS[INDEX_ACOS];
        } else if (name.equals("atan")) {
            return BUILTIN_FUNCTIONS[INDEX_ATAN];
        } else if (name.equals("sinh")) {
            return BUILTIN_FUNCTIONS[INDEX_SINH];
        } else if (name.equals("cosh")) {
            return BUILTIN_FUNCTIONS[INDEX_COSH];
        } else if (name.equals("tanh")) {
            return BUILTIN_FUNCTIONS[INDEX_TANH];
        } else if (name.equals("abs")) {
            return BUILTIN_FUNCTIONS[INDEX_ABS];
        } else if (name.equals("log")) {
            return BUILTIN_FUNCTIONS[INDEX_LOG];
        } else if (name.equals("log10")) {
            return BUILTIN_FUNCTIONS[INDEX_LOG10];
        } else if (name.equals("log2")) {
            return BUILTIN_FUNCTIONS[INDEX_LOG2];
        } else if (name.equals("log1p")) {
            return BUILTIN_FUNCTIONS[INDEX_LOG1P];
        } else if (name.equals("ceil")) {
            return BUILTIN_FUNCTIONS[INDEX_CEIL];
        } else if (name.equals("floor")) {
            return BUILTIN_FUNCTIONS[INDEX_FLOOR];
        } else if (name.equals("sqrt")) {
            return BUILTIN_FUNCTIONS[INDEX_SQRT];
        } else if (name.equals("cbrt")) {
            return BUILTIN_FUNCTIONS[INDEX_CBRT];
        } else if (name.equals("pow")) {
            return BUILTIN_FUNCTIONS[INDEX_POW];
        } else if (name.equals("exp")) {
            return BUILTIN_FUNCTIONS[INDEX_EXP];
        } else if (name.equals("expm1")) {
            return BUILTIN_FUNCTIONS[INDEX_EXPM1];
        } else if (name.equals("signum")) {
            return BUILTIN_FUNCTIONS[INDEX_SGN];
        } else {
            return null;
        }
    }

}
