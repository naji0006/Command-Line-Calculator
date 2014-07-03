package semestralka;

import java.math.BigInteger;
import java.util.Stack;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Cyrus Naji
 */
public class OperationsTest {

    public OperationsTest() {}

    /**
     * TEST 1 
     * Test of 'help' case in the doCommand method's switch statement, of class Operations.
     *
     * Purpose: check to see if user input is case sensitive in 'help' case statement. 
     * Result: FAILED. 
     * Reason: program is case sensitive to user inputs. 
     * Solution: changing ==> parser.setExpression(input);
     * to ==> parser.setExpression(input.toLowerCase()); ==> in line 48 of class Calc.java.
     */
    @Test
    public void firstTestDoCommandHelp() {
        String token = "help";
        Operations.doCommand(token);
        assertEquals(token, "Help");
    }

    /**
     * TEST 2 
     * Test of 'help' case in the doCommand method's switch statement, of class Operations.
     *
     * Purpose: checking solution to correct the issue discovered in TEST 1. 
     * result: PASSED.
     */
    @Test
    public void secondTestDoCommandHelp() {
        String token = "help";
        Operations.doCommand(token);
        assertEquals(token, "Help".toLowerCase());
    }

    /**
     * TEST 3 
     * Test of factorial method's loop, of class Operations.
     *
     * Purpose: checking if program can throw an error when BigInteger n is less than 0. 
     * Result: FAILED 
     * Reason: program enters to an infinite loop when dealing with negative factorial.
     * Solution: validate user input when they use the factorial function, or display 0 
     * as the result if chose to give the ability to users to enter negative factorial.
     */
    @Test
    public void firstTestFactorial() {
        int factorialValue = -5;
        int expectedValue = -120;
        BigInteger n = BigInteger.valueOf((long) factorialValue);
        int result = Operations.factorial(n).intValue();
        assertEquals(expectedValue, result);
    }

    /**
     * TEST 4 
     * Test of factorial method's loop, of class Operations.
     *
     * Purpose: checking if method can adhere to its loop condition. 
     * Result: Passed
     */
    @Test
    public void secondtTestFactorial() {
        int factorialValue = 5;
        int expectedValue = 120;
        BigInteger n = BigInteger.valueOf((long) factorialValue);
        int result = Operations.factorial(n).intValue();
        assertEquals(expectedValue, result);
    }
    
    /**
     * TEST 5
     * Test of if statement in method isDouble, of class Operations.
     *
     * Purpose: checking if the statement can match the pattern fpRegex in the if statement. 
     * Result: PASSED 
     */
    @Test
    public void firstTestIsDouble() {
        String token = "5.35";
        boolean expResult = true;
        boolean result = RPN_eval.isDouble(token);
        assertEquals(expResult, result);
    }
    
    /**
     * TEST 6
     * Test of if statement in method isDouble, of class Operations.
     *
     * Purpose: checking if the statement can match the pattern fpRegex in the if statement. 
     * Result: FAILED 
     * Reason: program deem the token invalid according to pattern characters in fpRegex.
     */
    @Test
    public void secondTestIsDouble() {
        String token = "6/25";
        boolean expResult = true;
        boolean result = RPN_eval.isDouble(token);
        assertEquals(expResult, result);
    }

    /**
     * TEST 7
     * Test of last if statement in method getDoubleFromStack, of class RPN_eval.
     *
     * Purpose: checking first alternate path of the if statement with first different condition.
     * Result: FAILED
     * Reason: first condition of first alternate path fails.
     */
    @Test
    public void firstTestGetDoubleFromStack() {
        Stack<String> stackTop = new Stack();
        stackTop.push("5.00");
        stackTop.push("+");
        stackTop.push("1.00");
        String stack = stackTop.pop();
        RPN_eval test = new RPN_eval();
        boolean expResult = false;
        if (!test.isNumeric(stack) && test.isDouble(stack)) {
            expResult = true;
        }
        assertEquals(expResult, true);
    }

    /**
     * TEST 8
     * Test of last if statement in method getDoubleFromStack, of class RPN_eval.
     *
     * Purpose: checking second alternate path of the if statement with second different condition.
     * Result: FAILED
     * Reason: second condition of second alternate path fails.
     */
    @Test
    public void secondTestGetDoubleFromStack() {
        Stack<String> stackTop = new Stack();
        stackTop.push("9.00");
        stackTop.push("+");
        stackTop.push("3.00");
        String stack = stackTop.pop();
        RPN_eval test = new RPN_eval();
        boolean expResult = false;
        if (test.isNumeric(stack) && !test.isDouble(stack)) {
            expResult = true;
        }
        assertEquals(expResult, true);
    }
    
    /**
     * TEST 9
     * Test of last if statement in method getDoubleFromStack, of class RPN_eval.
     *
     * Purpose: checking third alternate path of the if statement with both conditions being different.
     * Result: PASSED
     */
    @Test
    public void thirdTestGetDoubleFromStack() {
        Stack<String> stackTop = new Stack();
        stackTop.push("4.00");
        stackTop.push("+");
        stackTop.push("6.00");
        String stack = stackTop.pop();
        RPN_eval test = new RPN_eval();
        boolean expResult = false;
        if (test.isNumeric(stack) && test.isDouble(stack)) {
            expResult = true;
        }
        assertEquals(expResult, true);
    }
    
    /**
     * TEST 10
     * Test of last if statement in method eval, of class RPN_eval.
     *
     * Purpose: testing alternate path to the condition stack.size() != 1. 
     * Result: FAILED
     * Reason: Condition fails due to the size of stack being not eqaul to one.
     */
    @Test
    public void evalTest() {
        Stack<String> stack = new Stack();
        stack.push("RAD");
        stack.push("x");
        stack.push("=");
        stack.push("5");
        stack.push("!");
        stack.push("+");
        stack.push("1");
        boolean expResult = false;
        if (stack.size() == 1) {
            expResult = true;
        }
        assertEquals(expResult, true);
    }
}
