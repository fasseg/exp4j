package net.objecthunter.exp4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import net.objecthunter.exp4j.calculable.Calculable;
import net.objecthunter.exp4j.exceptions.UnparseableExpressionException;
import net.objecthunter.exp4j.function.CustomFunction;
import net.objecthunter.exp4j.operator.CustomOperator;
import net.objecthunter.exp4j.operator.Operators;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes"})
public class ExpressionBuilderComplexNumberTest {

	@Test
	public void testComplexNumberExpression1() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("2+2", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(4.0d, 0), result);
	}

	@Test
	public void testComplexNumberExpression2() throws Exception {
		ExpressionBuilder<ComplexNumber> e = new ExpressionBuilder<>("2+2i", ComplexNumber.class);
		ComplexNumber result = e.build().calculate();
		assertEquals(new ComplexNumber(2.0d, 2.0d), result);
	}

}
