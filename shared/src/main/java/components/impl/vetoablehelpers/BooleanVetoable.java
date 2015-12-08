package components.impl.vetoablehelpers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

/**
 * Created by sereGkaluv on 06-Dec-15.
 */
public class BooleanVetoable {

    private BooleanVetoable() {
    }

    public static boolean validate(PropertyChangeEvent evt)
    throws PropertyVetoException {
        Boolean newBoolean = (Boolean) evt.getNewValue();

        if (newBoolean == null) {
            String msg = evt.getPropertyName().toUpperCase() + " should not be null.";
            throw new PropertyVetoException(msg, evt);
        }

        return newBoolean;
    }
}
