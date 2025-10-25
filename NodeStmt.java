/**
 * NodeStmt represents a statement in the parse tree.
 * Currently, statements are assignment statements.
 */
public class NodeStmt extends Node {

	private NodeAssn assn;

	/**
	 * Constructs a new statement node.
	 * @param assn the assignment statement
	 */
	public NodeStmt(NodeAssn assn) {
		this.assn=assn;
	}

	/**
	 * Evaluates the statement.
	 * @param env the environment containing variable values
	 * @return the evaluated value as a double
	 * @throws EvalException if evaluation fails
	 */
	public double eval(Environment env) throws EvalException {
		return assn.eval(env);
	}

	/**
	 * Generates C code for the statement.
	 * @return C code string representation
	 */
	public String code() { return assn.code(); }

}
