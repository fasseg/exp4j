package net.objecthunter.exp4j.function;

public abstract class Function {
	private final String name;

	public Function(final String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract double apply(double... args);
}
