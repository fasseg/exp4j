package net.objecthunter.exp4j.bigdecimal;

import java.lang.*;
import java.util.*;
import java.math.*;

/**
 * Prime numbers. The implementation is a very basic computation of the set of all primes on demand, growing infinitely
 * without any defined upper limit. The effects of such scheme are (i) the lookup-times become shorter after a while as
 * more and more primes have been used and stored. The applications appear to become faster. (ii) Using the
 * implementation for factorizations may easily require all available memory and stall finally, because indeed a dense
 * list of primes with growing upper bound is kept without any hashing or lagging scheme.
 * 
 * @since 2006-08-11
 * @author Richard J. Mathar
 */
public class Prime {
	/**
	 * The list of all numbers as a vector.
	 */
	static Vector<BigInteger> a = new Vector<BigInteger>();

	/**
	 * The maximum integer covered by the high end of the list.
	 */
	static protected BigInteger nMax = new BigInteger("-1");

	/**
	 * Default constructor initializing a list of primes up to 17. 17 is enough to call the Miller-Rabin tests on the
	 * first 7 primes without further action.
	 */
	public Prime() {
		if (a.size() == 0) {
			a.add(new BigInteger("" + 2));
			a.add(new BigInteger("" + 3));
			a.add(new BigInteger("" + 5));
			a.add(new BigInteger("" + 7));
			a.add(new BigInteger("" + 11));
			a.add(new BigInteger("" + 13));
			a.add(new BigInteger("" + 17));
		}
		nMax = a.lastElement();
	}

	/**
	 * Test if a number is a prime.
	 * 
	 * @param n
	 *            the integer to be tested for primality
	 * @return true if prime, false if not
	 */
	public boolean contains(BigInteger n) {
		/*
		 * not documented return ( n.isProbablePrime() ) ;
		 */
		switch (millerRabin(n)) {
		case -1:
			return false;
		case 1:
			return true;
		}
		growto(n);
		return (a.contains(n));
	}

	/**
	 * Test whether a number n is a strong pseudoprime to base a.
	 * 
	 * @param n
	 *            the integer to be tested for primality
	 * @param a
	 *            the base
	 * @return true if the test is passed, so n may be a prime. false if the test is not passed, so n is not a prime.
	 * @since 2010-02-25
	 */
	public boolean isSPP(final BigInteger n, final BigInteger a) {
		final BigInteger two = new BigInteger("" + 2);

		/*
		 * numbers less than 2 are not prime
		 */
		if (n.compareTo(two) == -1)
			return false;
		/*
		 * 2 is prime
		 */
		else if (n.compareTo(two) == 0)
			return true;
		/*
		 * even numbers >2 are not prime
		 */
		else if (n.remainder(two).compareTo(BigInteger.ZERO) == 0)
			return false;
		else {
			/*
			 * q= n- 1 = d *2^s with d odd
			 */
			final BigInteger q = n.subtract(BigInteger.ONE);
			int s = q.getLowestSetBit();
			BigInteger d = q.shiftRight(s);

			/*
			 * test whether a^d = 1 (mod n)
			 */
			if (a.modPow(d, n).compareTo(BigInteger.ONE) == 0)
				return true;

			/*
			 * test whether a^(d*2^r) = -1 (mod n), 0<=r<s
			 */
			for (int r = 0; r < s; r++) {
				if (a.modPow(d.shiftLeft(r), n).compareTo(q) == 0)
					return true;
			}
			return false;
		}
	}

	/**
	 * Miller-Rabin primality tests.
	 * 
	 * @param n
	 *            The prime candidate
	 * @return -1 if n is a composite, 1 if it is a prime, 0 if it may be a prime.
	 * @since 2010-02-25
	 */
	public int millerRabin(final BigInteger n) {
		/*
		 * list of limiting numbers which fail tests on k primes, A014233 in the OEIS
		 */
		final String[] mr = { "2047", "1373653", "25326001", "3215031751",
				"2152302898747", "3474749660383", "341550071728321" };
		int mrLim = 0;
		while (mrLim < mr.length) {
			int l = n.compareTo(new BigInteger(mr[mrLim]));
			if (l < 0)
				break;
			/*
			 * if one of the pseudo-primes: this is a composite
			 */
			else if (l == 0)
				return -1;
			mrLim++;
		}
		/*
		 * cannot test candidates larger than the last in the mr list
		 */
		if (mrLim == mr.length)
			return 0;

		/*
		 * test the bases prime(1), prime(2) up to prime(mrLim+1)
		 */
		for (int p = 0; p <= mrLim; p++)
			if (isSPP(n, at(p)) == false)
				return -1;
		return 1;
	}

	/**
	 * return the ithprime
	 * 
	 * @param i
	 *            the zero-based index into the list of primes
	 * @return the ith prime. This is 2 if i=0, 3 if i=1 and so forth.
	 */
	public BigInteger at(int i) {
		/*
		 * If the current list is too small, increase in intervals of 5 until the list has at least i elements.
		 */
		while (i >= a.size()) {
			growto(nMax.add(new BigInteger("" + 5)));
		}
		return (a.elementAt(i));
	}

	/**
	 * return the count of primes <= n
	 * 
	 * @param n
	 *            the upper limit of the scan
	 * @return the ith prime. This is 2 if i=0, 3 if i=1 and so forth.
	 */
	public BigInteger pi(BigInteger n) {
		/*
		 * If the current list is too small, increase in intervals of 5 until the list has at least i elements.
		 */
		growto(n);
		BigInteger r = new BigInteger("0");
		for (int i = 0; i < a.size(); i++)
			if (a.elementAt(i).compareTo(n) <= 0)
				r = r.add(BigInteger.ONE);
		return r;
	}

	/**
	 * return the smallest prime larger than n
	 * 
	 * @param n
	 *            lower limit of the search
	 * @return the next larger prime.
	 * @since 2008-10-16
	 */
	public BigInteger nextprime(BigInteger n) {
		/* if n <=1, return 2 */
		if (n.compareTo(BigInteger.ONE) <= 0)
			return (a.elementAt(0));

		/*
		 * If the currently largest element in the list is too small, increase in intervals of 5 until the list has at
		 * least i elements.
		 */
		while (a.lastElement().compareTo(n) <= 0) {
			growto(nMax.add(new BigInteger("" + 5)));
		}
		for (int i = 0; i < a.size(); i++)
			if (a.elementAt(i).compareTo(n) == 1)
				return (a.elementAt(i));
		return (a.lastElement());
	}

	/**
	 * return the largest prime smaller than n
	 * 
	 * @param n
	 *            upper limit of the search
	 * @return the next smaller prime.
	 * @since 2008-10-17
	 */
	public BigInteger prevprime(BigInteger n) {
		/* if n <=2, return 0 */
		if (n.compareTo(BigInteger.ONE) <= 0)
			return BigInteger.ZERO;

		/*
		 * If the currently largest element in the list is too small, increase in intervals of 5 until the list has at
		 * least i elements.
		 */
		while (a.lastElement().compareTo(n) < 0)
			growto(nMax.add(new BigInteger("" + 5)));

		for (int i = 0; i < a.size(); i++)
			if (a.elementAt(i).compareTo(n) >= 0)
				return (a.elementAt(i - 1));
		return (a.lastElement());
	}

	/**
	 * extend the list of known primes up to n
	 * 
	 * @param n
	 *            the maximum integer known to be prime or not prime after the call.
	 */
	protected void growto(BigInteger n) {
		while (nMax.compareTo(n) == -1) {
			nMax = nMax.add(BigInteger.ONE);
			boolean isp = true;
			for (int p = 0; p < a.size(); p++) {
				/*
				 * Test the list of known primes only up to sqrt(n)
				 */
				if (a.get(p).multiply(a.get(p)).compareTo(nMax) == 1)
					break;

				/*
				 * The next case means that the p'th number in the list of known primes divides nMax and nMax cannot be
				 * a prime.
				 */
				if (nMax.remainder(a.get(p)).compareTo(BigInteger.ZERO) == 0) {
					isp = false;
					break;
				}
			}
			if (isp)
				a.add(nMax);
		}
	}

	/**
	 * Test program. Usage: java -cp . org.nevec.rjm.Prime n<br>
	 * This takes a single argument (n) and prints prime(n), the previous and next prime, and pi(n).
	 * 
	 * @since 2006-08-14
	 */
	public static void main(String[] args) throws Exception {
		Prime a = new Prime();
		int n = (new Integer(args[0])).intValue();
		if (n >= 1) {
			if (n >= 2)
				System.out.println("prime(" + (n - 1) + ") = " + a.at(n - 1));
			System.out.println("prime(" + n + ") = " + a.at(n));
			System.out.println("prime(" + (n + 1) + ") = " + a.at(n + 1));
			System.out.println("pi(" + n + ") = "
					+ a.pi(new BigInteger("" + n)));
		}
	}
} /* Prime */
