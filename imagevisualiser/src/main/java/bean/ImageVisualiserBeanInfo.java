package bean;

import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageVisualiserBeanInfo extends SimpleBeanInfo {
    private static final String PROP_IMAGE_WIDTH = "imageWidth";
    private static final String PROP_IMAGE_HEIGHT = "imageHeight";

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor pd1 = new PropertyDescriptor(PROP_IMAGE_WIDTH, ImageVisualiser.class);
            PropertyDescriptor pd2 = new PropertyDescriptor(PROP_IMAGE_HEIGHT, ImageVisualiser.class);

            return new PropertyDescriptor[]{pd1, pd2};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return new EventSetDescriptor[]{};
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return new MethodDescriptor[]{};
    }
}
