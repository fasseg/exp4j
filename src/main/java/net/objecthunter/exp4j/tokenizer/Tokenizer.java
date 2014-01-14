package net.objecthunter.exp4j.tokenizer;

import java.math.BigDecimal;
import java.math.MathContext;
import java.security.acl.LastOwnerException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.objecthunter.exp4j.ComplexNumber;
import net.objecthunter.exp4j.exceptions.UnknownCharacterException;
import net.objecthunter.exp4j.exceptions.UnparseableExpressionException;
import net.objecthunter.exp4j.function.CustomFunction;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.CustomOperator;
import net.objecthunter.exp4j.operator.Operators;
import net.objecthunter.exp4j.tokenizer.Token.Type;

public class Tokenizer<T> {

	private final Class<T> type;

	public Tokenizer(Class<T> type) {
		super();
		this.type = type;
	}

	public List<Token> tokenizeExpression(String expression) throws UnparseableExpressionException {
		return this.tokenizeExpression(expression, null, null, null);
	}

	public List<Token> tokenizeExpression(String expression, Set<String> variables, Map<String, CustomFunction> customFunctions, Map<String, CustomOperator> customOperators)
			throws UnparseableExpressionException {
		if (type == ComplexNumber.class) {
			return tokenizeComplexExpression(expression, variables, customFunctions, customOperators);
		} else {
			return tokenizeRealExpression(expression, variables, customFunctions, customOperators);
		}
	}

	private List<Token> tokenizeRealExpression(String expression, Set<String> variables, Map<String, CustomFunction> customFunctions, Map<String, CustomOperator> customOperators)
			throws UnparseableExpressionException {
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

			} else if (isOperatorCaracter(c, customOperators)) {
				/* an operator */
				OperatorToken op;
				/* check for the unary minus/plus */
				if (c == '-' || c == '+') {
					if (tokens.isEmpty()) {
						/* unary op */
						if (c == '-') {
							/* just skip unary plus signs */
							tokens.add(new OperatorToken(Operators.getUnaryMinusOperator()));
						}
					} else {
						Token last = tokens.get(tokens.size() - 1);
						if (last.getType() == Type.OPERATOR || (last.getType() == Type.PARANTHESES && ((ParanthesesToken) last).isOpen())) {
							/* unary op */
							if (c == '-') {
								/* just skip unary plus signs */
								tokens.add(new OperatorToken(Operators.getUnaryMinusOperator()));
							}
						} else {
							/* binary op */
							CustomOperator o = Operators.getBuiltinOperator(c);
							if (o == null) {
								/* can be a custom operator */
								o = customOperators.get(String.valueOf(c));
							}
							if (o == null) {
								throw new RuntimeException("Unknown operator " + o.getSymbol());
							}
							op = new OperatorToken(o);
							tokens.add(op);
						}
					}
				} else {
					/* might be a custom operator with more than one symbol */
					final StringBuilder nameBuilder = new StringBuilder();
					nameBuilder.append(c);
					while (expression.length() > i + 1) {
						char next = expression.charAt(++i);
						if (Operators.isAllowedOperatorSymbol(next)) {
							nameBuilder.append(next);
						} else {
							--i;
							break;
						}
					}

					/* binary op */
					CustomOperator o = Operators.getBuiltinOperator(nameBuilder.toString());

					if (o == null) {
						/* can be a custom operator */
						o = customOperators.get(nameBuilder.toString());
						if (o == null) {
							throw new RuntimeException("Unknown operator '" + nameBuilder.toString() + "'");
						}
					}

					op = new OperatorToken(o);
					tokens.add(op);
				}

			} else if (Character.isAlphabetic(c) || c == '_') {

				/* might be a function or a variable */
				StringBuilder nameBuilder = new StringBuilder();
				nameBuilder.append(c);
				while (expression.length() > i + 1) {
					char next = expression.charAt(++i);
					if (Character.isAlphabetic(next) || Character.isDigit(next) || next == '_') {
						nameBuilder.append(next);
					} else {
						--i; // step back or we might lose something
						break;
					}
				}

				/* check if a function is available by that name */
				CustomFunction func = Functions.getBuiltinFunction(nameBuilder.toString());
				if (func != null) {
					if (tokens.size() > 0 && tokens.get(tokens.size() - 1) instanceof NumberToken) {
						throw new UnparseableExpressionException("Invalid function usage at position " + i);
					}
					tokens.add(new FunctionToken(func));
				} else {
					/* might be a custom function */
					func = customFunctions.get(nameBuilder.toString());
					if (func != null) {
						tokens.add(new FunctionToken(func));
						/* might be a variable */
					} else if (variables.contains(nameBuilder.toString())) {
						if (tokens.size() > 0 && tokens.get(tokens.size() - 1) instanceof NumberToken) {
							throw new UnparseableExpressionException("Invalid variable usage for '" + nameBuilder.toString() + "'");
						}
						tokens.add(new VariableToken(nameBuilder.toString()));
					} else {
						throw new UnparseableExpressionException("Unable to parse name '" + nameBuilder.toString() + "' in expression '" + expression + "'");
					}
				}

			} else if (c == '(' || c == '[' || c == '{') {
				if (tokens.size() > 0 && tokens.get(tokens.size() - 1) instanceof NumberToken) {
					throw new UnparseableExpressionException("Invalid parantheses usage at " + i);
				}
				tokens.add(new ParanthesesToken(true));
			} else if (c == ')' || c == ']' || c == '}') {
				tokens.add(new ParanthesesToken(false));
			} else if (c == ',') {
				tokens.add(new ArgumentSeparatorToken());
			} else {
				throw new UnknownCharacterException(c, i, expression);
			}
		}
		return tokens;
	}

	private List<Token> tokenizeComplexExpression(String expression, Set<String> variables, Map<String, CustomFunction> customFunctions, Map<String, CustomOperator> customOperators)
			throws UnparseableExpressionException {
		final int expressionLength = expression.length();
		List<Token> tokens = new LinkedList<Token>();
		for (int i = 0; i < expressionLength; i++) {
			char c = expression.charAt(i);
			/* skip blanks */
			if (Character.isWhitespace(c)) {
				continue;
			}

			if (Character.isDigit(c)) {
				boolean imaginary = false;
				StringBuilder numberString = new StringBuilder();
				StringBuilder imgBuilder = new StringBuilder();

				/* a number */
				numberString.append(c);

				while (expressionLength > i + 1) {
					char next = expression.charAt(++i);
					if (Character.isDigit(next) || next == '.') {
						numberString.append((char) next);
					} else{
						while (expressionLength > i+1 && Character.isWhitespace(next)) {
							next = expression.charAt(++i);
						}
						if (next == '+' || next == '-') {
							boolean negative = false;
							int tmpIndex = i + 1;
							char tmp = expression.charAt(tmpIndex);
							if (next == '-') {
								negative = true;
							}
							while (expressionLength > tmpIndex + 1 && Character.isWhitespace(tmp)) {
								tmp = expression.charAt(++tmpIndex);
							}
							/* check for unary operations */
							while (expressionLength > tmpIndex + 1 && (Character.isWhitespace(tmp) || tmp == '+' || tmp == '-')) {
								if (tmp == '-') {
									negative = !negative;
								}
								tmp = expression.charAt(++tmpIndex);
							}
							if (negative) {
								imgBuilder.append('-');
							}
							/* check if there's an imaginary part coming */
							while (expressionLength > ++tmpIndex && (Character.isDigit(tmp) || tmp == '.')) {
								imgBuilder.append(tmp);
								tmp=expression.charAt(tmpIndex);
							}
							if (tmp == 'i') {
								imaginary = true;
								i = tmpIndex-1;
							}else {
								--i;
								break;
							}
						} else {
							--i; // go back a char or we lose something
							break;
						}
				    }
						
				}

				if (imaginary) {
					Token lastToken = tokens.size() > 0 ? tokens.get(tokens.size() - 1) : null;
					double real = Double.parseDouble(numberString.toString());
					double img = Double.parseDouble(imgBuilder.toString());
					NumberToken<ComplexNumber> n = new NumberToken<>(
							ComplexNumber.class, 
							new ComplexNumber(real, img),
							img != 0);
					tokens.add(n);
				} else {
					NumberToken<ComplexNumber> n = new NumberToken<ComplexNumber>(ComplexNumber.class, new ComplexNumber(Double.parseDouble(numberString.toString()), 0d));
					tokens.add(n);
				}

			} else if (isOperatorCaracter(c, customOperators)) {
				/* an operator */
				OperatorToken op;
				/* check for the unary minus/plus */
				if (c == '-' || c == '+') {
					if (tokens.isEmpty()) {
						/* unary op */
						if (c == '-') {
							/* just skip unary plus signs */
							tokens.add(new OperatorToken(Operators.getUnaryMinusOperator()));
						}
					} else {
						Token last = tokens.get(tokens.size() - 1);
						if (last.getType() == Type.OPERATOR || (last.getType() == Type.PARANTHESES && ((ParanthesesToken) last).isOpen())) {
							/* unary op */
							if (c == '-') {
								/* just skip unary plus signs */
								tokens.add(new OperatorToken(Operators.getUnaryMinusOperator()));
							}
						} else {
							/* binary op */
							CustomOperator o = Operators.getBuiltinOperator(c);
							if (o == null) {
								/* can be a custom operator */
								o = customOperators.get(String.valueOf(c));
							}
							if (o == null) {
								throw new RuntimeException("Unknown operator " + o.getSymbol());
							}
							op = new OperatorToken(o);
							tokens.add(op);
						}
					}
				} else {
					/* might be a custom operator with more than one symbol */
					final StringBuilder nameBuilder = new StringBuilder();
					nameBuilder.append(c);
					while (expression.length() > i + 1) {
						char next = expression.charAt(++i);
						if (Operators.isAllowedOperatorSymbol(next)) {
							nameBuilder.append(next);
						} else {
							--i;
							break;
						}
					}

					/* binary op */
					CustomOperator o = Operators.getBuiltinOperator(nameBuilder.toString());

					if (o == null) {
						/* can be a custom operator */
						o = customOperators.get(nameBuilder.toString());
						if (o == null) {
							throw new RuntimeException("Unknown operator '" + nameBuilder.toString() + "'");
						}
					}

					op = new OperatorToken(o);
					tokens.add(op);
				}

			} else if (Character.isAlphabetic(c) || c == '_') {

				/* might be a function or a variable */
				StringBuilder nameBuilder = new StringBuilder();
				nameBuilder.append(c);
				while (expression.length() > i + 1) {
					char next = expression.charAt(++i);
					if (Character.isAlphabetic(next) || Character.isDigit(next) || next == '_') {
						nameBuilder.append(next);
					} else {
						--i; // step back or we might lose something
						break;
					}
				}

				/* check if a function is available by that name */
				CustomFunction func = Functions.getBuiltinFunction(nameBuilder.toString());
				if (func != null) {
					if (tokens.size() > 0 && tokens.get(tokens.size() - 1) instanceof NumberToken) {
						throw new UnparseableExpressionException("Invalid function usage at position " + i);
					}
					tokens.add(new FunctionToken(func));
				} else {
					/* might be a custom function */
					func = customFunctions.get(nameBuilder.toString());
					if (func != null) {
						tokens.add(new FunctionToken(func));
						/* might be a variable */
					} else if (variables.contains(nameBuilder.toString())) {
						if (tokens.size() > 0 && tokens.get(tokens.size() - 1) instanceof NumberToken) {
							throw new UnparseableExpressionException("Invalid variable usage for '" + nameBuilder.toString() + "'");
						}
						tokens.add(new VariableToken(nameBuilder.toString()));
					} else if (nameBuilder.toString().equals("i")){
						tokens.add(new NumberToken<ComplexNumber>(ComplexNumber.class, new ComplexNumber(0d, 1d)));
					} else {
						throw new UnparseableExpressionException("Unable to parse name '" + nameBuilder.toString() + "' in expression '" + expression + "'");
					}
				}

			} else if (c == '(' || c == '[' || c == '{') {
				if (tokens.size() > 0 && tokens.get(tokens.size() - 1) instanceof NumberToken) {
					throw new UnparseableExpressionException("Invalid parantheses usage at " + i);
				}
				tokens.add(new ParanthesesToken(true));
			} else if (c == ')' || c == ']' || c == '}') {
				tokens.add(new ParanthesesToken(false));
			} else if (c == ',') {
				tokens.add(new ArgumentSeparatorToken());
			} else {
				throw new UnknownCharacterException(c, i, expression);
			}
		}
		return tokens;
	}

	private boolean isOperatorCaracter(char c, Map<String, CustomOperator> operators) {
		if (Operators.isBuiltinOperatorSymbol(c)) {
			return true;
		} else if (operators != null) {
			for (String key : operators.keySet()) {
				if (key.startsWith(String.valueOf(c))) {
					return true;
				}
			}
		}
		return false;
	}
}
