package bean;

import annotations.TargetDescriptor;
import filter.OpeningFilter;
import impl.ImageEvent;
import impl.ImageEventHandler;
import pipes.SupplierPipe;
import util.Kernel;
import interfaces.ImageListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.StreamCorruptedException;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public class Opening extends ImageEventHandler implements ImageListener {

    private Kernel _kernel = new Kernel();

    private ImageEvent _lastImageEvent;

    public Opening() {
        super();
    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        try {
            _lastImageEvent = imageEvent;

            OpeningFilter openingFilter = new OpeningFilter(
                new SupplierPipe<>(imageEvent),
                _kernel
            );

            ImageEvent result = openingFilter.read();

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
    }
}
