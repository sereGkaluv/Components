package components.impl;

import javax.media.jai.PlanarImage;
import java.util.EventObject;

/**
 * Created by f00 on 30.11.15.
 */
public class ImageEvent extends EventObject {

    private final PlanarImage _planarImage;
    private final int _shiftX;
    private final int _shiftY;

    /**
     * Prototype Event
     *
     * @param source the Event-throwing object
     * @param shiftX used to store shift for x
     * @param shiftY used to store shift for y
     * @throws IllegalArgumentException if source == null
     */
    public ImageEvent(Object source, PlanarImage planarImage, int shiftX, int shiftY) {
        super(source);
        _planarImage = planarImage;
        _shiftX = shiftX;
        _shiftY = shiftY;
    }

    public PlanarImage getImage() {
        return _planarImage;
    }

    public int getShiftX() {
        return _shiftX;
    }

    public int getShiftY() {
        return _shiftY;
    }
}