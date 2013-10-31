package net.objecthunter.exp4j.calculable;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import net.objecthunter.exp4j.tokenizer.FunctionToken;
import net.objecthunter.exp4j.tokenizer.NumberToken;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.ParanthesesToken;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.Token.Type;
import net.objecthunter.exp4j.tokenizer.VariableToken;

public class FloatCalculable extends Calculable<Float> {

	public FloatCalculable(final List<Token> tokens) {
		super(tokens);
	}

}
