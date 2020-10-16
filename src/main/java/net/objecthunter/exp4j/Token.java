package net.objecthunter.exp4j;

abstract class Token {
    protected Token(final Type type) {
        this.type = type;
    }

    public enum Type {NUMERICAL,OPERATOR, PARANTHESES, FUNCTION, VARIABLE}

    final Type type;
}
