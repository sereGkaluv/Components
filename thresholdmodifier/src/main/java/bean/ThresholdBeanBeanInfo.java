package bean;

import impl.AbstractBeanInfo;
import impl.ImageEvent;
import interfaces.ImageListener;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ThresholdBeanBeanInfo extends AbstractBeanInfo<ThresholdBean, ImageEvent, ImageListener> {

    public ThresholdBeanBeanInfo() {
        super(ThresholdBean.class, ImageEvent.class, ImageListener.class);
    }
}
