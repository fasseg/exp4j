package net.objecthunter.exp4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Functions {
	private static Map<String, CustomFunction> builtin = new HashMap<String, CustomFunction>();

	static {
		/* sine */
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

		builtin.put("cos", new CustomFunction("cos") {

			@Override
			public Object apply(Object... args) {
				Object arg = args[0];
				if (arg instanceof Float) {
					return Math.cos((double) arg);
				} else if (arg instanceof Double) {
					return Math.cos((double) arg);
				} else if (arg instanceof BigDecimal) {
					return Math.cos(((BigDecimal)arg).doubleValue());
				} else if (arg instanceof ComplexNumber) {
					throw new RuntimeException("no supprt for complex");
				} else {
					throw new RuntimeException("unknown type " + arg.getClass().getName());
				}
			}
		});

		builtin.put("tan", new CustomFunction("tan") {

			@Override
			public Object apply(Object... args) {
				Object arg = args[0];
				if (arg instanceof Float) {
					return Math.tan((double) arg);
				} else if (arg instanceof Double) {
					return Math.tan((double) arg);
				} else if (arg instanceof BigDecimal) {
					return Math.tan(((BigDecimal)arg).doubleValue());
				} else if (arg instanceof ComplexNumber) {
					throw new RuntimeException("no supprt for complex");
				} else {
					throw new RuntimeException("unknown type " + arg.getClass().getName());
				}
			}
		});
		builtin.put("abs", new CustomFunction("abs") {

			@Override
			public Object apply(Object... args) {
				Object arg = args[0];
				if (arg instanceof Float) {
					return Math.abs((float)arg);
				} else if (arg instanceof Double) {
					return Math.abs((double) arg);
				} else if (arg instanceof BigDecimal) {
					return ((BigDecimal)arg).abs();
				} else if (arg instanceof ComplexNumber) {
					throw new RuntimeException("no supprt for complex");
				} else {
					throw new RuntimeException("unknown type " + arg.getClass().getName());
				}
			}
		});
		builtin.put("log", new CustomFunction("log") {

			@Override
			public Object apply(Object... args) {
				Object arg = args[0];
				if (arg instanceof Float) {
					return Math.log((float)arg);
				} else if (arg instanceof Double) {
					return Math.log((double) arg);
				} else if (arg instanceof BigDecimal) {
					throw new RuntimeException("no supprt for big decimal");
				} else if (arg instanceof ComplexNumber) {
					throw new RuntimeException("no supprt for complex");
				} else {
					throw new RuntimeException("unknown type " + arg.getClass().getName());
				}
			}
		});
		builtin.put("log10", new CustomFunction("log10") {

			@Override
			public Object apply(Object... args) {
				Object arg = args[0];
				if (arg instanceof Float) {
					return Math.log10((float)arg);
				} else if (arg instanceof Double) {
					return Math.log10((double) arg);
				} else if (arg instanceof BigDecimal) {
					throw new RuntimeException("no supprt for big decimal");
				} else if (arg instanceof ComplexNumber) {
					throw new RuntimeException("no supprt for complex");
				} else {
					throw new RuntimeException("unknown type " + arg.getClass().getName());
				}
			}
		});
		builtin.put("ceil", new CustomFunction("ceil") {

			@Override
			public Object apply(Object... args) {
				Object arg = args[0];
				if (arg instanceof Float) {
					return Math.ceil((float)arg);
				} else if (arg instanceof Double) {
					return Math.ceil((double) arg);
				} else if (arg instanceof BigDecimal) {
					throw new RuntimeException("no supprt for bigdecimal");
				} else if (arg instanceof ComplexNumber) {
					throw new RuntimeException("no supprt for complex");
				} else {
					throw new RuntimeException("unknown type " + arg.getClass().getName());
				}
			}
		});
		builtin.put("floor", new CustomFunction("floor") {

			@Override
			public Object apply(Object... args) {
				Object arg = args[0];
				if (arg instanceof Float) {
					return Math.floor((float)arg);
				} else if (arg instanceof Double) {
					return Math.floor((double) arg);
				} else if (arg instanceof BigDecimal) {
					throw new RuntimeException("no support for big decimal");
				} else if (arg instanceof ComplexNumber) {
					throw new RuntimeException("no support for complex");
				} else {
					throw new RuntimeException("unknown type " + arg.getClass().getName());
				}
			}
		});
		builtin.put("abs", new CustomFunction("abs") {

			@Override
			public Object apply(Object... args) {
				Object arg = args[0];
				if (arg instanceof Float) {
					return Math.exp((float)arg);
				} else if (arg instanceof Double) {
					return Math.exp((double) arg);
				} else if (arg instanceof BigDecimal) {
					throw new RuntimeException("no support for big decimal");
				} else if (arg instanceof ComplexNumber) {
					throw new RuntimeException("no supprt for complex");
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
