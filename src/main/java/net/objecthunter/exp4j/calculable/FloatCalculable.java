package net.objecthunter.exp4j.calculable;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import net.objecthunter.exp4j.tokenizer.FunctionToken;
import net.objecthunter.exp4j.tokenizer.NumberToken;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.ParanthesesToken;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.Token.Type;
import net.objecthunter.exp4j.tokenizer.VariableToken;

public class FloatCalculable extends Calculable<Float> {

	public FloatCalculable(final List<Token> tokens) {
		super(tokens);
	}

	@Override
	public Float calculate() {
		final Stack<Float> output = new Stack<>();
		final Iterator<Token> tokenIterator = tokens.iterator();
		while (tokenIterator.hasNext()) {
			Token t = tokenIterator.next();

			if (t.getType() == Type.NUMBER) {

				/* push values onto the stack */
				output.push(((NumberToken<Float>) t).getValue());

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
					float rightArg = output.pop();
					float leftArg = output.pop();
					output.push((Float) op.getOperator().apply(leftArg, rightArg));
				} else if (op.getOperator().getArgumentCount() == 1) {
					/* pop the operand and push the result of the operation */
					float arg = output.pop();
					output.push((Float) op.getOperator().apply(arg));
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
				output.push((Float) func.getFunction().apply(args));
			}
		}
		if (output.size() != 1) {
			throw new RuntimeException("Invalid number of values on the stack");
		}
		return output.pop();
	}

}
