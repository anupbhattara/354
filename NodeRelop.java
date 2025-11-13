/**
 * NodeRelop represents a relational operator in the parse tree.
 * Relational operators compare two expressions and return a boolean value.
 */
public class NodeRelop extends Node {

	private String op;

	/**
	 * Constructs a new relational operator node.
	 * @param pos the position in the source code
	 * @param op the operator string (e.g., "<", "<=", ">", ">=", "<>", "==")
	 */
	public NodeRelop(int pos, String op) {
		this.pos = pos;
		this.op = op;
	}

	/**
	 * Applies the relational operator to two values.
	 * @param left the left operand
	 * @param right the right operand
	 * @return 1.0 if the comparison is true, 0.0 if false
	 */
	public double op(double left, double right) {
		switch (op) {
			case "<":  return left < right ? 1.0 : 0.0;
			case "<=": return left <= right ? 1.0 : 0.0;
			case ">":  return left > right ? 1.0 : 0.0;
			case ">=": return left >= right ? 1.0 : 0.0;
			case "<>": return left != right ? 1.0 : 0.0;
			case "==": return left == right ? 1.0 : 0.0;
			default: return 0.0;
		}
	}

	/**
	 * Generates C code for the relational operator.
	 * @return C code string representation
	 */
	public String code() {
		// Convert <> to != for C
		if (op.equals("<>")) {
			return "!=";
		}
		return op;
	}

}

