package net.objecthunter.exp4j;

public abstract class Token {
    protected Token(final Type type) {
        this.type = type;
    }

    public enum Type {NUMERICAL,OPERATOR}

    final Type type;
}
