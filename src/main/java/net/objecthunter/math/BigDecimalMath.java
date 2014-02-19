package net.objecthunter.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalMath {
	
	public static BigDecimal PI = new BigDecimal("3.14159265358979323846264338327950288419716939937510582097494459230781640628620899862803482534211706798214808651328230664709384460955058223172535940812848111745028410270193852110555964462294895493038196442881097566593344612847564823378678316527120190914565",new MathContext(255));
	public static BigDecimal E = new BigDecimal("2.71828182845904523536028747135266249775724709369995957496696762772407663035354759457138217852516642742746639193200305992181741359662904357290033429526059563073813232862794349076323382988075319525101901157383418793070215408914993488416750924476146066808226", new MathContext(255));
	public static BigDecimal TWO = BigDecimal.ONE.add(BigDecimal.ONE);
	public static BigDecimal sin(BigDecimal arg, MathContext mc) {
		// return known values
		if (arg == BigDecimal.ZERO) {
			return BigDecimal.ZERO;
		}
		if (arg.compareTo(PI) == 0) {
			return BigDecimal.ONE;
		}

		return Cordic.sin(arg, mc);
	}
	
	public static BigDecimal sinBySeries(BigDecimal arg, MathContext mc) {
		MathContext mcp1 = new MathContext(mc.getPrecision() + 1, RoundingMode.HALF_EVEN);
		BigDecimal pi2 = PI.multiply(TWO, mcp1);
		while (arg.abs().compareTo(pi2) >= 0) {
			if (arg.signum() < 0) {
				arg = arg.add(pi2, mcp1);
			}else {
				arg = arg.subtract(pi2, mcp1);
			}
		}
		// first element of the series
		BigDecimal sin = BigDecimal.ZERO;
		BigDecimal error = BigDecimal.ONE.scaleByPowerOfTen(-(mc.getPrecision()));
		for (int i = 0;i<Integer.MAX_VALUE;i++) {
			BigDecimal p = BigDecimal.ONE.negate().pow(i, mc);
			BigDecimal px = arg.pow(2*i+1, mc);
			BigDecimal f = factorial(2*i+1, mc);
			BigDecimal nth = p.multiply(px, mc).divide(f, mc);
			if (nth.abs().compareTo(error) < 0) {
				break;
			}
			sin = sin.add(nth, mc);
		}
		return sin;
	}

	private static BigDecimal factorial(int i, MathContext mc) {
		if (i == 0 || i == 1) {
			return BigDecimal.ONE;
		}
		return factorial(i-1, mc).multiply(new BigDecimal(i), mc);
	}

	public static BigDecimal cos(BigDecimal arg, MathContext mc) {
		// return known values
		if (arg == BigDecimal.ZERO) {
			return BigDecimal.ONE;
		}
		if (arg.compareTo(PI) == 0) {
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