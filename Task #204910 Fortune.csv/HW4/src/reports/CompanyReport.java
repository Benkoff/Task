package reports;

import analytics.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * A report for a single company of Fortune 500 data.
 * Report includes the minimum, maximum, average, and standard deviation of revenues, profits, and rank for all years
 * in which the company was ranked in the Fortune 500.
 */
public class CompanyReport implements Report {
    private String company;
    private File file;
    private List<Double> year;
    private List<Double> revenues;
    private List<Double> profits;
    private List<Double> rank;
    private Integer RANKED;
    private Double MINREV, MAXREV, AVGREV, STDREV;
    private Double MINPRO, MAXPRO, AVGPRO, STDPRO;
    private Double MINRANK, MAXRANK, AVGRANK, STDRANK;

    public CompanyReport(File inputFileIn, String companyIn) {
        file =  inputFileIn;
        company = companyIn;
        year = new ArrayList<>();
        revenues = new ArrayList<>();
        profits = new ArrayList<>();
        rank = new ArrayList<>();
    }

    /**
     * Reads data from Fortune 500 data file; processes the data.
     * The file is a csv file and can be assumed is formatted correctly.
     * See supplemental document for details on reading from csv files.
     * Calculates the minimum, maximum, average, and standard deviation of revenues, profits, and rank
     * for all years the company is ranked using tools in the Data class.
     * @return true if processing successful, false if not.
     */
    @Override
    public boolean processReport() {

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()){
                String[] tokens = scanner.nextLine().split(",");

                if (tokens[COMPANY_LOC].equals(company)) {
                    year.add(Double.parseDouble(tokens[YEAR_LOC]));
                    revenues.add(Double.parseDouble(tokens[REVENUE_LOC]));
                    profits.add(Double.parseDouble(tokens[PROFIT_LOC]));
                    rank.add(Double.parseDouble(tokens[RANK_LOC]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return false;
        }

        RANKED = year.size();
        if (RANKED == 0) {
            return false;
        }

        MINREV = Data.minimum(revenues.toArray(new Double[RANKED]));
        MAXREV = Data.maximum(revenues.toArray(new Double[RANKED]));
        AVGREV = Data.average(revenues.toArray(new Double[RANKED]));
        STDREV = Data.standardDeviation(revenues.toArray(new Double[RANKED]));

        MINPRO = Data.minimum(profits.toArray(new Double[RANKED]));
        MAXPRO = Data.maximum(profits.toArray(new Double[RANKED]));
        AVGPRO = Data.average(profits.toArray(new Double[RANKED]));
        STDPRO = Data.standardDeviation(profits.toArray(new Double[RANKED]));

        MINRANK = Data.minimum(rank.toArray(new Double[RANKED]));
        MAXRANK = Data.maximum(rank.toArray(new Double[RANKED]));
        AVGRANK = Data.average(rank.toArray(new Double[RANKED]));
        STDRANK = Data.standardDeviation(rank.toArray(new Double[RANKED]));

        System.out.println(toString());

        return true;
    }

    /**
     * Writes the processed report to the given file. File contents are based upon CompanyReport's toString.
     * @param outputFile File to write report to.
     * @return true if write successful, false if file cannot be created.
     * @throws DataNotProcessedException - Thrown if write attempted and report has not yet been processed.
     */
    @Override
    public boolean writeReport(File outputFile) throws DataNotProcessedException {

        if (RANKED == 0) {
            throw new DataNotProcessedException();
        }

        List<String> lines = Collections.singletonList(toString());

        try {
            Files.write(outputFile.toPath(), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    /**
     * Returns a formatted String of this report suitable for writing to an output file. String is of the form:
     * Fortune 500 Report for COMPANY ranked RANKED times
     * Revenue
     * Min: MINREV Max: MAXREV Avg: AVGREV StD: STDREV
     * Profit
     * Min: MINPRO Max: MAXPRO Avg: AVGPRO StD: STDPRO
     * Rank
     * Min: MINRANK Max: MAXRANK Avg: AVGRANK StD: STDRANK
     * Where COMPANY is the company, RANKED is the number of times the company has been ranked in the file,
     * MINREV, MAXREV, AVGREV, STDREV are the minimum, maximum, average, and standard deviation of revenues,
     * MINPRO, MAXPRO, AVGPRO, STDPRO are the minimum, maximum, average, and standard deviation of profits,
     * and MINRANK, MAXRANK, AVGRANK, STDRANK are the minimum, maximum, average, and standard deviation of rank.
     * These are all floating point values formatted to exactly three decimals except for MINRANK and MAXRANK
     * which are whole number values.
     * NOTE: There are no blank lines before, after, or between the lines, and the String DOES NOT end in a new line.
     * If your toString is not formatted exactly most tests will fail.
     * A JUnit test for this method is provided in the tests package to ensure your formatting is correct.
     * @return
     */
    @Override
    public String toString() {
        return "Fortune 500 Report for " + getCompany() + " ranked " + String.format("%d", RANKED) + " times\n" +
                "Revenue\n" +
                "Min: " + String.format("%.3f", MINREV) +
                " Max: " + String.format("%.3f", MAXREV) +
                " Avg: " + String.format("%.3f", AVGREV) +
                " StD: " + String.format("%.3f", STDREV) + "\n" +
                "Profit\n" +
                "Min: " + String.format("%.3f", MINPRO) +
                " Max: " + String.format("%.3f", MAXPRO) +
                " Avg: " + String.format("%.3f", AVGPRO) +
                " StD: " + String.format("%.3f", STDPRO) + "\n" +
                "Rank\n" +
                "Min: " + String.format("%.0f", MINRANK) +
                " Max: " + String.format("%.0f", MAXRANK) +
                " Avg: " + String.format("%.3f", AVGRANK) +
                " StD: " + String.format("%.3f", STDRANK);
    }

    /**
     * Returns the company of this report.
     * @return Company of this report.
     */
    public String getCompany() {
        return company;
    }
}
