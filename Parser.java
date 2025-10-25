/**
 * This class is a recursive-descent parser for a simple arithmetic language.
 * It constructs and maintains a Scanner for the program being parsed.
 * The parser handles expressions with addition, subtraction, multiplication, division,
 * parentheses, unary minus, and assignment statements.
 */
public class Parser {

	private Scanner scanner;

	/**
	 * Matches the current token with the expected token.
	 * @param s the expected token string
	 * @throws SyntaxException if the tokens don't match
	 */
	private void match(String s) throws SyntaxException {
		scanner.match(new Token(s));
	}

	/**
	 * Gets the current token from the scanner.
	 * @return the current token
	 * @throws SyntaxException if no token is available
	 */
	private Token curr() throws SyntaxException {
		return scanner.curr();
	}

	/**
	 * Gets the current position in the source code.
	 * @return the current position
	 */
	private int pos() {
		return scanner.pos();
	}

	/**
	 * Parses a multiplication or division operator.
	 * @return the parsed operator node, or null if no operator found
	 * @throws SyntaxException if parsing fails
	 */
	private NodeMulop parseMulop() throws SyntaxException {
		if (curr().equals(new Token("*"))) {
			match("*");
			return new NodeMulop(pos(), "*");
		}
		if (curr().equals(new Token("/"))) {
			match("/");
			return new NodeMulop(pos(), "/");
		}
		return null;
	}

	/**
	 * Parses an addition or subtraction operator.
	 * @return the parsed operator node, or null if no operator found
	 * @throws SyntaxException if parsing fails
	 */
	private NodeAddop parseAddop() throws SyntaxException {
		if (curr().equals(new Token("+"))) {
			match("+");
			return new NodeAddop(pos(), "+");
		}
		if (curr().equals(new Token("-"))) {
			match("-");
			return new NodeAddop(pos(), "-");
		}
		return null;
	}

	/**
	 * Parses a factor, which can be:
	 * - A parenthesized expression
	 * - A variable identifier
	 * - A numeric literal
	 * - A unary minus followed by a factor
	 * @return the parsed factor node
	 * @throws SyntaxException if parsing fails
	 */
	private NodeFact parseFact() throws SyntaxException {
		if (curr().equals(new Token("("))) {
			match("(");
			NodeExpr expr = parseExpr();
			match(")");
			return new NodeFactExpr(expr);
		}
		if (curr().equals(new Token("id"))) {
			Token id = curr();
			match("id");
			return new NodeFactId(pos(), id.lex());
		}
		if (curr().equals(new Token("-"))) {
			match("-");
			NodeFact fact = parseFact();
			return new NodeFactUnaryMinus(fact);
		}
		Token num = curr();
		match("num");
		return new NodeFactNum(num.lex());
	}

	/**
	 * Parses a term, which consists of factors connected by multiplication/division operators.
	 * Terms are right-associative.
	 * @return the parsed term node
	 * @throws SyntaxException if parsing fails
	 */
	private NodeTerm parseTerm() throws SyntaxException {
		NodeFact fact = parseFact();
		NodeMulop mulop = parseMulop();
		if (mulop == null)
			return new NodeTerm(fact, null, null);
		NodeTerm term = parseTerm();
		term.append(new NodeTerm(fact, mulop, null));
		return term;
	}

	/**
	 * Parses an expression, which consists of terms connected by addition/subtraction operators.
	 * Expressions are right-associative.
	 * @return the parsed expression node
	 * @throws SyntaxException if parsing fails
	 */
	private NodeExpr parseExpr() throws SyntaxException {
		NodeTerm term = parseTerm();
		NodeAddop addop = parseAddop();
		if (addop == null)
			return new NodeExpr(term, null, null);
		NodeExpr expr = parseExpr();
		expr.append(new NodeExpr(term, addop, null));
		return expr;
	}

	/**
	 * Parses an assignment statement.
	 * @return the parsed assignment node
	 * @throws SyntaxException if parsing fails
	 */
	private NodeAssn parseAssn() throws SyntaxException {
		Token id = curr();
		match("id");
		match("=");
		NodeExpr expr = parseExpr();
		NodeAssn assn = new NodeAssn(id.lex(), expr);
		return assn;
	}

	/**
	 * Parses a statement, which is currently an assignment statement followed by a semicolon.
	 * @return the parsed statement node
	 * @throws SyntaxException if parsing fails
	 */
	private NodeStmt parseStmt() throws SyntaxException {
		NodeAssn assn = parseAssn();
		match(";");
		NodeStmt stmt = new NodeStmt(assn);
		return stmt;
	}

	/**
	 * Parses a complete program.
	 * @param program the source code to parse
	 * @return the parsed statement node
	 * @throws SyntaxException if parsing fails
	 */
	public Node parse(String program) throws SyntaxException {
		scanner = new Scanner(program);
		scanner.next();
		NodeStmt stmt = parseStmt();
		match("EOF");
		return stmt;
	}

}
