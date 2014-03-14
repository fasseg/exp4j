package net.objecthunter.exp4j.bigdecimal;

import java.util.*;
import java.math.*;

/**
 * Factorials.
 * 
 * @since 2006-06-25
 * @since 2012-02-15 Storage of the values based on Ifactor, not BigInteger.
 * @author Richard J. Mathar
 */
public class Factorial {
	/**
	 * The list of all factorials as a vector.
	 */
	static Vector<Ifactor> a = new Vector<Ifactor>();

	/**
	 * ctor(). Initialize the vector of the factorials with 0!=1 and 1!=1.
	 */
	public Factorial() {
		if (a.size() == 0) {
			a.add(Ifactor.ONE);
			a.add(Ifactor.ONE);
		}
	} /* ctor */

	/**
	 * Compute the factorial of the non-negative integer.
	 * 
	 * @param n
	 *            the argument to the factorial, non-negative.
	 * @return the factorial of n.
	 */
	public BigInteger at(int n) {
		/*
		 * extend the internal list if needed.
		 */
		growto(n);
		return a.elementAt(n).n;
	} /* at */

	/**
	 * Compute the factorial of the non-negative integer.
	 * 
	 * @param n
	 *            the argument to the factorial, non-negative.
	 * @return the factorial of n.
	 */
	public Ifactor toIfactor(int n) {
		/*
		 * extend the internal list if needed.
		 */
		growto(n);
		return a.elementAt(n);
	} /* at */

	/**
	 * Extend the internal table to cover up to n!
	 * 
	 * @param n
	 *            The maximum factorial to be supported.
	 * @since 2012-02-15
	 */
	private void growto(int n) {
		/*
		 * extend the internal list if needed. Size to be 2 for n<=1, 3 for n<=2 etc.
		 */
		while (a.size() <= n) {
			final int lastn = a.size() - 1;
			final Ifactor nextn = new Ifactor(lastn + 1);
			a.add(a.elementAt(lastn).multiply(nextn));
		}
	} /* growto */

} /* Factorial */
