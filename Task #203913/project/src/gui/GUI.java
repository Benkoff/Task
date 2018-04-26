package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


// avoid extending complex JFrame
public class GUI {
    // TODO:  DON'T FORGET TO SET MY NAME
    // TODO:  DON'T FORGET TO SET MY NAME
    // TODO:  DON'T FORGET TO SET MY NAME
    // TODO:  DON'T FORGET TO SET MY NAME
    // TODO:  DON'T FORGET TO SET MY NAME
    private static final String myName = "My Name";
    // JFrame
    private final JFrame frame = new JFrame(myName);
    private int frameWidth = 800;
    private int frameHeight = 600;
    // draw panel
    private final DrawPanel panel = new DrawPanel(this);
    // buttons toolbar
    private final JToolBar toolbar;
    private int toolbarHeight = 24;
    // buttons
    private int buttonHeight = 20;
    private int buttonWidth = frameWidth/3;
    private final JButton swapButton = new JButton("swap");
    private final JButton centerButton = new JButton("center");
    private final JButton nameButton = new JButton(myName);

    public static void main(String[] args) {
        // Use an appropriate Look and Feel
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

    private static void createAndShowGUI() {
        new GUI();
    }

    private GUI() {
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(true);
        // locate in the center of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(((int)screenSize.getWidth() - frameWidth)/2,((int)screenSize.getHeight() - frameHeight)/2);

        // exit if the frame is closed
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //EXIT_ON_CLOSE may throw a SecurityException

        toolbar = new JToolBar();

        // layout managing
        frame.setLayout(new BorderLayout());
        frame.add(toolbar, BorderLayout.PAGE_START);
        frame.add(panel, BorderLayout.CENTER);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                frameHeight = e.getComponent().getHeight();
                frameWidth = e.getComponent().getWidth();

                setToolbarButtonsSize();
                panel.redraw();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                // do something here
            }
        });

        // make the frame visible
        frame.setVisible(true);

        buildToolbar();
    }


    private void setToolbarButtonsSize() {
        buttonWidth = frameWidth/3;
        // set buttons, width, height
        toolbar.setSize(new Dimension(frameWidth, toolbarHeight));
        swapButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        centerButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        nameButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
    }

    private void buildToolbar() {

        toolbar.add(swapButton);
        toolbar.add(centerButton);
        toolbar.add(nameButton);

        toolbar.setFloatable(false);

        swapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                System.out.println(this.toString());
                panel.swapGraphics();
            }
        });

        centerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.centerGraphics();
            }
        });

        nameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.invertColors();
            }
        });

        setToolbarButtonsSize();
    }

    public String getMyName() {
        return myName;
    }

    public int getToolbarHeight() {
        return toolbarHeight;
    }

    public Rectangle getFrameBounds() {
        Rectangle rectangle = new Rectangle(0, 0, frameWidth, frameHeight);
        System.out.println(rectangle + " " + getToolbarHeight());

        return rectangle;
    }
}
