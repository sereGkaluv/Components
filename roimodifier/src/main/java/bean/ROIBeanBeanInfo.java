package bean;

import impl.AbstractBeanInfo;
import impl.ImageEvent;
import interfaces.ImageListener;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ROIBeanBeanInfo extends AbstractBeanInfo<ROIBean, ImageEvent, ImageListener> {

    public ROIBeanBeanInfo() {
        super(ROIBean.class, ImageEvent.class, ImageListener.class);
    }
}
