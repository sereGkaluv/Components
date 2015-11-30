package interfaces;


public interface IOable<in, out> extends Readable<out>, Writable<in> {

}
