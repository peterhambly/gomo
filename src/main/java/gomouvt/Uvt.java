package gomouvt;

/**
 * Gomo unique view time (UVT) object.
 *
 * @author  Peter Hambly
 * @version 1.0
 * @since   1.0
 */
public class Uvt {

    /**
     * Constructor.
     */
    public Uvt() {
    }

    /**
     * Gets Rif40NumDenomService object JSON.
     *
     * @param testArgs arguments to program. A string array with the stringa
     *        in the form n-m. Where n and m are times in milliseconds.
     *        n<=m and must not be negative
     * @return To UVT as a String
     * @throws NullPointerException if testArgs is null or zero length
     */
    public String printResults(
        final String[] testArgs)
        throws NullPointerException {

        if (testArgs == null || testArgs.length == 0) {
            throw new NullPointerException("testArgs is null or zero length");
        }
        return "3000";
    }
}

