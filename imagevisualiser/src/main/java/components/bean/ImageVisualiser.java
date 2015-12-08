package components.bean;

import components.annotations.TargetDescriptor;
import components.impl.ImageEvent;
import components.impl.vetoablehelpers.IntegerVetoable;
import components.interfaces.ImageListener;

import java.awt.*;
import java.beans.*;
import java.io.Serializable;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageVisualiser extends Canvas
implements ImageListener, VetoableChangeListener, PropertyChangeListener, Serializable {
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final int MIN_SIZE_VALUE = 0;
    private static final int DEFAULT_IMAGE_PLACEHOLDER_SIZE = 100;

    private final PropertyChangeSupport _pcs = new PropertyChangeSupport(this);
    private final VetoableChangeSupport _vcs = new VetoableChangeSupport(this);

    @TargetDescriptor
    private int _width = DEFAULT_IMAGE_PLACEHOLDER_SIZE;
    @TargetDescriptor
    private int _height = DEFAULT_IMAGE_PLACEHOLDER_SIZE;

    private transient ImageEvent _lastImageEvent;

    public ImageVisualiser() {
        setSize(getWidth(), getHeight());
    }

    @Override
    public int getWidth() {
        return _width;
    }

    public void setWidth(int width)
    throws PropertyVetoException {
        int temp = _width;
        fireVetoableChange(this, WIDTH, temp, width);

        _width = width;
        firePropertyChange(this, WIDTH, temp, width);
    }

    @Override
    public int getHeight() {
        return _height;
    }

    public void setHeight(int height)
    throws PropertyVetoException {
        int temp = _height;
        fireVetoableChange(this, HEIGHT, temp, height);

        _height = height;
        firePropertyChange(this, HEIGHT, temp, height);
    }

    /* Canvas Overrides */
    @Override
    public void paint(Graphics g) {
        setSize(getWidth(), getHeight());

        if (_lastImageEvent != null && _lastImageEvent.getImage() != null) {
            g.drawImage(_lastImageEvent.getImage().getAsBufferedImage(), 0, 0, this);
        }
    }

    /* ImageListener override */
    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        _lastImageEvent = imageEvent;

        if (imageEvent != null && imageEvent.getImage() != null) {
            repaint();
        }
    }

    protected void reload() {
        if (_lastImageEvent != null) onImageEvent(_lastImageEvent);
    }

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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();

        if (source instanceof ImageVisualiser) {
            ((ImageVisualiser) source).reload();
        }
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        String propertyName = evt.getPropertyName();

        if (propertyName != null) {

            switch (propertyName) {

                case WIDTH: {
                    IntegerVetoable.validate(evt, MIN_SIZE_VALUE);
                    break;
                }

                case HEIGHT: {
                    IntegerVetoable.validate(evt, MIN_SIZE_VALUE);
                    break;
                }
            }
        }
    }
}
