package tfw.immutable.ilaf.doubleilaf;

import tfw.check.Argument;
import tfw.immutable.ila.doubleila.DoubleIlaFromCastFloatIla;
import tfw.immutable.ilaf.floatilaf.FloatIlaFactory;

public class DoubleIlaFactoryFromCastFloatIlaFactory {
    private DoubleIlaFactoryFromCastFloatIlaFactory() {}

    public static DoubleIlaFactory create(FloatIlaFactory floatIlaFactory, final int bufferSize) {
        Argument.assertNotNull(floatIlaFactory, "floatIlaFactory");

        return () -> DoubleIlaFromCastFloatIla.create(floatIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
