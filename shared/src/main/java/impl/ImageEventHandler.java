package impl;

import interfaces.EventHandler;
import interfaces.ImageListener;

import java.beans.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public abstract class ImageEventHandler
implements EventHandler<ImageListener, ImageEvent>, VetoableChangeListener, PropertyChangeListener {

    private final List<ImageListener> imageListenerRegistry = new LinkedList<>();
    private final PropertyChangeSupport _pcs = new PropertyChangeSupport(this);
    private final VetoableChangeSupport _vcs = new VetoableChangeSupport(this);

    protected ImageEventHandler() {
        _pcs.addPropertyChangeListener(this);
        _vcs.addVetoableChangeListener(this);
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

    protected abstract void reload();

    protected void firePropertyChange(
        Object source,
        String propertyName,
        Object oldValue,
        Object newValue
    ) {

        _pcs.firePropertyChange(
            new PropertyChangeEvent(source, propertyName, oldValue, newValue)
        );
    }

    protected void addPropertyChangeListener(PropertyChangeListener pcl) {
        _pcs.addPropertyChangeListener(pcl);
    }

    protected void removePropertyChangeListener(PropertyChangeListener pcl) {
        _pcs.removePropertyChangeListener(pcl);
    }

    protected void fireVetoableChange(
        Object source,
        String propertyName,
        Object oldValue,
        Object newValue
    ) throws PropertyVetoException {

        _vcs.fireVetoableChange(
            new PropertyChangeEvent(source, propertyName, oldValue, newValue)
        );
    }

    protected void addVetoableChangeListener(VetoableChangeListener vcl) {
        _vcs.addVetoableChangeListener(vcl);
    }

    protected void removeVetoableChangeListener(VetoableChangeListener vcl) {
        _vcs.removeVetoableChangeListener(vcl);
    }
}
