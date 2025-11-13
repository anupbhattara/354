/**
 * NodeBoolexpr represents a boolean expression in the parse tree.
 * A boolean expression compares two expressions using a relational operator.
 */
public class NodeBoolexpr extends Node {

	private NodeExpr expr1;
	private NodeRelop relop;
	private NodeExpr expr2;

	/**
	 * Constructs a new boolean expression node.
	 * @param expr1 the left expression
	 * @param relop the relational operator
	 * @param expr2 the right expression
	 */
	public NodeBoolexpr(NodeExpr expr1, NodeRelop relop, NodeExpr expr2) {
		this.expr1 = expr1;
		this.relop = relop;
		this.expr2 = expr2;
	}

	/**
	 * Evaluates the boolean expression.
	 * @param env the environment containing variable values
	 * @return 1.0 if the comparison is true, 0.0 if false
	 * @throws EvalException if evaluation fails
	 */
	public double eval(Environment env) throws EvalException {
		double left = expr1.eval(env);
		double right = expr2.eval(env);
		return relop.op(left, right);
	}

	/**
	 * Generates C code for the boolean expression.
	 * @return C code string representation
	 */
	public String code() {
		return "(" + expr1.code() + relop.code() + expr2.code() + ")";
	}

}

