/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public class ROI {
    private final int _x;
    private final int _y;
    private final int _width;
    private final int _height;

    public ROI(int x, int y, int width, int height) {
        _x = x;
        _y = y;
        _width = width;
        _height = height;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }
}
