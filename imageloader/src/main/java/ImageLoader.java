import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageLoader extends Canvas implements Runnable {
    private static final String DEFAULT_IMAGE_PLACEHOLDER_PATH = "image.png";
    private static final int DEFAULT_IMAGE_PLACEHOLDER_SIZE = 100;

    private transient String _imagePath = "";
    private transient BufferedImage _bufferedImage;

    public ImageLoader() {
        setImagePath(DEFAULT_IMAGE_PLACEHOLDER_PATH);
        setSize(DEFAULT_IMAGE_PLACEHOLDER_SIZE, DEFAULT_IMAGE_PLACEHOLDER_SIZE);
        new Thread(this).start();
    }

    private void loadImage(String imagePath) {
        try {
            _bufferedImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImagePath(String imagePath) {
        _imagePath = imagePath;
        new Thread(this).start();
    }

    public String getImagePath() {
        return _imagePath;
    }

    public void paint(Graphics g) {
        g.drawImage(_bufferedImage, 0, 0, this);
    }

    public void run() {
        try {
            loadImage(getImagePath());
            repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
