package ifaces;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by f00 on 30.11.15.
 */
public class EventPoolImage {

    private List<ImageListener> imageListeners;

    public EventPoolImage() {
        imageListeners = new LinkedList<>();
    }

    public void addImageEventHandler(ImageListener imageListener) {
        imageListeners.add(imageListener);
    }

    public void removeImageEventHandler(ImageListener imageListener) {
        imageListeners.remove(imageListener);
    }

    public boolean notify(ImageListener imageEventHandler, ImageEvent imageEvent) {

        ImageListener imageEventHandlertemp = imageListeners.get(imageListeners.indexOf(imageEventHandler));

        if (imageEventHandlertemp != null) {
            imageEventHandlertemp.onImage(imageEvent);
            return true;
        }
        return false;
    }

    public void notifyAll(ImageEvent imageEvent) {

        ImageEvent imageEvent1 = imageEvent;

        for (ImageListener imageListener : imageListeners) {
            imageListener.onImage(imageEvent1);
        }
    }
}