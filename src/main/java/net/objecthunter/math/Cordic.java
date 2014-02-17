package net.objecthunter.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import javax.print.attribute.standard.PresentationDirection;

public class Cordic {
	public static BigDecimal K[] = new BigDecimal[]{
			new BigDecimal("0.7071067811865474617150084668537602",
					MathContext.DECIMAL128),
			new BigDecimal("0.6324555320336757713306496953009628",
					MathContext.DECIMAL128),
			new BigDecimal("0.6135719910778962837838435007142834",
					MathContext.DECIMAL128),
			new BigDecimal("0.6088339125177524291387953780940734",
					MathContext.DECIMAL128),
			new BigDecimal("0.6076482562561682509993943313020281",
					MathContext.DECIMAL128),
			new BigDecimal("0.6073517701412960434481647098436952",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072776440935261366149688910809346",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072591122988928447057332959957421",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072544793325624912228022367344238",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072533210898752864537186724191997",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072530315291344571448917122324929",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529591389449477034645497042220",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529410413972650317759871541057",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529365170102888527026152587496",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529353859135170523586566559970",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529351031393796134238982631359",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529350324458174981145930360071",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529350147723992137116511003114",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529350103540446426109156163875",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529350092494837554113473743200",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529350089733712891870709427167",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529350089043154170553862059023",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529350088871069601736962795258",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529350088827770903776581690181",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529350088816668673530330124777",
					MathContext.DECIMAL128),
			new BigDecimal("0.6072529350088814448227481079811696",
					MathContext.DECIMAL128)};

	// In GNU octave this table is generated using this command:
	// printf("%.34f\n",atan(2.^-(0:40)))
	public static BigDecimal A[] = new BigDecimal[]{
			new BigDecimal("0.7853981633974482789994908671360463",
					MathContext.DECIMAL128),
			new BigDecimal("0.4636476090008060935154787784995278",
					MathContext.DECIMAL128),
			new BigDecimal("0.2449786631268641434733268624768243",
					MathContext.DECIMAL128),
			new BigDecimal("0.1243549945467614381566789916178095",
					MathContext.DECIMAL128),
			new BigDecimal("0.0624188099959573500230547438150097",
					MathContext.DECIMAL128),
			new BigDecimal("0.0312398334302682774421544564802389",
					MathContext.DECIMAL128),
			new BigDecimal("0.0156237286204768312941615349132007",
					MathContext.DECIMAL128),
			new BigDecimal("0.0078123410601011111439873069173245",
					MathContext.DECIMAL128),
			new BigDecimal("0.0039062301319669717573901390750279",
					MathContext.DECIMAL128),
			new BigDecimal("0.0019531225164788187584341550007139",
					MathContext.DECIMAL128),
			new BigDecimal("0.0009765621895593194594364927496599",
					MathContext.DECIMAL128),
			new BigDecimal("0.0004882812111948982899262139412144",
					MathContext.DECIMAL128),
			new BigDecimal("0.0002441406201493617712447448120372",
					MathContext.DECIMAL128),
			new BigDecimal("0.0001220703118936702078530659454358",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000610351561742087725935014541623",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000305175781155260957271547345160",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000152587890613157615423778681873",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000076293945311019699810389967098",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000038146972656064961417507561819",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000019073486328101869647792853193",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000009536743164059608441276310632",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000004768371582030888422810640821",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000002384185791015579736676881098",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000001192092895507806808997385635",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000596046447753905522081060953",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000298023223876953025738326494",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000149011611938476545956387749",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000074505805969238281250000000",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000037252902984619140625000000",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000018626451492309570312500000",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000009313225746154785156250000",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000004656612873077392578125000",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000002328306436538696289062500",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000001164153218269348144531250",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000000582076609134674072265625",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000000291038304567337036132812",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000000145519152283668518066406",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000000072759576141834259033203",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000000036379788070917129516602",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000000018189894035458564758301",
					MathContext.DECIMAL128),
			new BigDecimal("0.0000000000009094947017729282379150",
					MathContext.DECIMAL128)};

	public static BigDecimal[] apply(BigDecimal arg, MathContext mc) {
		// TODO: reduce the arg to 0 <= arg <= 2*pi

		if (arg.compareTo(BigDecimal.ZERO) == 0) {
			return new BigDecimal[]{BigDecimal.ONE, BigDecimal.ZERO};
		}

		if (arg.compareTo(new BigDecimal(Math.PI)) == 0) {
			return new BigDecimal[]{BigDecimal.ONE.negate(), BigDecimal.ZERO};
		}

		if (arg.compareTo(new BigDecimal(2 * Math.PI)) == 0) {
			return new BigDecimal[]{BigDecimal.ONE, BigDecimal.ZERO};
		}

		if (arg.compareTo(new BigDecimal(Math.PI / 2d)) == 0) {
			return new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE};
		}

		if (arg.compareTo(new BigDecimal(3d * Math.PI / 2d)) == 0) {
			return new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE.negate()};
		}

		int i = 0;
		BigDecimal beta = arg;
		BigDecimal sigma;
		BigDecimal factor;
		BigDecimal powerOfTwo = BigDecimal.ONE;
		BigDecimal x = BigDecimal.ONE;
		BigDecimal y = BigDecimal.ZERO;
		BigDecimal angle = A[0];
		BigDecimal two = BigDecimal.ONE.add(BigDecimal.ONE);
		BigDecimal precision = new BigDecimal(BigInteger.ONE, mc.getPrecision() + 1);
		BigDecimal tmpx;
		BigDecimal tmpy;
		for (; i < Integer.MAX_VALUE; i++) {
			if (beta.compareTo(BigDecimal.ZERO) < 0) {
				sigma = BigDecimal.ONE.negate();
			} else {
				sigma = BigDecimal.ONE;
			}
			factor = sigma.multiply(powerOfTwo, mc);
			tmpx = x.subtract(y.multiply(factor, mc), mc);
			tmpy = y.add(x.multiply(factor, mc), mc);
			x = tmpx;
			y = tmpy;
			beta = beta.subtract(sigma.multiply(angle));
			powerOfTwo = powerOfTwo.divide(two, mc);
			if (i + 2 >  A.length) {
				angle = angle.divide(two);
			}else {
				angle = A[i + 1];
			}
			if (beta.abs().compareTo(precision) < 1) {
				i++;
				break;
			}
		}
		return new BigDecimal[] {x.multiply(K[Math.min(K.length - 1,i)], mc), y.multiply(K[Math.min(K.length - 1,i)], mc)};
	}

	public static BigDecimal cos(BigDecimal arg, MathContext mc) {
		return apply(arg, mc)[0];
	}

	public static BigDecimal sin(BigDecimal arg, MathContext mc) {
		return apply(arg, mc)[1];
	}
}
