import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public class Test {

    @org.junit.Test
    public void TestMethods() {
        ImageSaverBeanInfo saverBeanInfo = new ImageSaverBeanInfo();
        EventSetDescriptor[] events = saverBeanInfo.getEventSetDescriptors();
        MethodDescriptor[] methods = saverBeanInfo.getMethodDescriptors();
        PropertyDescriptor[] properties = saverBeanInfo.getPropertyDescriptors();

        System.out.println("events:");
        for(EventSetDescriptor e : events) {
            System.out.println(e.getDisplayName());
        }

        System.out.println("methods:");
        for(MethodDescriptor e : methods) {
            System.out.println(e.getDisplayName());
        }

        System.out.println("properties:");
        for(PropertyDescriptor e : properties) {
            System.out.println(e.getDisplayName());
        }

        System.out.println("done.");
    }
}
