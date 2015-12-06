package bean;

import annotations.TargetDescriptor;

import filter.ROIFilter;
import impl.ImageEventHandler;
import impl.ImageEvent;
import interfaces.ImageListener;
import pipes.SupplierPipe;

import java.awt.*;
import java.io.StreamCorruptedException;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public class ROI extends ImageEventHandler implements ImageListener {
    @TargetDescriptor
    private int _x = 0;
    @TargetDescriptor
    private int _y = 0;
    @TargetDescriptor
    private int _width = 0;
    @TargetDescriptor
    private int _height = 0;

    private ImageEvent _lastImageEvent;

    public ROI() {
    }

    public int getX() {
        return _x;
    }

    public void setX(int x) {
        _x = x;
        reload();
    }

    public int getY() {
        return _y;
    }

    public void setY(int y) {
        _y = y;
        reload();
    }

    public int getWidth() {
        return _width;
    }

    public void setWidth(int width) {
        _width = width;
        reload();
    }

    public int getHeight() {
        return _height;
    }

    public void setHeight(int height) {
        _height = height;
        reload();
    }

    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        try {
            _lastImageEvent = imageEvent;

            ROIFilter roiFilter = new ROIFilter(
                new SupplierPipe<>(imageEvent),
                new Rectangle(getX(), getY(), getWidth(), getHeight())
            );

            ImageEvent result = roiFilter.read();

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
