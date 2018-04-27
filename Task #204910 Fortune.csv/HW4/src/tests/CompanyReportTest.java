package tests;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;
import reports.CompanyReport;

public class CompanyReportTest {

	@Test
	public void unprocessedToStringTest()
	{
		CompanyReport c = new CompanyReport(new File("fortune500.csv"), "Nike");
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

}
