package net.objecthunter.math;

import java.math.BigDecimal;
import java.math.MathContext;

public class Cordic {
	public static BigDecimal K[] = new BigDecimal[] {
			new BigDecimal("0.7071067811865475"),
			new BigDecimal("0.6324555320336758"),
			new BigDecimal("0.6135719910778963"),
			new BigDecimal("0.6088339125177524"),
			new BigDecimal("0.6076482562561683"),
			new BigDecimal("0.607351770141296"),
			new BigDecimal("0.6072776440935261"),
			new BigDecimal("0.6072591122988928"),
			new BigDecimal("0.6072544793325625"),
			new BigDecimal("0.6072533210898753"),
			new BigDecimal("0.6072530315291345"),
			new BigDecimal("0.607252959138945"),
			new BigDecimal("0.6072529410413973"),
			new BigDecimal("0.6072529365170103"),
			new BigDecimal("0.6072529353859135"),
			new BigDecimal("0.6072529351031394"),
			new BigDecimal("0.6072529350324458"),
			new BigDecimal("0.6072529350147724"),
			new BigDecimal("0.607252935010354"),
			new BigDecimal("0.6072529350092495"),
			new BigDecimal("0.6072529350089734"),
			new BigDecimal("0.6072529350089043"),
			new BigDecimal("0.6072529350088871"),
			new BigDecimal("0.6072529350088828"),
			new BigDecimal("0.6072529350088817"),
			new BigDecimal("0.6072529350088814"),
	};

	public static BigDecimal sin(BigDecimal arg, MathContext mc) {

		BigDecimal[] v = new BigDecimal[] { BigDecimal.ONE, BigDecimal.ZERO };
		BigDecimal[] vi = new BigDecimal[2];
		BigDecimal beta = arg;
		BigDecimal currentAngle = BigDecimal.ZERO;
		BigDecimal angleDiff;

		int sigma;
		int powerOfTwo = 1;
		int i = 0;

		for (; i < 25; i++) {
			if ((angleDiff = (beta.subtract(currentAngle)))
					.compareTo(BigDecimal.ZERO) == 0d) {
				return v[1].multiply(K[i]);
			}
			sigma = (beta.subtract(currentAngle).compareTo(BigDecimal.ZERO) > 0) ? 1
					: -1;
			// calc the current angle
			currentAngle = currentAngle.add(new BigDecimal(sigma
					* Math.atan(1d / (double) powerOfTwo)));

			// rotate the vector
			BigDecimal factor = new BigDecimal(sigma).divide(new BigDecimal(powerOfTwo));
			vi[0] = v[0].subtract(factor.multiply(v[1]));
			vi[1] = factor.multiply(v[0]).add(v[1]);
			v[0] = vi[0];
			v[1] = vi[1];
			powerOfTwo = powerOfTwo * 2;
		}
		return v[1].multiply(K[i]);
	}

	public static BigDecimal cos(BigDecimal arg, MathContext mc) {
		throw new RuntimeException("Not yet Implemented");
	}
}
