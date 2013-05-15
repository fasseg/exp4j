package net.objecthunter.exp4j.function;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.objecthunter.exp4j.ComplexNumber;

public class Functions {
	private static Map<String, CustomFunction> builtin = new HashMap<String, CustomFunction>();

	static {
		/* sine */
		builtin.put("sin", new CustomFunction("sin") {

			@Override
			public Object apply(Object... args) {
				Object arg = args[0];
				if (arg instanceof Float) {
					return (float) Math.sin(((Float) arg).doubleValue());
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
					return (float) Math.cos(((Float) arg).doubleValue());
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
					return (float) Math.tan(((Float) arg).doubleValue());
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
					return Math.abs(((Float) arg).doubleValue());
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
					return (float) Math.log(((Float) arg).doubleValue());
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
					return (float) Math.log10(((Float) arg).doubleValue());
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
					return (float) Math.ceil(((Float) arg).doubleValue());
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
					return (float) Math.floor(((Float) arg).doubleValue());
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
					return (float) Math.exp(((Float) arg).doubleValue());
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
		builtin.put("sqrt", new CustomFunction("sqrt") {

			@Override
			public Object apply(Object... args) {
				Object arg = args[0];
				if (arg instanceof Float) {
					return (float) Math.sqrt(((Float) arg).doubleValue());
				} else if (arg instanceof Double) {
					return Math.sqrt((double) arg);
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
