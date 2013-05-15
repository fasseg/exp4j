package net.objecthunter.exp4j.tokenizer;

public class NumberToken<T> extends Token {
	private final T value;
	private final Class<T> valueType;
	boolean imaginary = false;

	public NumberToken(final Class<T> valueType, final T value) {
		super(Token.Type.NUMBER);
		this.value = value;
		this.valueType = valueType;
	}

	public NumberToken(final Class<T> valueType, final T value, boolean imaginary) {
		super(Token.Type.NUMBER);
		this.value = value;
		this.valueType = valueType;
		this.imaginary = imaginary;
	}

	public T getValue() {
		return value;
	}

	public boolean isImaginary() {
		return imaginary;
	}
}
