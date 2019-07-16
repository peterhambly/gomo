package gomouvt;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import java.util.StringJoiner;
import java.lang.NullPointerException;

/**
 * Gomo unique view time (UVT) test object.
 *
 * @author  Peter Hambly
 * @version 1.0
 * @since   4.0
 */
public class gomoUvtTest {

    private Uvt uvt = new Uvt();
    private static int testCount=0;

    private void runTest(
        final String testDescription, 
        final String[] testArgs, 
        final String expectResult) {
        testCount++;
        
        if (testDescription == null) {
            throw new NullPointerException("testDescription is null");
        }
        
        if (testArgs != null && 
            testArgs.length> 0) {
            StringJoiner args = new StringJoiner(", ");
            for (String s: testArgs) {
                args.add(s);
            }
            System.out.println("Test " + testCount + 
                " [" + testDescription + "]: " + args.toString());
        }
        else {
            System.out.println("Test " + testCount + 
                " [" + testDescription + "]: no/null args");
        }
        
        String actualResult=uvt.printResults(testArgs);
        System.out.println("Result: " + actualResult);
        assertThat(actualResult, containsString(expectResult));
    }
    
    @Test
    public void gomoUvtTest1() {
        final String[] test = {"0-1000", "2000-3000", "2500-4000"};
        runTest("supplied example", 
                test, 
                "3000");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void gomoUvtTest2() {
        String testDescription="test NullPointerException";

        thrown.expect(NullPointerException.class);
        thrown.expectMessage("testArgs is null or zero length");
        runTest(testDescription, 
            null, 
            null); 
    } 
                
    @Test
    public void gomoUvtTest3() {
        final String[] test = {"0-1000", "2000-3000", "2500-4000"};
        runTest("no overlap", 
                test, 
                "4000");
    }
    
    // Multiple overlaps
    // Negative start time
    // No end time
    // No start time
    // Invalid characters
    // >2 times in a period
    // No separator for a period
}

