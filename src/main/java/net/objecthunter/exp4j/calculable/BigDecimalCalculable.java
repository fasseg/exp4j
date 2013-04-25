package net.objecthunter.exp4j.calculable;

import java.math.BigDecimal;

public class BigDecimalCalculable implements Calculable<BigDecimal> {

	@Override
	public BigDecimal calculate() {
		return new BigDecimal("1238913812312312312312313123123123213123213123131311231232131");
	}

}
