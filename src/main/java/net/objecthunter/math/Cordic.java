package net.objecthunter.math;

import java.math.BigDecimal;
import java.math.MathContext;

public class Cordic {
	public static BigDecimal K[] = new BigDecimal[] {
		new BigDecimal("0.7071067811865475244008443621048491", MathContext.DECIMAL128),
		new BigDecimal("0.6324555320336758663997787088865439", MathContext.DECIMAL128),
		new BigDecimal("0.6135719910778963496078090877580412", MathContext.DECIMAL128),
		new BigDecimal("0.6088339125177524210221135075473903", MathContext.DECIMAL128),
		new BigDecimal("0.6076482562561682009293216603095235", MathContext.DECIMAL128),
		new BigDecimal("0.6073517701412959590535123903877649", MathContext.DECIMAL128),
		new BigDecimal("0.6072776440935259990469153673375897", MathContext.DECIMAL128),
		new BigDecimal("0.6072591122988927300602945418225043", MathContext.DECIMAL128),
		new BigDecimal("0.6072544793325623297173980863251574", MathContext.DECIMAL128),
		new BigDecimal("0.6072533210898751633434351985637673", MathContext.DECIMAL128),
		new BigDecimal("0.6072530315291343354022846546615289", MathContext.DECIMAL128),
		new BigDecimal("0.6072529591389448136303517976375718", MathContext.DECIMAL128),
		new BigDecimal("0.6072529410413971635129701864241042", MathContext.DECIMAL128),
		new BigDecimal("0.6072529365170102341289712420797396", MathContext.DECIMAL128),
		new BigDecimal("0.6072529353859135007295556027452776", MathContext.DECIMAL128),
		new BigDecimal("0.6072529351031393173138631980695442", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350324457714558251909590865", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350147723849910585075599099", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350103540383748507628587645", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350092494517207978220677687", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350089733050572845240815379", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350089042683914061956607000", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088870092249366133102230", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088826944333192177072746", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088816157354148688055794", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088813460609387815800959", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812786423197597737211", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812617876650043221272", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812575740013154592287", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812565205853932435041", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812562572314126895728", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812561913929175510901", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812561749332937664696", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812561708183878203145", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812561697896613337759", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812561695324797121413", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812561694681843067325", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812561694521104553806", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812561694480919925426", MathContext.DECIMAL128),
		new BigDecimal("0.6072529350088812561694470873768332", MathContext.DECIMAL128),
	};
	
	public static BigDecimal A[] = new BigDecimal[] {
		new BigDecimal("0.7853981633974482789994908671360463", MathContext.DECIMAL128),
		new BigDecimal("0.2449786631268641434733268624768243", MathContext.DECIMAL128),
		new BigDecimal("0.1106572211738956340587591853363847", MathContext.DECIMAL128),
		new BigDecimal("0.0624188099959573500230547438150097", MathContext.DECIMAL128),
		new BigDecimal("0.0399786871232900437034274432335224", MathContext.DECIMAL128),
		new BigDecimal("0.0277706365934210358537015395086200", MathContext.DECIMAL128),
		new BigDecimal("0.0204053306865380860990466516113884", MathContext.DECIMAL128),
		new BigDecimal("0.0156237286204768312941615349132007", MathContext.DECIMAL128),
		new BigDecimal("0.0123450518442244881744951356949969", MathContext.DECIMAL128),
		new BigDecimal("0.0099996666866652376276514146979935", MathContext.DECIMAL128),
		new BigDecimal("0.0082642746596511603568435688771388", MathContext.DECIMAL128),
		new BigDecimal("0.0069443328150155508166063711428251", MathContext.DECIMAL128),
		new BigDecimal("0.0059170907060273249949977625306019", MathContext.DECIMAL128),
		new BigDecimal("0.0051019965469150732606529174972820", MathContext.DECIMAL128),
		new BigDecimal("0.0044444151809595374563732761430401", MathContext.DECIMAL128),
		new BigDecimal("0.0039062301319669717573901390750279", MathContext.DECIMAL128),
		new BigDecimal("0.0034601938028250176990208153426920", MathContext.DECIMAL128),
		new BigDecimal("0.0030864099527443968583317879250671", MathContext.DECIMAL128),
		new BigDecimal("0.0027700760172437058174998991688653", MathContext.DECIMAL128),
		new BigDecimal("0.0024999947916861976759950181303793", MathContext.DECIMAL128),
		new BigDecimal("0.0022675698096185910139499508630934", MathContext.DECIMAL128),
		new BigDecimal("0.0020661127625184834075144912901578", MathContext.DECIMAL128),
		new BigDecimal("0.0018903569165405651689854105157451", MathContext.DECIMAL128),
		new BigDecimal("0.0017361093668539701647540729467778", MathContext.DECIMAL128),
		new BigDecimal("0.0015999986346687639863356489300372", MathContext.DECIMAL128),
		new BigDecimal("0.0014792888617870532212694723028790", MathContext.DECIMAL128),
		new BigDecimal("0.0013717412520922271906148592890418", MathContext.DECIMAL128),
		new BigDecimal("0.0012755095123619503670597818967281", MathContext.DECIMAL128),
		new BigDecimal("0.0011890600817027303318790254493820", MathContext.DECIMAL128),
		new BigDecimal("0.0011111106538640789891114746268386", MathContext.DECIMAL128),
		new BigDecimal("0.0010405823507416904309674787754147", MathContext.DECIMAL128),
		new BigDecimal("0.0009765621895593194594364927496599", MathContext.DECIMAL128),
		new BigDecimal("0.0009182733874422829498107567758325", MathContext.DECIMAL128),
		new BigDecimal("0.0008650516873372378345852240499880", MathContext.DECIMAL128),
		new BigDecimal("0.0008163263492819760941063278458785", MathContext.DECIMAL128),
		new BigDecimal("0.0007716047851404402458525000341183", MathContext.DECIMAL128),
		new BigDecimal("0.0007304600600019678542651480057657", MathContext.DECIMAL128),
		new BigDecimal("0.0006925206649157694335783297390208", MathContext.DECIMAL128),
		new BigDecimal("0.0006574621011929812283702823094700", MathContext.DECIMAL128),
		new BigDecimal("0.0006249999186198107502601684082322", MathContext.DECIMAL128)
	};

	public static BigDecimal sin(BigDecimal arg, MathContext mc) {
		// TODO: reduce the arg to 0 <= arg <= 2*pi

		if (arg.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO;
		}
		
		if (arg.compareTo(new BigDecimal(Math.PI)) == 0) {
			return BigDecimal.ZERO;
		}

		if (arg.compareTo(new BigDecimal(2 * Math.PI)) == 0) {
			return BigDecimal.ZERO;
		}

		if (arg.compareTo(new BigDecimal(Math.PI/2d)) == 0) {
			return BigDecimal.ONE;
		}
		
		if (arg.compareTo(new BigDecimal(3d * Math.PI / 2d)) == 0) {
			return BigDecimal.ONE.negate();
		}

		BigDecimal[] v = new BigDecimal[] { BigDecimal.ONE, BigDecimal.ZERO };
		BigDecimal[] vi = new BigDecimal[2];
		BigDecimal beta = arg;
		BigDecimal currentAngle = BigDecimal.ZERO;
		BigDecimal angleDiff;

		
		int sigma;
		int powerOfTwo = 1;
		int i = 0;

		for (; i < 25; i++) {
			if ((angleDiff = (beta.subtract(currentAngle,mc)))
					.compareTo(BigDecimal.ZERO) == 0d) {
				return v[1].multiply(K[i],mc);
			}
			sigma = (beta.subtract(currentAngle,mc).compareTo(BigDecimal.ZERO) > 0) ? 1
					: -1;
			// calc the current angle
			currentAngle = currentAngle.add(new BigDecimal(sigma,mc).multiply(A[i],mc),mc);

			// rotate the vector
			BigDecimal factor = new BigDecimal(sigma,mc).divide(new BigDecimal(powerOfTwo),mc);
			vi[0] = v[0].subtract(factor.multiply(v[1],mc),mc);
			vi[1] = factor.multiply(v[0],mc).add(v[1],mc);
			v[0] = vi[0];
			v[1] = vi[1];
			powerOfTwo = powerOfTwo * 2;
		}
		return v[1].multiply(K[i],mc);
	}

	public static BigDecimal cos(BigDecimal arg, MathContext mc) {
		throw new RuntimeException("Not yet Implemented");
	}
}
