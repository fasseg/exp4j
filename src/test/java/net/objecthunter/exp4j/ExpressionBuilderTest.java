package net.objecthunter.exp4j;

import net.objecthunter.exp4j.function.Function;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ExpressionBuilderTest {
    @Test
    public void shouldBuildSimpleExpression() {
        final String term = "pow(2,2) * 2 + 1";
        final Expression e = new ExpressionBuilder(term)
                .build();
        assertThat(e.evaluate()).isEqualTo(9D);
    }

    @Test
    public void shouldBuildExpressionWithVariable() {
        final String term = "pow(2,x) * 2 + 1";
        final Expression e = new ExpressionBuilder(term)
                .build();
        e.setVariable("x", 3);
        assertThat(e.evaluate()).isEqualTo(17D);
    }

    @Test
    public void shouldNotBuildExpressionWithMissingVariable() {
        final String term = "pow(2,x) * 2 + 1";
        final Expression e = new ExpressionBuilder(term)
                .build();
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(e::evaluate);
    }

    @Test
    public void shouldBuildExpressionWithFunction() {
        final String term = "mult(2,3) * 2 + 1";
        final Function mult = new Function("mult", 2) {
            @Override
            public double apply(final double... args) {
                if (args.length != 2) {
                    throw new IllegalArgumentException("foo function takes two variables");
                }
                return args[0] * args [1];
            }
        };
        final Expression e = new ExpressionBuilder(term)
                .function(mult)
                .build();
        assertThat(e.evaluate()).isEqualTo(13D);
    }

    @Test
    public void shouldNotBuildExpressionWithMissingFunction() {
        final String term = "foo(x) * 2 + 1";
        final Expression e = new ExpressionBuilder(term)
                .build();
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(e::evaluate);
    }
}
