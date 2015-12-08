package components.bean;

import components.impl.AbstractBeanInfo;
import components.impl.ImageEvent;
import components.interfaces.ImageListener;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageSaverBeanInfo extends AbstractBeanInfo<ImageSaver, ImageEvent, ImageListener> {

    public ImageSaverBeanInfo() {
        super(ImageSaver.class, ImageEvent.class, ImageListener.class);
    }
}
