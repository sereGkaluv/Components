package ifaces;

/**
 * Created by f00 on 30.11.15.
 */
public class EventHandlerImageImpl implements EventHandlerImage {

    private EventPoolImage imageEventPool = new EventPoolImage();

    public EventHandlerImageImpl() {
    }

    @Override
    public void addImageListener(ImageListener listener) {
        imageEventPool.addImageEventHandler(listener);
    }

    @Override
    public void removeImageListener(ImageListener listener) {
        imageEventPool.removeImageEventHandler(listener);
    }

    public void notifyAllListener(ImageEvent imageEvent) {
        imageEventPool.notifyAll(imageEvent);
    }
}

