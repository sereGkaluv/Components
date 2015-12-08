package components.bean;

import components.annotations.TargetDescriptor;
import components.filter.ROIFilter;
import components.impl.ImageEvent;
import components.impl.handlers.ImageEventHandler;
import components.impl.vetoablehelpers.IntegerVetoable;
import components.interfaces.ImageListener;
import components.pipes.SupplierPipe;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.StreamCorruptedException;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public class ROI extends ImageEventHandler implements ImageListener {
    private static final String X_VALUE = "x";
    private static final String Y_VALUE = "y";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final int MIN_VALUE = 0;

    @TargetDescriptor
    private int _x = MIN_VALUE;
    @TargetDescriptor
    private int _y = MIN_VALUE;
    @TargetDescriptor
    private int _width = MIN_VALUE;
    @TargetDescriptor
    private int _height = MIN_VALUE;

    private transient ImageEvent _lastImageEvent;

    public ROI() {
        super();
    }

    public int getX() {
        return _x;
    }

    public void setX(int x)
    throws PropertyVetoException {
        int temp = _x;
        fireVetoableChange(this, X_VALUE, temp, x);

        _x = x;
        firePropertyChange(this, X_VALUE, temp, x);
    }

    public int getY() {
        return _y;
    }

    public void setY(int y)
    throws PropertyVetoException {
        int temp = _y;
        fireVetoableChange(this, Y_VALUE, temp, y);

        _y = y;
        firePropertyChange(this, Y_VALUE, temp, y);
    }

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

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        try {
            _lastImageEvent = imageEvent;

            ROIFilter roiFilter = new ROIFilter(
                new SupplierPipe<>(imageEvent),
                new Rectangle(getX(), getY(), getWidth(), getHeight())
            );

            ImageEvent result = roiFilter.read();

            notifyAllListeners(result);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            notifyAllListeners(null);
        }
    }

    @Override
    protected void reload() {
        if (_lastImageEvent != null) onImageEvent(_lastImageEvent);
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        String propertyName = evt.getPropertyName();

        if (propertyName != null) {

            switch (propertyName) {

                case X_VALUE: {
                    IntegerVetoable.validate(evt, MIN_VALUE);
                    break;
                }

                case Y_VALUE: {
                    IntegerVetoable.validate(evt, MIN_VALUE);
                    break;
                }

                case WIDTH: {
                    IntegerVetoable.validate(evt, MIN_VALUE);
                    break;
                }

                case HEIGHT: {
                    IntegerVetoable.validate(evt, MIN_VALUE);
                    break;
                }
            }
        }
    }
}
