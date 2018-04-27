package reports;

import java.io.File;

/**
 * A report for a single year of Fortune 500 data.
 * Report includes the minimum, maximum, average, and standard deviation of revenues and profits for all ranked
 * companies of the report's year.
 */
public class YearReport implements Report {
    private int year;

    public YearReport(File file, int inputInt) {
    }

    @Override
    public boolean processReport() throws YearNotFoundException {
        

        return false;
    }

    @Override
    public boolean writeReport(File outputFile) {
        return false;
    }

    public int getYear() {
        return year;
    }
}
