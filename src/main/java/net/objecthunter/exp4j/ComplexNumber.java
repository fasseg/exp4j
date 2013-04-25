package net.objecthunter.exp4j;

public class ComplexNumber {
	private final double real;
	private final double imaginary;

	public ComplexNumber(double real, double imaginary) {
		super();
		this.real = real;
		this.imaginary = imaginary;
	}

	public double getReal() {
		return real;
	}

	public double getImaginary() {
		return imaginary;
	}
	
	@Override
	public String toString() {
		return real + " + " + imaginary + "i";
	}

}
