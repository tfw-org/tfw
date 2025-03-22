package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIlaFromCastDoubleIla;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;

public class LongIlaFactoryFromCastDoubleIlaFactory {
    private LongIlaFactoryFromCastDoubleIlaFactory() {}

    public static LongIlaFactory create(DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
        Argument.assertNotNull(doubleIlaFactory, "doubleIlaFactory");

        return () -> LongIlaFromCastDoubleIla.create(doubleIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
