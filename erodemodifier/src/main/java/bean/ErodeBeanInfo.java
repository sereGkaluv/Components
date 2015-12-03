package bean;

import impl.AbstractBeanInfo;
import impl.ImageEvent;
import interfaces.ImageListener;

/**
 * Created by f00 on 03.12.15.
 */
public class ErodeBeanInfo extends AbstractBeanInfo<ErodeBean, ImageEvent, ImageListener> {

    public ErodeBeanInfo() {
        super(ErodeBean.class, ImageEvent.class, ImageListener.class);
    }
}
