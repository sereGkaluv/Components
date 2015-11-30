import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Created by f00 on 23.11.15.
 */
public class ROIFilterBeanInfo extends SimpleBeanInfo {

    public PropertyDescriptor[] getPropertyDescriptors() {
        try {

            PropertyDescriptor pd1;
            pd1 = new PropertyDescriptor("interval", ROIFilter.class);
            PropertyDescriptor pds[] = {pd1};
            return pds;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public EventSetDescriptor[] getEventSetDescriptors() {
        EventSetDescriptor esds[] = {};
        return esds;
    }

    public MethodDescriptor[] getMethodDescriptors() {
        MethodDescriptor mds[] = {};
        return mds;
    }
}