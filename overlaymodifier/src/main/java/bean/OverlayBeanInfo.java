package bean;

import impl.AbstractBeanInfo;
import impl.ImageEvent;
import interfaces.ImageListener;

/**
 * Created by f00 on 03.12.15.
 */
public class OverlayBeanInfo extends AbstractBeanInfo<Overlay, ImageEvent, ImageListener> {

    public OverlayBeanInfo() {
        super(Overlay.class, ImageEvent.class, ImageListener.class);
    }
}
