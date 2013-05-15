package net.objecthunter.exp4j.calculable;

import java.util.List;

import net.objecthunter.exp4j.tokenizer.Token;

public class DoubleCalculable extends Calculable<Double> {

	public DoubleCalculable(List<Token> tokens) {
		super(tokens);
	}

	public Double calculate() {
		return 0.0d;
	}
}
