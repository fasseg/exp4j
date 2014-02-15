package net.objecthunter.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class BigDecimalMath {

	public static BigDecimal sin(BigDecimal arg, MathContext mc) {
		// return known values
		if (arg == BigDecimal.ZERO) {
			return BigDecimal.ZERO;
		}
		if (arg == new BigDecimal(Math.PI)) {
			return BigDecimal.ONE;
		}

		return Cordic.sin(arg, mc);
	}

	public static BigDecimal cos(BigDecimal arg, MathContext mc) {
		// return known values
		if (arg == BigDecimal.ZERO) {
			return BigDecimal.ONE;
		}
		if (arg == new BigDecimal(Math.PI)) {
			return BigDecimal.ZERO;
		}
		return Cordic.cos(arg, mc);
	}

	public static BigDecimal tan(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal log(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal exp(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal log1p(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal asin(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal acos(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal atan(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal sinh(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal cosh(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal tanh(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal abs(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal sqrt(BigDecimal arg, MathContext mc) {
		// implement the babylonian method approximation
		MathContext mcp1 = new MathContext(mc.getPrecision() + 1);
		if (arg.signum() < 0) {
			throw new ArithmeticException("square root of a negative number");
		}
		if (arg.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO;
		}
		if (arg.compareTo(BigDecimal.ONE) == 0) {
			return BigDecimal.ONE;
		}
		BigDecimal result = sqrtGuess(arg, mc);
		BigDecimal precision = new BigDecimal(BigInteger.ONE, mc.getPrecision());
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			BigDecimal z = arg.divide(result, mcp1);
			if (result.subtract(z, mc).compareTo(precision) < 0) {
				return result;
			}
			result = result.add(z, mc).divide(new BigDecimal(2, mc), mc);
		}
		return result;
	}

	public static BigDecimal sqrtGuess(BigDecimal arg, MathContext mc) {
		double x = arg.doubleValue();
		double xhalf = 0.5d * x;
		long i = Double.doubleToLongBits(x);
		i = 0x5fe6ec85e7de30daL - (i >> 1);
		x = Double.longBitsToDouble(i);
		x = x * (1.5d - xhalf * x * x);
		return new BigDecimal(1d / x, mc);
	}

	public static BigDecimal cbrt(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal ceil(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal floor(BigDecimal arg, MathContext mc) {
		throw new IllegalArgumentException("Not Yet implemented");
	}

	public static BigDecimal max(BigDecimal... args) {
		BigDecimal max = args[0];
		int len = args.length;
		for (int i = 0; i < len; i++) {
			if (args[i].compareTo(max) == 1) {
				max = args[i];
			}
		}
		return max;
	}

	public static BigDecimal min(BigDecimal... args) {
		BigDecimal min = args[0];
		int len = args.length;
		for (int i = 0; i < len; i++) {
			if (args[i].compareTo(min) == -1) {
				min = args[i];
			}
		}
		return min;
	}
}