package net.objecthunter.exp4j;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import net.objecthunter.exp4j.tokenizer.Tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.Tokenizer.ParanthesesToken;
import net.objecthunter.exp4j.tokenizer.Tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.Tokenizer.Token.Type;

public class ShuntingYard {
	public static List<Token> translateToReversePolishNotation(List<Token> tokens){
		List<Token> output = new LinkedList<Token>();
		Deque<Token> stack = new LinkedList<Token>();
		for (Token t : tokens){
			if (t.getType() == Type.NUMBER){
				output.add(t);
			}else if (t.getType() == Type.FUNCTION){
				output.add(t);
			}else if (t.getType() == Type.ARGUMENT_SEPARATOR){
				Token next;
				while (!((next = stack.peek()).getType() == Type.PARANTHESES && ((ParanthesesToken)next).isOpen())){
					output.add(stack.pop());
				}
			}else if (t.getType() == Type.OPERATOR){
			}
		}
		return output;
	}
}
