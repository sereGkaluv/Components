package bean;

import annotations.TargetDescriptor;
import impl.ImageEvent;
import interfaces.EventHandler;
import interfaces.ImageListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageVisualiser extends Canvas implements ImageListener {
    private static final int DEFAULT_IMAGE_PLACEHOLDER_SIZE = 100;

    @TargetDescriptor
    private int _width = DEFAULT_IMAGE_PLACEHOLDER_SIZE;
    @TargetDescriptor
    private int _height = DEFAULT_IMAGE_PLACEHOLDER_SIZE;

    private transient BufferedImage _bufferedImage;

    public ImageVisualiser() {
        setSize(_width, _height);
    }

    @Override
    public int getWidth() {
        return _width;
    }

    public void setWidth(int width) {
        _width = width;
    }

    @Override
    public int getHeight() {
        return _height;
    }

    public void setHeight(int height) {
        _height = height;
    }

    /* Canvas Overrides */
    @Override
    public void paint(Graphics g) {
        setSize(_width, _height);
        g.drawImage(_bufferedImage, 0, 0, this);
    }

    /* ImageListener override */
    @Override
    @TargetDescriptor
    public void onImageEvent(ImageEvent imageEvent) {
        _bufferedImage = imageEvent.getImage().getAsBufferedImage();
        repaint();
    }
}
