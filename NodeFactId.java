/**
 * NodeFactId represents a variable identifier in the parse tree.
 */
public class NodeFactId extends NodeFact {

	private String id;

	/**
	 * Constructs a new variable identifier node.
	 * @param pos the position in the source code
	 * @param id the variable name
	 */
	public NodeFactId(int pos, String id) {
		this.pos=pos;
		this.id=id;
	}

	/**
	 * Evaluates the variable by looking up its value in the environment.
	 * @param env the environment containing variable values
	 * @return the value of the variable as a double
	 * @throws EvalException if the variable is undefined
	 */
	public double eval(Environment env) throws EvalException {
		return env.get(pos,id);
	}

	/**
	 * Generates C code for the variable identifier.
	 * @return C code string representation
	 */
	public String code() { return id; }

}
