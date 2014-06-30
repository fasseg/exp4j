package net.objecthunter.exp4j.tokenizer;

/**
 * Created by ruckus on 30.06.14.
 */
public class VariableToken extends Token {
    private final String name;

    public String getName() {
        return name;
    }

    public VariableToken(String name) {
        super(TOKEN_VARIABLE);
        this.name = name;
    }
}
