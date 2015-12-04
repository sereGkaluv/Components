package bean;

import impl.ImageEvent;
import interfaces.EventHandler;
import interfaces.ImageListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sereGkaluv on 23-Nov-15.
 * TODO
 */
public class CalcCentroids extends TextArea implements ImageListener, EventHandler<ImageListener, ImageEvent> {
    private static final Set<ImageListener> IMAGE_LISTENER_REGISTRY = new HashSet<>();

    private static final int DEFAULT_IMAGE_PLACEHOLDER_SIZE = 100;
    private int _width = DEFAULT_IMAGE_PLACEHOLDER_SIZE;
    private int _height = DEFAULT_IMAGE_PLACEHOLDER_SIZE;

    private transient BufferedImage _bufferedImage;

    public CalcCentroids() {
        setSize(_width, _height);
    }

    /* ImageListener Overrides */
    @Override
    public void addImageListener(ImageListener listener) {
        IMAGE_LISTENER_REGISTRY.add(listener);
    }

    @Override
    public void removeImageListener(ImageListener listener) {
        IMAGE_LISTENER_REGISTRY.remove(listener);
    }

    @Override
    public void notifyAllListeners(ImageEvent imageEvent) {
        IMAGE_LISTENER_REGISTRY.forEach(listener -> listener.onImageEvent(imageEvent));
    }

    /* Canvas Overrides */
    @Override
    public void paint(Graphics g) {
        setSize(_width, _height);
        g.drawImage(_bufferedImage, 0, 0, this);
    }

    /* EventListener override */
    @Override
    public void onImageEvent(ImageEvent imageEvent) {
        _bufferedImage = imageEvent.getImage().getAsBufferedImage();
        repaint();
    }


    /* getter / setter */
    public int getImageWidth() {
        return _width;
    }

    public void setImageWidth(int width) {
        _width = width;
    }

    public int getImageHeight() {
        return _height;
    }

    public void setImageHeight(int height) {
        _height = height;
    }
}
