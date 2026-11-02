package tfw.immutable.ila;

import java.io.Closeable;
import java.io.IOException;

public interface ImmutableLongArray extends Closeable {
    long length() throws IOException;
}
