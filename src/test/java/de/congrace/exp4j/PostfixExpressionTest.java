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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;

import org.junit.Test;

public class PostfixExpressionTest {
	private PostfixExpression pe;

	@Test
	public void testBench1() throws Exception {
		double factor;
		String expr = "foo(x,y)=log(x) - y * (cbrt(x^cos(y)))";
		int xMax = 100, yMax = 1000;
		pe = PostfixExpression.fromInfix(expr);
		long time = System.currentTimeMillis();
		for (int x = 0; x < xMax; x++) {
			for (int y = 0; y < yMax; y++) {
				pe.calculate(x, y);
			}
		}
		time = System.currentTimeMillis() - time;
		factor = (double) time;
		System.out.println("\n:: [PostfixExpression] simple benchmark");
		System.out.println("expression\t\t" + expr);
		System.out.println("num calculations\t" + xMax*yMax);
		System.out.println("exp4j\t\t\t~" + time + " ms");
		time = System.currentTimeMillis();
		@SuppressWarnings("unused")
		double val;
		for (int x = 0; x < xMax; x++) {
			for (int y = 0; y < yMax; y++) {
				val = Math.log(x) - y * (Math.cbrt(Math.pow(x, Math.cos(y))));
			}
		}
		time = System.currentTimeMillis() - time;
		System.out.println("Java Math\t\t~" + time + " ms");
		System.out.println("factor\t\t\t" + DecimalFormat.getInstance().format(factor / (double) time) + "\n");
	}

	@Test
	public void testExpression1() throws Exception {
		String expr;
		double expected;
		expr = "2 + 4";
		expected = 6d;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression10() throws Exception {
		String expr;
		double expected;
		expr = "1 * 1.5 + 1";
		expected = 1 * 1.5 + 1;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression11() throws Exception {
		double x = 1d;
		double y = 2d;
		String expr = "f(x,y)=log(x) ^ sin(y)";
		double expected = Math.pow(Math.log(x), Math.sin(y));
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x, y));
	}

	@Test
	public void testExpression12() throws Exception {
		String expr = "log(2.5333333333)^(0-1)";
		double expected = Math.pow(Math.log(2.5333333333d), -1);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression13() throws Exception {
		String expr = "2.5333333333^(0-1)";
		double expected = Math.pow(2.5333333333d, -1);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression14() throws Exception {
		String expr = "2 * 17.41 + (12*2)^(0-1)";
		double expected = 2 * 17.41d + Math.pow((12 * 2), -1);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression15() throws Exception {
		String expr = "2.5333333333 * 17.41 + (12*2)^log(2.764)";
		double expected = 2.5333333333d * 17.41d + Math.pow((12 * 2), Math.log(2.764d));
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression16() throws Exception {
		String expr = "2.5333333333/2 * 17.41 + (12*2)^(log(2.764) - sin(5.6664))";
		double expected = 2.5333333333d / 2 * 17.41d + Math.pow((12 * 2), Math.log(2.764d) - Math.sin(5.6664d));
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression17() throws Exception {
		String expr = "f(x,y)=x^2 - 2 * y";
		double x = Math.E;
		double y = Math.PI;
		double expected = x * x - 2 * y;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x, y));
	}

	@Test
	public void testExpression18() throws Exception {
		String expr = "-3";
		double expected = -3;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression19() throws Exception {
		String expr = "-3 * -24.23";
		double expected = -3 * -24.23d;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression2() throws Exception {
		String expr;
		double expected;
		expr = "2+3*4-12";
		expected = 2 + 3 * 4 - 12;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression20() throws Exception {
		String expr = "-2 * 24/log(2) -2";
		double expected = -2 * 24 / Math.log(2) - 2;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression21() throws Exception {
		String expr = "f(x)=-2 *33.34/log(x)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x));
	}

	@Test
	public void testExpression22() throws Exception {
		String expr = "MyFG(x)=-2 *33.34/log(x)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x));
	}

	@Test
	public void testExpression23() throws Exception {
		String expr = "MyFG(foo)=-2 *33.34/(log(foo)^-2 + 14 *6) - sin(foo)";
		double x = 1.334d;
		double expected = -2 * 33.34 / (Math.pow(Math.log(x), -2) + 14 * 6) - Math.sin(x);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x));
	}

	@Test
	public void testExpression24() throws Exception {
		String expr = "3+4-log(23.2)^(2-1) * -1";
		double expected = 3 + 4 - Math.pow(Math.log(23.2), (2 - 1)) * -1;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression25() throws Exception {
		String expr = "+3+4-+log(23.2)^(2-1) * + 1";
		double expected = 3 + 4 - Math.log(23.2d);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression26() throws Exception {
		String expr = "14 + -(1 / 2.22^3)";
		double expected = 14 + -(1d / Math.pow(2.22d, 3d));
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression27() throws Exception {
		String expr = "12^-+-+-+-+-+-+---2";
		double expected = Math.pow(12, -2);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression28() throws Exception {
		String expr = "12^-+-+-+-+-+-+---2 * (-14) / 2 ^ -log(2.22323) ";
		double expected = Math.pow(12, -2) * -14 / Math.pow(2, -Math.log(2.22323));
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression29() throws Exception {
		String expr = "24.3343 % 3";
		double expected = 24.3343 % 3;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression3() throws Exception {
		String expr;
		double expected;
		expr = "2+4*5";
		expected = 2 + 4 * 5;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression30() throws Exception {
		String expr = "24.3343 % 3 * 20 ^ -(2.334 % log(2 / 14))";
		double expected = 24.3343d % 3 * Math.pow(20, -(2.334 % Math.log(2d / 14d)));
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression31() throws Exception {
		String expr = "f(y_x)=-2 *33.34/log(y_x)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x));
	}

	@Test
	public void testExpression32() throws Exception {
		String expr = "f(y_2x)=-2 *33.34/log(y_2x)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x));
	}

	@Test
	public void testExpression33() throws Exception {
		String expr = "f(_y)=-2 *33.34/log(_y)^-2 + 14 *6";
		double x = 1.334d;
		double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x));
	}

	@Test
	public void testExpression34() throws Exception {
		String expr = "-2 + + (+4) +(4)";
		double expected = -2 + 4 + 4;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression4() throws Exception {
		String expr;
		double expected;
		expr = "2+4 * 5";
		expected = 2 + 4 * 5;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression5() throws Exception {
		String expr;
		double expected;
		expr = "(2+4)*5";
		expected = (2 + 4) * 5;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression6() throws Exception {
		String expr;
		double expected;
		expr = "(2+4)*5 + 2.5*2";
		expected = (2 + 4) * 5 + 2.5 * 2;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression7() throws Exception {
		String expr;
		double expected;
		expr = "(2+4)*5 + 10/2";
		expected = (2 + 4) * 5 + 10 / 2;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression8() throws Exception {
		String expr;
		double expected;
		expr = "(2 * 3 +4)*5 + 10/2";
		expected = (2 * 3 + 4) * 5 + 10 / 2;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testExpression9() throws Exception {
		String expr;
		double expected;
		expr = "(2 * 3 +4)*5 +4 + 10/2";
		expected = (2 * 3 + 4) * 5 + 4 + 10 / 2;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test(expected = UnparsableExpressionException.class)
	public void testFailUnknownFunction1() throws Exception {
		String expr;
		expr = "lig(1)";
		pe = PostfixExpression.fromInfix(expr);
		PostfixExpression.fromInfix(expr).calculate();
	}

	@Test(expected = UnparsableExpressionException.class)
	public void testFailUnknownFunction2() throws Exception {
		String expr;
		expr = "galength(1)";
		pe = PostfixExpression.fromInfix(expr);
		PostfixExpression.fromInfix(expr).calculate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFailUnknownFunction3() throws Exception {
		String expr;
		expr = "f(cos) = cos(cos)";
		pe = PostfixExpression.fromInfix(expr);
		PostfixExpression.fromInfix(expr).calculate();
	}

	@Test
	public void testFunction1() throws Exception {
		String expr;
		expr = "f(cos_1) = cos(cos_1)";
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(pe.calculate(1d) == Math.cos(1d));
	}

	@Test
	public void testGetExpression() throws Exception {
		String expr;
		expr = "2 + 4 - cbrt(2^3)";
		String expected = "2 4 + 2 3 ^ cbrt -";
		pe = PostfixExpression.fromInfix(expr);
		assertEquals(expected, pe.getExpression());
	}

	@Test
	public void testGetNumberFormat() throws Exception {
		String expr;
		expr = "2 + 4";
		pe = PostfixExpression.fromInfix(expr);
		assertNotNull(pe.getNumberFormat());
	}

	@Test
	public void testPostfix1() throws Exception {
		String expr;
		double expected;
		expr = "2.2232^0.1";
		expected = Math.pow(2.2232d, 0.1d);
		double actual = PostfixExpression.fromInfix(expr).calculate();
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == actual);
	}

	@Test
	public void testPostfixEverything() throws Exception {
		String expr;
		double expected;
		expr = "(sin(12) + log(34)) * 3.42 - cos(2.234-log(2))";
		expected = (Math.sin(12) + Math.log(34)) * 3.42 - Math.cos(2.234 - Math.log(2));
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testPostfixExponentation1() throws Exception {
		String expr;
		double expected;
		expr = "2^3";
		expected = Math.pow(2, 3);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testPostfixExponentation2() throws Exception {
		String expr;
		double expected;
		expr = "24 + 4 * 2^3";
		expected = 24 + 4 * Math.pow(2, 3);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testPostfixExponentation3() throws Exception {
		String expr;
		double expected;
		double x = 4.334d;
		expr = "f(x)=24 + 4 * 2^x";
		expected = 24 + 4 * Math.pow(2, x);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x));
	}

	@Test
	public void testPostfixExponentation4() throws Exception {
		String expr;
		double expected;
		double x = 4.334d;
		expr = "f(x)=(24 + 4) * 2^log(x)";
		expected = (24 + 4) * Math.pow(2, Math.log(x));
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x));
	}

	@Test
	public void testPostfixFunction1() throws Exception {
		String expr;
		double expected;
		expr = "log(1) * sin(0)";
		expected = Math.log(1) * Math.sin(0);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testPostfixFunction10() throws Exception {
		String expr;
		double expected;
		expr = "f(x)=cbrt(x)";
		pe = PostfixExpression.fromInfix(expr);
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.cbrt(x);
			assertTrue(expected == pe.calculate(x));
		}
	}

	@Test
	public void testPostfixFunction11() throws Exception {
		String expr;
		double expected;
		expr = "f(x)=cos(x) - (1/cbrt(x))";
		pe = PostfixExpression.fromInfix(expr);
		for (double x = -10; x < 10; x = x + 0.5d) {
			expected = Math.cos(x) - (1 / Math.cbrt(x));
			assertTrue(expected == pe.calculate(x));
		}
	}

	@Test
	public void testPostfixFunction2() throws Exception {
		String expr;
		double expected;
		expr = "log(1)";
		expected = 0d;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testPostfixFunction3() throws Exception {
		String expr;
		double expected;
		expr = "sin(0)";
		expected = 0d;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testPostfixFunction5() throws Exception {
		String expr;
		double expected;
		expr = "ceil(2.3) +1";
		expected = Math.ceil(2.3) + 1;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testPostfixFunction6() throws Exception {
		String expr;
		double expected;
		double x = 1.565d;
		double y = 2.1323d;
		expr = "f(x,y)=ceil(x) + 1 / y * abs(1.4)";
		expected = Math.ceil(x) + 1 / y * Math.abs(1.4);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x, y));
	}

	@Test
	public void testPostfixFunction7() throws Exception {
		String expr;
		double expected;
		double x = Math.E;
		expr = "f(x)=tan(x)";
		expected = Math.tan(x);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x));
	}

	@Test
	public void testPostfixFunction8() throws Exception {
		String expr;
		double expected;
		double e = Math.E;
		expr = "f(e)=2^3.4223232 + tan(e)";
		expected = Math.pow(2, 3.4223232d) + Math.tan(Math.E);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(e));
	}

	@Test
	public void testPostfixFunction9() throws Exception {
		String expr;
		double expected;
		double x = Math.E;
		expr = "f(x)=cbrt(x)";
		expected = Math.cbrt(x);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPostfixInvalidVariableName() throws Exception {
		String expr;
		double expected;
		double x = 4.5334332d;
		double log = Math.PI;
		expr = "f(x,log)=x * pi";
		expected = x * log;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x, log));
	}

	@Test
	public void testPostfixParanthesis() throws Exception {
		String expr;
		double expected;
		expr = "(3 + 3 * 14) * (2 * (24-17) - 14)/((34) -2)";
		expected = (3 + 3 * 14) * (2 * (24 - 17) - 14) / ((34) - 2);
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate());
	}

	@Test
	public void testPostfixVariables() throws Exception {
		String expr;
		double expected;
		double x = 4.5334332d;
		double pi = Math.PI;
		expr = "f(x,pi)=x * pi";
		expected = x * pi;
		pe = PostfixExpression.fromInfix(expr);
		assertTrue(expected == pe.calculate(x, pi));
	}
}