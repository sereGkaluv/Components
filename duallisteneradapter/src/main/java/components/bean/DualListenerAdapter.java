package components.bean;

import components.annotations.TargetDescriptor;
import components.impl.ImageEvent;
import components.interfaces.DualImageListener;
import components.interfaces.EventHandler;
import components.interfaces.ImageListener;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sereGkaluv on 07-Dec-15.
 */
public class DualListenerAdapter
implements EventHandler<DualImageListener, ImageEvent>, ImageListener, Serializable {
    private final List<DualImageListener> imageListenerRegistry = new LinkedList<>();

    @TargetDescriptor
    private Type _adaptorType = Type.Both;

    public DualListenerAdapter() {
    }

    public Type getAdaptorType() {
        return _adaptorType;
    }

    public void setAdaptorType(Type adaptorType) {
        _adaptorType = adaptorType;
    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        notifyAllListeners(imageEvent);
    }

    @Override
    public void addImageListener(DualImageListener listener) {
        imageListenerRegistry.add(listener);
    }

    @Override
    public void removeImageListener(DualImageListener listener) {
        imageListenerRegistry.remove(listener);
    }

    @Override
    public void notifyAllListeners(ImageEvent imageEvent) {
        if (!imageListenerRegistry.isEmpty()) {
            List<DualImageListener> listenerList = new LinkedList<>(imageListenerRegistry);

            for (DualImageListener listener : listenerList) {
                //Send event depending on adapter type
                switch (_adaptorType) {

                    case Left: {
                        listener.onLeftImageEvent(imageEvent);

                        break;
                    }

                    case Right: {
                        listener.onRightImageEvent(imageEvent);

                        break;
                    }

                    case Both: {
                        listener.onLeftImageEvent(imageEvent);
                        listener.onRightImageEvent(imageEvent);

                        break;
                    }
                }
            }
        }
    }

    public enum Type {
        Left, Right, Both
    }
}