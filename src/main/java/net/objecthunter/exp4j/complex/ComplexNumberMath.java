package net.objecthunter.exp4j.complex;

public class ComplexNumberMath {
	public static ComplexNumber power(ComplexNumber z1, ComplexNumber z2) {
		if (z2.isReal()) {
			int i = (int) z2.getReal();
			if ((double) i == z2.getReal()) {
				return power(z1, i);
			}
		}
		if (z1.isReal()) {
			return power(z1.getReal(), z2);
		}
		/* calc the principal value of the power */
		ComplexNumber exponent = multiply(z2, log(z1));
		return power(Math.E, exponent);
	}

	public static ComplexNumber power(ComplexNumber z, int n) {
		double mod = mod(z);
		double arg = arg(z);
		return scale(new ComplexNumber(Math.cos(n * arg), Math.sin(n * arg)),
				Math.pow(mod, n));
	}

	public static ComplexNumber power(double r, ComplexNumber z) {
		if (r <= 0) {
			throw new IllegalArgumentException(
					"Can't take complex powers of negative real numbers");
		}
		if (r == Math.E) {
			return exp(z);
		}
		return exp(scale(z, Math.log(r)));
	}

	public static ComplexNumber divide(ComplexNumber z1, ComplexNumber z2) {
		double denominator = z2.getImaginary() * z2.getImaginary()
				+ z2.getReal() * z2.getReal();
		double real = (z1.getReal() * z2.getReal() + z1.getImaginary()
				* z2.getImaginary())
				/ denominator;
		double img = (z1.getImaginary() * z2.getReal() - z1.getReal()
				* z2.getImaginary())
				/ denominator;
		return new ComplexNumber(real, img);
	}

	public static ComplexNumber multiply(ComplexNumber z, double scalefactor) {
		return new ComplexNumber(z.getReal() * scalefactor, z.getImaginary()
				* scalefactor);
	}

	public static ComplexNumber log(ComplexNumber w) {
		if (w.isReal()) {
			return new ComplexNumber(Math.log(w.getReal()), 0d);
		}
		/* log(z) = ln |z| + i arg(z) */
		double real = Math.log(abs(w));
		double img = arg(w);
		return new ComplexNumber(real, img);
	}

	public static double abs(ComplexNumber z) {
		if (z.isReal()) {
			return Math.abs(z.getReal());
		}
		return Math.sqrt(Math.pow(z.getReal(), 2d)
				+ Math.pow(z.getImaginary(), 2d));
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
		if (real == 0d && img == 0d) {
			return 0d;
		}
		if (real > 0) {
			return Math.atan(img / real);
		}
		if (real < 0 && img >= 0) {
			return Math.atan(img / real) + Math.PI;
		}
		if (real < 0 && img < 0) {
			return Math.atan(img / real) - Math.PI;
		}
		if (real == 0 && img > 0) {
			return Math.PI / 2d;
		}
		if (real == 0 && img < 0) {
			return -Math.PI / 2d;
		}
		throw new RuntimeException(
				"unable to generate angle from the origin z= " + z);
	}

	public static ComplexNumber subtract(ComplexNumber minuend,
			ComplexNumber subtrahend) {
		return new ComplexNumber(minuend.getReal() - subtrahend.getReal(),
				minuend.getImaginary() - subtrahend.getImaginary());
	}

	public static ComplexNumber sin(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.sin(z.getReal()), 0d);
		}
		return new ComplexNumber(Math.cosh(-z.getImaginary())
				* Math.sin(z.getReal()), -Math.sinh(-z.getImaginary())
				* Math.cos(z.getReal()));
	}

	public static ComplexNumber multiply(ComplexNumber z1, ComplexNumber z2) {
		return new ComplexNumber(z1.getReal() * z2.getReal()
				- (z1.getImaginary() * z2.getImaginary()), z1.getReal()
				* z2.getImaginary() + (z1.getImaginary() * z2.getReal()));
	}

	public static ComplexNumber cos(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.cos(z.getReal()), 0d);
		}
		return new ComplexNumber(Math.cosh(-z.getImaginary())
				* Math.cos(z.getReal()), -Math.sinh(-z.getImaginary())
				* Math.sin(z.getReal()));
	}

	public static ComplexNumber tan(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.tan(z.getReal()), 0d);
		}
		ComplexNumber z1 = new ComplexNumber(Math.tan(z.getReal()),
				-Math.tanh(-z.getImaginary()));
		ComplexNumber z2 = new ComplexNumber(1d, Math.tanh(-z.getImaginary())
				* Math.tan(z.getReal()));
		return divide(z1, z2);
	}

	public static ComplexNumber tanh(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.tanh(z.getReal()), 0d);
		}
		ComplexNumber z1 = new ComplexNumber(Math.tanh(z.getReal()), Math.tan(z
				.getImaginary()));
		ComplexNumber z2 = new ComplexNumber(1d, Math.tanh(z.getReal())
				* Math.tan(z.getImaginary()));
		return divide(z1, z2);
	}

	public static ComplexNumber atan(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.atan(z.getReal()), 0d);
		}
		double a = z.getReal();
		double b = z.getImaginary();
		return multiply(
				new ComplexNumber(0d, 0.5d),
				subtract(log(new ComplexNumber(1 + b, -a)),
						log(new ComplexNumber(1 - b, a))));
	}

	public static ComplexNumber cosh(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.cosh(z.getReal()), 0d);
		}
		return new ComplexNumber(Math.cosh(z.getReal())
				* Math.cos(z.getImaginary()), Math.sinh(z.getReal())
				* Math.sin(z.getImaginary()));
	}

	public static ComplexNumber sinh(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.sinh(z.getReal()), 0d);
		}
		return new ComplexNumber(Math.sinh(z.getReal())
				* Math.cos(z.getImaginary()), Math.cosh(z.getReal())
				* Math.sin(z.getImaginary()));
	}

	public static ComplexNumber exp(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.exp(z.getReal()), 0d);
		}
		return multiply(
				new ComplexNumber(Math.cos(z.getImaginary()), Math.sin(z
						.getImaginary())), Math.exp(z.getReal()));
	}

	public static ComplexNumber asin(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.asin(z.getReal()), 0d);
		}
		double a = z.getReal();
		double b = z.getImaginary();
		double c1 = (Math.sqrt((a * a + 2 * a + 1) + b * b) - Math
				.sqrt((1 - 2 * a + a * a) + b * b)) / 2d;
		double c2 = (Math.sqrt((a * a + 2 * a + 1) + b * b) + Math
				.sqrt((1 - 2 * a + a * a) + b * b)) / 2d;
		return new ComplexNumber(Math.asin(c1), Math.log(c2
				+ Math.sqrt(c2 * c2 - 1d)));
	}

	public static ComplexNumber expm1(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.expm1(z.getReal()), 0d);
		}

		return subtract(
				multiply(
						new ComplexNumber(Math.cos(z.getImaginary()),
								Math.sin(z.getImaginary())), Math.exp(z
								.getReal())), new ComplexNumber(1d, 1d));
	}

	public static ComplexNumber conj(ComplexNumber z) {
		return new ComplexNumber(z.getReal(), -z.getImaginary());
	}

	public static ComplexNumber acos(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.acos(z.getReal()), 0d);
		}
		double a = z.getReal();
		double b = z.getImaginary();
		double c1 = (Math.sqrt((a * a + 2 * a + 1) + b * b) - Math
				.sqrt((1 - 2 * a + a * a) + b * b)) / 2d;
		double c2 = (Math.sqrt((a * a + 2 * a + 1) + b * b) + Math
				.sqrt((1 - 2 * a + a * a) + b * b)) / 2d;
		return new ComplexNumber(Math.acos(c1), -Math.log(c2
				+ Math.sqrt(c2 * c2 - 1d)));
	}

	public static ComplexNumber cbrt(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.cbrt(z.getReal()), 0d);
		}
		return power(z, new ComplexNumber(1d / 3d, 0d));
	}

	public static ComplexNumber sqrt(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.sqrt(z.getReal()), 0d);
		}
		return power(z, new ComplexNumber(0.5d, 0d));
	}

	public static ComplexNumber floor(ComplexNumber z) {
		return new ComplexNumber(Math.floor(z.getReal()), Math.floor(z
				.getImaginary()));
	}

	public static ComplexNumber ceil(ComplexNumber z) {
		return new ComplexNumber(Math.ceil(z.getReal()), Math.ceil(z
				.getImaginary()));
	}

	public static ComplexNumber log10(ComplexNumber z) {
		if (z.isReal()) {
			return new ComplexNumber(Math.log10(z.getReal()), 0d);
		}
		return multiply(log(z), 1 / Math.log(10));
	}

	public static ComplexNumber scale(ComplexNumber z, double r) {
		return new ComplexNumber(z.getReal() * r, z.getImaginary() * r);
	}

	public static ComplexNumber add(ComplexNumber z1, ComplexNumber z2) {
		return new ComplexNumber(z1.getReal() + z2.getReal(), z1.getImaginary()
				+ z2.getImaginary());
	}

	public static ComplexNumber divide(ComplexNumber z, double divisor) {
		return scale(z, 1 / divisor);
	}

	public static double mod(ComplexNumber z) {
		return abs(z);
	}

	public static ComplexNumber negate(ComplexNumber z) {
		return new ComplexNumber(z.getReal() * -1d, z.getImaginary() * -1d);
	}

	public static ComplexNumber log1p(ComplexNumber z) {
		return log(new ComplexNumber(z.getReal() + 1, z.getImaginary()));
	}
}
