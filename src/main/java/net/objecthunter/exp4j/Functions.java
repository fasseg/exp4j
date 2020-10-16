package net.objecthunter.exp4j;

import java.util.HashMap;
import java.util.Map;

class Functions {

    static final Map<String, Function> BUILTIN_FUNCTIONS = new HashMap<>();

    static {
        BUILTIN_FUNCTIONS.put("abs", new Function("abs") {
            @Override
            double apply(final double... values) {
                return Math.abs(values[0]);
            }
        });
    }

    static boolean isFunctionChar(final char ch) {
        return ((ch > 64 && ch < 91) || (ch > 96 && ch < 123) || ch == '_');
    }

    static Function getBuiltinFunction(final String name) {
        return BUILTIN_FUNCTIONS.get(name);
    }
}
