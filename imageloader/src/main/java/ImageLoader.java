import impl.ImageEvent;
import impl.ImageEventHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageLoader extends ImageEventHandler implements Runnable {
    private transient String _imagePath = "";

    public ImageLoader() {
    }

    public String getImagePath() {
        return _imagePath;
    }

    public void setImagePath(String imagePath) {
        _imagePath = imagePath;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            ImageEvent imageEvent = new ImageEvent(this, loadImage(getImagePath()));
            notifyAllListeners(imageEvent);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage loadImage(String imagePath) {
        try {

            if (imagePath != null && !imagePath.trim().isEmpty()) return ImageIO.read(new File(imagePath));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
