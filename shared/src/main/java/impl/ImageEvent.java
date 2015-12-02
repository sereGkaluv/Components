package impl;

import java.awt.image.BufferedImage;
import java.util.EventObject;

/**
 * Created by f00 on 30.11.15.
 */
public class ImageEvent extends EventObject {

    private final BufferedImage _bufferedImage;

    /**
     * Prototype Event
     *
     * @param source the Event-throwing object
     * @throws IllegalArgumentException if source == null
     */
    public ImageEvent(Object source, BufferedImage bufferedImage) {
        super(source);
        _bufferedImage = bufferedImage;
    }

    public BufferedImage getImage() {
        return _bufferedImage;
    }
}