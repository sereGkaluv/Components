package components.bean;

import components.impl.descriptorhelpers.PropertyHelper;

import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class ImageLoaderBeanInfo extends SimpleBeanInfo {

    public ImageLoaderBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return PropertyHelper.getPropertyDescriptors(ImageLoader.class);
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return new MethodDescriptor[]{};
    }
}
