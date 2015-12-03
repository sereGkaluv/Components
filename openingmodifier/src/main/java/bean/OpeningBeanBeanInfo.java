package bean;

import impl.AbstractBeanInfo;
import impl.ImageEvent;
import interfaces.ImageListener;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class OpeningBeanBeanInfo extends AbstractBeanInfo<OpeningBean, ImageEvent, ImageListener> {

    public OpeningBeanBeanInfo() {
        super(OpeningBean.class, ImageEvent.class, ImageListener.class);
    }
}
