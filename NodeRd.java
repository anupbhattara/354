/**
 * NodeRd represents a read/input statement in the parse tree.
 * It reads a value from standard input and stores it in a variable.
 */
public class NodeRd extends Node {

	private String id;

	/**
	 * Constructs a new read node.
	 * @param id the variable name to read into
	 */
	public NodeRd(String id) {
		this.id = id;
	}

	/**
	 * Reads a value from standard input and stores it in the environment.
	 * @param env the environment to store the variable in
	 * @return the value that was read
	 * @throws EvalException if reading fails
	 */
	public double eval(Environment env) throws EvalException {
		try {
			java.util.Scanner scanner = new java.util.Scanner(System.in);
			double value = scanner.nextDouble();
			env.put(id, value);
			return value;
		} catch (Exception e) {
			throw new EvalException(pos, "read error: " + e.getMessage());
		}
	}

	/**
	 * Generates C code for the read statement.
	 * @return C code string representation
	 */
	public String code() {
		return "scanf(\"%lf\", &" + id + ");";
	}

}

