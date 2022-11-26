// Generated from java-escape by ANTLR 4.11.1
package net.objecthunter.exp4j.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Exp4jGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Exp4jGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Exp4jGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(Exp4jGrammarParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Exp4jGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(Exp4jGrammarParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link Exp4jGrammarParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(Exp4jGrammarParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Exp4jGrammarParser#decimal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimal(Exp4jGrammarParser.DecimalContext ctx);
	/**
	 * Visit a parse tree produced by {@link Exp4jGrammarParser#unary_prefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_prefix(Exp4jGrammarParser.Unary_prefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link Exp4jGrammarParser#unary_suffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_suffix(Exp4jGrammarParser.Unary_suffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link Exp4jGrammarParser#addition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddition(Exp4jGrammarParser.AdditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Exp4jGrammarParser#multiplication}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplication(Exp4jGrammarParser.MultiplicationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Exp4jGrammarParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(Exp4jGrammarParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link Exp4jGrammarParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(Exp4jGrammarParser.VariableContext ctx);
}