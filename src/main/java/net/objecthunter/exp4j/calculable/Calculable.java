package net.objecthunter.exp4j.calculable;

import java.util.List;

import net.objecthunter.exp4j.tokenizer.Token;

public abstract class Calculable<T> {
	protected final List<Token> tokens;
	
	public Calculable(List<Token> tokens) {
		super();
		this.tokens = tokens;
	}


	public abstract T calculate();
}
