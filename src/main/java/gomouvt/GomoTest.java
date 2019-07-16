package gomouvt;

/**
 * Gomo unique view time (UVT) main object.
 *
 * @author  Peter Hambly
 * @version 1.0
 * @since   1.0
 */
public class GomoTest {
    /**
     * main() function.
     *
     * @param args arguments to program.
     */
    private static void main(final String[] args) {
        Uvt uvt = new Uvt();
        System.out.println(uvt.printResults(args));
    }
}

