package tfw.immutable.ilaf.longilaf;

import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIlaFromCastFloatIla;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;

public class LongIlaFactoryFromCastFloatIlaFactory {
    private LongIlaFactoryFromCastFloatIlaFactory() {}

    public static LongIlaFactory create(FloatIlaFactory floatIlaFactory, final int bufferSize) {
        Argument.assertNotNull(floatIlaFactory, "floatIlaFactory");

        return () -> LongIlaFromCastFloatIla.create(floatIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
