package bean;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * Created by f00 on 03.12.15.
 */
public class CombineBeanInfo extends SimpleBeanInfo {

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {

        try {

            PropertyDescriptor pd1 = new PropertyDescriptor(RANDOMPROPERTY, Combine.class);

            return new PropertyDescriptor[]{pd1};

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
