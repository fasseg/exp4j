package net.objecthunter.exp4j;

class Operators {

    static char[] ALLOWED_OPERATOR_CHARS = { '+', '-', '*', '/', '<', '>', '~', '!', '%'};

    private static int BUILTIN_OPERATORS_LEN = ALLOWED_OPERATOR_CHARS.length;

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
        BUILTIN_OPERATORS[0] = new Operator("+", Operator.PRECEDENCE_ADDITION) {
            @Override
            double apply(final double[] values) {
                return values[0] + values[1];
            }
        };
        BUILTIN_OPERATORS[1] = new Operator("*", Operator.PRECEDENCE_MULTIPLICATION) {
            @Override
            double apply(final double[] values) {
                return values[0] * values[1];
            }
        };
        BUILTIN_OPERATORS[2] = new Operator("-", Operator.PRECEDENCE_ADDITION) {
            @Override
            double apply(final double[] values) {
                return values[0] - values[1];
            }
        };
        BUILTIN_OPERATORS[3] = new Operator("/", Operator.PRECEDENCE_MULTIPLICATION) {
            @Override
            double apply(final double[] values) {
                return values[0] / values[1];
            }
        };
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
