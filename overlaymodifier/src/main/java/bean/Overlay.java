package bean;

import annotations.TargetDescriptor;
import filter.OverlayFilter;
import impl.ImageEvent;
import impl.ImageEventHandler;
import interfaces.ImageListener;

import java.io.StreamCorruptedException;

/**
 * Created by f00 on 30.11.15.
 */
public class Overlay extends ImageEventHandler implements ImageListener {

    private ImageEvent _lastLeftImageEvent;
    private ImageEvent _lastRightImageEvent;

    public Overlay() {
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

                OverlayFilter overlayFilter = new OverlayFilter(
                    () -> _lastLeftImageEvent,
                    () -> _lastRightImageEvent
                );

                ImageEvent result = overlayFilter.read();

                notifyAllListeners(result);
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            notifyAllListeners(null);
        }
    }

    private void reload() {
        if (_lastLeftImageEvent != null) onImageEvent(_lastLeftImageEvent);
        if (_lastRightImageEvent != null) onImageEvent(_lastRightImageEvent);
    }

    private boolean isSuitableSource(ImageEvent newEvent, ImageEvent oldEvent) {
        return oldEvent == null || (newEvent != null && oldEvent.getSource() == newEvent.getSource());
    }
}
