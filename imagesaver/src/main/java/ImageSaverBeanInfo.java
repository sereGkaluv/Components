import impl.AbstractBeanInfo;
import impl.ImageEvent;
import interfaces.ImageListener;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageSaverBeanInfo extends AbstractBeanInfo<ImageSaver, ImageEvent, ImageListener> {

    public ImageSaverBeanInfo() {
        super(ImageSaver.class, ImageEvent.class, ImageListener.class);
    }
}
