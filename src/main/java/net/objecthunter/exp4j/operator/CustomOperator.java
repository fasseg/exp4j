package net.objecthunter.exp4j.operator;

public abstract class CustomOperator {
	
	private static final char[] ALLOWED_OPERATOR_CHARS = {'+','-','*','/','%','^','!','#','§','$','&',';',':','~','<','>','|','='}; 

	private final int argc;
	private final boolean leftAssociative;
	private final String symbol;
	private final int precedence;

	public CustomOperator(String symbol, int precedence, int argc, boolean leftAssociative) {
		super();
		for (int i = 0 ; i< symbol.length();i++){
			if (!isAllowedOperatorChar(symbol.charAt(i))){
				throw new RuntimeException("Invalid operator char");
			}
		}
		this.argc = argc;
		this.leftAssociative = leftAssociative;
		this.symbol = symbol;
		this.precedence = precedence;
	}

	public CustomOperator(String symbol, int precedence) {
		this(symbol, precedence, 2, true);
	}

	public CustomOperator(String symbol) {
		this(symbol, Operators.PRECEDENCE_ADDITION, 2, true);
	}

	public int getArgumentCount() {
		return argc;
	}

	public int getPrecedence() {
		return precedence;
	}

	public String getSymbol() {
		return symbol;
	}

	public boolean isLeftAssociative() {
		return leftAssociative;
	}

	public abstract Object apply(Object... args);
	
	private static boolean isAllowedOperatorChar(char s) {
		for (char c : ALLOWED_OPERATOR_CHARS){
			if (c == s){
				return true;
			}
		}
		return false;
	}


}
