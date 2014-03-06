package net.objecthunter.exp4j.complex;
import static java.lang.Math.*;

public class ComplexNumberMath {
	public static ComplexNumber multiply(ComplexNumber z1, ComplexNumber z2) {
		double r = z1.getReal() * z2.getReal() - (z1.getImaginary() * z2.getImaginary());
		double i = z1.getReal() * z2.getImaginary() + (z1.getImaginary() * z2.getReal());
		return new ComplexNumber(r, i);
	}
	public static ComplexNumber scale(ComplexNumber z, double r) {
		return new ComplexNumber(z.getReal() * r, z.getImaginary() * r);
	}
	public static ComplexNumber subtract(ComplexNumber z1, ComplexNumber z2) {
		return new ComplexNumber(z1.getReal() - z2.getReal(), z1.getImaginary() - z2.getImaginary());
	}
	public static ComplexNumber add(ComplexNumber z1, ComplexNumber z2) {
		return new ComplexNumber(z1.getReal() + z2.getReal(), z1.getImaginary() + z2.getImaginary());
	}
	public static ComplexNumber divide(ComplexNumber z1, ComplexNumber z2) {
		double div = z2.getReal() * z2.getReal() + (z2.getImaginary() * z2.getImaginary());
		double r = z1.getReal() * z2.getReal() + (z1.getImaginary()*z2.getImaginary());
		double i = z1.getImaginary() * z2.getReal() - (z1.getReal() * z2.getImaginary());
		return new ComplexNumber(r/div, i/div);
	}
	public static ComplexNumber power(ComplexNumber z1, ComplexNumber z2) {
		if (z2.isReal()) {
			int i = (int) z2.getReal();
			if ( (double) i == z2.getReal()) {
				return power(z1,i);
			}
		}
		if (z1.isReal()) {
			return power(z1.getReal(),z2);
		}
		/* calc the principal value of the power */
		ComplexNumber exponent = multiply(z2, log(z1));
		return power(E,exponent);
	}
	

	public static ComplexNumber power(ComplexNumber z, int n) {
		double mod = mod(z);
		double arg = arg(z);
		return scale(new ComplexNumber(cos(n*arg), sin(n*arg)),pow(mod,n));
	}

	public static ComplexNumber power(double r, ComplexNumber z) {
		if (r <= 0) {
			throw new IllegalArgumentException("Can't take complex powers of negative real numbers");
		}
		if (r == E) {
			return exp(z);
		}
		return exp(scale(z, Math.log(r)));
	}
	private static ComplexNumber exp(ComplexNumber z) {
		double factor = Math.exp(z.getReal());
		return scale(new ComplexNumber(cos(z.getImaginary()), sin(z.getImaginary())), factor);
	}
	public static ComplexNumber log(ComplexNumber z) {
		return new ComplexNumber(Math.log(mod(z)),arg(z));
	}
	
	public static double mod(ComplexNumber z) {
		return sqrt(pow(z.getReal(),2) + pow(z.getImaginary(),2));
	}
	public static double arg(ComplexNumber z) {
		return atan2(z.getImaginary(), z.getReal());
	}
}
