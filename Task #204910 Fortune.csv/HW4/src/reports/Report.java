package reports;

import java.io.File;

/**
 * Interface for all reports generated from Fortune 500 data.
 * @author 
 *
 */
public interface Report
{
	/**
	 * Number of companies reported for each year.
	 */
	int NUMCOMPANIES = 500;
	/**
	 * First year of reported Fortune 500 companies.
	 */
	int MINYEAR = 1955;
	/**
	 * Most recent year of possible Fortune 500 data.
	 */
	int MAXYEAR = 2017;
	/**
	 * Location of Year in a record.
	 */
	int YEAR_LOC = 0;
	/**
	 * Location of Rank in a record.
	 */
	int RANK_LOC = 1;
	/**
	 * Location of Company in a record.
	 */
	int COMPANY_LOC = 2;
	/**
	 * Location of Revenue in a record.
	 */
	int REVENUE_LOC = 3;
	/**
	 * Location of Profit in a record.
	 */
	int PROFIT_LOC = 4;

	/**
	 * Reads data from file and processes it as required by report type.
	 * See documentation of implementing class for details.
	 * @return true if processing successful, false if not.
	 */
	boolean processReport() throws YearNotFoundException;
	/**
	 * Writes the report to provided file.
	 * See documentation of implementing class for details.
	 * @param outputFile File to write report to.
	 * @return true if write successful, false if not.
	 */
	boolean writeReport(File outputFile);
}
