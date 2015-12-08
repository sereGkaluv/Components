package components.interfaces;

import java.io.StreamCorruptedException;

public interface Readable<T>  {
	T read() throws StreamCorruptedException;
}
