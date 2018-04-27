package reports;

/**
 * An Exception to be thrown if a requested year is not present in the data set.
 */
public class YearNotFoundException extends Exception {

    /**
     * Sets the message of the Exception to "Requested year not in file".
     */
    public YearNotFoundException() {
        super("Requested year not in file");
    }
}
