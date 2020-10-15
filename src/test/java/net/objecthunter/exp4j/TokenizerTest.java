package net.objecthunter.exp4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenizerTest {

    @Test
    public void shouldTokenizeSimpleExpression() {
        final Token token = new Tokenizer("3.14").next();
        assertThat(token).isNotNull();
        assertThat(token.type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken)token).value).isEqualTo(3.14D);
    }

    @Test
    public void shouldTokenizeSimpleAdditionExpression() {
        final Tokenizer tokenizer = new Tokenizer("3.14+16");

        final List<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = tokenizer.next()) != null) {
            tokens.add(token);
        }

        assertThat(tokens.get(0)).isNotNull();
        assertThat(tokens.get(0).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken)tokens.get(0)).value).isEqualTo(3.14D);
        assertThat(tokens.get(1)).isNotNull();
        assertThat(tokens.get(1).type).isEqualTo(Token.Type.OPERATOR);
        assertThat(((OperatorToken)tokens.get(1)).operator.symbol).isEqualTo("+");
        assertThat(tokens.get(2)).isNotNull();
        assertThat(tokens.get(2).type).isEqualTo(Token.Type.NUMERICAL);
        assertThat(((NumericalToken)tokens.get(2)).value).isEqualTo(16D);
    }
}
