package bean;

import impl.DescriptorHelpers.PropertyHelper;

import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageLoaderBeanInfo extends SimpleBeanInfo {
    private static final String PROP_IMAGE_PATH = "imagePath";

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return PropertyHelper.getPropertyDescriptors(ImageLoader.class);
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
