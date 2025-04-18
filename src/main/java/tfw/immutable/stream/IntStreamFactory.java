package tfw.immutable.stream;

import java.io.IOException;
import java.util.stream.IntStream;

public interface IntStreamFactory {
    IntStream create() throws IOException;
}
