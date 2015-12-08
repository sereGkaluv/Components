package components.interfaces;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Created by f00 on 30.11.15.
 */
public interface EventHandler<T extends EventListener, U extends EventObject> {
    void addImageListener(T listener);

    void removeImageListener(T listener);

    void notifyAllListeners(U event);
}