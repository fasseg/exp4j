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
    public static final int INDEX_POW = 18;

    private static final Function[] builtinFunctions = new Function[19];

    static {
        builtinFunctions[INDEX_SIN] = new Function("sin") {
            @Override
            public double apply(double... args) {
                return Math.sin(args[0]);
            }
        };
        builtinFunctions[INDEX_COS] = new Function("cos") {
            @Override
            public double apply(double... args) {
                return Math.cos(args[0]);
            }
        };
        builtinFunctions[INDEX_TAN] = new Function("tan") {
            @Override
            public double apply(double... args) {
                return Math.tan(args[0]);
            }
        };
        builtinFunctions[INDEX_LOG] = new Function("log") {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]);
            }
        };
        builtinFunctions[INDEX_LOG1P] = new Function("log1p") {
            @Override
            public double apply(double... args) {
                return Math.log1p(args[0]);
            }
        };
        builtinFunctions[INDEX_ABS] = new Function("abs") {
            @Override
            public double apply(double... args) {
                return Math.abs(args[0]);
            }
        };
        builtinFunctions[INDEX_ACOS] = new Function("acos") {
            @Override
            public double apply(double... args) {
                return Math.acos(args[0]);
            }
        };
        builtinFunctions[INDEX_ASIN] = new Function("asin") {
            @Override
            public double apply(double... args) {
                return Math.asin(args[0]);
            }
        };
        builtinFunctions[INDEX_ATAN] = new Function("atan") {
            @Override
            public double apply(double... args) {
                return Math.atan(args[0]);
            }
        };
        builtinFunctions[INDEX_CBRT] = new Function("cbrt") {
            @Override
            public double apply(double... args) {
                return Math.cbrt(args[0]);
            }
        };
        builtinFunctions[INDEX_FLOOR] = new Function("floor") {
            @Override
            public double apply(double... args) {
                return Math.floor(args[0]);
            }
        };
        builtinFunctions[INDEX_SINH] = new Function("sinh") {
            @Override
            public double apply(double... args) {
                return Math.sinh(args[0]);
            }
        };
        builtinFunctions[INDEX_SQRT] = new Function("sqrt") {
            @Override
            public double apply(double... args) {
                return Math.sqrt(args[0]);
            }
        };
        builtinFunctions[INDEX_TANH] = new Function("tanh") {
            @Override
            public double apply(double... args) {
                return Math.tanh(args[0]);
            }
        };
        builtinFunctions[INDEX_COSH] = new Function("cosh") {
            @Override
            public double apply(double... args) {
                return Math.cosh(args[0]);
            }
        };
        builtinFunctions[INDEX_CEIL] = new Function("ceil") {
            @Override
            public double apply(double... args) {
                return Math.ceil(args[0]);
            }
        };
        builtinFunctions[INDEX_POW] = new Function("pow", 2) {
            @Override
            public double apply(double... args) {
                return Math.pow(args[0], args[1]);
            }
        };
    }

    public static Function getBuiltinFunction(final String name) {

        switch(name) {
            case "sin":
                return builtinFunctions[INDEX_SIN];
            case "cos":
                return builtinFunctions[INDEX_COS];
            case "tan":
                return builtinFunctions[INDEX_TAN];
            case "asin":
                return builtinFunctions[INDEX_ASIN];
            case "acos":
                return builtinFunctions[INDEX_ACOS];
            case "atan":
                return builtinFunctions[INDEX_ATAN];
            case "sinh":
                return builtinFunctions[INDEX_SINH];
            case "cosh":
                return builtinFunctions[INDEX_COSH];
            case "tanh":
                return builtinFunctions[INDEX_TANH];
            case "abs":
                return builtinFunctions[INDEX_ABS];
            case "log":
                return builtinFunctions[INDEX_LOG];
            case "log1p":
                return builtinFunctions[INDEX_LOG1P];
            case "ceil":
                return builtinFunctions[INDEX_CEIL];
            case "floor":
                return builtinFunctions[INDEX_FLOOR];
            case "max":
                return builtinFunctions[INDEX_MAX];
            case "min":
                return builtinFunctions[INDEX_MIN];
            case "sqrt":
                return builtinFunctions[INDEX_SQRT];
            case "cbrt":
                return builtinFunctions[INDEX_CBRT];
            case "pow":
                return builtinFunctions[INDEX_POW];
            default:
                return null;
        }
    }

}