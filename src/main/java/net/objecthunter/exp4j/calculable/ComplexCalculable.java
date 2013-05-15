package net.objecthunter.exp4j.calculable;

import java.util.List;

import net.objecthunter.exp4j.ComplexNumber;
import net.objecthunter.exp4j.tokenizer.Token;

public class ComplexCalculable extends Calculable<ComplexNumber> {

	public ComplexCalculable(List<Token> tokens) {
		super(tokens);
	}

	@Override
	public ComplexNumber calculate() {
		return new ComplexNumber(3d, 14d);
	}

}
