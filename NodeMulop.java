/**
 * NodeMulop represents a multiplication or division operator in the parse tree.
 */
public class NodeMulop extends Node {

	private String mulop;

	/**
	 * Constructs a new multiplication/division operator node.
	 * @param pos the position in the source code
	 * @param mulop the operator string ("*" or "/")
	 */
	public NodeMulop(int pos, String mulop) {
		this.pos=pos;
		this.mulop=mulop;
	}

	/**
	 * Performs the multiplication or division operation.
	 * @param o1 the first operand
	 * @param o2 the second operand
	 * @return the result of the operation
	 * @throws EvalException if the operator is invalid
	 */
	public double op(double o1, double o2) throws EvalException {
		if (mulop.equals("*"))
			return o1*o2;
		if (mulop.equals("/"))
			return o1/o2;
		throw new EvalException(pos,"bogus mulop: "+mulop);
	}

	/**
	 * Generates C code for the operator.
	 * @return C code string representation
	 */
	public String code() { return mulop; }

}
