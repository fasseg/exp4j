package net.objecthunter.exp4j.calculable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.objecthunter.exp4j.tokenizer.Token;

public abstract class Calculable<T> {
	protected final List<Token> tokens;
	protected final Map<String,T> variables = new HashMap<String, T>();

	public Calculable(List<Token> tokens) {
		super();
		this.tokens = tokens;
	}

	public void setVariable(String name, T value){
		this.variables.put(name, value);
	}
	
	public abstract T calculate();
}
