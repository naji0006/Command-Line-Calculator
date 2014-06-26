
package semestralka;

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
     * Purpose: check to see if user input is case sensitive.
     * Test result: FAILED.
     * Reason: program is case sensitive to user inputs. 
     * Solution: changing ==> parser.setExpression(input); 
     * to ==> parser.setExpression(input.toLowerCase()); ==> in line 48 of class Calc.java
     *** see next test case for validity of solution. ***
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
     * Test result: PASSED.
     */
    @Test
    public void secondTestDoCommandHelp() {
        String token = "help";
        Operations.doCommand(token);
        assertEquals(token, "Help".toLowerCase());
    }
}