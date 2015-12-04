package impl;

import javax.media.jai.PlanarImage;
import java.awt.image.BufferedImage;
import java.util.EventObject;

/**
 * Created by f00 on 30.11.15.
 */
public class ImageEvent extends EventObject {

    private final PlanarImage _planarImage;

    /**
     * Prototype Event
     *
     * @param source the Event-throwing object
     * @throws IllegalArgumentException if source == null
     */
    public ImageEvent(Object source, PlanarImage planarImage) {
        super(source);
        _planarImage = planarImage;
    }

    public PlanarImage getImage() {
        return _planarImage;
    }
}