package net.objecthunter.exp4j;

public class ComplexNumber {
	private final double real;
	private final double imaginary;

	public static ComplexNumber parseComplex(String number) {
		int posSep = number.indexOf("+");
		if (posSep == -1) {
			posSep = number.indexOf("-");
		}
		double real = (posSep == -1) ? Double.parseDouble(number) : Double.parseDouble(number.substring(0, posSep));
		double img = (posSep == -1) ? 0d : Double.parseDouble(number.substring(posSep, number.length() - 1));
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
		if (Double.doubleToLongBits(imaginary) != Double.doubleToLongBits(other.imaginary))
			return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return real + " + " + imaginary + "i";
	}

}
