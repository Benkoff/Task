package tests;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;

import reports.YearReport;

public class YearReportTest {

	@Test
	public void unprocessedToStringTest()
	{
		YearReport y = new YearReport(new File("fortune500.csv"), 2020);
		String expected = "Fortune 500 Report for 2020\n" + 
				"Revenue\n" + 
				"Min: nul Max: nul Avg: nul StD: nul\n" + 
				"Profit\n" + 
				"Min: nul Max: nul Avg: nul StD: nul";
		
		assertEquals("Problem in YearReport basic toString format, check spelling, capitalization, spacing, and format",
				expected, y.toString());
	}

}
