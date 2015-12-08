package components.impl.handlers;

import components.impl.ImageEvent;
import components.interfaces.EventHandler;
import components.interfaces.ImageListener;

import java.beans.PropertyChangeEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public abstract class ImageEventHandler extends AbstractEventHandler
implements EventHandler<ImageListener, ImageEvent> {

    private final List<ImageListener> imageListenerRegistry = new LinkedList<>();

    protected ImageEventHandler() {
        super();
    }

    @Override
    public void addImageListener(ImageListener listener) {
        imageListenerRegistry.add(listener);
    }

    @Override
    public void removeImageListener(ImageListener listener) {
        imageListenerRegistry.remove(listener);
    }

    @Override
    public void notifyAllListeners(ImageEvent imageEvent) {
        if (!imageListenerRegistry.isEmpty()) {
            List<ImageListener> listenerList = new LinkedList<>(imageListenerRegistry);

            for (ImageListener listener : listenerList) {
                listener.onImageEvent(imageEvent);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();

        if (source instanceof ImageEventHandler) {
            ((ImageEventHandler) source).reload();
        }
    }
}
