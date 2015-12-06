package bean;

import annotations.TargetDescriptor;
import filter.MedianFilter;
import impl.ImageEvent;
import impl.ImageEventHandler;
import impl.vetoablehelpers.IntegerVetoable;
import interfaces.ImageListener;
import pipes.SupplierPipe;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.StreamCorruptedException;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public class Median extends ImageEventHandler implements ImageListener {
    private static final String MASK_SIZE = "maskSize";
    private static final int MIN_VALUE = 1;

    @TargetDescriptor
    private int _maskSize = MIN_VALUE;

    private ImageEvent _lastImageEvent;

    public Median() {
        super();
    }

    public int getMaskSize() {
        return _maskSize;
    }

    public void setMaskSize(int maskSize)
    throws PropertyVetoException {
        int temp = _maskSize;
        fireVetoableChange(this, MASK_SIZE, temp, maskSize);

        _maskSize = maskSize;
        firePropertyChange(this, MASK_SIZE, temp, maskSize);
    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        try {
            _lastImageEvent = imageEvent;

            MedianFilter medianFilter = new MedianFilter(
                new SupplierPipe<>(imageEvent),
                _maskSize
            );

            ImageEvent result = medianFilter.read();

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

                case MASK_SIZE: {
                    IntegerVetoable.validate(evt, MIN_VALUE);
                    break;
                }
            }
        }
    }
}
