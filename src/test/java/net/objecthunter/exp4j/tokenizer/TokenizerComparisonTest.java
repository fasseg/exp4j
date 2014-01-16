package net.objecthunter.exp4j.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class TokenizerComparisonTest {
	private final String expression = "1+6.77-14*sin(x)/log(y)-14*abs(2.445)--12*cos(x)-sqrt(x)/(sin(x)*cos(x)*cos(x/2)";

	@Test
	public void testTokenizer() throws Exception {
		Tokenizer<Double> tok = new Tokenizer<>(Double.class);
		Set<String> variables = new HashSet<>(Arrays.asList("x", "y"));
		long time = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			List<Token> tokens = tok.tokenizeExpression(expression, variables,
					null, null);
		}
		System.out.println("Tokenizer took "
				+ (System.currentTimeMillis() - time) + "ms");
	}

	@Test
	public void testNextGenTokenizer() throws Exception {
		NextGenTokenizer<Double> tok = new NextGenTokenizer<>(Double.class);
		Set<String> variables = new HashSet<>(Arrays.asList("x", "y"));
		long time = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			List<Token> tokens = tok.tokenizeExpression(expression, variables,
					null, null);
		}
		System.out.println("NextGenTokenizer took "
				+ (System.currentTimeMillis() - time) + "ms");
	}

	@Test
	public void testFastTokenizer() throws Exception {
		String[] variables = { "x", "y" };
		String[] functions = { "sin", "cos", "abs", "log", "sqrt" };
		FastTokenizer tok = new FastTokenizer(expression, functions, variables);
		List<String> tokens = new ArrayList<>();
		long time = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			while (!tok.isEOF()) {
				tok.nextToken();
				int type = tok.getType();
				String val = tok.getTokenValue();
				tokens.add(tok.getTokenValue());
			}
		}
		System.out.println("FastTokenizer took "
				+ (System.currentTimeMillis() - time) + "ms");
		System.out.println(tokens);
	}
}
