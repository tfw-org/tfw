package tfw.immutable.stream;

import tfw.check.Argument;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class LongStreamFactoryFromLongIlaFactory {
    private LongStreamFactoryFromLongIlaFactory() {}

    public static LongStreamFactory create(final LongIlaFactory factory) {
        Argument.assertNotNull(factory, "factory");

        return () -> LongStreamFromLongIla.create(factory.create());
    }
}
