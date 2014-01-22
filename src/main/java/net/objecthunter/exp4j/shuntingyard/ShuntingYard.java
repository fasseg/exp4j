package net.objecthunter.exp4j.shuntingyard;

import java.util.Stack;
import static net.objecthunter.exp4j.tokenizer.FastTokenizer.*;

import net.objecthunter.exp4j.exception.UnparseableExpressionException;
import net.objecthunter.exp4j.tokenizer.FastTokenizer;

public class ShuntingYard {
	public static void transformRpn(final int types[], final String[] values,
			final String[] functions, final String[] variables,
			final String[] operators) throws UnparseableExpressionException {
		int len = types.length;
		final StringBuilder output = new StringBuilder();
		final Stack<String> stack = new Stack<>();
		final Stack<Integer> stackType = new Stack<>();
		for (int i = 0; i < len; i++) {
			switch(types[i]) {
			case NUMBER:
				output.append(values[i]);
				break;
			case FUNCTION:
				stack.push(values[i]);
				stackType.push(FUNCTION);
				break;
			case ARGUMENT_SEPARATOR:
				while (stackType.peek() != PARANTHESES_OPEN) {
					if (stack.isEmpty()) {
						throw new UnparseableExpressionException("Misplaced Separator or mismatched parantheses after argument separator ','");
					}
					output.append(stack.pop());
					stackType.pop();
				}
				break;
			case OPERATOR:
				while (stackType.peek() == OPERATOR) {
					
				}
				break;
			case PARANTHESES_CLOSE:
				break;
			case PARANTHESES_OPEN:
				break;
			}
		}
	}
}
