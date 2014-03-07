package net.objecthunter.exp4j.complex;

public class ComplexNumber {
	public static ComplexNumber I = new ComplexNumber(0d, 1d);
	public static ComplexNumber ONE = new ComplexNumber(1d, 0d);
	public static ComplexNumber ZERO = new ComplexNumber(0d, 0d);
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		if (Double.doubleToLongBits(imaginary) != Double
				.doubleToLongBits(other.imaginary))
			return false;
		if (Double.doubleToLongBits(real) != Double
				.doubleToLongBits(other.real))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.real + ((this.imaginary < 0) ? "-" : "+") + Math.abs(this.imaginary) + "i";
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
			img = tmp.length() == 0 ? 1d : Double.parseDouble(tmp.toString());
		} else {
			real = Double.parseDouble(tmp.toString());
		}
		return new ComplexNumber(real, img);
	}
}
