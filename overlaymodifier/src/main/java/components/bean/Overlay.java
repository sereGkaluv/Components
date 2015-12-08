package components.bean;

import components.annotations.TargetDescriptor;
import components.filter.OverlayFilter;
import components.impl.ImageEvent;
import components.impl.handlers.ImageEventHandler;
import components.impl.vetoablehelpers.BooleanVetoable;
import components.interfaces.DualImageListener;
import components.pipes.SupplierPipe;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.StreamCorruptedException;

/**
 * Created by f00 on 30.11.15.
 */
public class Overlay extends ImageEventHandler implements DualImageListener {
    private static final String INVERTED_INPUT = "invertedInput";

    @TargetDescriptor
    private boolean _invertedInput = false;

    private transient ImageEvent _lastLeftImageEvent;
    private transient ImageEvent _lastRightImageEvent;

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
    public void onLeftImageEvent(ImageEvent imageEvent) {
        _lastLeftImageEvent = imageEvent;

        ImageEvent result = onImageEvent(imageEvent, _lastRightImageEvent);
        if (result != null) notifyAllListeners(result);
    }

    @Override
    @TargetDescriptor
    public void onRightImageEvent(ImageEvent imageEvent) {
        _lastRightImageEvent = imageEvent;

        ImageEvent result = onImageEvent(_lastLeftImageEvent, imageEvent);
        if (result != null) notifyAllListeners(result);
    }

    private ImageEvent onImageEvent(ImageEvent leftImageEvent, ImageEvent rightImageEvent) {
        try{

            OverlayFilter overlayFilter;

            if (leftImageEvent != null && rightImageEvent != null) {
                if (!getInvertedInput()) {
                    //normal way right image applies on left
                    overlayFilter = new OverlayFilter(
                        new SupplierPipe<>(leftImageEvent),
                        new SupplierPipe<>(rightImageEvent)
                    );
                } else {
                    //inverted way left image applies on right
                    overlayFilter = new OverlayFilter(
                        new SupplierPipe<>(leftImageEvent),
                        new SupplierPipe<>(rightImageEvent)
                    );
                }

                return overlayFilter.read();
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void reload() {
        if (_lastLeftImageEvent != null && _lastRightImageEvent != null) {

            ImageEvent result = onImageEvent(_lastLeftImageEvent, _lastRightImageEvent);
            if (result != null) notifyAllListeners(result);
        }
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
