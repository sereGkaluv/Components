package bean;

import impl.AbstractBeanInfo;
import impl.ImageEvent;
import interfaces.ImageListener;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class OpeningBeanInfo extends AbstractBeanInfo<Opening, ImageEvent, ImageListener> {

    public OpeningBeanInfo() {
        super(Opening.class, ImageEvent.class, ImageListener.class);
    }
}
