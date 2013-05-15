package net.objecthunter.exp4j.calculable;

import java.math.BigDecimal;
import java.util.List;

import net.objecthunter.exp4j.tokenizer.Token;

public class BigDecimalCalculable extends Calculable<BigDecimal> {

	public BigDecimalCalculable(List<Token> tokens) {
		super(tokens);
	}

	@Override
	public BigDecimal calculate() {
		return new BigDecimal("1238913812312312312312313123123123213123213123131311231232131");
	}

}
