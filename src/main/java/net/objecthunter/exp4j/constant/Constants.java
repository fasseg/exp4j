package net.objecthunter.exp4j.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Class contains all built-in constants which can be used in expressions
 */
public class Constants {
    /**
     * @return map of constant names and their values
     */
    public static Map<String, Double> getBuiltinConstants() {
        final Map<String, Double> vars = new HashMap<String, Double>(4);
        vars.put("pi", Math.PI);
        vars.put("π", Math.PI);
        vars.put("φ", 1.61803398874d);
        vars.put("e", Math.E);
        return vars;
    }
}
