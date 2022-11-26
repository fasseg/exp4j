package net.objecthunter.exp4j.function;

public abstract class Function {

    private final int numArgs;

    private final String name;

    /**
     * Create a new Function with a given name and number of arguments
     *
     * @param name         the name of the Function
     * @param numArguments the number of arguments the function takes
     */
    public Function(String name, int numArgs) {
        if (numArgs < 0) {
            throw new IllegalArgumentException("The number of function arguments can not be less than 0 for '" +
                    name + "'");
        }
        if (!isValidFunctionName(name)) {
            throw new IllegalArgumentException("The function name '" + name + "' is invalid");
        }
        this.name = name;
        this.numArgs = numArgs;

    }

    /**
     * Create a new Function with a given name that takes a single argument
     *
     * @param name the name of the Function
     */
    public Function(String name) {
        this(name, 1);
    }

    private static boolean isValidFunctionName(final String name) {
        if (name == null) {
            return false;
        }

        final int size = name.length();

        if (size == 0) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            final char c = name.charAt(i);
            if (Character.isLetter(c) || c == '_') {
                continue;
            } else if (Character.isDigit(c) && i > 0) {
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * Get the name of the Function
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the number of arguments for this function
     *
     * @return the number of arguments
     */
    public int getNumArguments() {
        return numArgs;
    }

    /**
     * Method that does the actual calculation of the function value given the arguments
     *
     * @param args the set of arguments used for calculating the function
     * @return the result of the function evaluation
     */
    public abstract double apply(double ... args);
}
