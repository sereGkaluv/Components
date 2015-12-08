package components.bean;

import components.impl.descriptorhelpers.MethodHelper;
import components.impl.descriptorhelpers.PropertyHelper;

import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Created by f00 on 03.12.15.
 */
public class OverlayBeanInfo extends SimpleBeanInfo {

    public OverlayBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return PropertyHelper.getPropertyDescriptors(Overlay.class);
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return MethodHelper.getMethodDescriptors(Overlay.class);
    }
}
