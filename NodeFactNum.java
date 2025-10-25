/**
 * NodeFactNum represents a numeric literal in the parse tree.
 * It can handle both integer and floating-point numbers.
 */
public class NodeFactNum extends NodeFact {

	private String num;

	/**
	 * Constructs a new numeric literal node.
	 * @param num the numeric string value
	 */
	public NodeFactNum(String num) {
		this.num=num;
	}

	/**
	 * Evaluates the numeric literal.
	 * @param env the environment (not used for literals)
	 * @return the numeric value as a double
	 * @throws EvalException if the number format is invalid
	 */
	public double eval(Environment env) throws EvalException {
		try {
			return Double.parseDouble(num);
		} catch (NumberFormatException e) {
			throw new EvalException(pos, "invalid number format: " + num);
		}
	}

	/**
	 * Generates C code for the numeric literal.
	 * @return C code string representation
	 */
	public String code() { return num; }

}
