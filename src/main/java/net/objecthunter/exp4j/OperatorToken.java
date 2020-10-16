package net.objecthunter.exp4j;

class OperatorToken extends Token {
    final Operator operator;

    OperatorToken(final Operator operator) {
        super(Type.OPERATOR);
        this.operator = operator;
    }
}
