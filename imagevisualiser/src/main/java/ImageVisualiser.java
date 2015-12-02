import impl.ImageEvent;
import interfaces.ImageListener;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageVisualiser extends Canvas implements ImageListener {
    private static final int DEFAULT_IMAGE_PLACEHOLDER_SIZE = 100;
    private int _width = DEFAULT_IMAGE_PLACEHOLDER_SIZE;
    private int _height = DEFAULT_IMAGE_PLACEHOLDER_SIZE;

    private transient BufferedImage _bufferedImage;

    public ImageVisualiser() {
        setSize(_width, _height);
    }

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

    @Override
    public void paint(Graphics g) {
        setSize(_width, _height);
        g.drawImage(_bufferedImage, 0, 0, this);
    }

    @Override
    public void onImageEvent(ImageEvent imageEvent) {
        _bufferedImage = imageEvent.getImage();
        repaint();
    }
}
