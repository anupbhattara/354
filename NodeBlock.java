/**
 * NodeBlock represents a block of statements in the parse tree.
 * A block is a sequence of statements that can be treated as a single unit.
 */
public class NodeBlock extends Node {

	private NodeStmt stmt;
	private NodeBlock block; // null if this is the last statement

	/**
	 * Constructs a new block node with a single statement.
	 * @param stmt the statement
	 */
	public NodeBlock(NodeStmt stmt) {
		this.stmt = stmt;
		this.block = null;
	}

	/**
	 * Constructs a new block node with a statement followed by another block.
	 * @param stmt the first statement
	 * @param block the remaining block (can be null)
	 */
	public NodeBlock(NodeStmt stmt, NodeBlock block) {
		this.stmt = stmt;
		this.block = block;
	}

	/**
	 * Evaluates the block by executing all statements in sequence.
	 * @param env the environment containing variable values
	 * @return the value of the last executed statement
	 * @throws EvalException if evaluation fails
	 */
	public double eval(Environment env) throws EvalException {
		double result = stmt.eval(env);
		if (block != null) {
			result = block.eval(env);
		}
		return result;
	}

	/**
	 * Generates C code for the block.
	 * Blocks are sequences of statements, so we just concatenate their code.
	 * @return C code string representation
	 */
	public String code() {
		StringBuilder sb = new StringBuilder();
		sb.append(stmt.code());
		if (block != null) {
			sb.append(block.code());
		}
		return sb.toString();
	}

}

