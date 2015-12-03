package bean;

import annotations.TargetDescriptor;
import filter.MedianFilter;
import impl.ImageEvent;
import impl.ImageEventHandler;
import interfaces.ImageListener;

import java.io.StreamCorruptedException;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public class MedianBean extends ImageEventHandler implements ImageListener {
    @TargetDescriptor
    private int _maskSize = 0;

    private ImageEvent _lastImageEvent;

    public MedianBean() {
    }

    public int getMaskSize() {
        return _maskSize;
    }

    public void setMaskSize(int maskSize) {
        _maskSize = maskSize;
        reload();
    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        try {
            _lastImageEvent = imageEvent;

            MedianFilter medianFilter = new MedianFilter(
                () -> imageEvent,
                _maskSize
            );

            ImageEvent result = medianFilter.read();

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
