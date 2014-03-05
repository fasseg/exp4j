package net.objecthunter.exp4j.complex;

public class ComplexNumberMath {
	public static ComplexNumber multiply(ComplexNumber z1, ComplexNumber z2) {
		double r = z1.getReal() * z2.getReal() - (z1.getImaginary() * z2.getImaginary());
		double i = z1.getReal() * z2.getImaginary() + (z1.getImaginary() * z2.getReal());
		return new ComplexNumber(r, i);
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
}
