/**
 * NodeAddop represents an addition or subtraction operator in the parse tree.
 */
public class NodeAddop extends Node {

	private String addop;

	/**
	 * Constructs a new addition/subtraction operator node.
	 * @param pos the position in the source code
	 * @param addop the operator string ("+" or "-")
	 */
	public NodeAddop(int pos, String addop) {
		this.pos=pos;
		this.addop=addop;
	}

	/**
	 * Performs the addition or subtraction operation.
	 * @param o1 the first operand
	 * @param o2 the second operand
	 * @return the result of the operation
	 * @throws EvalException if the operator is invalid
	 */
	public double op(double o1, double o2) throws EvalException {
		if (addop.equals("+"))
			return o1+o2;
		if (addop.equals("-"))
			return o1-o2;
		throw new EvalException(pos,"bogus addop: "+addop);
	}

	/**
	 * Generates C code for the operator.
	 * @return C code string representation
	 */
	public String code() { return addop; }

}
