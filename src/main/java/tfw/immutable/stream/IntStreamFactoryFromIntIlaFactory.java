package tfw.immutable.stream;

import tfw.check.Argument;
import tfw.immutable.ilaf.intilaf.IntIlaFactory;

public class IntStreamFactoryFromIntIlaFactory {
    private IntStreamFactoryFromIntIlaFactory() {}

    public static IntStreamFactory create(final IntIlaFactory factory) {
        Argument.assertNotNull(factory, "factory");

        return () -> IntStreamFromIntIla.create(factory.create());
    }
}
