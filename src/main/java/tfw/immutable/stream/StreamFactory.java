package tfw.immutable.stream;

import java.io.IOException;
import java.util.stream.Stream;

public interface StreamFactory<T> {
    Stream<T> create() throws IOException;
}
