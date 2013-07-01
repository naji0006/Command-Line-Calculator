/**
 * Calc
 * 
 * Simple scientific command line calculator
 * 
 * Semestral assignment for 'Umela inteligence a rozpoznavani' (KIV/UIR) 
 * at University of West Bohemia.
 * 
 * Module: Operation.java
 * Abstract class represeting mathematical operation,
 * either a function or an operator.
 * 
 * Author: Martin Kucera
 * Date: 19.05.2013
 * Version: 1.01
 * 
 */

package semestralka;

/**
 * Abstract class. Sets neccessary 
 * methods for classes that extend
 * this class.
 */
public abstract class Operation {
    /**
     * Returns operation operand count
     * 
     * @return Operand count
     */
    abstract public int getOperandCount();
    
    /**
     * Returns operation name
     * @return 
     */
    abstract public String getName();
}
