package bean;

import impl.AbstractBeanInfo;
import impl.ImageEvent;
import interfaces.ImageListener;

/**
 * Created by f00 on 03.12.15.
 */
public class ErodeBeanInfo extends AbstractBeanInfo<Erode, ImageEvent, ImageListener> {

    public ErodeBeanInfo() {
        super(Erode.class, ImageEvent.class, ImageListener.class);
    }
}
