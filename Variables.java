/**
 * Calc
 * 
 * Simple scientific command line calculator
 * 
 * Semestral assignment for 'Umela inteligence a rozpoznavani' (KIV/UIR) 
 * at University of West Bohemia.
 * 
 * Module: Variables.java
 * Represents calculator variables
 * 
 * Author: Martin Kucera
 * Date: 19.05.2013
 * Version: 1.01
 * 
 */

package semestralka;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing variables
 */
public class Variables {
    /**
     * Map holding all variables in name - value pairs
     */
    private static final Map<String, Double> variables;
    
    /**
     * Instantiate and add default variables
     */
    static {
	variables = new HashMap<>();
	
	variables.put("e", Math.E);
	variables.put("pi", Math.PI);
    }
    
    /**
     * Define variable
     * 
     * @param name Variable name
     * @param val Variable value
     */
    public static void set(String name, Double val) {
	variables.put(name, val);
    }
    
    /**
     * Get variable
     * 
     * @param name Variable name
     * @return Value
     */
    public static Double get(String name) {
	return variables.get(name);
    }
    
    /**
     * Checks if variable with input name exists
     * 
     * @param name Variable name
     * @return true if variable exists, false otherwise
     */
    public static boolean isVariable(String name) {
	return variables.containsKey(name);
    }
    
    /**
     * Returns all variables
     * 
     * @return Variable map
     */
    public static Map<String, Double> getAll() {
	return variables;
    }
}
