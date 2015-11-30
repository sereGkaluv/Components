package filter;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public class ClosingFilter extends ForwardingFilter {
    private int radius;

    public ClosingFilter(Readable input, int radius) throws InvalidParameterException {
        super(input);
        this.radius = radius;
    }

    @Override
    public ImageEvent read() throws StreamCorruptedException {
        return process((ImageEvent) readInput());
    }

    private ImageEvent process(ImageEvent imageEvent) {
        ImageEvent result = null;
        PlanarImage planarImage = imageEvent.getFastBitmap();
        Closing closing = new Closing(radius);
        closing.applyInPlace(fastBitmap);
        result = new ImageEvent(this, fastBitmap);
        return result;
    }
}