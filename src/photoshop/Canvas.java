package photoshop;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class Canvas extends JPanel {

    private BufferedImage image = null;
    boolean addedListeners = false;
    int stroke = 5;
    int x, y, currentX, currentY;
    Color paintColor;
    Graphics2D g2;

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    
    public Color getPaintColor() {
        return paintColor;
    }
    
    public void setStroke(int stroke) {
        this.stroke = stroke;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            g2 = (Graphics2D) image.getGraphics();
            g2.setStroke(new BasicStroke(stroke));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(paintColor);
            g.drawImage(image, getWidth() / 2 - image.getWidth() / 2, getHeight() / 2 - image.getHeight() / 2, this);
        }
    }

    public void paint() {
        addListeners();
        paintColor = JColorChooser.showDialog(this, "Select a color", Color.BLACK);
    }

    public void erase() {
        addListeners();
        paintColor = Color.WHITE;
    }

    private void addListeners() {
        if (!addedListeners) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    x = e.getX() - (getWidth() - image.getWidth()) / 2;
                    y = e.getY() - (getHeight() - image.getHeight()) / 2;
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    currentX = e.getX() - (getWidth() - image.getWidth()) / 2;
                    currentY = e.getY() - (getHeight() - image.getHeight()) / 2;

                    if (g2 != null) {
                        g2.drawLine(x, y, currentX, currentY);
                        repaint();
                        x = currentX;
                        y = currentY;
                    }
                }
            });
        }
    }
}
