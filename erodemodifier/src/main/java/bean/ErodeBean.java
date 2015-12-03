package bean;

import annotations.TargetDescriptor;
import filter.ErodeFilter;
import impl.ImageEvent;
import impl.ImageEventHandler;
import interfaces.ImageListener;
import util.Kernel;

import java.io.StreamCorruptedException;


/**
 * Created by f00 on 03.12.15.
 */
public class ErodeBean extends ImageEventHandler implements ImageListener {

    @TargetDescriptor
    private Kernel _kernel = new Kernel();

    private ImageEvent _lastImageEvent;

    /* empty constructor */
    public ErodeBean() {

    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        try {

            _lastImageEvent = imageEvent;

            ErodeFilter erodeFilter = new ErodeFilter(
                    () -> imageEvent,
                    _kernel
            );

            ImageEvent result = erodeFilter.read();

            notifyAllListeners(result);

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            notifyAllListeners(null);
        }
    }
}
