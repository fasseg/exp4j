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
		if (z.getImaginary() == 0){
			return new ComplexNumber(Math.sin(z.getReal()),0d);
		}else if (z.getReal() == 0){
			return new ComplexNumber(0, Math.sinh(z.getImaginary()));
		}else {
			throw new RuntimeException("unable to calcaluate a single sine value for " + z);
		}
	}
}
