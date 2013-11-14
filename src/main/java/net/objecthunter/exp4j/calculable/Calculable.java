package net.objecthunter.exp4j.calculable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.objecthunter.exp4j.tokenizer.FunctionToken;
import net.objecthunter.exp4j.tokenizer.NumberToken;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.VariableToken;
import net.objecthunter.exp4j.tokenizer.Token.Type;

public abstract class Calculable<T> {
	protected final List<Token> tokens;
	protected final Map<String,T> variables = new HashMap<String, T>();
	private ExecutorService executor;
	
	public ExecutorService getExecutor() {
		if (executor == null)  {
			executor = Executors.newFixedThreadPool(1);
		}
		return executor;
	}

	public Calculable(List<Token> tokens) {
		super();
		this.tokens = tokens;
	}

	public Calculable<T> setVariable(String name, T value){
		this.variables.put(name, value);
		return this;
	}
	
	public Future<T> calculateAsync() {
		return getExecutor().submit(new Callable<T>() {
			@Override
			public T call() throws Exception {
				return calculate();
			}
		});
	}
	
	public T calculate() {
		final Stack<T> output = new Stack<>();
		final Iterator<Token> tokenIterator = tokens.iterator();
		while (tokenIterator.hasNext()) {
			Token t = tokenIterator.next();

			if (t.getType() == Type.NUMBER) {

				/* push values onto the stack */
				output.push(((NumberToken<T>) t).getValue());

			} else if (t.getType() == Type.VARIABLE) {
				/* push the variable value onto the stack */
				output.push(variables.get(((VariableToken)t).getName()));

			} else if (t.getType() == Type.OPERATOR) {

				OperatorToken op = (OperatorToken) t;
				if (output.size() < op.getOperator().getArgumentCount()) {
					throw new RuntimeException("Invalid number of operands available");
				}

				if (op.getOperator().getArgumentCount() == 2) {
					/* pop the operands and push the result of the operation */
					T rightArg = output.pop();
					T leftArg = output.pop();
					output.push((T) op.getOperator().apply(leftArg, rightArg));
				} else if (op.getOperator().getArgumentCount() == 1) {
					/* pop the operand and push the result of the operation */
					T arg = output.pop();
					output.push((T) op.getOperator().apply(arg));
				}

			} else if (t.getType() == Type.FUNCTION) {

				FunctionToken func = (FunctionToken) t;
				if (output.size() < func.getFunction().getArgumentCount()) {
					throw new RuntimeException("Invalid number of arguments available");
				}

				/* collect the arguments from the stack */
				Object[] args = new Object[func.getFunction().getArgumentCount()];
				for (int i = func.getFunction().getArgumentCount() - 1; i >= 0; i--) {
					args[i] = output.pop();
				}
				output.push((T) func.getFunction().apply(args));
			}
		}
		if (output.size() != 1) {
			throw new RuntimeException("Invalid number of values on the stack");
		}
		return output.pop();
	}

}
