package impl.vetoablehelpers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

/**
 * Created by sereGkaluv on 06-Dec-15.
 */
public class StringVetoable {

    private StringVetoable() {
    }

    public static String validate(PropertyChangeEvent evt)
    throws PropertyVetoException {
        String newString = (String) evt.getNewValue();

        if (newString == null || newString.trim().isEmpty()) {
            String msg = evt.getPropertyName().toUpperCase() + " should not be empty.";
            throw new PropertyVetoException(msg, evt);
        }

        return newString;
    }
}
