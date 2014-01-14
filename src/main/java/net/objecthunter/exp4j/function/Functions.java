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
					return (float) Math.sin(((Float) arg).floatValue());
				} else if (arg instanceof Double) {
					return Math.sin((double) arg);
				} else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin sine function available for BigDecimal");
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
					return (float) Math.cos(((Float) arg).floatValue());
				} else if (arg instanceof Double) {
					return Math.cos((double) arg);
				} else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin cosine function available for BigDecimal");
				} else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.cos((ComplexNumber) arg);
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
					return (float) Math.tan(((Float) arg).floatValue());
				} else if (arg instanceof Double) {
					return Math.tan((double) arg);
				} else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin tan function available for BigDecimal");
				} else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.tan((ComplexNumber) arg);
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
					return Math.abs(((Float) arg).floatValue());
				} else if (arg instanceof Double) {
					return Math.abs((double) arg);
				} else if (arg instanceof BigDecimal) {
					return ((BigDecimal) arg).abs();
				} else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.abs((ComplexNumber) arg);
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
					return (float) Math.log(((Float) arg).floatValue());
				} else if (arg instanceof Double) {
					return Math.log((double) arg);
				} else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("no builtin log function available for big decimal");
				} else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.log((ComplexNumber) arg);
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
					return (float) Math.log10(((Float) arg).floatValue());
				} else if (arg instanceof Double) {
					return Math.log10((double) arg);
				} else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin log10 function available for BigDecimal");
				} else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.log10((ComplexNumber) arg);
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
					return (float) Math.ceil(((Float) arg).floatValue());
				} else if (arg instanceof Double) {
					return Math.ceil((double) arg);
				} else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin ceil function available for BigDecimal");
				} else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.ceil((ComplexNumber) arg);
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
					return (float) Math.floor(((Float) arg).floatValue());
				} else if (arg instanceof Double) {
					return Math.floor((double) arg);
				} else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin floor function available for BigDecimal");
				} else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.floor((ComplexNumber) arg);
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
					return (float) Math.sqrt(((Float) arg).floatValue());
				} else if (arg instanceof Double) {
					return Math.sqrt((double) arg);
				} else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin sqrt function available for BigDecimal");
				} else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.sqrt((ComplexNumber) arg);
				} else {
					throw new RuntimeException("unknown type " + arg.getClass().getName());
				}
			}
		});
        builtin.put("cbrt", new CustomFunction("cqrt") {

            @Override
            public Object apply(Object... args) {
                Object arg = args[0];
                if (arg instanceof Float) {
                    return (float) Math.cbrt(((Float) arg).floatValue());
                } else if (arg instanceof Double) {
                    return Math.cbrt((double) arg);
                } else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin cbrt function available for BigDecimal");
                } else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.cbrt((ComplexNumber) arg);
                } else {
                    throw new RuntimeException("unknown type " + arg.getClass().getName());
                }
            }
        });
        builtin.put("acos", new CustomFunction("acos") {

            @Override
            public Object apply(Object... args) {
                Object arg = args[0];
                if (arg instanceof Float) {
                    return (float) Math.acos(((Float) arg).floatValue());
                } else if (arg instanceof Double) {
                    return Math.acos((double) arg);
                } else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin acos function available for BigDecimal");
                } else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.acos((ComplexNumber) arg);
                } else {
                    throw new RuntimeException("unknown type " + arg.getClass().getName());
                }
            }
        });
        builtin.put("expm1", new CustomFunction("expm1") {

            @Override
            public Object apply(Object... args) {
                Object arg = args[0];
                if (arg instanceof Float) {
                    return (float) Math.expm1(((Float) arg).floatValue());
                } else if (arg instanceof Double) {
                    return Math.expm1((double) arg);
                } else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin expm1 function available for BigDecimal");
                } else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.expm1((ComplexNumber) arg);
                } else {
                    throw new RuntimeException("unknown type " + arg.getClass().getName());
                }
            }
        });
        builtin.put("asin", new CustomFunction("asin") {

            @Override
            public Object apply(Object... args) {
                Object arg = args[0];
                if (arg instanceof Float) {
                    return (float) Math.asin(((Float) arg).floatValue());
                } else if (arg instanceof Double) {
                    return Math.asin((double) arg);
                } else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin arcsine function available for BigDecimal");
                } else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.asin((ComplexNumber) arg);
                } else {
                    throw new RuntimeException("unknown type " + arg.getClass().getName());
                }
            }
        });
        builtin.put("exp", new CustomFunction("exp") {

            @Override
            public Object apply(Object... args) {
                Object arg = args[0];
                if (arg instanceof Float) {
                    return (float) Math.exp(((Float) arg).floatValue());
                } else if (arg instanceof Double) {
                    return Math.exp((double) arg);
                } else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin exp function available for BigDecimal");
                } else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.exp((ComplexNumber) arg);
                } else {
                    throw new RuntimeException("unknown type " + arg.getClass().getName());
                }
            }
        });
        builtin.put("sinh", new CustomFunction("sinh") {

            @Override
            public Object apply(Object... args) {
                Object arg = args[0];
                if (arg instanceof Float) {
                    return (float) Math.sinh(((Float) arg).floatValue());
                } else if (arg instanceof Double) {
                    return Math.sinh((double) arg);
                } else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin hyperbolic sine function available for BigDecimal");
                } else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.sinh((ComplexNumber) arg);
                } else {
                    throw new RuntimeException("unknown type " + arg.getClass().getName());
                }
            }
        });
        builtin.put("cosh", new CustomFunction("cosh") {

            @Override
            public Object apply(Object... args) {
                Object arg = args[0];
                if (arg instanceof Float) {
                    return (float) Math.cosh(((Float) arg).floatValue());
                } else if (arg instanceof Double) {
                    return Math.cosh((double) arg);
                } else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No hyperbolic cosine function available for BigDecimal");
                } else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.cosh((ComplexNumber) arg);
                } else {
                    throw new RuntimeException("unknown type " + arg.getClass().getName());
                }
            }
        });
        builtin.put("atan", new CustomFunction("atan") {

            @Override
            public Object apply(Object... args) {
                Object arg = args[0];
                if (arg instanceof Float) {
                    return (float) Math.atan(((Float) arg).floatValue());
                } else if (arg instanceof Double) {
                    return Math.atan((double) arg);
                } else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin arc tan function available for BigDecimal");
                } else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.atan((ComplexNumber) arg);
                } else {
                    throw new RuntimeException("unknown type " + arg.getClass().getName());
                }
            }
        });
        builtin.put("tanh", new CustomFunction("tanh") {

            @Override
            public Object apply(Object... args) {
                Object arg = args[0];
                if (arg instanceof Float) {
                    return (float) Math.tanh(((Float) arg).floatValue());
                } else if (arg instanceof Double) {
                    return Math.tanh((double) arg);
                } else if (arg instanceof BigDecimal) {
					throw new UnsupportedOperationException("No builtin hyperbolic tan function available for BigDecimal");
                } else if (arg instanceof ComplexNumber) {
					return ComplexNumberFunctions.tanh((ComplexNumber) arg);
                } else {
                    throw new RuntimeException("unknown type " + arg.getClass().getName());
                }
            }
        });
	}
	
	public static CustomFunction getBuiltinFunction(String name){
		return builtin.get(name);
	}
}
