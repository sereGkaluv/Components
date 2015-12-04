package bean;


import impl.AbstractBeanInfo;
import impl.ImageEvent;
import interfaces.ImageListener;

/**
 * Created by f00 on 03.12.15.
 */
public class DilateBeanInfo extends AbstractBeanInfo<Dilate, ImageEvent, ImageListener> {

    public DilateBeanInfo() {
        super(Dilate.class, ImageEvent.class, ImageListener.class);
    }
}
