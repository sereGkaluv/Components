package bean;

import impl.AbstractBeanInfo;
import impl.ImageEvent;
import interfaces.ImageListener;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class MedianBeanBeanInfo extends AbstractBeanInfo<MedianBean, ImageEvent, ImageListener> {

    public MedianBeanBeanInfo() {
        super(MedianBean.class, ImageEvent.class, ImageListener.class);
    }
}
