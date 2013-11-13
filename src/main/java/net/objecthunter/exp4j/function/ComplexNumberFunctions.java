package net.objecthunter.exp4j.function;

import net.objecthunter.exp4j.ComplexNumber;

public abstract class ComplexNumberFunctions {
	public static ComplexNumber power(final ComplexNumber base, final ComplexNumber exponent) {
		/* w^z = y */
		/* y.real = |w|^rexp * e^(-iexp*atan(ibase/rbase) * [ cos(rexp*atan(ibase/rbase + iexp * ln|p|)  */
		/* y.img  = sin( rexp * atan(ibase/rbase) + iexp * ln|p|] */
		final double a = base.getReal();
		final double b = base.getImaginary();
		final double c = exponent.getReal();
		final double d = exponent.getImaginary();
		final double phi = Math.atan(b/a);

		final double factor = Math.pow(abs(base),c) * Math.pow(Math.E,-d*phi);
		final double len = c*phi + d * Math.log(abs(base)); 
		
		final double real = factor * Math.cos(len);
		final double img =  factor * Math.sin(len);
		return new ComplexNumber(real, img);
		
	}
	
	public static ComplexNumber divide(ComplexNumber z1, ComplexNumber z2) {
		double denominator = z2.getImaginary() * z2.getImaginary() + z2.getReal() * z2.getReal();
		double real = (z1.getReal() * z2.getReal() + z1.getImaginary() * z2.getImaginary()) / denominator;
		double img = (z1.getImaginary() * z2.getReal() - z1.getReal() * z2.getImaginary()) / denominator;
		return new ComplexNumber(real, img);
	}
	
	public static ComplexNumber multiply(ComplexNumber z, double scalefactor){
		return new ComplexNumber(z.getReal() * scalefactor, z.getImaginary() * scalefactor);
	}

	public static ComplexNumber log(ComplexNumber w){
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
			return Math.atan(real/img);
		} else if (real < 0 && img >= 0) {
			return Math.atan(real/img) + Math.PI;
		} else if (real < 0 && img < 0) {
			return Math.atan(real/img) - Math.PI;
		} else if (real == 0 && img > 0) {
			return Math.PI/2d;
		} else if (real == 0 && img < 0) {
			return -Math.PI/2d;
		} else {
			throw new RuntimeException("unable to generate angle from the origin z= "  +z);
		}
	}

	public static ComplexNumber substract(ComplexNumber minuend, ComplexNumber subtrahend) {
		return new ComplexNumber(minuend.getReal() - subtrahend.getReal(), minuend.getImaginary()
				- subtrahend.getImaginary());
	}

	public static ComplexNumber sin(ComplexNumber z) {
		return new ComplexNumber(Math.cosh(-z.getImaginary())*Math.sin(z.getReal()), -Math.sinh(-z.getImaginary())*Math.cos(z.getReal()));
	}

	public static ComplexNumber multiply(ComplexNumber z1, ComplexNumber z2) {
		return new ComplexNumber(z1.getReal() * z2.getReal() - (z1.getImaginary()*z2.getImaginary()), z1.getReal() * z2.getImaginary() + (z1.getImaginary()*z2.getReal()));
	}

	public static Object cos(ComplexNumber z) {
		return new ComplexNumber(Math.cosh(-z.getImaginary())*Math.cos(z.getReal()), -Math.sinh(-z.getImaginary())*Math.sin(z.getReal()));
	}

	public static Object tan(ComplexNumber z) {
		ComplexNumber z1 = new ComplexNumber(Math.tan(z.getReal()),-Math.tanh(-z.getImaginary()));
		ComplexNumber z2 = new ComplexNumber(1d,Math.tanh(-z.getImaginary()) * Math.tan(z.getReal()));
		return divide(z1, z2);
	}

	public static Object tanh(ComplexNumber z) {
		ComplexNumber z1 = new ComplexNumber(Math.tanh(z.getReal()),Math.tan(z.getImaginary()));
		ComplexNumber z2 = new ComplexNumber(1d,Math.tanh(z.getReal()) * Math.tan(z.getImaginary()));
		return divide(z1, z2);
	}

	public static Object atan(ComplexNumber arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object cosh(ComplexNumber z) {
		return new ComplexNumber(Math.cosh(z.getReal())*Math.cos(z.getImaginary()), Math.sinh(z.getReal())*Math.sin(z.getImaginary()));
	}

	public static Object sinh(ComplexNumber z) {
		return new ComplexNumber(Math.sinh(z.getReal())*Math.cos(z.getImaginary()), Math.cosh(z.getReal())*Math.sin(z.getImaginary()));
	}

	public static Object exp(ComplexNumber arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object asin(ComplexNumber arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object expm1(ComplexNumber arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object acos(ComplexNumber arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object cbrt(ComplexNumber arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object sqrt(ComplexNumber z) {
		return power(z, new ComplexNumber(0.5d, 0d));
	}

	public static Object floor(ComplexNumber arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object ceil(ComplexNumber arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object log10(ComplexNumber arg) {
		// TODO Auto-generated method stub
		return null;
	}
}
