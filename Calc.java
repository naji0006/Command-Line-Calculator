/**
 * Calc
 * 
 * Simple scientific command line calculator
 * 
 * Semestral assignment for 'Umela inteligence a rozpoznavani' (KIV/UIR) 
 * at University of West Bohemia.
 * 
 * Module: Calc.java
 * Main program module, handles user input and displays results
 * 
 * Author: Martin Kucera
 * Date: 19.05.2013
 * Version: 1.01
 * 
 */

package semestralka;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Main class, takes user input in loop and passes
 * string first into parser than into eval
 */
public class Calc {
    /**
     * Main method, loops user input and prints result.
     */
    public static void main(String[] args) {
	BufferedReader reader = new BufferedReader(
		new InputStreamReader(System.in));
	
	RPN_parser parser = new RPN_parser();
	RPN_eval eval = new RPN_eval();
	
	String input;
	
	Operations.help();
	
	System.out.print("INPUT(" + Operations.getMode() + "): ");
	
	try {
	    while(!(input = reader.readLine()).equalsIgnoreCase("end") &&
		    !(input.equalsIgnoreCase("exit"))) {
		if(input.trim().length() > 0) {
		    parser.setExpression(input);
		    
		    if(parser.parse()) {
			eval.setExpression(parser.getParsed());
			eval.eval();

			if(eval.hasResult()) {
			    System.out.println("RESULT: " + eval.getResult());
			}
		    }
		    else {
			System.err.println("Syntax error: Mismatched parantheses.");
		    }
		}
		else {
		    System.err.println("Empty string.");
		}
		
		System.out.print("INPUT(" + Operations.getMode() + "): ");
	    }
	    
	    System.out.println("Exiting.");
	}
	catch (Exception e) {
	    System.err.println("Error reading input: " + e.getMessage());
	}
    }
}
