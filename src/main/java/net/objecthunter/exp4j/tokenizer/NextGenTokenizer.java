package net.objecthunter.exp4j.tokenizer;

import java.math.BigDecimal;
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

public class NextGenTokenizer<T> {
	private final Class<T> type;

	public NextGenTokenizer(Class<T> type) {
		super();
		this.type = type;
	}

	public List<Token> tokenizeExpression(String expression)
			throws UnparseableExpressionException {
		return this.tokenizeExpression(expression, null, null, null);
	}

	public List<Token> tokenizeExpression(
			final String expression,
			final Set<String> variables,
			final Map<String, CustomFunction> customFunctions,
			final Map<String, CustomOperator> customOperators)
			throws UnparseableExpressionException {
		return tokenizeExpression(expression, variables,
				customFunctions, customOperators, type == ComplexNumber.class);
	}

	private List<Token> tokenizeExpression(
			final String expression,
			final Set<String> variables,
			final Map<String, CustomFunction> functions,
			final Map<String, CustomOperator> operators,
			final boolean complex)
			throws UnparseableExpressionException {

		final LinkedList<Token> tokens = new LinkedList<>();

		// iterate over all the chars and parse the tokens accordingly
		final int len = expression.length();
		for (int idx = 0; idx < len;) {
			char ch = expression.charAt(idx);

			// operators
			if (isOperatorCaracter(ch)) {
				idx = tryParseOperator(expression, ch, idx + 1, tokens,
						operators);
			}
			// numbers
			else if (Character.isDigit(ch) || ch == '.') {
				if (complex) {
					idx = tryParseComplexNumber(expression, ch, idx + 1, tokens);
				} else {
					idx = tryParseRealNumber(expression, ch, idx + 1, tokens);
				}
			}
			else if (complex && ch == 'i') {
				idx = tryParseComplexNumber(expression, ch, idx + 1, tokens);
			}
			// functions and variables
			else if (Character.isAlphabetic(ch) || ch == '_') {
				idx = tryParseFunctionOrVariable(expression, ch, idx + 1,
						tokens,
						functions, variables);
			}
			// left parantheses
			else if (isLeftParantheses(ch)) {
				if (tokens.size() > 0
						&& tokens.get(tokens.size() - 1) instanceof NumberToken) {
					throw new UnparseableExpressionException(
							"Invalid parantheses usage at " + idx);
				}
				idx++;
				tokens.add(new ParanthesesToken(true));
			}
			// right parantheses
			else if (isRightParantheses(ch)) {
				idx++;
				tokens.add(new ParanthesesToken(false));
			}
			// whitespace
			else if (Character.isWhitespace(ch)) {
				idx++;
				continue;
			}
			else {
				throw new UnknownCharacterException(ch, idx + 1, expression);
			}
		}
		return tokens;
	}

	private int tryParseFunctionOrVariable(
			final String expression,
			final char ch,
			int offset,
			final LinkedList<Token> tokens,
			final Map<String, CustomFunction> functions,
			final Set<String> variables) throws UnparseableExpressionException {
		final StringBuilder nameBuilder = new StringBuilder();
		final int len = expression.length();
		nameBuilder.append(ch);
		char next = expression.charAt(offset);
		// read the whole function or variable name
		while (offset < len
				&&
				((Character.isAlphabetic(next) || Character.isDigit(next) || next == '_'))) {
			nameBuilder.append(next);
			offset++;
			next = expression.charAt(offset);
		}
		String name = nameBuilder.toString();
		// check if it's a function or a variable
		CustomFunction func = Functions.getBuiltinFunction(name);
		if (functions != null && func == null) {
			func = functions.get(name);
		}
		if (func != null) {
			// it's a function
			tokens.add(new FunctionToken(func));
		} else {
			// this might be a variable after all
			if (variables != null && variables.contains(name)) {
				tokens.add(new VariableToken(name));
			} else {
				throw new UnparseableExpressionException("Unknown symbol: "
						+ name + "");
			}
		}
		return offset;
	}

	private int tryParseComplexNumber(
			final String expression,
			final char ch,
			int offset,
			final LinkedList<Token> tokens) {

		boolean imgOnly = false;
		// first check the real part
		final StringBuilder realBuilder = new StringBuilder();
		if (ch == 'i') {
			imgOnly = true;
		} else {
			realBuilder.append(ch);
		}
		final int len = expression.length();
		if (offset < len) {
			char next = expression.charAt(offset);
			while (offset < len && (Character.isDigit(next) || next == '.')) {
				realBuilder.append(next);
				offset++;
				if (offset < len) {
					next = expression.charAt(offset);
				}
			}
		}
		if (imgOnly) {
			// a complex
			if (realBuilder.length() == 0) {
				realBuilder.append('1');
			}
			double imaginary = Double.parseDouble(realBuilder.toString());
			NumberToken<ComplexNumber> z = new NumberToken<ComplexNumber>(
					ComplexNumber.class,
					new ComplexNumber(0d, imaginary), true);
			tokens.add(z);
			return offset;
		}
		// check if there is an imaginary part coming
		int j = offset;
		// now read an arbitrary number of plus/minus and whitespace
		boolean negative = false;
		boolean operatorEncountered = false;
		while (j < len) {
			char next = expression.charAt(j);
			if (next == '+') {
				operatorEncountered = true;
			} else if (next == '-') {
				operatorEncountered = true;
				negative = !negative;
			} else if (!Character.isWhitespace(next)) {
				break;
			}
			j++;
		}
		if (!operatorEncountered) {
			// can't be complex so return with the original offset
			NumberToken<ComplexNumber> r = new NumberToken<ComplexNumber>(
					ComplexNumber.class,
					new ComplexNumber(
							Double.parseDouble(realBuilder.toString()),
							0d), false);
			tokens.add(r);
			return offset;
		}
		// read a number string and check that whether it contains an 'i'
		final StringBuilder imgBuilder = new StringBuilder();
		boolean imgPart = false;
		while (j < len) {
			char next = expression.charAt(j);
			if (next == 'i') {
				imgPart = true;
			} else if (Character.isDigit(next) || next == '.') {
				imgBuilder.append(next);
			} else {
				break;
			}
			j++;
		}
		if (imgPart) {
			// a complex
			if (imgBuilder.length() == 0) {
				imgBuilder.append('1');
			}
			double imaginary = Double.parseDouble(
					(negative ? '-' + imgBuilder.toString() : imgBuilder
							.toString()));
			NumberToken<ComplexNumber> z = new NumberToken<ComplexNumber>(
					ComplexNumber.class,
					new ComplexNumber(
							Double.parseDouble(realBuilder.toString()),
							imaginary), imaginary != 0d);
			tokens.add(z);
			offset = j;
		} else {
			// a real
			NumberToken<ComplexNumber> r = new NumberToken<ComplexNumber>(
					ComplexNumber.class,
					new ComplexNumber(
							Double.parseDouble(realBuilder.toString()),
							0d), false);
			tokens.add(r);
		}
		return offset;
	}

	private int tryParseRealNumber(
			final String expression,
			final char ch,
			int offset,
			final LinkedList<Token> tokens) {

		final StringBuilder numberBuilder = new StringBuilder();
		numberBuilder.append(ch);
		final int len = expression.length();
		if (offset < len) {
			char next = expression.charAt(offset);
			while (offset < len && (Character.isDigit(next) || next == '.')) {
				numberBuilder.append(next);
				offset++;
				if (offset < len) {
					next = expression.charAt(offset);
				}
			}
		}
		if (type == Float.class) {
			NumberToken<Float> n = new NumberToken<Float>(Float.class,
					Float.parseFloat(numberBuilder.toString()));
			tokens.add(n);
		} else if (type == Double.class || type == ComplexNumber.class) {
			NumberToken<Double> n = new NumberToken<Double>(Double.class,
					Double.parseDouble(numberBuilder.toString()), false);
			tokens.add(n);
		} else if (type == BigDecimal.class) {
			NumberToken<BigDecimal> n = new NumberToken<BigDecimal>(
					BigDecimal.class, new BigDecimal(numberBuilder.toString()));
			tokens.add(n);
		} else {
			throw new RuntimeException("Unable to handle the type " + type);
		}
		return offset;
	}

	private int tryParseOperator(
			final String expression,
			final char ch,
			final int offset,
			final LinkedList<Token> tokens,
			final Map<String, CustomOperator> operators)
			throws UnparseableExpressionException {

		Token last;
		if (!tokens.isEmpty()) {
			last = tokens.getLast();
			if (last.getType() == Type.OPERATOR ||
					(last.getType() == Type.PARANTHESES &&
					((ParanthesesToken) last).isOpen())) {
				// a unary operator since it follows another operator or a left paranthesis
				return this.parseUnaryOperator(expression, ch, offset, tokens,
						operators);
			} else {
				// a binary operation since it follows a number or a right paranthesis
				return this.parseBinaryOperator(expression, ch, offset, tokens,
						operators);
			}
		} else {
			// must be an unary operator or an error
			// since it's the first token
			return this
					.parseUnaryOperator(expression, ch, offset, tokens,
							operators);
		}
	}

	private int parseBinaryOperator(
			final String expression,
			final char ch,
			int offset,
			final LinkedList<Token> tokens,
			final Map<String, CustomOperator> operators)
			throws UnparseableExpressionException {
		final StringBuilder opBuilder = new StringBuilder();
		opBuilder.append(ch);
		final int len = expression.length();
		char next = expression.charAt(offset);
		// read the operator chars in a greedy fashion as long as they match an allowed Operator and are a valid
		// operator symbol
		while (offset < len
				&& isOperatorSubstring(opBuilder.toString() + next, operators)) {
			opBuilder.append(next);
			offset++;
			next = expression.charAt(offset);
		}
		final String opName = opBuilder.toString();
		if (opName.length() == 1
				&& Operators.isBuiltinOperatorSymbol(opName.charAt(0))) {
			tokens.add(new OperatorToken(Operators.getBuiltinOperator(opName
					.charAt(0))));
		} else {
			if (operators != null) {
				CustomOperator op = operators.get(opName);
				if (op.getArgumentCount() != 2) {
					throw new UnparseableExpressionException(
							"Unknown binary operator: "
									+ opName + "");
				}
				if (op == null) {
					throw new UnparseableExpressionException(
							"Unknown binary operator: "
									+ opName + "");
				}
				tokens.add(new OperatorToken(op));
			} else {
				throw new UnparseableExpressionException(
						"Unknown binary operator: "
								+ opName + "");
			}
		}
		return offset;
	}

	private int parseUnaryOperator(
			final String expression,
			final char ch,
			int offset,
			final LinkedList<Token> tokens,
			final Map<String, CustomOperator> operators)
			throws UnparseableExpressionException {
		final StringBuilder opBuilder = new StringBuilder();
		opBuilder.append(ch);
		final int len = expression.length();
		char next = expression.charAt(offset);
		// read the operator chars in a greedy fashion as long as they match an allowed Operator and are a valid
		// operator symbol
		while (offset < len
				&& isOperatorSubstring(opBuilder.toString() + next, operators)) {
			opBuilder.append(next);
			offset++;
			if (offset < len) {
				next = expression.charAt(offset);
			}
		}
		final String opName = opBuilder.toString();
		if (opName.equals("-")) {
			// this is a builtin unary minus operator
			tokens.add(new OperatorToken(Operators.getUnaryMinusOperator()));
		} else if (!opName.equals("+")) {
			// this might be a user supplied custom operator
			CustomOperator op = operators.get(opName);
			if (op.getArgumentCount() != 1) {
				throw new UnparseableExpressionException(
						"Unknown unary operator: "
								+ opName + "");
			}
			if (op == null) {
				throw new UnparseableExpressionException("Unknown operator: "
						+ opName + "");
			}
			tokens.add(new OperatorToken(op));
		}
		return offset;
	}

	private boolean isOperatorSubstring(final String prefix,
			final Map<String, CustomOperator> operators) {
		if (prefix.length() == 1
				&& Operators.isBuiltinOperatorSymbol(prefix.charAt(0))) {
			return true;
		}
		if (operators == null) {
			return false;
		}
		for (String opName : operators.keySet()) {
			if (opName.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	private boolean isRightParantheses(final char ch) {
		return ch == ')' || ch == ']' || ch == '}';
	}

	private boolean isLeftParantheses(final char ch) {
		return ch == '(' || ch == '[' || ch == '{';
	}

	private boolean isOperatorCaracter(final char ch) {
		return Operators.isAllowedOperatorSymbol(ch);
	}
}
