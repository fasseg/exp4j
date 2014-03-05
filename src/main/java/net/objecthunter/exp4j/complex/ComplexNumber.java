package net.objecthunter.exp4j.complex;

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
	
	public boolean isReal() {
		return imaginary == 0d;
	}
	
	public boolean isImaginary() {
		return real == 0d;
	}

	public static ComplexNumber parseComplex(String val) {
		final StringBuilder tmp = new StringBuilder();
		final int len = val.length();
		double real = 0d;
		double img = 0d;
		boolean imaginary = false;
		for (int i = 0; i < len; i++) {
			final char c = val.charAt(i);
			if (Character.isDigit(c) || c == '.') {
				tmp.append(c);
			} else if (c == 'i') {
				imaginary = true;
			} else if (c == '+' || c == '-') {
				real = Double.parseDouble(tmp.toString());
				tmp.setLength(0);
				tmp.append(c);
			}
		}
		if (imaginary) {
			img = Double.parseDouble(tmp.toString());
		} else {
			real = Double.parseDouble(tmp.toString());
		}
		return new ComplexNumber(real, img);
	}
}
