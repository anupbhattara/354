/**
 * NodeAssn represents an assignment statement in the parse tree.
 * It assigns the value of an expression to a variable.
 */
public class NodeAssn extends Node {

	private String id;
	private NodeExpr expr;

	/**
	 * Constructs a new assignment node.
	 * @param id the variable name to assign to
	 * @param expr the expression to evaluate and assign
	 */
	public NodeAssn(String id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}

	/**
	 * Evaluates the assignment by computing the expression value
	 * and storing it in the environment.
	 * @param env the environment to store the variable in
	 * @return the value that was assigned
	 * @throws EvalException if evaluation fails
	 */
	public double eval(Environment env) throws EvalException {
		return env.put(id, expr.eval(env));
	}

	/**
	 * Generates C code for the assignment statement.
	 * @return C code string representation
	 */
	public String code() {
		return id + "=" + expr.code() + ";";
	}

}
