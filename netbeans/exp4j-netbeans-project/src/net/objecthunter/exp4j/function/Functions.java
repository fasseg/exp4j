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

    private enum BuiltFunc {
        SIN(0, "sin"),
        COS(1, "cos"),
        TAN(2, "tan"),
        LOG(3, "log"),
        LOG1P(4, "log1p"),
        ABS(5, "abs"),
        ACOS(6, "acos"),
        ASIN(7, "asin"),
        ATAN(8, "atan"),
        CBRT(9, "cbrt"),
        CEIL(10, "ceil"),
        FLOOR(11, "floor"),
        SINH(12, "sinh"),
        SQRT(13, "sqrt"),
        TANH(14, "tanh"),
        COSH(15, "cosh"),
        POW(16, "pow"),
        EXP(17, "exp"),
        EXPM1(18, "expm1"),
        LOG10(19, "log10"),
        LOG2(20, "log2");
        
        private int funcIndex;
        private String funcName;
        
        BuiltFunc(int funcIndex, String funcName) {
            this.funcIndex = funcIndex;
            this.funcName = funcName;
        }

        public int getFuncIndex() {
            return funcIndex;
        }

        public String getFuncName() {
            return funcName;
        }
        
    }

    private static final Function[] builtinFunctions = new Function[21];

    static {
        builtinFunctions[BuiltFunc.SIN.getFuncIndex()] = new Function(BuiltFunc.SIN.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.sin(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.COS.getFuncIndex()] = new Function(BuiltFunc.COS.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.cos(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.TAN.getFuncIndex()] = new Function(BuiltFunc.TAN.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.tan(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.LOG.getFuncIndex()] = new Function(BuiltFunc.LOG.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.log(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.LOG2.getFuncIndex()] = new Function(BuiltFunc.LOG2.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.log(args[0]) / Math.log(2d);
            }
        };
        builtinFunctions[BuiltFunc.LOG10.getFuncIndex()] = new Function(BuiltFunc.LOG10.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.log10(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.LOG1P.getFuncIndex()] = new Function(BuiltFunc.LOG1P.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.log1p(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.ABS.getFuncIndex()] = new Function(BuiltFunc.ABS.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.abs(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.ACOS.getFuncIndex()] = new Function(BuiltFunc.ACOS.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.acos(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.ASIN.getFuncIndex()] = new Function(BuiltFunc.ASIN.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.asin(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.ATAN.getFuncIndex()] = new Function(BuiltFunc.ATAN.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.atan(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.CBRT.getFuncIndex()] = new Function(BuiltFunc.CBRT.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.cbrt(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.FLOOR.getFuncIndex()] = new Function(BuiltFunc.FLOOR.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.floor(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.SINH.getFuncIndex()] = new Function(BuiltFunc.SINH.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.sinh(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.SQRT.getFuncIndex()] = new Function(BuiltFunc.SQRT.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.sqrt(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.TANH.getFuncIndex()] = new Function(BuiltFunc.TANH.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.tanh(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.COSH.getFuncIndex()] = new Function(BuiltFunc.COSH.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.cosh(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.CEIL.getFuncIndex()] = new Function(BuiltFunc.CEIL.getFuncName()) {
            @Override
            public double apply(double ... args) {
                return Math.ceil(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.POW.getFuncIndex()] = new Function(BuiltFunc.POW.getFuncName(), 2) {
            @Override
            public double apply(double ... args) {
                return Math.pow(args[0], args[1]);
            }
        };
        builtinFunctions[BuiltFunc.EXP.getFuncIndex()] = new Function(BuiltFunc.EXP.getFuncName(), 1) {
            @Override
            public double apply(double ... args) {
                return Math.exp(args[0]);
            }
        };
        builtinFunctions[BuiltFunc.EXPM1.getFuncIndex()] = new Function(BuiltFunc.EXPM1.getFuncName(), 1) {
            @Override
            public double apply(double... args) {
                return Math.expm1(args[0]);
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
            return builtinFunctions[BuiltFunc.SIN.getFuncIndex()];
        } else if (name.equals("cos")) {
            return builtinFunctions[BuiltFunc.COS.getFuncIndex()];
        } else if (name.equals("tan")) {
            // return builtinFunctions[INDEX_TAN];
            return builtinFunctions[BuiltFunc.TAN.getFuncIndex()];
        } else if (name.equals("asin")) {
            //return builtinFunctions[INDEX_ASIN];
            return builtinFunctions[BuiltFunc.ASIN.getFuncIndex()];
        } else if (name.equals("acos")) {
            //return builtinFunctions[INDEX_ACOS];
            return builtinFunctions[BuiltFunc.ACOS.getFuncIndex()];
        } else if (name.equals("atan")) {
            //return builtinFunctions[INDEX_ATAN];
            return builtinFunctions[BuiltFunc.ATAN.getFuncIndex()];
        } else if (name.equals("sinh")) {
            // return builtinFunctions[INDEX_SINH];
            return builtinFunctions[BuiltFunc.SINH.getFuncIndex()];
        } else if (name.equals("cosh")) {
            // return builtinFunctions[INDEX_COSH];
            return builtinFunctions[BuiltFunc.COSH.getFuncIndex()];
        } else if (name.equals("tanh")) {
            // return builtinFunctions[INDEX_TANH];
            return builtinFunctions[BuiltFunc.TANH.getFuncIndex()];
        } else if (name.equals("abs")) {
            // return builtinFunctions[INDEX_ABS];
            return builtinFunctions[BuiltFunc.ABS.getFuncIndex()];
        } else if (name.equals("log")) {
            // return builtinFunctions[INDEX_LOG];
            return builtinFunctions[BuiltFunc.LOG.getFuncIndex()];
        } else if (name.equals("log10")) {
            // return builtinFunctions[INDEX_LOG10];
            return builtinFunctions[BuiltFunc.LOG10.getFuncIndex()];
        } else if (name.equals("log2")) {
            // return builtinFunctions[INDEX_LOG2];
            return builtinFunctions[BuiltFunc.LOG2.getFuncIndex()];
        } else if (name.equals("log1p")) {
            // return builtinFunctions[INDEX_LOG1P];
            return builtinFunctions[BuiltFunc.LOG1P.getFuncIndex()];
        } else if (name.equals("ceil")) {
            // return builtinFunctions[INDEX_CEIL];
            return builtinFunctions[BuiltFunc.CEIL.getFuncIndex()];
        } else if (name.equals("floor")) {
            // return builtinFunctions[INDEX_FLOOR];
            return builtinFunctions[BuiltFunc.FLOOR.getFuncIndex()];
        } else if (name.equals("sqrt")) {
            // return builtinFunctions[INDEX_SQRT];
            return builtinFunctions[BuiltFunc.SQRT.getFuncIndex()];
        } else if (name.equals("cbrt")) {
            // return builtinFunctions[INDEX_CBRT];
            return builtinFunctions[BuiltFunc.CBRT.getFuncIndex()];
        } else if (name.equals("pow")) {
            // return builtinFunctions[INDEX_POW];
            return builtinFunctions[BuiltFunc.POW.getFuncIndex()];
        } else if (name.equals("exp")) {
            // return builtinFunctions[INDEX_EXP];
            return builtinFunctions[BuiltFunc.EXP.getFuncIndex()];
        } else if (name.equals("expm1")) {
            // return builtinFunctions[INDEX_EXPM1];
            return builtinFunctions[BuiltFunc.EXPM1.getFuncIndex()];
        } else {
            return null;
        }
    }

}
