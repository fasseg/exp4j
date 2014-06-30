package net.objecthunter.exp4j.tokenizer;

import net.objecthunter.exp4j.function.Function;

/**
 * Created by ruckus on 30.06.14.
 */
public class FunctionToken extends Token{
    private final Function function;
    public FunctionToken(final Function function) {
        super(Token.TOKEN_FUNCTION);
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }
}
