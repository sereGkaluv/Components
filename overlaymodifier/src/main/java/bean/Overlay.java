package bean;

import annotations.TargetDescriptor;
import filter.OverlayFilter;
import impl.ImageEvent;
import impl.ImageEventHandler;
import impl.vetoablehelpers.BooleanVetoable;
import interfaces.ImageListener;
import pipes.SupplierPipe;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.StreamCorruptedException;

/**
 * Created by f00 on 30.11.15.
 */
public class Overlay extends ImageEventHandler implements ImageListener {
    private static final String INVERTED_INPUT = "invertedInput";

    @TargetDescriptor
    private boolean _invertedInput = false;

    private ImageEvent _lastLeftImageEvent;
    private ImageEvent _lastRightImageEvent;

    public Overlay() {
        super();
    }

    public boolean getInvertedInput() {
        return _invertedInput;
    }

    public void setInvertedInput(boolean invertedInput)
    throws PropertyVetoException {
        boolean temp = _invertedInput;
        fireVetoableChange(this, INVERTED_INPUT, temp, invertedInput);

        _invertedInput = invertedInput;
        firePropertyChange(this, INVERTED_INPUT, temp, invertedInput);
    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        try {

            if (isSuitableSource(_lastLeftImageEvent, imageEvent)) {
                _lastLeftImageEvent = imageEvent;
            } else if (isSuitableSource(_lastRightImageEvent, imageEvent)) {
                _lastRightImageEvent = imageEvent;
            }

            if (_lastLeftImageEvent != null && _lastRightImageEvent != null) {

                OverlayFilter overlayFilter;

                if (!getInvertedInput()) {
                    //normal way right image applies on left
                    overlayFilter = new OverlayFilter(
                        new SupplierPipe<>(_lastLeftImageEvent),
                        new SupplierPipe<>(_lastRightImageEvent)
                    );
                } else {
                    //inverted way left image applies on right
                    overlayFilter = new OverlayFilter(
                        new SupplierPipe<>(_lastRightImageEvent),
                        new SupplierPipe<>(_lastLeftImageEvent)
                    );
                }

                ImageEvent result = overlayFilter.read();

                notifyAllListeners(result);
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            notifyAllListeners(null);
        }
    }

    protected void reload() {
        if (_lastLeftImageEvent != null) onImageEvent(_lastLeftImageEvent);
        if (_lastRightImageEvent != null) onImageEvent(_lastRightImageEvent);
    }

    private boolean isSuitableSource(ImageEvent newEvent, ImageEvent oldEvent) {
        return oldEvent == null || (newEvent != null && oldEvent.getSource() == newEvent.getSource());
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        String propertyName = evt.getPropertyName();

        if (propertyName != null) {

            switch (propertyName) {

                case INVERTED_INPUT: {
                    BooleanVetoable.validate(evt);
                }
            }
        }
    }
}
