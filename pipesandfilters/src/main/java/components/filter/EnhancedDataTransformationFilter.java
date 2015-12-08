package components.filter;

import components.interfaces.Readable;
import components.interfaces.Writable;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;


public abstract class EnhancedDataTransformationFilter<T> extends AbstractFilter<T,T> {

    public EnhancedDataTransformationFilter(Readable<T> input, Writable<T> output)
    throws InvalidParameterException {
        super(input, output);
    }

    public EnhancedDataTransformationFilter(Readable<T> input)
    throws InvalidParameterException {
        super(input);
    }

    public EnhancedDataTransformationFilter(Writable<T> output)
    throws InvalidParameterException {
        super(output);
    }

    public T read()
    throws StreamCorruptedException {

        T entity = readInput();
        if (entity != null) return process(entity);
        return null;
    }

    public void write(T value)
    throws StreamCorruptedException {

        if (value != null) writeOutput(process(value));
    }
    
    /**
     * does the transformation on indsys.entity
     * @param entity
     */
    protected abstract T process(T entity);

    public void run() {
        T input = null;
        try {

            do {
    
                input = readInput();
                if (input != null) writeOutput(process(input));
                
            } while (input != null);

            sendEndSignal();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
}
