package tfw.immutable.stream;

import tfw.check.Argument;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;

public class DoubleStreamFactoryFromDoubleIlaFactory {
    private DoubleStreamFactoryFromDoubleIlaFactory() {}

    public static DoubleStreamFactory create(final DoubleIlaFactory factory) {
        Argument.assertNotNull(factory, "factory");

        return () -> DoubleStreamFromDoubleIla.create(factory.create());
    }
}
