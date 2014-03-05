package de.congrace.exp4j;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ConcurrentExpressionBuilderTest.class,
		ExpressionBuilderTest.class, ExpressionUtilTest.class,
		RPNConverterTest.class, TokenizerTest.class })
public class AllTests {

}
