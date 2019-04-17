package net.objecthunter.exp4j;

import net.objecthunter.exp4j.model.Symbol;

import java.util.List;

public class SymbolsUtil {
	public static String toString(List<Symbol> symbols) {
		final StringBuilder builder = new StringBuilder();
		symbols.forEach(s -> {
			builder.append(s.getType() == Symbol.Type.NUMBER ? String.valueOf(s.getDoubleValue()) : s.getStringValue());
		});
		return builder.toString();
	}
}
