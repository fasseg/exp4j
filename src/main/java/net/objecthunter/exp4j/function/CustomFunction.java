package net.objecthunter.exp4j.function;

public abstract class CustomFunction {
	final int argc;
	final String name;

	protected CustomFunction(final String name) {
		this(name,1);
	}

	protected CustomFunction(final String name, final int argc) {
		if (!isValidFunctionName(name)){
			throw new RuntimeException("Invalid function name");
		}
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
	
	private static boolean isValidFunctionName(String name){
		char first = name.charAt(0);
		if (!Character.isAlphabetic(first) && first != '_'){
			return false;
		}
		return true;
	}


}
