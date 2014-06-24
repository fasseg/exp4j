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

public abstract class Operator {
    public static final int PRECEDENCE_ADDITION = 500;
    public static final int PRECEDENCE_SUBTRACTION = PRECEDENCE_ADDITION;
    public static final int PRECEDENCE_MULTIPLICATION = 1000;
    public static final int PRECEDENCE_DIVISION = PRECEDENCE_MULTIPLICATION;
    public static final int PRECEDENCE_MODULO = PRECEDENCE_DIVISION;
    public static final int PRECEDENCE_POWER = 10000;
    public static final int PRECEDENCE_UNARY_MINUS = 20000;
    public static final int PRECEDENCE_UNARY_PLUS = PRECEDENCE_UNARY_MINUS;

    private final int numArgs;
    private final boolean leftAssociative;
    private final String symbol;
    private final int precedence;

    public Operator(String symbol, int numberOfArguments, boolean leftAssociative,
                    int precedence) {
        super();
        this.numArgs = numberOfArguments;
        this.leftAssociative = leftAssociative;
        this.symbol = symbol;
        this.precedence = precedence;
    }

    public boolean isLeftAssociative() {
        return leftAssociative;
    }

    public int getPrecedence() {
        return precedence;
    }

    public abstract double apply(double ... args);

    public String getSymbol() {
        return symbol;
    }

    public int getNumArgs() {
        return numArgs;
    }
}
