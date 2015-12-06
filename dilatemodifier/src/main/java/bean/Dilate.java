package bean;

import annotations.TargetDescriptor;
import filter.DilateFilter;
import impl.ImageEvent;
import impl.ImageEventHandler;
import interfaces.ImageListener;
import pipes.SupplierPipe;
import util.Kernel;

import java.io.StreamCorruptedException;

/**
 * Created by f00 on 03.12.15.
 */
public class Dilate extends ImageEventHandler implements ImageListener {
    @TargetDescriptor
    private Kernel _kernel = new Kernel();

    private ImageEvent _lastImageEvent;

    public Dilate() {
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

            DilateFilter dilateFilter = new DilateFilter(
                new SupplierPipe<>(imageEvent),
                _kernel
            );

            ImageEvent result = dilateFilter.read();

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


