/**
 * NodeFactUnaryMinus represents a unary minus operation in the parse tree.
 * It negates the value of a factor.
 */
public class NodeFactUnaryMinus extends NodeFact {

	private NodeFact fact;

	/**
	 * Constructs a new unary minus node.
	 * @param fact the factor to negate
	 */
	public NodeFactUnaryMinus(NodeFact fact) {
		this.fact = fact;
	}

	/**
	 * Evaluates the unary minus by negating the factor's value.
	 * @param env the environment containing variable values
	 * @return the negated value as a double
	 * @throws EvalException if evaluation fails
	 */
	public double eval(Environment env) throws EvalException {
		return -fact.eval(env);
	}

	/**
	 * Generates C code for the unary minus operation.
	 * @return C code string representation
	 */
	public String code() {
		return "(-" + fact.code() + ")";
	}

}
