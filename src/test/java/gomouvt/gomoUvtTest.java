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
 * @author  PAdds uvtPeriod to listeter Hambly
 * @version 1.0
 * @since   4.0
 */
public class gomoUvtTest {

    private static int testCount=0;
    private static final double PRECISION = 0.00001;
    
    private void runTest(
        final String testDescription, 
        final String[] testArgs, 
        final double expectResult)
        throws UvtException {

        Uvt uvt = new Uvt();

        testCount++;
        
        try {
            if (testDescription == null) {
                throw new UvtException("testDescription is null");
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
            
            uvt.addPeriod(testArgs); // Will throw an exception if null
            double actualResult=uvt.getResults();
            if (Math.rint(actualResult) == actualResult) { // Integer
                System.out.println("Result: " + actualResult);
            } else {
                System.out.println(String.format("Result: %.2f",
                    actualResult));
            }            
            assertEquals(actualResult, expectResult, PRECISION);
        } catch (Exception e) {
            System.out.println("Caught " +
             e.getClass() + " exception: " + e.getMessage());
            e.printStackTrace(); 
            throw e;
        }

    }
    
    // 1. supplied example: 0-1000, 2000-3000, 2500-4000
    @Test
    public void gomoUvtTest1()
        throws UvtException {
        final String[] test = {"0-1000", "2000-3000", "2500-4000"};
        runTest("supplied example", 
                test, 
                3000);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // 2. NullPointerException checks
    @Test
    public void gomoUvtTest2()
        throws UvtException {
        String testDescription="NullPointerException checks";

        thrown.expect(UvtException.class);
        thrown.expectMessage("testArgs is null or zero length");
        runTest(testDescription, 
            null, 
            0); 
    } 

    // 3. test invalid characters: 0-1000a, 2@000-3000, 2500-4000}
    @Test
    public void gomoUvtTest3()
        throws UvtException {
        String testDescription="test invalid characters";
        final String[] test = {"0-1000a", "2@000-3000", "2500-4000}"};

        thrown.expect(UvtException.class);
        thrown.expectMessage("String arg: '0-1000a' contains invalid characters");
        runTest(testDescription, 
            test, 
            0); 
    } 
 
    // 4. test no separator for a period: 0-1000, 2000, 2500-4000
    @Test
    public void gomoUvtTest4()
        throws UvtException {
        String testDescription="test no separator for a period";
        final String[] test = {"0-1000", "2000", "2500-4000"};

        thrown.expect(UvtException.class);
        thrown.expectMessage("String arg: '2000' contains invalid characters");
        runTest(testDescription, 
            test, 
            0); 
    }  
    
    // 5. test no period after separator: 0-1000, 2000-, 2500-4000
    @Test
    public void gomoUvtTest5()
        throws UvtException {
        String testDescription="test no period after separator";
        final String[] test = {"0-1000", "2000-", "2500-4000"};

        thrown.expect(UvtException.class);
        thrown.expectMessage("String arg: '2000-' contains invalid characters");
        runTest(testDescription, 
            test, 
            0); 
    } 
    
    // 6. test negative start time: -100-1000, 2000-3000, 2500-4000
    @Test
    public void gomoUvtTest6()
        throws UvtException {
        String testDescription="test negative start time";
        final String[] test = {"-100-1000", "2000-3000", "2500-4000"};

        thrown.expect(UvtException.class);
        thrown.expectMessage("String arg: '-100-1000' contains invalid characters");
        runTest(testDescription, 
            test, 
            0); 
    } 
    
    // 7. supplied example as decimal mS: 0.0-1000.0, 2000.0-3000.0, 2500.0-4000.0
    @Test
    public void gomoUvtTest7()
        throws UvtException {
        final String[] test = {"0.0-1000.0", "2000.0-3000.0", "2500.0-4000.0"};
        runTest("supplied example as decimal mS", 
                test, 
                3000);
    } 
    
    // 8. no overlap: 0-1000, 2000-2500, 3000-4000
    @Test
    public void gomoUvtTest8()
        throws UvtException {
        final String[] test = {"0-1000", "2000-2500", "3000-4000"};
        runTest("no overlap", 
                test, 
                2500);
    }
 
    // 9. different order: 0-1000, 2500-4000, 2000-3000
    @Test
    public void gomoUvtTest9()
        throws UvtException {
        final String[] test = {"0-1000", "2500-4000", "2000-3000"};
        runTest("different order", 
                test, 
                3000);
    } 
    
    // 10. different order 2: 2500-4000, 2000-3000, 0-1000
    @Test
    public void gomoUvtTest10()
        throws UvtException {
        final String[] test = {"2500-4000", "2000-3000", "0-1000"};
        runTest("different order 2", 
                test, 
                3000);
    }    

    // 11. multiple overlaps: 0-1000, 2000-3000, 2500-4000, 500-3500
    @Test
    public void gomoUvtTest11()
        throws UvtException {
        final String[] test = {"0-1000", "2000-3000", "2500-4000", "500-3500"};
        runTest("multiple overlaps", 
                test, 
                4000);
    }    
}

