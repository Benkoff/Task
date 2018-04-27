package fortuneapplication;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import reports.CompanyReport;
import reports.YearNotFoundException;
import reports.YearReport;

/**
 * Application for creating and writing reports from Fortune 500 data.
 * @author 
 *
 */
public class FortuneApplication extends JFrame
{
	/**
	 * Width of the application.
	 */
	private static final int APP_WIDTH = 450;
	/**
	 * Height of the application.
	 */
	private static final int APP_HEIGHT = 100;
	/**
	 * Input field size.
	 */
	private static final int FIELD_SIZE = 30;
	/**
	 * Label for input field.
	 */
	private JLabel inputLabel;
	/**
	 * Field for user to enter requested year or company.
	 */
	private JTextField input;
	/**
	 * User selects year report.
	 */
	private JRadioButton yearChoice;
	/**
	 * User selects company report.
	 */
	private JRadioButton companyChoice;
	/**
	 * Group radio buttons for exclusive behavior.
	 */
	private ButtonGroup radioButtons;
	/**
	 * Button for user to request report processing.
	 */
	private JButton process;
	/**
	 * Button for user to request report writing.
	 */
	private JButton write;
	/**
	 * Reference for generated YearReport.
	 */
	private YearReport yearReport;
	/**
	 * Reference for generated CompanyReport.
	 */
	private CompanyReport companyReport;
	/**
	 * File of Fortune 500 data.
	 */
	private File inputFile;
	
	/**
	 * Creates and initializes components of the application.
	 */
	private FortuneApplication()
	{
		//Set window title
		super("Fortune 500 Reports");

		setLayout(new FlowLayout());
		//Let user know what field is for
		inputLabel = new JLabel("Report On:");
		input = new JTextField(FIELD_SIZE);
		//Create user options using radio buttons
		yearChoice = new JRadioButton("Year Report", true);
		companyChoice = new JRadioButton("Company Report");
		//Group radio buttons for exclusive behavior
		radioButtons = new ButtonGroup();
		radioButtons.add(yearChoice);
		radioButtons.add(companyChoice);
		//Create buttons for user actions
		process = new JButton("Process");
		write = new JButton("Write");
		//Add listeners
		ButtonListener bl = new ButtonListener();
		process.addActionListener(bl);
		write.addActionListener(bl);
		//Add all components to frame
		add(inputLabel);
		add(input);
		add(yearChoice);
		add(companyChoice);
		add(process);
		add(write);
		//Set input file, change this if you want to try your own test files
		inputFile = new File("fortune500.csv");
	}
	
	/**
	 * Creates new application and initializes settings.
	 * @param args Not used.
	 */
	public static void main(String[] args)
	{
		//Create application
		FortuneApplication app = new FortuneApplication();
		//Terminate the application when window is closed
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Set dimensions
		app.setSize(APP_WIDTH, APP_HEIGHT);
		//Default size is all you get
		app.setResizable(false);
		//Center on screen
		app.setLocationRelativeTo(null);
		//Make sure it can be seen
		app.setVisible(true);
	}
	
	/**
	 * Listens for user selecting buttons and performs appropriate actions.
	 * @author Patrick Cavanaugh
	 *
	 */
	private class ButtonListener implements ActionListener
	{
		/**
		 * Executed when Process or Write button pressed.
		 */
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if (event.getSource() == process) // Create and Process Report
			{
				if (yearChoice.isSelected()) //Year Selected
				{
					try
					{	//Create the YearReport
						yearReport = new YearReport(inputFile, Integer.parseInt(input.getText()));

						if (!yearReport.processReport()) //Problem processing report
						{
							JOptionPane.showMessageDialog(null,
									"Error: Report Not Processed");
						}
						else //Report successfully processed
						{
							JOptionPane.showMessageDialog(null,
									"Report Processed");
						}
					}
					//Input string is not an integer!
					catch (NumberFormatException e)
					{
						//Inform user of their mistake
						JOptionPane.showMessageDialog(null, "Enter Whole Number for Year");
						//Reset input
						input.setText("");
					}
					//Requested year not in data
					catch (YearNotFoundException e)
					{
						JOptionPane.showMessageDialog(null, "Requested Year Not Found in Data");
						yearReport = null; //Reset YearReport to avoid writing irrelevant report
					}
				}
				else //Company Selected
				{
					//Ensure a company name is entered
					if (!input.getText().equals(""))
					{
						companyReport = new CompanyReport(inputFile, input.getText());
					}
					else //No company entered
					{
						//Let user know they need to enter a company name
						JOptionPane.showMessageDialog(null, "Enter Company");
						//Bail, nothing more can be done
						return;
					}
					
					if (!companyReport.processReport()) //Problem processing report
					{
						JOptionPane.showMessageDialog(null, "Error: Report Not Processed");
					}
					else //Report successfully processed
					{
						JOptionPane.showMessageDialog(null,
								"Report Processed");
					}
				}
			}
			else //Write Report
			{
				if (yearChoice.isSelected()) //Write Year Report
				{
					if (yearReport == null) //No report to write!
					{
						JOptionPane.showMessageDialog(null, "No Year Report to Write");
					}
					else
					{
						//Generate output file name based on year
						String f = String.format("Year%d.txt", yearReport.getYear());
						File out = new File(f);
						if (yearReport.writeReport(out)) //Try to write report
						{
							//Success! Reset!
							JOptionPane.showMessageDialog(null, "Year Report Written");
							yearReport = null;
							input.setText("");
						}
						else //Some problem writing the report
						{
							JOptionPane.showMessageDialog(null, "Could Not Write Report");
						}
					}
				}
				else //Write Company Report
				{
					if (companyReport == null) //No report to write!
					{
						JOptionPane.showMessageDialog(null, "No Company Report to Write");
					}
					else
					{
						//Generate output file name based on company
						String f = String.format("Company%s.txt", companyReport.getCompany());
						File out = new File(f);
						if (companyReport.writeReport(out)) //Try to write report
						{
							//Success! Reset!
							JOptionPane.showMessageDialog(null, "Company Report Written");
							companyReport = null;
							input.setText("");
						}
						else //Some problem writing report
						{
							JOptionPane.showMessageDialog(null, "Could Not Write Report");
						}
					}
				}
			}
			
		}
		
	}
}
