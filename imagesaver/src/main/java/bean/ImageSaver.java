package bean;

import annotations.TargetDescriptor;
import impl.ImageEvent;
import impl.ImageEventHandler;
import impl.vetoablehelpers.FilePathVetoable;
import impl.vetoablehelpers.StringVetoable;
import interfaces.ImageListener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageSaver extends ImageEventHandler implements ImageListener {
    private static final String IMAGE_PATH = "imagePath";
    private static final String FILE_FORMAT = "png";

    @TargetDescriptor
    private String _imagePath = "out." + FILE_FORMAT;

    private ImageEvent _lastImageEvent;

    public ImageSaver() {
        super();
    }

    public String getImagePath() {
        return _imagePath;
    }

    public void setImagePath(String imagePath)
    throws PropertyVetoException {
        String temp = _imagePath;
        fireVetoableChange(this, IMAGE_PATH, temp, imagePath);

        _imagePath = imagePath;
        firePropertyChange(this, IMAGE_PATH, temp, imagePath);
    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        _lastImageEvent = imageEvent;

        if (imageEvent != null) {
            try {
                BufferedImage image = imageEvent.getImage().getAsBufferedImage();
                ImageIO.write(image, FILE_FORMAT, new File(_imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void reload() {
        onImageEvent(_lastImageEvent);
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        String propertyName = evt.getPropertyName();

        if (propertyName != null) {

            switch (propertyName) {

                case IMAGE_PATH: {
                    FilePathVetoable.validate(evt);
                    break;
                }
            }
        }
    }
}
