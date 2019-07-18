package gomouvt;

/**
 * Gomo unique view time (UVT) main object.
 *
 * @author  Peter Hambly
 * @version 1.0
 * @since   1.0
 */
public final class GomoTest {

    /**
     * Not called. Exists to placate Java lint
     */
    private GomoTest() {
    }

    /**
     * main() function.
     *
     * @param args arguments to program.
     * @throws UvtException (various).
     */
    public static void main(final String[] args)
        throws UvtException {
        Uvt uvt = new Uvt();

        uvt.addPeriod(args);
        double totalPeriod = uvt.getResults();

        if (Math.rint(totalPeriod) == totalPeriod) { // Integer
            System.out.println(String.format("%.0f", totalPeriod));
        } else {
            System.out.println(String.format("%.2f", totalPeriod));
        }
    }
}

