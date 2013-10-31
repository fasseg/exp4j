package net.objecthunter.exp4j.calculable;

import java.math.BigDecimal;
import java.util.List;

import net.objecthunter.exp4j.tokenizer.Token;

public class BigDecimalCalculable extends Calculable<BigDecimal> {

	public BigDecimalCalculable(List<Token> tokens) {
		super(tokens);
	}

}
