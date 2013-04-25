package net.objecthunter.exp4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Functions {
	private static Map<String, CustomFunction> builtin = new HashMap<String, CustomFunction>();

	static {
		builtin.put("sin", new CustomFunction("sin") {

			@Override
			public Object apply(Object... args) {
				Object arg = args[0];
				if (arg instanceof Float) {
					return Math.sin((double) arg);
				} else if (arg instanceof Double) {
					return Math.sin((double) arg);
				} else if (arg instanceof BigDecimal) {
					return Math.sin(((BigDecimal)arg).doubleValue());
				} else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.sin((ComplexNumber) arg);
				} else {
					throw new RuntimeException("unknown type " + arg.getClass().getName());
				}
			}
		});
	}
	
	public static CustomFunction getFunction(String name){
		return builtin.get(name);
	}
}
