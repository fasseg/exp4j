/*
 * Copyright 2014 Bartosz Firyn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.objecthunter.exp4j;

import net.objecthunter.exp4j.function.Function;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;


public class ExpressionValidateTest {

	/**
	 * Dummy function with 2 arguments.
	 */
	Function beta = new Function("beta", 2) {

		@Override
		public double apply(double... args) {
			return args[1] - args[0];
		}
	};

	/**
	 * Dummy function with 3 arguments.
	 */
	Function gamma = new Function("gamma", 3) {

		@Override
		public double apply(double... args) {
			return args[0] * args[1] / args[2];
		}
	};

	/**
	 * Dummy function with 7 arguments.
	 */
	Function eta = new Function("eta", 7) {

		@Override
		public double apply(double... args) {
			double eta = 0;
			for (double a : args) {
				eta += a;
			}
			return eta;
		}
	};

	// valid scenarios

	@Test
	public void testValidateNumber() throws Exception {
		Expression exp = new ExpressionBuilder("1")
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateNumberPositive() throws Exception {
		Expression exp = new ExpressionBuilder("+1")
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateNumberNegative() throws Exception {
		Expression exp = new ExpressionBuilder("-1")
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateOperator() throws Exception {
		Expression exp = new ExpressionBuilder("x + 1 + 2")
			.variable("x")
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateFunction() throws Exception {
		Expression exp = new ExpressionBuilder("sin(x)")
			.variable("x")
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateFunctionPositive() throws Exception {
		Expression exp = new ExpressionBuilder("+sin(x)")
			.variable("x")
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateFunctionNegative() throws Exception {
		Expression exp = new ExpressionBuilder("-sin(x)")
			.variable("x")
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateFunctionAndOperator() throws Exception {
		Expression exp = new ExpressionBuilder("sin(x + 1 + 2)")
			.variable("x")
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateFunctionWithTwoArguments() throws Exception {
		Expression exp = new ExpressionBuilder("beta(x, y)")
			.variables("x", "y")
			.functions(beta)
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateFunctionWithTwoArgumentsAndOperator() throws Exception {
		Expression exp = new ExpressionBuilder("beta(x, y + 1)")
			.variables("x", "y")
			.functions(beta)
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateFunctionWithThreeArguments() throws Exception {
		Expression exp = new ExpressionBuilder("gamma(x, y, z)")
			.variables("x", "y", "z")
			.functions(gamma)
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateFunctionWithThreeArgumentsAndOperator() throws Exception {
		Expression exp = new ExpressionBuilder("gamma(x, y, z + 1)")
			.variables("x", "y", "z")
			.functions(gamma)
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateFunctionWithTwoAndThreeArguments() throws Exception {
		Expression exp = new ExpressionBuilder("gamma(x, beta(y, h), z)")
			.variables("x", "y", "z", "h")
			.functions(gamma, beta)
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateFunctionWithTwoAndThreeArgumentsAndOperator() throws Exception {
		Expression exp = new ExpressionBuilder("gamma(x, beta(y, h), z + 1)")
			.variables("x", "y", "z", "h")
			.functions(gamma, beta)
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateFunctionWithTwoAndThreeArgumentsAndMultipleOperator() throws Exception {
		Expression exp = new ExpressionBuilder("gamma(x * 2 / 4, beta(y, h + 1 + 2), z + 1 + 2 + 3 + 4)")
			.variables("x", "y", "z", "h")
			.functions(gamma, beta)
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateFunctionWithSevenArguments() throws Exception {
		Expression exp = new ExpressionBuilder("eta(1, 2, 3, 4, 5, 6, 7)")
			.functions(eta)
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	@Test
	public void testValidateFunctionWithSevenArgumentsAndoperator() throws Exception {
		Expression exp = new ExpressionBuilder("eta(1, 2, 3, 4, 5, 6, 7) * 2 * 3 * 4")
			.functions(eta)
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertTrue(result.isValid());
	}

	// invalid scenarios

	@Test
	public void testValidateInvalidFunction() throws Exception {
		Expression exp = new ExpressionBuilder("sin()")
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertFalse(result.isValid());
	}

	@Test
	public void testValidateInvalidOperand() throws Exception {
		Expression exp = new ExpressionBuilder("1 + ")
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertFalse(result.isValid());
	}

	@Test
	public void testValidateInvalidFunctionWithTooFewArguments() throws Exception {
		Expression exp = new ExpressionBuilder("beta(1)")
			.functions(beta)
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertFalse(result.isValid());
	}

	@Test
	public void testValidateInvalidFunctionWithTooFewArgumentsAndOperands() throws Exception {
		Expression exp = new ExpressionBuilder("beta(1 + )")
			.functions(beta)
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertFalse(result.isValid());
	}

	@Test
	public void testValidateInvalidFunctionWithManyArguments() throws Exception {
		Expression exp = new ExpressionBuilder("beta(1, 2, 3)")
			.functions(beta)
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertFalse(result.isValid());
	}

	@Test
	public void testValidateInvalidOperator() throws Exception {
		Expression exp = new ExpressionBuilder("+")
			.build();
		ValidationResult result = exp.validate(false);
		Assert.assertFalse(result.isValid());
	}

	// Thanks go out to werwiesel for reporting the issue
	// https://github.com/fasseg/exp4j/issues/59
	@Test
	public void testNoArgFunctionValidation() throws Exception {
		Function now = new Function("now", 0) {
			@Override
			public double apply(double... args) {
				return (double) new Date().getTime();
			}
		};
		Expression e = new ExpressionBuilder("14*now()")
				.function(now)
				.build();
		assertTrue(e.validate().isValid());

		e = new ExpressionBuilder("now()")
				.function(now)
				.build();
		assertTrue(e.validate().isValid());

		e = new ExpressionBuilder("sin(now())")
				.function(now)
				.build();
		assertTrue(e.validate().isValid());

		e = new ExpressionBuilder("sin(now()) % 14")
				.function(now)
				.build();
		assertTrue(e.validate().isValid());
	}

}
