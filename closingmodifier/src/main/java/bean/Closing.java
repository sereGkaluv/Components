package bean;


import annotations.TargetDescriptor;
import filter.ClosingFilter;
import impl.ImageEvent;
import impl.ImageEventHandler;
import interfaces.ImageListener;
import pipes.SupplierPipe;
import util.Kernel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.StreamCorruptedException;

/**
 * Created by f00 on 30.11.15.
 */
public class Closing extends ImageEventHandler implements ImageListener {
    @TargetDescriptor
    private Kernel _kernel = new Kernel();

    private ImageEvent _lastImageEvent;

    public Closing() {
    }

    public Kernel getKernel() {
        return _kernel;
    }

    public void setKernel(Kernel kernel) {
        _kernel = kernel;
        reload();
    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        try {
            _lastImageEvent = imageEvent;

            ClosingFilter closingFilter = new ClosingFilter(
                new SupplierPipe<>(imageEvent),
                _kernel
            );

            ImageEvent result = closingFilter.read();

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
        //TODO
    }
}
