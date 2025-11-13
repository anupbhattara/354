/**
 * This class is a lexical analyzer (scanner) for a simple arithmetic language.
 * It tokenizes the input program into tokens such as identifiers, numbers,
 * operators, and keywords.
 */

import java.util.*;

public class Scanner {

	private String program;		// source program being interpreted
	private int pos;			// index of next char in program
	private Token token;		// last/current scanned token

	// sets of various characters and lexemes
	private Set<String> whitespace=new HashSet<String>();
	private Set<String> digits=new HashSet<String>();
	private Set<String> letters=new HashSet<String>();
	private Set<String> legits=new HashSet<String>();
	private Set<String> keywords=new HashSet<String>();
	private Set<String> operators=new HashSet<String>();

	// initializers for previous sets

	/**
	 * Fills a set with characters in a range.
	 * @param s the set to fill
	 * @param lo the low character (inclusive)
	 * @param hi the high character (inclusive)
	 */
	private void fill(Set<String> s, char lo, char hi) {
		for (char c=lo; c<=hi; c++)
			s.add(c+"");
	}

	/**
	 * Initializes the whitespace character set.
	 * @param s the set to initialize
	 */
	private void initWhitespace(Set<String> s) {
		s.add(" ");
		s.add("\n");
		s.add("\t");
	}

	/**
	 * Initializes the digit character set.
	 * @param s the set to initialize
	 */
	private void initDigits(Set<String> s) {
		fill(s,'0','9');
	}

	/**
	 * Initializes the letter character set.
	 * @param s the set to initialize
	 */
	private void initLetters(Set<String> s) {
		fill(s,'A','Z');
		fill(s,'a','z');
	}

	/**
	 * Initializes the legitimate character set (letters and digits).
	 * @param s the set to initialize
	 */
	private void initLegits(Set<String> s) {
		s.addAll(letters);
		s.addAll(digits);
	}

	/**
	 * Initializes the operator character set.
	 * @param s the set to initialize
	 */
	private void initOperators(Set<String> s) {
		s.add("=");
		s.add("+");
		s.add("-");
		s.add("*");
		s.add("/");
		s.add("(");
		s.add(")");
		s.add(";");
		s.add("<");
		s.add(">");
	}

	/**
	 * Initializes the keyword set.
	 * @param s the set to initialize
	 */
	private void initKeywords(Set<String> s) {
		s.add("rd");
		s.add("wr");
		s.add("if");
		s.add("then");
		s.add("else");
		s.add("while");
		s.add("do");
		s.add("begin");
		s.add("end");
	}

	// constructor:
	//     - squirrel-away source program
	//     - initialize sets
	/**
	 * Constructs a new scanner for the given program.
	 * @param program the source code to scan
	 */
	public Scanner(String program) {
		this.program=program;
		pos=0;
		token=null;
		initWhitespace(whitespace);
		initDigits(digits);
		initLetters(letters);
		initLegits(legits);
		initKeywords(keywords);
		initOperators(operators);
	}

	// handy string-processing methods

	/**
	 * Checks if the scanner has reached the end of the program.
	 * @return true if at end of program, false otherwise
	 */
	public boolean done() {
		return pos>=program.length();
	}

	/**
	 * Advances the scanner position past any characters in the given set.
	 * @param s the set of characters to skip
	 */
	private void many(Set<String> s) {
		while (!done()&&s.contains(program.charAt(pos)+""))
			pos++;
	}

	/**
	 * This method advances the scanner until the current input character
	 * is just after a sequence of one or more of a particular character.
	 * Arguments:
	 *     c = the character to search for
	 * Members:
	 *     program = the scanner's input
	 *     pos = index of current input character
	 * @param c the character to advance past
	 */
	private void past(char c) {
		while (!done()&&c!=program.charAt(pos))
			pos++;
		if (!done()&&c==program.charAt(pos))
			pos++;
	}

	// scan various kinds of lexeme

	/**
	 * Scans a numeric literal, which can be an integer or floating-point number.
	 * Handles both decimal integers and floating-point numbers with decimal points.
	 */
	private void nextNumber() {
		int old=pos;
		many(digits);
		
		// Check for decimal point
		if (!done() && program.charAt(pos) == '.') {
			pos++; // consume the decimal point
			many(digits); // scan digits after decimal point
		}
		
		token=new Token("num",program.substring(old,pos));
	}

	/**
	 * Scans a keyword or identifier.
	 * Keywords are reserved words, identifiers are variable names.
	 */
	private void nextKwId() {
		int old=pos;
		many(letters);
		many(legits);
		String lexeme=program.substring(old,pos);
		token=new Token((keywords.contains(lexeme) ? lexeme : "id"),lexeme);
	}

	/**
	 * Scans an operator token.
	 * Handles both single-character and two-character operators.
	 */
	private void nextOp() {
		int old=pos;
		char c = program.charAt(pos);
		
		// Check for two-character relational operators
		if (pos + 1 < program.length()) {
			char next = program.charAt(pos + 1);
			String twoChar = "" + c + next;
			
			// Check for <=, >=, <>, ==
			if (twoChar.equals("<=") || twoChar.equals(">=") || 
			    twoChar.equals("<>") || twoChar.equals("==")) {
				pos = old + 2;
				token = new Token(twoChar);
				return;
			}
		}
		
		// Single character operator
		pos = old + 1;
		String lexeme = program.substring(old, pos);
		token = new Token(lexeme);
	}

	/**
	 * This method determines the kind of the next token (e.g., "id"),
	 * and calls a method to scan that token's lexeme (e.g., "foo").
	 * @return true if a token was scanned, false if EOF was reached
	 */
	public boolean next() {
		many(whitespace);
		if (done()) {
			token=new Token("EOF");
			return false;
		}
		String c=program.charAt(pos)+"";
		if (digits.contains(c))
			nextNumber();
		else if (letters.contains(c))
			nextKwId();
		else if (operators.contains(c))
			nextOp();
		else {
			System.err.println("illegal character at position "+pos);
			pos++;
			return next();
		}
		return true;
	}

	/**
	 * This method scans the next lexeme if the current token is the expected token.
	 * @param t the expected token
	 * @throws SyntaxException if the current token doesn't match the expected token
	 */
	public void match(Token t) throws SyntaxException {
		if (!t.equals(curr()))
			throw new SyntaxException(pos,t,curr());
		next();
	}

	/**
	 * Gets the current token.
	 * @return the current token
	 * @throws SyntaxException if no token is available
	 */
	public Token curr() throws SyntaxException {
		if (token==null)
			throw new SyntaxException(pos,new Token("ANY"),new Token("EMPTY"));
		return token;
	}

	/**
	 * Gets the current position in the source code.
	 * @return the current position
	 */
	public int pos() {
		return pos;
	}

	// for unit testing
	public static void main(String[] args) {
		try {
			Scanner scanner=new Scanner(args[0]);
			while (scanner.next())
				System.out.println(scanner.curr());
		} catch (SyntaxException e) {
			System.err.println(e);
		}
	}

}
