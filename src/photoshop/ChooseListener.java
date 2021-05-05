package photoshop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseListener implements ActionListener {

    private final Canvas panel;
    private final Filters filter;

    public ChooseListener(Canvas panel, Filters filter) {
        this.panel = panel;
        this.filter = filter;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "Restore to Original":
                panel.setImage(filter.restore());
                break;
            case "Open":
                panel.setImage(filter.open());
                break;
            case "Save":
                filter.save();
                break;
            case "Exit":
                System.exit(0);
            case "Horizontal Flip":
                panel.setImage(filter.horizontalFlip());
                break;
            case "Vertical Flip":
                panel.setImage(filter.verticalFlip());
                break;
            case "Sepia Tone":
                panel.setImage(filter.sepiaTone());
                break;
            case "Invert Colour":
                panel.setImage(filter.createInverted());
                break;
            case "Gray Scale":
                panel.setImage(filter.grayScale());
                break;
            case "Gaussian Blur":
                panel.setImage(filter.gaussianBlur());
                break;
            case "Bulge Effect":
                panel.setImage(filter.bulge());
                break;
        }
        panel.revalidate();
        panel.repaint();
    }
}
