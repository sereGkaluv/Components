package impl.descriptorhelpers;

import annotations.TargetDescriptor;

import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

/**
 * Created by sereGkaluv on 04-Dec-15.
 */
public class EventHelper {
    private static final String ADD_PREFIX = "add";
    private static final String REMOVE_PREFIX = "remove";
    private static final String LISTENER_SUFFIX = "Listener";

    private EventHelper() {
    }

    public static <T, U extends EventObject, V extends EventListener> EventSetDescriptor[] getEventSetDescriptors(
        Class<T> beanClass,
        Class<U> eventClass,
        Class<V> listenerClass
    ) {

        try {

            List<Method> allMethods = Arrays.asList(beanClass.getDeclaredMethods());
            Method[] targetMethods = allMethods.stream()
                    .filter(method -> method.isAnnotationPresent(TargetDescriptor.class))
                    .toArray(Method[]::new);

            EventSetDescriptor descriptor = new EventSetDescriptor(
                getEventSetName(listenerClass),
                eventClass,
                targetMethods,
                beanClass.getMethod(ADD_PREFIX + listenerClass.getSimpleName(), listenerClass),
                beanClass.getMethod(REMOVE_PREFIX + listenerClass.getSimpleName(), listenerClass)
            );

            return new EventSetDescriptor[]{descriptor};

        } catch (IntrospectionException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static <V extends EventListener> String getEventSetName(Class<V> listenerClass) {
        String listenerName =  listenerClass.getSimpleName();

        if (!listenerName.endsWith(LISTENER_SUFFIX)) throw new IllegalStateException();
        return listenerName.substring(0, listenerName.length() - LISTENER_SUFFIX.length());
    }
}
