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
package net.objecthunter.exp4j.operator;

public class Operators {
    
    private static final int INDEX_ADDITION = 0;
    private static final int INDEX_SUBTRACTION = 1;
    private static final int INDEX_MUTLIPLICATION = 2;
    private static final int INDEX_DIVISION = 3;
    private static final int INDEX_POWER = 4;
    private static final int INDEX_MODULO = 5;
    private static final int INDEX_UNARYMINUS = 6;
    private static final int INDEX_UNARYPLUS = 7;

    private static final Operator[] BUILTIN_OPERATORS = new Operator[8];
    
    private Operators() {}

    static {
        BUILTIN_OPERATORS[INDEX_ADDITION]= new Operator("+", 2, true, Precedence.ADDITION) {
            @Override
            public double apply(final double... args) {
                return args[0] + args[1];
            }
        };
        BUILTIN_OPERATORS[INDEX_SUBTRACTION]= new Operator("-", 2, true, Precedence.ADDITION) {
            @Override
            public double apply(final double... args) {
                return args[0] - args[1];
            }
        };
        BUILTIN_OPERATORS[INDEX_UNARYMINUS]= new Operator("-", 1, false, Precedence.UNARY_MINUS) {
            @Override
            public double apply(final double... args) {
                return -args[0];
            }
        };
        BUILTIN_OPERATORS[INDEX_UNARYPLUS]= new Operator("+", 1, false, Precedence.UNARY_PLUS) {
            @Override
            public double apply(final double... args) {
                return args[0];
            }
        };
        BUILTIN_OPERATORS[INDEX_MUTLIPLICATION]= new Operator("*", 2, true, Precedence.MULTIPLICATION) {
            @Override
            public double apply(final double... args) {
                return args[0] * args[1];
            }
        };
        BUILTIN_OPERATORS[INDEX_DIVISION]= new Operator("/", 2, true, Precedence.DIVISION) {
            @Override
            public double apply(final double... args) {
                if (args[1] == 0d) {
                    throw new ArithmeticException("Division by zero!");
                }
                return args[0] / args[1];
            }
        };
        BUILTIN_OPERATORS[INDEX_POWER]= new Operator("^", 2, false, Precedence.POWER) {
            @Override
            public double apply(final double... args) {
                return Math.pow(args[0], args[1]);
            }
        };
        BUILTIN_OPERATORS[INDEX_MODULO]= new Operator("%", 2, true, Precedence.MODULO) {
            @Override
            public double apply(final double... args) {
                if (args[1] == 0d) {
                    throw new ArithmeticException("Division by zero!");
                }
                return args[0] % args[1];
            }
        };
    }

    public static Operator getBuiltinOperator(final char symbol, final int numArguments) {
        switch(symbol) {
            case '+':
                if (numArguments != 1) {
                    return BUILTIN_OPERATORS[INDEX_ADDITION];
                }else{
                    return BUILTIN_OPERATORS[INDEX_UNARYPLUS];
                }
            case '-':
                if (numArguments != 1) {
                    return BUILTIN_OPERATORS[INDEX_SUBTRACTION];
                }else{
                    return BUILTIN_OPERATORS[INDEX_UNARYMINUS];
                }
            case '*':
                return BUILTIN_OPERATORS[INDEX_MUTLIPLICATION];
            case '/':
                return BUILTIN_OPERATORS[INDEX_DIVISION];
            case '^':
                return BUILTIN_OPERATORS[INDEX_POWER];
            case '%':
                return BUILTIN_OPERATORS[INDEX_MODULO];
            default:
                return null;
        }
    }

}
