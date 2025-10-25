// This class, and its subclasses,
// collectively model parse-tree nodes.
// Each kind of node can be eval()-uated,
// and/or code()-generated.

public abstract class Node {

	protected int pos=0;

	/**
	 * Evaluates the node and returns its value.
	 * @param env the environment containing variable values
	 * @return the evaluated value as a double
	 * @throws EvalException if evaluation fails
	 */
	public double eval(Environment env) throws EvalException {
		throw new EvalException(pos,"cannot eval() node!");
	}

	/**
	 * Generates C code for this node.
	 * @return C code string representation
	 */
	public String code() { return ""; }

}
