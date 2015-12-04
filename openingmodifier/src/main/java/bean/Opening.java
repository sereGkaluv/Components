package bean;

import annotations.TargetDescriptor;
import filter.OpeningFilter;
import impl.ImageEvent;
import impl.ImageEventHandler;
import util.Kernel;
import interfaces.ImageListener;

import java.io.StreamCorruptedException;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public class Opening extends ImageEventHandler implements ImageListener {

    @TargetDescriptor
    private Kernel _kernel = new Kernel();

    private ImageEvent _lastImageEvent;

    public Opening() {
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

            OpeningFilter openingFilter = new OpeningFilter(
                () -> imageEvent,
                _kernel
            );

            ImageEvent result = openingFilter.read();

            notifyAllListeners(result);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            notifyAllListeners(null);
        }
    }

    private void reload() {
        if (_lastImageEvent != null) onImageEvent(_lastImageEvent);
    }
}
