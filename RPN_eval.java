/**
 * Calc
 * 
 * Simple scientific command line calculator
 * 
 * Semestral assignment for 'Umela inteligence a rozpoznavani' (KIV/UIR) 
 * at University of West Bohemia.
 * 
 * Module: RPN_eval.java
 * Reverse polish notation (infix) equation evaluator
 * 
 * Author: Martin Kucera
 * Date: 19.05.2013
 * Version: 1.01
 * 
 */

package semestralka;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Postfix equation evaluation.
 * 
 */
public class RPN_eval {
    /**
     * Stack with operands
     */
    private Stack<String> stack;
    
    /**
     * Input postfix equation
     */
    private String expression;
    
    /**
     * Flag indicating if we have result value
     */
    private boolean hasResult = false;
    
    /**
     * Eval result
     */
    private double evalResult;
    
    /**
     * Sets expression to evaluate
     * 
     * @param expression Expression
     */
    public void setExpression(String expression) {
	this.hasResult = false;
	this.expression = expression;
    }
    
    /**
     * Evaluates current expression.
     * 
     * Algorithm: http://scriptasylum.com/tutorials/infix_postfix/algorithms/postfix-evaluation/index.htm
     * 
     * Special cases for assignments to variables and also cannot
     * assign value to reserved variable names such as 'pi' or 'e'
     */
    public void eval() {
	this.stack = new Stack<>();
	String[] tokens = this.expression.split(" ");
        
        if(tokens[tokens.length - 1].equalsIgnoreCase("dp")) {
            System.out.println(this.expression);
        }
		
	for(String token : tokens) {
	    if(Operations.isCommand(token)) {
		Operations.doCommand(token);
		
		this.hasResult = false;
		return;
	    }
	    else if(Operations.isFunction(token) || Operations.isOperator(token)) {
		double result;		
		Operation o;
		
		if(Operations.isFunction(token)) {
		    o = (Operation) Operations.getFunction(token);
		}
		else {
		    o = (Operation) Operations.getOperator(token);
		}
		
		// variable assignment
		if(o.getName().equals("=")) {
		    if(this.stack.empty()) {
			System.err.println("Missing variable name during assignment.");
			return;
		    }
		    
		    if(isNumeric(this.stack.peek()) || isDouble(this.stack.peek())) {
			System.err.println("Syntax error: Wrong variable name '" + this.stack.peek() + "'.");
			return;
		    }
		    
		    String variableName = this.stack.pop();
		    
		    if(variableName.equalsIgnoreCase("pi") || variableName.equalsIgnoreCase("e")
			    || variableName.equalsIgnoreCase("ans") || Operations.isFunction(variableName)) {
			System.err.println("Cannot assign to reserved variable name '" + variableName + "'.");
			return;
		    }
		    
		    double val;
		    
		    try {
			val = getDoubleFromStack();
		    }
		    catch(Exception e) {
			System.err.println("Error during assignment to variable '" + variableName + "': " +
				e.getMessage());
			return;
		    }
		    
		    result = val;
		    Variables.set(variableName, val);		    
		}
		// other operations
		else {		
		    int operandCount = o.getOperandCount();
		    double[] operands = new double[operandCount];

		    for(int i = 0; i < operandCount; i++) {
			try {
			    operands[i] = getDoubleFromStack();
			}
			catch (Exception e) {
			    System.err.println("Error during operation '" + 
				    o.getName() + "': " + e.getMessage());
			    return;
			}
		    }

		    result = Operations.doOperation(o, operands);
		}
		
		this.stack.push(String.valueOf(result));
	    }
	    else {
		int commaIndex = token.indexOf(",");
		
//		if(commaIndex != -1) {
//		    String[] arguments = token.split(",");
//		    System.out.println("token " + token);
//		    
//		    for(int d = 0; d < arguments.length; d++) {
//			stack.push(arguments[d]);
//		    }
//		}
//		else {
//		    stack.push(token);
//		}
                
                stack.push(token);
	    }
	}
	
	if(stack.size() != 1) {
	    System.err.println("Syntax error.");
	    return;
	}
	
	Double result;
	
	try {
	    result = getDoubleFromStack();
	}
	catch(Exception e) {
	    System.err.println("Syntax error: " + e.getMessage());
	    return;
	}
	
	// remember result
	Variables.set("ans", result);
	
	this.hasResult = true;
	this.evalResult = result;
    }
    
    /**
     * Checks if evaluation yielded a result
     * 
     * @return Evaluation result
     */
    public boolean hasResult() {
	return (this.hasResult);
    }
    
    /**
     * Tries to get double number from current stack.
     * 
     * @return Double
     * @throws Exception missing or invalid operand
     */
    private Double getDoubleFromStack() throws Exception {
	String stackTop;
	
	if(this.stack.empty()) {
	    throw new Exception("Missing operand.");
	}
	
	stackTop = this.stack.pop();
	
	if(Variables.isVariable(stackTop)) {
	    stackTop = String.valueOf(Variables.get(stackTop));
	}
	
	if(!isNumeric(stackTop) && !isDouble(stackTop)) {
	    throw new Exception("Invalid operand " + stackTop);
	}
	
	return (Double.valueOf(stackTop));
    }
    
    /**
     * Checks if given token is a number
     * 
     * @param token Number
     * @return true if number, false otherwise
     */
    private boolean isNumeric(String token) {
	NumberFormat formatter = NumberFormat.getInstance();
	ParsePosition pos = new ParsePosition(0);
	
	formatter.parse(token, pos);
	
	return (token.length() == pos.getIndex());
    }
    
    /**
     * Checks if input string is a double
     * 
     * @param token Double
     * @return true if double, false otherwise
     * 
     * !Taken directly from java source code double evaluation!
     */
    public static boolean isDouble(String token) {
	final String Digits     = "(\\p{Digit}+)";
	final String HexDigits  = "(\\p{XDigit}+)";
	// an exponent is 'e' or 'E' followed by an optionally 
	// signed decimal integer.
	final String Exp        = "[eE][+-]?"+Digits;
	final String fpRegex    =
	("[\\x00-\\x20]*"+  // Optional leading "whitespace"
	"[+-]?(" + // Optional sign character
	"NaN|" +           // "NaN" string
	"Infinity|" +      // "Infinity" string

	// A decimal floating-point string representing a finite positive
	// number without a leading sign has at most five basic pieces:
	// Digits . Digits ExponentPart FloatTypeSuffix
	// 
	// Since this method allows integer-only strings as input
	// in addition to strings of floating-point literals, the
	// two sub-patterns below are simplifications of the grammar
	// productions from the Java Language Specification, 2nd 
	// edition, section 3.10.2.

	// Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
	"((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

	// . Digits ExponentPart_opt FloatTypeSuffix_opt
	"(\\.("+Digits+")("+Exp+")?)|"+

	// Hexadecimal strings
	"((" +
	// 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
	"(0[xX]" + HexDigits + "(\\.)?)|" +

	// 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
	"(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

	")[pP][+-]?" + Digits + "))" +
	"[fFdD]?))" +
	"[\\x00-\\x20]*");// Optional trailing "whitespace"

	if (Pattern.matches(fpRegex, token))
	    return true;
	else {
	    return false;
	}
    }
    
    /**
     * Returns evaluation result
     * 
     * @return Result
     */
    public double getResult() {
	return this.evalResult;
    }
}
