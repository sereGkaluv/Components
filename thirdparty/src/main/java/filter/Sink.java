package filter;

import interfaces.Readable;

public abstract class Sink<T> implements Readable<T>, Runnable{
    public abstract void run();
}
