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
	 * Parses a relational operator.
	 * @return the parsed relational operator node, or null if no operator found
	 * @throws SyntaxException if parsing fails
	 */
	private NodeRelop parseRelop() throws SyntaxException {
		if (curr().equals(new Token("<"))) {
			match("<");
			return new NodeRelop(pos(), "<");
		}
		if (curr().equals(new Token("<="))) {
			match("<=");
			return new NodeRelop(pos(), "<=");
		}
		if (curr().equals(new Token(">"))) {
			match(">");
			return new NodeRelop(pos(), ">");
		}
		if (curr().equals(new Token(">="))) {
			match(">=");
			return new NodeRelop(pos(), ">=");
		}
		if (curr().equals(new Token("<>"))) {
			match("<>");
			return new NodeRelop(pos(), "<>");
		}
		if (curr().equals(new Token("=="))) {
			match("==");
			return new NodeRelop(pos(), "==");
		}
		return null;
	}

	/**
	 * Parses a boolean expression.
	 * @return the parsed boolean expression node
	 * @throws SyntaxException if parsing fails
	 */
	private NodeBoolexpr parseBoolexpr() throws SyntaxException {
		NodeExpr expr1 = parseExpr();
		NodeRelop relop = parseRelop();
		if (relop == null) {
			throw new SyntaxException(pos(), new Token("relop"), curr());
		}
		NodeExpr expr2 = parseExpr();
		return new NodeBoolexpr(expr1, relop, expr2);
	}

	/**
	 * Parses a block, which is a sequence of statements.
	 * @return the parsed block node
	 * @throws SyntaxException if parsing fails
	 */
	private NodeBlock parseBlock() throws SyntaxException {
		NodeStmt stmt = parseStmt();
		if (curr().equals(new Token(";"))) {
			match(";");
			// If next token is "end", don't try to parse another block
			// (this handles the case where a semicolon is followed by "end")
			if (curr().equals(new Token("end"))) {
				return new NodeBlock(stmt);
			}
			NodeBlock block = parseBlock();
			return new NodeBlock(stmt, block);
		}
		return new NodeBlock(stmt);
	}

	/**
	 * Parses a statement, which can be:
	 * - assignment
	 * - read (rd id)
	 * - write (wr expr)
	 * - if-then or if-then-else
	 * - while-do
	 * - begin-end block
	 * @return the parsed statement node
	 * @throws SyntaxException if parsing fails
	 */
	private NodeStmt parseStmt() throws SyntaxException {
		// Check for 'rd' keyword
		if (curr().equals(new Token("rd"))) {
			match("rd");
			Token id = curr();
			match("id");
			return new NodeStmt(new NodeRd(id.lex()));
		}
		
		// Check for 'wr' keyword
		if (curr().equals(new Token("wr"))) {
			match("wr");
			NodeExpr expr = parseExpr();
			return new NodeStmt(new NodeWr(expr));
		}
		
		// Check for 'if' keyword
		if (curr().equals(new Token("if"))) {
			match("if");
			NodeBoolexpr boolexpr = parseBoolexpr();
			match("then");
			NodeStmt stmt1 = parseStmt();
			if (curr().equals(new Token("else"))) {
				match("else");
				NodeStmt stmt2 = parseStmt();
				return new NodeStmt(new NodeIf(boolexpr, stmt1, stmt2));
			}
			return new NodeStmt(new NodeIf(boolexpr, stmt1));
		}
		
		// Check for 'while' keyword
		if (curr().equals(new Token("while"))) {
			match("while");
			NodeBoolexpr boolexpr = parseBoolexpr();
			match("do");
			NodeStmt stmt = parseStmt();
			return new NodeStmt(new NodeWhile(boolexpr, stmt));
		}
		
		// Check for 'begin' keyword
		if (curr().equals(new Token("begin"))) {
			match("begin");
			NodeBlock block = parseBlock();
			match("end");
			return new NodeStmt(block);
		}
		
		// Otherwise, it's an assignment
		NodeAssn assn = parseAssn();
		return new NodeStmt(assn);
	}

	/**
	 * Parses a complete program.
	 * @param program the source code to parse
	 * @return the parsed block node
	 * @throws SyntaxException if parsing fails
	 */
	public Node parse(String program) throws SyntaxException {
		scanner = new Scanner(program);
		scanner.next();
		NodeBlock block = parseBlock();
		match("EOF");
		return block;
	}

}
