/**
 * NodeIf represents an if statement in the parse tree.
 * It can be an if-then or if-then-else statement.
 */
public class NodeIf extends Node {

	private NodeBoolexpr boolexpr;
	private NodeStmt stmt1;
	private NodeStmt stmt2; // null for if-then, non-null for if-then-else

	/**
	 * Constructs a new if-then node.
	 * @param boolexpr the boolean expression to evaluate
	 * @param stmt1 the statement to execute if true
	 */
	public NodeIf(NodeBoolexpr boolexpr, NodeStmt stmt1) {
		this.boolexpr = boolexpr;
		this.stmt1 = stmt1;
		this.stmt2 = null;
	}

	/**
	 * Constructs a new if-then-else node.
	 * @param boolexpr the boolean expression to evaluate
	 * @param stmt1 the statement to execute if true
	 * @param stmt2 the statement to execute if false
	 */
	public NodeIf(NodeBoolexpr boolexpr, NodeStmt stmt1, NodeStmt stmt2) {
		this.boolexpr = boolexpr;
		this.stmt1 = stmt1;
		this.stmt2 = stmt2;
	}

	/**
	 * Evaluates the if statement.
	 * @param env the environment containing variable values
	 * @return the value of the executed statement
	 * @throws EvalException if evaluation fails
	 */
	public double eval(Environment env) throws EvalException {
		double condition = boolexpr.eval(env);
		if (condition != 0.0) {
			return stmt1.eval(env);
		} else if (stmt2 != null) {
			return stmt2.eval(env);
		}
		return 0.0;
	}

	/**
	 * Generates C code for the if statement.
	 * @return C code string representation
	 */
	public String code() {
		StringBuilder sb = new StringBuilder();
		sb.append("if (").append(boolexpr.code()).append(") {");
		sb.append(stmt1.code());
		sb.append("}");
		
		if (stmt2 != null) {
			sb.append(" else {");
			sb.append(stmt2.code());
			sb.append("}");
		}
		
		return sb.toString();
	}

}

