package impl;

import annotations.TargetDescriptor;

import java.beans.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sereGkaluv on 02-Dec-15.
 */
public class AbstractBeanInfo<T extends V, U extends EventObject, V extends EventListener> extends SimpleBeanInfo {
    private static final String PROPERTY_PREFIX = "_";
    private static final String ADD_PREFIX = "add";
    private static final String REMOVE_PREFIX = "remove";
    private static final String LISTENER_SUFFIX = "Listener";

    private final String _eventSetName;
    private final Class<T> _beanClass;
    private final Class<U> _eventClass;
    private final Class<V> _listenerClass;

    protected AbstractBeanInfo(Class<T> beanClass, Class<U> eventClass, Class<V> listenerClass){
        super();
        _beanClass = beanClass;
        _eventClass = eventClass;
        _listenerClass = listenerClass;

        String listenerName =  listenerClass.getSimpleName();

        if (!listenerName.endsWith(LISTENER_SUFFIX)) throw new IllegalStateException();
        _eventSetName = listenerName.substring(0, listenerName.length() - LISTENER_SUFFIX.length());
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {

            List<Field> allProperties = Arrays.asList(_beanClass.getDeclaredFields());
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
                descriptors[i] = new PropertyDescriptor(property, _beanClass);
                ++i;
            }
            return descriptors;

        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        try {

            List<Method> allMethods = Arrays.asList(_beanClass.getDeclaredMethods());
            Method[] targetMethods = allMethods.stream()
                    .filter(method -> method.isAnnotationPresent(TargetDescriptor.class))
                    .toArray(Method[]::new);

            EventSetDescriptor descriptor = new EventSetDescriptor(
                _eventSetName,
                _eventClass,
                targetMethods,
                _beanClass.getMethod(ADD_PREFIX + _listenerClass.getSimpleName(), _listenerClass),
                _beanClass.getMethod(REMOVE_PREFIX + _listenerClass.getSimpleName(), _listenerClass)
            );

            return new EventSetDescriptor[]{descriptor};

        } catch (IntrospectionException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MethodDescriptor[] getMethodDescriptors() {

        List<Method> allMethods = Arrays.asList(_beanClass.getDeclaredMethods());
        List<Method> targetMethods = allMethods.stream()
            .filter(method -> method.isAnnotationPresent(TargetDescriptor.class))
            .collect(Collectors.toList());

        return targetMethods.stream()
            .map(MethodDescriptor::new)
            .toArray(MethodDescriptor[]::new);
    }
}
