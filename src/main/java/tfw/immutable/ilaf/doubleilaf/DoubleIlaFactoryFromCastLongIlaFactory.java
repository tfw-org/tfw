package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class DoubleIlaFactoryFromCastLongIlaFactory {
    private DoubleIlaFactoryFromCastLongIlaFactory() {}

    public static DoubleIlaFactory create(LongIlaFactory longIlaFactory, final int bufferSize) {
        Argument.assertNotNull(longIlaFactory, "longIlaFactory");

        return () -> DoubleIlaFromCastLongIla.create(longIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
