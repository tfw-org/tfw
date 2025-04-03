package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaFromCastLongIla;
import tfw.immutable.ilaf.longilaf.LongIlaFactory;

public class FloatIlaFactoryFromCastLongIlaFactory {
    private FloatIlaFactoryFromCastLongIlaFactory() {}

    public static FloatIlaFactory create(LongIlaFactory longIlaFactory, final int bufferSize) {
        Argument.assertNotNull(longIlaFactory, "longIlaFactory");

        return () -> FloatIlaFromCastLongIla.create(longIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
