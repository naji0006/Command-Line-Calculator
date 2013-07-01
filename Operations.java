/**
 * Calc
 * 
 * Simple scientific command line calculator
 * 
 * Semestral assignment for 'Umela inteligence a rozpoznavani' (KIV/UIR) 
 * at University of West Bohemia.
 * 
 * Module: Operations.java
 * Handles all avaliable operations
 * 
 * Author: Martin Kucera
 * Date: 19.05.2013
 * Version: 1.01
 * 
 */

package semestralka;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;

/**
 * Represents all operations (functions, operators..)
 */
public class Operations {
    /**
     * Value indicating that operation has left assoc 
     */
    public static final int ASSOC_LEFT = 0;
    
    /**
     * Value indicating that operation has right assoc
     */
    public static final int ASSOC_RIGHT = 1;
    
    /**
     * All avaliable functions
     */
    private final static ArrayList<Function> functions;
    
    /**
     * All avaliable operators
     */
    private final static ArrayList<Operator> operators;
    
    /**
     * All avaliable commands
     */
    private final static ArrayList<String> commands;
    
    /**
     * Flag indicating that we are using radians
     */
    private static boolean inRadians = true;
    
    /**
     * Instantiates and adds all functions
     */
    static {
	functions = new ArrayList<>();
	
	functions.add(new Function("sin", 1));
	functions.add(new Function("cos", 1));
	functions.add(new Function("tan", 1));
	functions.add(new Function("arcsin", 1));
	functions.add(new Function("arccos", 1));
	functions.add(new Function("arctan", 1));
	functions.add(new Function("ln", 1));
	functions.add(new Function("arctan2", 2));
	functions.add(new Function("pow", 2));
	functions.add(new Function("log", 1));
    }
    
    /**
     * Instantiates and adds all operators
     */
    static {
	operators = new ArrayList<>();
	
	operators.add(new Operator("+", 2, 0, ASSOC_LEFT));
	operators.add(new Operator("-", 2, 0, ASSOC_LEFT));
	operators.add(new Operator("*", 2, 5, ASSOC_LEFT));
	operators.add(new Operator("/", 2, 5, ASSOC_LEFT));
	operators.add(new Operator("%", 2, 5, ASSOC_LEFT));
	operators.add(new Operator("^", 2, 10, ASSOC_RIGHT));
	operators.add(new Operator("_", 2, 10, ASSOC_RIGHT));
	operators.add(new Operator("=", 2, 20, ASSOC_RIGHT));
	operators.add(new Operator("!", 1, 20, ASSOC_RIGHT));
    }
    
    /**
     * Adds all avaliable commands
     */
    static {
	commands = new ArrayList<>();
	
	commands.add("setradians");
	commands.add("setdegrees");
	commands.add("listfunctions");
	commands.add("listoperators");
	commands.add("listvariables");
	commands.add("help");
	
	// unreachable commands, just for the sake of consistency
	commands.add("exit");
	commands.add("end");
    }
    
    /**
     * Checks if input token represents an operator
     * 
     * @param token Input string
     * @return true if operator, false otherwise
     */
    public static boolean isOperator(String token) {
	return (getOperator(token) != null);
    }
    
    /**
     * Checks if input token rerepsents a function
     * 
     * @param token Input string
     * @return true if operator, false otherwise
     */
    public static boolean isFunction(String token) {
	return (getFunction(token) != null);
    }
    
    /**
     * Returns operator by name
     * 
     * @param name Operator name
     * @return Operator object or null
     */
    public static Operator getOperator(String name) {
	Operator op = null;
	
	for(Operator o : operators) {
	    if(o.getName().equalsIgnoreCase(name)) {
		op = o;
		
		break;
	    }
	}
	
	return op;
    }
    
    /**
     * Returns function by name
     * 
     * @param name Function name
     * @return Function object or null
     */
    public static Function getFunction(String name) {
	Function fn = null;
	
	for(Function f: functions) {
	    if(f.getName().equalsIgnoreCase(name)) {
		fn = f;
		
		break;
	    }
	}
	
	return fn;
    }
    
    /**
     * Executes input operation.
     * 
     * @param op Operation to execute
     * @param args Operation arguments
     * @return Operation result
     */
    public static Double doOperation(Operation op, double[] args) {
	double result;
	
	if(op instanceof Function) {
	    result = doFunction((Function) op, args);
	}
	else {
	    result = doOperator((Operator) op, args);
	}
	
	return result;
    }
    
    /**
     * Executes input function.
     * 
     * @param f Function name
     * @param args Function arguments
     * @return Function result
     */
    public static Double doFunction(Function f, double[] args) {
	double result = .0;
	
	switch(f.getName()) {
	    case "sin":
		result = Math.sin((inRadians) ? args[0] : Math.toRadians(args[0]));
		break;
	    case "cos":
		result = Math.cos((inRadians) ? args[0] : Math.toRadians(args[0]));
		break;
	    case "tan":
		result = Math.tan((inRadians) ? args[0] : Math.toRadians(args[0]));
		break;
	    case "arcsin":
		result = Math.asin((inRadians) ? args[0] : Math.toRadians(args[0]));
		break;
	    case "arccos":
		result = Math.acos((inRadians) ? args[0] : Math.toRadians(args[0]));
		break;
	    case "arctan":
		result = Math.atan((inRadians) ? args[0] : Math.toRadians(args[0]));
		break;
	    case "arctan2":
		result = Math.atan2(args[1], args[0]);
		break;
	    case "ln":
		result = Math.log(args[0]);
		break;
	    case "log":
		result = Math.log10(args[0]);
		break;
	    case "pow":
		result = Math.pow(args[1], args[0]);
		break;
	}
	
	return result;
    }
    
    /**
     * Executes operator.
     * 
     * @param o Operator to execute
     * @param args Operator operands
     * @return Operator result
     */
    public static Double doOperator(Operator o, double[] args) {
	double result = .0;
	
	switch(o.getName()) {
	    case "+":
		result = args[1] + args[0];
		break;
	    case "-":
		result = args[1] - args[0];
		break;
	    case "*":
		result = args[1] * args[0];
		break;
	    case "/":
		result = args[1] / args[0];
		break;
	    case "%":
		result = args[1] % args[0];
		break;
	    case "^":
		result = Math.pow(args[1], args[0]);
		break;
	    case "!":
		BigInteger n = BigInteger.valueOf((long) args[0]);
		result = factorial(n).doubleValue();
		break;
	}
	
	return result;
    }
    
    /**
     * Executes input command.
     * 
     * @param token Command to execute
     */
    public static void doCommand(String token) {
	switch(token) {
	    case "setradians":
		setInRadians();
		break;
	    case "setdegrees":
		setInDegrees();
		break;
	    case "listfunctions":
		listFunctions();
		break;
	    case "listoperators":
		listOperators();
		break;
	    case "listvariables":
		listVariables();
		break;
	    case "help":
		help();
		break;
	}
    }
    
    /**
     * Calculates factorial in BigInteger.
     * @param n n
     * @return factorial 
     */
    public static BigInteger factorial(BigInteger n) {
	BigInteger result = BigInteger.ONE;

	while (!n.equals(BigInteger.ZERO)) {
	    result = result.multiply(n);
	    n = n.subtract(BigInteger.ONE);
	}

	return result;
    }
    
    /**
     * Sets current mode to radians
     */
    public static void setInRadians() {
	System.out.println("Setting mode to radians.");
	inRadians = true;
    }
    
    /**
     * Sets current mode to degrees
     */
    public static void setInDegrees() {
	System.out.println("Setting mode to degrees.");
	inRadians = false;
    }
    
    /**
     * Prints all avaliable functions
     */
    public static void listFunctions() {
	System.out.println("List of avaliable functions: ");
	StringBuilder sb = new StringBuilder();
	
	for(Function f : functions) {
	    sb.append(f.getName());
	    sb.append(", ");
	}
	
	System.out.println(sb.toString());
    }
    
    /**
     * Prints all avaliable operators
     */
    public static void listOperators() {
	System.out.println("List of avaliable operators: ");
	StringBuilder sb = new StringBuilder();
	
	for(Operator o : operators) {
	    if(o.getName().equalsIgnoreCase("_")) {
		continue;
	    }
	    sb.append(o.getName());
	    sb.append(", ");
	}
	
	System.out.println(sb.toString());
    }
    
    /**
     * Prints all avaliable variables
     */
    public static void listVariables() {
	System.out.println("List of avaliable variables: ");
	StringBuilder sb = new StringBuilder();
	
	Map<String, Double> variables = Variables.getAll();
	
	for(Map.Entry<String, Double> v : variables.entrySet()) {
	    sb.append(v.getKey());
	    sb.append(" = ");
	    sb.append(v.getValue());
	    sb.append(", ");
	}
	
	System.out.println(sb.toString());
    }
    
    /**
     * Prints user help
     */
    public static void help() {
	System.out.println("Simple command line calculator.");
	System.out.println("--------------------------------");
	
	System.out.println("List of examples: ");
	examples();
	System.out.println("--------------------------------");
	
	System.out.println("Avaliable commands: ");
	StringBuilder sb = new StringBuilder();
	
	for(String c : commands) {
	    sb.append(c);
	    sb.append(", ");
	}
	
	System.out.println(sb.toString());
	System.out.println("--------------------------------");
    }
    
    /**
     * Prints possible examples
     */
    public static void examples() {
	System.out.println("5 + 5");
	System.out.println("x = ans");
	System.out.println("x = 5! + 10");
	System.out.println("y = x + arctan2(30, 30)");
	System.out.println("cos ( ( 1.3 + 1 ) ^ ( 1 / 3 ) ) - log ( -2 * 3 / -14 )");
	System.out.println("(4!+8)/4 + (2+3*4)^2 - 3^4 + (9+10)(15-21) + 7(17-15)");
	
	System.out.println("x = 2");
	System.out.println("-4 [1/10(x-1)(x-4)] + 4[-1/6(x+1)(x-4)]+1[1/15(x+1)(x-1)]");
    }
    
    /**
     * Checks if input string is a command.
     * 
     * @param token Command name
     * @return true if valid command, false otherwise
     */
    public static boolean isCommand(String token) {
	return (commands.contains(token));
    }
    
    /**
     * Returns current calculation mode.
     * 
     * @return RAD if in radians, DEG if in degrees
     */
    public static String getMode() {
	if(inRadians) {
	    return "RAD";
	}
	else {
	    return "DEG";
	}
    }
}
