// This class provides an environment for variable storage and management.
// It uses a HashMap to store variable names and their values.
// Accessing an undefined variable throws an EvalException.
// The environment also generates C code declarations for all variables.

import java.util.*;

public class Environment {

	// HashMap to store variable names and their values
	private Map<String, Double> variables = new HashMap<String, Double>();

	/**
	 * Stores a variable and its value in the environment.
	 * @param var the variable name
	 * @param val the value to store
	 * @return the value that was stored
	 */
	public double put(String var, double val) {
		variables.put(var, val);
		return val;
	}

	/**
	 * Retrieves the value of a variable from the environment.
	 * @param pos the position in the source code (for error reporting)
	 * @param var the variable name to retrieve
	 * @return the value of the variable
	 * @throws EvalException if the variable is not defined
	 */
	public double get(int pos, String var) throws EvalException {
		if (!variables.containsKey(var)) {
			throw new EvalException(pos, "undefined variable: " + var);
		}
		return variables.get(var);
	}

	/**
	 * Generates C code declarations for all variables in the environment.
	 * @return C code string with variable declarations
	 */
	public String toC() {
		if (variables.isEmpty()) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("double ");
		
		// Add all variable names
		String sep = "";
		for (String var : variables.keySet()) {
			sb.append(sep).append(var);
			sep = ",";
		}
		sb.append(";\n");
		
		// Initialize all variables to 0
		for (String var : variables.keySet()) {
			sb.append(var).append("=0;");
		}
		sb.append("\n");
		
		return sb.toString();
	}

}
