package bean;

import impl.DescriptorHelpers.MethodHelper;
import impl.DescriptorHelpers.PropertyHelper;
import interfaces.ImageListener;

import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageVisualiserBeanInfo extends SimpleBeanInfo {

    public ImageVisualiserBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return PropertyHelper.getPropertyDescriptors(ImageListener.class);
    }

    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return new EventSetDescriptor[]{};
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return MethodHelper.getMethodDescriptors(ImageListener.class);
    }
}
