package calculator;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import static java.lang.Math.round;

public class PayCalc extends JPanel implements PropertyChangeListener {
    // Fields store labels values.
    private double rate = 0.0;
    private final double overtime = 1.5;
    private final int regular = 40;
    private int hours = 0;
    private double payment;

    // Labels to identify the fields.
    private JLabel rateLabel;
    private JLabel hoursLabel;
    private JLabel paymentLabel;

    // Strings for the labels.
    private static String rateString = "Rate per hour: ";
    private static String hoursString = "Total hours: ";
    private static String paymentString = "Payment: ";

    // Fields for data entry.
    private JFormattedTextField rateField;
    private JFormattedTextField hoursField;
    private JFormattedTextField paymentField;

    // Formats to format and parse numbers.
    private NumberFormat rateFormat;
    private NumberFormat hoursFormat;
    private NumberFormat paymentFormat;

    public static void main(String[] args) {
        // Use an appropriate Look and Feel.
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Schedule a job for the event dispatch thread: creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("PayCalc");
        frame.setResizable(false);

        // Add contents to the window.
        frame.add(new PayCalc());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int)(screenSize.getWidth() * 0.45),(int)(screenSize.getHeight() * 0.45));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //EXIT_ON_CLOSE may throw a SecurityException

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private PayCalc() {
        super(new BorderLayout());
        setUpFormats();
        payment = computePayment(rate, overtime, regular, hours);

        // Create the labels.
        rateLabel = new JLabel(rateString);
        hoursLabel = new JLabel(hoursString);
        paymentLabel = new JLabel(paymentString);

        // Create the text fields and set them up.
        rateField = new JFormattedTextField(rateFormat);
        rateField.setValue(rate);
        rateField.setColumns(10);
        rateField.addPropertyChangeListener("value", this);

        hoursField = new JFormattedTextField();
        hoursField.setValue(hours);
        hoursField.setColumns(10);
        hoursField.addPropertyChangeListener("value", this);

        paymentField = new JFormattedTextField(paymentFormat);
        paymentField.setValue(payment);
        paymentField.setColumns(10);
        paymentField.setEditable(false);
        paymentField.setForeground(Color.red);

        // Tell accessibility tools about label/textfield pairs.
        rateLabel.setLabelFor(rateField);
        hoursLabel.setLabelFor(hoursField);
        paymentLabel.setLabelFor(paymentField);

        // Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(rateLabel);
        labelPane.add(hoursLabel);
        labelPane.add(paymentLabel);

        // Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(rateField);
        fieldPane.add(hoursField);
        fieldPane.add(paymentField);

        // Put the panels in this panel, labels on left, text fields on right.
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
    }

    // Create and set up number formats. These objects also parse numbers input by user.
    private void setUpFormats() {
        rateFormat = NumberFormat.getNumberInstance();
        rateFormat.setMinimumFractionDigits(2);
        hoursFormat = NumberFormat.getNumberInstance();
        paymentFormat = NumberFormat.getCurrencyInstance();
    }

    private double computePayment(double rate, double overtime, int regular, int hours) {
        double result = 0;
        if(hours > regular) {
            result = ((hours - regular) * overtime + regular) * rate;
        } else {
            result = hours * rate;
        }
        // Round to the penny
        return (double)(round(result * 100))/ 100;
    }

    // Runs when fields values change
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
        if (source == rateField) {
            rate = ((Number)rateField.getValue()).doubleValue();
        } else if (source == hoursField) {
            hours = ((Number)hoursField.getValue()).intValue();
        }

        // Computes and sets payment with new values
        double payment = computePayment(rate, overtime, regular, hours);
        paymentField.setValue(payment);
    }
}
