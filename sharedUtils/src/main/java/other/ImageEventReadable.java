package other;

import java.io.StreamCorruptedException;

/**
 * Created by f00 on 30.11.15.
 */
public class ImageEventReadable<ImageEvent> implements interfaces.Readable {
    ImageEvent imageEvent = null;

    public ImageEventReadable(ImageEvent imageEvent) {
        this.imageEvent = imageEvent;
    }

    @Override
    public ImageEvent read() throws StreamCorruptedException {
        return imageEvent;
    }
}