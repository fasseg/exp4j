package net.objecthunter.exp4j.model;

import java.util.HashMap;
import java.util.Map;

public class Functions {
	public final static Map<String, Function> BUILTIN_FUNCTIONS = new HashMap<>();
	
	static {
		BUILTIN_FUNCTIONS.put("sin", new Function());
		BUILTIN_FUNCTIONS.put("cos", new Function());
		BUILTIN_FUNCTIONS.put("tan", new Function());
		BUILTIN_FUNCTIONS.put("log", new Function());
	}
}
