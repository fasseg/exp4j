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

public abstract class Operators {
    private static final int INDEX_ADDITION = 0;
    private static final int INDEX_SUBTRACTION = 1;
    private static final int INDEX_MULTIPLICATION = 2;
    private static final int INDEX_DIVISION = 3;
    private static final int INDEX_POWER = 4;
    private static final int INDEX_MODULO = 5;
    private static final int INDEX_UNARY_MINUS = 6;
    private static final int INDEX_UNARY_PLUS = 7;

    private static final Operator[] builtinOperators = new Operator[8];

    static {
        builtinOperators[INDEX_ADDITION] = new Operator("+", 2, true, Operator.PRECEDENCE_ADDITION) {
            @Override
            public double apply(final double... args) {
                return args[0] + args[1];
            }
        };
        builtinOperators[INDEX_SUBTRACTION] = new Operator("-", 2, true, Operator.PRECEDENCE_ADDITION) {
            @Override
            public double apply(final double... args) {
                return args[0] - args[1];
            }
        };
        builtinOperators[INDEX_UNARY_MINUS] = new Operator("-", 1, false, Operator.PRECEDENCE_UNARY_MINUS) {
            @Override
            public double apply(final double... args) {
                return -args[0];
            }
        };
        builtinOperators[INDEX_UNARY_PLUS] = new Operator("+", 1, false, Operator.PRECEDENCE_UNARY_PLUS) {
            @Override
            public double apply(final double... args) {
                return args[0];
            }
        };
        builtinOperators[INDEX_MULTIPLICATION] = new Operator("*", 2, true, Operator.PRECEDENCE_MULTIPLICATION) {
            @Override
            public double apply(final double... args) {
                return args[0] * args[1];
            }
        };
        builtinOperators[INDEX_DIVISION] = new Operator("/", 2, true, Operator.PRECEDENCE_DIVISION) {
            @Override
            public double apply(final double... args) {
                if (args[1] == 0d) {
                    throw new ArithmeticException("Division by zero!");
                }
                return args[0] / args[1];
            }
        };
        builtinOperators[INDEX_POWER] = new Operator("^", 2, false, Operator.PRECEDENCE_POWER) {
            @Override
            public double apply(final double... args) {
                return Math.pow(args[0], args[1]);
            }
        };
        builtinOperators[INDEX_MODULO] = new Operator("%", 2, true, Operator.PRECEDENCE_MODULO) {
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
        switch (symbol) {
            case '+':
                if (numArguments != 1) {
                    return builtinOperators[INDEX_ADDITION];
                }

                return builtinOperators[INDEX_UNARY_PLUS];
            case '-':
                if (numArguments != 1) {
                    return builtinOperators[INDEX_SUBTRACTION];
                }

                return builtinOperators[INDEX_UNARY_MINUS];
            case '*':
                return builtinOperators[INDEX_MULTIPLICATION];
            case '÷':
            case '/':
                return builtinOperators[INDEX_DIVISION];
            case '^':
                return builtinOperators[INDEX_POWER];
            case '%':
                return builtinOperators[INDEX_MODULO];
            default:
                return null;
        }
    }

}
