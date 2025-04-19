package tfw.immutable.stream;

import java.io.IOException;
import java.util.stream.LongStream;

public interface LongStreamFactory {
    LongStream create() throws IOException;
}
