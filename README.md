# Project #: Translator Assignment #2

* Author: Anup Bhattarai
* Class: CS354
* Semester: Fall 2025

## Compiling and Using

The main class takes program strings as command-line arguments. Each argument is a complete program.

To compile all Java files run:

$ javac *.java

## Examples

### 1. Simple assignment and write

$ java Main "x = 5; wr x"

**Expected output:** `5`

### 2. Boolean Expression and If Statement

$ java Main "if 5 < 10 then wr 1"

**Expected output:** `1`

### 3. If-Else Statement

$ java Main "x = 5; if x < 10 then wr 1 else wr 0"

**Expected output:** `1`

### 4. While Loop

$ java Main "x = 0; while x < 3 do begin x = x + 1; wr x; end"

**Expected output:** 
```
1
2
3
```

#### 5. Block with Multiple Statements

$ java Main "begin x = 1; y = 2; z = x + y; wr z; end"

**Expected output:** `3`

### 6. Read Statement

$ echo "42" | java Main "rd x; wr x"

**Expected output:** `42`

#### 7. Complex Example

$ java Main "x = 0; while x < 5 do begin x = x + 1; if x == 3 then wr x; end"

**Expected output:** `3`



## Generating C Code

To generate C code, set the `Code` environment variable:

```bash
export Code=output
java Main "x = 5; wr x"
```

