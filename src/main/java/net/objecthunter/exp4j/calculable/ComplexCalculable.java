package net.objecthunter.exp4j.calculable;

import net.objecthunter.exp4j.ComplexNumber;

public class ComplexCalculable implements Calculable<ComplexNumber> {

	@Override
	public ComplexNumber calculate() {
		return new ComplexNumber(3d, 14d);
	}

}
