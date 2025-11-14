/**
 * This class generates C code from the parsed program.
 * It writes the generated C code to a file specified by the Code environment variable.
 */

import java.util.HashMap;
import java.io.*;

public class Code {

	private final String[] prologue={
		"#include <stdio.h>",
		"int main() {",
	};

	private final String[] epilogue={
		"return 0;",
		"}",
	};

	/**
	 * Constructs a Code generator and writes the C code to a file.
	 * @param code the generated C code
	 * @param env the environment containing variable declarations
	 */
	public Code(String code, Environment env) {
		String fn=System.getenv("Code");
		if (fn==null)
			return;
		try {
			BufferedWriter f=new BufferedWriter(new FileWriter(fn+".c"));
			for (String s: prologue)
				f.write(s+"\n");
			f.write(env.toC());
			f.write(code);
			for (String s: epilogue)
				f.write(s+"\n");
			f.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
