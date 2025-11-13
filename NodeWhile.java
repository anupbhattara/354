/**
 * NodeWhile represents a while statement in the parse tree.
 * It repeatedly executes a statement while a boolean expression is true.
 */
public class NodeWhile extends Node {

	private NodeBoolexpr boolexpr;
	private NodeStmt stmt;

	/**
	 * Constructs a new while node.
	 * @param boolexpr the boolean expression to evaluate
	 * @param stmt the statement to execute while the condition is true
	 */
	public NodeWhile(NodeBoolexpr boolexpr, NodeStmt stmt) {
		this.boolexpr = boolexpr;
		this.stmt = stmt;
	}

	/**
	 * Evaluates the while statement.
	 * @param env the environment containing variable values
	 * @return the value of the last executed statement (or 0.0 if loop never executes)
	 * @throws EvalException if evaluation fails
	 */
	public double eval(Environment env) throws EvalException {
		double result = 0.0;
		while (boolexpr.eval(env) != 0.0) {
			result = stmt.eval(env);
		}
		return result;
	}

	/**
	 * Generates C code for the while statement.
	 * @return C code string representation
	 */
	public String code() {
		StringBuilder sb = new StringBuilder();
		sb.append("while (").append(boolexpr.code()).append(") {");
		sb.append(stmt.code());
		sb.append("}");
		return sb.toString();
	}

}

