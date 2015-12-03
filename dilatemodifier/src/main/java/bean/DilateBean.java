package bean;

import annotations.TargetDescriptor;
import filer.DilateFilter;
import impl.ImageEvent;
import impl.ImageEventHandler;
import interfaces.ImageListener;
import util.Kernel;

import java.io.StreamCorruptedException;

/**
 * Created by f00 on 03.12.15.
 */
public class DilateBean extends ImageEventHandler implements ImageListener {

    @TargetDescriptor
    private Kernel _kernel = new Kernel();

    private ImageEvent _lastImageEvent;

    /* empty constructor */
    public DilateBean() {

    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        try {

            _lastImageEvent = imageEvent;

            DilateFilter dilateFilter = new DilateFilter(
                    () -> imageEvent,
                    _kernel
            );

            ImageEvent result = dilateFilter.read();

            notifyAllListeners(result);

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            notifyAllListeners(null);
        }
    }
}


