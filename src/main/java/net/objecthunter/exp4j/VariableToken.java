package net.objecthunter.exp4j;

class VariableToken extends Token {

    final String name;

    VariableToken(final String name) {
        super(Token.Type.VARIABLE);
        this.name = name;
    }
}
