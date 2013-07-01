/**
 * Calc
 * 
 * Simple scientific command line calculator
 * 
 * Semestral assignment for 'Umela inteligence a rozpoznavani' (KIV/UIR) 
 * at University of West Bohemia.
 * 
 * Module: Operator.java
 * Represents calculator operator
 * 
 * Author: Martin Kucera
 * Date: 19.05.2013
 * Version: 1.01
 * 
 */

package semestralka;

/**
 * Represents math operators.
 */
public class Operator extends Operation {
    /**
     * Operator name
     */
    private String name;
    
    /**
     * Operator operand count
     */
    private int operandCount;
    
    /**
     * Operator's precedence
     */
    private int precedence;
    
    /**
     * Operators association
     */
    private int assoc;
    
    /**
     * Operator's constructor, sets name, op count, precedence and association
     * 
     * @param name Operator name
     * @param operandCount Operand count
     * @param precedence Precedence
     * @param assoc Association (Operations.ASSOC_LEFT, Operations.ASSOC_RIGHT)
     */
    public Operator(String name, int operandCount, int precedence, int assoc) {
	this.name = name;
	this.operandCount = operandCount;
	
	this.precedence = precedence;
	this.assoc = assoc;
    }
    
    @Override
    /**
     * Returns operator's operand count
     */
    public int getOperandCount() {
	return this.operandCount;
    }
    
    @Override
    /**
     * Returns operator's name
     */
    public String getName() {
	return this.name;
    }
    
    /**
     * Returns operator's precedence
     * 
     * @return Precedence
     */
    public int getPrecedence() {
	return this.precedence;
    }
    
    /**
     * Returns operator's association
     * 
     * @return Association
     */
    public int getAssoc() {
	return this.assoc;
    }
}
