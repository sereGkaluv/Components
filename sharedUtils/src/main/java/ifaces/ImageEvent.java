package ifaces;

import other.Coordinate;

import javax.media.jai.PlanarImage;
import java.util.EventObject;

/**
 * Created by f00 on 30.11.15.
 */
public class ImageEvent extends EventObject {

    private PlanarImage _planarImage;
    private Coordinate coordinate;

    /**
     * a prototype Event
     *
     * @param source the Event-throwing object
     *
     * @throws IllegalArgumentException
     */
    public ImageEvent(Object source, PlanarImage planarImage) {
        super(source);

        this._planarImage = planarImage;
    }

    public PlanarImage getFastBitmap() {
        return _planarImage;
    }

    public void setFastBitmap(PlanarImage planarImage) {
        this._planarImage = planarImage;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}