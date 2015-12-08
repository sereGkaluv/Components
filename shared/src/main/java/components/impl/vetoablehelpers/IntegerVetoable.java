package components.impl.vetoablehelpers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

/**
 * Created by sereGkaluv on 06-Dec-15.
 */
public class IntegerVetoable {

    private IntegerVetoable() {
    }

    public static int validate(PropertyChangeEvent evt, int minValue)
    throws PropertyVetoException {

        int integerValue = (Integer) NotNullVetoable.validate(evt);

        if (integerValue < minValue) {
            String msg = evt.getPropertyName().toUpperCase() + " should be > " + minValue + ".";
            throw new PropertyVetoException(msg, evt);
        }

        return integerValue;
    }

    public static int validate(PropertyChangeEvent evt, int minValue, int maxValue)
    throws PropertyVetoException {

        int integerValue = (Integer) NotNullVetoable.validate(evt);


        if (integerValue < minValue || integerValue > maxValue) {

            String msg = evt.getPropertyName().toUpperCase() +" should be in range from \"" +
                         minValue + "\" to \"" + maxValue + "\".";
            throw new PropertyVetoException(msg, evt);
        }

        return integerValue;
    }
}
