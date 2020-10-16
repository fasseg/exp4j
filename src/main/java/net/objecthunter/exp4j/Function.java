package net.objecthunter.exp4j;

public abstract class Function {
    final String name;
    final int numArgs;

    public Function(final String name) {
        this.name = name;
        this.numArgs = 1;
    }

    public Function(final String name, final int numArgs) {
        this.name = name;
        this.numArgs = numArgs;
    }

    abstract double apply(double ... values);
}
