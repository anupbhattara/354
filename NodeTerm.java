/**
 * NodeTerm represents a term in the parse tree.
 * A term can be a single factor or a factor combined with a term
 * using a multiplication or division operator.
 */
public class NodeTerm extends Node {

	private NodeFact fact;
	private NodeMulop mulop;
	private NodeTerm term;

	/**
	 * Constructs a new term node.
	 * @param fact the factor component
	 * @param mulop the multiplication/division operator (can be null)
	 * @param term the term component (can be null)
	 */
	public NodeTerm(NodeFact fact, NodeMulop mulop, NodeTerm term) {
		this.fact=fact;
		this.mulop=mulop;
		this.term=term;
	}

	/**
	 * Appends another term to this one.
	 * Used for building right-associative terms.
	 * @param term the term to append
	 */
	public void append(NodeTerm term) {
		if (this.term==null) {
			this.mulop=term.mulop;
			this.term=term;
			term.mulop=null;
		} else
			this.term.append(term);
	}

	/**
	 * Evaluates the term.
	 * @param env the environment containing variable values
	 * @return the evaluated value as a double
	 * @throws EvalException if evaluation fails
	 */
	public double eval(Environment env) throws EvalException {
		return term==null
			? fact.eval(env)
			: mulop.op(term.eval(env),fact.eval(env));
	}

	/**
	 * Generates C code for the term.
	 * @return C code string representation
	 */
	public String code() {
		return (term==null ? "" : term.code()+mulop.code())+fact.code();
	}

}
