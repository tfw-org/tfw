package tfw.immutable.ilaf.floatilaf;

import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIlaFromCastDoubleIla;
import tfw.immutable.ilaf.doubleilaf.DoubleIlaFactory;

public class FloatIlaFactoryFromCastDoubleIlaFactory {
    private FloatIlaFactoryFromCastDoubleIlaFactory() {}

    public static FloatIlaFactory create(DoubleIlaFactory doubleIlaFactory, final int bufferSize) {
        Argument.assertNotNull(doubleIlaFactory, "doubleIlaFactory");

        return () -> FloatIlaFromCastDoubleIla.create(doubleIlaFactory.create(), bufferSize);
    }
}
// AUTO GENERATED FROM TEMPLATE
