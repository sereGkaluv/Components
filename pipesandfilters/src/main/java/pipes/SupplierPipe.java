package pipes;

import interfaces.Readable;

import java.io.StreamCorruptedException;

/**
 * Created by sereGkaluv on 06-Dec-15.
 */
public class SupplierPipe<T> implements Readable<T> {
    private final T _value;

    public SupplierPipe(T value) {
        _value = value;
    }

    @Override
    public T read() throws StreamCorruptedException {
        return _value;
    }
}
