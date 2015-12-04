package bean;

import impl.AbstractBeanInfo;
import impl.ImageEvent;
import interfaces.ImageListener;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class MedianBeanInfo extends AbstractBeanInfo<Median, ImageEvent, ImageListener> {

    public MedianBeanInfo() {
        super(Median.class, ImageEvent.class, ImageListener.class);
    }
}
