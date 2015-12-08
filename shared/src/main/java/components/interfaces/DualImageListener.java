package components.interfaces;

import components.impl.ImageEvent;

import java.util.EventListener;

/**
 * Created by sereGkaluv on 07-Dec-15.
 */
public interface DualImageListener extends EventListener {
    void onLeftImageEvent(ImageEvent imageEvent);

    void onRightImageEvent(ImageEvent imageEvent);
}


