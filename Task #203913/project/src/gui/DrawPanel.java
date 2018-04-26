package gui;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;

public class DrawPanel extends JPanel {
    private GUI gui;
    private String myName;

    private static final int BORDER = 0; // Panel's border

    // Drawings
    private Point ovalPoint;
    private Point ovalSubset = new Point(2, 2);
    private Point fontSize; // gets the size of Graphics font
    private Point ovalSize; // depends on fontSize

    // Images
    private static final String imgCrossFilename = "images/cross.gif";
    private static final String imgNoughtFilename = "images/nought.gif";
    private Image imgCross;   // drawImage() uses an Image object
    private Image imgNought;
    private Point imagePoint;
    private Point imageSize = new Point(24, 24);

    private Color mainColor = new Color(245,245,245);
    private Color invertedColor = new Color(10,10,10);

    public DrawPanel(GUI gui) {
        super();
        this.gui = gui;
        myName = gui.getMyName();

        build(this);
    }

    private void build(DrawPanel panel) {
        panel.setLayout(null);
        panel.setBackground(mainColor);
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        panel.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                JComponent source = (JComponent) e.getSource();
                Point fixed = fixBorder(e.getX(), e.getY());
                ovalPoint = new Point(fixed.x - ovalSize.x/2, fixed.y - ovalSize.y/2);
                imagePoint = new Point((int)(Math.random() * source.getWidth()), (int)(Math.random() * source.getHeight()));

//                System.out.println("Released:" + fixed.x + ":" + fixed.y + " ");
                redraw();
            }

            public void mousePressed(MouseEvent e) {
                JComponent source = (JComponent) e.getSource();
                Point fixed = fixBorder(e.getX(), e.getY());

//                System.out.print("Pressed:" + fixed.x + ":" + fixed.y + " ");
                redraw();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                JComponent source = (JComponent) e.getSource();
                int x = e.getX();
                int y = e.getY();
                Point fix = fixBorder(x, y);

            }

            public void mouseDragged(MouseEvent e) {
                JComponent source = (JComponent) e.getSource();
                int x = e.getX();
                int y = e.getY();
                Point fix = fixBorder(x, y);
//                System.out.print(fix.x + ";" + fix.y + " ");
            }
        });

        // Prepare the ImageIcon and Image objects for drawImage()
        ImageIcon iconCross = null;
        ImageIcon iconNought = null;
        URL imgURL = getClass().getClassLoader().getResource(imgCrossFilename);
        if (imgURL != null) {
            iconCross = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + imgCrossFilename);
        }
        imgCross = iconCross.getImage();

        imgURL = getClass().getClassLoader().getResource(imgNoughtFilename);
        if (imgURL != null) {
            iconNought = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + imgNoughtFilename);
        }
        imgNought = iconNought.getImage();

        int width = gui.getFrameBounds().width;
        int height = gui.getFrameBounds().height;
        ovalPoint = new Point(width/4, height/4);
        imagePoint = new Point(width/4*3, height/4*3);
        System.out.println(ovalPoint + " " + imagePoint);

        redraw();
    }

    public void redraw() {
        DrawPanel panel = this;
        panel.validate();
        panel.repaint(0, 0, panel.getWidth(), panel.getHeight());
    }

    public void invertColors() {
        Color buffer = mainColor;
        mainColor = invertedColor;
        invertedColor = buffer;

        DrawPanel panel = this;
        if (!panel.getBackground().equals(mainColor)) {
            panel.setBackground(mainColor);
            panel.getGraphics().setColor(invertedColor);
        } else {
            panel.setBackground(invertedColor);
            panel.getGraphics().setColor(mainColor);
        }
//        System.out.println(this.getUIClassID() + " background " + panel.getBackground());

        panel.redraw();
    }

    public void centerGraphics() {
        DrawPanel panel = this;
        ovalPoint = new Point((panel.getWidth() - ovalSize.x)/2, (panel.getHeight() - ovalSize.y)/2);
        imagePoint = new Point(ovalPoint.x - imageSize.x/2 + ovalSize.x/2, ovalPoint.y - imageSize.y/2 + ovalSize.y/2);
        panel.redraw();
    }

    public void swapGraphics() {
        DrawPanel panel = this;
        Point buffer = ovalPoint;
        ovalPoint = imagePoint;
        imagePoint = buffer;
        panel.redraw();
//        System.out.println("Location:" + this.getLocation());
    }

    private Point fixBorder(int x, int y) {
        if (x > this.getWidth() - BORDER) x = this.getWidth() - BORDER;
        if (x < BORDER) x = BORDER;
        if (y > this.getHeight() - BORDER) y = this.getHeight() - BORDER;
        if (y < BORDER) y = BORDER;

        return new Point(x, y);
    }

    private void setMetrics(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        fontSize = new Point(fm.stringWidth(myName), fm.getHeight());
        ovalSize = new Point(fontSize.x + ovalSubset.x*2, fontSize.y + ovalSubset.y*2);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        g.setColor(invertedColor);
        setMetrics(g);
        g.drawString(myName, ovalPoint.x + ovalSubset.x, ovalPoint.y + fontSize.y/4*3 + ovalSubset.y);
        g.drawOval(ovalPoint.x, ovalPoint.y, ovalSize.x, ovalSize.y);

        boolean useCross = Math.random() < 0.5;
        Image img = useCross ? imgCross : imgNought;
        g.drawImage(img, imagePoint.x, imagePoint.y, imageSize.x, imageSize.y, null);
    }
}
