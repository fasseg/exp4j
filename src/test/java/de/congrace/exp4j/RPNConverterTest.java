/*
   Copyright 2011 frank asseg

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package de.congrace.exp4j;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class RPNConverterTest {

	static Map<String, CustomFunction> customFunctions = new HashMap<String, CustomFunction>();

	static Map<String, CustomOperator> operations = new HashMap<String, CustomOperator>();

	static Map<String, Double> variables = new LinkedHashMap<String, Double>();

	@BeforeClass
	public static void setup() throws Exception {
		CustomFunction log = new CustomFunction("log") {
			@Override
			public double applyFunction(double... args) {
				return Math.log(args[0]);
			}
		};
		CustomFunction sine = new CustomFunction("sin") {
			@Override
			public double applyFunction(double... args) {
				return Math.sin(args[0]);
			}
		};
		customFunctions.put("log", log);
		customFunctions.put("sin", sine);

		CustomOperator add = new CustomOperator("+") {
			@Override
			double applyOperation(double[] values) {
				return values[0] + values[1];
			}
		};
		CustomOperator sub = new CustomOperator("-") {
			@Override
			double applyOperation(double[] values) {
				return values[0] - values[1];
			}
		};
		CustomOperator div = new CustomOperator("/", 2) {
			@Override
			double applyOperation(double[] values) {
				return values[0] / values[1];
			}
		};
		CustomOperator mul = new CustomOperator("*", 2) {
			@Override
			double applyOperation(double[] values) {
				return values[0] / values[1];
			}
		};
		CustomOperator umin = new CustomOperator("\'", false, 4) {
			@Override
			double applyOperation(double[] values) {
				return -values[0];
			}
		};
		operations.put("+", add);
		operations.put("-", sub);
		operations.put("*", mul);
		operations.put("/", div);
		operations.put("\'", umin);
	}

	@Test
	public void testInfixTranslation1() throws Exception {
		String expr = "2 + 2";
		String expected = "2 2 +";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation10() throws Exception {
		String expr = "log(1) / -sin(2)";
		String expected = "1 log 2 sin ' /";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation11() throws Exception {
		String expr = "24/log(1)-2";
		String expected = "24 1 log / 2 -";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation2() throws Exception {
		String expr = "1 + 2 * 4";
		String expected = "1 2 4 * +";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation3() throws Exception {
		String expr = "3 - 4 * 5";
		String expected = "3 4 5 * -";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation4() throws Exception {
		String expr = "(1+2) * 4";
		String expected = "1 2 + 4 *";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation5() throws Exception {
		String expr = "(1+2) * (3-4) * 4";
		String expected = "1 2 + 3 4 - * 4 *";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation6() throws Exception {
		String expr = "1.23 + 3.14";
		String expected = "1.23 3.14 +";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation7() throws Exception {
		String expr = "1.23 + 3.14 * 7";
		String expected = "1.23 3.14 7 * +";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation8() throws Exception {
		String expr = "log(1) + sin(2)";
		String expected = "1 log 2 sin +";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation9() throws Exception {
		String expr = "log(1) / sin(2)";
		String expected = "1 log 2 sin /";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}
}
