package tfw.immutable.stream;

import java.io.IOException;
import java.util.stream.DoubleStream;

public interface DoubleStreamFactory {
    DoubleStream create() throws IOException;
}
