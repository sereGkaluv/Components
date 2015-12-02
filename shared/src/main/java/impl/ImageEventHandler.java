package impl;

import interfaces.EventHandler;
import interfaces.ImageListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public abstract class ImageEventHandler implements EventHandler<ImageListener, ImageEvent> {
    private static final Set<ImageListener> IMAGE_LISTENER_REGISTRY = new HashSet<>();

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
        IMAGE_LISTENER_REGISTRY.forEach(listener -> listener.onImageEvent(imageEvent));
    }
}
