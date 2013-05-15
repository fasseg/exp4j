package net.objecthunter.exp4j.tokenizer;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import net.objecthunter.exp4j.ComplexNumber;
import net.objecthunter.exp4j.function.CustomFunction;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.CustomOperator;
import net.objecthunter.exp4j.operator.Operators;

public class Tokenizer<T> {

	private final Class<T> type;

	public Tokenizer(Class<T> type) {
		super();
		this.type = type;
	}

	public List<Token> tokenizeExpression(String expression) {
		List<Token> tokens = new LinkedList<Token>();
		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);
			/* skip blanks */
			if (Character.isWhitespace(c)) {
				continue;
			}

			if (Character.isDigit(c)) {
				boolean imaginary = false;
				StringBuilder numberString = new StringBuilder();

				/* a number */
				numberString.append(c);

				while (expression.length() > i + 1) {
					char next = expression.charAt(++i);
					if (Character.isDigit(next) || next == '.') {
						numberString.append((char) next);
					} else if (type == ComplexNumber.class && next == 'i') {
						imaginary = true;
					} else {
						--i; // go back a char or we lose something
						break;
					}
				}

				if (type == Float.class) {
					NumberToken<Float> n = new NumberToken<Float>(Float.class, Float.parseFloat(numberString.toString()));
					tokens.add(n);
				} else if (type == Double.class || type == ComplexNumber.class) {
					NumberToken<Double> n = new NumberToken<Double>(Double.class, Double.parseDouble(numberString.toString()), imaginary);
					tokens.add(n);
				} else if (type == BigDecimal.class) {
					NumberToken<BigDecimal> n = new NumberToken<BigDecimal>(BigDecimal.class, new BigDecimal(numberString.toString()));
					tokens.add(n);
				} else {
					throw new RuntimeException("Unable to handle the type " + type);
				}

			} else if (Operators.isOperator(c)) {
				/* an operator */
				OperatorToken op = new OperatorToken(Operators.getOperator(c));
				tokens.add(op);

			} else if (Character.isAlphabetic(c)) {

				/* might be a function or a variable */
				StringBuilder nameBuilder = new StringBuilder();
				nameBuilder.append(c);
				while (expression.length() > i + 1) {
					char next = expression.charAt(++i);
					if (Character.isAlphabetic(next) || Character.isDigit(next)) {
						nameBuilder.append(next);
					} else {
						--i; // step back or we might lose something
						break;
					}
				}

				/* check if a function is available by that name */
				CustomFunction func = Functions.getFunction(nameBuilder.toString());
				if (func != null) {
					tokens.add(new FunctionToken(func));
				}

			} else if (c == '(' || c == '[') {
				tokens.add(new ParanthesesToken(true));
			} else if (c == ')' || c == ']') {
				tokens.add(new ParanthesesToken(false));
			} else if (c == ','){
				tokens.add(new ArgumentSeparatorToken());
			}
		}
		return tokens;
	}
}
