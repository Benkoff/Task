package reports;

/**
 * An Exception to be thrown if it is attempted to write a report that has not been processed.
 */
public class DataNotProcessedException extends RuntimeException {

    /**
     * Sets the message of the Exception to "Data not processed, cannot write report".
     */
    public DataNotProcessedException() {
        super("Data not processed, cannot write report");
    }
}
