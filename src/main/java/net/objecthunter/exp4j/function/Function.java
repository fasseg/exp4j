package net.objecthunter.exp4j.function;

public abstract class Function {
	private final String name;
	private final int argc;

	public Function(final String name, final int argc) {
		super();
		this.name = name;
		this.argc = argc;
	}

	public Function(final String name) {
		super();
		this.name = name;
		this.argc = 1;
	}

	public int getArgumentCount() {
		return argc;
	}
	
	public String getName() {
		return name;
	}

	public abstract double apply(double... args);
}
