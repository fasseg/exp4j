package net.objecthunter.exp4j;

public class ComplexNumber {
	private final double real;
	private final double imaginary;

	public static ComplexNumber parseComplex(String number) {
		int posSep = number.indexOf("+");
		if (posSep == -1) {
			posSep = number.indexOf("-");
		}
		double real = Double.parseDouble(number.substring(0, posSep));
		double img = Double.parseDouble(number.substring(posSep,number.length() - 1));
		return new ComplexNumber(real, img);
	}

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
