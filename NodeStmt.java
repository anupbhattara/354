/**
 * NodeStmt represents a statement in the parse tree.
 * Statements can be assignments, read, write, if, while, or blocks.
 */
public class NodeStmt extends Node {

	private NodeAssn assn;
	private NodeRd rd;
	private NodeWr wr;
	private NodeIf ifStmt;
	private NodeWhile whileStmt;
	private NodeBlock block;

	/**
	 * Constructs a new statement node for an assignment.
	 * @param assn the assignment statement
	 */
	public NodeStmt(NodeAssn assn) {
		this.assn = assn;
	}

	/**
	 * Constructs a new statement node for a read statement.
	 * @param rd the read statement
	 */
	public NodeStmt(NodeRd rd) {
		this.rd = rd;
	}

	/**
	 * Constructs a new statement node for a write statement.
	 * @param wr the write statement
	 */
	public NodeStmt(NodeWr wr) {
		this.wr = wr;
	}

	/**
	 * Constructs a new statement node for an if statement.
	 * @param ifStmt the if statement
	 */
	public NodeStmt(NodeIf ifStmt) {
		this.ifStmt = ifStmt;
	}

	/**
	 * Constructs a new statement node for a while statement.
	 * @param whileStmt the while statement
	 */
	public NodeStmt(NodeWhile whileStmt) {
		this.whileStmt = whileStmt;
	}

	/**
	 * Constructs a new statement node for a block.
	 * @param block the block
	 */
	public NodeStmt(NodeBlock block) {
		this.block = block;
	}

	/**
	 * Evaluates the statement.
	 * @param env the environment containing variable values
	 * @return the evaluated value as a double
	 * @throws EvalException if evaluation fails
	 */
	public double eval(Environment env) throws EvalException {
		if (assn != null) return assn.eval(env);
		if (rd != null) return rd.eval(env);
		if (wr != null) return wr.eval(env);
		if (ifStmt != null) return ifStmt.eval(env);
		if (whileStmt != null) return whileStmt.eval(env);
		if (block != null) return block.eval(env);
		throw new EvalException(pos, "empty statement");
	}

	/**
	 * Generates C code for the statement.
	 * @return C code string representation
	 */
	public String code() {
		if (assn != null) return assn.code();
		if (rd != null) return rd.code();
		if (wr != null) return wr.code();
		if (ifStmt != null) return ifStmt.code();
		if (whileStmt != null) return whileStmt.code();
		if (block != null) return "{" + block.code() + "}";
		return "";
	}

}
