package photoshop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ChangeCursorSettings implements ActionListener {

    Canvas panel;

    public ChangeCursorSettings(Canvas panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Paint":
                panel.paint();
                JButton paintButton = (JButton) e.getSource();
                paintButton.setBackground(panel.getPaintColor());
                break;
            case "Erase":
                panel.erase();
                break;
        }
    }
}
