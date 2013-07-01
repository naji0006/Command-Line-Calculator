/**
 * Calc
 * 
 * Simple scientific command line calculator
 * 
 * Semestral assignment for 'Umela inteligence a rozpoznavani' (KIV/UIR) 
 * at University of West Bohemia.
 * 
 * Module: RPN_parser.java
 * Reverse polish notation (infix) equation parser
 * 
 * Author: Martin Kucera
 * Date: 19.05.2013
 * Version: 1.01
 * 
 */

package semestralka;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Reverse Polish Notation parser. Converts
 * equation in infix notation to postfix notation.
 */
public class RPN_parser {
    /**
     * Expression to parse
     */
    private String expression;
    
    /**
     * Final parsed expression
     */
    private String parsed;
    
    /**
     * Sets expression to parse
     * 
     * @param expression Infix expression
     */
    public void setExpression(String expression) {
	this.expression = expression;
    }
    
    /**
     * Converts input expression into standartised version.
     * Adds corrent spacimg, replaces all brackets with ().
     */
    private void standartiseExpression() {
	this.expression = this.expression.replaceAll("\\s", "");
	this.expression = this.expression.replaceAll("\\{", "(");
	this.expression = this.expression.replaceAll("\\}", ")");
	this.expression = this.expression.replaceAll("\\[", "(");
	this.expression = this.expression.replaceAll("\\]", ")");
	
	boolean spaceLeft = false;
	
	StringBuilder sb = new StringBuilder();
	
	for(int i = 0; i < this.expression.length(); i++) {
	    Character c = this.expression.charAt(i);
	    
	    if(Operations.isOperator(String.valueOf(c)) ||
		    c.equals('(') || c.equals(')') || c.equals('!')) {
		if(!spaceLeft) {
		    sb.append(" ");
		}
		
		sb.append(c);
		sb.append(" ");
		
		spaceLeft = true;
	    }
	    else {
		sb.append(c);
		
		spaceLeft = false;
	    }
	}
	
	this.expression = sb.toString().trim();
    }
    
    /**
     * Extended version of standartising. First removes all
     * spaces, then converts all brackets to (). Goes
     * through whole epxression char by char and adds spaces
     * at correct locations. Also inserts missing multiplication
     * sign * at places where it is ommited, for example:
     * e2pi converts into e * 2 * pi
     */
    private void standartiseExpression_ext() {
	this.expression = this.expression.replaceAll("\\s", "");
	this.expression = this.expression.replaceAll("\\{", "(");
	this.expression = this.expression.replaceAll("\\}", ")");
	this.expression = this.expression.replaceAll("\\[", "(");
	this.expression = this.expression.replaceAll("\\]", ")");
	
	StringBuilder sb = new StringBuilder();
	StringBuilder var = new StringBuilder();
	
	boolean spaceLeft = false;
	boolean mulLeft = false;
	boolean parLeft = false;
	
	boolean pastAssignment = (this.expression.indexOf("=") >= 0) ? false : true;
	
	for(int i = 0; i < this.expression.length(); i++) {
	    Character c = this.expression.charAt(i);
	    
	    if(Operations.isOperator(String.valueOf(c)) ||
		    c.equals('(') || c.equals(')') || c.equals('!')) {
		// if it's not part of variable name
		if(pastAssignment) {
		    // if string buffer is not empty
		    if(var.toString().trim().length() != 0) {
			// if it's either a variable or a function
			if(Variables.isVariable(var.toString()) || Operations.isFunction(var.toString())) {
			    if(spaceLeft) {
				sb.append(" ");
			    }
			    if(mulLeft) {
				sb.append("* ");
			    }
			    
			    sb.append(var);
			    if(Variables.isVariable(sb.toString()) && c.equals('(')) {
				sb.append(" * ");
			    }
			    else {
				sb.append(" ");
			    }
			    var.setLength(0);
			    
			    if(Variables.isVariable(var.toString())) {			    
				spaceLeft = false;
				mulLeft = true;
			    }
			    else {
				mulLeft = false;
				spaceLeft = false;
			    }
			}
			// syntax error but include it anyways
			else {
			    sb.append(var);
			    sb.append(" ");
			    var.setLength(0);
			    
			    mulLeft = true;
			    spaceLeft = false;
			}
		    }
		    // part of variable name
		    else {
			sb.append(var);
		    }
		}
		
		if(spaceLeft) {
		    sb.append(" ");
		}
				
		if(mulLeft && c.equals('(')) {
		    sb.append("* ");		    
		}
		
		sb.append(c);
		sb.append(" ");
		
		if(c.equals('=')) {
		    pastAssignment = true;
		}
		
		spaceLeft = false;
		mulLeft = false;
		parLeft = false;
		
		if(c.equals(')')) {
		    mulLeft = true;
		    parLeft = true;
		}
	    }
	    else {
		// if it's not part of variable name
		if(pastAssignment) {
		    // if char is number or a dot and not a part of variable or function
		    if((RPN_eval.isDouble(c.toString()) || c.equals('.')) 
			    && !Operations.isFunction(var.toString() + "" + c.toString())
			    && !Variables.isVariable(var.toString() + "" + c.toString())) {
			// if string buffer is not empty
			if(var.toString().trim().length() != 0) {
			    // if it's a function or a variable
			    if(Operations.isFunction(var.toString()) || Variables.isVariable(var.toString())) {
				if(spaceLeft) {
				    sb.append(" ");
				}
				
				if(mulLeft) {
				    sb.append("* ");
				}

				sb.append(var);
				if(Variables.isVariable(var.toString())) {
				    sb.append(" * ");
				}
				else {
				    sb.append(" ");
				}
				var.setLength(0);
				
				if(Operations.isFunction(var.toString())) {
				    mulLeft = false;
				}
			    }
			    else {
				sb.append(var);
				var.setLength(0);
			    }
			}
			
			if(mulLeft && (var.length() > 0) || parLeft) {
			    sb.append("* ");
			}
			
			sb.append(c);
			
			spaceLeft = true;
			mulLeft = true;
			parLeft = false;
		    }
		    else {
			parLeft = false;
			var.append(c);
		    }
		}
		else {
		    sb.append(c);
		    
		    spaceLeft = true;
		    parLeft = false;
		}
	    }
	}
	
	// end of expression, empty string buffer
	if(var.toString().length() > 0) {
	    if(spaceLeft) {
		sb.append(" ");
	    }
	    if(mulLeft) {
		sb.append("* ");
	    }
	    
	    sb.append(var);
	}
        
	this.expression = sb.toString().trim();
    }
    
    /**
     * Converts input infix equation into
     * postfix.
     * 
     * Algorithm source: http://scriptasylum.com/tutorials/infix_postfix/algorithms/infix-postfix/index.htm
     * 
     * Special case for sign - indicating negative, if it's a first operator
     * or comming after another operator sign, it means negative, else subtraction
     * 
     * @return false indicating usually parantheses mismatch, 
     * true if parsing went correctly
     */
    public boolean parse() {
	Boolean success = true;
	
	if(this.expression.trim().length() == 0) {
	    return false;
	}
	
	this.standartiseExpression_ext();
	
	StringBuilder sb = new StringBuilder();
	Queue<String> output = new LinkedList<>();
	Stack<String> stack = new Stack<>();
	
	int left_count = 0;
	int right_count = 0;
	
	int equalIndex = this.expression.indexOf("=");
	String varName = "";
	
	if(equalIndex >= 0) {
	    varName = this.expression.substring(0, equalIndex);
	    
	    this.expression = this.expression.substring(equalIndex + 1).trim();
	}
	
	String[] tokens = this.expression.split(" ");
	
	boolean firstOp = true;	
	String lastOp = "";

	for(String token : tokens) {
	    if(Operations.isFunction(token)) {
		stack.push(token);
	    }
	    else if(Operations.isOperator(token)) {		
		if(token.equals("-") && (firstOp || (!lastOp.equals("") && !lastOp.equals("!")))) {
		    token = "_";
		}
		
		while(!stack.empty() && Operations.isOperator(stack.peek()) &&
			(
			(Operations.getOperator(token).getAssoc() == Operations.ASSOC_LEFT &&
			(Operations.getOperator(token).getPrecedence() - Operations.getOperator(stack.peek()).getPrecedence() <= 0)) ||
			(
			(Operations.getOperator(token).getPrecedence() - Operations.getOperator(stack.peek()).getPrecedence() < 0)
			)
			)) {
		    output.add(stack.pop());
		}
		
		lastOp = token;
		stack.push(token);
	    }
	    else if(token.equals("(")) {		
		lastOp = "(";
		
		stack.push(token);
		left_count++;
	    }
	    else if(token.equals(")")) {
		lastOp = "";
		
		while(!stack.empty() && !stack.peek().equals("(")) {
		    output.add(stack.pop());
		}
		
		if(!stack.empty()) {
		    if(!stack.peek().equals("(")) {
			success = false;
		    }
		}
		else {
		    return false;
		}
		
		stack.pop();
		
		if(!stack.empty()) {
		    if(Operations.isFunction(stack.peek())) {
			output.add(stack.pop());
		    }
		}
		
		right_count++;
	    }
	    else {
		lastOp = "";
		
		output.add(token);
	    }
	    
	    firstOp = false;
	}
	if(left_count != right_count) {
	    success = false;
	}
	
	while(!stack.empty()) {
	    output.add(stack.pop());
	}
	
	while(!output.isEmpty()) {
	    sb.append(output.poll());
	    sb.append(" ");
	}
	
	if(!varName.equals("")) {	    
	    sb.append(varName.trim());
	    sb.append(" ");
	    sb.append("=");
	}
	
	this.parsed = sb.toString().trim().replaceAll("_", "-1 *")
                .replaceAll("\\,", " ").replaceAll("\\s+", " ");
        
        return success;
    }
    
    /**
     * Returns parsed string
     * 
     * @return Postfix string
     */
    public String getParsed() {
	return this.parsed;
    }
}
