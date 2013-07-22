package net.objecthunter.exp4j.calculable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.NumberToken;
import net.objecthunter.exp4j.tokenizer.VariableToken;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.FunctionToken;

public final class Calculable<T> {

	private final List<Token> tokens;
	private final Map<String, T> variables = new HashMap<>();

	public Calculable(final List<Token> tokens) {
		super();
		this.tokens = tokens;
	}

	public Calculable<T> setVariable(final String name, final T value){
		this.variables.put(name, value);
		return this;
	}
	
	public T calculate() {
        final Stack<T> output = new Stack<>();
        for (Token t : tokens) {
            if (t.getType() == Token.Type.NUMBER) {
				/* push values onto the stack */
                output.push(((NumberToken<T>) t).getValue());

            } else if (t.getType() == Token.Type.VARIABLE) {
                /* push the variable value onto the stack */
                output.push(variables.get(((VariableToken) t).getName()));

            } else if (t.getType() == Token.Type.OPERATOR) {

                final OperatorToken op = (OperatorToken) t;
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

            } else if (t.getType() == Token.Type.FUNCTION) {

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
