/* 
 * Copyright 2015 Frank Asseg
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
package net.objecthunter.exp4j.optimizer;


import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokenizer.NumberToken;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.Token;

import java.util.Arrays;
import java.util.Stack;

/**
 * Optimize an expression for faster evaluation.
 * Be aware that optimizing an expression means sweeping over the input an additional time and therefore increasing the
 * time complexity.
 *
 * (1) Optimize simple operations:
 * This optimization checks for the occurence of simple operation like addition or multiplication with non variables
 * and substitutes the two NumberToken and a OperatorToken by a single NumberToken
 *
 * This optimization was suggested and first implemented by Federico Vera
 */
public class Optimizer {
    private final boolean optimizeSimpleOperations;

    public Optimizer(boolean optimizeSimpleOperations) {
        this.optimizeSimpleOperations = optimizeSimpleOperations;
    }

    /**
     * Optimize a set of tokens
     * @param tokens the set of tokens to optimize
     * @return the optimized set
     */
    public static Token[] optimize(Token[] tokens) {
        final Token[] output = new Token[tokens.length];
        int idx = 0;

        for (Token t : tokens) {
            switch (t.getType()) {
                case Token.TOKEN_OPERATOR:
                    final OperatorToken opToken = (OperatorToken) t;
                    final Operator op = opToken.getOperator();
                    if (output.length < op.getNumOperands()) {
                        throw new IllegalArgumentException("Invalid number of operands available");
                    }
                    if (op.getNumOperands() == 2) {
                        final Token rightOperand = output[--idx];
                        final Token leftOperand = output[--idx];
                        if (rightOperand.getType() == Token.TOKEN_NUMBER && leftOperand.getType() == Token.TOKEN_NUMBER) {
                            /* just replace the three tokens with a single NumberToken */
                            double leftVal = ((NumberToken) leftOperand).getValue();
                            double rightVal = ((NumberToken) rightOperand).getValue();
                            output[idx++] = new NumberToken(op.apply(leftVal, rightVal));
                        } else {
                            /* reset the index to the position after the operands */
                            idx += 2;
                            /* add the original operator token to the output */
                            output[idx++] = t;
                        }
                    } else if (op.getNumOperands() == 1) {
                        final Token operand = output[--idx];
                        if (operand.getType() == Token.TOKEN_NUMBER) {
                            /* just replace the two tokens with a single NumberToken */
                            double val = ((NumberToken) operand).getValue();
                            output[idx++] = new NumberToken(op.apply(val));
                        } else {
                            /* reset the index to the position after the operands */
                            idx += 1;
                            /* add the original operator token to the output */
                            output[idx++] = t;
                        }
                    } else {
                        /* can't optimize, the number of operands seems funky, since it's not 2 or 1, so just add the
                        token w/o optimization*/
                        output[idx++] = t;
                    }
                    break;
                default:
                    output[idx++]=t;
            }
        }
        return Arrays.copyOf(output, idx);
    }
}
