package net.objecthunter.exp4j;

class NumericalToken extends Token {

    final double value;

    NumericalToken(final double value) {
        super(Type.NUMERICAL);
        this.value = value;
    }
}
