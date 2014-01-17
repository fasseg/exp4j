package net.objecthunter.exp4j.operator;

import java.util.HashMap;
import java.util.Map;

import net.objecthunter.exp4j.exception.UnparseableExpressionException;

public class Operators {

	public static final int PRECEDENCE_ADDITION = 500;
	public static final int PRECEDENCE_SUBTRACTION = PRECEDENCE_ADDITION;
	public static final int PRECEDENCE_MULTIPLICATION = 1000;
	public static final int PRECEDENCE_DIVISION = PRECEDENCE_MULTIPLICATION;
	public static final int PRECEDENCE_MODULO = PRECEDENCE_DIVISION;
	public static final int PRECEDENCE_POWER = 10000;
	public static final int PRECEDENCE_UNARY_MINUS = 20000;

	public static final int INDEX_ADDITION = 0;
	public static final int INDEX_SUBTRACTION = 1;
	public static final int INDEX_MUTLIPLICATION = 2;
	public static final int INDEX_DIVISION = 3;
	public static final int INDEX_POWER = 4;
	public static final int INDEX_MODULO = 5;
	public static final int INDEX_UNARYMINUS = 6;

	private static final char[] ALLOWED_OPERATOR_CHARS = { '+', '-', '*', '/',
			'%', '^', '!', '#', '§', '$', '&', ';', ':', '~', '<', '>', '|',
			'=' };
	private static final Operator[] builtin = new Operator[7];

	static {
		builtin[INDEX_ADDITION] = new Operator("+", 2, true,
				PRECEDENCE_ADDITION) {
			@Override
			public double apply(double... args) {
				return args[0] + args[1];
			}
		};
		builtin[INDEX_SUBTRACTION] = new Operator("-", 2, true,
				PRECEDENCE_SUBTRACTION) {
			@Override
			public double apply(double... args) {
				return args[0] - args[1];
			}
		};
		builtin[INDEX_MUTLIPLICATION] = new Operator("*", 2, true,
				PRECEDENCE_MULTIPLICATION) {
			@Override
			public double apply(double... args) {
				return args[0] * args[1];
			}
		};
		builtin[INDEX_DIVISION] = new Operator("/", 2, true,
				PRECEDENCE_DIVISION) {
			@Override
			public double apply(double... args) {
				return args[0] / args[1];
			}
		};
		builtin[INDEX_POWER] = new Operator("^", 2, true,
				PRECEDENCE_POWER) {
			@Override
			public double apply(double... args) {
				return Math.pow(args[0], args[1]);
			}
		};
		builtin[INDEX_MODULO] = new Operator("%", 2, true,
				PRECEDENCE_MODULO) {
			@Override
			public double apply(double... args) {
				return args[0] % args[1];
			}
		};
		builtin[INDEX_UNARYMINUS] = new Operator("-", 1, true,
				PRECEDENCE_UNARY_MINUS) {
			@Override
			public double apply(double ... args) {
				return -args[0];
			}
		};
	}

	public static char[] getAllowedOperatorChars() {
		return ALLOWED_OPERATOR_CHARS;
	}
	
	public static boolean isAllowedChar(char ch) {
		int len = ALLOWED_OPERATOR_CHARS.length;
		for (int i = 0; i < len; i++) {
			if (ch == ALLOWED_OPERATOR_CHARS[i]) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOperator(String symbol) {
		return true;
	}

	public static Operator getBuiltinOperator(char ch, int argc) {
		switch (ch) {
		case '+':
			return builtin[INDEX_ADDITION];
		case '-':
			if (argc == 1) {
				return builtin[INDEX_UNARYMINUS];
			}else {
				return builtin[INDEX_SUBTRACTION];
			}
		case '*':
			return builtin[INDEX_MUTLIPLICATION];
		case '/':
			return builtin[INDEX_DIVISION];
		case '%':
			return builtin[INDEX_MODULO];
		case '^':
			return builtin[INDEX_POWER];
		default:
			return null;
		}
	}
}
