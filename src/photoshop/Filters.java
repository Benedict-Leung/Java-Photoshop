package photoshop;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Filters {

    private BufferedImage image;
    private File file;

    public BufferedImage open() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                file = fc.getSelectedFile();
                image = ImageIO.read(file);
                return image;
            } catch (IOException exception) {
            }
        }
        return null;
    }

    public BufferedImage horizontalFlip() {
        BufferedImage dimg = new BufferedImage(image.getWidth(), image.getHeight(), image.getColorModel().getTransparency());
        dimg.createGraphics().drawImage(image, 0, 0, image.getWidth(), image.getHeight(), image.getWidth(), 0, 0, image.getHeight(), null);
        image = dimg;
        return image;
    }

    public BufferedImage verticalFlip() {
        BufferedImage dimg = new BufferedImage(image.getWidth(), image.getHeight(), image.getColorModel().getTransparency());
        dimg.createGraphics().drawImage(image, 0, 0, image.getWidth(), image.getHeight(), 0, image.getHeight(), image.getWidth(), 0, null);
        image = dimg;
        return image;
    }

    public BufferedImage createInverted() {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if ((image.getRGB(x, y) >> 24) != 0x00) {
                    Color col = new Color(image.getRGB(x, y), true);
                    col = new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue());
                    image.setRGB(x, y, col.getRGB());
                }
            }
        }
        return image;
    }

    public BufferedImage grayScale() {
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(image, image);
        return image;
    }

    public BufferedImage sepiaTone() {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if ((image.getRGB(x, y) >> 24) != 0x00) {
                    Color col = new Color(image.getRGB(x, y), true);
                    col = new Color((0.393 * col.getRed() + 0.769 * col.getGreen() + 0.189 * col.getBlue() > 255) ? 255 : (int) ((0.393 * col.getRed() + 0.769 * col.getGreen() + 0.189 * col.getBlue())), (0.349 * col.getRed() + 0.686 * col.getGreen() + 0.168 * col.getBlue() > 255) ? 255 : (int) (0.349 * col.getRed() + 0.686 * col.getGreen() + 0.168 * col.getBlue()), (0.272 * col.getRed() + 0.534 * col.getGreen() + 0.131 * col.getBlue() > 255) ? 255 : (int) (0.272 * col.getRed() + 0.534 * col.getGreen() + 0.131 * col.getBlue()));
                    image.setRGB(x, y, col.getRGB());
                }
            }
        }
        return image;
    }

    public BufferedImage gaussianBlur() {
        float[] data = new float[9];
        Arrays.fill(data, 1.0f / 9.0f);
        ConvolveOp op = new ConvolveOp(new Kernel(3, 3, data), ConvolveOp.EDGE_NO_OP, null);
        image = op.filter(image, null);
        return image;
    }

    public BufferedImage bulge() {
        BufferedImage bulge = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                double rn = Math.pow(Math.sqrt((x - image.getWidth() / 2) * (x - image.getWidth() / 2) + (y - image.getHeight() / 2) * (y - image.getHeight() / 2)) / image.getWidth() / 2, 1.5) * 7 * Math.sqrt(image.getWidth() / 2 * image.getWidth() / 2 + image.getHeight() / 2 * image.getHeight() / 2);
                int newX = (int) (rn * Math.cos(Math.atan2(y - image.getHeight() / 2, x - image.getWidth() / 2)) + image.getWidth() / 2);
                int newY = (int) (rn * Math.sin(Math.atan2(y - image.getHeight() / 2, x - image.getWidth() / 2)) + image.getHeight() / 2);

                if (newX >= 0 && newX < image.getWidth() && newY >= 0 && newY < image.getHeight()) {
                    bulge.setRGB(x, y, image.getRGB(newX, newY));
                }
            }
        }
        image = bulge;
        return bulge;
    }

    public BufferedImage restore() {
        try {
            image = ImageIO.read(file);
            return image;
        } catch (IOException ex) {
            return null;
        }
    }

    public void save() {
        JFileChooser chooser = new JFileChooser();
        File saveFile = new File("untitled." + file.getName().substring(file.getName().indexOf('.') + 1, file.getName().length()));
        chooser.setSelectedFile(saveFile);

        if (chooser.showSaveDialog(chooser) == JFileChooser.APPROVE_OPTION) {
            try {
                ImageIO.write(image, file.getName().substring(file.getName().indexOf('.') + 1, file.getName().length()), chooser.getSelectedFile());
            } catch (IOException ex) {
            }
        }
    }
}
