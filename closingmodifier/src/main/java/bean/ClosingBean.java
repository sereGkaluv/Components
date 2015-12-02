package bean;


import filter.ClosingFilter;
import impl.ImageEvent;
import impl.ImageEventHandler;
import interfaces.ImageListener;

import java.io.StreamCorruptedException;

/**
 * Created by f00 on 30.11.15.
 */
public class ClosingBean extends ImageEventHandler implements ImageListener {
    //TODO annotations (@TargetDescriptor)

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
            onImageEvent(input);
        }
    }

    @Override
    public void onImageEvent(ImageEvent imageEvent) {
        closingFilter = new ClosingFilter(() -> imageEvent, radius);
        input = imageEvent;
        ImageEvent result = null;

        try {

            result = closingFilter.read();

        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        }
        notifyAllListeners(result);
    }
}
