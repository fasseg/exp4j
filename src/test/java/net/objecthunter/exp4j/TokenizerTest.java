package net.objecthunter.exp4j;

import net.objecthunter.exp4j.exception.Tokenizer;
import net.objecthunter.exp4j.model.Symbol;
import org.junit.Test;

import java.util.List;

public class TokenizerTest {
	
	@Test
	public void testTokenizer() {
		final String e = "1+1";
		final List<Symbol> symbols = new Tokenizer(e, null, null, null).tokenize();
		System.out.println(SymbolsUtil.toString(symbols));
	}
}
