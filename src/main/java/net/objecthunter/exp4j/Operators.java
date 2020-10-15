package net.objecthunter.exp4j;

class Operators {

    static char[] ALLOWED_OPERATOR_CHARS = { '+', '-', '*', '/'};

    private static int BUILTIN_OPERATORS_LEN = 4;

    static Operator[] BUILTIN_OPERATORS = new Operator[BUILTIN_OPERATORS_LEN];

    static boolean isOperatorChar(char ch) {
        for (int i = 0; i < BUILTIN_OPERATORS_LEN; i++) {
            if (ch == ALLOWED_OPERATOR_CHARS[i]) {
                return true;
            }
        }
        return false;
    }

    static {
        BUILTIN_OPERATORS[0] = new Operator("+");
        BUILTIN_OPERATORS[1] = new Operator("*");
        BUILTIN_OPERATORS[2] = new Operator("-");
        BUILTIN_OPERATORS[3] = new Operator("/");
    }

    static Operator getBuiltinOperator(final String symbol) {
        for (int i = 0; i < BUILTIN_OPERATORS_LEN; i++) {
            if (symbol.equals(BUILTIN_OPERATORS[i].symbol)) {
                return BUILTIN_OPERATORS[i];
            }
        }
        return null;
    }
}
