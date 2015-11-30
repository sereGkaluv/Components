package bean;


import filter.ClosingFilter;
import ifaces.EventHandlerImageImpl;
import ifaces.ImageEvent;
import ifaces.ImageListener;
import other.ImageEventReadable;

import java.io.StreamCorruptedException;

/**
 * Created by f00 on 30.11.15.
 */
public class ClosingBean extends EventHandlerImageImpl implements ImageListener {
    private ClosingFilter closingFilter = null;
    private int radius = 0;
    private ImageEvent input = null;

    public ClosingBean() {
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        if (input != null) {
            onImage(input);
        }
    }

    @Override
    public void onImage(ImageEvent e) {
        closingFilter = new ClosingFilter(new ImageEventReadable<ImageEvent>(e), radius);
        input = e;
        ImageEvent result = null;

        try {

            result = closingFilter.read();

        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        }
        notifyAllListener(result);
    }
}
}
