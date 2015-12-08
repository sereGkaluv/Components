package components.bean;

import components.impl.AbstractBeanInfo;
import components.impl.ImageEvent;
import components.interfaces.ImageListener;

/**
 * Created by f00 on 03.12.15.
 */
public class ErodeBeanInfo extends AbstractBeanInfo<Erode, ImageEvent, ImageListener> {

    public ErodeBeanInfo() {
        super(Erode.class, ImageEvent.class, ImageListener.class);
    }
}
