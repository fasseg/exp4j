package net.objecthunter.exp4j.tokenizer;

/**
 * Created by ruckus on 30.06.14.
 */
public class ParanthesesToken extends Token{
    private final boolean open;

    public ParanthesesToken(boolean open) {
        super(TOKEN_PARANTHESES);
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public static ParanthesesToken openParantheses() {
        return new ParanthesesToken(true);
    }
    public static ParanthesesToken closeParantheses() {
        return new ParanthesesToken(false);
    }
}
