package tfw.immutable.stream;

import tfw.check.Argument;
import tfw.immutable.ilaf.objectilaf.ObjectIlaFactory;

public class StreamFactoryFromObjectIlaFactory {
    private StreamFactoryFromObjectIlaFactory() {}

    public static <T> StreamFactory<T> create(final ObjectIlaFactory<T> factory, final Class<T> clazz) {
        Argument.assertNotNull(factory, "factory");
        Argument.assertNotNull(clazz, "clazz");

        return () -> StreamFromObjectIla.create(factory.create(), clazz);
    }
}
