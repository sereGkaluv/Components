package components.bean;

import components.impl.AbstractBeanInfo;
import components.impl.ImageEvent;
import components.interfaces.ImageListener;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ThresholdBeanInfo extends AbstractBeanInfo<Threshold, ImageEvent, ImageListener> {

    public ThresholdBeanInfo() {
        super(Threshold.class, ImageEvent.class, ImageListener.class);
    }
}
