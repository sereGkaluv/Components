package components.bean;

import components.impl.ImageEvent;
import components.impl.descriptorhelpers.EventHelper;
import components.impl.descriptorhelpers.MethodHelper;
import components.impl.descriptorhelpers.PropertyHelper;
import components.interfaces.DualImageListener;
import components.interfaces.ImageListener;

import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Created by sereGkaluv on 07-Dec-15.
 */
public class DualListenerAdapterBeanInfo extends SimpleBeanInfo {

    public DualListenerAdapterBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return PropertyHelper.getPropertyDescriptors(DualListenerAdapter.class);
    }

    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return EventHelper.getEventSetDescriptors(DualListenerAdapter.class, ImageEvent.class, DualImageListener.class);
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return MethodHelper.getMethodDescriptors(DualListenerAdapter.class);
    }
}
