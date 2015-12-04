package filter;

import interfaces.Readable;
import interfaces.Writable;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;


public abstract class DataMergeFilter<T, U, V> implements Readable<V>, Runnable {
    private Readable<T> m_BaseInput = null;
    private Readable<U> m_OverInput = null;
    private Writable<V> m_Output = null;

    public static Object ENDING_SIGNAL = null;

    public DataMergeFilter(Readable<T> baseInput, Readable<U> overInput)
    throws InvalidParameterException {
        if (baseInput == null){
            throw new InvalidParameterException("Base input can't be null!");
        }
        m_BaseInput = baseInput;

        if (overInput == null){
            throw new InvalidParameterException("Over input can't be null!");
        }
        m_OverInput = overInput;
    }

    public DataMergeFilter(Writable<V> output)
    throws InvalidParameterException {
        if (output == null){
            throw new InvalidParameterException("Output can't be null!");
        }
        m_Output = output;
    }

    public DataMergeFilter(Readable<T> baseInput, Readable<U> overInput, Writable<V> output)
    throws InvalidParameterException {
        if (baseInput == null){
            throw new InvalidParameterException("Base input can't be null!");
        }
        m_BaseInput = baseInput;

        if (overInput == null){
            throw new InvalidParameterException("Over input can't be null!");
        }
        m_OverInput = overInput;

        if (output == null){
            throw new InvalidParameterException("Output can't be null!");
        }
        m_Output = output;
    }

    @Override
    public V read()
    throws StreamCorruptedException {

        T baseEntity = readBaseInput();
        U overEntity = readOverInput();
        if (baseEntity != null && overEntity != null) return process(baseEntity, overEntity);
        return null;
    }

    @Override
    public void run() {
        T baseValue = null;
        U overValue = null;

        try {

            do {

                baseValue = readBaseInput();
                overValue = readOverInput();

                if (baseValue != null && overValue != null) writeOutput(process(baseValue, overValue));

            } while (baseValue != null && overValue != null);

            sendEndSignal();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Does the merge of baseValue entity and overValue entity to merged entity
     * @param baseValue Value to which changes will be merged (will be used as a base)
     * @param overValue Value that will be used as a changes source (will be overwritten over the base)
     * @return V merged entity
     */
    protected abstract V process(T baseValue, U overValue);

    protected T readBaseInput()
    throws StreamCorruptedException {
        try {
            return readInput(m_BaseInput);
        } catch (StreamCorruptedException e) {
            throw new StreamCorruptedException("Base input is null");
        }
    }

    protected U readOverInput()
    throws StreamCorruptedException {
        try {
            return readInput(m_OverInput);
        } catch (StreamCorruptedException e) {
            throw new StreamCorruptedException("Over input is null");
        }
    }

    protected void writeOutput(V value)
    throws StreamCorruptedException{
        if (m_Output != null){
            if (value == ENDING_SIGNAL) beforeSendingEndingSignal();
            m_Output.write(value);
        }else{
            throw new StreamCorruptedException("Output is null");
        }
    }

    /**
     * Send a signal, that the end of the stream has been reached
     */
    protected void sendEndSignal()
    throws StreamCorruptedException {
        writeOutput(null);
    }

    /**
     * Derived class may override this, if they are interested in
     * getting informed before the ending-signal is sended
     */
    protected void beforeSendingEndingSignal()
    throws StreamCorruptedException {}

    private <W> W readInput(Readable<W> readable)
    throws StreamCorruptedException{
        if (readable != null) {
            return readable.read();
        } else {
            throw new StreamCorruptedException();
        }
    }
}
