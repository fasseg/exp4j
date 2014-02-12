package net.objecthunter.exp4j.tokens;


public class NumberToken<T> extends Token {
	private final T value;

	public NumberToken(T value) {
		super(NUMBER);
		this.value = value;
	}

	public T getValue() {
		return value;
	}

}
