/**
 * NodeExpr represents an expression in the parse tree.
 * An expression can be a single term or a term combined with an expression
 * using an addition or subtraction operator.
 */
public class NodeExpr extends Node {

	private NodeTerm term;
	private NodeAddop addop;
	private NodeExpr expr;

	/**
	 * Constructs a new expression node.
	 * @param term the term component
	 * @param addop the addition/subtraction operator (can be null)
	 * @param expr the expression component (can be null)
	 */
	public NodeExpr(NodeTerm term, NodeAddop addop, NodeExpr expr) {
		this.term=term;
		this.addop=addop;
		this.expr=expr;
	}

	/**
	 * Appends another expression to this one.
	 * Used for building right-associative expressions.
	 * @param expr the expression to append
	 */
	public void append(NodeExpr expr) {
		if (this.expr==null) {
			this.addop=expr.addop;
			this.expr=expr;
			expr.addop=null;
		} else
			this.expr.append(expr);
	}

	/**
	 * Evaluates the expression.
	 * @param env the environment containing variable values
	 * @return the evaluated value as a double
	 * @throws EvalException if evaluation fails
	 */
	public double eval(Environment env) throws EvalException {
		return expr==null
			? term.eval(env)
			: addop.op(expr.eval(env),term.eval(env));
	}

	/**
	 * Generates C code for the expression.
	 * @return C code string representation
	 */
	public String code() {
		return (expr==null ? "" : expr.code()+addop.code())+term.code();
	}

}
