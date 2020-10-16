package net.objecthunter.exp4j;

class FunctionToken extends Token {

    final Function function;

    FunctionToken(final Function function) {
        super(Token.Type.FUNCTION);
        this.function = function;
    }
}
