package net.objecthunter.exp4j.tokenizer;

/**
 * Non-instantiable class to hold Token types.
 *
 * @author Leo Gutiérrez (leogutierrezramirez [at] gmail)
 */
public class TokenType {
    
    public static final short NUMBER = 1;
    public static final short OPERATOR = 2;
    public static final short FUNCTION = 3;
    public static final short PARENTHESES_OPEN = 4;
    public static final short PARENTHESES_CLOSE = 5;
    public static final short VARIABLE = 6;
    public static final short SEPARATOR = 7;
    
    private TokenType() {}

}
