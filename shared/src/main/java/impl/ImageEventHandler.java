package impl;

import interfaces.EventHandler;
import interfaces.ImageListener;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public abstract class ImageEventHandler implements EventHandler<ImageListener, ImageEvent> {
    private final List<ImageListener> IMAGE_LISTENER_REGISTRY = new LinkedList<>();

    protected ImageEventHandler() {
    }

    @Override
    public void addImageListener(ImageListener listener) {
        IMAGE_LISTENER_REGISTRY.add(listener);
    }

    @Override
    public void removeImageListener(ImageListener listener) {
        IMAGE_LISTENER_REGISTRY.remove(listener);
    }

    @Override
    public void notifyAllListeners(ImageEvent imageEvent) {
        if (!IMAGE_LISTENER_REGISTRY.isEmpty()) {
            List<ImageListener> listenerList = new LinkedList<>(IMAGE_LISTENER_REGISTRY);

            for (ImageListener listener : listenerList) {
                listener.onImageEvent(imageEvent);
            }
        }
    }
}
