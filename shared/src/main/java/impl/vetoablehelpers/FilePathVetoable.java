package impl.vetoablehelpers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.File;

/**
 * Created by sereGkaluv on 06-Dec-15.
 */
public class FilePathVetoable {

    private FilePathVetoable() {
    }

    public static boolean validate(PropertyChangeEvent evt)
    throws PropertyVetoException {
        String newPath = StringVetoable.validate(evt);

        File file = new File(newPath);
        if(!file.exists() || file.isDirectory()) {
            String msg = evt.getPropertyName().toUpperCase() + " should target existing file.";
            throw new PropertyVetoException(msg, evt);
        }

        return true;
    }
}
