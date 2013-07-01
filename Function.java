/**
 * Calc
 * 
 * Simple scientific command line calculator
 * 
 * Semestral assignment for 'Umela inteligence a rozpoznavani' (KIV/UIR) 
 * at University of West Bohemia.
 * 
 * Module: Function.java
 * Represents mathematical function
 * 
 * Author: Martin Kucera
 * Date: 19.05.2013
 * Version: 1.01
 * 
 */

package semestralka;

/**
 * Object representing function extending
 * object operation. Holds function name
 * and operand count which function takes.
 */
public class Function extends Operation {
    /**
     * Function name
     */
    private String name;
    
    /**
     * Function operand count
     */
    private int operandCount;
    
    /**
     * Constructor, sets function name and op count
     * 
     * @param name Function name
     * @param operandCount Function operand count
     */
    public Function(String name, int operandCount) {
	this.name = name;
	this.operandCount = operandCount;
    }
    
    @Override
    /**
     * Returns function operand count
     */
    public int getOperandCount() {
	return this.operandCount;
    }
    
    @Override
    /**
     * Returns function name
     */
    public String getName() {
	return this.name;
    }
}
