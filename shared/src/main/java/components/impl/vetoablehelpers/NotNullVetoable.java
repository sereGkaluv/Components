package components.impl.vetoablehelpers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

/**
 * Created by sereGkaluv on 06-Dec-15.
 */
public class NotNullVetoable {

    private NotNullVetoable() {
    }

    public static Object validate(PropertyChangeEvent evt)
    throws PropertyVetoException {
        Object newValue = evt.getNewValue();

        if (newValue == null) {
            String msg = evt.getPropertyName().toUpperCase() + " should not be null.";
            throw new PropertyVetoException(msg, evt);
        }

        return newValue;
    }
}
