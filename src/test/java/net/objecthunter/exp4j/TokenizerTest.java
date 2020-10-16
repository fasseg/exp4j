package net.objecthunter.exp4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenizerTest {

    @Test
    public void shouldTokenizeSimpleExpression() {
        final Token token = new Tokenizer("3.14", Collections.emptySet(), Collections.emptyMap(), Collections.emptyMap()).next();
        assertThat(token).isNotNull();
        assertThat(token.type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) token).value).isEqualTo(3.14D);
    }

    @Test
    public void shouldTokenizeSimpleAdditionExpression() {
        final Tokenizer tokenizer = new Tokenizer("3.14+16", Collections.emptySet(), Collections.emptyMap(), Collections.emptyMap());

        final List<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = tokenizer.next()) != null) {
            tokens.add(token);
        }

        assertThat(tokens.get(0)).isNotNull();
        assertThat(tokens.get(0).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(0)).value).isEqualTo(3.14D);
        assertThat(tokens.get(1)).isNotNull();
        assertThat(tokens.get(1).type).isEqualTo(Token.Type.OPERATOR);
        assertThat(((OperatorToken) tokens.get(1)).operator.symbol).isEqualTo("+");
        assertThat(tokens.get(2)).isNotNull();
        assertThat(tokens.get(2).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(2)).value).isEqualTo(16D);
    }

    @Test
    public void shouldTokenizeSimpleAdditionAndMultiplicationExpression() {
        final Tokenizer tokenizer = new Tokenizer("3.14+16*4.122130278641", Collections.emptySet(), Collections.emptyMap(), Collections.emptyMap());

        final List<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = tokenizer.next()) != null) {
            tokens.add(token);
        }

        assertThat(tokens.get(0)).isNotNull();
        assertThat(tokens.get(0).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(0)).value).isEqualTo(3.14D);
        assertThat(tokens.get(1)).isNotNull();
        assertThat(tokens.get(1).type).isEqualTo(Token.Type.OPERATOR);
        assertThat(((OperatorToken) tokens.get(1)).operator.symbol).isEqualTo("+");
        assertThat(tokens.get(2)).isNotNull();
        assertThat(tokens.get(2).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(2)).value).isEqualTo(16D);
        assertThat(tokens.get(3)).isNotNull();
        assertThat(tokens.get(3).type).isEqualTo(Token.Type.OPERATOR);
        assertThat(((OperatorToken) tokens.get(3)).operator.symbol).isEqualTo("*");
        assertThat(tokens.get(4)).isNotNull();
        assertThat(tokens.get(4).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(4)).value).isEqualTo(4.122130278641D);
    }

    @Test
    public void shouldTokenizeSimpleAdditionAndMultiplicationExpressionWithSpaces() {
        final Tokenizer tokenizer = new Tokenizer(" 3.14+ 16 * 4.122130278641 -14   ", Collections.emptySet(), Collections.emptyMap(), Collections.emptyMap());

        final List<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = tokenizer.next()) != null) {
            tokens.add(token);
        }

        assertThat(tokens.get(0)).isNotNull();
        assertThat(tokens.get(0).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(0)).value).isEqualTo(3.14D);
        assertThat(tokens.get(1)).isNotNull();
        assertThat(tokens.get(1).type).isEqualTo(Token.Type.OPERATOR);
        assertThat(((OperatorToken) tokens.get(1)).operator.symbol).isEqualTo("+");
        assertThat(tokens.get(2)).isNotNull();
        assertThat(tokens.get(2).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(2)).value).isEqualTo(16D);
        assertThat(tokens.get(3)).isNotNull();
        assertThat(tokens.get(3).type).isEqualTo(Token.Type.OPERATOR);
        assertThat(((OperatorToken) tokens.get(3)).operator.symbol).isEqualTo("*");
        assertThat(tokens.get(4)).isNotNull();
        assertThat(tokens.get(4).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(4)).value).isEqualTo(4.122130278641D);
        assertThat(tokens.get(5)).isNotNull();
        assertThat(tokens.get(5).type).isEqualTo(Token.Type.OPERATOR);
        assertThat(((OperatorToken) tokens.get(5)).operator.symbol).isEqualTo("-");
        assertThat(tokens.get(6)).isNotNull();
        assertThat(tokens.get(6).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(6)).value).isEqualTo(14D);
    }

    @Test
    public void shouldTokenizeSimpleAdditionAndParanthesesExpression() {
        final Tokenizer tokenizer = new Tokenizer("(3.14+16)", Collections.emptySet(), Collections.emptyMap(), Collections.emptyMap());

        final List<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = tokenizer.next()) != null) {
            tokens.add(token);
        }

        assertThat(tokens.get(0)).isNotNull();
        assertThat(tokens.get(0).type).isEqualTo(Token.Type.PARANTHESES);
        assertThat(((ParanthesesToken) tokens.get(0)).open).isEqualTo(true);
        assertThat(tokens.get(1)).isNotNull();
        assertThat(tokens.get(1).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(1)).value).isEqualTo(3.14D);
        assertThat(tokens.get(2)).isNotNull();
        assertThat(tokens.get(2).type).isEqualTo(Token.Type.OPERATOR);
        assertThat(((OperatorToken) tokens.get(2)).operator.symbol).isEqualTo("+");
        assertThat(tokens.get(3)).isNotNull();
        assertThat(tokens.get(3).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(3)).value).isEqualTo(16D);
        assertThat(tokens.get(4)).isNotNull();
        assertThat(tokens.get(4).type).isEqualTo(Token.Type.PARANTHESES);
        assertThat(((ParanthesesToken) tokens.get(4)).open).isEqualTo(false);
    }

    @Test
    public void shouldTokenizeFunctionExpression() {
        final Tokenizer tokenizer = new Tokenizer("abs(3.14)", Collections.emptySet(), Collections.emptyMap(), Collections.emptyMap());

        final List<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = tokenizer.next()) != null) {
            tokens.add(token);
        }

        assertThat(tokens.get(0)).isNotNull();
        assertThat(tokens.get(0).type).isEqualTo(Token.Type.FUNCTION);
        assertThat(tokens.get(1)).isNotNull();
        assertThat(tokens.get(1).type).isEqualTo(Token.Type.PARANTHESES);
        assertThat(((ParanthesesToken) tokens.get(1)).open).isEqualTo(true);
        assertThat(tokens.get(2)).isNotNull();
        assertThat(tokens.get(2).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(2)).value).isEqualTo(3.14D);
        assertThat(tokens.get(3)).isNotNull();
        assertThat(tokens.get(3).type).isEqualTo(Token.Type.PARANTHESES);
        assertThat(((ParanthesesToken) tokens.get(3)).open).isEqualTo(false);
    }

    @Test
    public void shouldTokenizeVariableExpression() {
        final Tokenizer tokenizer = new Tokenizer("x*3.14", Set.of("x"), Collections.emptyMap(), Collections.emptyMap());

        final List<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = tokenizer.next()) != null) {
            tokens.add(token);
        }

        assertThat(tokens.get(0)).isNotNull();
        assertThat(tokens.get(0).type).isEqualTo(Token.Type.VARIABLE);
        assertThat(((VariableToken) tokens.get(0)).name).isEqualTo("x");
        assertThat(tokens.get(1)).isNotNull();
        assertThat(tokens.get(1).type).isEqualTo(Token.Type.OPERATOR);
        assertThat(((OperatorToken) tokens.get(1)).operator.symbol).isEqualTo("*");
        assertThat(tokens.get(2)).isNotNull();
        assertThat(tokens.get(2).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(2)).value).isEqualTo(3.14D);
    }

    @Test
    public void shouldTokenizeCustomFunctionExpression() {
        final Tokenizer tokenizer = new Tokenizer("foo(3.14)", Set.of("x"), Collections.singletonMap("foo", new Function("foo") {
            @Override
            double apply(final double... values) {
                return 0;
            }
        }), Collections.emptyMap());

        final List<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = tokenizer.next()) != null) {
            tokens.add(token);
        }
        assertThat(tokens.get(0)).isNotNull();
        assertThat(tokens.get(0).type).isEqualTo(Token.Type.FUNCTION);
        assertThat(tokens.get(1)).isNotNull();
        assertThat(tokens.get(1).type).isEqualTo(Token.Type.PARANTHESES);
        assertThat(((ParanthesesToken) tokens.get(1)).open).isEqualTo(true);
        assertThat(tokens.get(2)).isNotNull();
        assertThat(tokens.get(2).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(2)).value).isEqualTo(3.14D);
        assertThat(tokens.get(3)).isNotNull();
        assertThat(tokens.get(3).type).isEqualTo(Token.Type.PARANTHESES);
        assertThat(((ParanthesesToken) tokens.get(3)).open).isEqualTo(false);
    }

    @Test
    public void shouldTokenizeCustomOperatorExpression() {
        final Map<String, Operator> customOperators = Collections.singletonMap(">", new Operator(">", Operator.PRECEDENCE_ADDITION) {
            @Override
            double apply(final double... values) {
                return 0;
            }
        });

        final Tokenizer tokenizer = new Tokenizer("3.14 > 1", Collections.emptySet(), Collections.emptyMap(), customOperators);

        final List<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = tokenizer.next()) != null) {
            tokens.add(token);
        }
        assertThat(tokens.get(0)).isNotNull();
        assertThat(tokens.get(0).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(0)).value).isEqualTo(3.14D);
        assertThat(tokens.get(1)).isNotNull();
        assertThat(tokens.get(1).type).isEqualTo(Token.Type.OPERATOR);
        assertThat(((OperatorToken) tokens.get(1)).operator.symbol).isEqualTo(">");
        assertThat(tokens.get(2)).isNotNull();
        assertThat(tokens.get(2).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken) tokens.get(2)).value).isEqualTo(1);
    }
}