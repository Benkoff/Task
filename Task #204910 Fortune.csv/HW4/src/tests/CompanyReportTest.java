package tests;

import org.junit.Test;
import reports.CompanyReport;
import reports.DataNotProcessedException;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class CompanyReportTest {
    private static final File FILE = new File("fortune500.csv");

	@Test
	public void unprocessedToStringTest() {
		CompanyReport c = new CompanyReport(FILE, "Nike");
		String expected = "Fortune 500 Report for Nike ranked 0 times\n" + 
				"Revenue\n" + 
				"Min: nul Max: nul Avg: nul StD: nul\n" + 
				"Profit\n" + 
				"Min: nul Max: nul Avg: nul StD: nul\n" + 
				"Rank\n" + 
				"Min: null Max: null Avg: nul StD: nul";
		
		assertEquals("Problem in CompanyReport basic toString format, check spelling, capitalization, spacing, and format",
				expected, c.toString());
	}

	@Test(expected = DataNotProcessedException.class)
	public void unprocessedThrowsDataNotProcessedException()  {
        CompanyReport n = new CompanyReport(FILE, "Nike");
        n.writeReport(new File("CompanyNike.txt"));
	}


}
