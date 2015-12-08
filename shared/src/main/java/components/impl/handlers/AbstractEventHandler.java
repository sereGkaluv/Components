package components.impl.handlers;

import java.beans.*;
import java.io.Serializable;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public abstract class AbstractEventHandler implements VetoableChangeListener, PropertyChangeListener, Serializable {
    private final PropertyChangeSupport _pcs = new PropertyChangeSupport(this);
    private final VetoableChangeSupport _vcs = new VetoableChangeSupport(this);

    protected AbstractEventHandler() {
        _pcs.addPropertyChangeListener(this);
        _vcs.addVetoableChangeListener(this);
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
