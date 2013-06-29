package net.objecthunter.exp4j;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import net.objecthunter.exp4j.exceptions.UnparseableExpressionException;
import net.objecthunter.exp4j.operator.CustomOperator;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.ParanthesesToken;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.Token.Type;

public class ShuntingYard {
	public static List<Token> translateToReversePolishNotation(List<Token> tokens) throws UnparseableExpressionException{
		Stack<Token> output = new Stack<Token>();
		Stack<Token> stack = new Stack<Token>();
		for (Token t : tokens) {
			if (t.getType() == Type.NUMBER || t.getType() == Type.VARIABLE) {
				output.add(t);
			} else if (t.getType() == Type.FUNCTION) {
				stack.push(t);
			} else if (t.getType() == Type.ARGUMENT_SEPARATOR) {
				Token next;
				while (!((next = stack.peek()).getType() == Type.PARANTHESES && ((ParanthesesToken) next).isOpen())) {
					output.add(stack.pop());
				}
			} else if (t.getType() == Type.OPERATOR) {
				CustomOperator o1 = ((OperatorToken) t).getOperator();
				while (!stack.isEmpty() && stack.peek().getType() == Type.OPERATOR) {
					CustomOperator o2 = ((OperatorToken) stack.peek()).getOperator();
					if (o1.getPrecedence() < o2.getPrecedence() || (o1.isLeftAssociative() && o1.getPrecedence() <= o2.getPrecedence())) {
						output.push(stack.pop());
					} else {
						break;
					}
				}
				stack.push(t);
			} else if (t.getType() == Type.PARANTHESES) {
				ParanthesesToken par = (ParanthesesToken) t;
				/* a left para */
				if (par.isOpen()) {
					stack.push(t);
				} else {
					/* a right para */
					while (stack.peek().getType() != Type.PARANTHESES || !((ParanthesesToken) stack.peek()).isOpen()) {
						output.push(stack.pop());
					}
					stack.pop(); // pop the left parantheses
					if (!stack.isEmpty() && stack.peek().getType() == Type.FUNCTION) {
						output.push(stack.pop());
					}
				}
			}
		}
		/* empty the stack onto the output */
		while (!stack.empty()) {
			Token t = stack.pop();
			if (t.getType() == Type.PARANTHESES) {
				throw new UnparseableExpressionException("Mismatched parantheses");
			}
			output.push(t);
		}
		return output;
	}
}
