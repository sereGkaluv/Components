package components.impl.descriptorhelpers;

import components.annotations.TargetDescriptor;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sereGkaluv on 04-Dec-15.
 */
public class PropertyHelper {
    private static final String PROPERTY_PREFIX = "_";

    private PropertyHelper() {
    }

    public static <T> PropertyDescriptor[] getPropertyDescriptors(Class<T> beanClass) {
        try {

            List<Field> allProperties = Arrays.asList(beanClass.getDeclaredFields());
            List<Field> targetProperties = allProperties.stream()
                .filter(property -> property.isAnnotationPresent(TargetDescriptor.class))
                .collect(Collectors.toList());

            List<String> propertyNames = targetProperties.stream()
                .map(property -> {
                    String propertyName = property.getName();
                    if (propertyName.startsWith(PROPERTY_PREFIX) && propertyName.length() > 1) {
                        propertyName = propertyName.substring(1);
                    }
                    return propertyName;
                })
                .collect(Collectors.toList());

            PropertyDescriptor[] descriptors = new PropertyDescriptor[propertyNames.size()];
            int i = 0;

            for (String property : propertyNames) {
                descriptors[i] = new PropertyDescriptor(property, beanClass);
                ++i;
            }
            return descriptors;

        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
