package components.bean;

import components.annotations.TargetDescriptor;
import components.impl.ImageEvent;
import components.impl.handlers.ImageEventHandler;
import components.impl.vetoablehelpers.FilePathVetoable;

import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageLoader extends ImageEventHandler {
    private static final String IMAGE_PATH = "imagePath";

    @TargetDescriptor
    private String _imagePath = "";

    public ImageLoader() {
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
    protected void reload() {
        try {

            PlanarImage image = PlanarImage.wrapRenderedImage(
                ImageIO.read(new File(getImagePath()))
            );

            ImageEvent imageEvent = new ImageEvent(this, image, 0, 0);
            notifyAllListeners(imageEvent);

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void vetoableChange(PropertyChangeEvent evt)
    throws PropertyVetoException {
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
