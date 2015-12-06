package bean;

import annotations.TargetDescriptor;
import impl.ImageEvent;
import impl.ImageEventHandler;

import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageLoader extends ImageEventHandler implements Runnable {
    @TargetDescriptor
    private String _imagePath = "";

    public ImageLoader() {
    }

    public String getImagePath() {
        return _imagePath;
    }

    public void setImagePath(String imagePath) {
        _imagePath = imagePath;
        run();
    }

    @Override
    public void run() {
        try {
            ImageEvent imageEvent = new ImageEvent(this, loadImage(getImagePath()), 0, 0);
            notifyAllListeners(imageEvent);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private PlanarImage loadImage(String imagePath) {
        try {

            if (imagePath != null && !imagePath.trim().isEmpty()) {
                return PlanarImage.wrapRenderedImage(
                    ImageIO.read(new File(imagePath))
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
