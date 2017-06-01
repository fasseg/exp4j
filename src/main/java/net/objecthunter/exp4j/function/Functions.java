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
    
    private Functions() {}
    
    private static enum BuiltinFunctions {
        SIN(new Function("sin") {
            @Override public double apply(double... args) {
                return Math.sin(args[0]);
            }
        }),
        COS(new Function("cos") {
            @Override public double apply(double... args) {
                return Math.cos(args[0]);
            }
        }),
        TAN(new Function("tan") {
            @Override public double apply(double... args) {
                return Math.tan(args[0]);
            }
        }),
        COT(new Function("cot") {
            @Override public double apply(double... args) {
                double tan = Math.tan(args[0]);
                if (tan == 0d) {
                    throw new ArithmeticException("Division by zero in cotangent!");
                }
                return 1d/Math.tan(args[0]);
            }
        }),
        LOG(new Function("log") {
            @Override public double apply(double... args) {
                return Math.log(args[0]);
            }
        }),
        LOG2(new Function("log2") {
            @Override public double apply(double... args) {
                return Math.log(args[0]) / Math.log(2d);
            }
        }),
        LOG10(new Function("log10") {
            @Override public double apply(double... args) {
                return Math.log10(args[0]);
            }
        }),
        LOG1P(new Function("log1p") {
            @Override public double apply(double... args) {
                return Math.log1p(args[0]);
            }
        }),
        ABS(new Function("abs") {
            @Override public double apply(double... args) {
                return Math.abs(args[0]);
            }
        }),
        ACOS(new Function("acos") {
            @Override public double apply(double... args) {
                return Math.acos(args[0]);
            }
        }),
        ASIN(new Function("asin") {
            @Override public double apply(double... args) {
                return Math.asin(args[0]);
            }
        }),
        ATAN(new Function("atan") {
            @Override public double apply(double... args) {
                return Math.atan(args[0]);
            }
        }),
        CBRT(new Function("cbrt") {
            @Override public double apply(double... args) {
                return Math.cbrt(args[0]);
            }
        }),
        FLOOR(new Function("floor") {
            @Override public double apply(double... args) {
                return Math.floor(args[0]);
            }
        }),
        SINH(new Function("sinh") {
            @Override public double apply(double... args) {
                return Math.sinh(args[0]);
            }
        }),
        SQRT(new Function("sqrt") {
            @Override public double apply(double... args) {
                return Math.sqrt(args[0]);
            }
        }),
        TANH(new Function("tanh") {
            @Override public double apply(double... args) {
                return Math.tanh(args[0]);
            }
        }),
        COSH(new Function("cosh") {
            @Override public double apply(double... args) {
                return Math.cosh(args[0]);
            }
        }),
        CEIL(new Function("ceil") {
            @Override public double apply(double... args) {
                return Math.ceil(args[0]);
            }
        }),
        POW(new Function("pow", 2) {
            @Override public double apply(double... args) {
                return Math.pow(args[0], args[1]);
            }
        }),
        EXP(new Function("exp", 1) {
            @Override public double apply(double... args) {
                return Math.exp(args[0]);
            }
        }),
        EXPM1(new Function("expm1", 1) {
            @Override public double apply(double... args) {
                return Math.expm1(args[0]);
            }
        }),
        SIGNUM(new Function("signum", 1) {
            @Override public double apply(double... args) {
                if (args[0] > 0) {
                    return 1;
                } else if (args[0] < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        })
        ;
        
        private final Function function;
        
        BuiltinFunctions(final Function function) {
            this.function = function;
        }
        
        public Function get() {
            return this.function;
        }
        
    }
    
    /**
     * Get the builtin function for a given name
     * @param name the name of the function
     * @return a Function instance
     */
    public static Function getBuiltinFunction(final String name) {
        Function function = null;
        try {
            function = BuiltinFunctions.valueOf(name.toUpperCase()).get();
        } catch (final IllegalArgumentException ex) {}
        return function;
    }

}
