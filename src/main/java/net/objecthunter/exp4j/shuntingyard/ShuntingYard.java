
package net.objecthunter.exp4j.shuntingyard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import net.objecthunter.exp4j.Exp4jException;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.Tokenizer;

/**
 * Created by ruckus on 30.06.14.
 */
public class ShuntingYard {

    public static Token[] convertToRPN(final String expression, final Map<String, Function> userFunctions,
            final Map<String, Operator> userOperators) throws Exp4jException {
        final Stack<Token> stack = new Stack<>();
        final List<Token> output = new ArrayList<>();

        final Tokenizer tokenizer = new Tokenizer(expression.toCharArray(), userFunctions, userOperators);
        while (tokenizer.hasNext()) {
            Token token = tokenizer.nextToken();
            switch (token.getType()) {
            case Token.TOKEN_NUMBER:
            case Token.TOKEN_VARIABLE:
                output.add(token);
                break;
            case Token.TOKEN_FUNCTION:
                stack.add(token);
                break;
            case Token.TOKEN_SEPARATOR:
                while (stack.peek().getType() != Token.TOKEN_PARANTHESES_OPEN) {
                    output.add(stack.pop());
                }
                break;
            case Token.TOKEN_OPERATOR:
                while (!stack.empty() && stack.peek().getType() == Token.TOKEN_OPERATOR) {
                    OperatorToken o1 = (OperatorToken) token;
                    OperatorToken o2 = (OperatorToken) stack.peek();
                    if ((o1.getOperator().isLeftAssociative() && o1.getOperator().getPrecedence() <= o2.getOperator()
                            .getPrecedence())
                            ||
                            (o1.getOperator().getPrecedence() < o2.getOperator().getPrecedence())) {
                        output.add(stack.pop());
                    }
                }
                stack.push(token);
                break;
            case Token.TOKEN_PARANTHESES_OPEN:
                stack.push(token);
                break;
            case Token.TOKEN_PARANTHESES_CLOSE:
                while (stack.peek().getType() != Token.TOKEN_PARANTHESES_OPEN) {
                    output.add(stack.pop());
                }
                stack.pop();
                if (stack.peek().getType() == Token.TOKEN_FUNCTION) {
                    output.add(stack.pop());
                }
                break;
            default:
                throw new Exp4jException("Unknown Token type encountered. This should not happen");
            }
        }
        while (!stack.empty()) {
            Token t = stack.pop();
            if (t.getType() == Token.TOKEN_PARANTHESES_CLOSE || t.getType() == Token.TOKEN_PARANTHESES_OPEN) {
                throw new Exp4jException("Mistmatched parantheses detected. Please check the expression");
            } else {
                output.add(t);
            }
        }
        return (Token[]) output.toArray(new Token[output.size()]);
    }
}
