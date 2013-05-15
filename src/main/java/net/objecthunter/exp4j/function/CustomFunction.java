package net.objecthunter.exp4j.function;

public abstract class CustomFunction {
	final int argc;
	final String name;

	protected CustomFunction(final String name) {
		this.argc = 1;
		this.name = name;
	}

	protected CustomFunction(final String name, final int argc) {
		this.argc = argc;
		this.name = name;
	}

	public int getArgumentCount() {
		return argc;
	}

	public String getName() {
		return name;
	}

	public abstract Object apply(Object... args);

}
