package bean;

import annotations.TargetDescriptor;
import impl.ImageEvent;
import impl.ImageEventHandler;
import interfaces.ImageListener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageSaver extends ImageEventHandler implements ImageListener {
    private static final String FILE_FORMAT = "png";

    @TargetDescriptor
    private transient String _imagePath = "";

    public ImageSaver() {
    }

    public String getImagePath() {
        return _imagePath;
    }

    public void setImagePath(String imagePath) {
        _imagePath = imagePath;
    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        if (imageEvent != null) {
            try {
                BufferedImage image = imageEvent.getImage().getAsBufferedImage();
                ImageIO.write(image, FILE_FORMAT, new File(_imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
