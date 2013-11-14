package net.objecthunter.exp4j.function;

import net.objecthunter.exp4j.ComplexNumber;

public abstract class ComplexNumberFunctions {
	public static ComplexNumber power(final ComplexNumber base, final ComplexNumber exponent) {
		final double a = base.getReal();
		final double b = base.getImaginary();
		final double c = exponent.getReal();
		final double d = exponent.getImaginary();
		if (a == 0d && b == 0d) {
			return new ComplexNumber(0d, 0d);
		}else if (a == 0d && b == 1d && exponent.isReal()){
			// check if the exponent is a natural number
			// then a simple check for the mod of the number will suffice
			if ((float) (int) c == c){
				int n = (int) c;
				if (n % 2 == 0){
					return new ComplexNumber(-1d, 0d);
				}else{
					return new ComplexNumber(1d, 0d);
				}
			}
			return new ComplexNumber(Math.cos(Math.PI*c/2d),Math.sin(Math.PI*c/2d));
		}else if (exponent.isReal()) {
			if (exponent.getReal() == 1d) {
				return base;
			}
			if (exponent.getReal() == 0d) {
				return new ComplexNumber(1d, 0d);
			}
			// DeMoivre's theorem can be used
			double theta = Math.atan(b/a);
			final double r = Math.pow(Math.sqrt(a*a+b*b),c) ;
			return multiply(new ComplexNumber(Math.cos(c*theta),Math.sin(c*theta)), r);
		}else if (base.isReal()) {
			// use euler's formula to calculate the power
			return multiply(new ComplexNumber(Math.cos(d*Math.log(a)), Math.sin(d*Math.log(a))), Math.pow(a, c));
		}else {
			final double phi = Math.atan(b/a);
	
			final double factor = Math.pow(abs(base),c) * Math.pow(Math.E,-d*phi);
			final double len = c*phi + d * Math.log(abs(base)); 
			
			final double real = factor * Math.cos(len);
			final double img =  factor * Math.sin(len);
			return new ComplexNumber(real, img);
		}
		
	}

	public static ComplexNumber divide(ComplexNumber z1, ComplexNumber z2) {
		double denominator = z2.getImaginary() * z2.getImaginary() + z2.getReal() * z2.getReal();
		double real = (z1.getReal() * z2.getReal() + z1.getImaginary() * z2.getImaginary()) / denominator;
		double img = (z1.getImaginary() * z2.getReal() - z1.getReal() * z2.getImaginary()) / denominator;
		return new ComplexNumber(real, img);
	}

	public static ComplexNumber multiply(ComplexNumber z, double scalefactor) {
		return new ComplexNumber(z.getReal() * scalefactor, z.getImaginary() * scalefactor);
	}

	public static ComplexNumber log(ComplexNumber w) {
		/* log(z) = ln |z| + i arg(z) */
		double real = Math.log(abs(w));
		double img = arg(w);
		return new ComplexNumber(real, img);
	}

	public static double abs(ComplexNumber z) {
		return Math.sqrt(Math.pow(z.getReal(), 2d) + Math.pow(z.getImaginary(), 2d));
	}

	public static ComplexNumber add(ComplexNumber... summands) {
		double real = 0d;
		double img = 0d;
		for (ComplexNumber sum : summands) {
			real += sum.getReal();
			img += sum.getImaginary();
		}
		return new ComplexNumber(real, img);
	}

	public static double arg(ComplexNumber z) {
		double real = z.getReal();
		double img = z.getImaginary();

		if (real > 0) {
			return Math.atan(img / real);
		} else if (real < 0 && img >= 0) {
			return Math.atan(img / real) + Math.PI;
		} else if (real < 0 && img < 0) {
			return Math.atan(img / real) - Math.PI;
		} else if (real == 0 && img > 0) {
			return Math.PI / 2d;
		} else if (real == 0 && img < 0) {
			return -Math.PI / 2d;
		} else {
			throw new RuntimeException("unable to generate angle from the origin z= " + z);
		}
	}

	public static ComplexNumber subtract(ComplexNumber minuend, ComplexNumber subtrahend) {
		return new ComplexNumber(minuend.getReal() - subtrahend.getReal(), minuend.getImaginary() - subtrahend.getImaginary());
	}

	public static ComplexNumber sin(ComplexNumber z) {
		return new ComplexNumber(Math.cosh(-z.getImaginary()) * Math.sin(z.getReal()), -Math.sinh(-z.getImaginary()) * Math.cos(z.getReal()));
	}

	public static ComplexNumber multiply(ComplexNumber z1, ComplexNumber z2) {
		return new ComplexNumber(z1.getReal() * z2.getReal() - (z1.getImaginary() * z2.getImaginary()), z1.getReal() * z2.getImaginary() + (z1.getImaginary() * z2.getReal()));
	}

	public static ComplexNumber cos(ComplexNumber z) {
		return new ComplexNumber(Math.cosh(-z.getImaginary()) * Math.cos(z.getReal()), -Math.sinh(-z.getImaginary()) * Math.sin(z.getReal()));
	}

	public static ComplexNumber tan(ComplexNumber z) {
		ComplexNumber z1 = new ComplexNumber(Math.tan(z.getReal()), -Math.tanh(-z.getImaginary()));
		ComplexNumber z2 = new ComplexNumber(1d, Math.tanh(-z.getImaginary()) * Math.tan(z.getReal()));
		return divide(z1, z2);
	}

	public static ComplexNumber tanh(ComplexNumber z) {
		ComplexNumber z1 = new ComplexNumber(Math.tanh(z.getReal()), Math.tan(z.getImaginary()));
		ComplexNumber z2 = new ComplexNumber(1d, Math.tanh(z.getReal()) * Math.tan(z.getImaginary()));
		return divide(z1, z2);
	}

	public static ComplexNumber atan(ComplexNumber z) {
		double a = z.getReal();
		double b = z.getImaginary();
		return multiply(new ComplexNumber(0d, 0.5d), subtract(log(new ComplexNumber(1 + b, -a)), log(new ComplexNumber(1 - b, a))));
	}

	public static ComplexNumber cosh(ComplexNumber z) {
		return new ComplexNumber(Math.cosh(z.getReal()) * Math.cos(z.getImaginary()), Math.sinh(z.getReal()) * Math.sin(z.getImaginary()));
	}

	public static ComplexNumber sinh(ComplexNumber z) {
		return new ComplexNumber(Math.sinh(z.getReal()) * Math.cos(z.getImaginary()), Math.cosh(z.getReal()) * Math.sin(z.getImaginary()));
	}

	public static ComplexNumber exp(ComplexNumber z) {
		return multiply(new ComplexNumber(Math.cos(z.getImaginary()), Math.sin(z.getImaginary())), Math.exp(z.getReal()));
	}

	public static ComplexNumber asin(ComplexNumber z) {
		double a = z.getReal();
		double b = z.getImaginary();
		double c1 = (Math.sqrt((a * a + 2 * a + 1) + b * b) - Math.sqrt((1 - 2 * a + a * a) + b * b)) / 2d;
		double c2 = (Math.sqrt((a * a + 2 * a + 1) + b * b) + Math.sqrt((1 - 2 * a + a * a) + b * b)) / 2d;
		return new ComplexNumber(Math.asin(c1), Math.log(c2 + Math.sqrt(c2 * c2 - 1d)));
	}

	public static ComplexNumber expm1(ComplexNumber z) {
		return subtract(multiply(new ComplexNumber(Math.cos(z.getImaginary()), Math.sin(z.getImaginary())), Math.exp(z.getReal())), new ComplexNumber(1d, 1d));
	}

	public static ComplexNumber conj(ComplexNumber z) {
		return new ComplexNumber(z.getReal(), -z.getImaginary());
	}

	public static ComplexNumber acos(ComplexNumber z) {
		double a = z.getReal();
		double b = z.getImaginary();
		double c1 = (Math.sqrt((a * a + 2 * a + 1) + b * b) - Math.sqrt((1 - 2 * a + a * a) + b * b)) / 2d;
		double c2 = (Math.sqrt((a * a + 2 * a + 1) + b * b) + Math.sqrt((1 - 2 * a + a * a) + b * b)) / 2d;
		return new ComplexNumber(Math.acos(c1), -Math.log(c2 + Math.sqrt(c2 * c2 - 1d)));
	}

	public static ComplexNumber cbrt(ComplexNumber z) {
		return power(z, new ComplexNumber(1d / 3d, 0d));
	}

	public static ComplexNumber sqrt(ComplexNumber z) {
		return power(z, new ComplexNumber(0.5d, 0d));
	}

	public static ComplexNumber floor(ComplexNumber z) {
		return new ComplexNumber(Math.floor(z.getReal()), Math.floor(z.getImaginary()));
	}

	public static ComplexNumber ceil(ComplexNumber z) {
		return new ComplexNumber(Math.ceil(z.getReal()), Math.ceil(z.getImaginary()));
	}

	public static ComplexNumber log10(ComplexNumber z) {
		return multiply(log(z), 1 / Math.log(10));
	}
}
