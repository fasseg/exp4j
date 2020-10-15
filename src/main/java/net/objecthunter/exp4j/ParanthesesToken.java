package net.objecthunter.exp4j;

public class ParanthesesToken extends Token {
    final boolean open;

    ParanthesesToken(final boolean open) {
        super(Type.PARANTHESES);
        this.open = open;
    }
}
