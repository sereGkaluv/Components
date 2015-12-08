package components.bean;

import components.impl.AbstractBeanInfo;
import components.impl.ImageEvent;
import components.interfaces.ImageListener;

/**
 * Created by sereGkaluv on 04-Dec-15.
 */
public class ClosingBeanInfo extends AbstractBeanInfo<Closing, ImageEvent, ImageListener> {

    public ClosingBeanInfo() {
        super(Closing.class, ImageEvent.class, ImageListener.class);
    }
}
