/**
 * NodeWr represents a write/output statement in the parse tree.
 * It evaluates an expression and outputs its value.
 */
public class NodeWr extends Node {
    private NodeExpr expr;
    
    /**
     * Constructs a new write node.
     * @param expr the expression to evaluate and output
     */
    public NodeWr(NodeExpr expr) {
        this.expr = expr;
    }
    
    /**
     * Evaluates the expression and outputs its value.
     * @param env the environment containing variable values
     * @return the evaluated value as a double
     * @throws EvalException if evaluation fails
     */
    public double eval(Environment env) throws EvalException {
        double d = expr.eval(env);
        
        // Print as integer if it's a whole number
        if (d == (long)d) {
            System.out.println((long)d);
        } else {
            System.out.println(d);
        }
        
        return d;
    }
    
    /**
     * Generates C code for the write statement.
     * @return C code string representation
     */
    public String code() {
        return "printf(\"%g\\n\", (double)(" + expr.code() + "));";
    }
}