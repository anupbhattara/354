/**
 * NodeFactExpr represents a parenthesized expression in the parse tree.
 */
public class NodeFactExpr extends NodeFact {

	private NodeExpr expr;

	/**
	 * Constructs a new parenthesized expression node.
	 * @param expr the expression inside the parentheses
	 */
	public NodeFactExpr(NodeExpr expr) {
		this.expr=expr;
	}

	/**
	 * Evaluates the parenthesized expression.
	 * @param env the environment containing variable values
	 * @return the evaluated value as a double
	 * @throws EvalException if evaluation fails
	 */
	public double eval(Environment env) throws EvalException {
		return expr.eval(env);
	}

	/**
	 * Generates C code for the parenthesized expression.
	 * @return C code string representation
	 */
	public String code() { return "("+expr.code()+")"; }

}
