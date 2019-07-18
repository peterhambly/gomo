package gomouvt;

/**
 * Gomo unique view time (UVT) exception.
 *
 * @author  Peter Hambly
 * @version 1.0
 * @since   1.0
 */
public class UvtException extends Exception {

    /**
     * Basic constructor.
     *
     * @param errorMessage textual error message
     */
    public UvtException(

        final String errorMessage) {

        super(errorMessage);
    }

    /**
     * Throwable constructor.
     *
     * @param errorMessage textual error message
     * @param err Throwable
     */
    public UvtException(
        final String errorMessage, final Throwable err) {

        super(errorMessage, err);
    }
}
