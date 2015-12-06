package bean;

import impl.DescriptorHelpers.EventHelper;
import impl.DescriptorHelpers.MethodHelper;
import impl.DescriptorHelpers.PropertyHelper;
import impl.ImageEvent;
import interfaces.ImageListener;

import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Created by sereGkaluv on 23-Nov-15.
 */
public class CalcCentroidsBeanInfo extends SimpleBeanInfo {

    public CalcCentroidsBeanInfo() {
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return PropertyHelper.getPropertyDescriptors(CalcCentroids.class);
    }

    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return new EventSetDescriptor[]{};
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return MethodHelper.getMethodDescriptors(CalcCentroids.class);
    }
}
