package photoshop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;

public class Setup extends JFrame {

    private Canvas panel;
    private Filters filter;

    public Setup() {
        panel = new Canvas();
        filter = new Filters();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
        }

        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        addMenuItem(file, "Open", KeyEvent.VK_O);
        addMenuItem(file, "Save", KeyEvent.VK_S);
        file.addSeparator();
        addMenuItem(file, "Exit", KeyEvent.VK_E);
        menuBar.add(file);

        JMenu options = new JMenu("Options");
        addMenuItem(options, "Restore to Original", KeyEvent.VK_R);
        options.addSeparator();
        addMenuItem(options, "Horizontal Flip", KeyEvent.VK_H);
        addMenuItem(options, "Vertical Flip", KeyEvent.VK_V);
        addMenuItem(options, "Gray Scale", KeyEvent.VK_G);
        addMenuItem(options, "Sepia Tone", KeyEvent.VK_P);
        addMenuItem(options, "Invert Colour", KeyEvent.VK_I);
        addMenuItem(options, "Gaussian Blur", KeyEvent.VK_U);
        addMenuItem(options, "Bulge Effect", KeyEvent.VK_B);
        menuBar.add(options);

        JToolBar toolBar = new JToolBar();
        toolBar.setPreferredSize(new Dimension(900, 60));
        toolBar.add(addNewButton("Paint", "paint.png"));
        toolBar.add(addNewButton("Erase", "eraser.jpg"));
        toolBar.addSeparator();
        toolBar.setRollover(true);
        toolBar.setFloatable(false);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener((ChangeEvent e) -> {
            panel.setStroke(slider.getValue());
        });
        Hashtable labelTable = new Hashtable();

        for (int i = 0; i <= 10; i++) {
            labelTable.put(i, new JLabel("" + i));
        }
        slider.setLabelTable(labelTable);
        toolBar.add(slider);

        add(panel);
        panel.add(toolBar, BorderLayout.WEST);
        setSize(new Dimension(1000, 1000));
        setJMenuBar(menuBar);
        setVisible(true);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    }

    private void addMenuItem(JMenu menu, String title, int event) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(event, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand(title);
        menuItem.addActionListener(new ChooseListener(panel, filter));
        menu.add(menuItem);
    }

    private JButton addNewButton(String actionCommand, String imageName) {
        JButton button = new JButton();
        button.setToolTipText(actionCommand);
        button.addActionListener(new ChangeCursorSettings(panel));
        URL imgURL = getClass().getResource(imageName);
        button.setIcon(new ImageIcon(imgURL));
        button.setActionCommand(actionCommand);
        return button;
    }
}
