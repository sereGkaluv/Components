package components.interfaces;

import components.impl.ImageEvent;

import java.util.EventListener;

/**
 * Created by f00 on 30.11.15.
 */
public interface ImageListener extends EventListener {
    void onImageEvent(ImageEvent imageEvent);
}


