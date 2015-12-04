package bean;

import annotations.TargetDescriptor;
import filter.ThresholdFilter;
import impl.ImageEvent;
import impl.ImageEventHandler;
import interfaces.ImageListener;

import java.io.StreamCorruptedException;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public class Threshold extends ImageEventHandler implements ImageListener {
    @TargetDescriptor
    private int _colorFrom = 0;
    @TargetDescriptor
    private int _colorTo = 0;
    @TargetDescriptor
    private int _targetColor = 0;

    private ImageEvent _lastImageEvent;

    public Threshold() {
    }

    public int getColorFrom() {
        return _colorFrom;
    }

    public void setColorFrom(int colorFrom) {
        _colorFrom = colorFrom;
        reload();
    }

    public int getColorTo() {
        return _colorTo;
    }

    public void setColorTo(int colorTo) {
        _colorTo = colorTo;
        reload();
    }

    public int getTargetColor() {
        return _targetColor;
    }

    public void setTargetColor(int targetColor) {
        _targetColor = targetColor;
        reload();
    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        try {
            _lastImageEvent = imageEvent;

            ThresholdFilter thresholdFilter = new ThresholdFilter(
                () -> imageEvent,
                new double[]{_colorFrom},
                new double[]{_colorTo},
                new double[]{_targetColor}
            );

            ImageEvent result = thresholdFilter.read();

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
