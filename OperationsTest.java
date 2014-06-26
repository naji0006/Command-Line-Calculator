package semestralka;

import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Cyrus Naji
 */
public class OperationsTest {

    public OperationsTest() {
    }

    /**
     * TEST 1 Test of 'help' case in the doCommand method's switch statement, of
     * class Operations.
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
     * TEST 2 Test of 'help' case in the doCommand method's switch statement, of
     * class Operations.
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
    public void SecondtTestFactorial() {
        int factorialValue = 5;
        int expectedValue = 120;
        BigInteger n = BigInteger.valueOf((long) factorialValue);
        int result = Operations.factorial(n).intValue();
        assertEquals(expectedValue, result);
    }
}
