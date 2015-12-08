package components.bean;

import components.impl.AbstractBeanInfo;
import components.impl.ImageEvent;
import components.interfaces.ImageListener;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ROIBeanInfo extends AbstractBeanInfo<ROI, ImageEvent, ImageListener> {

    public ROIBeanInfo() {
        super(ROI.class, ImageEvent.class, ImageListener.class);
    }
}
